package LexicalAnalyzer;

/**
 * 用于标识符
 */
public class Word extends Token{
    public final String lexme;
    // 参数t是词素，s是对应的值
    public Word(int t, String s){
        super(t);
        this.lexme = new String(s);
    }
}
