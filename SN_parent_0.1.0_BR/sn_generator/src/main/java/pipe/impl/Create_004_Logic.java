package pipe.impl;

import context.Context;
import pipe.Pipe;
import utils.FileUtils;
import utils.FormatUtils;
import utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by baolong on 2016/1/14.
 */
public class Create_004_Logic implements Pipe {
    public void exec(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_LOGIC))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_LOGIC)+"]创建不成功");
        }
        String tableName = (String)c.get(Context.CURR_TABLE);
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        String cleanentityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        //去掉Tbl的头部
        if(cleanentityClassName.startsWith("Tbl")){
            cleanentityClassName = cleanentityClassName.substring(3);
        }
        String className = cleanentityClassName+"Logic";
        String fileName = className+".java";
        File logicFile = new File((String)c.get(Context.PATH_LOGIC) + File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logicFile),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n * SN Generator \r\n */\r\n");
        sb.append("package "+(String)c.get(Context.PACKAGE_LOGIC)+";").append("\r\n").append("\r\n").append("\r\n");
        //增加import
        sb.append("import java.util.HashMap;").append("\r\n");
        sb.append("import " + (String) c.get(Context.PACKAGE_ENTITY) + "." + entityClassName + ";").append("\r\n");

        //增加类注释
        sb.append("/**").append("\r\n");
        sb.append(" * " + (String) c.get(Context.CURR_TABLE_COMMENT)).append("\r\n");
        sb.append(" * " + (String) c.get(Context.CURR_TABLE)).append("\r\n");
        sb.append(" */").append("\r\n");

        //内容
        sb.append("public interface " + className + " {").append("\r\n").append("\r\n");

        sb.append("    /*").append("\r\n");
        sb.append("     * 查询信息").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public " + entityClassName + " query" + cleanentityClassName + "(" + entityClassName + " " + FormatUtils.formatDBNameToVarName(tableName) + ") throws Exception;").append("\r\n").append("\r\n");

        sb.append("    /*").append("\r\n");
        sb.append("     * 新增信息").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public Integer insert" + cleanentityClassName + "(" + entityClassName + " " + FormatUtils.formatDBNameToVarName(tableName) + ") throws Exception;").append("\r\n").append("\r\n");

        sb.append("    /*").append("\r\n");
        sb.append("     * 修改信息").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public Integer update" + cleanentityClassName + "(" + entityClassName + " " + FormatUtils.formatDBNameToVarName(tableName) + ") throws Exception;").append("\r\n").append("\r\n");

        sb.append("    /*").append("\r\n");
        sb.append("     * 删除信息").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public Integer delete" + cleanentityClassName + "(" + entityClassName + " " + FormatUtils.formatDBNameToVarName(tableName) + ") throws Exception;").append("\r\n").append("\r\n");

        sb.append("    /*").append("\r\n");
        sb.append("     * 查询信息queryPage").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public HashMap queryPage" + cleanentityClassName + "(HashMap paramMap) throws Exception;").append("\r\n").append("\r\n");

        sb.append("}").append("\r\n");

        pw.println(sb.toString());
        pw.close();
    }
}
