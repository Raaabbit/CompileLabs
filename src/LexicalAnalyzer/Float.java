package LexicalAnalyzer;

/**
 * 用于浮点数字
 */
public class Float extends Token {
    public final float value;
    public Float(float v){
        super(Tag.FLOAT);
        this.value = v;
    }
}
