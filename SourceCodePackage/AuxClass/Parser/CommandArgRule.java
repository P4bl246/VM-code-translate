package AuxClass.Parser;
import java.util.ArrayList; 
import java.util.HashMap;

//this is a special class thinking for a function from the 'sintax_parsing' class, call 'CompareCommandsWithArgs'
//Esta es una clase especial pensada para una función de la clase 'sintax_parsing', llamada 'CompareCommandsWithArgs'
    //format of the arguments
    //Formato de los argumentos
     public class CommandArgRule {
    public HashMap<String, Integer> commandTable;
    public HashMap<String, Integer> argTable;
    public int commandLength;
    public int argLength; //store the lenght of the argument more long
                          //almacena el tamaño de el argumento mas largo
    public String formatPatternMostLong;
    public String formatPatternLessLong;
    public arrayList<String, String> MultipleformatsPatterns = new ArrayList<String, Strings>();

     //constructor for rules for the format and order of the parameters
    //Construtor para las reglas de formato y orden de parametros
    public CommandArgRule(HashMap<String, Integer> commandTable, HashMap<String, Integer> argTable, int commandLength, int argLength, String formatPatternMostLong, String formatPatterLessLong, ArrayList<String, String> MultiplesFormatPatterns) {
        this.commandTable = commandTable;
        this.argTable = argTable;
        this.commandLength = commandLength;
        this.argLength = argLength;
        this.formatPatternMostLong = formatPatternMostLong;
        this.formatPatterLeesLong = formatPatterLessLong;
        this.MultiplesFormatsPatterns = MultiplesFormatPatterns;
    }
}
