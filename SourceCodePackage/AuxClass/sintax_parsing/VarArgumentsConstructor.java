package AuxClass.sintax_parsing;
import java.util.ArrayList; 
import java.util.HashMap;

//this is a special class thinking for a function from the 'sintax_parsing' class, call 'CompareCommandsWithArgs'
//Esta es una clase especial pensada para una función de la clase 'sintax_parsing', llamada 'CompareCommandsWithArgs'
public class VarArgumentsConstructor{
    //format of the arguments
    //Formato de los argumentos
     public class CommandArgRule {
    public HashMap<String, Integer> commandTable;
    public HashMap<String, Integer> argTable;
    public int commandLength;
    public int argLength; //store the lenght of the argument more long
                          //almacena el tamaño de el argumento mas largo
    public String formatPattern;
    public ArrayList<String> MultipleFormatPattern = new ArrayList<String>();

     //constructor for rules for the format and order of the parameters
    //Construtor para las reglas de formato y orden de parametros
    public CommandArgRule(HashMap<String, Integer> commandTable, HashMap<String, Integer> argTable, int commandLength, int argLength, String formatPattern, ArrayList<String> MultipleFormatPattern2) {
        this.commandTable = commandTable;
        this.argTable = argTable;
        this.commandLength = commandLength;
        this.argLength = argLength;
        this.formatPattern = formatPattern;
        this.MultipleFormatPattern = MultipleFormatPattern2;
    }
}
}