
import AuxClass.Parser.*; // Import the Parser class from the AuxCLass.Parser package
                            // Importar la clase Parser del paquete AuxCLass.Parser

import java.util.ArrayList; // Import the ArrayList class from the java.util package
                           // Importar la clase ArrayList del paquete java.util
import java.util.HashMap; 
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
    int n;
    Parser parserf = new Parser();
    parserf.NumLines(File_in);
    try(Reader readFilein = new FileReader(File_in)) {
    String line;
    String nLine;
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
    HashMap<String, Integer>argsTable = new HashMap<>();
    CreateHashTable(null, 1, args, argsTable, null);
    //Starts the sintx parsing
    //Empieza el analisisi sintactico
    CommandArgRule argsCommands = new CommandArgRule(hashTablePOP_PUSH, argsTable, 4, 8, "popconstatn1", null);
    HashTablePreDet(); // Create the hash table with the pre-determined elements
                       // Crear la tabla hash con los elementos predefinidos
    while((nLine = parserf.get(readFilein, Parser.Readmode.NumberLine, ' ')) != null) {
        readFilein.read();// Skip the number line String and the space
                         // Omitir la cadena de número de línea y el espacio
        line = parserf.get(readFilein, Parser.Readmode.CompletelyLine, '0');
        
        n = CompareWithHashTable(line, nLine, 3, null, TableHash.Arithmetics);
        if (n != 0){
          n= CompareWithHashTable(line, nLine, 3, null, TableHash.Booleans);
          if (n != 0) {
          n = CompareCommandsWithArg(line, nLine, argsCommands, 1, null);
          if (n != 0){
            System.err.println("Error in the line %s\nDETAILS: Wrong Sintaxis\n");
             return -1;
            }
            continue;
          }
          continue;
        }
        continue;
     }
    return 0;
    }
    catch (FileNotFoundException e) {
        System.out.println("File not found: " + File_in);
        return -1;
    } catch (IOException e) {
        System.out.println("Error reading file: " + File_in);
        return -1;
    }
}
//------------------------------------------------------
private HashMap<String, Integer> hashTableArith = new HashMap<>(); // Create a hash table for arithmetic operations
                                                                  // Crear una tabla hash para operaciones aritméticas

private HashMap<String, Integer> hashTableBool = new HashMap<>(); // Create a hash table for boolean operations
                                                                      //Crear una tabla hash para operaciones booleanas

private HashMap<String, Integer> hashTablePOP_PUSH = new HashMap<>(); // Create a hash table for functions
                                                                    // Crear una tabla hash para funciones
