package AuxClass.sintax_parsing;
import AuxClass.Parser.Parser; // Import the Parser class from the AuxCLass.Parser package
                            // Importar la clase Parser del paquete AuxCLass.Parser
import AuxClass.Parser.Parser.Readmode;
import AuxClass.sintax_parsing.VarArgumentsConstructor.CommandArgRule;
import AuxClass.sintax_parsing.sintax_parsing.TableHash;

import java.util.ArrayList; // Import the ArrayList class from the java.util package
                           // Importar la clase ArrayList del paquete java.util
import java.util.HashMap; 
import java.io.*;

public class sintax_parsing {
// This class is used to parse the file and check the syntax
// Esta clase se utiliza para analizar el archivo y verificar la sintaxis
/* 
//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------

public void HashTablePreDet();// This method is used to create a hash table with the pre-determined elements
                                // Este método se utiliza para crear una tabla hash con los elementos predefinidos

//-----------------------------------****************--------------------------******   
//-------------------------------------------------------------
    public int CompareTableImplement(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare);//Implmentación for the next function(just commands don't recive arguments)
                                                                                                                                                     //Implementación para la siguiente función(Solo para comandos que no reciben argumentos)

    public int CompareWithHashTable(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, TableHash CompareWithPreDefined);
    //This method is used to compare the Command with the command table(Hash table), integrated the function CompareTableImplement                                                                                                                                                                               
    //Este método se utiliza para comparar el comando con la tabla de comandos (tabla hash), integra la función CompareTableImplement
//-------------------------------------------------------------           
    public int identifyTypeIntOrChar(char actual, int SensibleToMayus); //This method is used to find and identify the character and return his value like an integer
                                                                        //Ese método se utiliza para encontrar y indentificar el carácter y retornar su valor como entero

    public int identifyTheFormat(String FormatExample, int SensibletoMayus);//This method is used to get and identify the pattern of a format in a line, use the above function
                                                                            //Este método se utiliza para obtener y identificar el patrón de un formato en una linea, utiliza la función anterior    
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
    Parser parser = new Parser();
    parser.NumLines(File_in);
    try(Reader readFilein = new FileReader(File_in)) {
    String line;
    String nLine;
    HashTablePreDet(); // Create the hash table with the pre-determined elements
                       // Crear la tabla hash con los elementos predefinidos
    while((nLine = parser.get(readFilein, Readmode.NumberLine, ' ')) != null) {
        readFilein.read();// Skip the number line String and the space
                         // Omitir la cadena de número de línea y el espacio
        line = parser.get(readFilein, Readmode.CompletelyLine, '0');

        n = CompareWithHashTable(line, nLine, 3, null, TableHash.Arithmetics);
        if (n != 0){
          n= CompareWithHashTable(line, nLine, 3, null, TableHash.Booleans);
          if (n != 0) CompareCommandsWithArg(line, nLine, null, TableHash.POP_PUSH);
        }
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
    String result = "";
    if(n > input.length()) {
        System.out.println("The number of characters to get is greater than the length of the input string\nThe code will be executed with the length of the input string\n");
        n = input.length();
    }
    for (int i = 0; i < n; i++) {
        result += input.charAt(i);
    }
    return result;
}
//-------------------------------------------------------
public int CompareWithHashTable(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare, TableHash CompareWithPreDefined) {
    //if 
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
            case TableHash.POP_PUSH:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTablePOP_PUSH );
                break;
            case TableHash.Arithmetics:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableArith);
                break;
            case TableHash.Booleans:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableBool);
               break;
            default:
            n = -1;
                break;
        }
        if(n != 0) return -1;
        else return 0;
    }
    else if(hashTableForCompare != null && CompareWithPreDefined == null){
       int n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableForCompare);
       if(n != 0) return -1;
       else return 0;
    }
    else{
        int n;
        n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableForCompare);
        if(n != 0){
            switch (CompareWithPreDefined) {
            case TableHash.POP_PUSH:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTablePOP_PUSH );
                break;
            case TableHash.Arithmetics:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableArith);
                break;
            case TableHash.Booleans:
                n = CompareTableImplement(line, nLine, CharsNumToCompare_SRING_MORE_LONG, hashTableBool);
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
public int CompareTableImplement(String line, String nLine, int CharsNumToCompare_SRING_MORE_LONG, HashMap<String, Integer> hashTableForCompare){
    if(line != null){
    // Get the first three characters of the input string
        // Obtener los primeros tres caracteres de la cadena de entrada
        String element = GetNchars(line, CharsNumToCompare_SRING_MORE_LONG);
        if (hashTableForCompare.containsKey(element)) {
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
            int i = 1;
            int sub;
            //Iterater until found a conincidence 
            //Iterar hasta encontrar una conincidencia
            while(!(hashTableForCompare.containsKey(element)) && i<CharsNumToCompare_SRING_MORE_LONG){
                 sub= CharsNumToCompare_SRING_MORE_LONG-i;
                element = GetNchars(element, sub);             
                i++;
            }
            // Check if the string is a invalid expression (not found in the table)
           // Verificar si la cadena es una expresión no válida (no encontrada en la tabla)
            if(i == CharsNumToCompare_SRING_MORE_LONG){
                System.err.printf("Error in the line %s\nDETAILS: Invalid Expression\n");
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
public int CompareCommandsWithArg(String line, String nLine, CommandArgRule ArgsInputRules, int SensibleToMayus){
        
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