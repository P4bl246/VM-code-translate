package AuxClass.Parser;

import java.util.*;
/**
 * <p>Esta es una clase especial pensada para una función de la clase 'sintaxParsing', llamada 'CompareCommandsWithArgs', funciona como un encapsulador de argumentos</p>
 * <p>This class is thinked for the function of the class 'sintxParsing', called 'CompareCommandWithArgs', this function like a wrapper of arguments</p>
 *<b>This wrapper class are created for abstract the arguments used in the function are a lot,
 *and put thats in a individual form are so extended and reduces adaptability, maintainability, and customizability</b>
 */

public class CommandArgRule {
    public HashMap<String, Integer> commandTable;
    public HashMap<String, Integer> argTable;
    public int commandLength;
    public int argLength;

    // Formato más largo y más corto permitidos para las instrucciones con argumentos
    //Argumets format most Long and most less accepted for the instructions with arguments
    public String formatPatternMostLong;
    public String formatPatternLessLong;

    // Múltiples patrones permitidos
    // Multiples formats patterns accpeted
    public Map<String, String> multipleFormatsPatterns = new HashMap<>();
    //excpeciones(instrucciones que no son permitidas)
    //excepcions (instruction not accepted)
    public ArrayList<String> exceptions = new ArrayList<>();
    
    public ArrayList<String>commandsWithoutPatterns = new ArrayList<>();
    //for flexibles commands
    public ArrayList<String>commandsWithFlexiblePattern = new ArrayList<>();
    public String formatPatternFlexible;
    public ArrayList<String>multiplesFlexiblesFormatsPatterns = new ArrayList<>();
    public ArrayList<Character>specialCharsForIdentifyInTheFlexibleFormat = new ArrayList<>();
    public ArrayList<String> commandsWithFlexiblePatternForResultConflicts;
    public boolean thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns;
    public Character ORgateForFlexible;
    public Character stopForFlexible;
    public Map<Character, Integer> mapForFlexible = new HashMap<>();
    public Character stopForFlexibleForConflicts;
    //Constructor
    /**
     * <p>This is a **constructor** for this class</p>
     * 
     * <p><b>IMPORTANT:</b> I put this types for the parameters because needly and think use this in other similars projects, but if you don't needly this,you can custom the implementation(of the function like use this class) and this class</p>
     * 
     * @param commandTable This is a HashTable (type <String, Integer>), use for 'search the accepted commands'
     * @param argTable This is a HashTable (type <String, Integer>), use for 'search the accepted arguments'
     * @param commandLength This is a integer, use for have reference of what is the 'max length for the command part'
     * @param argLength This is a integer, use for have referece of what is the 'max length for a argument part'
     *
     * This are a set of parameters("formatPatternMostLong" and "formatPatternLessLong"), need put both or none
     * @param formatPatternMostLong This is a String, use for know what is the pattern what have most numbers of characters for an instruction (use this when the instructions have just a single format). That are need because the format is pass to a number for the function 'IdentifyTheFormat'(That are in class 'sintaxParsing')
     * @param formatPatterLessLong This is a String, use for know what is the patter what have less numbers of characters for an instruction (use this when the instructions have just a single format). That are need because the format is pass to a number for the funciont 'IdentifyTheFormat'(That are in class 'sintaxParsing')
     *
     * @param multipleFormatsPatterns This is a HashMap(type <String, String>, when the first String(his "key") is use for put 'formatPatterMostLong', and the second(his "value") put the 'formatPatterLessLong') of Strings, use when the instructions has multiples formats, this is equal to the above set of parameters, just change like in this you can put multiple formats or multiples 'Patterns of formats'
     * @param exceptions This is a Array of Strings, use for put 'excpetions', this can be used for "example" when the instruction can be writter but are ilegal or useless or problematical and you don't want this type of instructions in the code
     * <p>Use example:</p>
     * <pre>
     * <code>
     * Map<String, Integer>acceptedCommands = new Map<>();
     * acceptedCommands.put("HI", 1);
     * acceptedCommand.put("BYE", 2);
     * Map<String, Integer>acceptedArgs = new Map<>();
     * acceptedArgs.put("MARIO", 3);
     * accptedArgs.put("LUIGI12", 4);
     * ArrayList<String>excep = new ArrayList<>();
     * excep.add("BYEMARIO"); //that is possible but I don't want accepted this, want defined like a "ilegal" instruction
     * //Now create the objetc with this class
     * CommandArgRule rules = new CommandArgRule(acceptedCommands, acceptedArgs, 3, 7, "BYELUIGI12", "HIMARIO", null, excep);
     *
     * //if you use multiple formats can make something like this. And if you implemtation accpeted both are fine(in my case the function what use this class dont support both "modes"(multples and singles formats))
     * Map<String, String> patterns = new Map<>();
     * patterns.("BYELUIGI12HIMARIO", "BYELUIGIMARIO");
     * patterns.("HIMARIOHILUIGI", "HILUIGI"); //and others if you want
     * //so now just need put in the constructor in the space for 'multiplesFormatsPatterns' "patterns", and thats it
     * CommandArgrule rules = new CommandArgRule(...,"BYELUIGI12", "HIMARIO", patterns,...);
     * //OR
     * CommandArgRule rules = new CokmmandArgRule(..., null, null, patterns,...);
     * //AND OTHERS...
     * </code>
     * </pre>
     */
    public CommandArgRule(HashMap<String, Integer> commandTable, HashMap<String, Integer> argTable, int commandLength, int argLength, String formatPatternMostLong, String formatPatternLessLong, Map<String, String> multipleFormatsPatterns, ArrayList<String> exceptions, ArrayList<String>commandsWithoutPatterns,
    ArrayList<String> commandsWithFlexiblePattern, String formatPatternFlexible, ArrayList<String> multiplesFlexiblesFormatsPatterns,
    ArrayList<Character> specialCharsForIdentifyInTheFlexibleFormat, boolean thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns,
    ArrayList<String>commandsWithFlexiblePatternForResultConflicts, Character ORgateForFlexible, Character stopForFlexible,
    Map<Character, Integer> mapForFlexible, Character stopForFlexibleForConflicts) {
        this.commandTable = commandTable;
        this.argTable = argTable;
        this.commandLength = commandLength;
        this.argLength = argLength;
        this.formatPatternMostLong = formatPatternMostLong;
        this.formatPatternLessLong = formatPatternLessLong;
        this.multipleFormatsPatterns = multipleFormatsPatterns;
        this.exceptions = exceptions;
        this.commandsWithoutPatterns = commandsWithoutPatterns;
        this.commandsWithFlexiblePattern = commandsWithFlexiblePattern;
        this.formatPatternFlexible = formatPatternFlexible;
        this.multiplesFlexiblesFormatsPatterns = multiplesFlexiblesFormatsPatterns;
        this.specialCharsForIdentifyInTheFlexibleFormat = specialCharsForIdentifyInTheFlexibleFormat;
        this.thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns = thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns;
        this.commandsWithFlexiblePatternForResultConflicts = commandsWithFlexiblePatternForResultConflicts;
        this.ORgateForFlexible = ORgateForFlexible;
        this.stopForFlexible = stopForFlexible;
        this.mapForFlexible = mapForFlexible;
        this.stopForFlexibleForConflicts = stopForFlexibleForConflicts;
    }
}
