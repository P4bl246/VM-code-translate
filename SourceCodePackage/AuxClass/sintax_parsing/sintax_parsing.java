package AuxClass.sintax_parsing;
import AuxClass.Parser.Parser; // Import the Parser class from the AuxCLass.Parser package
                            // Importar la clase Parser del paquete AuxCLass.Parser
import java.util.ArrayList; // Import the ArrayList class from the java.util package
import java.util.HashMap;
                            // Importar la clase ArrayList del paquete java.util
public class sintax_parsing {
// This class is used to parse the file and check the syntax
// Esta clase se utiliza para analizar el archivo y verificar la sintaxis
/* 
//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------

    
    
    public int Arthmetic_Expression(String input);// This method is used to parse the arithmetic expression
                                                // Este método se utiliza para analizar las expresión aritmética

    public String GetNchars(String input, int n);// This method is used to get the first n characters of the input string
                                                // Este método se utiliza para obtener los primeros n caracteres de la cadena de entrada

    public void addToHashTable(String element, int SimpleOrMultiples, ArrayList<String> NewElements, HashMap<String, Integer> hashTable);// This method is used to add elements to the hash table
                                                                                                                                    // Este método se utiliza para agregar elementos a la tabla hash

    public void makeHashTablePreDet(ArrayList<String> arrayString);// This method is used to create a hash table with the pre-determined elements
                                                                    // Este método se utiliza para crear una tabla hash con los elementos predeterminados

//END OF FUNCTIONS FOR PARSING SINTAX (FIN DE LAS FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)--------------------------------------------------------------
*/
//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------
public  int parsing(String input) {
        return 0;
    }
//------------------------------------------------------
public int Arthmetic_Expression(String input) {
    return 0;
}
//------------------------------------------------------
public void makeHashTablePreDet(ArrayList<String> arrayString, HashMap<String, Integer> hashTable) {
    // This method is used to create a hash table with the pre-determined elements
    // Este método se utiliza para crear una tabla hash con los elementos predeterminados
  hashTable = new HashMap<>(); // Create a hash table
                                                        // Crear una tabla hash
   int hash;
   for(String element : ArrayString){
    hash = 0;
    for(int i = 0; i < element.length(); i++){
       hash += element.charAt(i);
    }
    hasTable.put(element, hash); // Add the element to the hash table
                                 // Agregar el elemento a la tabla hash
    
    }
}
public void addToHashTable(String element, int SimpleOrMultiples, ArrayList<String> NewElements, HashMap<String, Integer> hashTable){
    hashTable = new HashMap<>(); // Acceded to the hash table
                                 // Acceder a la tabla hash
                
    int hash;
    if(SimpleOrMultiples != 0){
        for(String element2 : NewElements){
            hash = 0;
            for(int i = 0; i < element2.length(); i++){
                hash += element2.charAt(i);
            }
            hashTable.put(element2, hash); // Add the element to the hash table
                                 // Agregar el elemento a la tabla hash
        return;
    }
 }
    else{
        hash = 0;
        for(int i = 0; i < element.length(); i++){
            hash += element.charAt(i);
        }
        hashTable.put(element, hash); // Add the element to the hash table
                                 // Agregar el elemento a la tabla hash
        return;
    }
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