package it.luke.plsql.pojo;

import java.util.ArrayList;

/**
 * @author luke
 * @Title: ${file_name}
 * @Package ${package_name}
 * @date 2021/1/2415:30
 */
public class TableInfo {
    String tableName = "";//表名
    ArrayList<TableInfo> joinClaus = new ArrayList<TableInfo>();//记录表的依赖关系

    public TableInfo(String tableName, ArrayList<TableInfo> joinClaus) {
        this.tableName = tableName;
        this.joinClaus = joinClaus;
    }

    public TableInfo() {
    }

    public TableInfo(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<TableInfo> getJoinClaus() {
        return joinClaus;
    }

    public void setJoinClaus(ArrayList<TableInfo> joinClaus) {
        this.joinClaus = joinClaus;
    }

    public void addDepTable(String tableName) {
        this.joinClaus.add(new TableInfo(tableName));
    }

    public void addDepTable(TableInfo tableInfo) {
        this.joinClaus.add(tableInfo);
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        if (joinClaus.size() != 0) {
            for (int i = 0; i < joinClaus.size(); i++) {
                sb.append( joinClaus.get(i).toString());
            }
        }
        sb.append(tableName);
        return "\r\n\t"+sb.toString();
    }


}
