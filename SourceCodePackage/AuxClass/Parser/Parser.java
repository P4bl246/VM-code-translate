package AuxClass.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.Reader;
import java.io.Writer;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;

public class Parser {
    /*

//FUNCTIONS FOR PARSING SINTAX (FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)----------------------------------------------------------------------------

         public static int parsing(char input[]);// This method is used to parse the input file(internal method)
                                                // Este método se utiliza para analizar el archivo de entrada(Método interno)
                                                
//END OF FUNCTIONS FOR PARSING SINTAX (FIN DE LAS FUNCIONES PARA EL ANÁLISIS SINTÁCTICO)--------------------------------------------------------------

//FUNCTIONS FOR PREPARE FILES (FUNCIONES PARA LA PREPARACIÓN DE ARCHIVOS)---------------------------------------------------------------------------------

*************************************************------------------------------------------------*******************************
    public int RemoveSpaces(String Read_File_In);// This method is used to remove spaces from the input file(internal method)
                                                            // Este método se utiliza para eliminar espacios del archivo de entrada(Método interno)

    public int RemoveSimpleComments(String Read_File_In);// This method is used to remove simple comments from the input file(internal method)
                                                                           // Este método se utiliza para eliminar comentarios simples del archivo de entrada(Método interno)

    public String obtainNumberLine(Reader fileIn) throws IOException;// This method is used to obtain the line number from the input file(internal method)
                                                            // Este método se utiliza para obtener el número de línea del archivo de entrada(Método interno)

    public int RemoveBlockComments(File Read_File_in, File Writte_File_Out);// This method is used to remove block comments from the input file(internal method)
                                                                           // Este método se utiliza para eliminar comentarios de bloque del archivo de entrada(Método interno)

    public int RemoveNestedBlockComments(int actual, Reader ReadFile, String nLine) throws IOException;// This method is used to remove nested block comments from the input file(internal method)
                                                                                                        // Este método se utiliza para eliminar comentarios de bloque anidados del archivo de entrada(Método interno)

    public int CleanFile(File file_In);// Clean the file of the spaces and void lines and comments,integrat the above functions, and 'NumLines'(internal method)
                                         // Limpiar el archivo de espacios y líneas vacías y comentarios integrando las funciones anteriores y 'NumLines'(Método interno)
*************************************************------------------------------------------------*******************************
    public int NumLines(File Read_File_in, File Writte_File_out);// This method is used to add the line number to the input file(internal method)
                                                            // Este método se utiliza para agregar el número de línea al archivo de entrada(Método interno)
------------------------------------------------------------------------------------------------------------------------------
    public int File_to_txt(String file_In);// This method is used to convert the file to txt format(internal method)
                                             // Este método se utiliza para convertir el archivo a formato txt(Método interno)
------------------------------------------------------------------------------------------------------------------------------
    public int PrepareFiles();

//END OF FUCNTION TO PREPARE FILES(FIN DE LAS FUNCIONES PARA LA PREPARACIÓN DE ARCHIVOS)---------------------------------------------------------------------------------
*/



//STARTS THE PROCESS OF PREPARE FILES (INICIA EL PROCESO DE PREPARACIÓN DE ARCHIVOS)----------------------------------------------------------------- 

public int File_to_txt(String file_In) {
    
    // Open the file_In in reading and create a temporary file for writing
    // Abrir el archivo de entrada en modo lectura y el archivo temporal en modo escritura
    try (FileReader fileP = new FileReader(file_In); Writer tempFile = new FileWriter("fileCopy.txt")) {
        int c;
        // Read character by character from the input file and write to the temporary file
        // Leer carácter por carácter del archivo de entrada y escribir en el archivo temporal
        while ((c = fileP.read()) != -1) {
            tempFile.write((char)c);
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
public int RemoveSpaces(String Read_File_In){
    System.out.printf("REMOVING SPACES FROM THE FILE: '%s'...\n", Read_File_In);
    // Open the file for reading and the file for writing
    // Abrir el archivo para lectura y el archivo de escritura
    try(Reader ReadFile = new FileReader(Read_File_In); Writer writterFile = new FileWriter("tempWithoutSpaces.txt")){ 
            int c = ReadFile.read(); // Read the first character
                                      // Leer el primer carácter

            //While don't find EOF (End of file)
            //Mientras no encuentre EOF (Fin de archivo)
            while(c != -1){
                if((char)c != ' '){
                    writterFile.write((char)c);
                }
                c = ReadFile.read(); // Read the next character
                                 // Leer el siguiente carácter
            }
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
            return -1; // Error
        }
        //Upload the change to input file rename it
        //Subir los cambios de el archivo de entrada renombrandolo 
        File inFile = new File(Read_File_In);
            if(inFile.delete()){
                File temp = new File("Temp.txt");
                if(temp.renameTo(inFile)){
                    System.out.printf("The file 'Temp.txt' is rename to '%s'\n", Read_File_In);
                    System.out.printf("THE FILE '%s' IS CLEAN OF SPACES\n", Read_File_In);
                    return 0;
                }
                System.out.printf("Error to try rename file 'TempWithoutSpaces.txt' to '%s'\n", Read_File_In);
                return -1;
            }
            System.out.printf("Error to try eliminated the file '%s'\n", Read_File_In);
             
            return -1;
} 
//--------------------------------------------------------------
public int RemoveSimpleComments(String Read_File_In){
    int actual;
    System.out.printf("REMOVING SIMPLE COMMENTS FROM THE FILE: '%s'...\n", Read_File_In);
    
    try(Reader ReadFile = new FileReader(Read_File_In); Writer WritteFile = new FileWriter("tempwithoutSimpleComments.txt")){
     //While don't find EOF (End of file)
    //Mientras no encuentre EOF (Fin de archivo)
    while((actual = ReadFile.read()) != -1){
        if((char)actual != '/'){
            WritteFile.write((char)actual);
        }
        else{
            //Read the next character
            //Leer el siguiente carácter
            actual = ReadFile.read();
            if((char)actual == '/'){
                //Read until the end of line
                //Leer hasta el final de la línea
                while(true){
                    actual = ReadFile.read();
                    if((char)actual == '\n' || actual == -1){
                        break;
                    }
                }
                if (actual == -1){
                    break;
                }
            }
            //if the next character not is '/', copy the above character, and the current character
            //Si el siguiente carácter no es '/', copiar el carácter anterior y el caracter actual
           else{
            WritteFile.write("/"); 
            WritteFile.write((char)actual);     
           }
        }
    }
  }
    catch(IOException e){
        System.out.println("Error: " + e.getMessage());
        return -1; // Error
    }
    
    //Upload the input file
    //Actualizar el archivo de entrada
    File infile = new File(Read_File_In);
    
    if(infile.delete()){
        File temp = new File("tempwithoutSimpleComments.txt");//Don't need close the files, because there are not opened, we just obtanin the files
                                                              //No es necesario cerrar los archivos, porque no están abiertos, solo obtenemos los archivos
        if(temp.renameTo(infile)){
            System.out.printf("The file 'tempwithoutSimpleComments.txt' is rename to '%s'\n", Read_File_In);
            System.out.printf("THE FILE '%s' IS CLEAN OF SIMPLE COMMENTS\n", Read_File_In);
            return 0;
        }
        System.out.printf("Error to try rename file 'tempwithoutSimpleComments.txt' to '%s'\n", Read_File_In);
        
        return -1;
    }
    System.out.printf("Error to try delte the file '%s'\n", Read_File_In);
    
    return -1;
}
//--------------------------------------------------------------
private int actual5; //Global variable to store the actual character (utlized just in the methods RemoveBlockComments and RemoveNestedBlockComments)
             //Variable global para almacenar el carácter actual (utilizado solo en los métodos RemoveBlockComments y RemoveNestedBlockComments)
//This variable is used to store the actual character in the method RemoveBlockComments and RemoveNestedBlockComments
//Esta variable se utiliza para almacenar el carácter actual en el método RemoveBlockComments y RemoveNestedBlockComments

public int RemoveBlockComments(File Read_File_in, File Writte_File_Out){
     actual5 = 0;
    
    // Open the file for reading and the file for writing
    // Abrir el archivo para lectura y el archivo de escritura
    try(Reader ReadFile = new FileReader(Read_File_in); Writer WritteFile = new FileWriter(Writte_File_Out)){
        actual5 = ReadFile.read(); // Read the first character
                                      // Leer el primer carácter
    //While don't find EOF (End of file)
    //Mientras no encuentre EOF (Fin de archivo)
    while(actual5 != -1){
        String nLine = obtainNumberLine(ReadFile);
        if((char)actual5 != '/'){
            WritteFile.write((char)actual5);
        }
        else{
            //Read the next character
            //Leer el siguiente carácter
            actual5 = ReadFile.read();
            //if the next character is a '*' is a block comment
            //Si el siguiente carácter es un '*' es un comentario de bloque
            if((char)actual5 == '*'){
                actual5 = ReadFile.read();
                //Read until the end of comment
                //Leer hasta el final del comentario
               RemoveNestedBlockComments(actual5, ReadFile, nLine);
             }  
            //if the next character not is '/', copy the above character, and the current character
            //Si el siguiente carácter no es '/', copiar el carácter anterior y el caracter actual
            else{
                WritteFile.write("/");      
            }
         }
        }
      //Don't writte
      //No hacer nada
    }
  catch(IOException e){
        System.out.println("Error: " + e.getMessage());
        return -1; // Error
    }
    
    return 0;
}
//--------------------------------------------------------------
public int RemoveNestedBlockComments(int actual, Reader ReadFile, String nLine) throws IOException{
    //Read until the end of comment
    //Leer hasta el final del comentario
    while(actual != -1){
        if((char)actual == '*'){
            actual = ReadFile.read();
            //if the next character is a '/', break the loop
            //si el siguiente carácter es un '/', salir del bucle
            if((char)actual == '/'){
                break;
            }  
        }
        else if((char)actual == '/'){
            actual = ReadFile.read();
            //if the next character is a '*', is a nested block comment
            //si el siguiente carácter es un '*', es un comentario de bloque anidado
            if((char)actual == '*'){
                if(RemoveNestedBlockComments(actual, ReadFile, nLine) != 0) return -1;
            }
        }
        actual = ReadFile.read();
        // If find the end of file without closing the comment
        // Si encuentra el final del archivo sin cerrar el comentario
        if(actual == -1){
            System.err.println("Error in the line: "+ nLine +"\nDETAILS:End of file without closing comment\n");
        }
    }
    return 0;
}
//--------------------------------------------------------------
public int NumLines(File Read_File_in, File Writte_File_out) {
    // Open the file for reading and the file for writing
    // Abrir el archivo para lectura y el archivo de escritura
    try (Reader ReadFile = new FileReader(Read_File_in);
         Writer writterFile = new FileWriter(Writte_File_out)) {
      
        int c = ReadFile.read();
        int line = 1;
        // Read the file character by character
        // Leer el archivo carácter por carácter
        while (c != -1) {
            // Escribir el número de línea
            // Write the line number
            writterFile.write(String.valueOf(line));
            writterFile.write(' ');

            // Escribir el contenido de la línea
            // Write the content of the line
            while (c != -1 && c != '\n') {
                writterFile.write((char) c);
                c = ReadFile.read();
            }

            // Escribir salto de línea si fue leído
            // Write a newline if it was read
            if (c == '\n') {
                writterFile.write('\n');
                c = ReadFile.read(); // Leer el siguiente carácter para la próxima línea
                                    // Read the next character for the next line
                line++;
            }
        }

        return 0;

    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return -1;
    }
}
//--------------------------------------------------------------
public String obtainNumberLine(Reader fileIn) throws IOException {
    int c;
    StringBuilder result = new StringBuilder();

    // Leer caracteres hasta encontrar un espacio. It is assumed that the line number is separated by a space
    // Read characters until a space is found. Se asume que el número de línea está separado por un espacio
    while ((c = fileIn.read()) != -1 && c != ' ') {
        result.append((char) c);
    }

    return result.toString();
}
}
