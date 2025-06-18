
import AuxClass.Parser.*; // Import the Parser class from the AuxCLass.Parser package
import AuxClass.Parser.Parser.MutableTypeData;

import java.util.ArrayList; // Import the ArrayList class from the java.util package
                           // Importar la clase ArrayList del paquete java.util
import java.util.HashMap;
import java.util.Map;
import java.io.*;

/**
 * @author Pablo Riveros Perea
 * <p>This class are created for the sintax-parsing proccess, and the unique function acoplated with the project is 'parser_Sintaxis'
 * <p><b>Functions and global objects inside the class</b>
 * <ul>
 *  <li><code>parser_Sintaxis</code>
 *  <li><code>hashTablePreDet</code>
 *  <li><code>createHashTable</code>
 *  <li><code>getNChars</code>
 *  <li><code>compareWithHashTable</code>
 *  <li><code>compareTableImplement</code>
 *  <li><code>compareCommandsWithArg</code>
 *  <li><code>orchestratorForFlexibleFormat</code>
 *  <li><code>orchestratorForStrictFormat</code>
 *  <li><code>checkStrictFormat</code>
 *  <li><code>checkFlexibleFormat</code>
 *  <li><code>flexiblePatternMatchWithOR</code><b>private</b>
 *  <li><code>checkExcep</code>
 *  <li><code>resolveConflicts</code>
 *  <li><code>identifyTheFlexibleFormat</code>
 *  <li><code>identifyTheStrictFormat</code>
 *  <li><code>createArrayForORLetters</code>
 *  <li><code>identifyTypeIntOrChar</code>
 *  <li><code>log2</code>
 * </lu>
 * <p><b>Objects:</b></p>
 * <ul>
 * <li>argsTable
 * <li>hashTableArith
 * <li>hashTableBool
 * <li>hashTablePOP_PUSH
 * </ul>
 */
