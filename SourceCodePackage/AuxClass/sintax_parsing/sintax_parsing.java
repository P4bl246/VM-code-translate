package AuxClass.sintax_parsing;
import AuxClass.Parser.Parser; // Import the Parser class from the AuxCLass.Parser package
                            // Importar la clase Parser del paquete AuxCLass.Parser
import java.util.ArrayList; // Import the ArrayList class from the java.util package
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

    public int parsing(String input);// This method is used to parse the input file, integrating funcions
                                    // Este método se utiliza para analizar el archivo de entrada, integrando funciones
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
public void makeHashTablePreDet(ArrayList<String> arrayString){
    
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