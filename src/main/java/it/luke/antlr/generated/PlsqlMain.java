package it.luke.antlr.generated;


import it.luke.antlr.generated.myListener.MyPlSqlListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @author luke
 * @Title: ${file_name}
 * @Package ${package_name}
 * @date 2021/1/2322:56
 */
public class PlsqlMain {
    public static void run(String expr) {

        //对每一个输入的字符串，构造一个 ANTLRStringStream 流 in
        ANTLRInputStream in = new ANTLRInputStream(expr);

        //用 in 构造词法分析器 lexer，词法分析的作用是产生记号
        PlSqlLexer lexer = new PlSqlLexer(in);

        //用词法分析器 lexer 构造一个记号流 tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //解析语法
        PlSqlParser parser = new PlSqlParser(tokens);

        //通过监听器的方式
        ParseTreeWalker parserWalk = new ParseTreeWalker();
        //重写部分监听操作,输出列名和表名
        parserWalk.walk(new MyPlSqlListener(),parser.selected_tableview() );

    }


    public static void main(String[] args) {
        run("select a from tabla;");
    }
}
