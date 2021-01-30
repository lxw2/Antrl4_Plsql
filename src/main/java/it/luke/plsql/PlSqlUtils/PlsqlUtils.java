package it.luke.plsql.PlSqlUtils;

import it.luke.antlr.generated.PlSqlParser.Tableview_nameContext;
import it.luke.antlr.generated.PlSqlParser.Table_ref_listContext;
import it.luke.plsql.pojo.TableInfo;
import it.luke.plsql.visitor.PlSqlGraphVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @author luke
 * @Title: ${file_name}
 * @Package ${package_name}
 * @date 2021/1/3019:16
 */
public class PlsqlUtils {
    public static TableInfo getLastChildObj(ParseTree ctx, String table_name , TableInfo tableInfo) {
//        String lastChildObj = "";
        if (ctx instanceof Tableview_nameContext) {
            table_name=ctx.getText();
            tableInfo.setTableName(table_name);
            return tableInfo;
        }
        if (ctx instanceof Table_ref_listContext) {
            TableInfo tableInfo1 = PlSqlGraphVisitor.visitTable_ref_list_sub((Table_ref_listContext) ctx, new TableInfo("tmp" + System.currentTimeMillis()));
            return tableInfo1;
        }
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            tableInfo = getLastChildObj(child,table_name,tableInfo);
//            return table_name;
        }
        return tableInfo;
    }
}
