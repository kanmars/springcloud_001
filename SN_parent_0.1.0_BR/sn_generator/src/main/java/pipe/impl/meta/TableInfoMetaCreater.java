package pipe.impl.meta;

import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by baolong on 2016/8/23.
 */
public class TableInfoMetaCreater extends TableInfoMetaGetter{

    private HashMap<String,String> tableComments = new HashMap<String,String>();
    private HashMap<String,Integer> columnCounts = new HashMap<String,Integer>();
    private HashMap<String,String> columnNames = new HashMap<String,String>();
    private HashMap<String,String> columnComments = new HashMap<String,String>();
    private HashMap<String,JSONObject> columnJSONs =new HashMap<String,JSONObject>();
    private HashMap<String,String> columnSQLTypes = new HashMap<String,String>();
    private HashMap<String,String[]> columnSizeAndDecimalDigits = new HashMap<String,String[]>();
    private HashMap<String,String[]> tablePrimarykeys = new HashMap<String,String[]>();

    /**传入tableName返回注释内容，为表的中文名称*/
    public String getTableComment(String tableName) {
        return tableComments.get(tableName);
    }
    /**传入表名，返回该表的字段数量*/
    public int getColumnCount(String tableName) {
        return columnCounts.get(tableName);
    }
    /**传入表名和序号，返回该表该字段的columnName*/
    public String getColumnNameByTableNameAndColumnIndex(String tableName, int columnIndex) {
        return columnNames.get(tableName+"_"+columnIndex);
    }
    /**传入表名和序号，返回该表该字段的columnComment */
    public String getColumnCommentByTableNameAndColumnIndex(String tableName, int columnIndex) {
        return columnComments.get(tableName+"_"+columnIndex);
    }
    /**传入表名和序号，返回该表该字段的JSON描述*/
    public JSONObject getColumnJSONByTableNameAndColumnIndex(String tableName, int columnIndex) {
        return columnJSONs.get(tableName+"_"+columnIndex);
    }
    /**传入表名和序号，返回该表该字段的SQLType*/
    public String getColumnSQLTypeByTableNameAndColumnIndex(String tableName, int columnIndex) {
        return columnSQLTypes.get(tableName + "_" + columnIndex);
    }
    /**传入表名和序号，返回该表该字段的columnSize和decimal_digits，以int[2]的形式存在*/
    public String[] getColumnSizeAndDecimalByTableNameAndColumnIndex(String tableName, int columnIndex) {
        return columnSizeAndDecimalDigits.get(tableName+"_"+columnIndex);
    }
    /**传入表名和序号，返回该表的主键的字段名称列表*/
    public String[] getPrimaryKeysByTableName(String tableName) {
        return tablePrimarykeys.get(tableName);
    }

    public TableInfoMetaCreater(){

    }
    /**增加一张表*/
    public void A_001_addTable(String tableName,String tableComment){
        tableComments.put(tableName,tableComment);
    }

    /**
     * 增加一个column
     * @param tableName 表名  tbl_demo_info
     * @param columnName    列名  order_nm
     * @param columnComment 列名注释    订单名称
     * @param isPrimaryKey  是否是主键   true或者false
     * @param sqlType       数据库类型
     * @param columnSize    数据库长度
     * @param decimalDigit  数据库小数点精度
     * @param columnJSON    column的JSON
     * @return
     */
    public String addColumn(String tableName,String columnName,String columnComment,boolean isPrimaryKey,SQLTYPE sqlType,String columnSize,String decimalDigit,JSONObject columnJSON){
        Integer columnCount = columnCounts.get(tableName);
        if(columnCount==null){
            columnCount = 0;
        }
        columnCount +=1;
        columnCounts.put(tableName,columnCount);
        String aKey = tableName+"_"+(columnCount-1);//用columnIndex标注的akey，因此此处要-1
        columnNames.put(aKey,columnName);
        columnComments.put(aKey,columnComment);
        if(isPrimaryKey){
            String[] pks = tablePrimarykeys.get(tableName);
            if(pks == null){
                pks = new String[]{};
            }
            List pks_arrays = new ArrayList();
            pks_arrays.addAll(Arrays.asList(pks));
            pks_arrays.add(columnName);
            pks = new String[pks_arrays.size()];
            int i=0;
            for(Object o : pks_arrays){
                String s = (String)o;
                pks[i]=s;
                i++;
            }
            tablePrimarykeys.put(tableName,pks);
        }
        columnSQLTypes.put(aKey,sqlType.toString());
        columnSizeAndDecimalDigits.put(aKey,new String[]{columnSize,decimalDigit});
        columnJSONs.put(aKey,columnJSON);
        return aKey;
    }

    public String[] getTables(){
        String[] result = new String[tableComments.keySet().size()];
        int i=0;
        for(String tableName:tableComments.keySet()){
            result[i]=tableName;
            i++;
        }
        return result;
    }

}
