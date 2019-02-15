package LexicalAnalyzer;

/**
 * 用于运算符号[ + - * / = ]
 */
public class Separator extends Token {
    public final char s;
    public Separator(char s){
        super(Tag.SEPARATOR);
        this.s = s;
    }
}