public class sintaxParsing {
// This class is used to parse the file and check the syntax
// Esta clase se utiliza para analizar el archivo y verificar la sintaxis
/* 
//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------

public void HashTablePreDet();// This method is used to create a hash table with the pre-determined elements
                                // Este método se utiliza para crear una tabla hash con los elementos predefinidos

//-----------------------------------****************--------------------------******   
//-------------------------------------------------------------
    public int CompareTableImplement(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, boolean withArguments);//Implmentación for the next function(just commands don't recive arguments)
                                                                                                                                                     //Implementación para la siguiente función(Solo para comandos que no reciben argumentos)

    public int CompareWithHashTable(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, TableHash CompareWithPreDefined, boolean witArgs);
    //This method is used to compare the Command with the command table(Hash table), integrated the function CompareTableImplement                                                                                                                                                                               
    //Este método se utiliza para comparar el comando con la tabla de comandos (tabla hash), integra la función CompareTableImplement
//-------------------------------------------------------------           
    public int identifyTypeIntOrChar(char actual, int SensibleToMayus); //This method is used to find and identify the character and return his value like an integer
                                                                        //Ese método se utiliza para encontrar y indentificar el carácter y retornar su valor como entero

    public int identifyTheFormat(String FormatExample, int SensibletoMayus);//This method is used to get and identify the pattern of a format in a line, use the above function
                                                                            //Este método se utiliza para obtener y identificar el patrón de un formato en una linea, utiliza la función anterior    

    public int CompareCommandsWithArg(String line, String nLine, CommandArgRule ArgsInputRules, int SensibleToMayus, ArrayList<Character> Delimiters); //This method is used to compare commands with arguments like POP and PUSH in this VM code(integrated the above functions)
                                                                                                                                                       //Este método se utiliza para comparar comandos con argumentos como POP y PUSH en este codigo VM(integra las funciones anteriores)
//-------------------------------------------------------------
    public void CreateHashTable(String element, int SimpleOrMultiples, ArrayList<String> NewElements, HashMap<String, Integer> hashTable,int AddToPreDefined);// This method is used to create a hash table
//-----------------------------------****************--------------------------******                                                                           // Este método se utiliza para crear una tabla hash

    public String GetNchars(String input, int n);// This method is used to get the first n characters of the input string
                                                // Este método se utiliza para obtener los primeros n caracteres de la cadena de entrada

    public int parser_Sintaxis(String File_in);// This method is used to parse the syntax of the file
                                                // Este método se utiliza para analizar la sintaxis del archivo

//END OF FUNCTIONS FOR PARSING SINTAX (FIN DE LAS FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)--------------------------------------------------------------
*/
//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------

/**
 * Parses and validates the syntax of a VM code file according to the rules and command patterns defined for this translator.
 * <p>
 * This method is the main entry point for the syntax analysis process in this project. It is tightly coupled to the internal logic, conventions, and data structures of this specific parser and VM code translator.
 * <br>
 * The function reads the input file line by line, checks each line against predefined hash tables for arithmetic, boolean, POP/PUSH commands, and argument types, and applies flexible and strict pattern validation as required.
 * <br>
 * It also manages special cases such as illegal instructions, commands without arguments, commands with flexible patterns, and function declarations, ensuring that all syntax rules specific to this project are enforced.
 * <br>
 * If any syntax error or exception is found, it prints a detailed error message and returns -1. If the parsing completes successfully, it returns 0.
 *
 * <b>Parameters:</b>
 * @param File_in The path to the input file to be parsed and validated.
 * @return 0 if the syntax parsing completes successfully; -1 if any error occurs.
 *
 * <b>Note:</b> This function is highly project-specific and was designed for use in this VM code parser. It is not intended for general-purpose syntax parsing.
 */
public int parser_Sintaxis(String File_in) {
    System.out.println("\nSTARTS THE SINTAX PARSING PROCESS\n");
    int n;
    Parser parserf = new Parser();
    try{parserf.numLines(File_in);}
    catch (ParsingException e) {
        System.out.println("Error reading file: " + File_in);
        System.out.println("Error details: " + e.getMessage());
        return -1;
    }
    try(BufferedReader readFilein = new BufferedReader(new FileReader(File_in))) {
    
    String line;
    String nLine;
    //Starts the sintx parsing
    //Empieza el analisisi sintactico
    ArrayList<String>excep = new ArrayList<>();
    excep.add("popconstant");
    ArrayList<String>withouthPattern = new ArrayList<>();
    withouthPattern.add("label");
    withouthPattern.add("goto");
    withouthPattern.add("if-goto");
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
    .setCommandTable(hashTablePOP_PUSH)
    .setArgTable(argsTable)
    .setCommandLength(8)
    .setArgLength(8)
    .setFormatPatternMostLong("pushconstant32768")
    .setFormatPatternLessLong("popthis0")
    .setExceptions(excep)
    .setMultipleFormatsPatterns(null)
    .setCommandsWithoutPatterns(withouthPattern)
    .setCommandsWithFlexiblePattern(withoutPatternButWithivalue)
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

    hashTablePreDet(); // Create the hash table with the pre-determined elements
                       // Crear la tabla hash con los elementos predefinidos
    HashMap<String, Integer> others = new HashMap<>();
    createHashTable("return", 0, null, others, null);
    HashMap<String, Integer> memoryOfFunctions = new HashMap<>();
    HashMap<String, Integer> memoryOfLabels = new HashMap<>();
    String functionActual = "";
    while(true) {
        nLine = parserf.get(readFilein, Parser.Readmode.NumberLine, ' ', null, null);
        if(nLine.equals(""))break;
        Parser.MutableTypeData<Boolean> contains = parserf.new MutableTypeData<>(false);
        line = parserf.get(readFilein, Parser.Readmode.CompletelyLine, '0', null, contains);
        if(line.equals("") && !contains.getValor()) break;
        
        Parser.MutableTypeData<Integer> i =  parserf.new MutableTypeData<>(0);
        if(line.length() >= 6){
            n = compareWithHashTable(line, nLine, 6, others, null, false, i);
            if(n == 0) continue;
        }
        n = compareWithHashTable(line, nLine, 3, null, TableHash.Arithmetics, false, i);
        if (n != 0){
          n= compareWithHashTable(line, nLine, 3, null, TableHash.Booleans, false, i);
          
          if (n != 0) {
            //Create a mutable value variable for upload his value in call in diferents functions
            //Crear una vairalbe de valor mutable para actualizar su valor en las llamadas a otras funciones 
            Parser.MutableTypeData<Integer>LengthOfCommand = parserf.new MutableTypeData<>(0);
            Parser.MutableTypeData<Integer>LengthOfArg = parserf.new MutableTypeData<>(0);
          n = compareCommandsWithArg(line, nLine, argsCommands, 1, LengthOfCommand, LengthOfArg);
          if (n < 0){
            System.err.printf("Error in the line %s\nDETAILS: Wrong Sintaxis\n", nLine);
             return -1;
            }
            String arg = line.substring(LengthOfCommand.getValor(), (LengthOfArg.getValor()+LengthOfCommand.getValor()));

             if(n != 2 && n!=3){
            if(arg.equals("pointer")){
             arg = line.substring(((LengthOfArg).getValor()+LengthOfCommand.getValor()), line.length());
             if(!(arg.equals("0") || arg.equals("1"))){
                System.err.println("Error in the line "+nLine+" the 'pointer' argument can has just 2 values '0'(THIS) or '1'(THAT)\n");
                return -1;
               }
             
            }
            else{
            arg = line.substring(((LengthOfArg).getValor()+LengthOfCommand.getValor()), line.length());
             try{
                if(Integer.parseInt(arg) > 32767 || Integer.parseInt(arg) < 0){
                System.err.printf("Error in the line %s\nDETAILS: The value of arg must be a value 0-32767\n", nLine);
                return -1;
             }
             }catch(NumberFormatException e){
              System.err.printf("Error in the line %s\nDETAILS: The value of arg (%s) can't be convert to integer, because contains some character can't be convert to integer\nException: %s", nLine, arg, e.getMessage());
              return -1;
             }
            }
            if(arg.equals("static")){
            arg = line.substring(((LengthOfArg).getValor()+LengthOfCommand.getValor()), line.length());
            if(Integer.parseInt(arg) > 239){
                System.err.printf("Error in the line %s\nDETAILS: The value of a 'static' argument can't be greather than 239\n");
                return -1;
             }
            }
            if(arg.equals("temp")){
              arg = line.substring(((LengthOfArg).getValor()+LengthOfCommand.getValor()), line.length());
            if(Integer.parseInt(arg) > 10){
                System.err.printf("Error in the line %s\nDETAILS: The value of a 'temp' argument can't be greather than 10\n");
                return -1;
             }  
            }
          }
          if(n == 2){
            String valueArg = line.substring(LengthOfCommand.getValor(), line.length());
            if(valueArg.charAt(0)>= '0' && valueArg.charAt(0)<='9'){
                System.err.printf("Error in the line %s\nDETAILS: The value for commands 'label', 'if-goto' or 'goto' can't starts with a digit\n", nLine);
                return -1;
                }   
            if(line.substring(0, LengthOfCommand.getValor()).equals("label")){
                if(!memoryOfLabels.containsKey(functionActual+"$"+valueArg)) memoryOfLabels.put(functionActual+"$"+valueArg, 0);
                else{
                    System.err.printf("Error in the line %s\nDETAILS: The label '%s' are definded before in the actual function %s", nLine, valueArg, functionActual);
                    return -1;
                }
            }
            else{
                if(!memoryOfLabels.containsKey(functionActual+"$"+valueArg)){
                    System.err.printf("Error in the line %s\nDETAILS: The label for the command 'goto' or 'if-goto' not exit in this function (%s)", nLine, functionActual);
                    return -1;
                }

            }
          }
          if(n == 3){
            // If the command is a flexible pattern, check if it has arguments
            // Si el comando es un patrón flexible, verificar si tiene argumentos
            if(LengthOfArg.getValor() == 0){
                System.err.printf("Error in the line %s\nDETAILS: The command '%s' must have arguments\n", nLine, line.substring(0, LengthOfCommand.getValor()));
                return -1;
            }
            StringBuilder functionName = new StringBuilder(line.substring(LengthOfCommand.getValor(), line.length()));
             functionName.delete(LengthOfArg.getValor()+1, line.length());
            if(functionName.toString().charAt(0)>= '0' && functionName.toString().charAt(0)<='9'){
                System.err.printf("Error in the line %s\nDETAILS: The argument for commands 'call' or 'function' can't starts with a digit\n", nLine);
                return -1;
                }
            String valueOfArg = line.substring(line.indexOf("~")+1, line.length());
             try{
                if(Integer.parseInt(valueOfArg) > 32767 || Integer.parseInt(valueOfArg) < 0){
                System.err.printf("Error in the line %s\nDETAILS: The value of arg must be a value 0-32767\n", nLine);
                return -1;
             }
             }catch(NumberFormatException e){
              System.err.printf("Error in the line %s\nDETAILS: The value of arg (%s) can't be convert to integer, because contains some character can't be convert to integer\nException: %s", nLine, valueOfArg, e.getMessage());
              return -1;
             }
            if(line.substring(0, LengthOfCommand.getValor()).equals("function")){ 
                functionActual = functionName.toString();
                if(!memoryOfFunctions.containsKey(functionName.toString())) memoryOfFunctions.put(functionName.toString(), 0);//add to functions creates
                else{
                    System.out.printf("Error in the line %s\nDETAILS: The function '%s' are created before\n", nLine, functionName.toString());
                    return -1;
                }
            }
          } 
            continue;
          }
            continue;
          }
          continue;
     }
    }
    catch (FileNotFoundException e) {
        System.out.println("File not found: " + File_in);
        return -1;
    } catch (IOException e) {
        System.out.println("Error reading file: " + File_in);
        return -1;
    } catch(ParsingException e){
        System.out.println("Parsing error in file: " + File_in);
        System.out.println("Error details: " + e.getMessage());
        return -1;
    }
    
    try{parserf.removeNLine(File_in, ' ');}
    catch (ParsingException e){
        System.out.println("Error removing line numbers from file: " + File_in);
        System.out.println("Error details: " + e.getMessage());
        return -1;
    }
    System.out.println("\nSINTAX PARSING COMPLETELY WITHOUT ERRORS\n");
    return 0;
}
//------------------------------------------------------
public HashMap<String, Integer>argsTable = new HashMap<>();

public HashMap<String, Integer> hashTableArith = new HashMap<>(); // Create a hash table for arithmetic operations
                                                                  // Crear una tabla hash para operaciones aritméticas

public HashMap<String, Integer> hashTableBool = new HashMap<>(); // Create a hash table for boolean operations
                                                                      //Crear una tabla hash para operaciones booleanas

public HashMap<String, Integer> hashTablePOP_PUSH = new HashMap<>(); // Create a hash table for functions
                                                                    // Crear una tabla hash para funciones
                
//constructor function for create the PreDeterminate hash tables
// función constructora para crear las tablas hash predefinidas
/**
 * Initializes the predefined hash tables for arithmetic, boolean, POP/PUSH commands, and argument types.
 * <p>
 * This method fills the internal hash tables with the standard VM commands and argument types used by the translator.
 * It adds arithmetic operations ("add", "sub", "neg"), boolean operations ("or", "and", "not", "eq", "lt", "gt"),
 * POP/PUSH commands ("pop", "push", "label", "goto", "if-goto", "function", "call"), and argument types
 * ("constant", "local", "argument", "static", "this", "that", "temp", "pointer") to their respective tables.
 * <br>
 * Throws a {@code ParsingException} if any error occurs during the creation of the hash tables.
 *
 * @throws ParsingException If an error occurs while adding elements to any of the hash tables.
 */
  public void hashTablePreDet() throws ParsingException{
    ArrayList<String> NewElements = new ArrayList<>();
    //Arithmetic operations
    // Operaciones aritméticas
    NewElements.add("add");
    NewElements.add("sub"); 
    NewElements.add("neg");
    try{createHashTable(null, 1, NewElements, null, TableHash.Arithmetics);}catch(ParsingException e){throw new ParsingException(e.getMessage());}
    NewElements.clear();
    //Boolean operations
    // Operaciones booleanas
    NewElements.add("or");
    NewElements.add("and");
    NewElements.add("not");
    NewElements.add("eq");
    NewElements.add("lt");
    NewElements.add("gt");
    try{createHashTable(null, 1, NewElements, null, TableHash.Booleans);}catch(ParsingException e){throw new ParsingException(e.getMessage());}
    NewElements.clear();
    //POP and PUSH comands
    // comandos POP y PUSH
    NewElements.add("pop");
    NewElements.add("push");
    NewElements.add("label");
    NewElements.add("goto");
    NewElements.add("if-goto");
    NewElements.add("function");
    NewElements.add("call");
    try{createHashTable(null, 1, NewElements, null, TableHash.POP_PUSH);}catch(ParsingException e){throw new ParsingException(e.getMessage());}
    //Create the table for arguments for this vm translator
    //Crear la tabla de argumentos para esta traductor vm
    ArrayList<String> args = new ArrayList<>();
    args.add("constant");
    args.add("local");
    args.add("argument");
    args.add("static");
    args.add("this");
    args.add("that");
    args.add("temp");
    args.add("pointer");
    try{createHashTable(null, 1, args, argsTable, null);}catch(ParsingException e){throw new ParsingException(e.getMessage());}
  }
//------------------------------------------------------
// This method is used to create a hash table with the pre-determined elements
// Este método se utiliza para crear una tabla hash con los elementos predefinidos
public enum TableHash{
    Arithmetics,
    Booleans,
    POP_PUSH
}
/**
 * Creates a hash table for commands or arguments, supporting both single and multiple elements.
 * <p>
 * This method calculates a simple hash for each element (sum of character codes) and adds it to the specified hash table or to one of the predefined tables (arithmetic, boolean, or POP_PUSH).
 * <br>
 * If {@code SimpleOrMultiples} is not 0, it expects a list of elements in {@code NewElements} and adds each to the hash table(s).
 * If {@code SimpleOrMultiples} is 0, it expects a single element in {@code element} and adds it to the hash table(s).
 * <br>
 * Throws a {@code ParsingException} if required parameters are missing or invalid.
 *
 * <b>Parameters:</b>
 * @param element The single element to add (used if {@code SimpleOrMultiples} is 0).
 * @param SimpleOrMultiples If 0, adds a single element; otherwise, adds multiple elements from {@code NewElements}.
 * @param NewElements List of elements to add (used if {@code SimpleOrMultiples} is not 0).
 * @param hashTable The hash table to which elements will be added (can be null if using a predefined table).
 * @param AddToPreDefined The predefined table to add elements to (can be null if using a custom hash table).
 * @throws ParsingException If required parameters are null or invalid.
 */
public void createHashTable(String element, int SimpleOrMultiples, ArrayList<String> NewElements, HashMap<String, Integer> hashTable, TableHash AddToPreDefined) throws ParsingException {
    if(hashTable == null && AddToPreDefined == null) {
        throw new ParsingException("Error: Hash table is null and AddToPreDefined is null");
    }
    else if(element == null && NewElements == null) {
        throw new ParsingException("Error: Element and NewElements are null");
    }
    int hash;
    // If SimpleOrMultiples is not 0, create a hash table for multiple elements
    // Si SimpleOrMultiples no es 0, crea una tabla hash para múltiples elementos
    if (SimpleOrMultiples != 0) {
        if(NewElements == null){
           throw new ParsingException("Error: NewElements are null and you select 'Multiples'(SimpleOrMultiple: "+SimpleOrMultiples+")");
        }
        // Iterate through the list of new elements and add them to the hash table
        // Itera a través de la lista de nuevos elementos y agrégales a la tabla hash
        for (String element2 : NewElements) {
            hash = 0;
            for (int i = 0; i < element2.length(); i++) {
                hash += element2.charAt(i);
            }
           if(hashTable != null) hashTable.put(element2, hash);
            if(AddToPreDefined == TableHash.Arithmetics) {
                // Add to the arithmetic hash table
                // Agregar a la tabla hash aritmética
                hashTableArith.put(element2, hash);
            }
            else if(AddToPreDefined == TableHash.Booleans) {
                // Add to the boolean hash table
                // Agregar a la tabla hash booleana
                hashTableBool.put(element2, hash);
            }
            else if(AddToPreDefined == TableHash.POP_PUSH) {
                // Add to the functions hash table
                // Agregar a la tabla hash de funciones
                hashTablePOP_PUSH.put(element2, hash);
            }
        }
    }
    // If SimpleOrMultiples is 0, create a hash table for a single element
    // Si SimpleOrMultiples es 0, crea una tabla hash para un solo elemento 
    else {
        if(element == null){
            throw new ParsingException("Error: element are null and you select 'Single'(SimpleOrMultiple: "+SimpleOrMultiples+")");
        }
        hash = 0;
        for (int i = 0; i < element.length(); i++) {
            hash += element.charAt(i);
        }
        hashTable.put(element, hash);
        if(AddToPreDefined == TableHash.Arithmetics) {
                // Add to the arithmetic hash table
                // Agregar a la tabla hash aritmética
                hashTableArith.put(element, hash);
            }
            else if(AddToPreDefined == TableHash.Booleans) {
                // Add to the boolean hash table
                // Agregar a la tabla hash booleana
                hashTableBool.put(element, hash);
            }
            else if(AddToPreDefined == TableHash.POP_PUSH) {
                // Add to the functions hash table
                // Agregar a la tabla hash de funciones
                hashTablePOP_PUSH.put(element, hash);
            }
    }
    return;
}
//-------------------------------------------------------
/**
 * Returns the first n characters of the input string.
 * <p>
 * This method extracts a substring from the beginning of the input string with the specified length {@code n}.
 * If {@code n} is greater than the length of the input string, it returns the entire string.
 * If {@code n} is negative, it throws a {@code ParsingException}.
 * If the input string is {@code null}, it throws a {@code ParsingException}.
 *
 * @param input The input string from which to extract characters.
 * @param n The number of characters to extract from the start of the string.
 * @param watchMessages If {@code true}, prints a message when {@code n} is greater than the input length.
 * @return A substring containing the first {@code n} characters.
 * @throws ParsingException If the input string is {@code null} or {@code n} is negative.
 */
public String getNchars(String input, int n, boolean watchMessages) throws ParsingException {
    if (input == null) {
        throw new ParsingException("Error: input string is null");
    }

    if (n < 0) {
        throw new ParsingException("Error: can't use negative numbers to get characters");
    }

    if (n > input.length()) {
       if(watchMessages) System.out.println("The number of characters to get is greater than the length of the input string\nThe code will be executed with the length of the input string\n");
        n = input.length();
    }

    return input.substring(0, n);
}
//-------------------------------------------------------
/**
 * Compares a string against a hash table or a predefined table to determine if it matches a valid command or argument.
 * <p>
 * This method checks if the input string (or its prefix) exists in the provided hash table or in one of the predefined tables (arithmetic, boolean, or POP_PUSH).
 * It supports both commands with and without arguments, and delegates the actual comparison to {@code compareTableImplement}.
 * <br>
 * The method updates the {@code iEspecial} mutable variable with the length of the matched command or argument.
 * <br>
 * Returns:
 * <ul>
 *   <li>0 if a valid match is found.</li>
 *   <li>-1 if no valid match is found or if there are unexpected characters.</li>
 *   <li>-3 if a fatal error occurs (should never be returned in normal operation).</li>
 * </ul>
 *
 * <b>Parameters:</b>
 * @param line The input string to check.
 * @param nLine The line number or identifier (for error messages).
 * @param CharsNumToCompare_SRING_MORE_LONG The maximum number of characters to compare.
 * @param hashTableForCompare The hash table containing valid commands or arguments (can be null if using a predefined table).
 * @param CompareWithPreDefined The predefined table to use (can be null if using a custom hash table).
 * @param withArgs True if the command expects arguments; false otherwise.
 * @param iEspecial Mutable variable to store the length of the matched command or argument.
 * @return An integer indicating the result (see above).
 */
public int compareWithHashTable(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, TableHash CompareWithPreDefined, boolean withArgs, MutableTypeData<Integer>iEspecial) {
    if(CharsNumToCompare_SRING_MORE_LONG <= 0){
        System.out.printf("Error\nDETAILS: Expected a long of string  > 0\n");
        return -1;
    }
    if(hashTableForCompare == null && CompareWithPreDefined == null){
        System.err.println("Error\nDETAILS: Exepected a table hash to compare");
        return -1;
    }
    else if(hashTableForCompare == null && CompareWithPreDefined != null){
        int n;
        switch (CompareWithPreDefined) {
            case POP_PUSH:
                n = compareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTablePOP_PUSH, iEspecial, true);
                break;
            case Arithmetics:
                n = compareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableArith, iEspecial, false);
                break;
            case Booleans:
                n = compareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableBool, iEspecial, false);
               break;
            default:
            n = -1;
                break;
        }
        if(n != 0) return -1;
        else return 0;
    }
    else if(hashTableForCompare != null && CompareWithPreDefined == null){
       int n = compareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableForCompare, iEspecial, withArgs);
       if(n != 0) return -1;
       else return 0;
    }
    else{
        int n;
        n = compareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableForCompare, iEspecial, withArgs);
        if(n != 0){
            switch (CompareWithPreDefined) {
            case POP_PUSH:
                n = compareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTablePOP_PUSH, iEspecial, true);
                break;
            case Arithmetics:
                n = compareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableArith, iEspecial, false);
                break;
            case Booleans:
                n = compareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableBool, iEspecial, false);
               break;
            default:
            n = -1;
                break;
        }
        if(n != 0) return -1;
        else return 0;
      }
    }
    System.err.println("Fatal ERROR Debug for see the error\n");
    return -3; //especial value, this never must be the out
             //Valor especial, esto nunca deberia salir
}
//-------------------------------------------------------  
/**
 * Compares a string against a hash table to determine if it matches a valid command or argument.
 * <p>
 * This method checks if the input string (or its prefix) exists in the provided hash table, supporting both commands with and without arguments.
 * If {@code withArguments} is true, the method iteratively reduces the prefix length to find the longest matching command.
 * If {@code withArguments} is false, it ensures that there are no unexpected characters after the command.
 * <br>
 * The method updates the {@code iEspecial} mutable variable with the length of the matched command or argument.
 * <br>
 * Returns:
 * <ul>
 *   <li>0 if a valid match is found.</li>
 *   <li>-1 if no valid match is found or if there are unexpected characters.</li>
 *   <li>-2 if the input string is null.</li>
 * </ul>
 *
 * <b>Parameters:</b>
 * @param line The input string to check.
 * @param nLine The line number or identifier (for error messages).
 * @param CharsNumToCompare_SRING_MORE_LONG The maximum number of characters to compare.
 * @param hashTableForCompare The hash table containing valid commands or arguments.
 * @param iEspecial Mutable variable to store the length of the matched command or argument.
 * @param withArguments True if the command expects arguments; false otherwise.
 * @return An integer indicating the result (see above).
 */
