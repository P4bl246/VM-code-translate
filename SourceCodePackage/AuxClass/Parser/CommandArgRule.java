package AuxClass.Parser;

import java.util.*;
/**
 * <p>Esta es una clase especial pensada para una función de la clase 'sintaxParsing', llamada 'CompareCommandsWithArgs', funciona como un encapsulador de argumentos</p>
 * <p>This class is thinked for the function of the class 'sintxParsing', called 'CompareCommandWithArgs', this function like a wrapper of arguments</p>
 *<b>This wrapper class are created for abstract the arguments used in the function are a lot,
 *and put thats in a individual form are so extended and reduces adaptability, maintainability, and customizability</b>
 */

public class CommandArgRule {
    private HashMap<String, Integer> commandTable = new HashMap<>();
    private HashMap<String, Integer> argTable = new HashMap<>();
    private int commandLength = 0;
    private int argLength = 0;

    // Formato más largo y más corto permitidos para las instrucciones con argumentos
    //Argumets format most Long and most less accepted for the instructions with arguments
    private String formatPatternMostLong = null;
    private String formatPatternLessLong = null;

    // Múltiples patrones permitidos
    // Multiples formats patterns accpeted
    private Map<String, String> multipleFormatsPatterns = new HashMap<>();
    //excpeciones(instrucciones que no son permitidas)
    //excepcions (instruction not accepted)
    private ArrayList<String> exceptions = new ArrayList<>();
    
    private ArrayList<String>commandsWithoutPatterns = new ArrayList<>();
    //for flexibles commands
    private ArrayList<String>commandsWithFlexiblePattern = new ArrayList<>();
    private String formatPatternFlexible = null;
    private ArrayList<String>multiplesFlexiblesFormatsPatterns = new ArrayList<>();
    private ArrayList<Character>specialCharsForIdentifyInTheFlexibleFormat = new ArrayList<>();
    private ArrayList<String> commandsWithFlexiblePatternForResultConflicts = new ArrayList<>();
    private boolean thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns = false;
    private Character ORgateForFlexible = null;
    private Character stopForFlexible = null;
    private Map<Character, Integer> mapForFlexible = new HashMap<>();
    private Character stopForFlexibleForConflicts = null;
    private boolean theLineAreInTheFormatExpected = false;
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
    private CommandArgRule(Builder builder) {
        this.commandTable = builder.commandTable;
        this.argTable = builder.argTable;
        this.commandLength = builder.commandLength;
        this.argLength = builder.argLength;
        this.formatPatternMostLong = builder.formatPatternMostLong;
        this.formatPatternLessLong = builder.formatPatternLessLong;
        this.multipleFormatsPatterns = builder.multipleFormatsPatterns;
        this.exceptions = builder.exceptions;
        this.commandsWithoutPatterns = builder.commandsWithoutPatterns;
        this.commandsWithFlexiblePattern = builder.commandsWithFlexiblePattern;
        this.formatPatternFlexible = builder.formatPatternFlexible;
        this.multiplesFlexiblesFormatsPatterns = builder.multiplesFlexiblesFormatsPatterns;
        this.specialCharsForIdentifyInTheFlexibleFormat = builder.specialCharsForIdentifyInTheFlexibleFormat;
        this.thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns = builder.thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns;
        this.commandsWithFlexiblePatternForResultConflicts = builder.commandsWithFlexiblePatternForResultConflicts;
        this.ORgateForFlexible = builder.ORgateForFlexible;
        this.stopForFlexible = builder.stopForFlexible;
        this.mapForFlexible = builder.mapForFlexible;
        this.stopForFlexibleForConflicts = builder.stopForFlexibleForConflicts;
        this.theLineAreInTheFormatExpected = builder.theLineAreInTheFormatExpected;
    }
public static class Builder{
    private HashMap<String, Integer> commandTable = new HashMap<>();
    private HashMap<String, Integer> argTable = new HashMap<>();
    private int commandLength = 0;
    private int argLength = 0;

    // Formato más largo y más corto permitidos para las instrucciones con argumentos
    //Argumets format most Long and most less accepted for the instructions with arguments
    private String formatPatternMostLong = null;
    private String formatPatternLessLong = null;

    // Múltiples patrones permitidos
    // Multiples formats patterns accpeted
    private Map<String, String> multipleFormatsPatterns = new HashMap<>();
    //excpeciones(instrucciones que no son permitidas)
    //excepcions (instruction not accepted)
    private ArrayList<String> exceptions = new ArrayList<>();
    
    private ArrayList<String>commandsWithoutPatterns = new ArrayList<>();
    //for flexibles commands
    private ArrayList<String>commandsWithFlexiblePattern = new ArrayList<>();
    private String formatPatternFlexible = null;
    private ArrayList<String>multiplesFlexiblesFormatsPatterns = new ArrayList<>();
    private ArrayList<Character>specialCharsForIdentifyInTheFlexibleFormat = new ArrayList<>();
    private ArrayList<String> commandsWithFlexiblePatternForResultConflicts = new ArrayList<>();
    private boolean thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns = false;
    private Character ORgateForFlexible = null;
    private Character stopForFlexible = null;
    private Map<Character, Integer> mapForFlexible = new HashMap<>();
    private Character stopForFlexibleForConflicts = null;
    private boolean theLineAreInTheFormatExpected = false;

    //setters for the builder

