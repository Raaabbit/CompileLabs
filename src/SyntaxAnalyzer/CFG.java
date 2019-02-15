package SyntaxAnalyzer;

import java.util.ArrayList;

// 上下文无关文法
public class CFG {
    public ArrayList<String> cfg;
    public char[] terminator;
    public char[] nonTerminal;
    public CFG(){
        terminator = new char[9];
        nonTerminal = new char[7];
        this.cfg = new ArrayList<String>();
        this.cfg.add("S->iCtSA");
        this.cfg.add("A->eS");
        this.cfg.add("A->ε");
        this.cfg.add("S->a");
        this.cfg.add("C->DB");
        this.cfg.add("B->oDC'");
        this.cfg.add("B->ε");
        this.cfg.add("D->EF");
        this.cfg.add("F->oEF");
        this.cfg.add("F->ε");
        this.cfg.add("E->(C)");
        this.cfg.add("E->b");

        this.nonTerminal[0] = 'S';
        this.nonTerminal[1] = 'A';
        this.nonTerminal[2] = 'C';
        this.nonTerminal[3] = 'B';
        this.nonTerminal[4] = 'D';
        this.nonTerminal[5] = 'F';
        this.nonTerminal[6] = 'E';

        this.terminator[0] = 'i';
        this.terminator[1] = 't';
        this.terminator[2] = 'e';
        this.terminator[3] = 'a';
        this.terminator[4] = 'o';
        this.terminator[5] = '(';
        this.terminator[6] = ')';
        this.terminator[7] = 'b';
        this.terminator[8] = '$';
    }
    public void showCFG(){
        System.out.print("终结符: ");
        for (char item : terminator) {
            System.out.print(item+" ");
        }
        System.out.print("\n===============================================\n");
        System.out.print("非终结符: ");
        for (char item : nonTerminal) {
            System.out.print(item+" ");
        }
        System.out.print("\n===============================================\n");
        System.out.println("上下文无关文法：");
        for (int i = 0; i < cfg.size(); i++) {
            System.out.println(cfg.get(i));
        }
    }
}
