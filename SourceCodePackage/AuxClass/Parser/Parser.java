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
import java.util.ArrayList;

/**
<p><b>FUNCTIONS FOR PREPARE FILES (FUNCIONES PARA LA PREPARACIÓN DE ARCHIVOS)</b>---------------------------------------------------------------------------------</p>

<b>IMPORTANT:</b>All market like a label 'internal method' refer this function are used for the principal(CleanFile) o other functions
<p><b>*************************************************------------------------------------------------*******************************-------------------------------</b></p>
    <p><b>public int searchString(boolean searchAll, String line, String searchThis, int startIndex, Character delimiter, boolean WatchMessages);</b>//method for search a string in a line
                                                                                                                                         //método para buscar una cadena en una linea</p>

    <p><b>public int RemoveString(String Read_File_In, String Delimiter);</b>// This method is used to remove spaces from the input file(internal method)<br>
                                                            // Este método se utiliza para eliminar espacios del archivo de entrada(Método interno)</p>

    <p><b>public int RemoveSimpleComments(String Read_File_In, String Delmiter);</b>// This method is used to remove simple comments from the input file(internal method)<br>
                                                                           // Este método se utiliza para eliminar comentarios simples del archivo de entrada(Método interno)</p>

    <p><b>public String get(Reader fileIn, Readmode mode, Character forNumberLine_Delimiter, MutableTypeData<Integer> actual, MutableTypeData<Boolean> containsBeforeEOForEndLine) throws IOException;</b>// This method is used to obtain the line number or completely line from the input file(internal method)<br>
                                                            // Este método se utiliza para obtener el número de línea del archivo de entrada o su linea completa(Método interno)</p>

    <p><b>public int RemoveBlockComments(ReadmodeBlock mode, String Read_File_in, String Delimiter, String delimiterEnd, Character DelimiterNumLine);</b>// This method is used to remove block comments from the input file, and can obtanin the number line if you want(internal method)
                                                                                                     // Este método se utiliza para eliminar comentarios de bloque del archivo de entrada, y puede obtener el numero de linea si lo desea(Método interno)</p>

    <p><b>public int RemoveNestedBlockComments(ProccessBlockComments dataForProccess) throws IOException;</b>// This method is used to remove nested block comments from the input file(internal method)<br>
                                                                                                        // Este método se utiliza para eliminar comentarios de bloque anidados del archivo de entrada(Método interno)</p>
    
    <p><b>public int RemoveVoidChars(String Read_File_In, Character VoidCharacterStart);</b>// This method is used to remove void lines or characters from the input file(internal method)<br>
                                                        // Este método se utiliza para eliminar líneas vacías o caracteres del archivo de entrada(Método interno)</p>

    <p><b>public int RemoveNLine(String file_in);</b>// This method is used to remove the number line from the input file(internal method)<br>
                                                // Este método se utiliza para eliminar el número de línea del archivo de entrada(Método interno)</p>

    <p><b>public int CleanFile(String file_in);</b>// Clean the file of the spaces and void lines and comments,integrat the above functions, and 'NumLines'<br>
                                         // Limpiar el archivo de espacios y líneas vacías y comentarios integrando las funciones anteriores y 'NumLines'</p>

<p><b>*************************************************------------------------------------------------*******************************------------------------------</b></p>
    <p><b>public int NumLines(File Read_File_in, File Writte_File_out);</b>// This method is used to add the line number to the input file(internal method)<br>
                                                            // Este método se utiliza para agregar el número de línea al archivo de entrada(Método interno)</p>
------------------------------------------------------------------------------------------------------------------------------
    <p><b>public int File_to(String file_In, String nameOfNewFileWithFormat);</b>// This method is used to convert the file to other format(internal method)<br>
                                             // Este método se utiliza para convertir el archivo a otro formato(Método interno)</p>
<p>------------------------------------------------------------------------------------------------------------------------------</p>
<p>
<b>END OF FUCNTION TO PREPARE FILES(FIN DE LAS FUNCIONES PARA LA PREPARACIÓN DE ARCHIVOS)</b>---------------------------------------------------------------------------------</p>
*/
public class Parser {
//STARTS THE PROCESS OF PREPARE FILES (INICIA EL PROCESO DE PREPARACIÓN DE ARCHIVOS)----------------------------------------------------------------- 
//Enum for return 
public enum Return {
    ERROR(-1),
    SUCCESS(0);

