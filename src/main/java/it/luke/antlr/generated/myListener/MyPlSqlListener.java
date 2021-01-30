package it.luke.antlr.generated.myListener;


import it.luke.antlr.generated.PlSqlBaseListener;
import it.luke.antlr.generated.PlSqlParser;

/**
 * @author luke
 * @Title: ${file_name}
 * @Package ${package_name}
 * @date 2021/1/2323:01
 */
public class MyPlSqlListener extends PlSqlBaseListener {

    /**
     * 表节点
     * @param ctx
     */
    @Override public void enterSelected_tableview(PlSqlParser.Selected_tableviewContext ctx) {
        System.out.println("view :" + ctx.getText());
    }
    @Override public void exitSelected_tableview(PlSqlParser.Selected_tableviewContext ctx) { }

}
