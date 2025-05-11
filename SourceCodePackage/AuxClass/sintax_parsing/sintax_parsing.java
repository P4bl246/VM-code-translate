package AuxClass.sintax_parsing;
import AuxClass.Parser.Parser; // Import the Parser class from the AuxCLass.Parser package
                            // Importar la clase Parser del paquete AuxCLass.Parser
import AuxClass.Parser.Parser.Readmode;

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
    public int Arthmetic_Expression(String input);// This method is used to parse the arithmetic expression
                                                // Este método se utiliza para analizar las expresión aritmética

    public void CreateHashTable(String element, int SimpleOrMultiples, ArrayList<String> NewElements, HashMap<String, Integer> hashTable,int AddToPreDefined);// This method is used to create a hash table
//-----------------------------------****************--------------------------******                                                                                                                                    // Este método se utiliza para crear una tabla hash

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

        n = Arthmetic_Expression(line, nLine, hashTableArith);
        if (n != 0) return -1;
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
private HashMap<String, Integer> hashTableFunc = new HashMap<>(); // Create a hash table for functions
                                                                    // Crear una tabla hash para funciones
//constructor for create the PreDeterminate hash tables
// constructor para crear las tablas hash predefinidas
  public void HashTablePreDet(){
    ArrayList<String> NewElements = new ArrayList<>();
    NewElements.add("add");
    NewElements.add("sub"); 
    NewElements.add("neg");
    CreateHashTable(null, 1, NewElements, null, TableHash.Arithmetics);
    NewElements.clear();
    NewElements.add("or");
    NewElements.add("and");
    NewElements.add("not");
    NewElements.add("eq");
    NewElements.add("lt");
    NewElements.add("gt");
    CreateHashTable(null, 1, NewElements, null, TableHash.Booleans);
    NewElements.clear();
    NewElements.add("pop");
    NewElements.add("push");
    CreateHashTable(null, 1, NewElements, null, TableHash.Functions);
  }
//------------------------------------------------------
public int Arthmetic_Expression(String input, String nLine, HashMap<String, Integer> hashTable) {
    if (input != null) {
        // Get the first three characters of the input string
        // Obtener los primeros tres caracteres de la cadena de entrada
        String element = GetNchars(input, 3);
        if (hashTable.containsKey(element)) {
            // Verificar que no haya caracteres inesperados después del tercer carácter
            // Check that there are no unexpected characters after the third character
            String remaining = input.substring(3); // Tomamos lo que sigue 
            if (!remaining.isEmpty()) {
                System.out.printf("Error in the line %s\nDETAILS: Unexpected characters after arithmetic instruction\n", nLine);
                return -1;
            }
            return 0;
        }
        // Check if the string is a valid arithmetic expression
        // Verificar si la cadena es una expresión aritmética válida
        else {
            System.out.printf("Error in the line %s\nDETAILS: Invalid arithmetic expression\n", nLine);
            return -1;
        }
    }
    // If the input string is null, print an error message
    // Si la cadena de entrada es nula, imprime un mensaje de error
     else {
        System.out.printf("Error in the line %s\nDETAILS: Input is null\n", nLine);
        return -1;
    }
}
//------------------------------------------------------
// This method is used to create a hash table with the pre-determined elements
// Este método se utiliza para crear una tabla hash con los elementos predefinidos
public enum TableHash{
    Arithmetics,
    Booleans,
    Functions
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
            else if(AddToPreDefined == TableHash.Functions) {
                // Add to the functions hash table
                // Agregar a la tabla hash de funciones
                hashTableFunc.put(element2, hash);
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
            else if(AddToPreDefined == TableHash.Functions) {
                // Add to the functions hash table
                // Agregar a la tabla hash de funciones
                hashTableFunc.put(element, hash);
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
//END THE PROCESS OF PARSING SINTAX (TERMINA EL PROCESO DE ANÁLISIS SINTÁCTICO)-----------------------------------------------------------------

}