    private final int value;

    Return(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
/**
 * This function pass content of a file to other
 * @param file_In File to convert or pass, path or name(if are in the same path of the class)
 * @param nameOfNewFileWithFormat The file where you want to convert or pass
 * @return 0 = SUCCESS,  -1 = ERROR
 */
public int File_to(String file_In, String nameOfNewFileWithFormat) {  
    // Open the file_In in reading and create a temporary file for writing
    // Abrir el archivo de entrada en modo lectura y el archivo temporal en modo escritura
    try (FileReader fileP = new FileReader(file_In); Writer tempFile = new FileWriter(nameOfNewFileWithFormat)) {
        int c;
        // Read character by character from the input file and write to the temporary file
        // Leer carácter por carácter del archivo de entrada y escribir en el archivo temporal
        while ((c = fileP.read()) != -1) {
            tempFile.write((char)c);
        }
        return Return.SUCCESS.getValue(); // Éxito
        }
      catch (IOException e) {
        // Manejar errores de entrada/salida
        // Handle input/output errors
        System.out.println("Error: " + e.getMessage());
        return Return.ERROR.getValue(); // Error
        }
  }
//--------------------------------------------------------------
/**
 * This function are an orchestrator of other functions, creating a cleaner
 * @param file_in File to clean
 * @return 0 = SUCCESS, -1 = ERROR
 */
public int CleanFile(String file_in){
    System.out.printf("\nCLEANING THE FILE: '%s'...\n\n", file_in);
    int n;
    ArrayList<String> specialsForIdentify = new ArrayList<>();
    specialsForIdentify.add("function");
    specialsForIdentify.add("call");
    n = trateSpecialsStrings(specialsForIdentify, file_in);
    if(n!= 0) return n;

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
    return Return.SUCCESS.getValue();
}
//--------------------------------------------------------------
/**
 * Remove all the appears of a String in a file
 * @param Read_File_In File to clean of Strings
 * @param Delimiter String to remove from the file
 * @return 0 = SUCCESS, -1 = ERROR
 */
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
        return Return.ERROR.getValue();
    }

    File originalFile = new File(Read_File_In);
    File tempFile = new File("tempWithoutString.txt");