public int compareTableImplement(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, MutableTypeData<Integer> iEspecial, boolean withArgumets){
    if(line != null){
    // Get the first N characters of the input string
        // Obtener los primeros N caracteres de la cadena de entrada
        String element;
        try{element = getNchars(line, CharsNumToCompare_SRING_MORE_LONG, false);
                }catch(ParsingException e){
                    System.err.println("Error:"+e.getMessage());
              return -1;
            } 
        if(element.equals("")) return -1;
        if (hashTableForCompare.containsKey(element) && !withArgumets) {
            // Verificar que no haya caracteres inesperados después del  carácter
            // Check that there are no unexpected characters after the  character
            String remaining = line.substring(element.length(), line.length()); // Tomamos lo que sigue //Take the rest of line 
            remaining = remaining.trim();//ignore the spaces and tab
                             //ignorar esapcios y tabulaciones
            if (!remaining.isEmpty()) {
                System.out.printf("Error in the line %s\nDETAILS: Unexpected characters after instruction\n", nLine);
                return -1;
            }
            iEspecial.setValor(line.length());
            return 0;
        }
        if(withArgumets){
             if(iEspecial != null)iEspecial.setValor(1);
            int i = 1;
            int sub = CharsNumToCompare_SRING_MORE_LONG;
            //Iterater until found a conincidence 
            //Iterar hasta encontrar una conincidencia
            while(!(hashTableForCompare.containsKey(element)) && i <= CharsNumToCompare_SRING_MORE_LONG){
                 sub= CharsNumToCompare_SRING_MORE_LONG-i;
                try{element = getNchars(element, sub, false);
                }catch(ParsingException e){
                    System.err.println("Error:"+e.getMessage());
              return -1;
            }             
                if(iEspecial != null) iEspecial.setValor(sub);
                i++;
            }
            if(i == 1){ 
                if(iEspecial != null) iEspecial.setValor(CharsNumToCompare_SRING_MORE_LONG);//if is the comand more long
                i = CharsNumToCompare_SRING_MORE_LONG;
            }
            
            // Check if the string is a invalid expression (not found in the table)
           // Verificar si la cadena es una expresión no válida (no encontrada en la tabla)
            if(i > CharsNumToCompare_SRING_MORE_LONG){
                System.err.printf("Error in the line %s\nDETAILS: Invalid Expression, not found in the Hash Table\n", nLine);
                return -1;
            }
            if(sub == line.length()){
                System.err.println("Error in the line %s\nDETAILS: Without args after of the command\n");
                return -1;
            }
            return 0;
        }
        System.err.println("Error in the line "+nLine+"\nDETAILS: Invalid Expression, not find in the Hash Table\n");
            return -1;
    }
    // If the input string is null, print an error message
    // Si la cadena de entrada es nula, imprime un mensaje de error
     else {
        System.out.printf("Error in the line %s\nDETAILS: Input is null\n", nLine);
        //especial value of -2 to indicate that the input is null
        // valor especial de -2 para indicar que la entrada es nula
        return -2;
    }
}
//-------------------------------------------------------
/**
 * Compares and validates commands with arguments according to the defined syntax rules.
 * <p>
 * This method checks if a line of code contains a valid command with arguments, following the rules specified in a {@code CommandArgRule} object.
 * It supports commands without pattern, with flexible pattern, and with strict pattern. It also handles exceptions and validates the syntax of the arguments.
 * <br>
 * The method updates the mutable values {@code LengthOfCommand} and {@code LengthOfArg} with the detected command and argument lengths.
 * <br>
 * Returns:
 * <ul>
 *   <li>0 if the command and its arguments are valid and match the strict pattern.</li>
 *   <li>2 if the command is valid and does not require arguments (command without pattern).</li>
 *   <li>3 if the command is valid and matches a flexible pattern.</li>
 *   <li>-1 if there is a syntax error or the command is not valid.</li>
 * </ul>
 *
 * <b>Parameters:</b>
 * @param line Input line to analyze.
 * @param nLine Line number or identifier (for error messages).
 * @param ArgsInputRule Argument and pattern rules for the commands.
 * @param sensibleToUppercase 1 if validation is case-sensitive, 0 otherwise.
 * @param LengthOfCommand Mutable variable to store the detected command length.
 * @param LengthOfArg Mutable variable to store the detected argument length.
 * @return An integer indicating the validation result (see above).
 * @throws ParsingException If an error occurs during parsing or validation.
 *
 * <b>Example usage:</b>
 * <pre>
 * {@code
 * Parser.MutableTypeData<Integer> lenCmd = new Parser.MutableTypeData<>(0);
 * Parser.MutableTypeData<Integer> lenArg = new Parser.MutableTypeData<>(0);
 * int result = compareCommandsWithArg("push constant 10", "5", rule, 1, lenCmd, lenArg);
 * // result == 0 if the line is valid and matches the strict pattern
 * }
 * </pre>
 */
