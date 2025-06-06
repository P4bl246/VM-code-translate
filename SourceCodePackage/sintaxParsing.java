
import AuxClass.Parser.*; // Import the Parser class from the AuxCLass.Parser package
                            // Importar la clase Parser del paquete AuxCLass.Parser
                            import AuxClass.Parser.Parser.MutableTypeData;

import java.util.ArrayList; // Import the ArrayList class from the java.util package
                           // Importar la clase ArrayList del paquete java.util
import java.util.HashMap;
import java.util.Map;
import java.io.*;

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
public int parser_Sintaxis(String File_in) {
    System.out.println("\nSTARTS THE SINTAX PARSING PROCESS\n");
    int n;
    Parser parserf = new Parser();
    parserf.NumLines(File_in);
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
    ArrayList<Character>specials = new ArrayList<>();
    specials.add('^');
    Map<Character, Integer> removeThis = new HashMap<>();
        removeThis.put('N', 0);
        removeThis.put('L', 0);
        removeThis.put('P', 0);
        removeThis.put('W', 0);
    CommandArgRule argsCommands = new CommandArgRule(hashTablePOP_PUSH, argsTable, 8, 8, "pushconstant-32768", "popthis0", null, excep, withouthPattern, withoutPatternButWithivalue, "W|N|L|PSN", null, specials, true, withoutPatternButWithivalue, '|', '^', removeThis, 'S', false);
    HashTablePreDet(); // Create the hash table with the pre-determined elements
                       // Crear la tabla hash con los elementos predefinidos
    while(true) {
        nLine = parserf.get(readFilein, Parser.Readmode.NumberLine, ' ', null, null);
        if(nLine.equals(""))break;
        Parser.MutableTypeData<Boolean> contains = parserf.new MutableTypeData<>(false);
        line = parserf.get(readFilein, Parser.Readmode.CompletelyLine, '0', null, contains);
        if(line.equals("") && !contains.getValor()) break;
        Parser.MutableTypeData<Integer> i =  parserf.new MutableTypeData<>(0);
        n = CompareWithHashTable(line, nLine, 3, null, TableHash.Arithmetics, false, i);
        if (n != 0){
          n= CompareWithHashTable(line, nLine, 3, null, TableHash.Booleans, false, i);
          
          if (n != 0) {
            //Create a mutable value variable for upload his value in call in diferents functions
            //Crear una vairalbe de valor mutable para actualizar su valor en las llamadas a otras funciones 
            Parser.MutableTypeData<Integer>LengthOfCommand = parserf.new MutableTypeData<>(0);
            Parser.MutableTypeData<Integer>LengthOfArg = parserf.new MutableTypeData<>(0);
          n = CompareCommandsWithArg(line, nLine, argsCommands, 1, null, LengthOfCommand, LengthOfArg);
          if (n < 0){
            System.err.printf("Error in the line %s\nDETAILS: Wrong Sintaxis\n", nLine);
             return -1;
            }
             if(n != 2 && n!=3){
                String arg = line.substring(LengthOfCommand.getValor(), (LengthOfArg.getValor()+LengthOfCommand.getValor()));
            if(arg.equals("pointer")){
             arg = line.substring(((LengthOfArg).getValor()+LengthOfCommand.getValor()), line.length());
             if(!(arg.equals("0") || arg.equals("1"))){
                System.err.println("Error in the line "+nLine+" the 'pointer' argument can has just 2 values '0'(THIS) or '1'(THAT)\n");
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
    }
    parserf.RemoveNLine(File_in, ' ');
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
  public void HashTablePreDet(){
    ArrayList<String> NewElements = new ArrayList<>();
    //Arithmetic operations
    // Operaciones aritméticas
    NewElements.add("add");
    NewElements.add("sub"); 
    NewElements.add("neg");
    CreateHashTable(null, 1, NewElements, null, TableHash.Arithmetics);
    NewElements.clear();
    //Boolean operations
    // Operaciones booleanas
    NewElements.add("or");
    NewElements.add("and");
    NewElements.add("not");
    NewElements.add("eq");
    NewElements.add("lt");
    NewElements.add("gt");
    CreateHashTable(null, 1, NewElements, null, TableHash.Booleans);
    NewElements.clear();
    //POP and PUSH comands
    // comandos POP y PUSH
    NewElements.add("pop");
    NewElements.add("push");
    NewElements.add("label");
    NewElements.add("goto");
    NewElements.add("if-goto");
    NewElements.add("function");
    CreateHashTable(null, 1, NewElements, null, TableHash.POP_PUSH);
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
    CreateHashTable(null, 1, args, argsTable, null);
  }
//------------------------------------------------------
// This method is used to create a hash table with the pre-determined elements
// Este método se utiliza para crear una tabla hash con los elementos predefinidos
public enum TableHash{
    Arithmetics,
    Booleans,
    POP_PUSH
}

public void CreateHashTable(String element, int SimpleOrMultiples, ArrayList<String> NewElements, HashMap<String, Integer> hashTable, TableHash AddToPreDefined) {
    if(hashTable == null && AddToPreDefined == null) {
        System.err.println("Error: Hash table is null and AddToPreDefined is null");
        return;
    }
    else if(element == null && NewElements == null) {
        System.err.println("Error: Element and NewElements are null");
        return;
    }
    int hash;
    // If SimpleOrMultiples is not 0, create a hash table for multiple elements
    // Si SimpleOrMultiples no es 0, crea una tabla hash para múltiples elementos
    if (SimpleOrMultiples != 0) {
        if(NewElements == null){
            System.err.printf("Error: NewElements are null and you select 'Multiples'(SimpleOrMultiple: %d)", SimpleOrMultiples);
        return;
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
            System.err.printf("Error: element are null and you select 'Single'(SimpleOrMultiple: %d)", SimpleOrMultiples);
        return;
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
public String GetNchars(String input, int n) {
    if (input == null) {
        System.out.println("Error: input string is null");
        return null;
    }

    if (n < 0) {
        System.out.println("Error: can't use negative numbers to get characters");
        return null;
    }

    if (n > input.length()) {
        System.out.println("The number of characters to get is greater than the length of the input string\nThe code will be executed with the length of the input string\n");
        n = input.length();
    }

    return input.substring(0, n);
}
//-------------------------------------------------------
public int CompareWithHashTable(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, TableHash CompareWithPreDefined, boolean withArgs, MutableTypeData<Integer>iEspecial) {
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
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTablePOP_PUSH, iEspecial, true);
                break;
            case Arithmetics:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableArith, iEspecial, false);
                break;
            case Booleans:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableBool, iEspecial, false);
               break;
            default:
            n = -1;
                break;
        }
        if(n != 0) return -1;
        else return 0;
    }
    else if(hashTableForCompare != null && CompareWithPreDefined == null){
       int n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableForCompare, iEspecial, withArgs);
       if(n != 0) return -1;
       else return 0;
    }
    else{
        int n;
        n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableForCompare, iEspecial, withArgs);
        if(n != 0){
            switch (CompareWithPreDefined) {
            case POP_PUSH:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTablePOP_PUSH, iEspecial, true);
                break;
            case Arithmetics:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableArith, iEspecial, false);
                break;
            case Booleans:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableBool, iEspecial, false);
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
public int CompareTableImplement(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, MutableTypeData<Integer> iEspecial, boolean withArgumets){
    if(line != null){
    // Get the first N characters of the input string
        // Obtener los primeros N caracteres de la cadena de entrada
        String element = GetNchars(line, CharsNumToCompare_SRING_MORE_LONG);
        if(element == null) return -1;
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
                element = GetNchars(element, sub);             
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
public int CompareCommandsWithArg(String line, String nLine, CommandArgRule ArgsInputRule , int sensibleToUppercase, ArrayList<Character> Delimiters, Parser.MutableTypeData<Integer>LengthOfCommand, Parser.MutableTypeData<Integer>LengthOfArg){

    //Verifica que no se usen ambos tipos de formato al mismo tiempo
    //Check if is use both types of format to the same time
    if (ArgsInputRule.multipleFormatsPatterns != null && ArgsInputRule.formatPatternMostLong != null || (ArgsInputRule.multipleFormatsPatterns == null && ArgsInputRule.formatPatternMostLong == null)) {
        System.err.println("Error\nDETAILS: You can only select one: 'multipleFormatsPatterns' or 'formatPatternMostLong'\n");
        return -1;
    }
    if(LengthOfCommand == null || LengthOfArg == null){
        System.err.println("Error: Need put a arguemnts in the parameters 'LenghtOfCommand' and 'LengthOfArg'\n");
        return -1;
    }

    // Validación para múltiples formatos
    //If select multiples format, check this
    if (ArgsInputRule.multipleFormatsPatterns != null) {
        for (HashMap.Entry<String, String> entry : ArgsInputRule.multipleFormatsPatterns.entrySet()) {
            String lessLong = entry.getKey();
            String mostLong = entry.getValue();
            if (lessLong == null || mostLong == null) {
                System.err.printf("Error in format pattern: formatPatternMostLong: %s, formatPatternLessLong: %s\nDETAILS: All formats must define both a most long and less long pattern.\n", mostLong, lessLong);
                return -1;
            }
        }
    }

    // Validación para formato único
    //Check if is just one format
    if ((ArgsInputRule.formatPatternMostLong != null && ArgsInputRule.formatPatternLessLong == null) ||
        (ArgsInputRule.formatPatternLessLong != null && ArgsInputRule.formatPatternMostLong == null)) {
        System.err.printf("Error in format pattern:\nMost long: %s\nLess long: %s\nDETAILS: Both formatPatternMostLong and formatPatternLessLong must be defined.\n",
                          ArgsInputRule.formatPatternMostLong, ArgsInputRule.formatPatternLessLong);
        return -1;
    }
    //Validacion para formatos flexibles
    //Check for flexibles formats patterns
    if(ArgsInputRule.commandsWithFlexiblePattern != null && (ArgsInputRule.formatPatternFlexible == null && ArgsInputRule.multiplesFlexiblesFormatsPatterns == null)){
        System.err.println("Error in the format flexible commands arguments\nDETAILS: If you have commands with flexible patterns, need put something in 'formatpatternflexible' or 'multiplesFlexiblesFormatsPatterns'\n");
        return -1;
    }
    if(ArgsInputRule.commandsWithFlexiblePattern != null && (ArgsInputRule.commandsWithFlexiblePattern != null && ArgsInputRule.multiplesFlexiblesFormatsPatterns != null)){
        System.err.println("Error in the flexibles formats patterns arguments\nDETAILS: Just can select one theme for use\n");
        return -1;
    }


    Parser p = new Parser();
    Parser.MutableTypeData<Boolean>coincidence = p.new MutableTypeData<>(false);
    String temp = "";
    //validar si es un tipo especial de comando sin formato
    //Check if its a comand withouth format
    boolean isWithoutPattern = false;
    if(ArgsInputRule.commandsWithoutPatterns != null){
        String line2 = line.trim();
        CompareTableImplement(line2, nLine, ArgsInputRule.commandLength, ArgsInputRule.commandTable, LengthOfCommand, true);
        if(ArgsInputRule.commandsWithoutPatterns.contains(line2.substring(0, LengthOfCommand.getValor()))) isWithoutPattern = true;
    }
    //validar si es un tipo especial de comando con formato flexible
    //check if its a speacial command with flexible format
    boolean isFlexiblePattern = false;
    if(ArgsInputRule.commandsWithFlexiblePattern != null && !isWithoutPattern){
        String line2 = line.trim();
        CompareTableImplement(line2, nLine, ArgsInputRule.commandLength, ArgsInputRule.commandTable, LengthOfCommand, true);
        if(ArgsInputRule.commandsWithFlexiblePattern.contains(line2.substring(0, LengthOfCommand.getValor()))) isFlexiblePattern = true;
    }
    if(isFlexiblePattern && isWithoutPattern){
        String line2 = line.trim();
        temp = line2.substring(0, LengthOfCommand.getValor());
        System.err.printf("Error in the command %s\nDETAILS:The command can't match in both types of commands 'commandsWithoutPatterns' and 'commandsWithFlexiblePattern'\n",temp);
        return -1;
    }
    // Si hay múltiples formatos, validar si la línea entra en alguno
    //Check for multiples formats the line
    if(!isFlexiblePattern && !isWithoutPattern && ArgsInputRule.multipleFormatsPatterns != null){
         if(checkStrictFormat(line, ArgsInputRule.multipleFormatsPatterns, null, null, null, coincidence, sensibleToUppercase) != 0) return -1;
       if(!coincidence.getValor()){
        System.err.printf("Error in the line %s\nDETAILS:Error in the format of line not match in the range of formats '%s'\n", nLine, ArgsInputRule.multipleFormatsPatterns);
        return -1;
     }
    }
    // Validar con formato único
    //Check for singular format the line
    if (ArgsInputRule.formatPatternMostLong != null && ArgsInputRule.formatPatternLessLong != null && !isWithoutPattern && !isFlexiblePattern){
         if(checkStrictFormat(line, null, ArgsInputRule.formatPatternMostLong, ArgsInputRule.formatPatternLessLong, coincidence, null, sensibleToUppercase) != 0) return -1;
     if(!coincidence.getValor()){
        System.err.printf("Error in the line %s\nDETAILS:Error in the format of line not match in the range of formats '%s' and '%s'\n", nLine, ArgsInputRule.formatPatternMostLong, ArgsInputRule.formatPatternLessLong);
        return -1;
     }
    }
    ArrayList<Integer>indexSpecialChars = new ArrayList<>();
    if(ArgsInputRule.formatPatternFlexible != null && isFlexiblePattern){
        Parser.MutableTypeData<String> formatOfline = p.new MutableTypeData<>("");
        Parser.MutableTypeData<String> formatOfPattern = p.new MutableTypeData<>(ArgsInputRule.formatPatternFlexible);
       if(checkFlexibleFormat(line, formatOfline, formatOfPattern, null, ArgsInputRule.formatPatternFlexible, ArgsInputRule.specialCharsForIdentifyInTheFlexibleFormat,ArgsInputRule.ORgateForFlexible, coincidence, null, sensibleToUppercase, ArgsInputRule.thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns, indexSpecialChars, ArgsInputRule.theLineAreInTheFormatExpected) != 0) return -1;
        temp = line.substring(0, LengthOfCommand.getValor());
       if(!coincidence.getValor() && ArgsInputRule.commandsWithFlexiblePatternForResultConflicts.contains(temp)){ 
        
        if(resolveConflicts(formatOfline, formatOfPattern.getValor(),ArgsInputRule.mapForFlexible, ArgsInputRule.stopForFlexibleForConflicts, ArgsInputRule.ORgateForFlexible)!= 0) return -1;
       else{
       if(checkFlexibleFormat(formatOfline.getValor(), formatOfline, formatOfPattern, null, ArgsInputRule.formatPatternFlexible, ArgsInputRule.specialCharsForIdentifyInTheFlexibleFormat, ArgsInputRule.ORgateForFlexible, coincidence, null, sensibleToUppercase, ArgsInputRule.thePatternsAreBeInTheFormatExpectedOrNeedBeConvert_ForFlexiblePatterns, indexSpecialChars, true) != 0) return -1;
       if(!coincidence.getValor()){
       System.err.printf("Error in the line %s\nDETAILS:Error in the format not pass the check for flexibles formats\nFormat of Line: %s\nFormat expected: %s\n", nLine, formatOfline.getValor(), formatOfPattern.getValor());
        return -1;
          }
       } 
    }
    else if(!coincidence.getValor()&& !ArgsInputRule.commandsWithFlexiblePatternForResultConflicts.contains(temp)){
       System.err.printf("Error in the line %s\nDETAILS:Error in the format not pass the check for flexibles formats\nFormat of Line: %s\nFormat expected: %s\n", nLine, formatOfline.getValor(), formatOfPattern.getValor());
        return -1;
       }
    }
    boolean without = false;
    boolean flexible = false;
    //check if are an exception (ilegal instruction)
    //revisar si es una exepción(instrucción no perimitda)
    if(ArgsInputRule.exceptions != null){
        int r = checkExcep(line, ArgsInputRule.exceptions);
        if(r == -1) return -1;
      if(r == 1){
        System.out.printf("Error in the line %s\nThe instruction is an ilegal instruction find in this list %s\n", nLine, ArgsInputRule.exceptions);
        return -1;
      }
    }
    String newLine = line;
    // Eliminar delimitadores
    //Delete delimiters
    if(Delimiters != null){
    StringBuilder WithoutDel = new StringBuilder();
    for (char c : line.toCharArray()) {
        if (!Delimiters.contains(c)) WithoutDel.append(c);
    }
    newLine = WithoutDel.toString();
}
    // Comparar comando
    // Compare the comand

    LengthOfCommand.setValor(0);
    if (CompareTableImplement(newLine, nLine, ArgsInputRule.commandLength, ArgsInputRule.commandTable, LengthOfCommand, true) != 0)  return -1;
    if(ArgsInputRule.commandsWithoutPatterns.contains(newLine.substring(0, LengthOfCommand.getValor()))) without = true;
    if(ArgsInputRule.commandsWithFlexiblePattern.contains(newLine.substring(0, LengthOfCommand.getValor()))) flexible = true;
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
    if (CompareTableImplement(remainingNewLine, nLine, ArgsInputRule.argLength, ArgsInputRule.argTable, LengthOfArg, true) != 0) {
        return -1;
    }

    return 0;
}
//-------------------------------------------------------
public int checkStrictFormat(String lineToCheck, Map<String, String>multiplesPatterns, String singlePatternMostLong, String singlePatternLessLong, Parser.MutableTypeData<Boolean>matchWithSingle, Parser.MutableTypeData<Boolean>matchWithMultiples, int sensibleToUppercase){
    if(lineToCheck == null){
        System.err.println("Error in the parameters, need put the line to check in 'lineToCheck' parameter");
        return -1;
    }
    if(multiplesPatterns == null && (singlePatternMostLong == null || singlePatternLessLong == null)){
        System.err.println("Error in thhe parameter, need put something in the 'multiplesPatterns' or 'singlePatternMostLong and singlePatternLessLong");
        return -1;
    }
    // Si hay múltiples formatos, validar si la línea entra en alguno
    //Check for multiples formats the line
    boolean coincidence = false;
    if (multiplesPatterns != null) {
        double linePattern= identifyTheStrictFormat(lineToCheck, sensibleToUppercase);
        for (HashMap.Entry<String, String> entry : multiplesPatterns.entrySet()) {
            double mostLong = identifyTheStrictFormat(entry.getKey(), sensibleToUppercase);
            double lessLong = identifyTheStrictFormat(entry.getValue(), sensibleToUppercase);
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

        if (!(linePattern >= lessLong && linePattern <= mostLong)) {
            if(matchWithSingle != null)matchWithSingle.setValor(false);
        }
        else if(matchWithSingle != null) matchWithSingle.setValor(true);
    }
    return 0;
}
//-------------------------------------------------------
public int checkFlexibleFormat(String lineToCheck, Parser.MutableTypeData<String>formatLine, Parser.MutableTypeData<String>formatOfPattern, ArrayList<String> multiplesPatterns, String singlePattern, ArrayList<Character> specialsCharactersForIdentify, Character indicateORgateInThePatterns, Parser.MutableTypeData<Boolean> matchWitSingle, Parser.MutableTypeData<Boolean> matchWithMultiples, int sensibleToUppercase, boolean thePatternsAreTheFormatExpectedOrNeedBeConvert, ArrayList<Integer>indexWhereFindTheSpecialCharsInTheLine, boolean lineToCheckHasBeFormat) {
    //Check the inputs
    //Revisar las entradas
    if (lineToCheck == null) {
        System.err.println("Error in the parameters, need put the line to check in 'lineToCheck' parameter");
        return -1;
    }
    if (multiplesPatterns == null && singlePattern == null) {
        System.err.println("Error in the parameter, need put something in the 'multiplesPatterns' or 'singlePattern'\n");
        return -1;
    }
    boolean multiples = false;
    if(multiplesPatterns != null){
        if( multiplesPatterns.isEmpty() ){
    System.err.println("Error in the parameter, need put something in the 'multiplesPatterns' or 'singlePattern'\n");
        return -1;
    }
    multiples = true;
    }
    if(singlePattern.equals("") && !multiples){
    System.err.println("Error in the parameter, need put something in the 'multiplesPatterns' or 'singlePattern'\n");
        return -1;
    }
    // Validar que no haya conflicto entre delimitadores especiales y OR
    //Check if have conflicts between special chars and OR character delimiter
    if (specialsCharactersForIdentify != null && indicateORgateInThePatterns != null) {
            if (specialsCharactersForIdentify.contains(indicateORgateInThePatterns)) {
                System.err.println("Error: Conflict between special delimiters and OR gate characters: '" + indicateORgateInThePatterns + "'");
                return -1;
            }
    }
    //Verificar que el delimitador de OR no se alguna letra que se use en la creacion de el patron flexible
    //Verified that delimiter for OR gate not be some letter or character use in the creation of the flexible creation
    if(thePatternsAreTheFormatExpectedOrNeedBeConvert && (indicateORgateInThePatterns == 'N' || indicateORgateInThePatterns== 'L') && sensibleToUppercase == 0){
        System.err.println("Error: Conflict between indicate OR gate and characters of the format, change for other different to 'N' and 'L'\n");
        return -1;
    }
    if(thePatternsAreTheFormatExpectedOrNeedBeConvert && (indicateORgateInThePatterns == 'N' || indicateORgateInThePatterns== 'L'||
    indicateORgateInThePatterns == 'P' || indicateORgateInThePatterns == 'W') && sensibleToUppercase != 0){
        System.err.println("Error: conflict between indicate OR gate and characters of the format, change for other different to 'N', 'L', 'P' and 'W'\n");
      return -1;
    }
     
    boolean coincidence = false;
    //Chekc for multiples pattern
    //Revision para patrones múltiples
    if (multiplesPatterns != null) {
        //make and get the format for the line to check
        //hacer y obtener el formato para la linea a revisar
        String linePattern = "";
        if(!lineToCheckHasBeFormat)identifyTheFlexibleFormat(lineToCheck, sensibleToUppercase, specialsCharactersForIdentify, indexWhereFindTheSpecialCharsInTheLine);
        else linePattern = lineToCheck;
        if(linePattern.equals("")) return -1; //error 
        if(formatLine != null) formatLine.setValor(linePattern);//upload the format of line //actualizar el formato de la linea
        //get the pattern for compare
        //obtener el patron para comparar
        for (String pattern : multiplesPatterns) {
            boolean match;
            //if the pattern need be convert to a format (because is an example)
            //si el patron nesecita ser convertido a el formato (porque es un ejemplo)
            if (!thePatternsAreTheFormatExpectedOrNeedBeConvert) {
                pattern = identifyTheFlexibleFormat(pattern, sensibleToUppercase, specialsCharactersForIdentify, indexWhereFindTheSpecialCharsInTheLine);
                if(pattern.equals("")) return -1;
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
        if(linePattern.equals(null)) return -1;
        if(formatLine != null) formatLine.setValor(linePattern);
        String pattern = singlePattern;
        boolean match;
        if (!thePatternsAreTheFormatExpectedOrNeedBeConvert) {
            pattern = identifyTheFlexibleFormat(singlePattern, sensibleToUppercase, specialsCharactersForIdentify, indexWhereFindTheSpecialCharsInTheLine);
            if(pattern.equals(null)) return -1;
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
    return 0;
}
//-------------------------------------------------------
/**
 * Compara un patrón con OR gates (por ejemplo, AL|NS, entonces sera "ALS" o "ANS") contra un formato de línea.
 * Devuelve true si hay coincidencia con alguna de las opciones separadas por OR.
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
public int checkExcep(String lineToCheck, ArrayList<String>exceptions){
    if(lineToCheck == null){
        System.err.println("Error in the parameters, need put a line in 'lineToCheck'\n");
        return -1;
    }
    if(exceptions == null){
        System.err.println("Error in the parameters, need put a array list of exceptions\n");
        return -1;
    }
    //check if are an exception 
    //revisar si es una exepción
    if(exceptions != null){
      for(String excep : exceptions){
            int siz = excep.length();
            String forComprobate = lineToCheck;
             forComprobate = GetNchars(forComprobate, siz);
            if(exceptions.contains(forComprobate)){
                return 1;
            }
        }
    }
    return 0;
}
//-------------------------------------------------------
public int resolveConflicts(Parser.MutableTypeData<String> formatToResolve, String formatForResolve, Map<Character, Integer>forReomveAndStayInTheEndOfFormatForTheFinalResult,Character stopToAnalizeWhenFindThis,  Character indicateORgateInFormatForResolve){
    if(formatToResolve == null || formatForResolve == null || (formatToResolve.getValor().equals("") || formatForResolve.equals(""))){
        System.err.println("Error in the parameters, need put 'foramtForResolve' and 'formatToResolve'\n");
        return -1;
    }
    if(formatToResolve.getValor().length() <= formatForResolve.length()){
        System.err.println("Error in the parameters, can't proccess if the formatForResolve is equal or greather than the formatToResolve\nDETAILS:Because that reomve characters for the formatToResolve\n");
        return -1;
    }
    Parser p = new Parser();
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
    if(error) return -1;
  }
    ArrayList<ArrayList<Character>> ORgatesOptionsAppears = new ArrayList<>();
    int n2;
    ArrayList<Integer>indexOfORgatesStart = new ArrayList<>();
    while((n2 = p.searchString(false, formatForResolve, indicateORgateInFormatForResolve.toString(), 0, stopToAnalizeWhenFindThis, false))!=-1){
        Parser.MutableTypeData<String> formatForResolve2 = p.new MutableTypeData<>(formatForResolve);
        ArrayList<Character> ORoptions = new ArrayList<>();
        createArrayForORLetters(formatForResolve2, ORoptions, stopToAnalizeWhenFindThis, indicateORgateInFormatForResolve);
        ORgatesOptionsAppears.add(ORoptions);
        formatForResolve = formatForResolve2.getValor();
        indexOfORgatesStart.add(n2);
    }
          
     StringBuilder newFormatToR = new StringBuilder(formatToResolve.getValor());
     int index2 = 0;
     StringBuilder newFormatToR2 = new StringBuilder();
 if(forReomveAndStayInTheEndOfFormatForTheFinalResult != null && !forReomveAndStayInTheEndOfFormatForTheFinalResult.isEmpty()){
        
        
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
   
    String upload = newFormatToR.toString().replace(stringBetween, newFormatToR2);
    newFormatToR.setLength(0);
    newFormatToR.append(upload);
  }
   formatToResolve.setValor(newFormatToR.toString());
    return 0;
}
//-------------------------------------------------------
public String identifyTheFlexibleFormat(String forGetTheFormat, int sensibleToUppercase, ArrayList<Character> specialCharsForIdentify, ArrayList<Integer>indexWhereFindTheSpecialCharsInTheLine){
    if(forGetTheFormat == null || forGetTheFormat.equals("")){
        System.err.println("Error in the parameters, need put a string for get his format\n");
        return null;
    }
    if(forGetTheFormat != null){
        if(forGetTheFormat.equals("")){
        System.err.println("Error in the parameters, need put a string for get his format\n");
        return null;
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
                for(Character charActual : specialCharsForIdentify){
                    if(actualCharType == identifyTypeIntOrChar(charActual, sensibleToUppercase)){
                        format.append("S");//add EL for SpecialLetter
                        if(indexWhereFindTheSpecialCharsInTheLine != null) indexWhereFindTheSpecialCharsInTheLine.add(i);
                        break;
                    }
                }
            }
            //if not have special character identify like other letter
            else{
                     if((char)actualCharType != 'L') format.append("L");
                 }
         continue;
        }
        before = actualCharType;
    }
  }
  return format.toString();
}
//-------------------------------------------------------
public void createArrayForORLetters(MutableTypeData<String> formatForIdentify, ArrayList<Character> forEdit, Character stopWhenFindThis, Character indicateORgate) {
    if (formatForIdentify == null || forEdit == null || indicateORgate == null) return;
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
            if (i + 1 < sb.length() && sb.charAt(i + 1) != indicateORgate) {
                char next = sb.charAt(i + 1);
                // Solo considerar si es letra y no está repetida
                if (Character.isLetter(next) && !forEdit.contains(next)) {
                    forEdit.add(next);
                }
                // Eliminar el OR y la letra siguiente
                sb.delete(i, i + 2);
                foundOR = true;
                // No incrementar i porque la cadena se ha reducido
                i=1;
                continue;
            } else {
                // Si el siguiente no es letra o es otro OR, solo eliminar el OR
                sb.deleteCharAt(i);
                continue;
            }
        } else if (foundOR && i + 1 < sb.length() && Character.isLetter(c) && Character.isLetter(sb.charAt(i + 1))) {
            // Si hay dos letras consecutivas después de un OR, se detiene el proceso de OR
            // Solo considerar si es letra y no está repetida
                if (Character.isLetter(c) && !forEdit.contains(c)) {
                    forEdit.add(c);
                }
            break;
        }
        i++;
    }
    // Actualizar el valor en formatForIdentify con la cadena resultante
    formatForIdentify.setValor(sb.toString());
}
//-------------------------------------------------------
private double identifyTheStrictFormat(String FormatExample, int sensibleToUppercase){
double n = 0; //Get the format in a integer number
            //Obtener el formato en un número entero

//iterate until the end of the FormatExample
//Recorrer la cadena FormatExample
int characterValueBefore = 0;
        for (int i = 0; i < FormatExample.length(); i++) {
        char actualChar = FormatExample.charAt(i);
            int characterValue = identifyTypeIntOrChar(actualChar, sensibleToUppercase);
            double merchCharacter = i/((i*2)+1); //para dividir el caracter actual por su indice multiplicado por 2, +1
            double x = (i+((characterValue*2)+characterValueBefore))*merchCharacter;
            //in both operations sum +1 because this function for convert pairs nums to inpairs nums, and inpairs nums to pairs nums, and the multiply help, because if the num are pairs or inpairs affected, because pair*pair=pair
            //en ambas operaciones suma +1 porque esto funciona para convertir numeros pares a impares y viceversa, y la multiplicacion ayuda, porque si el numero es par o no par afecta, porque par*par=par
             if(characterValue == 1 || characterValue == 2 || characterValue == 3 || characterValue == 4)n+= ((((i+1)*i+x)*merchCharacter)*characterValue)+1; 
             else n += (((i+1)*i+x)*merchCharacter)+1;//integral f(x)*dx == ((i+1)*i+x)*dx)
            characterValueBefore = characterValue;
            }
 return n;
}
//-------------------------------------------------------
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