    if (originalFile.delete()) {
        if (tempFile.renameTo(originalFile)) {
            System.out.printf("The file was cleaned and renamed to '%s'\n", Read_File_In);
            return Return.SUCCESS.getValue();
        } else {
            System.out.println("Error renaming temp file.");
            return Return.ERROR.getValue();
        }
    } else {
        System.out.println("Error deleting original file.");
        return Return.ERROR.getValue();
    }
}
//--------------------------------------------------------------
/**
 * Search a String in a line
 * @param searchAll Indicate if you want search all iterations and return the index of the first find, if is 'false', or how much are in the line
 * @param line Line to check
 * @param searchThis String to search in the line
 * @param startIndex Index in the line where start the searching
 * @param delimiter Stop to search when find this delimiter
 * @param WatchMessages For debug
 * @return >=0 = SUCCESS, -1 = NOT FIND, -2 = ERROR IN THE PARAMETERS, -3 = ERROR IN THE INDEX PARAMETER
 */
public int searchString(boolean searchAll, String line, String searchThis, int startIndex, Character delimiter, boolean WatchMessages) {
    if (line == null || searchThis == null || startIndex < 0) {
        System.err.printf("Error: Invalid input parameters.\nDETAILS: line: %s, searchThis: %s, Index: %d, sizeOfLine: %d\n", line, searchThis, startIndex, line.length());
        return -2;
    }
   if (startIndex >= line.length()){ 
      if(WatchMessages) System.err.println("Error: The index start is greather than the line");
      return -3;
    }

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
    return Return.ERROR.getValue();
}
//--------------------------------------------------------------
/**
 * Remove simple comments from a file. Delete all before delimiter and the delimiter (not delete after)
 * @param Read_File_In File to check
 * @param SimpleCommentIdent Idetinficator for Simple comments start
 * @return 0 = SUCCESS, -1 = ERROR 
 */
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
        return Return.ERROR.getValue(); // Error
    }
     //Upload the input file
    // Actualizar el archivo de entrada
    File infile = new File(Read_File_In);
    if (infile.delete()) {
        File temp = new File("tempwithoutSimpleComments.txt");
        if (temp.renameTo(infile)) {
            System.out.printf("The file 'tempwithoutSimpleComments.txt' is renamed to '%s'\n", Read_File_In);
            System.out.printf("\nTHE FILE '%s' IS CLEAN OF SIMPLE COMMENTS\n", Read_File_In);
            return Return.SUCCESS.getValue();
        }
        System.out.printf("Error trying to rename file 'tempwithoutSimpleComments.txt' to '%s'\n", Read_File_In);
        return Return.ERROR.getValue();
    }
    System.out.printf("Error trying to delete the file '%s'\n", Read_File_In);
    return Return.ERROR.getValue();
}
//--------------------------------------------------------------
//class for simulate the pass for reference
//clase para simular el paso por referencia
/**
 * Class for simulate the 'pass for reference'
 */
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
/**
 * This function have or can have 2 "functions", the first is cordinate(similar to an orchestrator)the search and delete proccess to remove block comments in the file, and the second, be an API to the user of the function 'RemoveNestedBlockComments'
 * @param mode mode for know how proccess the block comments, both preserve the struct of the file and the code between block comments
<p>SingleEnd: search only the first close of the comment, independent if are nested block comments(not check the content, not are important)</p>
<p>NestedEnd: search and verify that all nested comments are closed correctly(check the content and are important)</p>
 * @param Read_File_in File to proccess
 * @param Delimiter Delimiter or Identificator to indicate the Start of a Block Comment
 * @param delimiterEnd Delimiter or Identificator to indicate the End of a Block Comment
 * @param DelimiterNumLine Optional parameter for ignore or get some String before delimiter and not proccess this(thinked for somethings like the numberofLine)
 * @return 0 = SUCCESS, -1 = ERROR
 */
