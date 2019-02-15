package SyntaxAnalyzer;

import java.util.Scanner;
import java.util.Stack;

public class SyntaxAnalyzer {
    // 预测分析白哦
    private String[][] PPT;
    private CFG _cfg;
    private String operationQueue = "";
    private Stack<Character> stack = new Stack<Character>();
    // 使用CFG初始化语法分析器
    public SyntaxAnalyzer(){
        _cfg = new CFG();
        PPT = new String[7][9];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                PPT[i][j] = "";
            }
        }
        this.PPT[0][0] = _cfg.cfg.get(0);
        this.PPT[0][3] = _cfg.cfg.get(3);
        this.PPT[1][2] = _cfg.cfg.get(1);
        this.PPT[1][8] = _cfg.cfg.get(2);
        this.PPT[2][5] = _cfg.cfg.get(4);
        this.PPT[2][7] = _cfg.cfg.get(4);
        this.PPT[3][1] = _cfg.cfg.get(6);
        this.PPT[3][4] = _cfg.cfg.get(5);
        this.PPT[3][6] = _cfg.cfg.get(6);
        this.PPT[4][5] = _cfg.cfg.get(7);
        this.PPT[4][7] = _cfg.cfg.get(7);
        this.PPT[5][1] = _cfg.cfg.get(9);
        this.PPT[5][4] = _cfg.cfg.get(8);
        this.PPT[5][8] = _cfg.cfg.get(9);
        this.PPT[6][7] = _cfg.cfg.get(11);
    }
    // 参数：非终结符和终结符；返回：操作
    public String getOperation(char nont,char t){
        for (int i = 0; i < _cfg.nonTerminal.length; i++) {
            if (nont == _cfg.nonTerminal[i]){
                for (int j = 0; j < _cfg.terminator.length; j++) {
                    if (t == _cfg.terminator[j]){
                        return PPT[i][j];
                    }
                }
            }
        }
        return "";
    }

    // 判断是否是终结符
    public boolean isTerminator(char c){
        for (char item : _cfg.terminator) {
            if (item == c){
                return true;
            }
        }
        return false;
    }

    // 判断是否是非终结符
    public boolean isNonTerminal(char c) {
        for (char item : _cfg.nonTerminal) {
            if (item == c) {
                return true;
            }
        }
        return false;
    }

    // 表达式右部压栈
    public void next(String temp){
        temp = temp.split(">")[1];
        temp = new StringBuffer(temp).reverse().toString();
        for (int i = 0; i < temp.length(); i++) {
            stack.push(temp.charAt(i));
        }
    }
    // 对一个输入字符串进行语法分析
    public String analyzer(String input){
        // 初始化
        stack.push('$');
        stack.push('S');

        //读入下一符号
        int readIndex = 0;
        char readChar = ' ';
        readChar = input.charAt(readIndex);

        // 判断操作并加入队列
        while(stack.peek() != '$'){
            if (stack.peek() == readChar){
                // 如果栈顶元素等于读头下元素，弹栈
                stack.pop();
                if (readIndex < input.length()-1){
                    readIndex++;
                    readChar = input.charAt(readIndex);
                }
            }else if (stack.peek() == 'ε'){
                // 栈顶是ε，弹栈
                stack.pop();
            }else if (isTerminator(stack.peek())){
                // 栈顶是非终结符，报错
                operationQueue += "ERROR 栈顶是非终结符号";
                return operationQueue;
            } else{
                int i = 0;
                for (i = 0; i < _cfg.cfg.size(); i++) {
                    String tempOpe = getOperation(stack.peek(),readChar);
                    if (tempOpe.equals(_cfg.cfg.get(i))){
                        String temp = _cfg.cfg.get(i);
                        operationQueue += (temp+'\n');
                        stack.pop();
                        next(temp);
                        break;
                    }
                }
                if (i == _cfg.cfg.size()){
                    // 匹配不到，报错
                    operationQueue += "ERROR 不匹配";
                    return operationQueue;
                }
            }
        }
        if (!operationQueue.equals("")){
            return operationQueue;
        }
        return input;
    }
    public static void main(String arg[]){
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
        syntaxAnalyzer._cfg.showCFG();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要分析的字符串（如ibta$）:");
        String test = sc.nextLine();
        System.out.println(syntaxAnalyzer.analyzer(test));
    }
}
