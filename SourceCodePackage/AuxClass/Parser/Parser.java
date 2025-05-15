package AuxClass.Parser;
//import AuxClass.sintax_parsing.sintax_parsing; // Import the sintax_parsing class from the AuxClass.sintax_parsing package
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

    public int RemoveString(String Read_File_In, String Delimiter);// This method is used to remove spaces from the input file(internal method)
                                                            // Este método se utiliza para eliminar espacios del archivo de entrada(Método interno)

    public int RemoveSimpleComments(String Read_File_In, String Delmiter);// This method is used to remove simple comments from the input file(internal method)
                                                                           // Este método se utiliza para eliminar comentarios simples del archivo de entrada(Método interno)

    public String get(Reader fileIn, Readmode mode, char forNumberLine_Delimiter) throws IOException;// This method is used to obtain the line number or completely line from the input file(internal method)
                                                            // Este método se utiliza para obtener el número de línea del archivo de entrada o su linea completa(Método interno)

    public int RemoveBlockComments(String Read_File_In, String Delimiter, Character DelimiterNumLine);// This method is used to remove block comments from the input file, and can obtanin the number line if you want(internal method)
                                                                                                     // Este método se utiliza para eliminar comentarios de bloque del archivo de entrada, y puede obtener el numero de linea si lo desea(Método interno)

    public int RemoveNestedBlockComments(String line, Reader ReadFile, String nLine,String delimiter, String delmiterEnd, String DelimiterNumLine, int indexActualLine) throws IOException;// This method is used to remove nested block comments from the input file(internal method)
                                                                                                        // Este método se utiliza para eliminar comentarios de bloque anidados del archivo de entrada(Método interno)
    
    public int RemoveVoidChars(String Read_File_In, Character Delimiter);// This method is used to remove void lines or characters from the input file(internal method)
                                                        // Este método se utiliza para eliminar líneas vacías o caracteres del archivo de entrada(Método interno)

    public int RemoveNLine(String file_in);// This method is used to remove the number line from the input file(internal method)
                                                // Este método se utiliza para eliminar el número de línea del archivo de entrada(Método interno)

    public int CleanFile(String file_in);// Clean the file of the spaces and void lines and comments,integrat the above functions, and 'NumLines')
                                         // Limpiar el archivo de espacios y líneas vacías y comentarios integrando las funciones anteriores y 'NumLines'

*************************************************------------------------------------------------*******************************------------------------------
    public int NumLines(File Read_File_in, File Writte_File_out);// This method is used to add the line number to the input file(internal method)
                                                            // Este método se utiliza para agregar el número de línea al archivo de entrada(Método interno)
------------------------------------------------------------------------------------------------------------------------------
    public int File_to_txt(String file_In, String nameOfNewFileWithFormat);// This method is used to convert the file to txt format(internal method)
                                             // Este método se utiliza para convertir el archivo a formato txt(Método interno)
------------------------------------------------------------------------------------------------------------------------------
    public int PrepareFiles();

//END OF FUCNTION TO PREPARE FILES(FIN DE LAS FUNCIONES PARA LA PREPARACIÓN DE ARCHIVOS)---------------------------------------------------------------------------------
*/



//STARTS THE PROCESS OF PREPARE FILES (INICIA EL PROCESO DE PREPARACIÓN DE ARCHIVOS)----------------------------------------------------------------- 