//constructor for create the PreDeterminate hash tables
// constructor para crear las tablas hash predefinidas
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
    CreateHashTable(null, 1, NewElements, null, TableHash.POP_PUSH);
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
    if(hashTable == null) {
        System.err.println("Error: Hash table is null and AddToPreDefined is 0");
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
        // Iterate through the list of new elements and add them to the hash table
        // Itera a través de la lista de nuevos elementos y agrégales a la tabla hash
        for (String element2 : NewElements) {
            hash = 0;
            for (int i = 0; i < element2.length(); i++) {
                hash += element2.charAt(i);
            }
            hashTable.put(element2, hash);
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
    if(n < 0){
        System.out.printf("Error: cant't use negative numbers to get characters\n");
        return null;
    }
    StringBuilder result = new StringBuilder();
    if(n > input.length()) {
        System.out.println("The number of characters to get is greater than the length of the input string\nThe code will be executed with the length of the input string\n");
        n = input.length();
    }
    for (int i = 0; i < n; i++) {
        char c = input.charAt(i);
        result.append(c);
    }
    return result.toString();
}
//-------------------------------------------------------
public int CompareWithHashTable(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, TableHash CompareWithPreDefined, boolean withArgs) {
    int i = 0;
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
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTablePOP_PUSH, i, true);
                break;
            case Arithmetics:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableArith, i, false);
                break;
            case Booleans:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableBool, i, false);
               break;
            default:
            n = -1;
                break;
        }
        if(n != 0) return -1;
        else return 0;
    }
    else if(hashTableForCompare != null && CompareWithPreDefined == null){
       int n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableForCompare, i, withArgs);
       if(n != 0) return -1;
       else return 0;
    }
    else{
        int n;
        n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableForCompare, i, withArgs);
        if(n != 0){
            switch (CompareWithPreDefined) {
            case POP_PUSH:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTablePOP_PUSH, i, true);
                break;
            case Arithmetics:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableArith, i, false);
                break;
            case Booleans:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableBool, i, false);
               break;
            default:
            n = -1;
                break;
        }
        if(n != 0) return -1;
        else return 0;
        }
    }
    return -3;//especial value, this never must be the out
             //Valor especial, esto nunca deberia salir
}
//-------------------------------------------------------
public int CompareTableImplement(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, int iEspecial, boolean withArgumets){
    if(line != null){
    // Get the first three characters of the input string
        // Obtener los primeros tres caracteres de la cadena de entrada
        String element = GetNchars(line, CharsNumToCompare_SRING_MORE_LONG);
        if(element == null) return -1;
        if (hashTableForCompare.containsKey(element) && !withArgumets) {
            // Verificar que no haya caracteres inesperados después del tercer carácter
            // Check that there are no unexpected characters after the third character
            String remaining = line.substring(CharsNumToCompare_SRING_MORE_LONG); // Tomamos lo que sigue //Take the rest of line 
            if (!remaining.isEmpty()) {
                System.out.printf("Error in the line %s\nDETAILS: Unexpected characters after instruction\n", nLine);
                return -1;
            }
            return 0;
        }
        else{
             iEspecial = 1;
            int i = 1;
            int sub;
            //Iterater until found a conincidence 
            //Iterar hasta encontrar una conincidencia
            while(!(hashTableForCompare.containsKey(element)) && i <= CharsNumToCompare_SRING_MORE_LONG){
                 sub= CharsNumToCompare_SRING_MORE_LONG-i;
                element = GetNchars(element, sub);             
                iEspecial = sub;
                i++;
            }
            // Check if the string is a invalid expression (not found in the table)
           // Verificar si la cadena es una expresión no válida (no encontrada en la tabla)
            if(i > CharsNumToCompare_SRING_MORE_LONG){
                System.err.printf("Error in the line %s\nDETAILS: Invalid Expression, not found in the Hash Table\n", nLine);
                return -1;
            }
            return 0;
        }
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
public int CompareCommandsWithArg(String line, String nLine, CommandArgRule ArgsInputRule , int SensibleToMayus, ArrayList<Character> Delimiters) {
    // Check for conflicting parameters (MultipleFormatPattern and formatPattern cannot be both set)
    if (ArgsInputRule.MultipleFormatPattern != null && ArgsInputRule.formatPattern != null) {
        System.err.println("Error\nDETAILS: You can only select one of them: 'MultipleFormatPattern' or 'formatPattern'\n");
        return -1;
    }

    int n = 0, r = 0;
    boolean coincidence = false;

    // Check if multiple formats are provided
    //Revisar si se dieron multiples formatos

    if (ArgsInputRule.MultipleFormatPattern != null) {
        r = identifyTheFormat(line, SensibleToMayus);
        for (String pattern : ArgsInputRule.MultipleFormatPattern) {
            if ((n = identifyTheFormat(pattern, SensibleToMayus)) == r) {
                coincidence = true;
                break;
            }
        }

        if (!coincidence) {
            System.err.printf("Error in the line %s\nDETAILS: The format for the line is invalid. The format should be: '%s'\n", nLine, ArgsInputRule.MultipleFormatPattern);
            return -1;
        }
    }

    // Check if the single format pattern is valid
    //Revisar si solo se dio un formatodo

    n = identifyTheFormat(ArgsInputRule.formatPattern, SensibleToMayus);
    if ((n != (r = identifyTheFormat(line, SensibleToMayus))) && !coincidence) {
        System.err.printf("Error in the line %s\nDETAILS: The format for the line is invalid. The format is: '%s'\n", nLine, ArgsInputRule.formatPattern);
        return -1;
    }

    // Remove delimiters from the line
    //Eliminaro los delimitadores de la line
    StringBuilder WithoutDel = new StringBuilder();
    for (int i = 0; i < line.length(); i++) {
        char character = line.charAt(i);
        if (!Delimiters.contains(character)) {
            WithoutDel.append(character);  // Add character to new string
        }
    }
    String newLine = WithoutDel.toString();

    // Compare with the command table
    //comparar con la tabla de comandos el comando
    int LengthOfCommand = 0;
    if ((n = CompareTableImplement(newLine, nLine, ArgsInputRule.commandLength, ArgsInputRule.commandTable, LengthOfCommand, true)) != 0) {
        return -1;
    }

    // Ignore the command part and compare the arguments
    //Ignorar el comando y comparar los argumentos

    String remainingNewLine = newLine.substring(LengthOfCommand);
    if ((n = CompareTableImplement(remainingNewLine, nLine, ArgsInputRule.argLength, ArgsInputRule.argTable, LengthOfCommand, true)) != 0) {
        return -1;
    }

    return 0;
}
//-------------------------------------------------------
public int identifyTheFormat(String FormatExample, int SensibletoMayus){
int n = 0; //Get the format in a integer number
            //Obtener el formato en un número entero

//iterate until the end of the FormatExample
//Recorrer la cadena FromatExample
        for (int i = 0; i < FormatExample.length(); i++) {
        char actualChar = FormatExample.charAt(i);
        n += identifyTypeIntOrChar(actualChar, SensibletoMayus);
    }
 return n;
}
//-------------------------------------------------------
public int identifyTypeIntOrChar(char actual, int SensibleToMayus){
    //If character actual is a number
    //Si el caracter actual es un número
    if(actual >= '0' && actual <= '9'){
        return 1;
    }
    //else if character actual is a letter and not is sensible to mayus
    //Si el caracter actual es una letra y no es sensible a las mayusculas
    else if((actual >= 'a' && actual <='z' || actual >= 'A' && actual <= 'Z') && SensibleToMayus == 0){
     return 2;
    }
    //else if character actual is a letter and is sensible to mayus
    //si es sensible a mayusculas y es una letra
    else if(SensibleToMayus != 0 && actual >= 'a' && actual <= 'z'){
        return 3;
    }
    else if(SensibleToMayus != 0 && actual >= 'A' && actual <= 'Z'){
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