import AuxClass.Parser.*; // Import the Parser class from the AuxCLass.Parser package
                               // Importar la clase Parser del paquete AuxCLass.Parser
public class executable {

    public static void main (String[] args) {
       Parser parseF = new Parser();// Create a new instance of the Parser class for use his methods in the main method
                                   //Crear una nueva instancia de la clase Parser para usar sus métodos en el método principal

         int n;
         n= parseF.File_to_txt("Archivo.vm"); 
         if(n != 0) return;
        
         
         n = parseF.CleanFile("fileCopy.txt");
         if(n != 0) return;

      sintaxParsing r = new sintaxParsing();
    
         n = r.CreateHashTable("hi", 0, )
         if(n != 0) return;
     
         return;        
    }
}