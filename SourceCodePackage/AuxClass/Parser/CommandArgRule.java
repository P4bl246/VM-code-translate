package AuxClass.Parser;

import java.util.HashMap;
import java.util.Map;

// Esta es una clase especial pensada para una función de la clase 'sintax_parsing', llamada 'CompareCommandsWithArgs'
public class CommandArgRule {
    public HashMap<String, Integer> commandTable;
    public HashMap<String, Integer> argTable;
    public int commandLength;
    public int argLength;

    // Formato más largo y más corto permitidos para las instrucciones con argumentos
    public String formatPatternMostLong;
    public String formatPatternLessLong;

    // Múltiples patrones permitidos (por nombre)
    public Map<String, String> multipleFormatsPatterns = new HashMap<>();

    // Constructor completo
    public CommandArgRule(HashMap<String, Integer> commandTable, HashMap<String, Integer> argTable, int commandLength, int argLength, String formatPatternMostLong, String formatPatternLessLong, Map<String, String> multipleFormatsPatterns) {
        this.commandTable = commandTable;
        this.argTable = argTable;
        this.commandLength = commandLength;
        this.argLength = argLength;
        this.formatPatternMostLong = formatPatternMostLong;
        this.formatPatternLessLong = formatPatternLessLong;
        this.multipleFormatsPatterns = multipleFormatsPatterns;
    }
}
