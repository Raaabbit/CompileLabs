package LexicalAnalyzer;
/*
关系运算符 [> < >= <= ==]
 */
public class Relation extends Token{
    public final String s;
    public Relation(String s){
        super(Tag.RELATION);
        this.s = s;
    }
}
