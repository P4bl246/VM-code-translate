package AuxClass.Parser;
import java.util.ArrayList;
import java.io.*;
public class ProccessBlockComments{

    public Parser.ReadmodeBlock mode;
    public Parser.MutableTypeData<String> line;
    public Reader ReadFile;
    public String NumberOfLine;
    public String DelimiterStart;
    public String DelimiterEnd;
    public Character DelimiterNumLine;
    public Parser.MutableTypeData<Integer>indexActualInTheLine;
    public Parser.MutableTypeData<Integer>countOfLineProcessed;
    public boolean itsMultiLine;
    public ArrayList<String> BetweenComments;
    public Parser.MutableTypeData<Integer> lastEndofCommentDelimiter;
    public boolean recursiveCall;
    public Parser.MutableTypeData<Boolean> lastRecursiveCallFlag;
     public ProccessBlockComments(Parser.ReadmodeBlock mode, Parser.MutableTypeData<String> line, Reader ReadFile, String NumberOfLine, String DelimiterStart, String DelimiterEnd, 
     Character DelimiterNumLine, Parser.MutableTypeData<Integer>indexActualInTheLine, Parser.MutableTypeData<Integer>countOfLineProcessed, boolean itsMultiLine,
     ArrayList<String> BetweenComments, Parser.MutableTypeData<Integer> lastEndofCommentDelimiter, boolean recursiveCall, Parser.MutableTypeData<Boolean> lastRecursiveCallFlag){
        this.mode = mode;
        this.line = line;
        this.ReadFile = ReadFile;
        this.NumberOfLine = NumberOfLine;
        this.DelimiterStart = DelimiterStart;
        this.DelimiterEnd = DelimiterEnd;
        this.DelimiterNumLine = DelimiterNumLine;
        this.indexActualInTheLine = indexActualInTheLine;
        this.countOfLineProcessed = countOfLineProcessed;
        this.itsMultiLine = itsMultiLine;
        this.BetweenComments = BetweenComments;
        this.lastEndofCommentDelimiter = lastEndofCommentDelimiter;
        this.recursiveCall = recursiveCall;
        this.lastRecursiveCallFlag = lastRecursiveCallFlag;
     }
}