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

        if (fileOrDir.isDirectory()) {
            // Unir todos los archivos .vm en uno solo
            File[] files = fileOrDir.listFiles((file) -> file.getName().endsWith(".vm"));
            if (files != null && files.length > 0) {
                String mergedFile = "merged.vm";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(mergedFile))) {
                    for (File file : files) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                writer.write(line);
                                writer.newLine();
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error merging files: " + e.getMessage());
                    return;
                }
                // Procesar el archivo unido
                // Process the merged file
                if(procesarArchivo(mergedFile, parseF, sintax, assembly)!=0) return;
            }
        } else if (fileOrDir.isFile()) {
            // Si es un solo archivo
            //if just is a single file
            if(inputPath.lastIndexOf(".vm") == -1 && inputPath.lastIndexOf(".") != -1) {
            System.out.println("The file need take .vm extension\n");
            return;
        }
            if(procesarArchivo(inputPath, parseF, sintax, assembly) != 0) return;
        } else {
            System.out.println("Invalid path.\n");
        }
    }

    private static int procesarArchivo(String filePath, Parser parseF, sintaxParsing sintax, TranslateToAssembly assembly) {
        int n;
        String copy = "fileCopy.txt";
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
        n = assembly.transalte(copy);
        if(n != 0) return n;
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