public int compareCommandsWithArg(String line, String nLine, CommandArgRule ArgsInputRule , int sensibleToUppercase, Parser.MutableTypeData<Integer>LengthOfCommand, Parser.MutableTypeData<Integer>LengthOfArg) throws ParsingException{


    if(LengthOfCommand == null || LengthOfArg == null){
        System.err.println("Error: Need put a arguemnts in the parameters 'LenghtOfCommand' and 'LengthOfArg'\n");
        return -1;
    }
    if(ArgsInputRule == null){
        System.err.println("Error: Need put a arguemnts in the parameter 'ArgsInputRule'\n");
        return -1;
    }
    //check if are an exception (ilegal instruction)
    //revisar si es una exepción(instrucción no perimitda)
    if(ArgsInputRule.getExceptions() != null){
        int r;
        try{ r = checkExcep(line, ArgsInputRule.getExceptions());}catch(ParsingException e){throw new ParsingException(e.getMessage());}
        if(r == -1) return -1;
      if(r == 1){
        System.out.printf("Error in the line %s\nThe instruction is an ilegal instruction find in this list %s\n", nLine, ArgsInputRule.getExceptions());
        return -1;
      }
    }

    Parser p = new Parser();
    Parser.MutableTypeData<Boolean>coincidence = p.new MutableTypeData<>(false);
    String temp = "";
    //validar si es un tipo especial de comando sin formato
    //Check if its a comand withouth format
    boolean isWithoutPattern = false;
    if(ArgsInputRule.getCommandsWithoutPatterns() != null){
        String line2 = line.trim();
        compareTableImplement(line2, nLine, ArgsInputRule.getCommandLength(), ArgsInputRule.getCommandTable(), LengthOfCommand, true);
        if(ArgsInputRule.getCommandsWithoutPatterns().contains(line2.substring(0, LengthOfCommand.getValor()))) isWithoutPattern = true;
    }
    //validar si es un tipo especial de comando con formato flexible
    //check if its a speacial command with flexible format
    boolean isFlexiblePattern = false;
    if(ArgsInputRule.getCommandsWithFlexiblePattern() != null && !isWithoutPattern){
        String line2 = line.trim();
        compareTableImplement(line2, nLine, ArgsInputRule.getCommandLength(), ArgsInputRule.getCommandTable(), LengthOfCommand, true);
        if(ArgsInputRule.getCommandsWithFlexiblePattern().contains(line2.substring(0, LengthOfCommand.getValor()))) isFlexiblePattern = true;
    }
    //if its a flexible pattern and without pattern, print an error
    //Si es un patrón flexible y sin patrón, imprimir un error
    if(isFlexiblePattern && isWithoutPattern){
        String line2 = line.trim();
        temp = line2.substring(0, LengthOfCommand.getValor());
        System.err.printf("Error in the command %s\nDETAILS:The command can't match in both types of commands 'commandsWithoutPatterns' and 'commandsWithFlexiblePattern'\n",temp);
        return -1;
    }

    // Si hay múltiples formatos, validar si la línea entra en alguno
    //Check for multiples formats the line
    if(!isFlexiblePattern && !isWithoutPattern){ try{orchestratorForStrictFormat(line, nLine, ArgsInputRule, sensibleToUppercase, coincidence);
   }catch(ParsingException e){
    throw new ParsingException(e.getMessage());
    }
   }

    ArrayList<Integer>indexSpecialChars = new ArrayList<>();
    if(isFlexiblePattern){ try{orchestratorForFlexibleFormat(line, nLine, ArgsInputRule, sensibleToUppercase, LengthOfCommand, LengthOfArg, coincidence, indexSpecialChars);
    }catch(ParsingException e){
         throw new ParsingException(e.getMessage());
        }
    }
    
    boolean without = false;
    boolean flexible = false;
    
    String newLine = line;
    // Comparar comando
    // Compare the comand

    LengthOfCommand.setValor(0);
    if (compareTableImplement(newLine, nLine, ArgsInputRule.getCommandLength(), ArgsInputRule.getCommandTable(), LengthOfCommand, true) != 0)  return -1;
    if(ArgsInputRule.getCommandsWithoutPatterns().contains(newLine.substring(0, LengthOfCommand.getValor()))) without = true;
    if(ArgsInputRule.getCommandsWithFlexiblePattern().contains(newLine.substring(0, LengthOfCommand.getValor()))) flexible = true;
    if(without){
            LengthOfArg.setValor(0);
            return 2;
    }
    if(flexible){
       if(!indexSpecialChars.isEmpty())LengthOfArg.setValor(newLine.substring(LengthOfCommand.getValor(), indexSpecialChars.get(indexSpecialChars.size()-1)).length());
       else LengthOfArg.setValor(0); 
       return 3;
    }
    // Comparar argumentos (lo que sobra después del comando)
    //compare the arguments (the rest after the comand)
    String remainingNewLine = newLine.substring(LengthOfCommand.getValor(), newLine.length());
    if (compareTableImplement(remainingNewLine, nLine, ArgsInputRule.getArgLength(), ArgsInputRule.getArgTable(), LengthOfArg, true) != 0) {
        return -1;
    }

    return 0;
}
//-------------------------------------------------------
/**
 * Orchestrates the flexible format validation for a given line using the provided command argument rules.
 * <p>
 * This method determines whether the input line matches a flexible format, either by using a single flexible pattern
 * (defined by {@code formatPatternFlexible}) or by checking against multiple flexible format patterns (each defined in a list).
 * It validates the configuration of the provided {@code CommandArgRule} and delegates the actual format checking to {@code checkFlexibleFormat}.
 * <br>
 * If the line does not match any of the allowed flexible formats, a {@code ParsingException} is thrown.
 *
 * <b>Parameters:</b>
 * @param line The input line to check.
 * @param nLine The line number or identifier (used for error messages).
 * @param ArgsInputRule The command argument rule containing flexible format patterns and validation settings.
 * @param sensibleToUppercase 1 if case-sensitive, 0 otherwise.
 * @param LengthOfCommand Mutable integer to store the length of the command.
 * @param LengthOfArg Mutable integer to store the length of the argument.
 * @param coincidence Mutable boolean to indicate if the line matches any flexible format.
 * @param indexSpecialChars List to store the indexes of found special characters (can be null).
 *
 * @throws ParsingException If the input parameters are invalid or if the line does not match any allowed flexible format.
 *
 * <b>Example usage:</b>
 * <pre>
 * {@code
 * CommandArgRule rule = ...; // properly configured CommandArgRule
 * MutableTypeData<Integer> LengthOfCommand = new MutableTypeData<>(0);
 * MutableTypeData<Integer> LengthOfArg = new MutableTypeData<>(0);
 * MutableTypeData<Boolean> coincidence = new MutableTypeData<>(false);
 * ArrayList<Integer> indexSpecialChars = new ArrayList<>();
 * orchestratorForFlexibleFormat("functionMain0", "5", rule, 1, LengthOfCommand, LengthOfArg, coincidence, indexSpecialChars);
 * // If the line matches, coincidence.getValor() will be true; otherwise, a ParsingException is thrown.
 * }
 * </pre>
 */
public void orchestratorForFlexibleFormat(String line,String nLine, CommandArgRule ArgsInputRule, int sensibleToUppercase, Parser.MutableTypeData<Integer>LengthOfCommand, Parser.MutableTypeData<Integer>LengthOfArg, MutableTypeData<Boolean>coincidence, ArrayList<Integer>indexSpecialChars) throws ParsingException{
    //Check the inputs
    //Revisar las entradas
    if (line == null) {
        throw new ParsingException("Error in the parameters, need put the line to check in 'line' parameter\n");
    }
    if (ArgsInputRule == null) {
        throw new ParsingException("Error in the parameter, need put something in the 'ArgsInputRule'\n");
    }
    if(ArgsInputRule.getFormatPatternFlexible() == null && ArgsInputRule.getMultiplesFlexiblesFormatsPatterns() == null){
        throw new ParsingException("Error in the parameter, need put something in the 'formatPatternFlexible' or 'multiplesFlexiblesFormatsPatterns'\n");
    }
    //Validacion para formatos flexibles
    //Check for flexibles formats patterns
    if(ArgsInputRule.getCommandsWithFlexiblePattern() != null && (ArgsInputRule.getFormatPatternFlexible() == null && ArgsInputRule.getMultiplesFlexiblesFormatsPatterns() == null)){
        throw new ParsingException("Error in the format flexible commands arguments\nDETAILS: If you have commands with flexible patterns, need put something in 'formatpatternflexible' or 'multiplesFlexiblesFormatsPatterns'\n");
    }
    if(ArgsInputRule.getCommandsWithFlexiblePattern() != null && (ArgsInputRule.getCommandsWithFlexiblePattern() != null && ArgsInputRule.getMultiplesFlexiblesFormatsPatterns() != null)){
        throw new ParsingException("Error in the flexibles formats patterns arguments\nDETAILS: Just can select one theme for use\n");
    }

    String temp = "";

    Parser p = new Parser();
    if(ArgsInputRule.getFormatPatternFlexible() != null){
        Parser.MutableTypeData<String> formatOfline = p.new MutableTypeData<>("");
        Parser.MutableTypeData<String> formatOfPattern = p.new MutableTypeData<>(ArgsInputRule.getFormatPatternFlexible());
       try{checkFlexibleFormat(line, formatOfline, formatOfPattern, null, ArgsInputRule.getFormatPatternFlexible(), ArgsInputRule.getSpecialCharsForIdentifyInTheFlexibleFormat(),ArgsInputRule.getORgateForFlexible(), coincidence, null, sensibleToUppercase, ArgsInputRule.getThePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns(), indexSpecialChars, ArgsInputRule.getTheLineAreInTheFormatExpected());}catch(ParsingException e){throw new ParsingException(e.getMessage());}
        temp = line.substring(0, LengthOfCommand.getValor());
       if(!coincidence.getValor() && ArgsInputRule.getCommandsWithFlexiblePatternForResultConflicts().contains(temp)){ 
        
        try{resolveConflicts(formatOfline, formatOfPattern.getValor(),ArgsInputRule.getMapForFlexible(), ArgsInputRule.getStopForFlexibleForConflicts(), ArgsInputRule.getORgateForFlexible());}catch(ParsingException e){throw new ParsingException(e.getMessage());}
       try{checkFlexibleFormat(formatOfline.getValor(), formatOfline, formatOfPattern, null, ArgsInputRule.getFormatPatternFlexible(), ArgsInputRule.getSpecialCharsForIdentifyInTheFlexibleFormat(), ArgsInputRule.getORgateForFlexible(), coincidence, null, sensibleToUppercase, ArgsInputRule.getThePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns(), indexSpecialChars, true);}catch(ParsingException e){throw new ParsingException(e.getMessage());}
       if(!coincidence.getValor()){
       throw new ParsingException("Error in the line "+nLine+"\nDETAILS:Error in the format not pass the check for flexibles formats\nFormat of Line: "+formatOfline.getValor()+"\nFormat expected: "+formatOfPattern.getValor()+"\n");
          }
    }
    else if(!coincidence.getValor()&& !ArgsInputRule.getCommandsWithFlexiblePatternForResultConflicts().contains(temp)){
       throw new ParsingException("Error in the line "+nLine+"\nDETAILS:Error in the format not pass the check for flexibles formats\nFormat of Line: "+formatOfline.getValor()+"\nFormat expected: "+formatOfPattern.getValor()+"\n");
       }
    }
    else if(ArgsInputRule.getMultiplesFlexiblesFormatsPatterns()!= null){
        Parser.MutableTypeData<String> formatOfline = p.new MutableTypeData<>("");
        Parser.MutableTypeData<String> formatOfPattern = p.new MutableTypeData<>("");
        try{checkFlexibleFormat(line, formatOfline, formatOfPattern, ArgsInputRule.getMultiplesFlexiblesFormatsPatterns(), null, ArgsInputRule.getSpecialCharsForIdentifyInTheFlexibleFormat(), ArgsInputRule.getORgateForFlexible(), coincidence, null, sensibleToUppercase, ArgsInputRule.getThePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns(), indexSpecialChars, ArgsInputRule.getTheLineAreInTheFormatExpected());}catch(ParsingException e){throw new ParsingException(e.getMessage());}
        temp = line.substring(0, LengthOfCommand.getValor());
       if(!coincidence.getValor() && ArgsInputRule.getCommandsWithFlexiblePatternForResultConflicts().contains(temp)){
        try{resolveConflicts(formatOfline, formatOfPattern.getValor(),ArgsInputRule.getMapForFlexible(), ArgsInputRule.getStopForFlexibleForConflicts(), ArgsInputRule.getORgateForFlexible());}catch(ParsingException e){throw new ParsingException(e.getMessage());}
       try{checkFlexibleFormat(formatOfline.getValor(), formatOfline, formatOfPattern, ArgsInputRule.getMultiplesFlexiblesFormatsPatterns(), null, ArgsInputRule.getSpecialCharsForIdentifyInTheFlexibleFormat(), ArgsInputRule.getORgateForFlexible(), coincidence, null, sensibleToUppercase, ArgsInputRule.getThePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns(), indexSpecialChars,true);}catch(ParsingException e){throw new ParsingException(e.getMessage());}
       if(!coincidence.getValor()){
       throw new ParsingException("Error in the line "+nLine+"\nDETAILS:Error in the format not pass the check for flexibles formats\nFormat of Line: "+formatOfline.getValor()+"\nFormat expected: "+formatOfPattern.getValor()+"\n");
          }
    }
    else if(!coincidence.getValor()&& !ArgsInputRule.getCommandsWithFlexiblePatternForResultConflicts().contains(temp)){
       throw new ParsingException("Error in the line "+nLine +"\nDETAILS:Error in the format not pass the check for flexibles formats\nFormat of Line: "+formatOfline.getValor()+"\nFormat expected: "+formatOfPattern.getValor()+"\n");
       }
    }
    
    return;
}
//-------------------------------------------------------
/**
 * Orchestrates the strict format validation for a given line using the provided command argument rules.
 * <p>
 * This method determines whether the input line matches a strict format, either by using a single strict pattern
 * (defined by the most-long and less-long format) or by checking against multiple format patterns (each defined as a range).
 * It validates the configuration of the provided {@code CommandArgRule} and delegates the actual format checking to {@code checkStrictFormat}.
 * <br>
 * If the line does not match any of the allowed formats, a {@code ParsingException} is thrown.
 *
 * <b>Parameters:</b>
 * @param line The input line to check.
 * @param nLine The line number or identifier (used for error messages).
 * @param ArgsInputRule The command argument rule containing format patterns and validation settings.
 * @param sensibleToUppercase 1 if case-sensitive, 0 otherwise.
 * @param coincidence Mutable boolean to indicate if the line matches any strict format.
 *
 * @throws ParsingException If the input parameters are invalid or if the line does not match any allowed strict format.
 *
 * <b>Example usage:</b>
 * <pre>
 * {@code
 * CommandArgRule rule = ...; // properly configured CommandArgRule
 * MutableTypeData<Boolean> coincidence = new MutableTypeData<>(false);
 * orchestratorForStrictFormat("pushconstant10", "5", rule, 1, null, null, coincidence);
 * // If the line matches, coincidence.getValor() will be true; otherwise, a ParsingException is thrown.
 * }
 * </pre>
 */
