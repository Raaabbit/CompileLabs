package LexicalAnalyzer;

/**
 * 一个Token类，用来作出语法分析的决定
 * 用整形常量来表示一个符号
 */
public class Token {
    public final int tag;
    public Token(int t){
        this.tag = t;
    }
}