public int RemoveBlockComments(ReadmodeBlock mode, String Read_File_in, String Delimiter, String delimiterEnd, Character DelimiterNumLine) {

  MutableTypeData<String>line5 = new MutableTypeData<>("");
  // variable to store the actual character and line (utlized just in the methods RemoveBlockComments and RemoveNestedBlockComments)
  //Variable para almacenar el carácter actual y linea (utilizado solo en los métodos RemoveBlockComments y RemoveNestedBlockComments)
   System.out.printf("\nREMOVING BLOCK COMMENTS FROM THE FILE: '%s'...\n\n", Read_File_in);
   if(Delimiter == null){
     System.err.println("Error: Need put a delimiter\n");
     return Return.ERROR.getValue();
   }
  if (delimiterEnd == null) {
    System.err.println("Error: delimiterEnd is required.\n");
    return Return.ERROR.getValue();
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
                    if(n == -2 || n == -3) return Return.ERROR.getValue(); //Error
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
                        ArrayList<String> between = new ArrayList<>();
                        MutableTypeData<Integer>last = new MutableTypeData<>(0);
                        MutableTypeData<Boolean>lastCallFlag = new MutableTypeData<>(false);
                        ProccessBlockComments data = new ProccessBlockComments(mode, line5, ReadFile, nLine, Delimiter, delimiterEnd, DelimiterNumLine, index, LinesJump, false, between, last, false, lastCallFlag);
                        if(RemoveNestedBlockComments(data) != 0) return Return.ERROR.getValue();
                               
                            //conseverd the structure of the file including the prosecced lines
                            //conservar la estructure de el archivo incluyendo las lineas procesadas                        
                            for(int i= 0; i < LinesJump.getValor(); i++){
                            WritteFile.newLine();
                        }
                        for(String str : between){
                            if(!str.equals("")) WritteFile.write(" " + str);
                        }
                            
                       //get all the content after the end of comment
                       //obtener todo lo que hay despues de el comentario
                        if(mode == ReadmodeBlock.NestedEnd && line5.getValor().indexOf(last.getValor()) != '\n' && line5.getValor().indexOf(last.getValor()) != '\0') getString= line5.getValor().substring((last.getValor()+1), line5.getValor().length());
                        else if(mode == ReadmodeBlock.SingleEnd){
                            String ischaracterofDel = line5.getValor().substring(last.getValor() + delimiterEnd.length(), line5.getValor().length());
                            Character m = ischaracterofDel.charAt(0);
                            if(m.equals(delimiterEnd.charAt(delimiterEnd.length()-1))){
                                if(ischaracterofDel.length() > 1) getString = ischaracterofDel.substring(1, ischaracterofDel.length());
                                else getString = "";
                            }
                        }
                    if(!getString.equals("")) WritteFile.write(" " + getString);//if are a void line(""), write a newLine
                    }
                }
                //write the completely line
                //escribir la linea completa
                else WritteFile.write(line5.getValor());//Escrbir la linea
                    WritteFile.newLine();
                
                
            }
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return Return.ERROR.getValue(); // Error
    }

    // Actualizar el archivo de entrada
    //Upload the input file
    File infile = new File(Read_File_in);
    if (infile.delete()) {
        File temp = new File("tempWithoutBlockComments.txt");
        if (temp.renameTo(infile)) {
            System.out.printf("The file 'tempWithoutBlockComments.txt' is renamed to '%s'\n", Read_File_in);
            System.out.printf("\nTHE FILE '%s' IS CLEAN OF BLOCK COMMENTS\n", Read_File_in);
            return Return.SUCCESS.getValue();
        }
        System.out.printf("Error trying to rename file 'tempWithoutBlockComments.txt' to '%s'\n", Read_File_in);
        return Return.ERROR.getValue();
    }
    System.out.printf("Error trying to delete the file '%s'\n", Read_File_in);
    return Return.ERROR.getValue();
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

/**
 * This function remove the block comments and nested block comments if are setup for make this, this change his configuratino depende of the mode proporcionated flexibility and adaptability
 * @param dataForProccess Datas for porccess the block comments thats are wrapper in the class 'ProccessBlockComments'
 * @return 0 = SUCCES, -1 = ERROR
 */
