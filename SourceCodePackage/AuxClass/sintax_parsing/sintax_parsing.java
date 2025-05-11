package AuxClass.sintax_parsing;
import AuxClass.Parser.Parser; // Import the Parser class from the AuxCLass.Parser package
                            // Importar la clase Parser del paquete AuxCLass.Parser

import java.util.ArrayList; // Import the ArrayList class from the java.util package
                           // Importar la clase ArrayList del paquete java.util
import java.util.HashMap; 
import java.io.*;

public class sintax_parsing {
// This class is used to parse the file and check the syntax
// Esta clase se utiliza para analizar el archivo y verificar la sintaxis
/* 
//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------

    
//-----------------------------------****************--------------------------******   
    public int Arthmetic_Expression(String input);// This method is used to parse the arithmetic expression
                                                // Este método se utiliza para analizar las expresión aritmética

    public void CreateHashTable(String element, int SimpleOrMultiples, ArrayList<String> NewElements, HashMap<String, Integer> hashTable);// This method is used to create a hash table
//-----------------------------------****************--------------------------******                                                                                                                                    // Este método se utiliza para crear una tabla hash

    public String GetNchars(String input, int n);// This method is used to get the first n characters of the input string
                                                // Este método se utiliza para obtener los primeros n caracteres de la cadena de entrada

    public int parser_Sintaxis(String File_in);// This method is used to parse the syntax of the file
                                                // Este método se utiliza para analizar la sintaxis del archivo

//END OF FUNCTIONS FOR PARSING SINTAX (FIN DE LAS FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)--------------------------------------------------------------
*/
//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------
public  int parser_Sintaxis(String File_in) {
    int n;
    Parser parser = new Parser();
    parser.NumLines(File_in);
    try(Reader readFilein = new FileReader(File_in)) {
    String line;
    String nLine;
    while((nLine = parser.get(readFilein, 1, ' ', 0)) != null){
        readFilein.read();// Skip the number line String and the space
                         // Omitir la cadena de número de línea y el espacio
        line = parser.get(readFilein, 0, '0', 1);

        n = Arthmetic_Expression(line, nLine);
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
public int Arthmetic_Expression(String input, String nLine) {
    ArrayList<String> preDet = new ArrayList<>();
    preDet.add("add");
    preDet.add("sub");
    preDet.add("neg");
    
    HashMap<String, Integer> hashTable = new HashMap<>();
    CreateHashTable(null, 1, preDet, hashTable);
    
    if(input != null) {
        String element = GetNchars(input, 3);
        if (hashTable.containsKey(element)) {
            
            return 0;
        } else {
            System.out.printf("Error in the line %s\nDETAILS: Invalid arithmetic expression\n", nLine);
            return -1;
        }
    } else {
        System.out.printf("Error int he line %s\nDETAILS: Input is null\n", nLine);
        return -1;
    }
}

//------------------------------------------------------

public void CreateHashTable(String element, int SimpleOrMultiples, ArrayList<String> NewElements, HashMap<String, Integer> hashTable) {
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
    }
    return;
}
//-------------------------------------------------------
public String GetNchars(String input, int n) {
    String result = "";
    for (int i = 0; i < n; i++) {
        result += input.charAt(i);
    }
    return result;
}
//END THE PROCESS OF PARSING SINTAX (TERMINA EL PROCESO DE ANÁLISIS SINTÁCTICO)-----------------------------------------------------------------

}