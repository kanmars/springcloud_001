package pipe.impl;

import context.Context;
import context.GeneratorJSONProperties;
import net.sf.json.JSONObject;
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
public class Create_003_Mapper implements Pipe {

    /**
     * 数据库类型，可以填写MYSQL或者ORACLE
     */
    public String DBTYPE = "MYSQL";
    /**
     * 设置生成的queryforpage的默认类型为true
     */
    public String DEFAULT_QUERY_FORPAGE_like = "true";

    public Create_003_Mapper() {

    }

    public Create_003_Mapper(String DBTYPE, String DEFAULT_QUERY_FORPAGE_like) {
        this.DBTYPE = DBTYPE;
        this.DEFAULT_QUERY_FORPAGE_like = DEFAULT_QUERY_FORPAGE_like;
    }


    public void exec(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_MAPPER))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_MAPPER)+"]创建不成功");
        }
        String tableName = (String)c.get(Context.CURR_TABLE);
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        String daoClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName))+"Mapper";
        String mapperXmlName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName))+"Mapper";
        String fileName = mapperXmlName+".xml";
        File entityFile = new File((String)c.get(Context.PATH_MAPPER) + File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(entityFile),(String)c.get(Context.FILE_CHARSET)));

        String packageDao = (String)c.get(Context.PACKAGE_DAO);
        String packageEntity = (String)c.get(Context.PACKAGE_ENTITY);

        //准备内容
        //************************************************************************************************************
        //头部
        //************************************************************************************************************
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\r\n");
        sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append("\r\n");
        sb.append("<mapper namespace=\""+packageDao+"."+daoClassName+"\">").append("\r\n");



        //************************************************************************************************************
        //resultMap
        //************************************************************************************************************
        sb.append("    <resultMap id=\"BaseResultMap\" type=\""+packageEntity+"."+entityClassName+"\">").append("\r\n");
        sb.append("        <constructor>").append("\r\n");
        //增加参数
        int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);
        List<String> primaryKeyColumn = (List<String>)c.get(Context.CURR_TABLE_PRIMARYKEY_COLUMN);
        //增加主键列表
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            if(isPrimaryKey){
                sb.append("            <idArg column=\""+column_name
                        +"\" javaType=\""+TypeUtils.transDBType2MapperJavaType(column_type, column_size, decimal_digits)
                        +"\" jdbcType=\""+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)
                        +"\" />").append("\r\n");
            }
        }
        //增加普通字段
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            if(!isPrimaryKey){
                sb.append("            <arg column=\""+column_name
                        +"\" javaType=\""+TypeUtils.transDBType2MapperJavaType(column_type, column_size, decimal_digits)
                        +"\" jdbcType=\""+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)
                        +"\" />").append("\r\n");
            }
        }
        sb.append("\r\n");
        sb.append("        </constructor>").append("\r\n");
        sb.append("    </resultMap>").append("\r\n");


        //************************************************************************************************************
        //Base_Column_List
        //************************************************************************************************************
        sb.append("    <sql id=\"Base_Column_List\">").append("\r\n");
        String base_column_list ="";
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            if(StringUtils.isNotEmpty(base_column_list))base_column_list=base_column_list+",";
            base_column_list = base_column_list + column_name;
        }
        sb.append("        "+base_column_list).append("\r\n");
        sb.append("    </sql>").append("\r\n");


        //************************************************************************************************************
        //select    select查询单个对象
        //************************************************************************************************************
        sb.append("    <select id=\"select\" parameterType=\""+packageEntity+"."+entityClassName+"\" resultMap=\"BaseResultMap\">").append("\r\n");
        sb.append("        select").append("\r\n");
        sb.append("        <include refid=\"Base_Column_List\" />").append("\r\n");
        sb.append("        from "+tableName+"").append("\r\n");
        sb.append("        where 1=1").append("\r\n");

        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
            sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"}").append("\r\n");
            sb.append("        </if>").append("\r\n");

        }
        sb.append("    </select>").append("\r\n");


        //************************************************************************************************************
        //selectList    selectList查询对象列表
        //************************************************************************************************************
        sb.append("    <select id=\"selectList\" parameterType=\""+packageEntity+"."+entityClassName+"\" resultMap=\"BaseResultMap\">").append("\r\n");
        sb.append("        select").append("\r\n");
        sb.append("        <include refid=\"Base_Column_List\" />").append("\r\n");
        sb.append("        from "+tableName+"").append("\r\n");
        sb.append("        where 1=1").append("\r\n");

        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
            sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"}").append("\r\n");
            sb.append("        </if>").append("\r\n");

        }
        sb.append("    </select>").append("\r\n");




        //************************************************************************************************************
        //insert    新增对象，返回修改的数量
        //************************************************************************************************************
        sb.append("    <insert id=\"insert\" parameterType=\""+packageEntity+"."+entityClassName+"\" >").append("\r\n");
        sb.append("        insert into " + tableName + "").append("\r\n");
        sb.append("        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("            <if test=\""+column_name_tf+" != null \">").append("\r\n");
            sb.append("                "+column_name+",").append("\r\n");
            sb.append("            </if>").append("\r\n");
        }
        sb.append("        </trim>").append("\r\n");
        sb.append("        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("            <if test=\""+column_name_tf+" != null \">").append("\r\n");
            sb.append("                #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"},").append("\r\n");
            sb.append("            </if>").append("\r\n");
        }
        sb.append("        </trim>").append("\r\n");
        sb.append("    </insert>").append("\r\n");


        //************************************************************************************************************
        //update    修改对象，返回修改的数量，非主键不可修改
        //************************************************************************************************************
        sb.append("    <update id=\"updateByPrimaryKey\" parameterType=\""+packageEntity+"."+entityClassName+"\" >").append("\r\n");
        sb.append("        update "+tableName).append("\r\n");
        sb.append("        <set>").append("\r\n");
        //非主键
        //增加普通字段
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            if(!isPrimaryKey){
                sb.append("            <if test=\""+column_name_tf+ " != null\">").append("\r\n");
                sb.append("                "+column_name+" = #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"} ,").append("\r\n");
                sb.append("            </if>").append("\r\n");
            }
        }
        sb.append("        </set>").append("\r\n");
        sb.append("        where 1=1").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            if(isPrimaryKey){
                sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"}").append("\r\n");
            }
        }
        sb.append("    </update>").append("\r\n");





        //************************************************************************************************************
        //delete    删除对象，返回删除的数量
        //************************************************************************************************************
        sb.append("    <delete id=\"delete\" parameterType=\""+packageEntity+"."+entityClassName+"\" >").append("\r\n");
        sb.append("        delete from "+tableName).append("\r\n");
        sb.append("        where 1=1").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("            <if test=\""+column_name_tf+ " != null and "+column_name_tf+ " != '' \">").append("\r\n");
            sb.append("                and "+column_name+" = #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"}").append("\r\n");
            sb.append("            </if>").append("\r\n");
        }
        sb.append("    </delete>").append("\r\n");





        //************************************************************************************************************
        //queryOneMap    queryOneMap查询一个对象Map，字段未驼峰化
        //************************************************************************************************************
        sb.append("    <select id=\"queryOneMap\" parameterType=\"java.util.HashMap\" resultType=\"java.util.HashMap\">").append("\r\n");
        sb.append("        select").append("\r\n");
        sb.append("        <include refid=\"Base_Column_List\" />").append("\r\n");
        sb.append("        from "+tableName+"").append("\r\n");
        sb.append("        where 1=1").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
            sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"}").append("\r\n");
            sb.append("        </if>").append("\r\n");

        }
        sb.append("    </select>").append("\r\n");

        //************************************************************************************************************
        //queryListMap    queryListMap查询一个对象List<Map>，字段未驼峰化
        //************************************************************************************************************
        sb.append("    <select id=\"queryListMap\" parameterType=\"java.util.HashMap\" resultType=\"java.util.HashMap\">").append("\r\n");
        sb.append("        select").append("\r\n");
        sb.append("        <include refid=\"Base_Column_List\" />").append("\r\n");
        sb.append("        from "+tableName+"").append("\r\n");
        sb.append("        where 1=1").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
            sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"}").append("\r\n");
            sb.append("        </if>").append("\r\n");

        }
        sb.append("    </select>").append("\r\n");


        //************************************************************************************************************
        //updateCAS    updateCAS原子性更新一个对象，主键不可修改
        //************************************************************************************************************
        sb.append("    <update id=\"updateCAS\" parameterType=\"java.util.HashMap\" >").append("\r\n");
        sb.append("        update "+tableName).append("\r\n");
        sb.append("        <set>").append("\r\n");
        //非主键
        //增加普通字段
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            if(!isPrimaryKey){
                sb.append("            <if test=\""+column_name_tf+ "_new != null\">").append("\r\n");
                sb.append("                "+column_name+" = #{"+column_name_tf+"_new,jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"} ,").append("\r\n");
                sb.append("            </if>").append("\r\n");
            }
        }
        sb.append("        </set>").append("\r\n");
        sb.append("        where 1=1").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("            <if test=\""+column_name_tf+ " != null \">").append("\r\n");
            sb.append("                and "+column_name+" = #{"+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"}").append("\r\n");
            sb.append("            </if>").append("\r\n");
        }
        sb.append("    </update>").append("\r\n");



        //************************************************************************************************************
        //queryForPage    queryForPage分页查询
        //参数有两种情况：
        //    1、传递countFlag=Y标记,则返回的是计数
        //    2、传递countFlag=N标记、limitStart、limitSize,则返回的是分页结果,字段未驼峰化
        //根据DEFAULT_QUERY_FORPAGE_like去判断，是否生成like的查询，或者标准的等于型查询
        //************************************************************************************************************
        if(DBTYPE.equals("MYSQL")||DBTYPE.equals("SQLITE")){
            sb.append("    <select id=\"queryForPage\" parameterType=\"java.util.HashMap\" resultType=\"java.util.HashMap\">").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'Y'.toString() \">").append("\r\n");
            sb.append("        select count(1) as TOTALCOUNT from (").append("\r\n");
            sb.append("        </if>").append("\r\n").append("\r\n").append("\r\n");

            sb.append("        select").append("\r\n");
            sb.append("        <include refid=\"Base_Column_List\" />").append("\r\n");
            sb.append("        from "+tableName+"").append("\r\n");
            sb.append("        where 1=1").append("\r\n");
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_comment_all = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                JSONObject column_comment_json = (JSONObject)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
                String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
                try{
                    type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
                }catch (Exception e){
                    //e.printStackTrace();
                }
                //如果默认标志为true，并且字段类型为input，并且类型是VARCHAR型的，则生成like型的字段。
                String MAPPERJDBCTYPE = TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits);
                if(DEFAULT_QUERY_FORPAGE_like.equals("true")&&type.equals(GeneratorJSONProperties.FIELD_type_input)&&MAPPERJDBCTYPE.indexOf("CHAR")>0){
                    sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                    sb.append("            and "+column_name+" like CONCAT(#{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"},'%')").append("\r\n");
                    sb.append("        </if>").append("\r\n");
                    continue;
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_date)||type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                    //如果是date或者datetime类型，则先写默认条件
                    sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                    sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                    sb.append("        </if>").append("\r\n");
                    //判断是否有queryrange标志
                    {
                        try{
                            JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                            Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                            if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                                //如果有queryrange条件，则增加_start和_end查询，闭区间
                                sb.append("        <if test=\""+column_name_tf+"_start != null and "+column_name_tf+"_start != '' \">").append("\r\n");
                                sb.append("            and "+column_name+" &gt;= #{"+column_name_tf+"_start,jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                                sb.append("        </if>").append("\r\n");
                                sb.append("        <if test=\""+column_name_tf+"_end != null and "+column_name_tf+"_end != '' \">").append("\r\n");
                                sb.append("            and "+column_name+" &lt;= #{"+column_name_tf+"_end,jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                                sb.append("        </if>").append("\r\n");
                            }
                        }catch (Exception e){
                            //e.printStackTrace();
                            System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                        }
                    }
                    //判断是否有queryrange标志结束
                    continue;
                }
                sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                sb.append("        </if>").append("\r\n");
            }
            sb.append("").append("\r\n").append("\r\n");

            sb.append("        <if test=\"countFlag != null and countFlag == 'N'.toString() \">").append("\r\n");
            sb.append("        limit #{limitStart,jdbcType=DECIMAL},#{limitSize,jdbcType=DECIMAL}").append("\r\n");
            sb.append("        </if>").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'Y'.toString() \">").append("\r\n");
            sb.append("        ) as TMP_COUNT_TABLE").append("\r\n");
            sb.append("        </if>").append("\r\n");

            sb.append("    </select>").append("\r\n");
        }else if(DBTYPE.equals("ORACLE")){
            sb.append("    <select id=\"queryForPage\" parameterType=\"java.util.HashMap\" resultType=\"java.util.HashMap\">").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'Y'.toString() \">").append("\r\n");
            sb.append("        select count(1) as TOTALCOUNT from (").append("\r\n");
            sb.append("        </if>").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'N'.toString() \">").append("\r\n");
            sb.append("        <![CDATA[ select * from ( select interTable.*,rownum rn from ( ]]>").append("\r\n");
            sb.append("        </if>").append("\r\n").append("\r\n");

            sb.append("        select").append("\r\n");
            sb.append("        <include refid=\"Base_Column_List\" />").append("\r\n");
            sb.append("        from " + tableName + "").append("\r\n");
            sb.append("        where 1=1").append("\r\n");
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_comment_all = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                JSONObject column_comment_json = (JSONObject)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
                String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
                try{
                    type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
                }catch (Exception e){
                    //e.printStackTrace();
                }
                //如果默认标志为true，并且字段类型为input，并且类型是VARCHAR型的，则生成like型的字段。
                String MAPPERJDBCTYPE = TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits);
                if(DEFAULT_QUERY_FORPAGE_like.equals("true")&&type.equals(GeneratorJSONProperties.FIELD_type_input)&&MAPPERJDBCTYPE.indexOf("CHAR")>0){
                    sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                    sb.append("            and "+column_name+" like CONCAT(#{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"},'%')").append("\r\n");
                    sb.append("        </if>").append("\r\n");
                    continue;
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_date)||type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                    //如果是date或者datetime类型，则先写默认条件
                    sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                    sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                    sb.append("        </if>").append("\r\n");
                    //判断是否有queryrange标志
                    {
                        try{
                            JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                            Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                            if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                                //如果有queryrange条件，则增加_start和_end查询，闭区间
                                sb.append("        <if test=\""+column_name_tf+"_start != null and "+column_name_tf+"_start != '' \">").append("\r\n");
                                sb.append("            and "+column_name+" &gt;= #{"+column_name_tf+"_start,jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                                sb.append("        </if>").append("\r\n");
                                sb.append("        <if test=\""+column_name_tf+"_end != null and "+column_name_tf+"_end != '' \">").append("\r\n");
                                sb.append("            and "+column_name+" &lt;= #{"+column_name_tf+"_end,jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                                sb.append("        </if>").append("\r\n");
                            }
                        }catch (Exception e){
                            //e.printStackTrace();
                            System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                        }
                    }
                    //判断是否有queryrange标志结束
                    continue;
                }
                sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                sb.append("        </if>").append("\r\n");
            }
            sb.append("").append("\r\n").append("\r\n");
            //MYSQL从0开始,ORACLE从1开始，而dao层统一从0开始，因此此处为limitStart<outerTable.rn<=limitStart+limitSize
            sb.append("        <if test=\"countFlag != null and countFlag == 'N'.toString() \">").append("\r\n");
            sb.append("        ) interTable where rownum &lt;= #{limitStart,jdbcType=DECIMAL}+#{limitSize,jdbcType=DECIMAL}").append("\r\n");
            sb.append("        </if>").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'N'.toString() \">").append("\r\n");
            sb.append("        ) outerTable where outerTable.rn > #{limitStart,jdbcType=DECIMAL}").append("\r\n");
            sb.append("        </if>").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'Y'.toString() \">").append("\r\n");
            sb.append("        ) TMP_COUNT_TABLE").append("\r\n");
            sb.append("        </if>").append("\r\n");

            sb.append("    </select>").append("\r\n");
        }else if(DBTYPE.equals("DB2")){
            sb.append("    <select id=\"queryForPage\" parameterType=\"java.util.HashMap\" resultType=\"java.util.HashMap\">").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'Y'.toString() \">").append("\r\n");
            sb.append("        select count(1) as TOTALCOUNT from (").append("\r\n");
            sb.append("        </if>").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'N'.toString() \">").append("\r\n");
            //默认主键作为排序字段，因此此处需要获取主键列表
            String perimarkKeys = "";
            for(int i=0;i<count;i++) {
                String column_name = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_comment_all = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
                String column_type = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                JSONObject column_comment_json = (JSONObject) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
                String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
                boolean isPrimaryKey = false;
                for (String primaryKeyColumnName : primaryKeyColumn) {
                    if (primaryKeyColumnName.equals(column_name)) {
                        isPrimaryKey = true;
                        if(StringUtils.isEmpty(perimarkKeys)){
                            perimarkKeys+=column_name;
                        }else{
                            perimarkKeys+=(","+column_name);
                        }
                        break;
                    }
                }
            }

            sb.append("        <![CDATA[ select * from ( select interTable.*,ROW_NUMBER() OVER ( ORDER BY "+perimarkKeys+" ) as ROWNUM from ( ]]>").append("\r\n");
            sb.append("        </if>").append("\r\n").append("\r\n");

            sb.append("        select").append("\r\n");
            sb.append("        <include refid=\"Base_Column_List\" />").append("\r\n");
            sb.append("        from " + tableName + "").append("\r\n");
            sb.append("        where 1=1").append("\r\n");
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_comment_all = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                JSONObject column_comment_json = (JSONObject)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
                String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
                try{
                    type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
                }catch (Exception e){
                    //e.printStackTrace();
                }
                //如果默认标志为true，并且字段类型为input，并且类型是VARCHAR型的，则生成like型的字段。
                String MAPPERJDBCTYPE = TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits);
                if(DEFAULT_QUERY_FORPAGE_like.equals("true")&&type.equals(GeneratorJSONProperties.FIELD_type_input)&&MAPPERJDBCTYPE.indexOf("CHAR")>0){
                    sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                    sb.append("            and "+column_name+" like CONCAT(#{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"},'%')").append("\r\n");
                    sb.append("        </if>").append("\r\n");
                    continue;
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_date)||type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                    //如果是date或者datetime类型，则先写默认条件
                    sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                    sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                    sb.append("        </if>").append("\r\n");
                    //判断是否有queryrange标志
                    {
                        try{
                            JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                            Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                            if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                                //如果有queryrange条件，则增加_start和_end查询，闭区间
                                sb.append("        <if test=\""+column_name_tf+"_start != null and "+column_name_tf+"_start != '' \">").append("\r\n");
                                sb.append("            and "+column_name+" &gt;= #{"+column_name_tf+"_start,jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                                sb.append("        </if>").append("\r\n");
                                sb.append("        <if test=\""+column_name_tf+"_end != null and "+column_name_tf+"_end != '' \">").append("\r\n");
                                sb.append("            and "+column_name+" &lt;= #{"+column_name_tf+"_end,jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                                sb.append("        </if>").append("\r\n");
                            }
                        }catch (Exception e){
                            //e.printStackTrace();
                            System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                        }
                    }
                    //判断是否有queryrange标志结束
                    continue;
                }
                sb.append("        <if test=\""+column_name_tf+" != null and "+column_name_tf+" != '' \">").append("\r\n");
                sb.append("            and "+column_name+" = #{"+column_name_tf+",jdbcType="+MAPPERJDBCTYPE+"}").append("\r\n");
                sb.append("        </if>").append("\r\n");
            }
            sb.append("").append("\r\n").append("\r\n");
            //MYSQL从0开始,ORACLE从1开始,DB2从1开始，而dao层统一从0开始，因此此处为limitStart<interTable.ROWNUM<=limitStart+limitSize
            sb.append("        <if test=\"countFlag != null and countFlag == 'N'.toString() \">").append("\r\n");
            sb.append("        ) interTable )where ROWNUM &gt;#{limitStart,jdbcType=DECIMAL} and ROWNUM &lt;= #{limitStart,jdbcType=DECIMAL}+#{limitSize,jdbcType=DECIMAL} ").append("\r\n");
            sb.append("        </if>").append("\r\n");
            sb.append("        <if test=\"countFlag != null and countFlag == 'Y'.toString() \">").append("\r\n");
            sb.append("        ) TMP_COUNT_TABLE").append("\r\n");
            sb.append("        </if>").append("\r\n");

            sb.append("    </select>").append("\r\n");
        }else{
            throw new RuntimeException("不支持的数据库");
        }

        //************************************************************************************************************
        //insertBatch    insertBatch批量更新,如果是mysql数据库，需要在jdbcUrl中设置allowMultiQueries=true参数才可以使用
        //************************************************************************************************************
        sb.append("    <!-- 如果是mysql数据库，需要在jdbcUrl中设置allowMultiQueries=true参数才可以使用 -->").append("\r\n");
        sb.append("    <!-- 返回值为第一条更新语句的执行结果，并非所有批量更新的语句总和 -->").append("\r\n");
        sb.append("    <!--").append("\r\n");
        sb.append("        ORACLE的写法").append("\r\n");
        sb.append("        <insert id=\"insertBatch\"  parameterType=\"java.util.List\">").append("\r\n");
        sb.append("            <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\"end;\" separator=\";\">").append("\r\n");
        sb.append("                insert into test (a,b,c) values (#{item.a},#{item.b},#{item.c})").append("\r\n");
        sb.append("            </foreach>").append("\r\n");
        sb.append("        </insert>").append("\r\n");
        sb.append("        MYSQL的写法").append("\r\n");
        sb.append("        <insert id=\"insertBatch\"  parameterType=\"java.util.List\">").append("\r\n");
        sb.append("            insert into test (a,b,c) values ").append("\r\n");
        sb.append("            <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\",\">").append("\r\n");
        sb.append("                #{item.a},#{item.b},#{item.c}").append("\r\n");
        sb.append("            </foreach>").append("\r\n");
        sb.append("        </insert>").append("\r\n");
        sb.append("        ").append("\r\n");
        sb.append("        ").append("\r\n");
        sb.append("    -->").append("\r\n");


        sb.append("    <insert id=\"insertBatch\"  parameterType=\"java.util.List\">").append("\r\n");
        if(DBTYPE.equals("MYSQL")||DBTYPE.equals("SQLITE")){
            sb.append("        insert into "+tableName+" (");
            //增加普通字段
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                boolean isPrimaryKey = false;
                for(String primaryKeyColumnName :primaryKeyColumn){
                    if(primaryKeyColumnName.equals(column_name)){
                        isPrimaryKey=true;
                        break;
                    }
                }
                if(i!=0){
                    sb.append(",");
                }
                sb.append(column_name);
            }
            sb.append(") values ").append("\r\n");
            sb.append("        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\"),(\">").append("\r\n");
            sb.append("            ");
            //增加普通字段
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                boolean isPrimaryKey = false;
                for(String primaryKeyColumnName :primaryKeyColumn){
                    if(primaryKeyColumnName.equals(column_name)){
                        isPrimaryKey=true;
                        break;
                    }
                }
                if(i!=0){
                    sb.append(",");
                }
                sb.append("#{item."+column_name_tf+"}");
            }
            sb.append("\r\n");
            sb.append("        </foreach>").append("\r\n");
        } else if(DBTYPE.equals("ORACLE")){
            sb.append("        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\"end;\" separator=\";\">").append("\r\n");
            sb.append("            insert into "+tableName+" ( ");
            //增加普通字段
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                boolean isPrimaryKey = false;
                for(String primaryKeyColumnName :primaryKeyColumn){
                    if(primaryKeyColumnName.equals(column_name)){
                        isPrimaryKey=true;
                        break;
                    }
                }
                if(i!=0){
                    sb.append(",");
                }
                sb.append(column_name);
            }
            sb.append(" ) values ( ");
            //增加普通字段
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                boolean isPrimaryKey = false;
                for(String primaryKeyColumnName :primaryKeyColumn){
                    if(primaryKeyColumnName.equals(column_name)){
                        isPrimaryKey=true;
                        break;
                    }
                }
                if(i!=0){
                    sb.append(",");
                }
                sb.append("#{item."+column_name_tf+"}");
            }
            sb.append(")\r\n");
            sb.append("        </foreach>").append("\r\n");
        } else if(DBTYPE.equals("DB2")){
            sb.append("        insert into "+tableName+" (");
            //增加普通字段
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                boolean isPrimaryKey = false;
                for(String primaryKeyColumnName :primaryKeyColumn){
                    if(primaryKeyColumnName.equals(column_name)){
                        isPrimaryKey=true;
                        break;
                    }
                }
                if(i!=0){
                    sb.append(",");
                }
                sb.append(column_name);
            }
            sb.append(") values ").append("\r\n");
            sb.append("        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\"),(\">").append("\r\n");
            sb.append("            ");
            //增加普通字段
            for(int i=0;i<count;i++){
                String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
                String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
                String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
                boolean isPrimaryKey = false;
                for(String primaryKeyColumnName :primaryKeyColumn){
                    if(primaryKeyColumnName.equals(column_name)){
                        isPrimaryKey=true;
                        break;
                    }
                }
                if(i!=0){
                    sb.append(",");
                }
                sb.append("#{item."+column_name_tf+"}");
            }
            sb.append("\r\n");
            sb.append("        </foreach>").append("\r\n");
        } else {
            throw new RuntimeException("不支持的数据库");
        }
        sb.append("    </insert>").append("\r\n");


        //************************************************************************************************************
        //updateBatch    updateBatch批量更新,如果是mysql数据库，需要在jdbcUrl中设置allowMultiQueries=true参数才可以使用
        //************************************************************************************************************
        sb.append("    <!-- 如果是mysql数据库，需要在jdbcUrl中设置allowMultiQueries=true参数才可以使用 -->").append("\r\n");
        sb.append("    <!-- 返回值为第一条更新语句的执行结果，并非所有批量更新的语句总和 -->").append("\r\n");
        sb.append("    <!--").append("\r\n");
        sb.append("         ORACLE的写法").append("\r\n");
        sb.append("         <update id=\"updateBatch\"  parameterType=\"java.util.List\">").append("\r\n");
        sb.append("             <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\"end;\" separator=\";\">").append("\r\n");
        sb.append("                 update test").append("\r\n");
        sb.append("                 <set>").append("\r\n");
        sb.append("                     test=${item.test}+1").append("\r\n");
        sb.append("                 </set>").append("\r\n");
        sb.append("                 where id = ${item.id}").append("\r\n");
        sb.append("             </foreach>").append("\r\n");
        sb.append("         </update>").append("\r\n");
        sb.append("         MYSQL的写法").append("\r\n");
        sb.append("         <update id=\"updateBatch\"  parameterType=\"java.util.List\">").append("\r\n");
        sb.append("             <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">").append("\r\n");
        sb.append("                 update test").append("\r\n");
        sb.append("                 <set>").append("\r\n");
        sb.append("                     test=${item.test}+1").append("\r\n");
        sb.append("                 </set>").append("\r\n");
        sb.append("                 where id = ${item.id}").append("\r\n");
        sb.append("             </foreach>").append("\r\n");
        sb.append("         </update>").append("\r\n");
        sb.append("         ").append("\r\n");
        sb.append("         ").append("\r\n");
        sb.append("    -->").append("\r\n");


        sb.append("    <update id=\"updateBatch\" parameterType=\"java.util.List\" >").append("\r\n");
        if(DBTYPE.equals("MYSQL")||DBTYPE.equals("SQLITE")){
            sb.append("        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">").append("\r\n");
        } else if(DBTYPE.equals("ORACLE")){
            sb.append("        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\"end;\" separator=\";\">").append("\r\n");
        } else if(DBTYPE.equals("DB2")){
            sb.append("        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">").append("\r\n");
        } else {
            throw new RuntimeException("不支持的数据库");
        }
        sb.append("            update "+tableName).append("\r\n");
        sb.append("            <set>").append("\r\n");
        //非主键
        //增加普通字段
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            if(!isPrimaryKey){
                sb.append("                <if test=\"item."+column_name_tf+ "_new != null\">").append("\r\n");
                sb.append("                    "+column_name+" = #{item."+column_name_tf+"_new,jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"} ,").append("\r\n");
                sb.append("                </if>").append("\r\n");
            }
        }
        sb.append("            </set>").append("\r\n");
        sb.append("            where 1=1").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("                <if test=\"item."+column_name_tf+ " != null \">").append("\r\n");
            sb.append("                    and "+column_name+" = #{item."+column_name_tf+",jdbcType="+TypeUtils.transDBType2MapperJDBCType(column_type, column_size, decimal_digits)+"}").append("\r\n");
            sb.append("                </if>").append("\r\n");
        }
        sb.append("        </foreach>").append("\r\n");
        sb.append("    </update>").append("\r\n");




        //************************************************************************************************************
        //selectByPrimaryKey    selectByPrimaryKey根据主键查询对象
        //************************************************************************************************************
        sb.append("    <select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\">").append("\r\n");
        sb.append("        select").append("\r\n");
        sb.append("        <include refid=\"Base_Column_List\" />").append("\r\n");
        sb.append("        from "+tableName+"").append("\r\n");
        sb.append("        where 1=1").append("\r\n");
        //增加主键，以字段排序为序
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            if(isPrimaryKey){
                sb.append("        and "+column_name+" = #{"+i+"}").append("\r\n");
            }
        }
        sb.append("    </select>").append("\r\n");





        //************************************************************************************************************
        //editInfo    editInfo根据主键查询对象
        //************************************************************************************************************
        sb.append("    <update id=\"editInfo\" parameterType=\"java.util.HashMap\">").append("\r\n");
        sb.append("    </update>").append("\r\n");

        sb.append("").append("\r\n");


        sb.append("</mapper>").append("\r\n");
        pw.println(sb.toString());
        pw.close();
    }
}