public void orchestratorForStrictFormat(String line, String nLine, CommandArgRule ArgsInputRule, int sensibleToUppercase, MutableTypeData<Boolean>coincidence) throws ParsingException{
    // This method is used to orchestrate the strict format checking
    // Este método se utiliza para orquestar la verificación del formato estricto

    // Check the inputs
    if (line == null) {
        throw new ParsingException("Error in the parameters, need put the line to check in 'line' parameter");
    }
    //Verifica que no se usen ambos tipos de formato al mismo tiempo
    //Check if is use both types of format to the same time
    if (ArgsInputRule.getMultipleFormatsPatterns() != null && ArgsInputRule.getFormatPatternMostLong() != null || (ArgsInputRule.getMultipleFormatsPatterns() == null && ArgsInputRule.getFormatPatternMostLong() == null)) {
        throw new ParsingException("Error\nDETAILS: You can only select one: 'multipleFormatsPatterns' or 'formatPatternMostLong' && 'formatPatternLessLong'\n");
    }
    // Validación para múltiples formatos
    //If select multiples format, check this
    if (ArgsInputRule.getMultipleFormatsPatterns() != null) {
        for (HashMap.Entry<String, String> entry : ArgsInputRule.getMultipleFormatsPatterns().entrySet()) {
            String lessLong = entry.getKey();
            String mostLong = entry.getValue();
            if (lessLong == null || mostLong == null) {
                throw new ParsingException("Error in format pattern: formatPatternMostLong: "+mostLong+", formatPatternLessLong: "+lessLong+"\nDETAILS: All formats must define both a most long and less long pattern.\n");
            }
        }
    }
    // Validación para formato único
    //Check if is just one format
    if ((ArgsInputRule.getFormatPatternMostLong() != null && ArgsInputRule.getFormatPatternLessLong() == null) ||
        (ArgsInputRule.getFormatPatternLessLong() != null && ArgsInputRule.getFormatPatternMostLong() == null)) {
       throw new ParsingException("Error in format pattern:\nMost long: "+ArgsInputRule.getFormatPatternMostLong()+"\nLess long: "+ArgsInputRule.getFormatPatternLessLong()+"\nDETAILS: Both formatPatternMostLong and formatPatternLessLong must be defined.\n");
    }
    // Si hay múltiples formatos, validar si la línea entra en alguno
    //Check for multiples formats the line
    if(ArgsInputRule.getMultipleFormatsPatterns() != null){
         try{checkStrictFormat(line, ArgsInputRule.getMultipleFormatsPatterns(), null, null, null, coincidence, sensibleToUppercase);}catch(ParsingException e){throw new ParsingException(e.getMessage());}
       if(!coincidence.getValor()){
        throw new ParsingException("Error in the line "+nLine+"\nDETAILS:Error in the format of line not match in the range of formats '"+ArgsInputRule.getMultipleFormatsPatterns()+"'\n");
     }
    }
    // Validar con formato único
    //Check for singular format the line
    if (ArgsInputRule.getFormatPatternMostLong() != null && ArgsInputRule.getFormatPatternLessLong() != null){
         try{checkStrictFormat(line, null, ArgsInputRule.getFormatPatternMostLong(), ArgsInputRule.getFormatPatternLessLong(), coincidence, null, sensibleToUppercase);}catch(ParsingException e){throw new ParsingException(e.getMessage());}
     if(!coincidence.getValor()){
      throw new ParsingException("Error in the line "+nLine+"\nDETAILS:Error in the format of line not match in the range of formats '"+ArgsInputRule.getFormatPatternMostLong()+"' and '"+ArgsInputRule.getFormatPatternLessLong()+"'\n");
     }
    }
    return;
}
//-------------------------------------------------------
/**
 * Checks if a given line matches a strict format pattern or any of multiple strict format patterns.
 * <p>
 * This method analyzes the input line and compares its structure against one or more strict patterns, which are defined by their "format fingerprints".
 * It supports both a single strict pattern (defined by a most-long and less-long format) and a map of multiple patterns (each with a range).
 * <br>
 * The method updates the provided mutable booleans to indicate if the line matches the single pattern or any of the multiple patterns.
 * <br>
 * If a match is found, the corresponding mutable boolean is set to true. If no match is found, it is set to false.
 *
 * <b>Parameters:</b>
 * @param lineToCheck The input string to check.
 * @param multiplesPatterns Map of multiple format patterns, where each entry defines a range (key: less-long, value: most-long). Can be null if using single pattern.
 * @param singlePatternMostLong The most-long format pattern for single-pattern checking. Can be null if using multiplesPatterns.
 * @param singlePatternLessLong The less-long format pattern for single-pattern checking. Can be null if using multiplesPatterns.
 * @param matchWithSingle Mutable boolean to indicate if the line matches the single pattern (can be null).
 * @param matchWithMultiples Mutable boolean to indicate if the line matches any of the multiple patterns (can be null).
 * @param sensibleToUppercase 1 if case-sensitive, 0 otherwise.
 *
 * @throws ParsingException If any parameter is invalid or if an error occurs during processing.
 *
 * <b>Example usage:</b>
 * <pre>
 * {@code
 * Map<String, String> patterns = new HashMap<>();
 * patterns.put("pushconstant32768", "popthis0");
 * MutableTypeData<Boolean> match = new MutableTypeData<>(false);
 * checkStrictFormat("pushconstant10", patterns, null, null, null, match, 1);
 * // match.getValor() will be true if the line matches any pattern in the map
 * }
 * </pre>
 */
public void checkStrictFormat(String lineToCheck, Map<String, String>multiplesPatterns, String singlePatternMostLong, String singlePatternLessLong, Parser.MutableTypeData<Boolean>matchWithSingle, Parser.MutableTypeData<Boolean>matchWithMultiples, int sensibleToUppercase) throws ParsingException{
    if(lineToCheck == null){
      throw new ParsingException("Error in the parameters, need put the line to check in 'lineToCheck' parameter");
    }
    if(multiplesPatterns == null && (singlePatternMostLong == null || singlePatternLessLong == null)){
      throw new ParsingException("Error in thhe parameter, need put something in the 'multiplesPatterns' or 'singlePatternMostLong and singlePatternLessLong");
    }
    // Si hay múltiples formatos, validar si la línea entra en alguno
    //Check for multiples formats the line
    boolean coincidence = false;
    if (multiplesPatterns != null) {
        double linePattern= identifyTheStrictFormat(lineToCheck, sensibleToUppercase);
        for (HashMap.Entry<String, String> entry : multiplesPatterns.entrySet()) {
            double mostLong = identifyTheStrictFormat(entry.getKey(), sensibleToUppercase);
            double lessLong = identifyTheStrictFormat(entry.getValue(), sensibleToUppercase);
            if(mostLong < lessLong){
                 double newMostLong = lessLong;
                lessLong = mostLong;
                mostLong = newMostLong;
            }
            if (linePattern >= lessLong && linePattern <= mostLong) {
                coincidence = true;
                if(matchWithMultiples != null) matchWithMultiples.setValor(true);
                break;
            }
        }
        if(!coincidence) if(matchWithMultiples != null) matchWithMultiples.setValor(false);
    }
    if(singlePatternMostLong != null && singlePatternLessLong != null){
        double mostLong = identifyTheStrictFormat(singlePatternMostLong, sensibleToUppercase);
        double lessLong= identifyTheStrictFormat(singlePatternLessLong, sensibleToUppercase);
        double linePattern = identifyTheStrictFormat(lineToCheck, sensibleToUppercase);
        if(mostLong < lessLong){
                 double newMostLong = lessLong;
                lessLong = mostLong;
                mostLong = newMostLong;
            }
        if (!(linePattern >= lessLong && linePattern <= mostLong)) {
            if(matchWithSingle != null)matchWithSingle.setValor(false);
        }
        else if(matchWithSingle != null) matchWithSingle.setValor(true);
    }
    return;
}
//-------------------------------------------------------
/**
 * Checks if a given line matches a flexible format pattern or any of multiple flexible format patterns.
 * <p>
 * This method analyzes the input line and compares its format against one or more flexible patterns, which may include special characters and OR gates (e.g., '|').
 * It supports both a single flexible pattern and a list of multiple patterns. The function can also handle special characters and OR gates for more complex matching.
 * <br>
 * The method can update mutable wrappers with the detected format of the line and the pattern, and can record the positions of special characters if requested.
 * <br>
 * If a match is found, the corresponding mutable boolean is set to true. If no match is found, it is set to false.
 * <p>
 * <b>Important:</b> The operation of OR gates is based on indices. For example, if the format is <code>A|BCD|F</code> and <code>'|'</code> is the OR indicator, then index 0 can be <code>A</code> or <code>B</code>, index 2 can be <code>D</code> or <code>F</code>, but index 1 will always be <code>C</code>.
 * </p>
 * <p><b>Parameters:</b></p>
 * @param lineToCheck The input string to check.
 * @param formatLine Mutable wrapper to store the detected format of the input line (can be null).
 * @param formatOfPattern Mutable wrapper to store the detected format of the pattern (can be null).
 * @param multiplesPatterns List of multiple flexible patterns to check against (can be null if using singlePattern).
 * @param singlePattern A single flexible pattern to check against (can be null if using multiplesPatterns).
 * @param specialsCharactersForIdentify List of special characters to identify in the format (can be null).
 * @param indicateORgateInThePatterns Character used as the OR gate in patterns (e.g., '|', can be null).
 * @param matchWitSingle Mutable boolean to indicate if the line matches the single pattern (can be null).
 * @param matchWithMultiples Mutable boolean to indicate if the line matches any of the multiple patterns (can be null).
 * @param sensibleToUppercase 1 if case-sensitive, 0 otherwise.
 * @param thePatternsAreTheFormatExpectedOrNeedBeConvert If true, patterns are already in the expected format; if false, they will to be converted.
 * @param indexWhereFindTheSpecialCharsInTheLine List to store the indexes of found special characters (can be null).
 * @param lineToCheckHasBeFormat If true, the input line is already in format form; if false, it will be converted.
 *
 * @throws ParsingException If any parameter is invalid or if an error occurs during processing.
 *
 * <p><b>Example usage:</b></p>
 * <pre>
 * {@code
 * ArrayList<String> patterns = new ArrayList<>();
 * patterns.add("W|N|L|PSN");
 * MutableTypeData<Boolean> match = new MutableTypeData<>(false);
 * checkFlexibleFormat("functionMain0", null, null, patterns, null, null, '|', null, match, 1, true, null, false);
 * // match.getValor() will be true if the line matches the pattern
 * }
 * </pre>
 */
