
import java.util.*;
import java.io.*;
import AuxClass.Parser.*;
public class TranslateToAssembly {
public int transalte(String file_in){
    System.out.println("\nGENERATING ASSEMBLY FILE...\n");
    Parser function = new Parser();
    function.RemoveVoidChars(file_in, null);
    int n;
    ArrayList<String>commands = new ArrayList<>();
    ArrayList<String>representationAssembly = new ArrayList<>();
    ArrayList<Boolean>state = new ArrayList<>();
    ArrayList<String> repeatCode = new ArrayList<>();
    CreatePredefindArrays(commands, representationAssembly, state, repeatCode);
    n= CreateHash(commands, representationAssembly, predet);
    if(n != 0) return -1;
    try(BufferedReader file = new BufferedReader( new FileReader(file_in));
    BufferedWriter writteFile = new BufferedWriter(new FileWriter("Assebmly.asm"))){
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
       String nameOfFile = file_in.substring(0, file_in.indexOf('.'));
       segments.put("static", "@"+nameOfFile+"."+staticlabel.getValor());
       segments.put("this", "@THIS");
       segments.put("that", "@THAT");
       segments.put("pointer0", "@THIS");
       segments.put("pointer1", "@THAT");
       segments.put("temp", "@temp");
       ArrayList<String>excep = new ArrayList<>();
       excep.add("pointer");
       int i = 0, c = 0;
      while((line = file.readLine()) != null){
      flag.setValor(false);
       String  nLine = null;
       String  tr = "(true" +i+")";
       String fs = "(false"+i+")";
       String continueH = "(Continue"+c+")";
       String continueHCall = "@Continue"+c+"\n0;JMP";
       String trCall = "@true"+i;
       String fsCall = "@false"+i;
        String assembly = replace(line, predet, nLine, lengthCommand, arg, isBoolCommand, true, lengthArg, valueArg, isArgCommand, excep, flag);
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
           line.substring(0, lengthCommand.getValor()).equals("if-goto")) assembly = assembly.replaceFirst("LBL", arg.getValor());
           else{
          assembly = assembly.replaceFirst("RPI", "@"+valueArg.getValor());
          assembly = assembly.replaceFirst("RARG", segments.get(arg.getValor()));
          if(arg.getValor() == "static") staticlabel.setValor(staticlabel.getValor()+1);//incremet the static label value for generete a new label
         }
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
    representationAssembly.add("\n//neg command\n@SP\nA=M\nM=-M\n/");
    commands.add("lt");
    representationAssembly.add("\n//lt command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M-D\nct\nD;JLT\ncf\nD;JGE\n");
    commands.add("gt");
    representationAssembly.add("\n//gt command\n@SP\nA=M-1\nD=M\nA=A-1\nD=M-D\nct\nD;JGT\ncf\nD;JLE\n");
    commands.add("eq");
    representationAssembly.add("\n//eq command\n@SP\nA=M-1\nA=A-1\nD=M-D\nct\nD;JEQ\ncf\nD;JLG\n");
    commands.add("pop");
    representationAssembly.add("\n//pop command\nRPI\nD=A\nRARG\nD=A+D\n@SP\nA=M\nM=D\n@SP\nA=M\nM=D\n@SP\nA=M-1\nD=M\nM=0\n@SP\nA=M\nM=D\n@SP\nM=M-1\n");
    commands.add("push");
    representationAssembly.add("\n//push command\nRPI\nD=A\nRARG\nA=A+D\nD=M\nM=0\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    commands.add("and");
    representationAssembly.add("\n//and command\n@SP\nA=M-1\nA=A-1\nD=M\n@SP\nA=M-1\nD=M-D\nct\nD;JEQ\ncf\nD;JGL\n");
    commands.add("or");
    representationAssembly.add("\n//or command\n@2\nD=A\n@SP\nA=M-1\nA=A-1\nD=D-M\n@SP\nA=M-1\nD=M-D\nD=D-1\nct\nD;JLE\ncf\nD;JGT\n");
    commands.add("not");
    representationAssembly.add("\n//not command\n@SP\nA=M-1\nM=-M\n");
    commands.add("label");
    representationAssembly.add("\n//label command\n(LBL)\n");
    commands.add("goto");
    representationAssembly.add("\n//goto command\n@LBL\n0;JMP\n");
    commands.add("if-goto");
    representationAssembly.add("\n//if-goto command\n@SP\nA=M-1\nD=M\n@LBL\nD;JGT\n");
    
    //constant code
    //codigo constante
    constantsC.add("\nt\n@SP\nA=M-1\nA=A-1\nM=1\n@SP\nM=M-1\n@SP\nA=M\nM=0\n/\nf\n@SP\nA=M\nM=0\n@SP\nM=M-1\n@SP\nA=M\nM=0\n");
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
        n.HashTablePreDet();
        boolean withoutPattern = false;
        if(argCommand != null) argCommand.setValor(false);
        if(n.CompareWithHashTable(line, nLine, 4, null, sintaxParsing.TableHash.Booleans, false, lengthofCommand) != 0){
            if(BoolCommand != null)BoolCommand.setValor(false);
            //if has been commands with args
            //si tiene comandos con argumetos
            if(n.CompareWithHashTable(line, nLine, 4, null, sintaxParsing.TableHash.Arithmetics, false, lengthofCommand) != 0 && withArgsCommands){
                ArrayList<String>commandswithoutFormat = new ArrayList<>();
                commandswithoutFormat.add("label");
                commandswithoutFormat.add("goto");
                commandswithoutFormat.add("if-goto");
                   int f = 0;
                CommandArgRule argsCommands = new CommandArgRule(n.hashTablePOP_PUSH, n.argsTable, 7, 8, "pushconstant-32768", "popthis0", null, null, commandswithoutFormat);
                if((f = n.CompareCommandsWithArg(line, nLine, argsCommands, 0, null, lengthofCommand, lengthofarg)) != 0 && f != 2) return null;
                else if(argCommand != null)argCommand.setValor(true);
                if(f == 2) withoutPattern = true;
                
            }
            else if(n.CompareWithHashTable(line, nLine, 4, null, sintaxParsing.TableHash.Arithmetics, false, lengthofCommand) != 0 && !withArgsCommands) return null;
        }
        else if(n.CompareWithHashTable(line, nLine, 4, null, sintaxParsing.TableHash.Booleans, true, lengthofCommand) == 0 && BoolCommand != null) BoolCommand.setValor(true);
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
                arg.setValor(command.substring(lengthofCommand.getValor(), lengthofarg.getValor()+lengthofCommand.getValor()));
                String h = command.substring(lengthofarg.getValor()+lengthofCommand.getValor(), command.length());
                 h =h.trim();
                ivalue.setValor(h);
                }
            }
        }
        command = line.substring(0, lengthofCommand.getValor());
        if(excpetionsArgs != null && excpetionsArgs.contains(arg.getValor())) exceptionFlag.setValor(true);
        return RelaseKeyValue.get(command);
}
}

