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
public class Create_004_LogicImpl implements Pipe {
    public void exec(Context c) throws Exception {
        String pathLogic = (String) c.get(Context.PATH_LOGIC);
        String pathLogicImpl = (String)c.get(Context.PATH_LOGIC) + File.separator + "impl";
        //创建文件夹
        if(!FileUtils.mkdirs(pathLogic)){
            throw new Exception("文件夹["+pathLogic+"]创建不成功");
        }
        if(!FileUtils.mkdirs(pathLogicImpl)){
            throw new Exception("文件夹[" + pathLogicImpl + "]创建不成功");
        }
        String tableName = (String)c.get(Context.CURR_TABLE);
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        String cleanentityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        //去掉Tbl的头部
        if(cleanentityClassName.startsWith("Tbl")){
            cleanentityClassName = cleanentityClassName.substring(3);
        }
        String className = cleanentityClassName+"LogicImpl";
        String fileName = className+".java";
        File logicImplFile = new File(pathLogicImpl + File.separator + fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logicImplFile),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n * Gome SN Generator \r\n */\r\n");
        sb.append("package "+(String)c.get(Context.PACKAGE_LOGIC)+".impl;").append("\r\n").append("\r\n").append("\r\n");
        //增加import
        sb.append("import cn.com.sn.frame.logger.HLogger;").append("\r\n");
        sb.append("import cn.com.sn.frame.logger.LoggerManager;").append("\r\n");

        sb.append("import cn.kanmars.sn.util.PageQueryUtil;").append("\r\n");
        sb.append("import net.sf.json.JSONObject;").append("\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        sb.append("import org.springframework.stereotype.Service;").append("\r\n");
        sb.append("import org.springframework.transaction.annotation.Propagation;").append("\r\n");
        sb.append("import org.springframework.transaction.annotation.Transactional;").append("\r\n");
        sb.append("import java.util.HashMap;").append("\r\n");
        sb.append("import " + (String) c.get(Context.PACKAGE_ENTITY) + "." + entityClassName + ";").append("\r\n");
        sb.append("import " + (String) c.get(Context.PACKAGE_DAO) + "." + entityClassName + "Mapper;").append("\r\n");
        //增加类注释
        sb.append("/**").append("\r\n");
        sb.append(" * "+(String)c.get(Context.CURR_TABLE_COMMENT)).append("\r\n");
        sb.append(" * "+(String)c.get(Context.CURR_TABLE)).append("\r\n");
        sb.append(" */").append("\r\n");

        //增加注解
        List<String> annotation_entity_list = (List)c.get(Context.ANNOTATION_LOGIC);
        if(annotation_entity_list!=null){
            for (String s : annotation_entity_list){
                sb.append("@"+s).append("\r\n");
            }
        }

        //增加类名
        sb.append("public class " + className + " ");
        String extendsClass = (String)c.get(Context.EXTENDS_LOGIC);
        if(!StringUtils.isEmpty(extendsClass)){
            sb.append("extends " + extendsClass+ " ");
        }

        //增加接口
        sb.append("implements " + (String) c.get(Context.PACKAGE_LOGIC) + "." + cleanentityClassName + "Logic");//cn.kanmars.sn.admin.logic.UserInfoLogic
        List<String> implements_entity_list = (List)c.get(Context.IMPLEMENTS_LOGIC);
        if(implements_entity_list!=null){
            for (int i=0;i<implements_entity_list.size();i++){
                sb.append("," + implements_entity_list.get(i));
            }
        }
        sb.append("{").append("\r\n").append("\r\n");

        sb.append("    protected HLogger logger = LoggerManager.getLoger(\""+ className + "\");").append("\r\n").append("\r\n");

        sb.append("    @Autowired").append("\r\n");
        sb.append("    private " + entityClassName + "Mapper " + FormatUtils.formatDBNameToVarName(tableName) + "Mapper;").append("\r\n").append("\r\n");

        sb.append("    /*").append("\r\n");
        sb.append("     * 查询信息").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public " + entityClassName + " query" + cleanentityClassName + "(" + entityClassName + " " + FormatUtils.formatDBNameToVarName(tableName) + ") throws Exception {").append("\r\n");
        sb.append("        logger.debug(\"query" + cleanentityClassName + ":start\");").append("\r\n");
        sb.append("        logger.debug(\"传入参数:" + FormatUtils.formatDBNameToVarName(tableName) + ":\" + (" + FormatUtils.formatDBNameToVarName(tableName) + " != null ? JSONObject.fromObject(" + FormatUtils.formatDBNameToVarName(tableName) + ").toString() : \"null\"));").append("\r\n");
        sb.append("        " + entityClassName + " result = " + FormatUtils.formatDBNameToVarName(tableName) + "Mapper.select(" + FormatUtils.formatDBNameToVarName(tableName) + ");").append("\r\n");
        sb.append("        logger.debug(\"查询结果:" + FormatUtils.formatDBNameToVarName(tableName) + ":\" + (result != null ? JSONObject.fromObject(result).toString() : \"null\"));").append("\r\n");
        sb.append("        logger.debug(\"query" + cleanentityClassName + ":end\");").append("\r\n");
        sb.append("        return result;").append("\r\n");
        sb.append("    }").append("\r\n");


        sb.append("    /*").append("\r\n");
        sb.append("     * 新增信息").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    @Transactional(value=\"sn-txManager\",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)").append("\r\n");
        sb.append("    public Integer insert" + cleanentityClassName + "(" + entityClassName + " " + FormatUtils.formatDBNameToVarName(tableName) + ") throws Exception {").append("\r\n");
        sb.append("        logger.debug(\"insert" + cleanentityClassName + ":start\");").append("\r\n");
        sb.append("        logger.debug(\"传入参数:" + FormatUtils.formatDBNameToVarName(tableName) + ":\" + (" + FormatUtils.formatDBNameToVarName(tableName) + " != null ? JSONObject.fromObject(" + FormatUtils.formatDBNameToVarName(tableName) + ").toString() : \"null\"));").append("\r\n");
        sb.append("        int insertNum = " + FormatUtils.formatDBNameToVarName(tableName) + "Mapper.insert("+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n");
        sb.append("        logger.debug(\"操作结果:insertNum[\"+insertNum+\"]\");").append("\r\n");
        sb.append("        logger.debug(\"insert" + cleanentityClassName + ":end\");").append("\r\n");
        sb.append("        return insertNum;").append("\r\n");
        sb.append("    }").append("\r\n");

        sb.append("    /*").append("\r\n");
        sb.append("     * 修改信息").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    @Transactional(value=\"sn-txManager\",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)").append("\r\n");
        sb.append("    public Integer update" + cleanentityClassName + "(" + entityClassName + " " + FormatUtils.formatDBNameToVarName(tableName) + ") throws Exception {").append("\r\n");
        sb.append("        logger.debug(\"update" + cleanentityClassName + ":start\");").append("\r\n");
        sb.append("        logger.debug(\"传入参数:" + FormatUtils.formatDBNameToVarName(tableName) + ":\" + (" + FormatUtils.formatDBNameToVarName(tableName) + " != null ? JSONObject.fromObject(" + FormatUtils.formatDBNameToVarName(tableName) + ").toString() : \"null\"));").append("\r\n");
        sb.append("        int updateNum = " + FormatUtils.formatDBNameToVarName(tableName) + "Mapper.updateByPrimaryKey("+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n");
        sb.append("        logger.debug(\"操作结果:updateNum[\"+updateNum+\"]\");").append("\r\n");
        sb.append("        logger.debug(\"update" + cleanentityClassName + ":end\");").append("\r\n");
        sb.append("        return updateNum;").append("\r\n");
        sb.append("    }").append("\r\n");



        sb.append("    /*").append("\r\n");
        sb.append("     * 删除信息").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    @Transactional(value=\"sn-txManager\",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)").append("\r\n");
        sb.append("    public Integer delete" + cleanentityClassName + "(" + entityClassName + " " + FormatUtils.formatDBNameToVarName(tableName) + ") throws Exception {").append("\r\n");
        sb.append("        logger.debug(\"delete" + cleanentityClassName + ":start\");").append("\r\n");
        sb.append("        logger.debug(\"传入参数:" + FormatUtils.formatDBNameToVarName(tableName) + ":\" + (" + FormatUtils.formatDBNameToVarName(tableName) + " != null ? JSONObject.fromObject(" + FormatUtils.formatDBNameToVarName(tableName) + ").toString() : \"null\"));").append("\r\n");
        sb.append("        int deleteNum = " + FormatUtils.formatDBNameToVarName(tableName) + "Mapper.delete("+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n");
        sb.append("        logger.debug(\"操作结果:deleteNum[\"+deleteNum+\"]\");").append("\r\n");
        sb.append("        logger.debug(\"delete" + cleanentityClassName + ":end\");").append("\r\n");
        sb.append("        return deleteNum;").append("\r\n");
        sb.append("    }").append("\r\n");

        sb.append("    /*").append("\r\n");
        sb.append("     * 查询信息queryPage").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    public HashMap queryPage" + cleanentityClassName + "(HashMap paramMap) throws Exception {").append("\r\n");
        sb.append("        logger.debug(\"queryPage"+cleanentityClassName+ ":start\");").append("\r\n");
        sb.append("        logger.debug(\"传入参数:paramMap:\" + paramMap);").append("\r\n");
        sb.append("        String startIndex_s = (String)paramMap.get(\"startIndex\");").append("\r\n");
        sb.append("        String pageSize_s =  (String)paramMap.get(\"pageSize\");").append("\r\n");
        sb.append("        int startIndex = 1;").append("\r\n");
        sb.append("        int pageSize = 10;").append("\r\n");
        sb.append("        try{").append("\r\n");
        sb.append("            startIndex = Integer.parseInt(startIndex_s);").append("\r\n");
        sb.append("            pageSize = Integer.parseInt(pageSize_s);").append("\r\n");
        sb.append("        }catch(Exception e){").append("\r\n");
        sb.append("            logger.error(\"startIndex和pageSize解析异常\",e);").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        HashMap result = PageQueryUtil.pageQuery(" + FormatUtils.formatDBNameToVarName(tableName) + "Mapper, startIndex, pageSize, paramMap);").append("\r\n");
        sb.append("        logger.debug(\"查询结果:result:\" + result);").append("\r\n");
        sb.append("        logger.debug(\"queryPage"+cleanentityClassName+ ":end\");").append("\r\n");
        sb.append("        return result;").append("\r\n");
        sb.append("    }").append("\r\n");

        sb.append("}").append("\r\n");

        pw.println(sb.toString());
        pw.close();
    }
}
