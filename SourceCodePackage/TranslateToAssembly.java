
import java.util.*;
import java.io.*;
import AuxClass.Parser.*;
/**
 * @author Pablo Riveros Perea
 * <p><b>Functions and objects within the class</b></p>
 * <ul>
 *   <li><code>translate</code> – Main method for translating VM code to Hack assembly.</li>
 *   <li><code>CreatePredefindArrays</code> – Initializes lists of VM commands and their assembly representations.</li>
 *   <li><code>CreateHash</code> – Fills a HashMap with command-template pairs.</li>
 *   <li><code>replace</code> – Replaces a VM command line with its corresponding assembly code template.</li>
 * </ul>
 * <p><b>Objects:</b></p>
 * <ul>
 *   <li>predet – HashMap containing the mapping of VM commands to assembly templates.</li>
 * </ul>
 * <p>
 * <b>Note:</b> This class and all its methods and objects are tightly coupled to the internal logic and requirements of this specific VM translator project. 
 * They were designed exclusively for this context and are not intended for general-purpose or external use.
 * </p>
 */
public class TranslateToAssembly {
/**
 * Translates a VM code file into Hack assembly code and writes the result to an output file.
 * <p>
 * This is the main translation method of the VM-to-assembly translator. It reads the input VM file, processes each command line,
 * and generates the corresponding Hack assembly code, handling bootstrap code insertion, command parsing, argument extraction,
 * and special cases such as function calls and boolean logic.
 * <br>
 * The method is tightly integrated with the rest of the translator and expects specific mutable parameters for state tracking and folder processing.
 * <br>
 * Returns 0 if the translation is successful, or -1 if any error occurs.
 *
 * <b>Parameters:</b>
 * @param file_in The path to the input VM file.
 * @param nameOfAssemblyFileGenerate The path for the output assembly file to generate.
 * @param indicatePutBootStrap Mutable flag to indicate if the bootstrap code has already been inserted.
 * @param itsPartOfFolder True if the file is part of a folder translation (affects label and function naming).
 * @param counterForFolders Object containing counters for unique label/function generation across multiple files.
 * @return 0 if translation is successful; -1 if an error occurs.
 */
public int translate(String file_in, String nameOfAssemblyFileGenerate, Parser.MutableTypeData<Boolean> indicatePutBootStrap, boolean itsPartOfFolder, Counters counterForFolders){
    System.out.println("\nGENERATING ASSEMBLY FILE...\n");
    if(nameOfAssemblyFileGenerate == null || file_in == null){
        System.err.println("Error: Need puth both parameters\n");
        return -1;
    }
    if(nameOfAssemblyFileGenerate.isEmpty() || file_in.isEmpty()){
        System.err.println("Error: Need puth something in the parameters\n");
        return -1;
    }
    if(itsPartOfFolder){
        if(counterForFolders == null){
            System.err.println("Error: if its a file from a folder need put the counters\n");
            return -1;
        } 
    }
    Parser function = new Parser();
    int n;
    ArrayList<String>commands = new ArrayList<>();
    ArrayList<String>representationAssembly = new ArrayList<>();
    ArrayList<Boolean>state = new ArrayList<>();
    ArrayList<String> repeatCode = new ArrayList<>();
    CreatePredefindArrays(commands, representationAssembly, state, repeatCode);
    n= CreateHash(commands, representationAssembly, predet);
    try{
         function.removeVoidLines(file_in);
         function.removeVoidChars(file_in, null);
    } catch (ParsingException e) {
        System.out.println("Error parsing file: " + file_in);}
    if(n != 0) return -1;
    String fileEdit = "COPY♫file_in.txt";
    //Bootstrap code insert one time
    //Bootstrap code\n@SP\nM=256\n//call Sys.init 0\n
    
    try(BufferedReader file = new BufferedReader(new FileReader(file_in));
    BufferedWriter writteFile = new BufferedWriter(new FileWriter(fileEdit))){
        String line2 = null;
        if(!indicatePutBootStrap.getValor()) writteFile.write("callSys.init~0\n");
            
        while((line2 = file.readLine()) != null){
            writteFile.write(line2);
            writteFile.newLine();
        }
        
    } catch (IOException e) {
        System.out.println("Error creating COPY file: " + e.getMessage());
        return -1;
    }
  
    try(BufferedReader file = new BufferedReader( new FileReader(fileEdit));
    BufferedWriter writteFile = new BufferedWriter(new FileWriter(nameOfAssemblyFileGenerate))){
        //put one time the bootstrap code
        //poner una vez el codigo de bootstrap
        if(!indicatePutBootStrap.getValor()){
            writteFile.write("//Bootstrap code\n//Iinitialize SP\n@256\nD=A\n@SP\nM=D\n");
            indicatePutBootStrap.setValor(true);
        }
        String line;
        Parser.MutableTypeData<String> valueArg = function.new MutableTypeData<>("");
        Parser.MutableTypeData<Boolean> isBoolCommand = function.new MutableTypeData<>(false);
        Parser.MutableTypeData<Boolean> isArgCommand = function.new MutableTypeData<>(false);
       Parser.MutableTypeData<String> arg = function.new MutableTypeData<>(""); 
       Parser.MutableTypeData<Integer>lengthArg = function.new MutableTypeData<>(0);
       Parser.MutableTypeData<Integer>lengthCommand = function.new MutableTypeData<>(0);
       Parser.MutableTypeData<Integer>staticlabel = function.new MutableTypeData<>(0);
       Parser.MutableTypeData<Boolean>flag = function.new MutableTypeData<>(false);
       HashMap<String, String>segments = new HashMap<>();
       segments.put("constant", "@SP");
       segments.put("local","@LCL" );
       segments.put("argument", "@ARG");
       String nameOfFile = file_in.substring(0, file_in.lastIndexOf('.'));
       segments.put("static", "@"+nameOfFile+"."+Integer.toString(staticlabel.getValor()));
       segments.put("this", "@THIS");
       segments.put("that", "@THAT");
       segments.put("pointer0", "@THIS");
       segments.put("pointer1", "@THAT");
       segments.put("temp", "@5");
       ArrayList<String>excep = new ArrayList<>();
       excep.add("pointer");
       int i = 0, c = 0, localPut = 0, functionsCalls = 0;
       if(itsPartOfFolder){
        i = counterForFolders.counter1.getValor();
        c = counterForFolders.counter2.getValor();
        localPut = counterForFolders.counter3.getValor();
        functionsCalls = counterForFolders.counter4.getValor();
       } 
       String functionName = "";
       boolean beforeIsBoolCommand = false;
      while((line = file.readLine()) != null){
      flag.setValor(false);
       String  nLine = null;
       String  tr = "(true♫" +Integer.toString(i)+")";
       String fs = "(false~"+Integer.toString(i)+")";
       String continueH = "(Continue~"+Integer.toString(c)+")";
       String continueHCall = "@Continue~"+Integer.toString(c)+"\n0;JMP";
       String trCall = "@true♫"+Integer.toString(i);
       String fsCall = "@false~"+Integer.toString(i);
        String assembly = replace(line, predet, nLine, lengthCommand, arg, isBoolCommand, true, lengthArg, valueArg, isArgCommand, excep, flag);
        if(assembly == null){
            System.out.println("Error in the line: "+nLine+"\n");
            return -1;
        }
        if(isBoolCommand.getValor()){
            beforeIsBoolCommand = true;
            if(assembly.contains("ct") || assembly.contains("cf")){
        assembly = assembly.replace("ct", trCall);
        assembly = assembly.replace("cf", fsCall);
           String trueandfalse= repeatCode.get(0);
           trueandfalse = trueandfalse.replace("t", tr);
           trueandfalse = trueandfalse.replace("f", fs);
           trueandfalse = trueandfalse.replace("/", continueHCall);
           i++;
           c++;
            
           writteFile.write(assembly+trueandfalse);
           writteFile.newLine();
           writteFile.write(continueH);
           writteFile.newLine();
           }
           else writteFile.write(assembly);
           beforeIsBoolCommand = true;
           continue;
           }
       else if(isArgCommand.getValor()){
          if(flag.getValor()){
             arg.setValor(arg.getValor()+valueArg.getValor());
              valueArg.setValor("0");
            }
           if(line.substring(0, lengthCommand.getValor()).equals("label") || line.substring(0, lengthCommand.getValor()).equals("goto") ||
           line.substring(0, lengthCommand.getValor()).equals("if-goto")){
            if(!beforeIsBoolCommand && line.substring(0, lengthCommand.getValor()).equals("if-goto"))assembly = "\n//comment\n@SP\nA=M-1\nD=M\n@SP\nM=M-1\n@LBL\nD;JGT\n";
             assembly = assembly.replace("LBL", functionName+"$"+arg.getValor());
             assembly = assembly.replace("comment", line);
             }
           else{
           if(line.substring(0, lengthCommand.getValor()).equals("function")){
            functionName = arg.getValor();
            assembly = assembly.replace("FCM", "function "+functionName+" "+valueArg.getValor());
            assembly = assembly.replace("RNMF", arg.getValor());
            assembly = assembly.replace("nArgs", valueArg.getValor());
            assembly = assembly.replace("ñ", "ñ"+Integer.toString(localPut));
            assembly = assembly.replace("continueH", "inyectLocal-ñ-~♫"+Integer.toString(localPut)); 
            localPut++;
           }
           else{
           if(line.substring(0, lengthCommand.getValor()).equals("call")){
            assembly = assembly.replace("comment", "call "+arg.getValor()+" "+valueArg.getValor());
            assembly = assembly.replace("NAME_F", arg.getValor());
            assembly = assembly.replace("numberI", Integer.toString(functionsCalls));
            assembly = assembly.replace("ArgPos", valueArg.getValor());
            assembly = assembly.replace("RPCFN", arg.getValor());
            functionsCalls++;
           }
           else{
            if(arg.getValor().equals("constant")){
                assembly = "\n//comment\n@"+valueArg.getValor()+"\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
                assembly = assembly.replace("comment", line);
            }
            else{
                if(arg.getValor().equals("pointer1")|| arg.getValor().equals("pointer0")){
                    if(line.substring(0, lengthCommand.getValor()).equals("pop"))assembly = "\n//comment\n@SP\nA=M-1\nD=M\nRARG\nM=D\n@SP\nM=M-1\n";
                        else assembly = "\n//comment\nRARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
                        assembly = assembly.replace("RARG", segments.get(arg.getValor()));  
                        assembly = assembly.replace("comment", line);
                }
                else{
                    if(arg.getValor().equals("static")){
                        if(line.substring(0, lengthCommand.getValor()).equals("pop")){
                            assembly = "\n//comment\n@SP\nA=M-1\nD=M\n@staticBase\nM=D\n@SP\nM=M-1\n";
                        }
                        else assembly = "\n//comment\n@staticBase\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
                        assembly = assembly.replace("comment", line);
                        assembly = assembly.replace("staticBase", nameOfFile+"."+valueArg.getValor());
                        staticlabel.setValor(staticlabel.getValor()+1);//increment the static label value for generate a new label
                    }
                    else{
                    if(arg.getValor().equals("temp")){
                        if(line.substring(0, lengthCommand.getValor()).equals("pop"))assembly = "\n//comment\nRPI\nD=A\nRARG\nD=A+D\n@SP\nA=M\nM=D\n@SP\nA=M-1\nD=M\n@SP\nA=M//go to the last value store in the stack\nA=M//go to this value\nM=D\n@SP\nA=M\nM=0\n@SP\nA=M-1\n@SP\nM=M-1\n";
                      else assembly = "\n//comment\nRPI\nD=A\nRARG\nA=A+D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
                      
                    }
                    
                    
                 assembly = assembly.replace("comment", line);
          assembly = assembly.replace("RPI", "@"+valueArg.getValor());
          assembly = assembly.replace("RARG", segments.get(arg.getValor()));    
                }
                
                }
            }
           }
          }
         }
        beforeIsBoolCommand = false;
        }
        if(itsPartOfFolder){
            counterForFolders.counter1.setValor(i);
            counterForFolders.counter2.setValor(c);
            counterForFolders.counter3.setValor(localPut);
            counterForFolders.counter4.setValor(functionsCalls);
        }
       writteFile.write(assembly);
       writteFile.newLine();
        }
    }
    catch (FileNotFoundException e) {
        System.out.println("File not found: " + file_in);
        return -1;
    } catch (IOException e) {
        System.out.println("Error reading file: " + file_in);
        return -1;
    }
    //remove the copy file
    File copyFile = new File(fileEdit);
    if(copyFile.exists()){
        if(!copyFile.delete()){
            System.out.println("Error deleting the copy file: " + fileEdit);
            return -1;
        }
    }
    try{function.removeVoidLines(nameOfAssemblyFileGenerate);
    } catch (ParsingException e) {
        System.out.println("Error parsing Assembly file: " + e.getMessage());
        return -1;
    }
    System.out.println("\nASSEMBLY FILE GENERATE\n");
    return 0;
}
public HashMap<String, String>predet = new HashMap<>();
//------------------------------------------------------------------------
/**
 * Initializes the lists of VM commands, their corresponding assembly code representations, state flags, and constant code snippets.
 * <p>
 * This method fills the provided lists with the predefined VM commands supported by the translator, their associated Hack assembly code templates, and any required state or constant code.
 * The order of elements in each list corresponds, so the first command matches the first assembly code, and so on.
 * These arrays are used to build the translation HashMap and to support the translation process.
 *
 * <b>Parameters:</b>
 * @param commands List to be filled with the names of supported VM commands.
 * @param representationAssembly List to be filled with the corresponding Hack assembly code templates for each command.
 * @param state List to be filled with boolean flags for each command (usage may vary by implementation).
 * @param constantsC List to be filled with constant code snippets used in translation (e.g., for boolean logic).
 */
public void CreatePredefindArrays(ArrayList<String>commands, ArrayList<String>representationAssembly, ArrayList<Boolean>state, ArrayList<String>constantsC){
    commands.add("add");
    representationAssembly.add("\n//'add' command\n@SP\nA=M-1\nD=M\nA=A-1\nD=D+M\n@SP\nA=M-1\nA=A-1\nM=D\n@SP\nM=M-1");
    commands.add("sub");
    representationAssembly.add("\n//'sub' command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M-D\n@SP\nA=M-1\nA=A-1\nM=D\n@SP\nM=M-1"); 
    commands.add("neg");
    representationAssembly.add("\n//'neg' command\n@SP\nA=M-1\nM=-M\n");
    commands.add("lt");
    representationAssembly.add("\n//'lt' command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M-D\nct\nD;JLT\ncf\nD;JGE\n");
    commands.add("gt");
    representationAssembly.add("\n//'gt' command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M-D\nct\nD;JGT\ncf\nD;JLE\n");
    commands.add("eq");
    representationAssembly.add("\n//'eq' command\n@SP\nA=M-1\nA=A-1\nD=M-D\nct\nD;JEQ\ncf\nD;JNE\n");
    commands.add("pop");
    representationAssembly.add("\n//comment\nRPI\nD=A\nRARG\nD=M+D\n@SP\nA=M\nM=D\n@SP\nA=M-1\nD=M\n@SP\nA=M//go to the last value store in the stack\nA=M//go to this value\nM=D\n@SP\nA=M\nM=0\n@SP\nA=M-1\n@SP\nM=M-1\n");
    commands.add("push");
    representationAssembly.add("\n//comment\nRPI\nD=A\nRARG\nA=M+D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    commands.add("and");
    representationAssembly.add("\n//'and' command\n@SP\nA=M-1\nA=A-1\nD=M\n@SP\nA=M-1\nD=M-D\nct\nD;JEQ\ncf\nD;JNE\n");
    commands.add("or");
    representationAssembly.add("\n//'or' command\n@2\nD=A\n@SP\nA=M-1\nA=A-1\nM=-M\nD=D-M\n@SP\nA=M\nM=D\n@SP\nA=M-1\nD=-M\n@SP\nA=M\nD=D-M\nD=D-1\nct\nD;JLE\ncf\nD;JGT\n");
    commands.add("not");
    representationAssembly.add("\n//'not' command\n@SP\nA=M-1\nM=!M\n");
    commands.add("label");
    representationAssembly.add("\n//comment\n(LBL)\n");
    commands.add("goto");
    representationAssembly.add("\n//comment\n@LBL\n0;JMP\n");
    commands.add("if-goto");
    representationAssembly.add("\n//comment\n@SP\nA=M-1\nD=M\n@SP\nM=M-1\n@LBL\nD;JLT\n");
    commands.add("call");
    representationAssembly.add("\n//comment\n/*push return address\n*code after the 'call' or the next line*/\n@NAME_F$ret.numberI\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
                    "//push LCL pointer value\n@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
                    "//push ARG pointer value\n@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
                    "//push THIS pointer value\n@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
                    "//push THAT pointer value\n@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
                    "//reposition ARG pointer value\n@SP\nD=M\n@5\nD=D-A\n@ArgPos\nD=D-A\n@ARG\nM=D\n"+
                    "//repostion LCL pointer value\n@SP\nD=M\n@LCL\nM=D\n//goto RPCFN\n@RPCFN\n0;JMP\n(NAME_F$ret.numberI)\n");
    
    commands.add("function");
    representationAssembly.add("\n//FCM\n(RNMF)\n@nArgs\nD=A\n($Esp_Lo~opñ)\n@continueH\nD;JLE\n@SP\nA=M\nM=0\n@SP\nM=M+1\nD=D-1\n@$Esp_Lo~opñ\n0;JMP\n(continueH)\n");
    commands.add("return");
    representationAssembly.add("\n//RETURN\n"+
    "//get and save the retrun address\n@LCL\nD=M\n@5\nD=D-A\nA=D\nD=M\n@RFRNAD~\nM=D\n"+
    "//put the value in ARG from the caller\n@SP\nA=M-1\nD=M\n@ARG\nA=M\nM=D"+
    "//reposition SP\n@ARG\nD=M\n@SP\nM=D+1\n"+
    "/*\nRestore and recuperate the values\n*THAT\n*THIS\n*ARG\n*LCL\n*/\n"+
    "//restore THAT\n@LCL\nA=M-1\nD=M\n@THAT\nM=D\n"+
    "//restore THIS\n@2\nD=A\n@LCL\nA=M-D\nD=M\n@THIS\nM=D\n"+
    "//restore ARG\n@3\nD=A\n@LCL\nA=M-D\nD=M\n@ARG\nM=D\n"+
    "//restore LCL\n@4\nD=A\n@LCL\nA=M-D\nD=M\n@LCL\nM=D\n"+
    "//goto Return-Addr\n@RFRNAD~\nA=M\n0;JMP\n");
    //constant code
    //codigo constante
    constantsC.add("\nt\n@SP\nA=M-1\nA=A-1\nM=-1\n@SP\nM=M-1\n@SP\nA=M\nM=0\n/\nf\n@SP\nA=M-1\nA=A-1\nM=0\n@SP\nM=M-1\n@SP\nA=M\nM=0\n");
}
//------------------------------------------------------------------------
/**
 * Fills a HashMap with key-value pairs from two lists.
 * <p>
 * This method takes two lists, one of keys and one of values, and inserts their pairs into the provided HashMap.
 * The insertion is done in order, so the first key is paired with the first value, the second key with the second value, and so on.
 * If any of the parameters are {@code null}, or if the lists are of different sizes, the method prints an error message and returns -1.
 * <br>
 * Returns 0 if the operation is successful.
 *
 * <b>Parameters:</b>
 * @param keys List of keys to insert into the HashMap.
 * @param values List of values to associate with the keys.
 * @param hashT The HashMap to fill with the key-value pairs.
 * @return 0 if the HashMap was filled successfully; -1 if an error occurred.
 */
public int CreateHash(ArrayList<String>keys, ArrayList<String>values, HashMap<String, String>hashT){
    int i = 0;
    if(keys == null || values == null || hashT == null){
        System.err.println("Error put all the paramters\n");
        return -1;
    }
    for(String key : keys){
         String value = "";
        if (!values.isEmpty()) {
       value = values.get(i);
       i++;
     }
    hashT.put(key, value);
   }
   return 0;
}
//------------------------------------------------------------------------
/**
 * Replaces a VM command line with its corresponding assembly code using a HashMap of templates and extracts arguments if needed.
 * <p>
 * This method processes a single VM command line, determines its type (arithmetic, boolean, command with arguments, etc.), and replaces it with the appropriate assembly code template from the provided HashMap.
 * It also extracts and sets argument values, command lengths, and flags for special cases (such as boolean commands or commands with flexible patterns).
 * <br>
 * The method is designed to work closely with the rest of the VM translator and expects specific mutable parameters for output and state tracking.
 * <br>
 * Returns the generated assembly code as a string, or {@code null} if an error occurs.
 *
 * <b>Parameters:</b>
 * @param line The VM command line to process.
 * @param RelaseKeyValue The HashMap containing command templates (keys: VM commands, values: assembly code).
 * @param nLine The line number or identifier (for error messages).
 * @param lengthofCommand Mutable variable to store the detected command length.
 * @param arg Mutable variable to store the detected argument.
 * @param BoolCommand Mutable flag to indicate if the command is a boolean command.
 * @param withArgsCommands Indicates if the command expects arguments.
 * @param lengthofarg Mutable variable to store the detected argument length.
 * @param ivalue Mutable variable to store the value of the argument.
 * @param argCommand Mutable flag to indicate if the command is an argument command.
 * @param excpetionsArgs List of argument exceptions (can be null).
 * @param exceptionFlag Mutable flag to indicate if an exception argument was found (can be null).
 * @return The resulting assembly code as a string, or {@code null} if an error occurs.
 */
public String replace(String line, HashMap<String, String> RelaseKeyValue, String nLine, Parser.MutableTypeData<Integer> lengthofCommand, Parser.MutableTypeData<String> arg,  Parser.MutableTypeData<Boolean> BoolCommand, boolean withArgsCommands, Parser.MutableTypeData<Integer> lengthofarg, Parser.MutableTypeData<String> ivalue, Parser.MutableTypeData<Boolean>argCommand, ArrayList<String>excpetionsArgs, Parser.MutableTypeData<Boolean>exceptionFlag){
    //Check the parameters    
    if(RelaseKeyValue == null){
            System.err.println("Error: Need put an argument in 'RealseKeyValue' parameter\n");
            return null;
        }
        if(line == null){
            System.err.println("Error: Need put an argument in a 'line' parameter\n");
            return null;
        }
        if(withArgsCommands && (lengthofarg == null || ivalue == null || arg == null)){
           System.err.println("Error: if you select 'withArgsCommands' need put a argument in 'lengthofarg', 'arg' and 'ivalue' parametera\n");
           return null;      
        }
        if(lengthofCommand == null){
            System.err.println("Error: Need put something in the parameter 'lengthofCommand'\n");
            return null;
        }
        if(exceptionFlag != null && excpetionsArgs == null || excpetionsArgs != null && exceptionFlag == null){
            System.err.println("Error: Need put both parameter if you have exceptions (exceptionFlag and exceptionArgs)\n");
            return null;
        }
        //identify the type of command
        //identificar el tipo de comando
        sintaxParsing n  = new sintaxParsing();
        try{
        n.hashTablePreDet();
        boolean withoutPattern = false, flexibleFormat = false;
        HashMap<String, Integer> others = new HashMap<>();
        n.createHashTable("return", 0, null, others, null);
        if(argCommand != null) argCommand.setValor(false);
        if(n.compareWithHashTable(line, nLine, 6, others, null, false, lengthofCommand)!= 0){
        if(n.compareWithHashTable(line, nLine, 4, null, sintaxParsing.TableHash.Booleans, false, lengthofCommand) != 0){
            if(BoolCommand != null)BoolCommand.setValor(false);
            //if has been commands with args
            //si tiene comandos con argumetos
            if(n.compareWithHashTable(line, nLine, 4, null, sintaxParsing.TableHash.Arithmetics, false, lengthofCommand) != 0 && withArgsCommands){
                ArrayList<String>commandswithoutFormat = new ArrayList<>();
                commandswithoutFormat.add("label");
                commandswithoutFormat.add("goto");
                commandswithoutFormat.add("if-goto");
                   int f = 0;
            ArrayList<String>withoutPatternButWithivalue = new ArrayList<>();
    withoutPatternButWithivalue.add("function");
    withoutPatternButWithivalue.add("call");
    
    ArrayList<Character>specials = new ArrayList<>();
    specials.add('~');
    Map<Character, Integer> removeAllAppearsOfThisChars = new HashMap<>();
        removeAllAppearsOfThisChars.put('N', 0);
        removeAllAppearsOfThisChars.put('L', 0);
        removeAllAppearsOfThisChars.put('P', 0);
        removeAllAppearsOfThisChars.put('W', 0);
        
    CommandArgRule argsCommands = new CommandArgRule.Builder()
    .setCommandTable(n.hashTablePOP_PUSH)
    .setArgTable(n.argsTable)
    .setCommandLength(8)
    .setArgLength(8)
    .setFormatPatternMostLong("pushconstant32768")
    .setFormatPatternLessLong("popthis0")
    .setCommandsWithoutPatterns(commandswithoutFormat)
    .setCommandsWithFlexiblePattern(withoutPatternButWithivalue)
    .setMultipleFormatsPatterns(null)
    .setFormatPatternFlexible("W|N|L|PSN")//format expected for flexible because fleible are the format 'command-name-nVars or Args' tranlate to 'W|N|L|PSN' where W is the command, N is the number of arguments, because the name and the command are letters or nums or cahracters of a name, and the 'nArgs or Vars' is the unique like just expected nums
    .setMultiplesFlexiblesFormatsPatterns(null) // Set to null if not using multiple flexible formats
    .setSpecialCharsForIdentifyInTheFlexibleFormat(specials)
    .setORgateForFlexible('|')
    .setThePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns(true)
    .setTheLineAreInTheFormatExpected(false)
    .setMapForFlexible(removeAllAppearsOfThisChars)
    .setStopForFlexibleForConflicts('S')
    .setStopForFlexible('~')
    .setCommandsWithFlexiblePatternForResultConflicts(withoutPatternButWithivalue)
    .build(); // Create the command argument rule with the specified parameters
              // Crear la regla de argumentos de comando con los parámetros especificados
                if((f = n.compareCommandsWithArg(line, nLine, argsCommands, 0,  lengthofCommand, lengthofarg)) < 0 ) return null;
               else{ 
                if(argCommand != null)argCommand.setValor(true);
                if(f == 2) withoutPattern = true;
                if(f == 3) flexibleFormat = true;
                }
                
            }
        }
        else{
             if(BoolCommand != null)BoolCommand.setValor(true);
             }
    }
        //return the value of command in the hash map
        //devolver el valor de el comando en la tabla hash
        String command = line;
        command = command.trim();//remove de spaces and tab //eliminar los espaicos y tabulaciones
        //if is a commmand with args
        //si es un comando con argumentos
        if(argCommand != null){ 
            if(argCommand.getValor() == true){
                if(withoutPattern){
                 arg.setValor(command.substring(lengthofCommand.getValor(), command.length()));
                }
                else{
                if(flexibleFormat){
                    arg.setValor(command.substring(lengthofCommand.getValor(), command.indexOf('~')));
                    ivalue.setValor(command.substring(command.indexOf('~')+1, command.length()));
                }
                else{
                arg.setValor(command.substring(lengthofCommand.getValor(), lengthofarg.getValor()+lengthofCommand.getValor()));
                String h = command.substring(lengthofarg.getValor()+lengthofCommand.getValor(), command.length());
                 h =h.trim();
                ivalue.setValor(h);
                }
              }
            }
        }
        command = line.substring(0, lengthofCommand.getValor());
        if(excpetionsArgs != null && excpetionsArgs.contains(arg.getValor())) exceptionFlag.setValor(true);
        return RelaseKeyValue.get(command);
    }catch(ParsingException e){
        System.err.println(e.getMessage());
        return null;
    }

  }
}

