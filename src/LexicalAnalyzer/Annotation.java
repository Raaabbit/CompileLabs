package LexicalAnalyzer;

// 注释
public class Annotation extends Token{
    public final String lexme;
    public Annotation(int t,String s){
        super(t);
        this.lexme = s;
    }
}
