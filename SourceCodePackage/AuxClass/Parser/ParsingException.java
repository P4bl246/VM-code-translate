package AuxClass.Parser;
/**
 * @author Pablo Riveros Perea
 */
public class ParsingException extends Exception {
    private static final long serialVersionUID = 1L;

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(Throwable cause) {
        super(cause);
    }
}
