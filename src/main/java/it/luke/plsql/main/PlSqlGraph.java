package it.luke.plsql.main;

import it.luke.neo4j.APP;
import it.luke.plsql.pojo.PlSqlParserTree;
import it.luke.plsql.pojo.TableInfo;
import it.luke.plsql.visitor.PlSqlGraphVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PlSqlGraph {

    private static Properties prop = new Properties();

    /**
     * 解析文件
     *
     * @param fileName
     * @throws IOException
     */
    public static void processFile(String fileName) throws IOException {
        PlSqlParserTree tree = null;
        String resultName = "t_res_log_0130";

        System.out.println("Start " + fileName);
        //获取文件流
        FileInputStream parseFile = new FileInputStream(fileName);
        try {
            //初始化树 parrserTree
            tree = new PlSqlParserTree(new ANTLRInputStream(parseFile));

            PlSqlGraphVisitor visitor = new PlSqlGraphVisitor(resultName, tree);
            //开始访问节点
            visitor.visit();

            TableInfo mainTable = visitor.mainTable;
            APP.addDepGraphWithObj(mainTable);
        } finally {
            parseFile.close();
        }

        System.out.println("End");
    }

    public static void main(String[] args) {
//            String fileName = prop.getProperty("default_file_name");
        String fileName = "testData/plsql.sql";
        if ((fileName != null) && (!fileName.isEmpty())) {
            try {
                processFile(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No files to parse");
            return;
        }
    }

}
