package LexicalAnalyzer;
/**
 * 简单的词法分析器
 * 主要功能：读入.txt文件，生成token序列
 */
import java.io.*;
import java.util.*;


public class LexicalAnalyzer {
    public int line = 1;
    private char peek = ' ';
    // 保留字表
    private Hashtable words = new Hashtable();
    // 文件读取
    private FileReader fileReader;
    // 分析结果暂存
    private String lexRes = "type            detail\n====================================\n";
    // 分析结果
    ArrayList<Token> tokens;
    void reserve(Word t){
        words.put(t.lexme,t);
    }

    public LexicalAnalyzer(String path) throws IOException {
        // 读入的文件
        fileReader = new FileReader(path);
        // 保留字
        reserve(new Word(Tag.TRUE,"true"));
        reserve(new Word(Tag.FALSE,"false"));
        reserve(new Word(Tag.IF,"if"));
        reserve(new Word(Tag.ELSE,"else"));
        reserve(new Word(Tag.DO,"do"));
        reserve(new Word(Tag.WHILE,"while"));
        reserve(new Word(Tag.FOR,"for"));
        reserve(new Word(Tag.RETURN,"return"));
    }
    public Token scan() throws IOException {
        // 跳过空格，制表符，换行符号
        for (; ; peek = (char)fileReader.read()) {
            if (peek == ' ' || peek == '\t') {
                continue;
            } else if (peek == '\n' || peek == '\r') { // 针对windows特殊处理
                line++;
            } else {
                break;
            }
        }
        if ((int)peek == -1){
            return new Token(-1);
        }
        // 探测整形数字，浮点数
        if (Character.isDigit(peek)) {
            int v = 0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                peek = (char)fileReader.read();
            } while (Character.isDigit(peek));
            // 判断是不是浮点数
            if ((char)fileReader.read() == '.') {
                peek = (char) fileReader.read();
                float v2;
                do {
                    v2 = v + Character.digit(peek, 10) / 10;
                    peek = (char) fileReader.read();
                } while (Character.isDigit(peek));
                lexRes += ("float           " + v2 + '\n');
                return new Float(v2);
            }else{
                lexRes += ("int             "+v + '\n');
                return new Int(v);
            }
        }
        // 探测标识符和保留字
        if (Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                peek = (char)fileReader.read();
            } while (Character.isLetterOrDigit(peek));
            String s = b.toString();
            // 探测是否是保留字
            Word w = (Word)words.get(s);
            if (w != null){
                lexRes += ("keyword         "+s+'\n');
                return w;
            }
            w = new Word(Tag.ID, s);
            lexRes += ("word            "+s+'\n');
            return w;
        }
        if (peek == ';'){

        }
        // 探测注释
        if (peek == '/'){
            peek = (char)fileReader.read();
            if (peek=='/'){
                // 单行注释的判断
                StringBuffer b = new StringBuffer();
                b.append('/');
                do {
                    b.append(peek);
                    peek = (char)fileReader.read();
                }while (peek != '\n' && peek != '\r');
                String s = b.toString();
                Annotation a = new Annotation(Tag.ANNOTATION,s);
                lexRes += ("annotation      "+s+'\n');
                return a;
            }else if (peek == '*'){
                // 多行注释的判断
                StringBuffer b = new StringBuffer();
                b.append('/');
                do {
                    b.append(peek);
                    peek = (char)fileReader.read();
                    if (peek == '*'){
                        peek = (char)fileReader.read();
                        if (peek == '/'){
                            b.append("*/");
                            break;
                        }
                    }
                }while (true);
                String s = b.toString();
                Annotation a = new Annotation(Tag.ANNOTATION,s);
                lexRes += ("annotation      "+s+'\n');
                peek = (char)fileReader.read();
                return a;
            }
        }
        // 探测运算符号 [+,-,*,/,%,=],同时对[==]进行判断
        if (peek == '+' || peek == '-'|| peek == '*'|| peek == '/' || peek == '%'){
            lexRes += ("operation       "+peek+'\n');
            peek = (char)fileReader.read();
            return new Operator(peek);
        }else if (peek == '='){
            peek = (char)fileReader.read();
            if (peek != '='){
                lexRes += ("operation       "+"="+'\n');
                return new Operator('=');
            }else {
                lexRes += ("relation      "+"=="+'\n');
                return new Relation("==");
            }
        }
        // 探测关系符号 [> < >= <= ]
        if (peek == '>'){
            peek = (char)fileReader.read();
            if (peek == '='){
                lexRes += ("relation      "+">="+'\n');
                peek = (char)fileReader.read();
                return new Relation(">=");
            }else{
                lexRes += ("relation      "+">"+'\n');
                return new Relation(">");
            }
        }else if (peek == '<'){
            peek = (char)fileReader.read();
            if (peek == '='){
                lexRes += ("relation        "+"<="+'\n');
                peek = (char)fileReader.read();
                return new Relation("<=");
            }else {
                lexRes += ("relation      "+"<"+'\n');
                return new Relation("<");
            }
        }
        // 探测分隔符号 separator
        if (peek == '(' || peek == ')' || peek == '{' || peek == '}' || peek == ';'){
            lexRes += ("separator       "+peek+'\n');
            peek = (char)fileReader.read();
            return new Separator(peek);
        }

        Token t = new Token(peek);
        lexRes += ("[error]unknown symbol" + peek + '\n');
        peek = (char)fileReader.read();
        return t;

    }
    public void setLexRes() throws IOException{
        while (true){
            Token token = this.scan();
            if (token.tag == 65535){
                break;
            }
        }
    }
    // 展示分析结果
    public void showResult(){
        System.out.print(this.lexRes);
    }

    public static void main(String arg[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the absolute path to the file you want to analyze: ");
        String firStr = sc.next();
        System.out.println("File: " + firStr);
//        String firStr = "/home/raaabbit/Documents/CompileLabs/test.txt";
        try {
            LexicalAnalyzer lexer = new LexicalAnalyzer(firStr);
            lexer.setLexRes();
            lexer.showResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
