package pipe.impl;

import context.Context;
import net.sf.json.JSONObject;
import pipe.Pipe;
import pipe.impl.meta.SQLTYPE;
import pipe.impl.meta.TableInfoMetaGetter;
import utils.FormatUtils;
import utils.LoggerUtil;
import utils.StringUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by baolong on 2016/1/7.
 */
public class A_GetTableInfo implements Pipe {

    private String DBTYPE="MYSQL";

    private TableInfoMetaGetter getter;


    public A_GetTableInfo() {
    }

    public A_GetTableInfo(String DBTYPE) {
        this.DBTYPE = DBTYPE;
    }

    public A_GetTableInfo(String DBTYPE,TableInfoMetaGetter getter) {
        this.DBTYPE = DBTYPE;
        this.getter = getter;
    }

    public TableInfoMetaGetter getGetter() {
        return getter;
    }

    public void setGetter(TableInfoMetaGetter getter) {
        this.getter = getter;
    }

    public void exec(Context c) throws Exception {
        if(getter==null){
            getInfoFromDB(c);
        }else{
            getInfoFromMetaGetter(c);
        }
    }

    public void getInfoFromDB(Context c)  throws Exception {
        Connection conn = (Connection)c.get(Context.JDBC_CONNECTION);
        //获取表信息，设置表的说明
        String currTableName = (String)c.get(Context.CURR_TABLE);
        ResultSet tableSet = null;
        if(DBTYPE.equals("MYSQL")){
            currTableName = currTableName;
        }else if(DBTYPE.equals("ORACLE")){
            currTableName = currTableName.toUpperCase();
        }else if(DBTYPE.equals("DB2")){
            currTableName = currTableName.toUpperCase();
        }else if(DBTYPE.equals("SQLITE")){
            currTableName = currTableName;
        }else{
            throw new Exception("暂不支持的数据类型");
        }
        tableSet = conn.getMetaData().getTables((String)c.get(Context.JDBC_SCHEMA), "%", currTableName, new String[]{"TABLE"});
        if(!tableSet.next()){
            throw new Exception("该表不存在");
        }

        String tableName = tableSet.getString("TABLE_NAME");
        String tableComment = tableSet.getString("REMARKS");
        tableComment = queryTableComment(c,tableName,tableComment);
        c.put(Context.CURR_TABLE_COMMENT, tableComment);
        c.put(Context.CURR_TABLE_COMMENT_ALL, tableComment);
        c.put(Context.CURR_TABLE_COMMENT_JSON, "{}");
        c.put(Context.CURR_TABLE_TF, FormatUtils.formatDBNameToVarName((String)c.get(Context.CURR_TABLE)));

        int count = 0;
        //获取tableName表列信息
        ResultSet columnSet = conn.getMetaData().getColumns((String) c.get(Context.JDBC_SCHEMA), "%", currTableName, "%");
        c.removeList(Context.CURR_TABLE_COLUMNS_NAME);
        c.removeList(Context.CURR_TABLE_COLUMNS_NAME_TF);
        c.removeList(Context.CURR_TABLE_COLUMNS_COMMENT_ALL);
        c.removeList(Context.CURR_TABLE_COLUMNS_COMMENT);
        c.removeList(Context.CURR_TABLE_COLUMNS_COMMENT_JSON);
        c.removeList(Context.CURR_TABLE_COLUMNS_TYPE);
        c.removeList(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE);
        c.removeList(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS);
        while(columnSet.next()){
            count++;
            String columnName = columnSet.getString("COLUMN_NAME");
            String columnComment_ALL = columnSet.getString("REMARKS");
            columnComment_ALL = queryColumnComment(c,currTableName,columnName,columnComment_ALL);
            String sqlType = columnSet.getString("TYPE_NAME");
            String column_size = columnSet.getString("COLUMN_SIZE");
            String decimal_digits = columnSet.getString("DECIMAL_DIGITS");
            c.addtoList(Context.CURR_TABLE_COLUMNS_NAME,columnName);
            c.addtoList(Context.CURR_TABLE_COLUMNS_NAME_TF, FormatUtils.formatDBNameToVarName(columnName));
            c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT_ALL, columnComment_ALL);
            //如果是描述+:+标准JSON格式COMMENT
            if(columnComment_ALL!=null && columnComment_ALL.indexOf(":")>0 && columnComment_ALL.substring(columnComment_ALL.indexOf(":")+1).trim().charAt(0)=='{'){
                try {
                    JSONObject json = JSONObject.fromObject(columnComment_ALL.substring(columnComment_ALL.indexOf(":") + 1).trim());
                    c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT, columnComment_ALL.substring(0, columnComment_ALL.indexOf(":")));
                    c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT_JSON, json);
                }catch(Exception e){
                    c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT, columnComment_ALL);
                    c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT_JSON, new JSONObject());
                    System.out.println("运行异常：在解析数据库Comment的时候，发现JSON字符串报错，错误信息为");
                    System.out.println("tablename["+currTableName+"],columnName["+columnName+"],columnComment_ALL["+columnComment_ALL+"]");
                    throw new Exception("运行异常：在解析数据库Comment的时候，发现JSON字符串报错。"
                            +"此处Exception 可以删除，删除后则将报错的字段视作Input处理，但不建议删除，建议按照严格JSON字符串设置报错处理");
                }
            } else {
                c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT, columnComment_ALL);
                c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT_JSON, new JSONObject());
            }


            c.addtoList(Context.CURR_TABLE_COLUMNS_TYPE, sqlType);
            c.addtoList(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE, column_size);
            c.addtoList(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS, decimal_digits);
        }
        //添加列的数量,
        c.put(Context.CURR_TABLE_COLUMN_COUNT, count);

        //查询主键信息
        ResultSet primaryKeyResultSet = conn.getMetaData().getPrimaryKeys(null, "%", currTableName);
        c.removeList(Context.CURR_TABLE_PRIMARYKEY_COLUMN);
        String primaryKeys = "";
        while(primaryKeyResultSet.next()){
            c.addtoList(Context.CURR_TABLE_PRIMARYKEY_COLUMN,primaryKeyResultSet.getString(4));
        }
    }

    //从数据库层面查询
    public String queryTableComment(Context c,String tablename,String oldComment){
        String result = oldComment;
        if(oldComment!=null && !oldComment.trim().equals("")){
            return oldComment;
        }
        //如果旧的comment中没有值，则尝试mysql的做法
        PreparedStatement sta = null;
        try {
            Connection conn = (Connection)c.get(Context.JDBC_CONNECTION);
            String sql = "";
            String columnName = "";
            if(DBTYPE.equals("MYSQL")){
                sql = "SELECT * FROM information_schema.tables  WHERE table_name ='"+tablename+"'";
                columnName = "TABLE_COMMENT";
            }else if(DBTYPE.equals("ORACLE")){
                sql = "select * from user_tab_comments where upper(table_name) = upper('"+tablename+"')";
                columnName = "COMMENTS";
            }else if(DBTYPE.equals("SQLITE")){
                return "";
            }else if(DBTYPE.equals("DB2")){
                LoggerUtil.info("A_GETTABLEINFO.queryTableComment 异常  在技术层面，此处不应到达，因为在DB2数据库中应该可以从oldComment中获取到comment，因无验证环境，此处报错处理，如果以后看到DB2数据库运行此项目且运行正常，此分支可删");
                LoggerUtil.info("如果报错，请在此分值增加：从DB2数据库中查询表comment的代码");
                return "";
            }else{
                throw new Exception("暂不支持的数据类型");
            }
            sta = conn.prepareStatement(sql);
            ResultSet rs = sta.executeQuery();
            while(rs.next()){
                String comment = rs.getString(columnName);
                if(comment!=null && !comment.trim().equals("")){
                    result = comment;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(sta!=null){
                    sta.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //ORACLE专用
    public String queryColumnComment(Context c,String tablename,String columnname,String oldComment){
        String result = oldComment;
        if(oldComment!=null && !oldComment.trim().equals("")){
            return oldComment;
        }
        //如果旧的comment中没有值，则尝试ORACLE的做法
        PreparedStatement sta = null;
        try {
            Connection conn = (Connection)c.get(Context.JDBC_CONNECTION);
            String sql = "";
            String columnName = "";
            if(DBTYPE.equals("MYSQL")){
                if(StringUtils.isNotEmpty((String)c.get(Context.JDBC_SCHEMA))){
                    sql = "select * from `COLUMNS` where  upper(table_name) =upper('"+tablename+"') and upper(table_schema)=upper('"+(String)c.get(Context.JDBC_SCHEMA)+"')";
                }else{
                    sql = "select * from `COLUMNS` where  upper(table_name) =upper('"+tablename+"')";
                }
                columnName = "COLUMN_COMMENT";
            }else if(DBTYPE.equals("ORACLE")){
                sql = "select * from user_col_comments where upper(TABLE_NAME) = upper('"+tablename+"') and upper(COLUMN_NAME ) =upper('"+columnname+"')";
                columnName = "COMMENTS";
            }else if(DBTYPE.equals("SQLITE")){
                return "";
            }else if(DBTYPE.equals("DB2")){
                LoggerUtil.info("A_GETTABLEINFO.queryColumnComment 异常  在技术层面，此处不应到达，因为在DB2数据库中应该可以从oldComment中获取到comment，因无验证环境，此处报错处理，如果以后看到DB2数据库运行此项目且运行正常，此分支可删");
                LoggerUtil.info("如果报错，请在此分值增加：从DB2数据库中查询列comment的代码");
                return "";
            }else {
                throw new Exception("暂不支持的数据类型");
            }
            sta = conn.prepareStatement(sql);
            ResultSet rs = sta.executeQuery();
            while(rs.next()){
                String comment = rs.getString(columnName);
                if(comment!=null && !comment.trim().equals("")){
                    result = comment;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(sta!=null)
                sta.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void getInfoFromMetaGetter(Context c)  throws Exception {
        String tableName = (String) c.get(Context.CURR_TABLE);
        c.put(Context.CURR_TABLE_COMMENT, getter.getTableComment(tableName));
        c.put(Context.CURR_TABLE_COMMENT_ALL, getter.getTableComment(tableName));//表的commentall一般和comment是相同的
        c.put(Context.CURR_TABLE_COMMENT_JSON, "{}");
        c.put(Context.CURR_TABLE_TF, FormatUtils.formatDBNameToVarName(tableName));//在PipeLine中已经放入
        /**清理掉上一次的数据*/
        c.removeList(Context.CURR_TABLE_COLUMNS_NAME);
        c.removeList(Context.CURR_TABLE_COLUMNS_NAME_TF);
        c.removeList(Context.CURR_TABLE_COLUMNS_COMMENT_ALL);
        c.removeList(Context.CURR_TABLE_COLUMNS_COMMENT);
        c.removeList(Context.CURR_TABLE_COLUMNS_COMMENT_JSON);
        c.removeList(Context.CURR_TABLE_COLUMNS_TYPE);
        c.removeList(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE);
        c.removeList(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS);

        for(int columnIndex=0;columnIndex<getter.getColumnCount(tableName);columnIndex++){
            String columnName = getter.getColumnNameByTableNameAndColumnIndex(tableName, columnIndex);
            String columnComment = getter.getColumnCommentByTableNameAndColumnIndex(tableName, columnIndex);
            JSONObject columnJSON = getter.getColumnJSONByTableNameAndColumnIndex(tableName, columnIndex);
            c.addtoList(Context.CURR_TABLE_COLUMNS_NAME,columnName);
            c.addtoList(Context.CURR_TABLE_COLUMNS_NAME_TF, FormatUtils.formatDBNameToVarName(columnName));
            c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT_ALL, columnComment+":"+columnJSON.toString());
            c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT, columnComment);
            c.addtoList(Context.CURR_TABLE_COLUMNS_COMMENT_JSON, columnJSON);

            c.addtoList(Context.CURR_TABLE_COLUMNS_TYPE, getter.getColumnSQLTypeByTableNameAndColumnIndex(tableName, columnIndex));
            c.addtoList(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE, getter.getColumnSizeAndDecimalByTableNameAndColumnIndex(tableName, columnIndex)[0]);
            c.addtoList(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS, getter.getColumnSizeAndDecimalByTableNameAndColumnIndex(tableName, columnIndex)[1]);
        }
        c.put(Context.CURR_TABLE_COLUMN_COUNT, getter.getColumnCount(tableName));

        String[] primaryKeys = getter.getPrimaryKeysByTableName(tableName);
        if(primaryKeys==null||primaryKeys.length==0){
            throw new RuntimeException("该表必须有主键");
        }
        for(String pk:primaryKeys){
            c.addtoList(Context.CURR_TABLE_PRIMARYKEY_COLUMN,pk);
        }
    }
}
