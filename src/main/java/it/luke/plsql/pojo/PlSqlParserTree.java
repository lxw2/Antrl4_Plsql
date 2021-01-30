package it.luke.plsql.pojo;

import it.luke.antlr.generated.PlSqlLexer;
import it.luke.antlr.generated.PlSqlParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * PlSqlRuleVisitor
 */
public class PlSqlParserTree {

    private ANTLRInputStream input;
    private PlSqlLexer lexer;
    private CommonTokenStream tokens;
    private PlSqlParser parser;
    private TokenStreamRewriter rewriter;
    private ParseTree parseTree;

    public ANTLRInputStream getInput() {
        return input;
    }

    public TokenStreamRewriter getRewriter() {
        return rewriter;
    }

    public ParseTree getParseTree() {
        return parseTree;
    }

    public PlSqlParserTree(ANTLRInputStream input) {
        this.input = input;//传入输入流
        parseInput();//进行初始化
    }

    // 初始化
    private ParseTree parseInput() {
        //新建词法分析器
        lexer = new PlSqlLexer(input);
        lexer.removeErrorListeners();
        //根据词法分析构建记号流
        tokens = new CommonTokenStream(lexer);
        //解析 后生成解析树
        parser = new PlSqlParser(tokens);
        rewriter = new TokenStreamRewriter(tokens);
        //compilation_unit 是其中一个主节点 在g4文件可以找到
        parseTree = parser.compilation_unit();

        return parseTree;
    }

    public String getResultText() {
        return rewriter.getText();
    }
}
