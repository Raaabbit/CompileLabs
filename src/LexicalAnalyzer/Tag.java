package LexicalAnalyzer;

public class Tag {
    // AXCII字符通常被转化为0～255的整数，所以我们使用大于255的整数来实现
    public final static int
            INT = 256,          // 整形数字
            FLOAT = 257,        // 浮点数
            ID=258,             // 标识符号
            TRUE=259,           // true
            FALSE = 260,        // false
            ANNOTATION = 261,   // 注释
            OPERATION = 262,    // 运算符号
            RELATION = 263,     // 关系符号
            // 部分保留字
            IF = 264,
            ELSE = 265,
            FOR = 266,
            WHILE = 267,
            DO = 268,
            RETURN = 269,
            END = 270,
            SEPARATOR = 271;    //分隔符

}
