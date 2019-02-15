package LexicalAnalyzer;

/**
 * 用于整形数字
 */
public class Int extends Token {
    public final int value;
    public Int(int v){
        super(Tag.INT);
        this.value = v;
    }
}
