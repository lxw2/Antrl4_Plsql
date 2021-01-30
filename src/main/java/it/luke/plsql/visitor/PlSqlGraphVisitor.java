package it.luke.plsql.visitor;

import it.luke.antlr.generated.PlSqlBaseVisitor;
import it.luke.antlr.generated.PlSqlParser.Table_ref_listContext;
import it.luke.plsql.PlSqlUtils.PlsqlUtils;
import it.luke.plsql.pojo.PlSqlParserTree;
import it.luke.plsql.pojo.TableInfo;
import org.antlr.v4.runtime.tree.ParseTree;


public class PlSqlGraphVisitor extends PlSqlBaseVisitor {
    public String result_tableName = "";
    public TableInfo mainTable = null;
    public PlSqlParserTree tree = null;

    public PlSqlGraphVisitor(String result_tableName, PlSqlParserTree tree) {
        this.result_tableName = result_tableName;
        //初始化结果表
        mainTable = new TableInfo(result_tableName);
        //调用父方法执行遍历解析树的作用
        this.tree = tree;
//        super.visit(tree.getParseTree());
    }

    public void visit() {
        super.visit(tree.getParseTree());
    }

    @Override
    public Object visitTable_ref_list(Table_ref_listContext ctx) {
        if (mainTable.getJoinClaus().size() != 0) {
            return null;
        }
        //表集合的节点
        int childCount = ctx.getChild(0).getChildCount();
        for (int i = 0; i <= childCount - 1; i++) {
            ParseTree child = ctx.getChild(0).getChild(i);
            TableInfo tableInfo = PlsqlUtils.getLastChildObj(child, "", new TableInfo());
            //将关联的表添加到依赖中去
            mainTable.addDepTable(tableInfo);
        }
        return visitChildren(ctx);
    }

    static public TableInfo visitTable_ref_list_sub(Table_ref_listContext ctx, TableInfo subtableInfo) {
        //表集合的节点
        int childCount = ctx.getChild(0).getChildCount();
        for (int i = 0; i <= childCount - 1; i++) {
            ParseTree child = ctx.getChild(0).getChild(i);
            TableInfo tableInfo = PlsqlUtils.getLastChildObj(child, "", new TableInfo());
            //将关联的表添加到依赖中去
            subtableInfo.addDepTable(tableInfo);
        }

        return subtableInfo;
    }

}
