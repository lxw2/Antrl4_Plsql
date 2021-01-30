package it.luke.neo4j;

import it.luke.plsql.pojo.TableInfo;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * @author luke
 * @date 2021/1/2418:50
 */

public class APP {
    //    初始化驱动器
    static Driver driver = null;
    //    初始化执行语句对象
    static StringBuilder sb = new StringBuilder();
    //    初始化
    static StringBuilder relationsb =new StringBuilder();

    public static void main(String[] args) {
        //测试
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"));
        Session session = driver.session();
        session.run("CREATE (a:Person {name: {name}, title: {title}})",
                parameters("name", "Arthur001", "title", "King001"));

        StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} " +
                        "RETURN a.name AS name, a.title AS title",
                parameters("name", "Arthur001"));
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record.get("title").asString() + " " + record.get("name").asString());
        }
        session.close();
        driver.close();
    }

    /**
     * 获取连接会话
     * @return
     */
    static public Session getConnect() {
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"));
        Session session = driver.session();
        return session;
    }


    /**
     * 解析对象生成执行语句,通过调用api接口的形式在neo4j生成对应的节点和关系
     * @param tableInfo
     */
    public static void addDepGraphWithObj(TableInfo tableInfo) {

        Session session = getConnect();
        //默认主节点(目标表)
        String defaultLable = "tables";//默认节点
        String tableName = tableInfo.getTableName();
        //拼接Cypher语句
        //初始化主节点
        sb.append("CREATE (" + tableName + ":" + defaultLable + " {comment:'"+tableName+"', status:'live'})");
        sb.append("\n");
        //初始化与主节点的建立关系语句
        String recyp = "-[:join]->(" + tableName + "),\n";
        relationsb = new StringBuilder();
        relationsb.append("CREATE\n");
        ArrayList<TableInfo> joinClaus = tableInfo.getJoinClaus();
        //判断该执行关系是否有关联关系
        if (joinClaus.size() > 0) {
            relationsb = relation_main_dep(recyp, relationsb, joinClaus);
            String substring = relationsb.substring(0, relationsb.toString().length() - 2);
            sb.append(substring);
        }
        //执行语句
        session.run(sb.toString());
//        System.out.println(substring);
        //关闭资源
        session.close();
        driver.close();
    }

    /**
     * 解析对象并构建有关联关系的Cypher语句
     * @param recyp
     * @param relationsb
     * @param joinClaus
     * @return
     */
    public static StringBuilder relation_main_dep(String recyp, StringBuilder relationsb,  ArrayList<TableInfo> joinClaus) {
        String defaultLable = "tables";
        if (joinClaus.size() == 0) {
            return relationsb;
        }
        for (TableInfo tableInfo : joinClaus) {
            String tableName = tableInfo.getTableName();
            sb.append("CREATE (" + tableName + ":" + defaultLable + " {comment:'table:" + tableName + "', status:'live'})\n");
            if (tableInfo.getJoinClaus().size() == 0) {
                relationsb.append("(" + tableName + ")" + recyp);
            }
            if (tableInfo.getJoinClaus().size() != 0) {
                relationsb.append("(" + tableName + ")" + recyp);//自己添加依赖后,其余的也要添加依赖
                String recyp_sub = "-[:join]->(" + tableName + "),\n";
                relation_main_dep(recyp_sub, relationsb, tableInfo.getJoinClaus());
            }
        }
        return relationsb;
    }
}
