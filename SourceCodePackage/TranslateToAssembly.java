
import java.util.*;
import java.io.*;
import AuxClass.Parser.*;
public class TranslateToAssembly {
public int transalte(String file_in){
    System.out.println("\nGENERATING ASSEMBLY FILE...\n");
    Parser function = new Parser();
    int n;
    ArrayList<String>commands = new ArrayList<>();
    ArrayList<String>representationAssembly = new ArrayList<>();
    ArrayList<Boolean>state = new ArrayList<>();
    ArrayList<String> repeatCode = new ArrayList<>();
    CreatePredefindArrays(commands, representationAssembly, state, repeatCode);
    n= CreateHash(commands, representationAssembly, predet);
    try{
         function.removeVoidChars(file_in, null);
    } catch (ParsingException e) {
        System.out.println("Error parsing file: " + file_in);}
    if(n != 0) return -1;
    String fileEdit = "COPY♫file_in";
    //Bootstrap code insert one time
    //Bootstrap code\n@SP\nM=256\n//call Sys.init 0\n
    try(BufferedReader file = new BufferedReader(new FileReader(file_in));
    BufferedWriter writteFile = new BufferedWriter(new FileWriter(fileEdit))){
        String line2 = null;
        writteFile.write("callSys.init~0\n");
        while((line2 = file.readLine()) != null){
            writteFile.write(line2);
            writteFile.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error creating COPY file: " + e.getMessage());
        return -1;
    }
    try(BufferedReader file = new BufferedReader( new FileReader(fileEdit));
    BufferedWriter writteFile = new BufferedWriter(new FileWriter("Assembly.asm"))){
        //put one time the bootstrap code
        //poner una vez el codigo de bootstrap
        writteFile.write("//Bootstrap code\n//Iinitialize SP\n@256\nD=M\n@SP\nM=D\n");
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
       segments.put("temp", "@temp");
       ArrayList<String>excep = new ArrayList<>();
       excep.add("pointer");
       int i = 0, c = 0, localPut = 0;
       int functionsCalls = 0;
       String functionName = "";
       String returnAddress = "";
      while((line = file.readLine()) != null){
      isBoolCommand.setValor(false);
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
           continue;
        }
       else if(isArgCommand.getValor()){
          if(flag.getValor()){
             arg.setValor(arg.getValor()+valueArg.getValor());
              valueArg.setValor("0");
            }
           if(line.substring(0, lengthCommand.getValor()).equals("label") || line.substring(0, lengthCommand.getValor()).equals("goto") ||
           line.substring(0, lengthCommand.getValor()).equals("if-goto")) assembly = assembly.replace("LBL", functionName+"$"+arg.getValor());
           else{
           if(line.substring(0, lengthCommand.getValor()).equals("function")){
            functionName = arg.getValor();
            assembly = assembly.replace("FCM", line);
            assembly = assembly.replace("RNMF", arg.getValor());
            assembly = assembly.replace("nArgs", valueArg.getValor());
            assembly = assembly.replace("ñ", "ñ"+Integer.toString(localPut));
            assembly = assembly.replace("continueH", "inyectLocal-ñ-~♫"+Integer.toString(localPut));
            localPut++;
           }
           else{
           if(line.substring(0, lengthCommand.getValor()).equals("call")){
            assembly = assembly.replace("NAME_F", arg.getValor());
            assembly = assembly.replace("numberI", Integer.toString(functionsCalls));
            returnAddress = arg.getValor()+"$ret."+Integer.toString(functionsCalls);
            assembly = assembly.replace("ArgPos", valueArg.getValor());
            assembly = assembly.replace("RPCFN", arg.getValor());
            functionsCalls++;
           }
           else{
          assembly = assembly.replace("RPI", "@"+valueArg.getValor());
          assembly = assembly.replace("RARG", segments.get(arg.getValor()));
          if(arg.getValor() == "static") staticlabel.setValor(staticlabel.getValor()+1);//incremet the static label value for generete a new label
             
           }
          }
         }
        }
        if(line.substring(0, lengthCommand.getValor()).equals("return")){
            assembly = assembly.replace("RFRNAD", returnAddress);
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
    System.out.println("\nASSEMBLY FILE GENERATE\n");
    return 0;
}
public HashMap<String, String>predet = new HashMap<>();
//------------------------------------------------------------------------
public void CreatePredefindArrays(ArrayList<String>commands, ArrayList<String>representationAssembly, ArrayList<Boolean>state, ArrayList<String>constantsC){
    commands.add("add");
    representationAssembly.add("\n//add command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M+D\n@SP\nA=M-1\nA=A-1\nM=D\n@SP\nM=M-1\n@SP\nA=M\nM=0\n");
    commands.add("sub");
    representationAssembly.add("\n//sub command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M-D\n@SP\nA=M-1\nA=A-1\nM=D\n@SP\nM=M-1\n@SP\nA=M\nM=0");
    commands.add("neg");
    representationAssembly.add("\n//neg command\n@SP\nA=M-1\nM=!M\n");
    commands.add("lt");
    representationAssembly.add("\n//lt command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M-D\nct\nD;JLT\ncf\nD;JGE\n");
    commands.add("gt");
    representationAssembly.add("\n//gt command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M-D\nct\nD;JGT\ncf\nD;JLE\n");
    commands.add("eq");
    representationAssembly.add("\n//eq command\n@SP\nA=M-1\nA=A-1\nD=M-D\nct\nD;JEQ\ncf\nD;JNE\n");
    commands.add("pop");
    representationAssembly.add("\n//pop command\nRPI\nD=A\nRARG\nD=M+D\n@SP\nA=M\nM=D\n@SP\nA=M-1\nD=M\n@SP\nA=M//go to the last value store in the stack\nA=M//go to this value\nM=D\n@SP\nA=M\nM=0\n@SP\nA=M-1\nM=0\n@SP\nM=M-1\n");
    commands.add("push");
    representationAssembly.add("\n//push command\nRPI\nD=A\nRARG\nA=M+D\nD=M\nM=0\n@SP\nA=M\nM=D\n@SP\nM=M+1\n@SP\nA=M\nM=0\n");
    commands.add("and");
    representationAssembly.add("\n//and command\n@SP\nA=M-1\nA=A-1\nD=M\n@SP\nA=M-1\nD=M-D\nct\nD;JEQ\ncf\nD;JGL\n");
    commands.add("or");
    representationAssembly.add("\n//or command\n@2\nD=A\n@SP\nA=M-1\nA=A-1\nD=D-M\n@SP\nA=M-1\nD=M-D\nD=D-1\nct\nD;JLE\ncf\nD;JGT\n");
    commands.add("not");
    representationAssembly.add("\n//not command\n@SP\nA=M-1\nD=!M\nct\nD;JEQ\ncf\nD;JNE\n");
    commands.add("label");
    representationAssembly.add("\n//label command\n(LBL)\n");
    commands.add("goto");
    representationAssembly.add("\n//goto command\n@LBL\n0;JMP\n");
    commands.add("if-goto");
    representationAssembly.add("\n//if-goto command\n@SP\nA=M-1\nD=M-1\n@LBL\nD;JEQ\n");
    commands.add("call");
    representationAssembly.add("\n/*push return address\n*code after the 'call' or the next line*/\n@NAME_F$ret.numberI\nD=A\n@SP\nA=M\nA=D\n@SP\nM=M+1\n"+
                    "//push LCL pointer value\n@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
                    "//push ARG pointer value\n@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
                    "//push THIS pointer value\n@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
                    "//push THAT pointer value\n@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n@SP\nA=M\nM=0\n"+
                    "//reposition ARG pointer value\n@SP\nD=M\n@5\nD=D-A\n@ArgPos\nA=A-1\nD=D-A\n@ARG\nM=D\n"+
                    "//repostion LCL pointer value\n@SP\nD=M\n@LCL\nM=D\n//goto RPCFN\n@RPCFN\n0;JMP\n(NAME_F$ret.numberI)\n");
    
    commands.add("function");
    representationAssembly.add("\n//FCM\n(RNMF)\n@nArgs\nD=A\n($Esp_Lo~opñ)\n@continueH\nD;JLE\n@SP\nA=M\nM=0\n@SP\nM=M+1\nD=D-1\n@$Esp_Lo~opñ\n0;JMP\n(continueH)\n");
    commands.add("return");
    representationAssembly.add("\n@SP\nA=M-1\nD=M\n@ARG\nA=M\nM=D\n"+
    "\n@ARG\nD=M\n//reposition SP\n@SP\nM=D+1\n/*restore and recuperate the values*/\n"+
    "//restore THAT\n@LCL\nA=M-1\nD=M\n@THAT\nM=D\n"+
    "//restore THIS\n@2\nD=A\n@LCL\nA=M-D\nD=M\n@THIS\nM=D\n"+
    "//restore ARG\n@3\nD=A\n@LCL\nA=M-D\nD=M\n@ARG\nM=D\n"+
    "//restore LCL\n@4\nD=A\n@LCL\nA=M-D\nD=M\n@LCL\nM=D\n"+
    "@RFRNAD\n0;JMP\n");
    //constant code
    //codigo constante
    constantsC.add("\nt\n@SP\nA=M-1\nA=A-1\nM=1\n@SP\nM=M-1\n@SP\nA=M\nM=0\n/\nf\n@SP\nA=M-1\nA=A-1\nM=0\n@SP\nM=M-1\n@SP\nA=M\nM=0\n");
}
//------------------------------------------------------------------------
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
 * Replace a line use a HashMap with values and args.
 * @param line Line for proccess.
 * @param RelaseKeyValue The HashMap with values and keys.
 * @param nLine The number of Line.
 * @param lengthofCommand Length of command.
 * @param BoolCommand Flag for indicate if is a boolean command.
 * @param withArgsCommands Indicate if use command with arguments.
 * @param arg Argumento of the command.
 * @param ivalue value of argument.
 * @return The result of the replace or null if appears an error.
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
    .setFormatPatternMostLong("pushconstant-32768")
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
                if((f = n.compareCommandsWithArg(line, nLine, argsCommands, 0, null, lengthofCommand, lengthofarg)) < 0 ) return null;
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
}
}

