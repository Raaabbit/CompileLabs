package LexicalAnalyzer;

/**
 * 用于运算符号[ + - * / = ]
 */
public class Operator extends Token {
    public final char type;
    public Operator(char t){
        super(Tag.OPERATION);
        this.type = t;
    }
}