//All in this function are a proccess you can divide that in aux functions but not is necesary because just reduce a little bit the number of lines, but not is necesary, and this in this implementation are used aux functions too
public int RemoveNestedBlockComments(ProccessBlockComments dataForProccess) throws IOException{ 
    if(dataForProccess.lastRecursiveCallFlag == null){
        System.err.println("Error: Need put a parameter 'lastCallFlag'\n");
        return Return.ERROR.getValue();
    }
    dataForProccess.lastRecursiveCallFlag.setValor(true);//initialize always in true //incializar siempre en verdadero
    MutableTypeData<Integer> actual = new MutableTypeData<>(0);
    //Read until the end of comment
    //Leer hasta el final del comentario
    while(true){
      int endDelIndex = 0, startDelIndex = 0; //variables for watch the result of the search //variables para ver el resultado de la busqueda
      //search the delimiter that indicate the start of the comment
      //Buca el delimitador que indica el iniciio de un comentario
      startDelIndex = searchString(false, dataForProccess.line.getValor(), dataForProccess.DelimiterStart, dataForProccess.indexActualInTheLine.getValor(), null, false); 
     int m = startDelIndex;
      //remove the delimiter readed and upload de line
      //eliminar el delimitador ya leido y actualizar la linea
        if(startDelIndex >= 0 && startDelIndex != dataForProccess.line.getValor().length()){ 
            StringBuilder newLine = new StringBuilder(dataForProccess.line.getValor());
            newLine.delete(startDelIndex, startDelIndex+dataForProccess.DelimiterStart.length());
            dataForProccess.line.setValor(newLine.toString());
            dataForProccess.indexActualInTheLine.setValor(0);
        }
         //Search the delimiter of end comment
    //buscar el delimitador de final de comentario
        endDelIndex = searchString(false, dataForProccess.line.getValor(), dataForProccess.DelimiterEnd, dataForProccess.indexActualInTheLine.getValor(), null, false);
        
        //remove the delimiter after readed
        //eliminar el delimitador una vez leido
        if(dataForProccess.mode == ReadmodeBlock.NestedEnd && endDelIndex >= 0 && endDelIndex != dataForProccess.line.getValor().length()){ 
            StringBuilder newLine = new StringBuilder(dataForProccess.line.getValor());
            newLine.delete(endDelIndex, endDelIndex+dataForProccess.DelimiterEnd.length());
            dataForProccess.line.setValor(newLine.toString());
            dataForProccess.indexActualInTheLine.setValor(0);
        }
        //search the delimiter in the uploading line, for search nested comments
        //buscar el delimitador en la linea actualizada, para buscar comentarios anidados
    startDelIndex = searchString(false, dataForProccess.line.getValor(), dataForProccess.DelimiterStart, dataForProccess.indexActualInTheLine.getValor(), null, false); 
        
    if(endDelIndex == -2 || startDelIndex == -2 || (endDelIndex == -3 || startDelIndex == -3)) return Return.ERROR.getValue(); //error in the parameters 
         
        //if not find in this line
        //si no encuentra en esta line
      //Upload the parameters, uploading the line, the index, and the number of line actual
      //Actualizar los parametros, actualizando la linea, el indice y el numbero de linea actual
      else if(endDelIndex == -1 && startDelIndex == -1){
        //Get the number of line if it has
        //Obtener el numero de linea si lo tiene
        actual.setValor((int)dataForProccess.line.getValor().length());

        while((char)((int)actual.getValor()) == '\n') actual.setValor(dataForProccess.ReadFile.read()); 
        
         if(dataForProccess.DelimiterNumLine != null){
            int h = dataForProccess.line.getValor().indexOf(dataForProccess.DelimiterNumLine);
            dataForProccess.NumberOfLine = dataForProccess.line.getValor().substring(0, h);
        }
        //Check if the character actual is the end of the before line
        //Revisar si el caracter actual es el final de la linea anteriror
        MutableTypeData<Boolean> contain = new MutableTypeData<>(false);
        dataForProccess.line.setValor(get(dataForProccess.ReadFile, Readmode.CompletelyLine, dataForProccess.DelimiterNumLine, actual, contain));
            //If find EOF and not are content before
        //Si se llego a EOF y no habia contenido antes
        if(dataForProccess.line.getValor().equals("") || dataForProccess.line.getValor().equals("\n") || dataForProccess.line.getValor().equals("\0") && !contain.getValor()) break;
        //Upload the parameters, uploading the index
        //Actualizar los parametros, actualizando el indice
        dataForProccess.indexActualInTheLine.setValor(0);
        if(dataForProccess.countOfLineProcessed != null){
        int moreLine = dataForProccess.countOfLineProcessed.getValor()+1;
        dataForProccess.countOfLineProcessed.setValor(moreLine);
        }
        dataForProccess.itsMultiLine = true;
        continue;
      } 
      
        
        //if has a nested comment block
           //Si tiene un comentario en bloque anidado
    if(startDelIndex >= 0 || (dataForProccess.itsMultiLine && m >= 0)){    
        //Dependend the mode
        //Dependiendo de el modo
        switch (dataForProccess.mode){
          case NestedEnd:
            //if has code between comments get it
            //si tiene codigo entre comentarios extraerlo
             if(!(endDelIndex > startDelIndex) && endDelIndex >= 0 && !dataForProccess.itsMultiLine){
            String newl;
            if(startDelIndex != -1) newl = dataForProccess.line.getValor().substring(endDelIndex, startDelIndex);//get the string between comments
            else newl = dataForProccess.line.getValor().substring(endDelIndex, dataForProccess.line.getValor().length());
            dataForProccess.BetweenComments.add(newl);
            }
            dataForProccess.recursiveCall = true;
         if(RemoveNestedBlockComments(dataForProccess) != 0) return Return.ERROR.getValue();
         break;
          case SingleEnd: 
          //if has code between comments get it
        //si tiene codigo entre comentarios extraerlo
        if(!(endDelIndex > startDelIndex) && endDelIndex >= 0 && !dataForProccess.itsMultiLine){
            boolean edit = false;
            String newl;
            if(!(endDelIndex+dataForProccess.DelimiterEnd.length() > dataForProccess.line.getValor().length())){
            if(startDelIndex != -1 && !(endDelIndex+dataForProccess.DelimiterEnd.length() >= startDelIndex)) newl = dataForProccess.line.getValor().substring(endDelIndex+dataForProccess.DelimiterEnd.length(), startDelIndex);//get the string between comments
            else if(startDelIndex != -1 && endDelIndex + dataForProccess.DelimiterEnd.length() >= startDelIndex){
                newl = dataForProccess.line.getValor().substring(startDelIndex+dataForProccess.DelimiterStart.length(), dataForProccess.line.getValor().length()); //if n+delimiterEnd length is equal or grether than r, stary r(priorize n(coment end delimiter))(thats appear when push something like '*/*/' because r starts in 2 and n in 1(in this example) in this mode)
                  int s = searchString(false, newl, dataForProccess.DelimiterStart, 0, null, false);
                  if(s != -1){
                     int indexStart = s + (startDelIndex+dataForProccess.DelimiterStart.length());
                  newl = dataForProccess.line.getValor().substring(startDelIndex+dataForProccess.DelimiterStart.length(), indexStart);
                  }
                  edit = true;
                }
                 else newl = dataForProccess.line.getValor().substring(endDelIndex+dataForProccess.DelimiterEnd.length(), dataForProccess.line.getValor().length());
            dataForProccess.BetweenComments.add(newl);
            }
            //if is in mode SingleEnd, upload n, because in this mode search only the first close
                //remove the delimiter after readed
               //eliminar el delimitador una vez leido
               if(!(edit) && endDelIndex >= 0 && endDelIndex != dataForProccess.line.getValor().length()){ 
                  StringBuilder newLine = new StringBuilder(dataForProccess.line.getValor());
                   newLine.delete(endDelIndex, endDelIndex+dataForProccess.DelimiterEnd.length());
                   dataForProccess.line.setValor(newLine.toString());
                   dataForProccess.indexActualInTheLine.setValor(0);
                }
                //if are editing the code because find a case like this example '*/*/'(conflict into delimiter end and delimiter start) remove this conflict after processed
                else if(edit){
                    StringBuilder newLine = new StringBuilder(dataForProccess.line.getValor());
                   newLine.delete(endDelIndex, startDelIndex+dataForProccess.DelimiterEnd.length());
                   dataForProccess.line.setValor(newLine.toString());
                   dataForProccess.indexActualInTheLine.setValor(0);
                }
        }
        dataForProccess.recursiveCall = true;
          if(RemoveNestedBlockComments(dataForProccess) != 0 ) return Return.ERROR.getValue();
          break;
          default:
          System.err.println("Error in the argument 'mode'\n");
          return Return.ERROR.getValue();
      }
    }

       if(endDelIndex >= 0){
        //Si es una llamda recursiva, y hay un delimitador final actualziar el parametro 'lastEndofCommentDelimiter', need be a recursive call, because if not are that proccess not is necessary, because after of that 'indexActualInTheLine' upload with index of the first appear of Delimiter End commentBlock
        //If are a recursive call, and have a final delimiter upload the parameter 'lastEndofCommentDelimiter', nesecita ser una llamda recursiva porque sino es una este proceso no es necesario, porque despues de esto 'IndexActualInTheLine' se actualiza con el indice de la primera aparacion de un delimitador de final de comentario en bloque 
       if(dataForProccess.recursiveCall && (dataForProccess.lastRecursiveCallFlag.getValor() == true) && endDelIndex >= 0){
        if(endDelIndex > 0) dataForProccess.lastEndofCommentDelimiter.setValor(endDelIndex-1);//upload with the index of the las end comment delimiter
        else if(endDelIndex == 0) dataForProccess.lastEndofCommentDelimiter.setValor(endDelIndex);
       dataForProccess.lastRecursiveCallFlag.setValor(false);//set the value of flag in the last call recursive for stay his value
       }
       dataForProccess.indexActualInTheLine.setValor(endDelIndex-1);
        return Return.SUCCESS.getValue();
      } 
    }
      // If find the end of file without closing the comment
    // Si encuentra el final del archivo sin cerrar el comentario
            System.err.println("Error in the line: "+ dataForProccess.NumberOfLine +"\nDETAILS:Find the 'End of file' and don't closing a comment block\n");
            return Return.ERROR.getValue();
}
//--------------------------------------------------------------
/**
 * Remove chars considerateds 'void' in a file like tabs and others
 * @param Read_File_In, File to search and remove void characters
 * @param VoidCharacterStart Remove a character considerated 'void'
 * @return 0 = SUCCESS, -1 = ERROR
 */
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
        return Return.ERROR.getValue(); // Error
    }
    //Upload the input file
    //Actualizar el archivo de entrada
    File infile = new File(Read_File_In);
    if(infile.delete()){
        File temp = new File("tempWithoutVoidChars.txt");
        if(temp.renameTo(infile)){
            System.out.printf("The file 'tempWithoutVoidLines.txt' is rename to '%s'\n", Read_File_In);
            System.out.printf("\nTHE FILE '%s' IS CLEAN OF VOID CHARS\n", Read_File_In);
            return Return.SUCCESS.getValue();
        }
        System.out.printf("Error to try rename file 'tempWithoutVoidLines.txt' to '%s'\n", Read_File_In);
        return Return.ERROR.getValue();
    }
    System.out.printf("Error to try delte the file '%s'\n", Read_File_In);
    return Return.ERROR.getValue();
}
//--------------------------------------------------------------
/**
 * Numerate lines from 1-n (where n are the num of lines in the file)
 * @param Read_File_in File to numerate his lines
 * @return 0 = SUCCESS, -1 = ERROR
 */
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
        return Return.ERROR.getValue();
    }
    // Upload the input file
    // Actualizar el archivo de entrada
    File infile = new File(Read_File_in);  

    if (infile.delete()) {
        File temp = new File("fileWithNumLines.txt");
        if (temp.renameTo(infile)) {
            System.out.printf("The file 'fileWithNumLines.txt' is rename to '%s'\n", Read_File_in);
            System.out.printf("\nTHE FILE '%s' ARE NUMERATED\n", Read_File_in);
            return Return.SUCCESS.getValue();
        }
        System.out.printf("Error to try rename file 'fileWithNumLines.txt' to '%s'\n", Read_File_in);
        return Return.ERROR.getValue();
    }
    System.out.printf("Error to try delte the file '%s'\n", Read_File_in);
    return Return.ERROR.getValue();
}
//--------------------------------------------------------------
//This enum is used to define the read mode of the file for the method get
//Este enum se utiliza para definir el modo de lectura del archivo para el método get
public enum Readmode{
    NumberLine,
    CompletelyLine
}
/**
 * Get a String or a line in a file can be type 'Reader'(uploading too the index in the reader file), this get or search in a line not in all the file
 * @param fileIn File open in Reader mode
 * @param mode  mode type 'Readmode', thats have 2 modes
 * <p>NumberLine: get the "numberofLine" or a String before delimiter for number line from the file and return this</p>
 * <p>CompletelyLine: get a completely line from the file and return this</p>
 * @param forNumberLine_Delimiter If select the mode 'NumberLine' need put this parameter, else don't put
 * @param actual If you want get and mantain informated about the index in the file cand put a actual parameter to upload this in real time
 * @param containsBeforeEOForEndLine If you want know if before EOF(End of File) or End of Line have characters 
 * @return NULL = VOID LINE, ERROR = ERROR IN THE PARAMETERS
 */
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
/**
 * Remove a String before the delimiter, and the delimimter, thinked for remove numberofLines, but can use for remove a string before a delimiter
 * @param file_in File to clear 
 * @param delimiter Delimiter that indicate the start of writte in the temp file, for last rename or upload the input file
 * @return 0 = SUCCESS, -1 = ERROR
 */
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
        return Return.ERROR.getValue(); // Error
    }
    //Upload the input file
    //Actualizar el archivo de entrada
    File infile = new File(file_in);
    if(infile.delete()){
        File temp = new File("tempFinalClean.txt");
        if(temp.renameTo(infile)){
            System.out.printf("The file 'tempFinalClean.txt' is rename to '%s'\n", file_in);
            System.out.printf("\nTHE FILE '%s' IS CLEAN TO THE VOID LINES AND NUMBER LINE\n", file_in);
            return Return.SUCCESS.getValue();
        }
        System.out.printf("Error to try rename file 'tempFinalClean.txt' to '%s'\n", file_in);
        return Return.ERROR.getValue();
    }
    System.out.printf("Error to try delte the file '%s'\n", file_in);
    return Return.ERROR.getValue();
}
//--------------------------------------------------------------
public int trateSpecialsStrings(ArrayList<String> lineToidentify, String inputFile) {
    if (lineToidentify == null || lineToidentify.isEmpty()) {
        System.err.println("Error: Need put some value in the string of input.");
        return -1;
    }
    String tempFile = "tempSpecialsStrings.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        String line;
        while ((line = reader.readLine()) != null) {
            StringBuilder newLine = new StringBuilder(line);

            // Construir la línea sin espacios ni caracteres vacíos
            StringBuilder withoutSpaces = new StringBuilder();
            for (int i = 0; i < newLine.length(); i++) {
                char c = newLine.charAt(i);
                if (c != ' ' && c != '\t' && c != '\r' && c != '\f' && c != '\u000B' && c != '\u2028' && c != '\0') {
                    withoutSpaces.append(c);
                }
                else{
                    if(c == ' ' || c == '\t' || c == '\r') newLine.deleteCharAt(i);
                    break;
                }
            }

            // Si la línea sin espacios es igual a la cadena de entrada
            if (lineToidentify.contains(withoutSpaces.toString())) {
                int indexOfSpace = newLine.indexOf(" ");
                if (indexOfSpace != -1) {
                    newLine.setCharAt(indexOfSpace, '^');
                }
            }

            // Eliminar todos los espacios y caracteres vacíos (incluyendo el ^ si lo pusimos)
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < newLine.length(); i++) {
                char c = newLine.charAt(i);
                if (c != ' ' && c != '\t' && c != '\r' && c != '\f' && c != '\u000B' && c != '\u2028' && c != '\0') {
                    result.append(c);
                }
            }

            writer.write(result.toString());
            writer.newLine();
        }

    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        return -1;
    }

    // Reemplaza el archivo original por el temporal
    File original = new File(inputFile);
    File temporal = new File(tempFile);

    if (original.delete()) {
        if (temporal.renameTo(original)) {
            System.out.printf("THE FILE '%s' ARE BE CLEANING AND UPLOAD.\n", inputFile);
            return 0;
        } else {
            System.out.println("Error to try rename the file.");
            return -1;
        }
    } else {
        System.out.println("Error to try remove the original file.");
        return -1;
    }
}
//END THE PROCCES TO PREPARE FILES(TERMINA EL PROCESO DE PREPARACIÓN DE ARCHIVOS)--------------------------------------------------------------

}