public void checkFlexibleFormat(String lineToCheck, Parser.MutableTypeData<String>formatLine, Parser.MutableTypeData<String>formatOfPattern, ArrayList<String> multiplesPatterns, String singlePattern, ArrayList<Character> specialsCharactersForIdentify, Character indicateORgateInThePatterns, Parser.MutableTypeData<Boolean> matchWitSingle, Parser.MutableTypeData<Boolean> matchWithMultiples, int sensibleToUppercase, boolean thePatternsAreTheFormatExpectedOrNeedBeConvert, ArrayList<Integer>indexWhereFindTheSpecialCharsInTheLine, boolean lineToCheckHasBeFormat) throws ParsingException{
    //Check the inputs
    //Revisar las entradas
    if (lineToCheck == null) {
        throw new ParsingException("Error in the parameters, need put the line to check in 'lineToCheck' parameter");
    }
    if (multiplesPatterns == null && singlePattern == null) {
        throw new ParsingException("Error in the parameter, need put something in the 'multiplesPatterns' or 'singlePattern'\n");
    }
    boolean multiples = false;
    if(multiplesPatterns != null){
        if( multiplesPatterns.isEmpty() ){
      throw new ParsingException("Error in the parameter, need put something in the 'multiplesPatterns' or 'singlePattern'\n");
    }
    multiples = true;
    }
    if(singlePattern.equals("") && !multiples){
     throw new ParsingException("Error in the parameter, need put something in the 'multiplesPatterns' or 'singlePattern'\n");
    }
    // Validar que no haya conflicto entre delimitadores especiales y OR
    //Check if have conflicts between special chars and OR character delimiter
    if (specialsCharactersForIdentify != null && indicateORgateInThePatterns != null) {
            if (specialsCharactersForIdentify.contains(indicateORgateInThePatterns)) {
            throw new ParsingException("Error: Conflict between special delimiters and OR gate characters: '" + indicateORgateInThePatterns + "'");
            }
    }
    //Verificar que el delimitador de OR no se alguna letra que se use en la creacion de el patron flexible
    //Verified that delimiter for OR gate not be some letter or character use in the creation of the flexible creation
    if(thePatternsAreTheFormatExpectedOrNeedBeConvert && (indicateORgateInThePatterns == 'N' || indicateORgateInThePatterns== 'L') && sensibleToUppercase == 0){
     throw new ParsingException("Error: Conflict between indicate OR gate and characters of the format, change for other different to 'N' and 'L'\n");
    }
    if(thePatternsAreTheFormatExpectedOrNeedBeConvert && (indicateORgateInThePatterns == 'N' || indicateORgateInThePatterns== 'L'||
    indicateORgateInThePatterns == 'P' || indicateORgateInThePatterns == 'W') && sensibleToUppercase != 0){
     throw new ParsingException("Error: conflict between indicate OR gate and characters of the format, change for other different to 'N', 'L', 'P' and 'W'\n");
    }
     
    boolean coincidence = false;
    try{
    //Chekc for multiples pattern
    //Revision para patrones múltiples
    if (multiplesPatterns != null) {
        //make and get the format for the line to check
        //hacer y obtener el formato para la linea a revisar
        String linePattern = "";
        if(!lineToCheckHasBeFormat)identifyTheFlexibleFormat(lineToCheck, sensibleToUppercase, specialsCharactersForIdentify, indexWhereFindTheSpecialCharsInTheLine);
        else linePattern = lineToCheck;
        if(linePattern.equals("")) throw new ParsingException("Error the line pattern is empty\n"); //error 
        if(formatLine != null) formatLine.setValor(linePattern);//upload the format of line //actualizar el formato de la linea
        //get the pattern for compare
        //obtener el patron para comparar
        for (String pattern : multiplesPatterns) {
            boolean match;
            //if the pattern need be convert to a format (because is an example)
            //si el patron nesecita ser convertido a el formato (porque es un ejemplo)
            if (!thePatternsAreTheFormatExpectedOrNeedBeConvert) {
                pattern = identifyTheFlexibleFormat(pattern, sensibleToUppercase, specialsCharactersForIdentify, indexWhereFindTheSpecialCharsInTheLine);
                if(pattern.equals("")) throw new ParsingException("Error the pattern is empty\n");
                if(formatOfPattern != null) formatOfPattern.setValor(pattern);
                //search a single and simple match because just can put OR gates when the pattern are in the format expected, because the "convert" don't make that and don't can't recognize this, and just identify like other letter
                //buscar un coincidencia simple y unica porque solo puede poner compuertas OR cuando el patrón es el esperado, porque el "convertidor" no hace esto y no puede reconocer estos, y solo los identifica o trata como otra letra
                match = pattern.equals(linePattern);
            } else {
                //if the pattern are in the expected format, check if have OR gates and search match
                //si el patron es el esperado, revisr si tiene compuertar OR y buscar una coincidencia 
                match = flexiblePatternMatchWithOR(linePattern, pattern, indicateORgateInThePatterns);
            }
            //si hay alguna conincidencia
            //if match
            if (match) {
                coincidence = true;
                if (matchWithMultiples != null) matchWithMultiples.setValor(true);
                break;
            }
        }
        if (!coincidence && matchWithMultiples != null) matchWithMultiples.setValor(false);
    }
    //for sigle pattern
    //para un solo pátron
    if (singlePattern != null) {
        String linePattern = "";
        if(!lineToCheckHasBeFormat) linePattern= identifyTheFlexibleFormat(lineToCheck, sensibleToUppercase, specialsCharactersForIdentify, indexWhereFindTheSpecialCharsInTheLine);
        else linePattern = lineToCheck;
        if(linePattern.equals(null)) throw new ParsingException("Error: the line pattern is 'null'\n");
        if(formatLine != null) formatLine.setValor(linePattern);
        String pattern = singlePattern;
        boolean match;
        if (!thePatternsAreTheFormatExpectedOrNeedBeConvert) {
            pattern = identifyTheFlexibleFormat(singlePattern, sensibleToUppercase, specialsCharactersForIdentify, indexWhereFindTheSpecialCharsInTheLine);
            if(pattern.equals(null)) throw new ParsingException("Error: the pattern is 'null'\n");
            if(formatOfPattern != null) formatOfPattern.setValor(pattern);
            match = linePattern.equals(pattern);
        } else {
            match = flexiblePatternMatchWithOR(linePattern, pattern, indicateORgateInThePatterns);
        }
        if (!match) {
            if (matchWitSingle != null) matchWitSingle.setValor(false);
        } else if (matchWitSingle != null) {
            matchWitSingle.setValor(true);
        }
     }
    }catch(ParsingException e){
        throw new ParsingException(e.getMessage());
    }
    return;
}
//-------------------------------------------------------
/**
 * Compares a pattern containing OR gates (for example, "AL|NS", which means "ALS" or "ANS") against a line format.
 * <p>
 * This method splits the pattern by the OR gate character (e.g., '|') and checks if the given line format matches any of the possible options.
 * <br>
 * <p>
 * <b>Important:</b> The operation of OR gates is based on indices. For example, if the format is <code>A|BCD|F</code> and <code>'|'</code> is the OR indicator, then index 0 can be <code>A</code> or <code>B</code>, index 2 can be <code>D</code> or <code>F</code>, but index 1 will always be <code>C</code>.
 * </p>
 * Returns true if there is a match with any of the options separated by the OR gate.
 *
 * <b>Example:</b>
 * <pre>
 * {@code
 * // Suppose linePattern = "ALS" and pattern = "AL|NS" with orGates = '|'
 * boolean result = flexiblePatternMatchWithOR("ALS", "AL|NS", '|');
 * // result == true, because "ALS" matches one of the options ("ALS" or "ANS")
 * }
 * </pre>
 *
 * @param linePattern The format string to compare.
 * @param pattern The pattern string, possibly containing OR gates.
 * @param orGates The character used as the OR gate (e.g., '|').
 * @return true if the linePattern matches any option in the pattern <ul><li>false otherwise.</ul>
 */
private boolean flexiblePatternMatchWithOR(String linePattern, String pattern, Character orGates) {
    if (orGates == null) {
        return linePattern.equals(pattern);
    }
    // Solo soporta un caracter OR, por ejemplo '|'
    //Just support a character OR, for example '|'
    char orChar = orGates;
    // Separar las opciones por el caracter OR
    // Merch the options for the OR character
    String[] options = pattern.split("\\" + orChar);
    int i = 0;
    for (String option : options) {
        //Tomar el caracter OR y unirlo con el resto corespondiente, es deicr si tenemos M|SNW entonces si el OR es '|' entonces lo que haremos sera agregar a el ArrayLlist "MNW" y "SNW", porque son las 2 posibilidades separadas por la compuerta OR
        //Get the OR character and match whit the correnspodent rest, for example if we have M|SNW when '|' is the OR indicator, so we make with this is add for the ArrayList "MNW" and "SNW", because are all the permutations merch for the OR gate
        if(i != options.length-1){
            StringBuilder option2 = new StringBuilder(options[options.length-1]);
            option2.replace(0, option.length(), option);
            option = option2.toString();
            i++;
        } 
        if (linePattern.equals(option)) {
            return true;
        }
    }
    return false;
}
//-------------------------------------------------------
/**
 * Checks if a given line matches any of the specified exception patterns.
 * <p>
 * This method is used to determine if the input line should be treated as an exception (for example, an illegal or special instruction)
 * by comparing its initial characters with each string in the provided list of exceptions.
 * <br>
 * For each exception in the list, the method extracts from the input line the same number of characters as the exception's length,
 * and checks if this substring is present in the exceptions list.
 * <br>
 * Returns 1 if an exception is found, 0 if not, and throws a ParsingException if parameters are invalid.
 *
 * <p><b>Example usage:</b></p>
 * <pre>
 * {@code
 * ArrayList<String> exceptions = new ArrayList<>();
 * exceptions.add("popconstant");
 * int result = checkExcep("popconstant32768", exceptions);
 * // result == 1 (because "popconstant" matches the start of the line)
 * }
 * </pre>
 *
 * @param lineToCheck The input string to check for exceptions.
 * @param exceptions  The list of exception patterns to compare against.
 * @return 1 if the line matches an exception <lu><li>0 otherwise.</lu>
 * @throws ParsingException If the input string or exceptions list is null.
 */
