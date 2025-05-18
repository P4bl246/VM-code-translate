import AuxClass.Parser.*; // Import the Parser class from the AuxCLass.Parser package
                               // Importar la clase Parser del paquete AuxCLass.Parser
//import java.util.ArrayList; // Import the ArrayList class from the java.util package
                           // Importar la clase ArrayList del paquete java.util
//import java.util.HashMap; 

public class executable {

    public static void main (String[] args) {
       Parser parseF = new Parser();// Create a new instance of the Parser class for use his methods in the main method
                                   //Crear una nueva instancia de la clase Parser para usar sus métodos en el método principal

         int n;
         String copy = "fileCopy.txt";
         n= parseF.File_to_txt("Archivo.vm", copy); 
         if(n != 0) return;
          

         n = parseF.CleanFile(copy);
         if(n != 0) return;

         return;        
    }
}