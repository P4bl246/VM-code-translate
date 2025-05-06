package VM_code_Translator;

import java.io.File;
imtport java.nio.File;

public class Parser {
    /*

    Parsing (análisis sintáctico)---------------------------------------------------------------------

    public static int parsing(char input[]);// Constructor for the parsing
                                // Constructor para el análisis sintáctico
    Files Preparation---------------------------------------------------------------------------------
    public int CleanFile(File file_In);// Clean the file of the spaces and void lines and comments(internal method)
                                         // Limpiar el archivo de los espacios y líneas vacias y comentarios(Método interno)

    public int CreateANewFile(String nameFile);// This method is used to create a new file(internal method)
                                              // Este método se utiliza para crear un nuevo archivo(Método interno)

    protected int File_to_txt(File file_In);// This method is used to convert the file to txt format(internal method)
                                            // Este método se utiliza para convertir el archivo a formato txt(Método interno)
    public int PrepareFiles();


    */



   /*
    * This method are the Core method for parse input.
    * Has integrated other methods for enhanced functionality.

    * Este es el método principal para analizar la entrada.
    * Ha integrado otros métodos para mejorar la funcionalidad.
    */

    public static int parseInput(char[] inputFile) {
        System.out.println("Parse input: %s...", inputFile);
       return 0;
    }
//----------------------------------------------------------------------
    public int parsing(char[] input) {
        
        return 0;
    }

//STARTS THE PROCESS OF PREPARE FILES (INICIA EL PROCESO DE PREPARACIÓN DE ARCHIVOS)----------------------------------------------------------------- 

protected int File_to_txt(File file_In) {
    // Create a new file, call "file.txt"
    // Crear un nuevo archivo, llamado "file.txt"
    int n = CreateANewFile("file.txt");
    if (n != 0){
        return 1; // Error case-En caso de error
    }
    // open the new file in write mode and the file_In in read mode
    // abrir el nuevo archivo en modo escritura y el file_In en modo lectura
    
    return 0;
}
//-----------------------------------------------------------------------------
public int CreateANewFile(String nameFile){
    File file = new File(nameFile);
try {
    if (file.createNewFile()) {
        System.out.println("File create.\n");
        return 0;
    } else {
        System.out.println("The file exist.\n");
        return 0;
    }
} catch (IOException e) {
    System.out.printf("ERROR to try to create the file: %s\n", new String(nameFile));
    return 1;
}
}

public int CleanFile(File file_In){
}
}
