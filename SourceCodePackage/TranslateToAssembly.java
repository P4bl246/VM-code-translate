
import java.util.*;
import java.io.*;
import AuxClass.Parser.*;
public class TranslateToAssembly {
public int transalte(String file_in){
    int n;
    ArrayList<String>commands = new ArrayList<>();
    ArrayList<String>representationAssembly = new ArrayList<>();
    ArrayList<Boolean>state = new ArrayList<>();
    ArrayList<String> repeatCode = new ArrayList<>();
    CreatePredefindArrays(commands, representationAssembly, state, repeatCode);
    n= CreateHash(commands, representationAssembly, predet);
    if(n != 0) return -1;
    return 0;
}
private HashMap<String, String>predet = new HashMap<>();
//------------------------------------------------------------------------
public void CreatePredefindArrays(ArrayList<String>commands, ArrayList<String>representationAssembly, ArrayList<Boolean>state, ArrayList<String>constantsC){
    commands.add("add");
    representationAssembly.add("\n@SP\nA=M-1\nD=M\nA=A-1\nD=D+M\n@SP\nM=M+1\n0;JMP\n/\n");
    commands.add("sub");
    representationAssembly.add("\n@SP\nA=M-1\nD=M\nA=A-1\nD=D-M\n@SP\nM=M+1\n0;JMP\n/\n");
    commands.add("neg");
    representationAssembly.add("\n@SP\nA=M\nM=-M\n/");
    commands.add("lt");
    representationAssembly.add("\n@SP\nA=M-1\nD=M\nA=A-1\nD=D-M\nct\nD;JLT\ncf\nD;JGE\n/");
    commands.add("gt");
    representationAssembly.add("\n@SP\nA=M-1\nD=M\nA=A-1\nD=D-M\nct\nD;JGT\ncf\nD;JLE\n/");
    commands.add("eq");
    representationAssembly.add("\n@SP\nA=M-1\nA=A-1\nD=D-M\nct\nD;JEQ\ncf\n@false\n0;JMP\n/");
    commands.add("pop");
    representationAssembly.add("\nRPI\nD=A\nRARG\nD=A+D\n@SP\nA=M\nM=D\n@SP\nA=M\nM=D\n@SP\nA=M-1\nD=M\nM=0\n@SP\nA=M\nA=M\nM=D\n@SP\nM=M-1\n/\n");
    commands.add("push");
    representationAssembly.add("\nRPI\nD=A\nRPARG\nA=A+D\nD=M\nM=0\n@SP\nA=M\nM=D\n@SP\nM=M+1\n/");
    commands.add("and");
    representationAssembly.add("\n@SP\nA=M-1\nA=A-1\nD=M\n@SP\nA=M-1\nD=M-D\nct\nD;JEQ\ncf\nD;JGL\n/\n");
    commands.add("or");
    representationAssembly.add("\n@2\nD=A\n@SP\nA=M-1\nA=A-1\nD=D-M\n@SP\nA=M-1\nD=M-D\nD=D-1\nct\nD;JLE\ncf\nD;JGT\n/\n");
    commands.add("not");
    representationAssembly.add("\n@SP\nA=M-1\nM=-M\n/");
    
    //constant code
    //codigo constante
    constantsC.add("\nt\n@SP\nA=M\nM=1\n/\nf\n@SP\nA=M\nM=0\n/\n");
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
public String replace(String line, HashMap<String, String> RelaseKeyValue, String nLine, String NumberLinedelimieter){
        if(RelaseKeyValue == null){
            System.err.println("Error: Need put an argument in 'RealseKeyValue' parameter\n");
            return null;
        }
        if(line == null){
            System.err.println("Error: Need put an argument in a 'line' parameter\n");
            return null;
        }
        sintaxParsing n  = new sintaxParsing();
        HashMap<String, Integer> forUse = new HashMap<>();
        ArrayList<String>values = new ArrayList<>();
        Parser k = new Parser();
        Parser.MutableTypeData<Integer> lenghtofCommand = k.new MutableTypeData<>(0);
        for (Map.Entry<String, String> entry : RelaseKeyValue.entrySet()) {
            String key = entry.getKey();
            n.CreateHashTable(key, 0, null, forUse, null);  
            values.add(entry.getValue());
        }
        if(n.CompareWithHashTable(line, nLine, 4, forUse, null, true, lenghtofCommand) != 0) return null;
        String command = line.substring(line.indexOf(NumberLinedelimieter+NumberLinedelimieter.length()), lenghtofCommand.getValor());
        command = command.trim();//remove de spaces and tab //eliminar los espaicos y tabulaciones
        return RelaseKeyValue.get(command);
    }
}

