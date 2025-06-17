package AuxClass.Parser;
/**
 * @author Pablo Riveros Perea
 * This is a class creted for the class 'TranslateToAssembly', for mantain a global counters between files form a folder
 */
public class Counters {
    Parser f = new Parser();
    public Parser.MutableTypeData<Integer> counter1 = f.new MutableTypeData<>(0);
    public Parser.MutableTypeData<Integer> counter2 = f.new MutableTypeData<>(0);
    public Parser.MutableTypeData<Integer> counter3 = f.new MutableTypeData<>(0);
    public Parser.MutableTypeData<Integer> counter4 = f.new MutableTypeData<>(0);

}
