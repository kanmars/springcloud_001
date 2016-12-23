package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by baolong on 2016/1/7.
 */
public class Context extends HashMap {
    public static final String FILE_CHARSET = "file.charset";
    public static final String JDBC_DRIVERCLASS="jdbc.driverClass";
    public static final String JDBC_CONNECTIONURL="jdbc.connectionURL";
    public static final String JDBC_USERNAME="jdbc.username";
    public static final String JDBC_PASSWORD="jdbc.password";
    public static final String JDBC_SCHEMA="jdbc.schema";

    public static final String PATH_ENTITY = "path.entity";
    public static final String PATH_DAO = "path.dao";
    public static final String PATH_MAPPER = "path.mapper";
    public static final String PATH_LOGIC = "path.logic";
    public static final String PATH_CONTROLLER = "path.controller";
    public static final String PATH_FTL = "path.ftl";
    public static final String PATH_JS = "path.js";

    public static final String PACKAGE_ENTITY = "package.entity";
    public static final String PACKAGE_DAO = "package.dao";
    public static final String PACKAGE_LOGIC = "package.logic";
    public static final String PACKAGE_CONTROLLER = "package.controller";

    public static final String JDBC_CONNECTION="jdbc.connection";

    public static final String CURR_TABLE ="curr.table";//当前原始表名
    public static final String CURR_TABLE_TF ="curr.table.TF";//当前原始表名的驼峰形式
    public static final String CURR_TABLE_COMMENT_ALL ="curr.table.comment.all";//当前原始表的comment
    public static final String CURR_TABLE_COMMENT ="curr.table.comment";//当前原始表的comment加工后的显示
    public static final String CURR_TABLE_COMMENT_JSON ="curr.table.comment.json";//当前原始表的comment加工后的JSON

    public static final String CURR_TABLE_PRIMARYKEY_COLUMN ="curr.table.primary.column";//当前原始表的主键的列

    public static final String CURR_TABLE_COLUMN_COUNT ="curr.table.column.count";//当前表的列的数量
    public static final String CURR_TABLE_COLUMNS_NAME ="curr.table.column.names";//当前表的列的原始名称
    public static final String CURR_TABLE_COLUMNS_NAME_TF ="curr.table.column.names.tf";//当前表的列的原始名称的驼峰形式
    public static final String CURR_TABLE_COLUMNS_COMMENT_ALL ="curr.table.column.comment.all";//当前表的列的备注，全数据
    public static final String CURR_TABLE_COLUMNS_COMMENT ="curr.table.column.comment";//当前表的列的备注
    public static final String CURR_TABLE_COLUMNS_COMMENT_JSON ="curr.table.column.comment.json";//当前表的列的备注
    public static final String CURR_TABLE_COLUMNS_TYPE ="curr.table.column.type";//当前表的列的类型
    public static final String CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE ="curr.table.column.type.column.size";//当前表的列的类型的长度
    public static final String CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS ="curr.table.column.type.column.size.decimal.digits";//当前表的列的类型的小数位精度

    //list
    public static final String ANNOTATION_ENTITY = "annotation.entity";
    //list
    public static final String ANNOTATION_DAO="annotation.dao";
    //list
    public static final String ANNOTATION_LOGIC="annotation.logic";
    //list
    public static final String ANNOTATION_CONTROLLER="annotation.controller";

    public static final String EXTENDS_ENTITY = "extends.entity";
    public static final String EXTENDS_DAO = "extends.dao";
    public static final String EXTENDS_LOGIC = "extends.logic";
    public static final String EXTENDS_CONTROLLER = "extends.controller";

    //list
    public static final String IMPLEMENTS_ENTITY = "implements.entity";
    //list
    public static final String IMPLEMENTS_DAO = "implements.dao";
    //list
    public static final String IMPLEMENTS_LOGIC = "implements.logic";
    //list
    public static final String IMPLEMENTS_CONTROLLER = "implements.controller";


    /**
     * @throws Exception
     */
    public void init() throws Exception{
        Class.forName((String) this.get(Context.JDBC_DRIVERCLASS));
        Connection conn = DriverManager.getConnection((String) this.get(Context.JDBC_CONNECTIONURL), (String) this.get(Context.JDBC_USERNAME), (String) this.get(Context.JDBC_PASSWORD));
        this.put(Context.JDBC_CONNECTION, conn);
    }

    public void close() throws Exception{
        Context c = this;
        Connection conn = (Connection)c.get(Context.JDBC_CONNECTION);
        conn.close();
    }

    public void addtoList(String listname,Object value){
        List<Object> list = (List<Object>)this.get(listname);
        if(list ==null){
            list = new ArrayList<Object>();
            this.put(listname,list);
        }
        list.add(value);
    }
    public void removeList(String listname){
        this.remove(listname);
    }
}
