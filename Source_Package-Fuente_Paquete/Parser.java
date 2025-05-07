package VM_code_Translator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    /*

//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------

         public static int parsing(char input[]);// This method is used to parse the input file(internal method)
                                                // Este método se utiliza para analizar el archivo de entrada(Método interno)
                                                
//END OF FUNCTIONS FOR PARSING SINTAX (FIN DE LAS FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)--------------------------------------------------------------

//FUNCTIONS FOR PREPARE FILES (FUNCIONES PARA LA PREPARACIÓN DE ARCHIVOS)---------------------------------------------------------------------------------

*************************************************------------------------------------------------*******************************
    public int RemoveSpaces(File ReadFile, File WritteFile);// This method is used to remove spaces from the input file(internal method)
                                                            // Este método se utiliza para eliminar espacios del archivo de entrada(Método interno)

    public int CleanFile(File file_In);// Clean the file of the spaces and void lines and comments,integrat the above functions(internal method)
                                         // Limpiar el archivo de espacios y líneas vacías y comentarios integrando las funciones anteriores(Método interno)
*************************************************------------------------------------------------*******************************
    public void NumberLine(File ReadFile, File WritteFile);// This method is used to add the line number to the input file(internal method)
                                                            // Este método se utiliza para agregar el número de línea al archivo de entrada(Método interno)
------------------------------------------------------------------------------------------------------------------------------
    protected int File_to_txt(File file_In);// This method is used to convert the file to txt format(internal method)
                                             // Este método se utiliza para convertir el archivo a formato txt(Método interno)
------------------------------------------------------------------------------------------------------------------------------
    public int PrepareFiles();

//END OF FUCNTION TO PREPARE FILES(FIN DE LAS FUNCIONES PARA LA PREPARACIÓN DE ARCHIVOS)---------------------------------------------------------------------------------
*/



//STARTS THE PROCESS OF PREPARE FILES (INICIA EL PROCESO DE PREPARACIÓN DE ARCHIVOS)----------------------------------------------------------------- 

protected int File_to_txt(File file_In) {
    // Open the file_In in reading and create a temporary file for writing
    // Abrir el archivo de entrada en modo lectura y el archivo temporal en modo escritura
    try (FileReader fileP = new FileReader(file_In);
         FileWriter tempFile = new FileWriter("temp.txt")) {
        
        int c;
        // Read character by character from the input file and write to the temporary file
        // Leer carácter por carácter del archivo de entrada y escribir en el archivo temporal
        while ((c = fileP.read()) != -1) {
            tempFile.write(c);
        }
        return 0; // Éxito
    } catch (IOException e) {
        // Manejar errores de entrada/salida
        // Handle input/output errors
        System.out.println("Error: " + e.getMessage());
        return -1; // Error
    }
}

//--------------------------------------------------------------
public int CleanFile(File file_In){
    
    return 0;
}
//--------------------------------------------------------------
public int RemoveSpaces(File ReadFile, File WritteFile){
    int c;
    //While don't find EOF (End of file)
    //Mientras no encuentre EOF (Fin de archivo)
    while((c = ReadFile.read()) != -1){
        if(c != ' '){
            WritteFile.write(c);
        }
      //Don't writte
      //No hacer nada
    }
    close(ReadFile);
    close(WritteFile);
    return 0;
} 
//--------------------------------------------------------------
public int RemoveComments(File ReadFile, File WritteFile){
    int actual;
    int commentBlock = 0; // Counter for block comments
                          // Contador de comentarios de bloque

    //While don't find EOF (End of file)
    //Mientras no encuentre EOF (Fin de archivo)
    while((actual = ReadFile.read()) != -1){
        if(actual != '/'){
            WritteFile.write(c);
        }
        else{
            //Read the next character
            //Leer el siguiente carácter
            int actual = ReadFile.read();
            if(actual == '/'){
                //Read until the end of line
                //Leer hasta el final de la línea
                while((actual = ReadFile.read()) != '\n' && actual != -1);
            }     
        }
    
    return 0;
}
//--------------------------------------------------------------
public int RemoveBlockComments(File ReadFile, File WritteFile){
    int actual;
    int commentBlock = 0; // Counter for block comments
                          // Contador de comentarios de bloque

    //While don't find EOF (End of file)
    //Mientras no encuentre EOF (Fin de archivo)
    while((actual = ReadFile.read()) != -1){
        if(actual != '/'){
            WritteFile.write(c);
        }
        else{
            //Read the next character
            //Leer el siguiente carácter
            int actual = ReadFile.read();
            //if the next character is a '*' is a block comment
            //Si el siguiente carácter es un '*' es un comentario de bloque
            if(actual == '*'){
                commentBlock++; // Increment the block comment counter
                                // Incrementar el contador de comentarios de bloque

                //Read until the end of comment
                //Leer hasta el final del comentario
                while((actual = ReadFile.read()) != '*' && actual != -1);
                if(actual == '*'){
                    actual = ReadFile.read();
                    if(actual == '/'){
                        continue;
                    }
                    else if(actual == -1){
                        // End of file reached
                        // Fin del archivo alcanzado
                    System.out.printf("ERROR in the line %s\n DETAILS: The block comment is not closed\n", nLinea);
                        break;
                    }
                }
            }
        }
      //Don't writte
      //No hacer nada
    }
    
    return 0;
}
//--------------------------------------------------------------
public void NumberLines(File Read_File, File Writte_File){
    // Open the file for reading
    // Abrir el archivo para lectura
    Reader ReadFile = new FileReader(Read_File);
    // Open the file for writing
    // Abrir el archivo para escritura
    Writer WritteFile = new FileWriter(Writte_File);

    int c;
    int line = 1; // Counter for lines
                  // Contador de líneas
    //While don't find EOF (End of file)
    //Mientras no encuentre EOF (Fin de archivo)
    while((c = ReadFile.read()) != -1){
        WritteFile.write(line);
        WriteFile.write(' ');
        if(c == '\n'){
            line++;
        }
        WritteFile.write(c);
    }
    close(ReadFile);
    close(WritteFile);
    return 0;
}
//--------------------------------------------------------------
public String obtainNumberLine(Reader fileIn) {

    int c;
    ArrayList<Character> line = new ArrayList<>();
   
    // Leer caracteres hasta encontrar un espacio
    // Read characters until a space is found
    while ((c = fileIn.read()) != -1 && c != ' ') {
        line.add((char) c);
    }

    fileIn.close();

    // Convertir el ArrayList a una cadena
    // Convert the ArrayList to a string
    StringBuilder result = new StringBuilder();
    for (char ch : line) {
        result.append(ch);
    }

    return result.toString();
}
}