    public Builder setFormatPatternMostLong(String formatPatternMostLong){
                this.formatPatternMostLong = formatPatternMostLong;
                return this;
       }
    public Builder setFormatPatternLessLong(String formatPatternLessLong){
                this.formatPatternLessLong = formatPatternLessLong;
                return this;
       }
    public Builder setCommandTable(HashMap<String, Integer> commandTable){
                this.commandTable = commandTable;
                return this;
       }
    public Builder setArgTable(HashMap<String, Integer> argTable){
                this.argTable = argTable;
                return this;
       }
    public Builder setCommandLength(int commandLength){
                this.commandLength = commandLength;
                return this;
    }      
    public Builder setArgLength(int argLength){
                this.argLength = argLength;
                return this;   
    }
    public Builder setMultipleFormatsPatterns(Map<String, String> multipleFormatsPatterns){
                this.multipleFormatsPatterns = multipleFormatsPatterns;
                return this;
       }
    public Builder setExceptions(ArrayList<String> exceptions){
                this.exceptions = exceptions;
                return this;
       }
    public Builder setCommandsWithoutPatterns(ArrayList<String> commandsWithoutPatterns){
                this.commandsWithoutPatterns = commandsWithoutPatterns;
                return this;
       }

        public Builder setCommandsWithFlexiblePattern(ArrayList<String> commandsWithFlexiblePattern){
            this.commandsWithFlexiblePattern = commandsWithFlexiblePattern;
            return this;
        }
        public Builder setFormatPatternFlexible(String formatPatternFlexible){
            this.formatPatternFlexible = formatPatternFlexible;
            return this;
        }
        public Builder setMultiplesFlexiblesFormatsPatterns(ArrayList<String> multiplesFlexiblesFormatsPatterns){
            this.multiplesFlexiblesFormatsPatterns = multiplesFlexiblesFormatsPatterns;
            return this;
        }
        public Builder setSpecialCharsForIdentifyInTheFlexibleFormat(ArrayList<Character> specialCharsForIdentifyInTheFlexibleFormat){
            this.specialCharsForIdentifyInTheFlexibleFormat = specialCharsForIdentifyInTheFlexibleFormat;
            return this;
        }
        public Builder setCommandsWithFlexiblePatternForResultConflicts(ArrayList<String> commandsWithFlexiblePatternForResultConflicts){
            this.commandsWithFlexiblePatternForResultConflicts = commandsWithFlexiblePatternForResultConflicts;
            return this;
        }
        public Builder setThePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns(boolean thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns){
            this.thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns = thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns;
            return this;
        }
        public Builder setORgateForFlexible(Character ORgateForFlexible){
            this.ORgateForFlexible = ORgateForFlexible;
            return this;
        }
        public Builder setStopForFlexible(Character stopForFlexible){
            this.stopForFlexible = stopForFlexible;
            return this;
        }
        public Builder setMapForFlexible(Map<Character, Integer> mapForFlexible){
            this.mapForFlexible = mapForFlexible;
            return this;
        }
        public Builder setStopForFlexibleForConflicts(Character stopForFlexibleForConflicts){
            this.stopForFlexibleForConflicts = stopForFlexibleForConflicts;
            return this;
        }
        public Builder setTheLineAreInTheFormatExpected(boolean theLineAreInTheFormatExpected){
            this.theLineAreInTheFormatExpected = theLineAreInTheFormatExpected;
            return this;
       }  
    public CommandArgRule build(){
        return new CommandArgRule(this);
      }
    }
    //getters for the class
    public HashMap<String, Integer> getCommandTable() {
        return commandTable;
    }
    public HashMap<String, Integer> getArgTable() {
        return argTable;
    }
    public int getCommandLength() {
        return commandLength;
    }
    public int getArgLength() {
        return argLength;
    }
    public String getFormatPatternMostLong() {
        return formatPatternMostLong;
    }
    public String getFormatPatternLessLong() {
        return formatPatternLessLong;
    }
    public Map<String, String> getMultipleFormatsPatterns() {
        return multipleFormatsPatterns;
    }
    public ArrayList<String> getExceptions() {
        return exceptions;
    }
    public ArrayList<String> getCommandsWithoutPatterns() {
        return commandsWithoutPatterns;
    }
    public ArrayList<String> getCommandsWithFlexiblePattern() {
        return commandsWithFlexiblePattern;
    }
    public String getFormatPatternFlexible() {
        return formatPatternFlexible;
    }
    public ArrayList<String> getMultiplesFlexiblesFormatsPatterns() {
        return multiplesFlexiblesFormatsPatterns;
    }
    public ArrayList<Character> getSpecialCharsForIdentifyInTheFlexibleFormat() {
        return specialCharsForIdentifyInTheFlexibleFormat;
    }
    public ArrayList<String> getCommandsWithFlexiblePatternForResultConflicts() {
        return commandsWithFlexiblePatternForResultConflicts;
    }
    public boolean getThePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns() {
        return thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns;
    }
    public Character getORgateForFlexible() {
        return ORgateForFlexible;
    }
    public Character getStopForFlexible() {
        return stopForFlexible;
    }
    public Map<Character, Integer> getMapForFlexible() {
        return mapForFlexible;
    }
    public Character getStopForFlexibleForConflicts() {
        return stopForFlexibleForConflicts;
    }
    public boolean getTheLineAreInTheFormatExpected() {
        return theLineAreInTheFormatExpected;
    }  
}
