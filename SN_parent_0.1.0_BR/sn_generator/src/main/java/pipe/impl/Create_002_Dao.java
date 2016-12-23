package pipe.impl;

import context.Context;
import pipe.Pipe;
import utils.FileUtils;
import utils.FormatUtils;
import utils.StringUtils;
import utils.TypeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by baolong on 2016/1/8.
 */
public class Create_002_Dao implements Pipe {
    public void exec(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_DAO))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_DAO)+"]创建不成功");
        }
        String tableName = (String)c.get(Context.CURR_TABLE);
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        String className = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName))+"Mapper";
        String fileName = className+".java";
        File entityFile = new File((String)c.get(Context.PATH_DAO) + File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(entityFile),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n * SN Generator \r\n */\r\n");
        sb.append("package "+(String)c.get(Context.PACKAGE_DAO)+";").append("\r\n").append("\r\n").append("\r\n");
        //增加import
        sb.append("import java.util.List;").append("\r\n");
        sb.append("import java.util.Map;").append("\r\n");
        sb.append("import java.util.HashMap;").append("\r\n");
        sb.append("import " + (String) c.get(Context.PACKAGE_ENTITY) + "." +entityClassName+";").append("\r\n");


        //增加类注释
        sb.append("/**").append("\r\n");
        sb.append(" * "+(String)c.get(Context.CURR_TABLE_COMMENT)).append("\r\n");
        sb.append(" * "+(String)c.get(Context.CURR_TABLE)).append("\r\n");
        sb.append(" */").append("\r\n");

        //增加标签
        List<String> annotation_entity_list = (List)c.get(Context.ANNOTATION_DAO);
        if(annotation_entity_list!=null){
            for (String s : annotation_entity_list){
                sb.append("@"+s).append("\r\n");
            }
        }
        //增加类名
        sb.append("public interface " + className + " ");
        String extendsClass = (String)c.get(Context.EXTENDS_DAO);
        if(!StringUtils.isEmpty(extendsClass)){
            sb.append("extends " + extendsClass+ " ");
        }

        //增加接口
        List<String> implements_entity_list = (List)c.get(Context.IMPLEMENTS_DAO);
        if(implements_entity_list!=null){
            sb.append("implements ");
            for (int i=0;i<implements_entity_list.size();i++){
                sb.append("" + implements_entity_list.get(i));
                if (i!=implements_entity_list.size()-1){
                    sb.append(",");
                }
            }
        }
        sb.append("{").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 查询单个对象").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public " + entityClassName + " select("+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 查询一个对象列表").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public List<" + entityClassName + "> selectList("+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 新增对象，返回增加的数量").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public int insert("+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 通过主键修改对象，返回修改的数量，主键不可修改").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public int updateByPrimaryKey("+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 删除对象，返回删除的数量").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public int delete("+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n").append("\r\n");


        sb.append("    /**").append("\r\n");
        sb.append("     * 查询一个对象HashMap，字段未驼峰化").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public HashMap queryOneMap(HashMap paramMap);").append("\r\n").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 查询一个对象List<HashMap>，字段未驼峰化").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public List<HashMap> queryListMap(HashMap paramMap);").append("\r\n").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 原子性更新一个对象，主键不可修改").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public int updateCAS(HashMap paramMap);").append("\r\n").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 分页查询").append("\r\n");
        sb.append("     * 参数有两种情况：").append("\r\n");
        sb.append("     *     1、传递countFlag=Y标记,则返回的是计数").append("\r\n");
        sb.append("     *     2、传递countFlag=N标记、limitStart、limitSize,则返回的是分页结果,字段未驼峰化").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public List<HashMap> queryForPage(HashMap paramMap);").append("\r\n").append("\r\n");


        sb.append("    /**").append("\r\n");
        sb.append("     * 批量插入,如果是mysql数据库，需要在jdbcUrl中设置allowMultiQueries=true参数才可以使用").append("\r\n");
        sb.append("     * 如果为MYSQL数据库，返回值为插入条数，如果为ORACLE，返回值为第一条更新语句的指定结果，并非所有批量插入的总影响行数").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public int insertBatch(List<HashMap> "+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n").append("\r\n");

        sb.append("    /**").append("\r\n");
        sb.append("     * 批量更新,如果是mysql数据库，需要在jdbcUrl中设置allowMultiQueries=true参数才可以使用").append("\r\n");
        sb.append("     * 返回值为第一条更新语句的执行结果，并非所有批量更新的语句总和").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public int updateBatch(List<HashMap> "+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n").append("\r\n");

        //主键查询
        sb.append("    /**").append("\r\n");
        sb.append("     * 根据主键查询单个对象").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public " + entityClassName + " selectByPrimaryKey(");
        String parameterSign = "";
        int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);
        List<String> primaryKeyColumn = (List<String>)c.get(Context.CURR_TABLE_PRIMARYKEY_COLUMN);
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    if(StringUtils.isNotEmpty(parameterSign))parameterSign=parameterSign+",";
                    //如果是主键
                    parameterSign=parameterSign+TypeUtils.transDBType2JavaType(column_type,column_size,decimal_digits)+" "+column_name_tf;
                }
            }
        }
        sb.append(parameterSign);
        sb.append(");").append("\r\n").append("\r\n");


        sb.append("    /**").append("\r\n");
        sb.append("     * editInfo，空的update语句").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public int editInfo(HashMap paramMap);").append("\r\n").append("\r\n");
        sb.append("    ").append("\r\n");


        sb.append("}").append("\r\n");
        pw.println(sb.toString());
        pw.close();
    }
}
