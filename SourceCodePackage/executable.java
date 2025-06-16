import AuxClass.Parser.*; // Import the Parser class from the AuxCLass.Parser package
import java.io.*;
import java.lang.System;
import java.util.Scanner;
public class executable {

    public static void main (String[] args) {
        Parser parseF = new Parser();
        sintaxParsing sintax = new sintaxParsing();
        TranslateToAssembly assembly = new TranslateToAssembly();
    
       Scanner sc = new Scanner(System.in);
        System.out.print("Put the path or name of file: ");
        String inputPath = sc.nextLine();
       if (inputPath.isEmpty()) {
        System.out.println("Need put a path or name of file\n");
        while(true){
         sc = new Scanner(System.in);
        System.out.print("Put the path or name of file: ");
        inputPath = sc.nextLine();
        if(inputPath.isEmpty()) continue;
        else break;
        }
       } 
        
        File fileOrDir = new File(inputPath);
         Parser.MutableTypeData<Boolean> first = parseF.new MutableTypeData<>(false);

        if (fileOrDir.isDirectory()) {
            String ASMfolderName = inputPath+"$ASM";
            Counters counters = new Counters();
            //procesar todos los archivos .vm
            File[] files = fileOrDir.listFiles((file) -> file.getName().endsWith(".vm"));
            if (files != null && files.length > 0) {
                    for (File file : files) {
                        if(procesarArchivo(file.toString(), parseF, sintax, assembly, true, ASMfolderName, first, counters)!=0) return;
                        }
                        //unir todos los archivos .asm generados
                        File asm = new File(ASMfolderName);
                     File[] asmFiles = asm.listFiles((file) -> file.getName().endsWith(".asm"));
                  try (BufferedWriter fileAsm = new BufferedWriter(new FileWriter(inputPath+".asm"))) {
                    if (asmFiles != null) {
                        for (File asmFile : asmFiles) {
                          try (BufferedReader reader = new BufferedReader(new FileReader(asmFile))) {
                           String line;
                        while ((line = reader.readLine()) != null) {
                           fileAsm.write(line);
                            fileAsm.newLine();
                      }
                     }
                    }
                }
               } catch (IOException e) {
                          System.err.println("Error while merge the folder\n");
                           return;
                        }
                    if(!asm.delete()){
                        System.err.printf("Error when try to remove the folder %s", ASMfolderName);
                        return;
                    }    
                   }
                }   else if (fileOrDir.isFile()) {
            // Si es un solo archivo
            //if just is a single file
            if(inputPath.lastIndexOf(".vm") == -1 && inputPath.lastIndexOf(".") != -1) {
            System.out.println("The file need take .vm extension\n");
            return;
        }
            if(procesarArchivo(inputPath, parseF, sintax, assembly, false, null, first, null) != 0) return;
        } else {
            System.out.println("Invalid path.\n");
        }
    }

private static int procesarArchivo(String filePath, Parser parseF, sintaxParsing sintax, TranslateToAssembly assembly, boolean isAFolder, String nameOfFolderASM, Parser.MutableTypeData<Boolean> boostrapPutFlag, Counters counters) {
        if(boostrapPutFlag == null){
            System.err.println("Erro: Need put a parameter 'boostrapPutFlag'\n");
            return -1;
        }
        if(isAFolder){
            if(nameOfFolderASM == null){
                System.err.println("Error: need put a folder name\n");
                return -1;
            }
            else if(nameOfFolderASM != null){
                if(nameOfFolderASM.isEmpty()){
                    System.err.println("Error: Need put a name of folder\n");
                    return -1;
                }
            }
        if(isAFolder){
            if(counters == null){
                System.err.println("Error: if its a folder need put something in a 'counters' parameter\n");
            }
        } 
         File folder = new File(nameOfFolderASM);
         if (!folder.exists()) {
    boolean creada = folder.mkdir(); // o mkdirs() para crear carpetas anidadas
    if (creada) {
        System.out.println("Carpeta creada correctamente.");
      } else {
        System.out.println("No se pudo crear la carpeta.");
        return -1;
      }
    }
        }
        int n;
        String copy = filePath.replace(".vm", ".txt");
        cleanConsole();
        n = parseF.fileTo(filePath, copy);
        if(n != 0) return n;
        cleanConsole();
        n = parseF.cleanFile(copy);
        if(n != 0) return n;
        cleanConsole();
        n = sintax.parser_Sintaxis(copy);
        if(n != 0) return n;
        cleanConsole();
        String asm = filePath.replace(".vm", ".asm");
         if (isAFolder) {
            // Usar File para unir la carpeta y el nombre del archivo
            asm = new File(nameOfFolderASM, new File(asm).getName()).getPath();
            n = assembly.translate(copy, asm, boostrapPutFlag, true, counters);
           }
       else n = assembly.translate(copy, asm, boostrapPutFlag, false, null);
        if(n != 0) return n;
        File removeCopy = new File(copy);
        if(!removeCopy.delete()){
            System.err.printf("Erro when try to remove the file '%s'", copy);
            return -1;
        }
        return 0;
    }
public static void cleanConsole() {
    try {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.equals("windows") || os.contains("win")) {
            new ProcessBuilder( "cls").inheritIO().start().waitFor();
        } else {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
    } catch (Exception e) {
        // Si falla, simplemente no limpia la consola
    }
}
}