public int File_to_txt(String file_In, String nameOfNewFileWithFormat) {
    
    // Open the file_In in reading and create a temporary file for writing
    // Abrir el archivo de entrada en modo lectura y el archivo temporal en modo escritura
    try (FileReader fileP = new FileReader(file_In); Writer tempFile = new FileWriter(nameOfNewFileWithFormat)) {
        int c;
        // Read character by character from the input file and write to the temporary file
        // Leer carácter por carácter del archivo de entrada y escribir en el archivo temporal
        while ((c = fileP.read()) != -1) {
            tempFile.write((char)c);
        }
        return 0; // Éxito
        }
      catch (IOException e) {
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
    n = RemoveVoidChars(file_in, null);
    if(n != 0) return n;

    n = RemoveSimpleComments(file_in, "//");
    if(n != 0) return n;
  
    n = RemoveVoidChars(file_in, null);
    if(n != 0) return n;
    
    n = NumLines(file_in);
    if(n != 0) return n;

    n = RemoveBlockComments(file_in, "/*", "*/", " ");
    if(n != 0) return n;

    n = RemoveNLine(file_in);
    if(n != 0) return n;
    
    n = RemoveVoidLines(file_in);
    if(n != 0) return n;

    System.out.printf("\nTHE FILE '%s' IS CLEAN\n", file_in);
    return 0;
}
//--------------------------------------------------------------
public int RemoveString(String Read_File_In, String Delimiter) {
    System.out.printf("\nREMOVING STRING '%s' FROM THE FILE: '%s'...\n\n", Delimiter, Read_File_In);

    try (BufferedReader reader = new BufferedReader(new FileReader(Read_File_In));
         BufferedWriter writer = new BufferedWriter(new FileWriter("tempWithoutString.txt"))) {

        String line;
        while ((line = reader.readLine()) != null) {
            String cleanedLine = line.replace(Delimiter, "");
            writer.write(cleanedLine);
            writer.newLine();
        }

    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return -1;
    }

    File originalFile = new File(Read_File_In);
    File tempFile = new File("tempWithoutString.txt");

    if (originalFile.delete()) {
        if (tempFile.renameTo(originalFile)) {
            System.out.printf("The file was cleaned and renamed to '%s'\n", Read_File_In);
            return 0;
        } else {
            System.out.println("Error renaming temp file.");
            return -1;
        }
    } else {
        System.out.println("Error deleting original file.");
        return -1;
    }
}
//--------------------------------------------------------------
public int searchString(boolean searchAll, String line, String searchThis, int startIndex, Character delimiter) {
    if (line == null || searchThis == null || startIndex < 0) {
        System.err.printf("Error: Invalid input parameters.\nDETAILS: line: %s, searchThis: %s, Index: %d, sizeOfLine: %d\n", line, searchThis, startIndex, line.length());
        return -3;
    }
   if (startIndex >= line.length()) return -2;

    int count = 0;
    for (int i = startIndex; i <= line.length() - searchThis.length(); i++) {
        if (line.charAt(i) == delimiter) break;
        
        boolean match = true;
        for (int j = 0; j < searchThis.length(); j++) {
            if (i + j >= line.length() || line.charAt(i + j) != searchThis.charAt(j)) {
                match = false;
                break;
            }
        }

        if (match) {
            if (!searchAll) {
                System.out.println("String found at index " + i);
                return i;
            }
            count++;
            i += searchThis.length() - 1; // Avoid overlapping matches
        }
    }

    if (searchAll) return count;
    System.out.println("String not found.");
    return -1;
}
//--------------------------------------------------------------
public int RemoveSimpleComments(String Read_File_In, String SimpleCommentIdent) {
    int actual;
    System.out.printf("\nREMOVING SIMPLE COMMENTS FROM THE FILE: '%s'...\n\n", Read_File_In);

    try (Reader ReadFile = new FileReader(Read_File_In);
         Writer WritteFile = new FileWriter("tempwithoutSimpleComments.txt")) {
        StringBuilder line = new StringBuilder();
        while ((actual = ReadFile.read()) != -1) { // Leer el primer carácter
                                                   //Read the first character
           line.setLength(0);
          int n = 0;  
          while((char)actual != '\n' && actual != -1){
                line.append((char)actual);
                actual = ReadFile.read();
          }
          if(actual == -1) break;
          String h = line.toString();
          if((n = searchString(false, h , SimpleCommentIdent, 0, null)) != -1){
            //Writter the character out of the simple comment
            //Escribir los caracters fuera de el comentario simple
            for(int i = 0; i < n; i++){
              WritteFile.write(h.charAt(i)); 
            }
            //Ignore the character after of the simple coment
            // Ignorar los caracteres despues de el comentario simple
            
               WritteFile.write('\n'); // Escribir el salto de línea
                                       // Writte the jump of line
          }
          //Escribir la línea
          //Write the line
          else WritteFile.write(h + '\n');
        }

    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return -1; // Error
    }
     //Upload the input file
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
public class MutableTypeData<T> {
    private T valor;
    
    public MutableTypeData(T valor) {
        this.valor = valor;
    }
    
    public T getValor() {
        return valor;
    }
    
    public void setValor(T valor) {
        this.valor = valor;
    }
    
    public Class<?> getTipo() {
        return valor.getClass();
    }
}

public int RemoveBlockComments(String Read_File_in, String Delimiter, String delimiterEnd, Character DelimiterNumLine) {
  MutableTypeData<Integer>actual5 = new MutableTypeData<>(0);
MutableTypeData<String>line5 = new MutableTypeData<>("");
  //Globals variables to store the actual character and line (utlized just in the methods RemoveBlockComments and RemoveNestedBlockComments)
  //Variables globales para almacenar el carácter actual y linea (utilizado solo en los métodos RemoveBlockComments y RemoveNestedBlockComments)
    System.out.printf("\nREMOVING BLOCK COMMENTS FROM THE FILE: '%s'...\n\n", Read_File_in);
   if(Delimiter == null){
     System.err.println("Error: Need put a delimiter\n");
     return -1;
   }
  if (delimiterEnd == null) {
    System.err.println("Error: delimiterEnd is required.\n");
    return -1;
    }
    actual5.setValor(0); // Inicializar la variable global
    line5.setValor(null);

    try (Reader ReadFile = new FileReader(Read_File_in);
         Writer WritteFile = new FileWriter("tempWithoutBlockComments.txt")) {
         
        StringBuilder line = new StringBuilder();
        while (true)  { // Mientras no se alcance el EOF
           line.setLength(0);
            String nLine = null;
            if(actual5.getValor()  == -1) break; // Si se alcanza el EOF, salir del bucle
                                    // If EOF is reached, exit the loop
           if(DelimiterNumLine != null){
            nLine = get(ReadFile, Readmode.NumberLine, DelimiterNumLine); // Obtener el número de línea
            WritteFile.write(nLine);
            WritteFile.write(DelimiterNumLine);
            actual5.setValor(ReadFile.read()); // Leer el primer carácter
           }
        // Leer el archivo línea por línea
        // Read the file line by line
          int r = 0, n = 0;
           while(actual5.getValor() != -1 && ((char)((int)actual5.getValor()))  != '\n' && (n = searchString(false, line, Delimiter, r, null)) == -1){
                line.append((char)((int)actual5.getValor()));
                actual5.setValor(ReadFile.read());
             r++;
           }
           if(actual5.getValor() == -1) break;
           else if(n == -1 && ((char)((int)actual5.getValor())) == '\n'){
             WritteFile.write(line.toString() + '\n');
             actual5.serValor(ReadFile.read());
             continue;
           }
           else{
            int i;
                for(i = 0; i < n; i++) WritteFile.write(line.charAt(i));
                line5.setValor(line.toString());
                    // Si se encuentra un comentario de bloque, eliminarlo
                    // If a block comment is found, remove it
                    int nr = RemoveNestedBlockComments(line5, actual5, ReadFile, nLine,Delimiter, delimiterEnd, DelimiterNumLine, (i+Delimiter.length()));
                    if(nr != 0) return -1;
           }
          while(((char)((int)actual5.getValor())) != '\n' && actual5.getValor() != -1) WritteFile.write((char)((int)actual5.getValor()));
  
          if(((char)((int)actual5.getValor())) == '\n'){
            actual5.setValor(ReadFile.read());
               WritteFile.write('\n');
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
public int RemoveNestedBlockComments(MutableTypeData<String> line, MutableTypeData<Integer> actual, Reader ReadFile, String nLine,String delimiter, String delmiterEnd, String DelimiterNumLine, int indexActualLine) throws IOException{
    //Read until the end of comment
    //Leer hasta el final del comentario
    while(actual.getValor()  != -1){
      int n = 0, r = 0;  
      while(actual.getValor()  != -1 && ((char)((int)actual.getValor()))  != '\n' && (n = searchString(false, line.getValor(), delimiterEnd, indexActualLine, null)) == -1 && (r = searchString(false, line.getValor(), delimiter, indexActualLine)) == -1){
         actual.setValor(ReadFile.read());
         indexActualLine++;
      }
      // If find the end of file without closing the comment
        // Si encuentra el final del archivo sin cerrar el comentario
        if(actual.getValor()  == -1) continue;
      //If find the end of the line actual
      //Si llega a el finla de la línea actual
      //Upload the parameters, uploading the line, the index, and the number of line actual
      //Actualizar los parametros, actualizando la linea, el indice y el numbero de linea actual
      if(n == -3 || r == -3){
        //Get the number of line if it has
        //Obtener el numbero de linea si lo tiene
         if(DelimiterNumLine != null){
            nLine = get(ReadFile, Readmode.NumberLine, DelimiterNumLine); // Obtener el número de línea
            actual.setValor(ReadFile.read()); // Leer el primer carácter
           }
  
          StringBuilder newLine = new StringBuilder();
        //Check if the character actual is the end of the before line
        //Revisar si el caracter actual es el final de la linea anteriror
        if(((char)((int)actual.getValor()))  == '\n') actual.setValor(ReadFile.read());
        //If not have more lines
        //Si no hay mas lineas
        if(actual.getValor()  == -1) continue;
        //Upload the parameters, uploading the line, the index
        //Actualizar los parametros, actualizando la linea, el indice
         while(((char)((int)actual.getValor()))  != '\n' && actual.getValor() != -1){
           actual.setValor(ReadFile.read());
           newLine.append((char)((int)actual.getValor()));
         }
        line.setValor(newLine.toString());
        indexActualLine = 0;
        continue;
      }
      if(n == 0) return 0;
      //if has a nested comment block
      //Si tiene un comentario en bloque anidado
      if(r == 0){
         if(RemoveNestedBlockComments(line, actual, ReadFile, nLine, delimiter, delimiterEnd, DelimiterNumLine, indexActualLine) != 0) return -1;
      }
    }
  // If find the end of file without closing the comment
        // Si encuentra el final del archivo sin cerrar el comentario
        if(actual.getValor()  == -1){
            System.err.println("Error in the line: "+ nLine +"\nDETAILS:End of file without closing comment\n");
        }
  return 0;
  }
//--------------------------------------------------------------
public int RemoveVoidChars(String Read_File_In, Character VoidCharacterStart){
    System.out.printf("\nREMOVING VOID CHARS FROM THE FILE: '%s'...\n\n", Read_File_In);
    // Open the file for reading
    // Abrir el archivo para lectura y el archivo de escritura
    try(Reader ReadFile = new FileReader(Read_File_In); Writer WritteFile = new FileWriter("tempWithoutVoidChars.txt")){
        int c = ReadFile.read(); // Read the first character
                                  // Leer el primer carácter
        //While don't find EOF (End of file)
        //Mientras no encuentre EOF (Fin de archivo)
        while(c != -1){
            while(c != -1 && (char)c != '\0' && (char)c != ' ' && (char)c != VoidCharacterStart){
                WritteFile.write((char)c);
                c = ReadFile.read(); // Read the next character
                                     // Leer el siguiente carácter
            }
                if(c == -1){
                    break;
                }
                //ignore it
                //ignóralo
                else if((char)c == '\0' || (char)c == VoidCharacterStart || (char)c == ' '){
                   c = ReadFile.read(); // Read the next character
                                         // Leer el siguiente carácter
                }
            //if the next character is a '\n' or '\0'or VoidCharacterStart, ginore it
            //Si el siguiente carácter es un '\n' o '\0' o VoidCharacterStart, ignorarlo
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
        File temp = new File("tempWithoutVoidChars.txt");
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
public enum Readmode{
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
    else if(mode == Readmode.CompletelyLine){
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
            get(readFile, Readmode.NumberLine, ' '); 
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

