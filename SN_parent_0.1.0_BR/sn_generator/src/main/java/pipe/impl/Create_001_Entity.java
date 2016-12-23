package pipe.impl;

import context.Context;
import pipe.Pipe;
import utils.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by baolong on 2016/1/8.
 */
public class Create_001_Entity implements Pipe {
    public void exec(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String)c.get(Context.PATH_ENTITY))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_ENTITY)+"]创建不成功");
        }
        String tableName = (String)c.get(Context.CURR_TABLE);
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        String className = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        String fileName = className+".java";
        File entityFile = new File((String)c.get(Context.PATH_ENTITY) + File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(entityFile),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n * SN Generator \r\n */\r\n");
        sb.append("package "+(String)c.get(Context.PACKAGE_ENTITY)+";").append("\r\n").append("\r\n").append("\r\n");

        //增加类注释
        sb.append("/**").append("\r\n");
        sb.append(" * "+(String)c.get(Context.CURR_TABLE_COMMENT)).append("\r\n");
        sb.append(" * "+(String)c.get(Context.CURR_TABLE)).append("\r\n");
        sb.append(" */").append("\r\n");

        //增加标签
        List<String> annotation_entity_list = (List)c.get(Context.ANNOTATION_ENTITY);
        if(annotation_entity_list!=null){
            for (String s : annotation_entity_list){
                sb.append("@"+s).append("\r\n");
            }
        }
        //增加类名
        sb.append("public class " + className + " ");
        String extendsClass = (String)c.get(Context.EXTENDS_ENTITY);
        if(!StringUtils.isEmpty(extendsClass)){
            sb.append("extends " + extendsClass+ " ");
        }

        //增加接口
        List<String> implements_entity_list = (List)c.get(Context.IMPLEMENTS_ENTITY);
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

        int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);
        //属性
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);

            sb.append("    /**").append("\r\n");
            sb.append("     * "+column_comment).append("\r\n");
            sb.append("     * "+tableName+"."+column_name).append("\r\n");
            sb.append("     */").append("\r\n");
            sb.append("    private "+ TypeUtils.transDBType2JavaType(column_type,column_size,decimal_digits)+" "+column_name_tf+";").append("\r\n").append("\r\n");

        }
        sb.append("\r\n");

        //构造器
        sb.append("    public "+className+"(){super();}").append("\r\n");
        sb.append("    public " + className + "(");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append(TypeUtils.transDBType2JavaType(column_type,column_size,decimal_digits)+" "+column_name_tf+"");
            if(i!=count-1){
                sb.append(",");
            }
        }
        sb.append(") {").append("\r\n");
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            sb.append("        this."+column_name_tf+" = "+column_name_tf+";").append("\r\n");
        }

        sb.append("    }").append("\r\n");


        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);

            sb.append("    /**").append("\r\n");
            sb.append("     * "+column_comment).append("\r\n");
            sb.append("     * "+tableName+"."+column_name).append("\r\n");
            sb.append("     */").append("\r\n");
            sb.append("    public "+ TypeUtils.transDBType2JavaType(column_type,column_size,decimal_digits)+" get"+FormatUtils.firstBig(column_name_tf)+"(){").append("\r\n");
            sb.append("        return "+column_name_tf+";").append("\r\n");
            sb.append("    }").append("\r\n").append("\r\n");

            sb.append("    /**").append("\r\n");
            sb.append("     * "+column_comment).append("\r\n");
            sb.append("     * "+tableName+"."+column_name).append("\r\n");
            sb.append("     */").append("\r\n");
            sb.append("    public void set"+FormatUtils.firstBig(column_name_tf)+"("+TypeUtils.transDBType2JavaType(column_type,column_size,decimal_digits)+" "+column_name_tf+"){").append("\r\n");
            sb.append("        this."+column_name_tf+"="+column_name_tf+";").append("\r\n");
            sb.append("    }").append("\r\n").append("\r\n");

        }
        //增加自定义的列
        sb.append("    /**").append("\r\n");
        sb.append("     * 自定义列，分页查询用").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    private Integer limitStart;").append("\r\n");
        sb.append("    public Integer getLimitStart(){").append("\r\n");
        sb.append("        return limitStart;").append("\r\n");
        sb.append("    }").append("\r\n");
        sb.append("    public void setLimitStart(Integer limitStart){").append("\r\n");
        sb.append("        this.limitStart = limitStart;").append("\r\n");
        sb.append("    }").append("\r\n");
        sb.append("    private Integer limitSize;").append("\r\n");
        sb.append("    public Integer getLimitSize(){").append("\r\n");
        sb.append("        return limitSize;").append("\r\n");
        sb.append("    }").append("\r\n");
        sb.append("    public void setLimitSize(Integer limitSize){").append("\r\n");
        sb.append("        this.limitSize = limitSize;").append("\r\n");
        sb.append("    }").append("\r\n");
        sb.append("}").append("\r\n");
        pw.println(sb.toString());
        pw.close();
    }
}
