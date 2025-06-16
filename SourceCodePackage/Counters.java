import AuxClass.Parser.Parser;

public class Counters {
    Parser f = new Parser();
    public Parser.MutableTypeData<Integer> counter1 = f.new MutableTypeData<>(0);
    public Parser.MutableTypeData<Integer> counter2 = f.new MutableTypeData<>(0);
    public Parser.MutableTypeData<Integer> counter3 = f.new MutableTypeData<>(0);
    public Parser.MutableTypeData<Integer> counter4 = f.new MutableTypeData<>(0);

}