public int checkExcep(String lineToCheck, ArrayList<String>exceptions) throws ParsingException{
    if(lineToCheck == null){
        throw new ParsingException("Error in the parameters, need put a line in 'lineToCheck'\n");
    }
    if(exceptions == null){
        throw new ParsingException("Error in the parameters, need put a array list of exceptions\n");
    }
    //check if are an exception 
    //revisar si es una exepción
    if(exceptions != null){
      for(String excep : exceptions){
            int siz = excep.length();
            String forComprobate = lineToCheck;
             forComprobate = getNchars(forComprobate, siz, false);
            if(exceptions.contains(forComprobate)){
                return 1;
            }
        }
    }
    return 0;
}
//-------------------------------------------------------
/**
 * Resolves conflicts in a flexible format pattern by removing or retaining specific characters according to the provided rules.
 * <p>
 * This method is typically used when a flexible format pattern (such as one containing OR gates or special delimiters)
 * needs to be normalized or adjusted to match a required format for further parsing or validation.
 * <br>
 * The method scans the format string, processes OR gates (e.g., '|'), and removes or keeps certain characters
 * based on the {@code forReomveAndStayInTheEndOfFormatForTheFinalResult} map, which specifies how many occurrences
 * of each character should remain at the end of the process.
 * <br>
 * The process can be stopped early if the {@code stopToAnalizeWhenFindThis} character is found.
 *
 * @param formatToResolve A mutable wrapper containing the format string to be resolved. This value will be updated in place.
 * @param formatForResolve The reference format string used to guide the resolution process.
 * @param forReomveAndStayInTheEndOfFormatForTheFinalResult A map specifying, for each character, how many occurrences should remain in the final result.
 * @param stopToAnalizeWhenFindThis If not null, the process stops when this character is found in the format string.
 * @param indicateORgateInFormatForResolve The character used to indicate an OR gate in the format string (e.g., '|').
 * @throws ParsingException If any parameter is invalid, or if the resolution process encounters an inconsistency (such as trying to retain more characters than exist).
 *
 *<p><b>Example usage:</b></p>
 * <pre>
 * {@code
 * MutableTypeData<String> format = new MutableTypeData<>("MN|W");
 * String reference = "MN|W";
 * Map<Character, Integer> keep = new HashMap<>();
 * keep.put('N', 0); // Remove all 'N'
 * keep.put('W', 1); // Keep one 'W'
 * resolveConflicts(format, reference, keep, null, '|');
 * // After execution, format.getValor() will be updated accordingly.
 * }
 * </pre>
 */
public void resolveConflicts(Parser.MutableTypeData<String> formatToResolve, String formatForResolve, Map<Character, Integer>forReomveAndStayInTheEndOfFormatForTheFinalResult,Character stopToAnalizeWhenFindThis,  Character indicateORgateInFormatForResolve) throws ParsingException{
    if(formatToResolve == null || formatForResolve == null || (formatToResolve.getValor().equals("") || formatForResolve.equals(""))){
        throw new ParsingException("Error in the parameters, need put 'foramtForResolve' and 'formatToResolve'\n");
    }
    Parser p = new Parser();
    ArrayList<ArrayList<Character>> ORgatesOptionsAppears = new ArrayList<>();
    int n2;
    ArrayList<Integer>indexOfORgatesStart = new ArrayList<>();
    try{
        //remove all the OR options and gates from the 'formatForResolve', for use this clean
        //eliminar todas las opciones OR y compuertas de el 'formatForResolve', para usar este limpio
    while((n2 = p.searchString(false, formatForResolve, indicateORgateInFormatForResolve.toString(), 0, stopToAnalizeWhenFindThis, false))!=-1){
        Parser.MutableTypeData<String> formatForResolve2 = p.new MutableTypeData<>(formatForResolve);
        ArrayList<Character> ORoptions = new ArrayList<>();
        createArrayForORLetters(formatForResolve2, ORoptions, stopToAnalizeWhenFindThis, indicateORgateInFormatForResolve);
        ORgatesOptionsAppears.add(ORoptions);
        formatForResolve = formatForResolve2.getValor();
        indexOfORgatesStart.add(n2);
    }
    if(formatToResolve.getValor().length() <= formatForResolve.length()){
        throw new ParsingException("Error in the parameters, can't proccess if the formatForResolve is equal or less than the formatToResolve\nDETAILS:Because that reomve characters for the formatToResolve\n");
    }
    
    //verified the character for mantain in the end of format are less or equal like the actuals find or put in the formatToResolve
    if(forReomveAndStayInTheEndOfFormatForTheFinalResult != null && !forReomveAndStayInTheEndOfFormatForTheFinalResult.isEmpty()){
        boolean error = false;
    for(Map.Entry<Character, Integer>entry : forReomveAndStayInTheEndOfFormatForTheFinalResult.entrySet()){
        Character charForRemove = entry.getKey();
        int toStayinTheFormat = (int)entry.getValue();
    int tempForThisVerfication = p.searchString(true, formatToResolve.getValor(), charForRemove.toString(), 0, stopToAnalizeWhenFindThis, false);  
    if(toStayinTheFormat > tempForThisVerfication){
        System.err.printf("Error in the Map, the number of characters to mantain in the end of format(%i) can't be greather than the number of this in the format(%i)\nPart in the Map: %s", toStayinTheFormat, tempForThisVerfication, entry);
        error = true;
      }
    }
    if(error) throw new ParsingException("Error: in the map\n");
  }
          
     StringBuilder newFormatToR = new StringBuilder(formatToResolve.getValor());
     int index2 = 0;
     StringBuilder newFormatToR2 = new StringBuilder();
     //start the resolve process
     //Comienza el proceso de resolver
 if(forReomveAndStayInTheEndOfFormatForTheFinalResult != null && !forReomveAndStayInTheEndOfFormatForTheFinalResult.isEmpty()){
        //create the arrays of char to delete and the total num of delete of this
        //crear los arreglos de caracteres a remover y el numero total a remover estos
        ArrayList<Character> charsToDelete = new ArrayList<>();
        ArrayList<Integer> deleteOfThisChar = new ArrayList<>();
       for(Map.Entry<Character, Integer>entry : forReomveAndStayInTheEndOfFormatForTheFinalResult.entrySet()){
        Character charForRemove = entry.getKey();
        charsToDelete.add(charForRemove);
        int toStayinTheFormat = (int)entry.getValue();
        index2 = formatForResolve.indexOf(stopToAnalizeWhenFindThis);
        int deleteNAppearsOfThisChar = p.searchString(true, newFormatToR.toString(), charForRemove.toString(),index2, stopToAnalizeWhenFindThis, false)-toStayinTheFormat;
        deleteOfThisChar.add(deleteNAppearsOfThisChar);
    }
    //get the string between the limits, like are the 'stopToAnalizeWhenFindThis', and after the num of characters after the 'formatForResolve', in other words, from the index to 'formatForResolve' original(after clean), until the index of 'stopToAnalizeWhenFindThis'
    String stringBetween = newFormatToR.substring(index2, newFormatToR.toString().indexOf(stopToAnalizeWhenFindThis));
    newFormatToR2.append(stringBetween);
    int i = 0;
    while(true){
        if(i == newFormatToR2.toString().length()) break;
       if(charsToDelete.contains(newFormatToR2.toString().charAt(i))){
        int index = charsToDelete.indexOf(newFormatToR2.toString().charAt(i));
         int deleteNAppears = deleteOfThisChar.get(index);
         if(deleteNAppears == 0) continue; //control before continue to loop for case when the deleteNAppear its 0, if the user want stay all the appears of this character in the format
         while(deleteNAppears > 0){
            int indexAc = newFormatToR2.toString().indexOf(charsToDelete.get(index));
            newFormatToR2.deleteCharAt(indexAc);
            deleteNAppears--;
         }
         i=0;
         continue;
       }
       i++;
    }
   
    String upload = newFormatToR.toString().replaceFirst(stringBetween, newFormatToR2.toString());
    newFormatToR.setLength(0);
    newFormatToR.append(upload);
  }
   formatToResolve.setValor(newFormatToR.toString());
   return;
  }catch(ParsingException e){
    throw new ParsingException(e.getMessage());
 }
}
//-------------------------------------------------------
/**
 * Generates a format pattern string for the given input, identifying the type of each character and just change this when the type change in the String, in other words, just add a letter to the output format if the character change(not is equal wich above)
 * <p>
 * The resulting format is a string where each character represent change of type of character at the corresponding in the original string:
 * <ul>0
 *   <li><b>N</b>: Number(N) (0-9)</li>
 *   <li><b>L</b>: Letter(L) (when <code>sensibleToUppercase == 0</code>)</li>
 *   <li><b>W</b>: Lowercase letter(P) (when <code>sensibleToUppercase != 0</code>)</li>
 *   <li><b>P</b>: Uppercase letter(W) (when <code>sensibleToUppercase != 0</code>)</li>
 *   <li><b>S</b>: Special character(S) (if present in <code>specialCharsForIdentify</code>, else put 'L'(other letter))</li>
 * </ul>
 * If a character is neither a letter nor a number, it is classified as <b>L</b> or <b>S</b> as appropriate.
 * <br>
 * If <code>indexWhereFindTheSpecialCharsInTheLine</code> is provided, it will be filled with the indexes where special characters are found.
 *
 * <p><b>Example:</b></p>
 * <pre>
 * {@code
 * // Suppose specials = ['~']
 * ArrayList<Character> specials = new ArrayList<>();
 * specials.add('~');
 * ArrayList<Integer> indices = new ArrayList<>();
 * String format = identifyTheFlexibleFormat("Abc123~X", 1, specials, indices);
 * // format = "PWNSP"
 * // indices will contain [6] (position of '~')
 * }
 * </pre>
 *
 * @param forGetTheFormat The input string to analyze and generate the format pattern.
 * @param sensibleToUppercase Indicates if uppercase/lowercase should be distinguished (1 for case-sensitive, 0 for not).
 * @param specialCharsForIdentify List of special characters to be marked as 'S' in the format.
 * @param indexWhereFindTheSpecialCharsInTheLine List to store the indexes of found special characters (can be null).
 * @return The generated format pattern string.
 * @throws ParsingException If the input string is null or empty.
 */
