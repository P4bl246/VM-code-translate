package AuxClass.Parser;
import AuxClass.sintax_parsing.sintax_parsing; // Import the sintax_parsing class from the AuxClass.sintax_parsing package
                                               // importar la clase sintax_parsing del paquete AuxClass.sintax_parsing

//****Call classes individuals not is necesary because we call java.io.*, this import all classes from this package
//****Llamadas a clases individuales no son necesarias porque llamamos a java.io.*, esto importa todas las clases de este paquete

//import java.io.File; 
//import java.io.IOException;
//import java.io.Reader;
//import java.io.Writer;
import java.io.*;
//import java.io.FileReader;
//import java.io.FileWriter;

public class Parser {
/*
//FUNCTIONS FOR PREPARE FILES (FUNCIONES PARA LA PREPARACIÓN DE ARCHIVOS)---------------------------------------------------------------------------------

*************************************************------------------------------------------------*******************************-------------------------------

    public int RemoveSpaces(String Read_File_In);// This method is used to remove spaces from the input file(internal method)
                                                            // Este método se utiliza para eliminar espacios del archivo de entrada(Método interno)

    public int RemoveSimpleComments(String Read_File_In);// This method is used to remove simple comments from the input file(internal method)
                                                                           // Este método se utiliza para eliminar comentarios simples del archivo de entrada(Método interno)

    public String get(Reader fileIn, Readmode mode, char forNumberLine_Delimiter) throws IOException;// This method is used to obtain the line number from the input file(internal method)
                                                            // Este método se utiliza para obtener el número de línea del archivo de entrada(Método interno)

    public int RemoveBlockComments(String Read_File_In);// This method is used to remove block comments from the input file(internal method)
                                                                           // Este método se utiliza para eliminar comentarios de bloque del archivo de entrada(Método interno)

    public int RemoveNestedBlockComments(int actual, Reader ReadFile, String nLine) throws IOException;// This method is used to remove nested block comments from the input file(internal method)
                                                                                                        // Este método se utiliza para eliminar comentarios de bloque anidados del archivo de entrada(Método interno)
    
    public int RemoveVoidLines(String Read_File_In);// This method is used to remove void lines from the input file(internal method)
                                                        // Este método se utiliza para eliminar líneas vacías del archivo de entrada(Método interno)

    public int RemoveNLine(String file_in);// This method is used to remove the number line from the input file(internal method)
                                                // Este método se utiliza para eliminar el número de línea del archivo de entrada(Método interno)

    public int CleanFile(String file_in);// Clean the file of the spaces and void lines and comments,integrat the above functions, and 'NumLines')
                                         // Limpiar el archivo de espacios y líneas vacías y comentarios integrando las funciones anteriores y 'NumLines'

*************************************************------------------------------------------------*******************************------------------------------
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
public int CleanFile(String file_in){
    System.out.printf("\nCLEANING THE FILE: '%s'...\n\n", file_in);
    int n;
    n = RemoveSpaces(file_in);
    if(n != 0) return n;

    n = RemoveSimpleComments(file_in);
    if(n != 0) return n;

    n = RemoveVoidLines(file_in);
    if(n != 0) return n;
    
    n = NumLines(file_in);
    if(n != 0) return n;

    n = RemoveBlockComments(file_in);
    if(n != 0) return n;

    n = RemoveNLine(file_in);
    if(n != 0) return n;
    
    n = RemoveVoidLines(file_in);
    if(n != 0) return n;

    System.out.printf("\nTHE FILE '%s' IS CLEAN\n", file_in);
    return 0;
}
//--------------------------------------------------------------
public int RemoveSpaces(String Read_File_In){
    System.out.printf("\nREMOVING SPACES FROM THE FILE: '%s'...\n\n", Read_File_In);
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
                File temp = new File("tempWithoutSpaces.txt");
                if(temp.renameTo(inFile)){
                    System.out.printf("The file 'Temp.txt' is rename to '%s'\n", Read_File_In);
                    System.out.printf("\nTHE FILE '%s' IS CLEAN OF SPACES\n", Read_File_In);
                    return 0;
                }
                System.out.printf("Error to try rename file 'tempWithoutSpaces.txt' to '%s'\n", Read_File_In);
                return -1;
            }
            System.out.printf("Error to try eliminated the file '%s'\n", Read_File_In);
             
            return -1;
} 
//--------------------------------------------------------------
public int RemoveSimpleComments(String Read_File_In) {
    int actual;
    System.out.printf("\nREMOVING SIMPLE COMMENTS FROM THE FILE: '%s'...\n\n", Read_File_In);

    try (Reader ReadFile = new FileReader(Read_File_In);
         Writer WritteFile = new FileWriter("tempwithoutSimpleComments.txt")) {

        while ((actual = ReadFile.read()) != -1) { // Leer el primer carácter
            if ((char) actual == '/') {
                int next = ReadFile.read(); // Leer el siguiente carácter
                if (next == -1) {
                    WritteFile.write((char) actual); // Escribir el '/' si es el último carácter
                    break;
                }
                if ((char) next == '/') {
                    // Leer hasta el final de la línea
                    while ((actual = ReadFile.read()) != -1 && (char) actual != '\n') {
                        // Ignorar caracteres hasta el final de la línea
                    }
                    if (actual == '\n') {
                        WritteFile.write('\n'); // Escribir el salto de línea
                    }
                } else {
                    WritteFile.write((char) actual); // Escribir el '/'
                    WritteFile.write((char) next);   // Escribir el siguiente carácter
                }
            } else {
                WritteFile.write((char) actual); // Escribir el carácter actual
            }
        }

    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return -1; // Error
    }

    // Actualizar el archivo de entrada
    File infile = new File(Read_File_In);
    if (infile.delete()) {
        File temp = new File("tempwithoutSimpleComments.txt");
        if (temp.renameTo(infile)) {
            System.out.printf("The file 'tempwithoutSimpleComments.txt' is renamed to '%s'\n", Read_File_In);
            System.out.printf("\nTHE FILE '%s' IS CLEAN OF SIMPLE COMMENTS\n", Read_File_In);
            return 0;
        }
        System.out.printf("Error trying to rename file 'tempwithoutSimpleComments.txt' to '%s'\n", Read_File_In);
        return -1;
    }
    System.out.printf("Error trying to delete the file '%s'\n", Read_File_In);
    return -1;
}
//--------------------------------------------------------------
private int actual5; //Global variable to store the actual character (utlized just in the methods RemoveBlockComments and RemoveNestedBlockComments)
             //Variable global para almacenar el carácter actual (utilizado solo en los métodos RemoveBlockComments y RemoveNestedBlockComments)
//This variable is used to store the actual character in the method RemoveBlockComments and RemoveNestedBlockComments
//Esta variable se utiliza para almacenar el carácter actual en el método RemoveBlockComments y RemoveNestedBlockComments

public int RemoveBlockComments(String Read_File_in) {
    System.out.printf("\nREMOVING BLOCK COMMENTS FROM THE FILE: '%s'...\n\n", Read_File_in);
    actual5 = 0; // Inicializar la variable global

    try (Reader ReadFile = new FileReader(Read_File_in);
         Writer WritteFile = new FileWriter("tempWithoutBlockComments.txt");
         BufferedReader bufferedReader = new BufferedReader(ReadFile)) {

        
        while (true)  { // Mientras no se alcance el EOF
            if(actual5 == -1) break; // Si se alcanza el EOF, salir del bucle
                                    // If EOF is reached, exit the loop

            String nLine = get(ReadFile, 0, ' '); // Obtener el número de línea
            WritteFile.write(nLine);
            WritteFile.write(" ");
            actual5 = ReadFile.read(); // Leer el primer carácter
        // Leer el archivo línea por línea
        // Read the file line by line

            // Leer hasta el final de la línea
            // Read until the end of the line
             while((char)actual5 != '/' && actual5 != -1){
                WritteFile.write((char)actual5);
                actual5 = ReadFile.read(); // Leer el siguiente carácter
                                           // Read the next charactera
            }
            if(actual5 == -1) break;

            else if((char)actual5 == '/'){
                actual5 = ReadFile.read(); // Leer el siguiente carácter
                                           // Read the next character
                if(actual5 == -1){
                    WritteFile.write('/');
                    break;
                } 
                if((char)actual5 == '*'){
                    actual5 = ReadFile.read(); // Leer el siguiente carácter
                                                   // Read the next character

                    // Si se encuentra un comentario de bloque, eliminarlo
                    // If a block comment is found, remove it
                    int nr = RemoveNestedBlockComments(actual5, ReadFile, nLine);
                    if(nr != 0) return -1;
                }
                else{
                    WritteFile.write('/');
                    continue;
                }
            }
        
        }

    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return -1; // Error
    }

    // Actualizar el archivo de entrada
    File infile = new File(Read_File_in);
    if (infile.delete()) {
        File temp = new File("tempWithoutBlockComments.txt");
        if (temp.renameTo(infile)) {
            System.out.printf("The file 'tempWithoutBlockComments.txt' is renamed to '%s'\n", Read_File_in);
            System.out.printf("\nTHE FILE '%s' IS CLEAN OF BLOCK COMMENTS\n", Read_File_in);
            return 0;
        }
        System.out.printf("Error trying to rename file 'tempWithoutBlockComments.txt' to '%s'\n", Read_File_in);
        return -1;
    }
    System.out.printf("Error trying to delete the file '%s'\n", Read_File_in);
    return -1;
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
        //Continue reading the file
        //Continuar leyendo el archivo
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
public int RemoveVoidLines(String Read_File_In){
    System.out.printf("\nREMOVING VOID LINES FROM THE FILE: '%s'...\n\n", Read_File_In);
    // Open the file for reading
    // Abrir el archivo para lectura y el archivo de escritura
    try(Reader ReadFile = new FileReader(Read_File_In); Writer WritteFile = new FileWriter("tempWithoutVoidLines.txt")){
        int c = ReadFile.read(); // Read the first character
                                  // Leer el primer carácter
        //While don't find EOF (End of file)
        //Mientras no encuentre EOF (Fin de archivo)
        while(c != -1){
            while(c != -1 && (char)c != '\n' && (char)c != '\0' && (char)c != ' '){
                WritteFile.write((char)c);
                c = ReadFile.read(); // Read the next character
                                     // Leer el siguiente carácter
                if(c == -1){
                    break;
                }
                else if((char)c == '\n'){
                    WritteFile.write('\n');
                    c = ReadFile.read(); // Read the next character
                                         // Leer el siguiente carácter
                }
                //ignore it
                //ignóralo
                else if((char)c == '\0'){
                    c = ReadFile.read(); // Read the next character
                                         // Leer el siguiente carácter
                }
            }
            //if the next character is a '\n' or '\0', ginore it
            //Si el siguiente carácter es un '\n' o '\0', ignorarlo
            c = ReadFile.read(); // Read the next character
                                 // Leer el siguiente carácter
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
        File temp = new File("tempWithoutVoidLines.txt");
        if(temp.renameTo(infile)){
            System.out.printf("The file 'tempWithoutVoidLines.txt' is rename to '%s'\n", Read_File_In);
            System.out.printf("\nTHE FILE '%s' IS CLEAN OF VOID LINES\n", Read_File_In);
            return 0;
        }
        System.out.printf("Error to try rename file 'tempWithoutVoidLines.txt' to '%s'\n", Read_File_In);
        return -1;
    }
    System.out.printf("Error to try delte the file '%s'\n", Read_File_In);
    return -1;
}
//--------------------------------------------------------------
public int NumLines(String Read_File_in) {
    System.out.printf("\nADDING LINE NUMBERS TO THE FILE: '%s'...\n\n", Read_File_in);
    // Open the file for reading and the file for writing
    // Abrir el archivo para lectura y el archivo de escritura
    try (Reader ReadFile = new FileReader(Read_File_in);
         Writer writterFile = new FileWriter("fileWithNumLines.txt")) {
       
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

    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return -1;
    }
    // Upload the input file
    // Actualizar el archivo de entrada
    File infile = new File(Read_File_in);  

    if (infile.delete()) {
        File temp = new File("fileWithNumLines.txt");
        if (temp.renameTo(infile)) {
            System.out.printf("The file 'fileWithNumLines.txt' is rename to '%s'\n", Read_File_in);
            System.out.printf("\nTHE FILE '%s' IS CLEAN OF VOID LINES\n", Read_File_in);
            return 0;
        }
        System.out.printf("Error to try rename file 'fileWithNumLines.txt' to '%s'\n", Read_File_in);
        return -1;
    }
    System.out.printf("Error to try delte the file '%s'\n", Read_File_in);
    return -1;
}
//--------------------------------------------------------------
//This enum is used to define the read mode of the file for the method get
//Este enum se utiliza para definir el modo de lectura del archivo para el método get
private enum Readmode{
    NumberLine,
    CompletelyLine
}
public String get(Reader fileIn, Readmode mode,char forNumberLine_Delimiter) throws IOException {

    int c;
    StringBuilder result = new StringBuilder();
    
    // Leer caracteres hasta encontrar un espacio. Se asume que el número de línea está separado por un espacio
    // Read characters until a space is found. It is assumed that the line number is separated by a space
     if(mode == Readmode.NumberLine){
        while ((c = fileIn.read()) != -1 && c != forNumberLine_Delimiter) {
        result.append((char) c);
     }
     return result.toString();
    }
    else if(mode == Readmode.CompletelyLine)){
        while ((c = fileIn.read()) != -1 && c != '\n') {
            result.append((char) c);
        }
        return result.toString();
    }
    // Si no se especifica el número de línea ni la línea completa, se devuelve null
    // If neither the line number nor the complete line is specified, return null
    return null;
}
//--------------------------------------------------------------
public int RemoveNLine(String file_in){
    System.out.printf("\nFINAL CLEANING THE FILE: '%s'...\n\n", file_in);
    // open the file for reading and the file for writing
    // Abrir el archivo para lectura y el archivo de escritura
    try(Reader readFile = new FileReader(file_in); Writer WrtterFile = new FileWriter("tempFinalClean.txt")){
        int c = 0;
        while(c != -1){
            //Get the number line and ignore it
            //Obtener el número de línea y ignorarlo
            get(readFile, 0, ' ');
            c = readFile.read(); // Read the next character
                                    // Leer el siguiente carácter
           if(c == -1) break;
           if((char)c == '\n' || (char)c == '\0' || (char)c == ' '){
            WrtterFile.write('\n');
            while(c != -1 && (char)c != '\n'){
                c = readFile.read(); // Read the next character
                                    // Leer el siguiente carácter
            }
            continue;
           }
           //if find a character after the number line, write the line without the number line
           //si encuentra un carácter después del número de línea, escriba la línea sin el número de línea
           else{
            while(c != -1 && (char)c != '\n'){
                WrtterFile.write((char)c);
                c = readFile.read(); // Read the next character
                                    // Leer el siguiente carácter
             }
             if(c == -1) break;
            WrtterFile.write('\n');
           }

       } 
    }
    catch(IOException e){
        System.out.println("Error: " + e.getMessage());
        return -1; // Error
    }
    //Upload the input file
    //Actualizar el archivo de entrada
    File infile = new File(file_in);
    if(infile.delete()){
        File temp = new File("tempFinalClean.txt");
        if(temp.renameTo(infile)){
            System.out.printf("The file 'tempFinalClean.txt' is rename to '%s'\n", file_in);
            System.out.printf("\nTHE FILE '%s' IS CLEAN TO THE VOID LINES AND NUMBER LINE\n", file_in);
            return 0;
        }
        System.out.printf("Error to try rename file 'tempFinalClean.txt' to '%s'\n", file_in);
        return -1;
    }
    System.out.printf("Error to try delte the file '%s'\n", file_in);
    return -1;
}
//--------------------------------------------------------------
//END THE PROCCES TO PREPARE FILES(TERMINA EL PROCESO DE PREPARACIÓN DE ARCHIVOS)--------------------------------------------------------------


//STARTS THE PROCESS OF PARSING SINTAX (INICIA EL PROCESO DE ANÁLISIS SINTÁCTICO)-----------------------------------------------------------------
}

