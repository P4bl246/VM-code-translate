package AuxClass.Parser;
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
    public int searchString(boolean searchAll, String line, String searchThis, int startIndex, Character delimiter, boolean WatchMessages)//method for search a string in a line
                                                                                                                                         //método para buscar una cadena en una linea

    public int RemoveString(String Read_File_In, String Delimiter);// This method is used to remove spaces from the input file(internal method)
                                                            // Este método se utiliza para eliminar espacios del archivo de entrada(Método interno)

    public int RemoveSimpleComments(String Read_File_In, String Delmiter);// This method is used to remove simple comments from the input file(internal method)
                                                                           // Este método se utiliza para eliminar comentarios simples del archivo de entrada(Método interno)

    public String get(Reader fileIn, Readmode mode, Character forNumberLine_Delimiter, MutableTypeData<Integer> actual, MutableTypeData<Boolean> containsBeforeEOForEndLine) throws IOException;// This method is used to obtain the line number or completely line from the input file(internal method)
                                                            // Este método se utiliza para obtener el número de línea del archivo de entrada o su linea completa(Método interno)

    public int RemoveBlockComments(ReadmodeBlock mode, String Read_File_in, String Delimiter, String delimiterEnd, Character DelimiterNumLine);// This method is used to remove block comments from the input file, and can obtanin the number line if you want(internal method)
                                                                                                     // Este método se utiliza para eliminar comentarios de bloque del archivo de entrada, y puede obtener el numero de linea si lo desea(Método interno)

    public int RemoveNestedBlockComments(ReadmodeBlock mode, MutableTypeData<String> line, Reader ReadFile, String nLine,String delimiter, String delimiterEnd, Character DelimiterNumLine, MutableTypeData<Integer> indexActualLine, MutableTypeData<Integer> CountOfLinePass, boolean NestedJobFlag, boolean itsMultiLine) throws IOException;// This method is used to remove nested block comments from the input file(internal method)
                                                                                                        // Este método se utiliza para eliminar comentarios de bloque anidados del archivo de entrada(Método interno)
    
    public int RemoveVoidChars(String Read_File_In, Character VoidCharacterStart);// This method is used to remove void lines or characters from the input file(internal method)
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
    
    n = NumLines(file_in);
    if(n != 0) return n;

    n = RemoveBlockComments(ReadmodeBlock.NestedEnd, file_in, "/*", "*/", ' ');
    if(n != 0) return n;
    
    n = RemoveSimpleComments(file_in, "//");
    if(n != 0) return n;
    
    n = RemoveNLine(file_in, ' ');
    if(n != 0) return n;

    n = RemoveVoidChars(file_in, null);
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
public int searchString(boolean searchAll, String line, String searchThis, int startIndex, Character delimiter, boolean WatchMessages) {
    if (line == null || searchThis == null || startIndex < 0) {
        System.err.printf("Error: Invalid input parameters.\nDETAILS: line: %s, searchThis: %s, Index: %d, sizeOfLine: %d\n", line, searchThis, startIndex, line.length());
        return -2;
    }
   if (startIndex >= line.length()) return -3;

    int count = 0;
    for (int i = startIndex; i <= line.length() - searchThis.length(); i++) {
        if(delimiter != null) if (line.charAt(i) == delimiter) break;
        
        boolean match = true;
        for (int j = 0; j < searchThis.length(); j++) {
            if (i + j >= line.length() || line.charAt(i + j) != searchThis.charAt(j)) {
                match = false;
                break;
            }
        }

        if (match) {
            if (!searchAll) {
             if(WatchMessages)  System.out.println("String found at index " + i);
                return i;
            }
            count++;
            i += searchThis.length() - 1; // Avoid overlapping matches
        }
    }

    if (searchAll) return count;
    if(WatchMessages) System.out.println("String not found.");
    return -1;
}
//--------------------------------------------------------------
public int RemoveSimpleComments(String Read_File_In, String SimpleCommentIdent) {
    
    System.out.printf("\nREMOVING SIMPLE COMMENTS FROM THE FILE: '%s'...\n\n", Read_File_In);

     try (BufferedReader ReadFile = new BufferedReader(new FileReader(Read_File_In));
         BufferedWriter WritteFile = new BufferedWriter(new FileWriter("tempwithoutSimpleComments.txt"))) {
        
        String line;
        while ((line = ReadFile.readLine()) != null) {
            // Buscar comentario en la línea actual
            //Search the delimiter in the actual line
            int commentPosition = searchString(false, line, SimpleCommentIdent, 0, null, false);
            
            if (commentPosition != -1 && commentPosition != -2 && commentPosition != -3) {
                // Si hay un comentario, escribir solo la parte antes del comentario
                // if has a comment, writte just the part before of the delimiter
                String codePart = line.substring(0, commentPosition);
                WritteFile.write(codePart);
            } else {
                // Si no hay comentario, escribir la línea completa
                // If not has a comment, writte the line
                WritteFile.write(line);
            }
            
            // Añadir salto de línea (utiliza el separador de línea del sistema)
            // Add jump of line (use the separator of the line for the system)
            WritteFile.newLine();
        }
        
        WritteFile.flush();
        WritteFile.close();
        ReadFile.close();

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
//class for simulate the pass for identify
//clase para simular el paso por identificador
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

public int RemoveBlockComments(ReadmodeBlock mode, String Read_File_in, String Delimiter, String delimiterEnd, Character DelimiterNumLine) {

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
    line5.setValor(null);
    String nLine = null;
    try (BufferedReader ReadFile = new BufferedReader(new FileReader(Read_File_in));
         BufferedWriter WritteFile = new BufferedWriter(new FileWriter("tempWithoutBlockComments.txt"))) {
            String line;
            //search in the completely file and remove
            //buscar en el archivo completo y eliminar
            while((line = ReadFile.readLine()) != null){
                if(line.equals("") || line.equals("\n") || line.equals("\0")) continue;
                line5.setValor(line);
                int n; 
                //if find the delimiter that indicate the start of comment
                //si encuentra el delimitador que indica el inicio de un comentario
                if((n = searchString(false, line5.getValor(), Delimiter, 0, null, false)) != -1){
                    if(n == -2 || n == -3) return -1; //Error
                    else{
                        //Get the number line
                        //obtener el numero de linea
                        if(DelimiterNumLine != null){
                            int h = line5.getValor().indexOf(DelimiterNumLine);
                            nLine = line5.getValor().substring(0, h);
                        }
                        //copy all before of the start of the delimiter 
                        //copiar todo antes de el incio de el delimitador
                        String getString = line5.getValor().substring(0, n);
                        WritteFile.write(getString);
                        //variables for upload the actual index and know the number of lines proccesed
                        //variables para actualizar el inidce actual y saber cuantaas lineas fueron procesadas
                        MutableTypeData<Integer> index = new MutableTypeData<>(n);
                        MutableTypeData<Integer> LinesJump = new MutableTypeData<Integer>(0);
                        MutableTypeData<String> between = new MutableTypeData<>("");
                        MutableTypeData<Integer>last = new MutableTypeData<>(0);
                        switch (mode){
                            case NestedEnd:
                               if(RemoveNestedBlockComments(ReadmodeBlock.NestedEnd, line5, ReadFile, nLine, Delimiter, delimiterEnd, DelimiterNumLine, index, LinesJump, false, between, last) != 0) return -1;
                               break;
                            case SingleEnd: 
                               if(RemoveNestedBlockComments(ReadmodeBlock.SingleEnd, line5, ReadFile, nLine, Delimiter, delimiterEnd, DelimiterNumLine, index, LinesJump, false, between, last) != 0 ) return -1;
                               break;
                            default:
                               System.err.println("Error in the argument 'mode'\n");
                               return -1;
                            }
                            //conseverd the structure of the file including the prosecced lines
                            //conservar la estructure de el archivo incluyendo las lineas procesadas                        
                            for(int i= 0; i < LinesJump.getValor(); i++){
                            WritteFile.newLine();
                        }
                        if(!(between.getValor().equals(""))) WritteFile.write(" "+between.getValor());
                            
                       //get all the content after the end of comment
                       //obtener todo lo que hay despues de el comentario
                        if(mode == ReadmodeBlock.NestedEnd && line5.getValor().indexOf(last.getValor()) != '\n' && line5.getValor().indexOf(last.getValor()) != '\0') getString= line5.getValor().substring((last.getValor()+1), line5.getValor().length());
                        else if(mode == ReadmodeBlock.SingleEnd) getString = line5.getValor().substring((index.getValor()+1), line5.getValor().length());
                        if(!getString.equals("")){
                            WritteFile.write(" " + getString);//if are a void line(""), write a newLine
                            WritteFile.newLine();
                        }
                    }
                }
                //write the completely line
                //escribir la linea completa
                else{
                    WritteFile.write(line5.getValor());//Escrbir la linea
                    WritteFile.newLine();
                }
                
            }
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return -1; // Error
    }

    // Actualizar el archivo de entrada
    //Upload the input file
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
/*mode for know how proccess the block comments
*SingleEnd: search only the first close of the comment, independent if are nested block comments(not check the content, not are important)
*NestedEnd: search and verify that all nested comments are closed correctly(check the content and are important)
*/
/*modo para saber como procesar los comentarios en bloque
 * SingleEnd: busca solo el primer cierre de el comentario, independientemente de si hay comentarios andidados(no interesa el contenido)
 * NestedEnd: busca y verifica que todos los comentarios anidados este cerrados correctamente(interesa el conteindo)
 */
public enum ReadmodeBlock{
    NestedEnd,
    SingleEnd
}

public int RemoveNestedBlockComments(ReadmodeBlock mode, MutableTypeData<String> line, Reader ReadFile, String nLine,String delimiter, String delimiterEnd, Character DelimiterNumLine, MutableTypeData<Integer> indexActualLine, MutableTypeData<Integer> CountOfLinePass, boolean itsMultiLine,  MutableTypeData<String> BetweenComments, MutableTypeData<Integer> LastEndOfcomment) throws IOException{
    MutableTypeData<Integer> actual = new MutableTypeData<>(0);
    //Read until the end of comment
    //Leer hasta el final del comentario
    while(true){
      int n = 0, r = 0; //variables for watch the result of the search //variables para ver el resultado de la busqueda
      //search the delimiter that indicate the start of the comment
      //Buca el delimitador que indica el iniciio de un comentario
      r = searchString(false, line.getValor(), delimiter, indexActualLine.getValor(), null, false); 
     int m = r;
      //remove the delimiter readed and upload de line
      //eliminar el delimitador ya leido y actualizar la linea
        if(r >= 0 && r != line.getValor().length()){ 
            StringBuilder newLine = new StringBuilder(line.getValor());
            newLine.delete(r, r+delimiter.length());
            line.setValor(newLine.toString());
            indexActualLine.setValor(0);
        }
         //Search the delimiter of end comment
    //buscar el delimitador de final de comentario
        n = searchString(false, line.getValor(), delimiterEnd, indexActualLine.getValor(), null, false);
        
        //remove the delimiter after readed
        //eliminar el delimitador una vez leido
        if(mode == ReadmodeBlock.NestedEnd && n >= 0 && n != line.getValor().length()){ 
            StringBuilder newLine = new StringBuilder(line.getValor());
            newLine.delete(n, n+delimiterEnd.length());
            line.setValor(newLine.toString());
            indexActualLine.setValor(0);
        }
        //search the delimiter in the uploading line, for search nested comments
        //buscar el delimitador en la linea actualizada, para buscar comentarios anidados
    r = searchString(false, line.getValor(), delimiter, indexActualLine.getValor(), null, false); 

        if(n == -2 || r == -2 || (n == -3 || r == -3)) return -1; //error in the parameters 
         
        //if not find in this line
        //si no encuentra en esta line
      //Upload the parameters, uploading the line, the index, and the number of line actual
      //Actualizar los parametros, actualizando la linea, el indice y el numbero de linea actual
      else if(n == -1 && r == -1){
        //Get the number of line if it has
        //Obtener el numero de linea si lo tiene
        actual.setValor((int)line.getValor().length());

        while((char)((int)actual.getValor()) == '\n') actual.setValor(ReadFile.read()); 
        
         if(DelimiterNumLine != null){
            int h = line.getValor().indexOf(DelimiterNumLine);
            nLine = line.getValor().substring(0, h);
        }
        //Check if the character actual is the end of the before line
        //Revisar si el caracter actual es el final de la linea anteriror
        MutableTypeData<Boolean> contain = new MutableTypeData<>(false);
        line.setValor(get(ReadFile, Readmode.CompletelyLine, DelimiterNumLine, actual, contain));
            //If find EOF and not are content before
        //Si se llego a EOF y no habia contenido antes
        if(line.getValor().equals("") || line.getValor().equals("\n") || line.getValor().equals("\0") && !contain.getValor()) break;
        //Upload the parameters, uploading the index
        //Actualizar los parametros, actualizando el indice
        indexActualLine.setValor(0);
        if(CountOfLinePass != null){
        int moreLine = CountOfLinePass.getValor()+1;
        CountOfLinePass.setValor(moreLine);
        }
        itsMultiLine = true;
        continue;
      } 

      //if has a nested comment block
      //Si tiene un comentario en bloque anidado
      if(r >= 0 || (itsMultiLine && m >= 0)){
        //Dependend the mode
        //Dependiendo de el modo
        switch (mode){
          case NestedEnd:
          if(BetweenComments != null && n >= 0){
            String newl;
            if(r != -1) newl = line.getValor().substring(n, r);//get the string between comments
            else newl = line.getValor().substring(n, line.getValor().length());
            BetweenComments.setValor(newl);
        }
         if(RemoveNestedBlockComments(ReadmodeBlock.NestedEnd, line, ReadFile, nLine, delimiter, delimiterEnd, DelimiterNumLine, indexActualLine, CountOfLinePass,  itsMultiLine,  null, LastEndOfcomment) != 0) return -1;
           break;
          case SingleEnd: 

          if(RemoveNestedBlockComments(ReadmodeBlock.SingleEnd, line, ReadFile, nLine, delimiter, delimiterEnd, DelimiterNumLine, indexActualLine, CountOfLinePass, itsMultiLine,  null, LastEndOfcomment) != 0 ) return -1;
          break;
          default:
          System.err.println("Error in the argument 'mode'\n");
          return -1;
      }
    }
       if(n >= 0){
       if(indexActualLine.getValor() != 0 && mode == ReadmodeBlock.NestedEnd)LastEndOfcomment.setValor(indexActualLine.getValor());//upload with the index of the las end comment delimiter
       indexActualLine.setValor(n-1);
        return 0;
      } 
 }
  // If find the end of file without closing the comment
    // Si encuentra el final del archivo sin cerrar el comentario
            System.err.println("Error in the line: "+ nLine +"\nDETAILS:Find the 'End of file' and don't closing a comment block\n");
            return -1;
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
            if(VoidCharacterStart != null){
            while(c != -1 && (char)c != '\0' && (char)c != ' ' && (char)c != '\t' && (char)c != '\r' && (char)c !='\f' && (char)c != '\u000B' && (char)c != '\u2028'  && (char)c != VoidCharacterStart){
                WritteFile.write((char)c);
                c = ReadFile.read(); // Read the next character
                                     // Leer el siguiente carácter
            }
                if(c == -1){
                    break;
                }
                //ignore it
                //ignóralo
                else{
                   c = ReadFile.read(); // Read the next character
                                         // Leer el siguiente carácter
                  continue;
                }
        }
        else {
            while(c != -1 && (char)c != '\0' && (char)c != ' ' && (char)c != '\t' && (char)c != '\r' && (char)c !='\f' && (char)c != '\u000B' && (char)c != '\u2028'){
                WritteFile.write((char)c);
                c = ReadFile.read(); // Read the next character
                                     // Leer el siguiente carácter
            }
                if(c == -1){
                    break;
                }
                //ignore it
                //ignóralo
                else{
                   c = ReadFile.read(); // Read the next character
                                         // Leer el siguiente carácter
                   continue;
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
        File temp = new File("tempWithoutVoidChars.txt");
        if(temp.renameTo(infile)){
            System.out.printf("The file 'tempWithoutVoidLines.txt' is rename to '%s'\n", Read_File_In);
            System.out.printf("\nTHE FILE '%s' IS CLEAN OF VOID CHARS\n", Read_File_In);
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
public String get(Reader fileIn, Readmode mode, Character forNumberLine_Delimiter, MutableTypeData<Integer> actual, MutableTypeData<Boolean> containsBeforeEOForEndLine) throws IOException {
    if(fileIn == null){ 
        System.err.println("Error: Need put a argument in parameter 'fileIn'\n");
        return "ERROR";
    }
    
    int c = 0;
    if(actual != null) {
        c = actual.getValor();
    } else {
        c = fileIn.read(); // Lee el primer carácter
        
        // Filtrar BOM si existe
        if (c == 0xFEFF) {
            c = fileIn.read(); // Leer el siguiente carácter después del BOM
        }
    }
    
    StringBuilder result = new StringBuilder();
    if(containsBeforeEOForEndLine != null) {
        containsBeforeEOForEndLine.setValor(false);
    }
    
    if(mode == Readmode.NumberLine) {
        while (c != -1 && (char)c != forNumberLine_Delimiter) {
            result.append((char)c);
            if(containsBeforeEOForEndLine != null) {
                containsBeforeEOForEndLine.setValor(true);
            }
            if(actual != null) {
                actual.setValor(c);
            }
            c = fileIn.read();
        }
        return result.toString();
    }
    else if(mode == Readmode.CompletelyLine) {
        while (c != -1 && (char)c != '\n') {
            // Solo agregar caracteres imprimibles o espacios
            if ((c >= 32 && c <= 126) || c == 9 || c == 32) { // ASCII imprimible o tabs/espacios
                result.append((char)c);
            }
            
            if(containsBeforeEOForEndLine != null) {
                containsBeforeEOForEndLine.setValor(true);
            }
            if(actual != null) {
                actual.setValor(c);
            }
            c = fileIn.read();
        }
        return result.toString();
    }
    
    return null;
}
//--------------------------------------------------------------
public int RemoveNLine(String file_in, Character delimiter){
    System.out.printf("\nFINAL CLEANING THE FILE: '%s'...\n\n", file_in);
    // open the file for reading and the file for writing
    // Abrir el archivo para lectura y el archivo de escritura
    try(Reader readFile = new FileReader(file_in); Writer WrtterFile = new FileWriter("tempFinalClean.txt")){
        int c = 0;
        while(true){
            //Get the number line and ignore it
            //Obtener el número de línea y ignorarlo
            get(readFile, Readmode.NumberLine, delimiter, null, null); 
            c = readFile.read(); // Read the next character
                                    // Leer el siguiente carácter
           if(c == -1) break;
           if((char)c == '\n' || (char)c == '\0'){
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