public String identifyTheFlexibleFormat(String forGetTheFormat, int sensibleToUppercase, ArrayList<Character> specialCharsForIdentify, ArrayList<Integer>indexWhereFindTheSpecialCharsInTheLine)throws ParsingException{
    if(forGetTheFormat == null || forGetTheFormat.equals("")) throw new ParsingException("Error in the parameters, need put a string for get his format\n");
    
    if(forGetTheFormat != null){
        if(forGetTheFormat.equals("")){
        throw new ParsingException("Error in the parameters, need put a string for get his format\n");
    }
    }
    StringBuilder format = new StringBuilder("");
    boolean first = true;
    int before = 0;
    for(int i = 0; i < forGetTheFormat.length(); i++){ 
        int actualCharType = identifyTypeIntOrChar(forGetTheFormat.charAt(i), sensibleToUppercase);
    if(first){
            if(actualCharType == 1) format.append("N");//add N for Number 
        else if(actualCharType == 2) format.append("L");//add L for Letter 
        else if(actualCharType == 3) format.append("W");//add W for lowercaseLetter if are sensible to uppercase
        else if(actualCharType == 4) format.append("P");//add P for upercaseLetter if are sensible to uppercase
        //if are a other character or special character
        else{
            //if the character actual are in the list of special characters
            if(specialCharsForIdentify != null){
                for(Character charActual : specialCharsForIdentify){
                    if(actualCharType == identifyTypeIntOrChar(charActual, sensibleToUppercase)){
                        format.append("S");//add EL for SpecialLetter
                        break;
                    }
                }
            }
            //if not have special character identify like Other letter
            else format.append("L");
        }
        first = false;
        before = actualCharType;
        continue;
        } 
        if(before != actualCharType){
        if(actualCharType == 1) format.append("N");//add N for Number 
        else if(actualCharType == 2) format.append("L");//add L for Letter 
        else if(actualCharType == 3) format.append("W");//add W for lowercaseLetter if are sensible to uppercase
        else if(actualCharType == 4) format.append("P");//add P for upercaseLetter if are sensible to uppercase
        //if are a other character or special character
        else{
            //if the character actual are in the list of special characters
            if(specialCharsForIdentify != null){
                if(specialCharsForIdentify.contains(forGetTheFormat.charAt(i))){
                        format.append("S");//add S for SpecialLetter
                        if(indexWhereFindTheSpecialCharsInTheLine != null) indexWhereFindTheSpecialCharsInTheLine.add(i);
                        
                    }
                    //if not have special character identify like other letter
                 else{
                     if((char)actualCharType != 'L') format.append("L");
                 }
                    
                }
            }
        before = actualCharType;
         continue;
        }
        before = actualCharType;
    }
  return format.toString();
}
//-------------------------------------------------------
/**
 * Extracts and collects the characters involved in OR gates within a format string.
 * <p>
 * This method scans the given format string (wrapped in {@code formatForIdentify}) and identifies the characters
 * that are part of OR gates, as indicated by the {@code indicateORgate} character. It adds these characters to the
 * {@code forEdit} list. The method also removes the OR gate character and the following character from the format string,
 * updating {@code formatForIdentify} with the modified string.
 *  <p>
 * <b>Important:</b> OR gates function extract the letter before and after of this.
 * </p>
 * </p>
 * 
 * <p>
 * The process stops if the {@code stopWhenFindThis} character is found in the string.
 * </p>
 *<b>Use example:</b>
 * <pre>
 * {@code
 * // Supose that the format is "A|BCL|D" y el OR es '|'
 * MutableTypeData<String> format = new MutableTypeData<>("A|BCL|D");
 * ArrayList<Character> orLetters = new ArrayList<>();
 * createArrayForORLetters(format, orLetters, null, '|');
 * //  orLetters =['A', 'B', 'L', 'D']
 * //  format.getValor()="C"
 * }
 * </pre>
 *
 * <b>Other example:</b>
 * <pre>
 * {@code
 * // If the format is "MN|W" y el OR es '|'
 * MutableTypeData<String> format = new MutableTypeData<>("MN|W");
 * ArrayList<Character> orLetters = new ArrayList<>();
 * createArrayForORLetters(format, orLetters, null, '|');
 * // orLetters= ['N', 'W']
 * // format.getValor() = "M"
 * }
 * </pre>
 * 
 * @param formatForIdentify   A mutable wrapper containing the format string to analyze and update.
 * @param forEdit             The list where the characters involved in OR gates will be collected.
 * @param stopWhenFindThis    If not null, the process stops when this character is found in the string.
 * @param indicateORgate      The character used to indicate an OR gate in the format string (e.g., '|').
 * @throws ParsingException For errors in the input parameters
 */
public void createArrayForORLetters(MutableTypeData<String> formatForIdentify, ArrayList<Character> forEdit, Character stopWhenFindThis, Character indicateORgate) throws ParsingException {
    if (formatForIdentify == null || forEdit == null || indicateORgate == null) throw new ParsingException("Must put a parameters 'forEdit', 'indicateORgate', and 'formatForIdentify'\n");
    StringBuilder sb = new StringBuilder(formatForIdentify.getValor());
    int i = 0;
    boolean foundOR = false;
    while (i < sb.length()) {
        char c = sb.charAt(i);
        if(stopWhenFindThis != null){
        if(c == stopWhenFindThis){
            formatForIdentify.setValor(sb.toString());
            return;
            }
        }
        if (c == indicateORgate) {
            if(i != 0){
                char beforeC = sb.charAt(i-1);
                if (Character.isLetter(beforeC) && !forEdit.contains(beforeC)) {
                    forEdit.add(beforeC);
                }
            }
            // Solo considerar si hay una letra después del OR y no es otro OR
            // just considerate is appears a letter after the OR and not is other OR
            if (i + 1 < sb.length() && sb.charAt(i + 1) != indicateORgate) {
                char next = sb.charAt(i + 1);
                // Solo considerar si es letra y no está repetida
                if (Character.isLetter(next) && !forEdit.contains(next)) {
                    forEdit.add(next);
                }
                // Eliminar el OR y la letra siguiente
                //delete OR and the next letter
                sb.delete(i, i + 2);
                foundOR = true;
                // No incrementar i porque la cadena se ha reducido
                //Don't increment i because the string has be reduced
                i=1;
                continue;
            } else {
                // Si el siguiente no es letra o es otro OR, solo eliminar el OR
                // if the next character not is a letter or is other OR, just remove the OR
                sb.deleteCharAt(i);
                continue;
            }
        } else if (foundOR && i + 1 < sb.length() && Character.isLetter(c) && Character.isLetter(sb.charAt(i + 1))) {
            // Si hay dos letras consecutivas después de un OR, se detiene el proceso de OR
            // Solo considerar si es letra y no está repetida
            //if two letters are consecutively after the OR, stop the OR proccessing
            //Just considrate if is a letter not put before in the array
                if (Character.isLetter(c) && !forEdit.contains(c)) {
                    forEdit.add(c);
                }
            break;
        }
        i++;
    }
    // Actualizar el valor en formatForIdentify con la cadena resultante
    //upload the value of the formarForIdentify with the result string
    formatForIdentify.setValor(sb.toString());
}
//-------------------------------------------------------
/**
 * This method create a numerical 'ID' or <code>fingerPrint</code> more precisely, <code> of the 'structure' of the 'String'</code>, in other words, the <code>types of characters and his position</code> <b>NOT its lieterals characters</b> 
 <p><code>IMPORTANT</code>: This uses the methods 'log2' and 'identifyTypeIntOrChar' to generate the fingerprint.</p>
<p>This <code>fingerprint</code> is created through mathematical operations, but it does <b>not guarantee 100% uniqueness</b>. This is because UNICODE characters can have very large values, which may cause collisions between fingerprints of different strings with different structures.</p>
<p>Also, the <code>math operations are deterministic</code> (meaning they always produce the same result for the same input)</p>
 * @param FormatExample Is the String wich convert or 'identify' or get and return his 'figerprint'
 * @param sensibleToUppercase Flag for indicate if are sensible to uppercase letters or not, when sensibleToUppercase != 0 this are means like are sensible to uppercase
 * @return Double. The fingerprint is a float point number
 */
public double identifyTheStrictFormat(String FormatExample, int sensibleToUppercase){
 double n = 0; //Get the format in a integer number
            //Obtener el formato en un número entero

 //iterate until the end of the FormatExample
 //Recorrer la cadena FormatExample
 int characterValueBefore = 1;

        for (int i = 0; i < FormatExample.length(); i++) {
        char actualChar = FormatExample.charAt(i);
            int characterValue = identifyTypeIntOrChar(actualChar, sensibleToUppercase);
            // sum +1 because this function for convert pairs nums to inpairs nums, and inpairs nums to pairs nums, and the multiply help, because if the num are pairs or inpairs affected, because pair*pair=pair and disapper log(1)=0
            // suma +1 porque esto funciona para convertir numeros pares a impares y viceversa, y la multiplicacion ayuda, porque si el numero es par o no par afecta, porque par*par=par, y eevita log(1)=0
            if(i == 0) n+=log2(characterValue+1); 
            else n+= ((log2(characterValue + 1) / log2(characterValueBefore+(characterValue+1))) * i) + log2(characterValue + 1);//f(x,y) where x = i and y = characterValue f(x,y)= ((log(Y+1)/log(Y(X-1)+(Y+1))*X)+log(Y+1) 
            characterValueBefore = characterValue;
            }
 return n;
}
//-------------------------------------------------------
/**
 * This method return the <code>log in base 2</code> of an integer
 * @param getLogOf Integer for get his log in base 2
 * @return <code>Double</code> with the value of the log of the integer
 */
public double log2(int getLogOf){
    return Math.log(getLogOf)/Math.log(2);
}
//-------------------------------------------------------
/**
 * This method clasifies the character, and return his numeric representation or equivalence <code>depend of the type of character</code> 'not the literal character', and change his behavior depended of the parameter 'sensibleToUppercase'
 * @param actual An Character for clasified, in other words, know his type and classifie depend this, no his literal value
 * @param sensibleToUppercase An int like indicate if the Uppercase are important, <code>when is != 0 is sensibleToUppercase</code>
 * <p><b>Example</b></p>
 * <pre>
 * <code>
 * Character forIdentiyHisType = 'S';
 * int sensible = identifyTypeIntOrChar(forIdenifyHisType, 1);
 * //now sensible = 4, because 'S' is an Uppercase and are sensibleToUppercase
 * //But if don't sensibleToUppercase
 * int notSensible = identifyTypeIntOrChar(forIdentifyHisType, 0); //now notSensible = 2, because is a letter and not import if its Uppercase or Lowercase
 * </pre>
 * </code>
 * <p><b>Example of use for UNICODE characters</b></p>
 * <pre>
 * <code>
 * Character forIdentifyHisType = '♫';
 * //for this cases of UNICODE characteres don't import the flag of 'sensibleToUppercase', because ever return the UNICODE value of this character
 * int UNICODE = identifyTypeIntOrChar(forIdentifyHisType, 0);//now UNICODE = 9835, because this si the UNICODE value of this character
 * </pre>
 * </code>
 * @return 1 For <code>digit</code> characters <ul>
 <li>2 For letters when <code>not is sensibleToUppercase</code></li>
 <li>3 For <code>Lowercase</code> letters when <code>is sensibleToUppercase</code></li>
 <li>4 For <code>Uppercase</code> letters when <code>is sensibleToUPPercase</code></li>
 <li><code>UNICODE VALUE</code> when are a character <code>different to digit and a letter</code></li>
 </ul>
 */
public int identifyTypeIntOrChar(Character actual, int sensibleToUppercase){
    //If character actual is a number
    //Si el caracter actual es un número
    if(actual >= '0' && actual <= '9'){
        return 1;
    }
    //else if character actual is a letter and not is sensible to mayus
    //Si el caracter actual es una letra y no es sensible a las mayusculas
    else if((actual >= 'a' && actual <='z' || actual >= 'A' && actual <= 'Z') && sensibleToUppercase == 0){
     return 2;
    }
    //else if character actual is a letter and is sensible to mayus
    //si es sensible a mayusculas y es una letra
    else if(sensibleToUppercase != 0 && actual >= 'a' && actual <= 'z'){
        return 3;
    }
    else if(sensibleToUppercase != 0 && actual >= 'A' && actual <= 'Z'){
        return 4;
    }
    //If the character is other reutrn de UNICODE integer
    //Si no es ninguno de los anteriores devolver el entero de el UNICODE
    else{
        return (int)actual;
    }
}
//END THE PROCESS OF PARSING SINTAX (TERMINA EL PROCESO DE ANÁLISIS SINTÁCTICO)-----------------------------------------------------------------

}
