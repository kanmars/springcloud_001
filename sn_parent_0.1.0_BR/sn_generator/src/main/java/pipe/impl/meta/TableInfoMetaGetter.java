package pipe.impl.meta;

import net.sf.json.JSONObject;

/**
 * Created by baolong on 2016/8/23.
 */
public abstract class TableInfoMetaGetter{

    /**传入tableName返回注释内容，为表的中文名称*/
    public abstract String getTableComment(String tableName);
    /**传入表名，返回该表的字段数量*/
    public abstract int getColumnCount(String tableName);
    /**传入表名和序号，返回该表该字段的columnName*/
    public abstract String getColumnNameByTableNameAndColumnIndex(String tableName,int columnIndex);
    /**传入表名和序号，返回该表该字段的columnComment*/
    public abstract String getColumnCommentByTableNameAndColumnIndex(String tableName,int columnIndex);
    /**传入表名和序号，返回该表该字段的JSON描述*/
    public abstract JSONObject getColumnJSONByTableNameAndColumnIndex(String tableName,int columnIndex);
    /**传入表名和序号，返回该表该字段的SQLType*/
    public abstract String getColumnSQLTypeByTableNameAndColumnIndex(String tableName,int columnIndex);
    /**传入表名和序号，返回该表该字段的columnSize和decimal_digits，以int[2]的形式存在*/
    public abstract String[] getColumnSizeAndDecimalByTableNameAndColumnIndex(String tableName,int columnIndex);
    /**传入表名和序号，返回该表的主键的字段名称列表*/
    public abstract String[] getPrimaryKeysByTableName(String tableName);
}
