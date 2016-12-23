package pipe.impl;

import context.Context;
import context.GeneratorJSONProperties;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pipe.Pipe;
import utils.FileUtils;
import utils.FormatUtils;
import utils.StringUtils;
import utils.TypeUtils;

import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by baolong on 2016/1/14.
 */
public class Create_005_Controller implements Pipe {




    public void exec(Context c) throws Exception {
//创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_CONTROLLER))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_CONTROLLER)+"]创建不成功");
        }
        String tableName = (String)c.get(Context.CURR_TABLE);
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        String cleanentityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));
        //去掉Tbl的头部
        if(cleanentityClassName.startsWith("Tbl")){
            cleanentityClassName = cleanentityClassName.substring(3);
        }
        String className = cleanentityClassName+"Controller";
        String controllerPath = FormatUtils.firstSmall(cleanentityClassName);
        String logicClassName = cleanentityClassName + "Logic";
        String logicVarName = FormatUtils.firstSmall(cleanentityClassName + "Logic");
        String fileName = className+".java";
        File logicFile = new File((String)c.get(Context.PATH_CONTROLLER) + File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logicFile),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n * SN Generator \r\n */\r\n");
        sb.append("package "+(String)c.get(Context.PACKAGE_CONTROLLER)+";").append("\r\n").append("\r\n").append("\r\n");
        //增加import
        sb.append("import cn.com.xcommon.frame.logger.HLogger;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.logger.LoggerManager;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.util.DateFormatUtils;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.util.MapObjTransUtils;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.util.MoneyFormatUtils;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.util.StringUtils;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.util.DateUtils;").append("\r\n");
        sb.append("import cn.kanmars.sn.util.SysSequenceUtils;").append("\r\n");
        sb.append("import cn.kanmars.sn.util.SysDicUtils;").append("\r\n");
        sb.append("import cn.kanmars.sn.base.AdvancedAjaxBaseController;").append("\r\n");
        sb.append("import " + (String)c.get(Context.PACKAGE_LOGIC) + "." + logicClassName + ";").append("\r\n");
        sb.append("import " + (String)c.get(Context.PACKAGE_ENTITY) + "." + entityClassName + ";").append("\r\n");
        sb.append("import cn.kanmars.sn.util.PageQueryUtil;").append("\r\n");
        sb.append("import net.sf.json.JSONObject;").append("\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        sb.append("import org.springframework.stereotype.Controller;").append("\r\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("import javax.servlet.http.HttpServletRequest;").append("\r\n");
        sb.append("import javax.servlet.http.HttpServletResponse;").append("\r\n");
        sb.append("import java.io.UnsupportedEncodingException;").append("\r\n");
        sb.append("import java.net.URLDecoder;").append("\r\n");
        sb.append("import java.util.Date;").append("\r\n");
        sb.append("import java.util.HashMap;").append("\r\n");
        sb.append("import java.util.List;").append("\r\n");
        sb.append("import java.util.Map;").append("\r\n");
        sb.append("import java.math.BigDecimal;").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("").append("\r\n");

        //增加类注释
        sb.append("/**").append("\r\n");
        sb.append(" * " + cleanentityClassName+"Controller").append("\r\n");
        sb.append(" * " + (String) c.get(Context.CURR_TABLE)).append("\r\n");
        sb.append(" * " + (String) c.get(Context.CURR_TABLE_COMMENT)).append("\r\n");
        sb.append(" * 方法如下").append("\r\n");
        sb.append(" * queryObject 根据id_key查询一个对象，查询结果作为JSONArray放入结果中，用于查看详情、修改查询,联合主键用逗号分割").append("\r\n");
        sb.append(" * insert 插入一个数据").append("\r\n");
        sb.append(" * edit 修改一个数据").append("\r\n");
        sb.append(" * del 根据id_key删除一个对象，多个id时用分号分割,联合主键用逗号分割").append("\r\n");
        sb.append(" * search 根据查询的条件，分页查询或者响应一个下载的Excel。").append("\r\n");
        sb.append(" *     如果传入queryType=download，则必须传入exportParam导出参数，fileName文件名").append("\r\n");
        sb.append(" *     如果传入queryType=query，则必须传入startIndex开始页数，和pageSize每页页数").append("\r\n");
        sb.append(" * beferLogic 针对insert,edit,search等含多个入参的操作使用，在logic前使用，用于把页面上的参数转化为数据库格式").append("\r\n");
        sb.append(" * afterLogic 针对queryObject,search等含有多个出参的操作使用，在logic后使用，用于把数据库输出转化为页面格式").append("\r\n");
        sb.append(" * beforeExport 针对search进行导出预处理，设置开始页数为1，设置最大数量为Integer.MAXVALUE").append("\r\n");
        sb.append(" * export 针对search进行导出").append("\r\n");
        sb.append(" * ").append("\r\n");
        sb.append(" */").append("\r\n");
        sb.append("@Controller").append("\r\n");
        sb.append("@RequestMapping(\"/" + controllerPath + "\")").append("\r\n");
        sb.append("public class "+cleanentityClassName+"Controller ");
        String extendsClass = (String)c.get(Context.EXTENDS_CONTROLLER);
        if(!StringUtils.isEmpty(extendsClass)){
            sb.append("extends " + extendsClass+ " ");
        }


        sb.append(" {").append("\r\n").append("\r\n");
        sb.append("    protected HLogger logger = LoggerManager.getLoger(\"" + cleanentityClassName + "Controller\");").append("\r\n").append("\r\n");

        sb.append("    @Autowired").append("\r\n");
        sb.append("    private " + logicClassName + " " + logicVarName+";").append("\r\n").append("\r\n");

        //************************************************************************************************************
        //init方法
        //************************************************************************************************************
        sb.append("    @RequestMapping(\"/" + controllerPath + "View.dhtml\")").append("\r\n");
        sb.append("    public String init() {").append("\r\n");
        sb.append("        return \"" + controllerPath.toLowerCase() + "/" + controllerPath.toLowerCase() + "\";").append("\r\n");
        sb.append("    }").append("\r\n").append("\r\n");

        //************************************************************************************************************
        //queryObject方法
        //************************************************************************************************************
        sb.append("    @RequestMapping(\"/queryObject.dhtml\")").append("\r\n");
        sb.append("    public void queryObject(HttpServletRequest request, HttpServletResponse response) {").append("\r\n");
        sb.append("        logger.info(\"queryObject:start\");").append("\r\n");
        sb.append("        String requestJson = request.getParameter(\"jsonStr\");").append("\r\n");
        sb.append("        JSONObject jsonObject = JSONObject.fromObject(requestJson);").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("        logger.info(\"传入参数:jsonObject:\" + jsonObject);").append("\r\n");
        sb.append("        "+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+" = new "+entityClassName+"();").append("\r\n");
        //对查询对象生成主键
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
            if(isPrimaryKey&&primaryKeyColumn.size()==1){
                //单个主键
                sb.append("        "+FormatUtils.firstSmall(entityClassName)+".set"+FormatUtils.firstBig(column_name_tf)+"(");
                sb.append(TypeUtils.transStringVar2JavaType(column_type, column_size, decimal_digits, "jsonObject.get(\"id_key\").toString()"));
                sb.append(");").append("// 联合主键用逗号分割").append("\r\n");
            }
            if(isPrimaryKey&&primaryKeyColumn.size()>1){
                //联合主键
                sb.append("        "+FormatUtils.firstSmall(entityClassName)+".set"+FormatUtils.firstBig(column_name_tf)+"(");
                sb.append(TypeUtils.transStringVar2JavaType(column_type, column_size, decimal_digits,"jsonObject.get(\"id_key\").toString().split(\",\")["+i+"]"));
                sb.append(");").append("// 联合主键用逗号分割").append("\r\n");
            }
        }
        sb.append("").append("\r\n");

        sb.append("        try {").append("\r\n");
        sb.append("            "+FormatUtils.formatDBNameToVarName(tableName)+" = "+logicVarName+".query"+cleanentityClassName+"("+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n");
        sb.append("            logger.info(\"查询结果:" + FormatUtils.formatDBNameToVarName(tableName) + ":\" + (" + FormatUtils.formatDBNameToVarName(tableName) + " != null ? JSONObject.fromObject(" + FormatUtils.formatDBNameToVarName(tableName) + ").toString() : \"null\"));").append("\r\n");
        sb.append("            Map result = new HashMap();").append("\r\n");
        sb.append("            Map " + FormatUtils.formatDBNameToVarName(tableName) + "_map = afterLogic(request, JSONObject.fromObject(" + FormatUtils.formatDBNameToVarName(tableName) + "));").append("\r\n");
        sb.append("            result.put(\""+FormatUtils.formatDBNameToVarName(tableName)+"\", "+FormatUtils.formatDBNameToVarName(tableName)+"_map);").append("\r\n");
        sb.append("            result.put(\"id_key\",jsonObject.get(\"id_key\").toString());").append("\r\n");
        sb.append("            ajaxJsonMessage(request, response, result, QUERY);").append("\r\n");
        sb.append("        } catch (Exception e) {").append("\r\n");
        sb.append("            logger.error(\"处理失败\", e);").append("\r\n");
        sb.append("            ajaxJsonMessage(request, response, ERROR, \"操作异常,请联系管理员！\", QUERY);").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        logger.info(\"queryObject:end\");").append("\r\n");
        sb.append("    }").append("\r\n").append("\r\n");


        //************************************************************************************************************
        //insert方法
        //************************************************************************************************************
        sb.append("    @RequestMapping(\"/insert.dhtml\")").append("\r\n");
        sb.append("    public void insert(HttpServletRequest request, HttpServletResponse response) {").append("\r\n");
        sb.append("        logger.info(\"insert:start\");").append("\r\n");
        sb.append("        String requestJson = request.getParameter(\"jsonStr\");").append("\r\n");
        sb.append("        try {").append("\r\n");
        sb.append("            JSONObject jsonObject = JSONObject.fromObject(requestJson);").append("\r\n");
        sb.append("            // logic前处理，格式转化：页面->数据库层").append("\r\n");
        sb.append("            HashMap<String, Object> paramMap = new HashMap<String, Object>();").append("\r\n");
        sb.append("            paramMap = beferLogic(request, jsonObject);").append("\r\n");
        sb.append("            "+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+" = new "+entityClassName+"();").append("\r\n");
        sb.append("            try {").append("\r\n");
        sb.append("                MapObjTransUtils.mapToObj("+FormatUtils.formatDBNameToVarName(tableName)+", paramMap);").append("\r\n");
        sb.append("            } catch (Exception e) {").append("\r\n");
        sb.append("                logger.error(\"对象转化异常\", e);").append("\r\n");
        sb.append("            }").append("\r\n");
        //对autoinsert的的时间进行自动插入 开始
        for(int i=0;i<count;i++) {
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            String column_comment_all = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
            JSONObject column_comment_json = (JSONObject) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
            try {
                type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            String value_str = null;
            if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                value_str = "DateUtils.getCurrDate()";
            }else if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                value_str = "DateUtils.getCurrDateTime()";
            }else{
                continue;
            }
            String autoinsert = GeneratorJSONProperties.DEFAULT_autoinsert;
            try{
                JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                autoinsert = attr.getString(GeneratorJSONProperties.PATH_autoinsert);
            }catch(Exception e){
                //e.printStackTrace();
            }
            if(autoinsert.equals("true")){
                sb.append("            " + FormatUtils.firstSmall(entityClassName) + ".set" + FormatUtils.firstBig(column_name_tf) + "(" + value_str + ");").append("\r\n");
            }
        }
        //对autoinsert的的时间进行自动插入 结束


        sb.append("            try {").append("\r\n");

        //尝试获取主键
        //
        for(int i=0;i<count;i++) {
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            String column_comment_all = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
            JSONObject column_comment_json = (JSONObject) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
            try {
                type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            String sequence = GeneratorJSONProperties.DEFAULT_sequence;//设置为默认的权限
            try {
                JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                sequence = attr.getString(GeneratorJSONProperties.PATH_sequence);
                //如果当前字段配置了sequence，则设置一个主键的生成对象
                //如果此处报错，则不生成主键，即，如果是主键，要么是从页面传递过来的，要么是通过配置sequence生成的
                //增加主键列表
                sb.append("                String pk"+i+" = SysSequenceUtils.generateStringId(\""+sequence+"\");").append("\r\n");

                if(isPrimaryKey&&primaryKeyColumn.size()>=1){
                    //单个主键
                    sb.append("                "+FormatUtils.firstSmall(entityClassName)+".set"+FormatUtils.firstBig(column_name_tf)+"(");
                    sb.append(TypeUtils.transStringVar2JavaType(column_type, column_size, decimal_digits, "pk"+i));
                    sb.append(");").append("// 联合主键用逗号分割").append("\r\n");
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        sb.append("            } catch (Exception e) {").append("\r\n");
        sb.append("                logger.error(\"对象转化异常\", e);").append("\r\n");
        sb.append("            }").append("\r\n");


        sb.append("            logger.info(\"传入参数:"+FormatUtils.formatDBNameToVarName(tableName)+":\" + JSONObject.fromObject(" + FormatUtils.formatDBNameToVarName(tableName) + ").toString());").append("\r\n");

        sb.append("            int count = " + logicVarName + ".insert"+cleanentityClassName+"("+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n");
        sb.append("            logger.info(\"成功数量[\" + count + \"]\");").append("\r\n");
        sb.append("            ajaxJsonMessage(request, response, SUCCESS, SUCCESS_TXT, SAVE);").append("\r\n");
        sb.append("        } catch (Exception e) {").append("\r\n");
        sb.append("            logger.error(\"处理失败\", e);").append("\r\n");
        sb.append("            ajaxJsonMessage(request, response, ERROR, \"操作异常,请联系管理员！\", SAVE);").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        logger.info(\"insert:end\");").append("\r\n");
        sb.append("    }").append("\r\n").append("\r\n");



        //************************************************************************************************************
        //edit方法
        //************************************************************************************************************
        sb.append("    @RequestMapping(\"/edit.dhtml\")").append("\r\n");
        sb.append("    public void edit(HttpServletRequest request, HttpServletResponse response) {").append("\r\n");
        sb.append("        logger.info(\"edit:start\");").append("\r\n");
        sb.append("        String requestJson = request.getParameter(\"jsonStr\");").append("\r\n");
        sb.append("        try {").append("\r\n");
        sb.append("            JSONObject jsonObject = JSONObject.fromObject(requestJson);").append("\r\n");
        sb.append("            // logic前处理，格式转化：页面->数据库层").append("\r\n");
        sb.append("            HashMap<String, Object> paramMap = new HashMap<String, Object>();").append("\r\n");
        sb.append("            paramMap = beferLogic(request, jsonObject);").append("\r\n");
        sb.append("            "+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+" = new "+entityClassName+"();").append("\r\n");
        sb.append("            try {").append("\r\n");
        sb.append("                MapObjTransUtils.mapToObj("+FormatUtils.formatDBNameToVarName(tableName)+", paramMap);").append("\r\n");
        sb.append("            } catch (Exception e) {").append("\r\n");
        sb.append("                logger.error(\"对象转化异常\", e);").append("\r\n");
        sb.append("            }").append("\r\n");
        //对autoupdate的的时间进行自动插入 开始
        for(int i=0;i<count;i++) {
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_type = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE)).get(i);
            String column_size = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE)).get(i);
            String decimal_digits = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS)).get(i);
            String column_comment_all = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
            JSONObject column_comment_json = (JSONObject) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
            boolean isPrimaryKey = false;
            for(String primaryKeyColumnName :primaryKeyColumn){
                if(primaryKeyColumnName.equals(column_name)){
                    isPrimaryKey=true;
                    break;
                }
            }
            String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
            try {
                type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            String value_str = null;
            if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                value_str = "DateUtils.getCurrDate()";
            }else if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                value_str = "DateUtils.getCurrDateTime()";
            }else{
                continue;
            }
            String autoupdate = GeneratorJSONProperties.DEFAULT_autoupdate;
            try{
                JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                autoupdate = attr.getString(GeneratorJSONProperties.PATH_autoupdate);
            }catch(Exception e){
                //e.printStackTrace();
            }
            if(autoupdate.equals("true")){
                sb.append("            "+FormatUtils.firstSmall(entityClassName)+".set"+FormatUtils.firstBig(column_name_tf)+"("+value_str+");").append("\r\n");
            }
        }
        //对autoupdate的的时间进行自动插入 结束
        sb.append("").append("\r\n");
        sb.append("            //为对象设置主键开始").append("\r\n");
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
            if(isPrimaryKey&&primaryKeyColumn.size()==1){
                //单个主键
                sb.append("            "+FormatUtils.firstSmall(entityClassName)+".set"+FormatUtils.firstBig(column_name_tf)+"(");
                sb.append(TypeUtils.transStringVar2JavaType(column_type, column_size, decimal_digits, "jsonObject.get(\"id_key\").toString()"));
                sb.append(");").append("// 联合主键用逗号分割").append("\r\n");
            }
            if(isPrimaryKey&&primaryKeyColumn.size()>1){
                //联合主键
                sb.append("            "+FormatUtils.firstSmall(entityClassName)+".set"+FormatUtils.firstBig(column_name_tf)+"(");
                sb.append(TypeUtils.transStringVar2JavaType(column_type, column_size, decimal_digits,"jsonObject.get(\"id_key\").toString().split(\",\")["+i+"]"));
                sb.append(");").append("// 联合主键用逗号分割").append("\r\n");
            }
        }
        sb.append("            //为对象设置主键结束").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("            logger.info(\"传入参数:"+FormatUtils.formatDBNameToVarName(tableName)+":\" + JSONObject.fromObject(" + FormatUtils.formatDBNameToVarName(tableName) + ").toString());").append("\r\n");
        sb.append("            int count = " + logicVarName + ".update"+cleanentityClassName+"("+FormatUtils.formatDBNameToVarName(tableName)+");").append("\r\n");
        sb.append("            if (count == 0) {").append("\r\n");
        sb.append("                logger.info(\"修改失败:修改数量为0\");").append("\r\n");
        sb.append("                ajaxJsonMessage(request, response, ERROR, EDIT_FAIL_TXT, EDIT);").append("\r\n");
        sb.append("            } else {").append("\r\n");
        sb.append("                logger.info(\"修改成功:修改数量为\" + count);").append("\r\n");
        sb.append("                ajaxJsonMessage(request, response, SUCCESS, EDIT_SUCCESS_TXT, EDIT);").append("\r\n");
        sb.append("            }").append("\r\n");
        sb.append("        } catch (Exception e) {").append("\r\n");
        sb.append("            logger.error(\"处理失败\", e);").append("\r\n");
        sb.append("            ajaxJsonMessage(request, response, ERROR, \"操作异常,请联系管理员！\", SAVE);").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        logger.info(\"edit:end\");").append("\r\n");
        sb.append("    }").append("\r\n").append("\r\n");



        //************************************************************************************************************
        //delete方法
        //************************************************************************************************************
        sb.append("    @RequestMapping(\"/del.dhtml\")").append("\r\n");
        sb.append("    public void del(HttpServletRequest request, HttpServletResponse response) {").append("\r\n");
        sb.append("        logger.info(\"del:start\");").append("\r\n");
        sb.append("        String requestJson = request.getParameter(\"jsonStr\");").append("\r\n");
        sb.append("        JSONObject jsonObject = JSONObject.fromObject(requestJson);").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("        logger.info(\"传入参数:jsonObject:\" + jsonObject);").append("\r\n");
        sb.append("        int count = 0;").append("\r\n");
        sb.append("        try {").append("\r\n");
        sb.append("            String id_key = jsonObject.get(\"id_key\").toString().trim();").append("\r\n");
        sb.append("            // 循环删除").append("\r\n");
        sb.append("            for (String s : id_key.split(\";\")) { // 多个主键用分号分割").append("\r\n");
        sb.append("                logger.info(\"尝试删除:\" + s);").append("\r\n");
        sb.append("                "+entityClassName+" "+FormatUtils.formatDBNameToVarName(tableName)+" = new "+entityClassName+"();").append("\r\n");
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
            if(isPrimaryKey&&primaryKeyColumn.size()==1){
                //单个主键
                sb.append("                "+FormatUtils.firstSmall(entityClassName)+".set"+FormatUtils.firstBig(column_name_tf)+"(");
                sb.append(TypeUtils.transStringVar2JavaType(column_type, column_size, decimal_digits, "s"));
                sb.append(");").append("// 联合主键用逗号分割").append("\r\n");
            }
            if(isPrimaryKey&&primaryKeyColumn.size()>1){
                //联合主键
                sb.append("                "+FormatUtils.firstSmall(entityClassName)+".set"+FormatUtils.firstBig(column_name_tf)+"(");
                sb.append(TypeUtils.transStringVar2JavaType(column_type, column_size, decimal_digits,"s.split(\",\")["+i+"]"));
                sb.append(");").append("// 联合主键用逗号分割").append("\r\n");
            }
        }
        sb.append("                try {").append("\r\n");
        sb.append("                    count += "+logicVarName+".delete"+cleanentityClassName+"("+FormatUtils.firstSmall(entityClassName)+");").append("\r\n");
        sb.append("                } catch(Exception e) {").append("\r\n");
        sb.append("                    logger.error(\"删除[\" + s +\"]失败，执行下一个\", e);").append("\r\n");
        sb.append("                }").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("            }").append("\r\n");
        sb.append("            if (count == 0) {").append("\r\n");
        sb.append("                logger.info(\"删除失败:删除数量为0\");").append("\r\n");
        sb.append("                ajaxJsonMessage(request, response, ERROR, DEL_FAIL_TXT, DEL);").append("\r\n");
        sb.append("            } else {").append("\r\n");
        sb.append("                logger.info(\"删除成功:删除数量为\" + count);").append("\r\n");
        sb.append("                ajaxJsonMessage(request, response, SUCCESS, DEL_SUCCESS_TXT, DEL);").append("\r\n");
        sb.append("            }").append("\r\n");
        sb.append("        } catch (Exception e) {").append("\r\n");
        sb.append("            logger.error(\"处理失败，影响数据[\" + count + \"]\", e);").append("\r\n");
        sb.append("            ajaxJsonMessage(request, response, ERROR, \"操作异常,请联系管理员！\", DEL);").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        logger.info(\"del:end\");").append("\r\n");
        sb.append("    }").append("\r\n").append("\r\n");



        //************************************************************************************************************
        //search方法
        //************************************************************************************************************
        sb.append("    @RequestMapping(\"/search.dhtml\")").append("\r\n");
        sb.append("    public void search(HttpServletRequest request, HttpServletResponse response) {").append("\r\n");
        sb.append("        logger.info(\"search:start\");").append("\r\n");
        sb.append("        String requestJson = request.getParameter(\"jsonStr\");").append("\r\n");
        sb.append("        try {").append("\r\n");
        sb.append("            requestJson = URLDecoder.decode(requestJson, \"utf-8\");").append("\r\n");
        sb.append("        } catch (UnsupportedEncodingException e) {").append("\r\n");
        sb.append("            logger.error(\"URLDecoderError\", e);").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        logger.info(\"requestJson:\" + requestJson);").append("\r\n");
        sb.append("        JSONObject jsonObject = JSONObject.fromObject(requestJson);").append("\r\n");
        sb.append("        logger.info(\"jsonObject:\" + jsonObject.toString());").append("\r\n");
        sb.append("        try {").append("\r\n");
        sb.append("            // 参数预处理").append("\r\n");
        sb.append("            HashMap<String, Object> paramMap = new HashMap<String, Object>();").append("\r\n");
        sb.append("            paramMap = beferLogic(request, jsonObject);").append("\r\n");
        sb.append("            paramMap = beforeExport(request, paramMap);").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("            logger.info(\"查询参数:paramMap:\" + paramMap);").append("\r\n");
        sb.append("            paramMap = "+logicVarName+".queryPage"+cleanentityClassName+"(paramMap);").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("            logger.info(\"查询结果:paramMap:\" + paramMap);").append("\r\n");
        sb.append("            paramMap = (HashMap<String, Object>) afterLogic(request, paramMap);").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("            logger.info(\"传出结果:paramMap:\" + paramMap);").append("\r\n");
        sb.append("            // 输出模式").append("\r\n");
        sb.append("            String queryType = (String)jsonObject.get(\"queryType\");").append("\r\n");
        sb.append("            if (StringUtils.isEmpty(queryType) || !queryType.equals(\"download\")) {").append("\r\n");
        sb.append("                // 正常输出模式").append("\r\n");
        sb.append("                ajaxJsonMessage(request, response, paramMap, QUERY);").append("\r\n");
        sb.append("            } else {").append("\r\n");
        sb.append("                // 导出模式").append("\r\n");
        sb.append("                export(request, response, paramMap);").append("\r\n");
        sb.append("                return;").append("\r\n");
        sb.append("            }").append("\r\n");
        sb.append("        } catch (Exception e) {").append("\r\n");
        sb.append("            logger.error(\"处理失败\", e);").append("\r\n");
        sb.append("            ajaxJsonMessage(request, response, ERROR, \"操作异常,请联系管理员！\", QUERY);").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        logger.info(\"search:end\");").append("\r\n");
        sb.append("    }").append("\r\n").append("\r\n");


        //************************************************************************************************************
        //afterLogic方法
        //************************************************************************************************************
        sb.append("    /**").append("\r\n");
        sb.append("     * logic后处理，格式转化：数据库层->页面，入口为单个或者列表").append("\r\n");
        sb.append("     * 操作原理为对原对象进行修改，不生成新对象").append("\r\n");
        sb.append("     * ").append("\r\n");
        sb.append("     * @param request").append("\r\n");
        sb.append("     * @param obj").append("\r\n");
        sb.append("     * @return").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    protected Map<String, Object> afterLogic(HttpServletRequest request, Map<String, Object> obj) throws Exception {").append("\r\n");
        sb.append("        List<HashMap<String, Object>> records = (List<HashMap<String, Object>>) obj.get(PageQueryUtil.PAGERECORDS);").append("\r\n");
        sb.append("        if (records != null) {").append("\r\n");
        sb.append("            // 如果是分页的查询，则对每一个结果进行处理").append("\r\n");
        sb.append("            for (HashMap<String, Object> cell_of_list : records) {").append("\r\n");
        sb.append("                afterLogicpreCell(request, cell_of_list);").append("\r\n");
        sb.append("            }").append("\r\n");
        sb.append("        } else {").append("\r\n");
        sb.append("            // 如果是单个查询，则直接对查询结果进行处理").append("\r\n");
        sb.append("            afterLogicpreCell(request, obj);").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        return obj;").append("\r\n");
        sb.append("    }").append("\r\n").append("\r\n");


        //************************************************************************************************************
        //beferLogic方法
        //************************************************************************************************************
        sb.append("    /**").append("\r\n");
        sb.append("     * logic前处理，格式转化：页面->数据库层，功能为将obj中的对象生成为一个HashMap<String,Object>对象").append("\r\n");
        sb.append("     * 生成的对象为全新的对象").append("\r\n");
        sb.append("     * 如果对此方法进行修改，请“增量式”的修改，尽量避免对原代码造成不必要影响").append("\r\n");
        sb.append("     * 前处理默认规则：金额、日期从数据库层面定义。金钱根据input.attr中是否有\"ismoney\":\"true\"来判断").append("\r\n");
        sb.append("     *          金额、日期为直接替换").append("\r\n");
        sb.append("     * @param request").append("\r\n");
        sb.append("     * @param obj").append("\r\n");
        sb.append("     * @return").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    protected HashMap<String, Object> beferLogic(HttpServletRequest request, Map<String, Object> obj) throws Exception {").append("\r\n");
        sb.append("        HashMap<String, Object> result = new HashMap<String, Object>();").append("\r\n");
        sb.append("        for (Map.Entry<String, Object> e : obj.entrySet()) {").append("\r\n");
        sb.append("            String key = e.getKey();").append("\r\n");
        sb.append("            String value = e.getValue().toString();").append("\r\n");
        sb.append("            result.put(key, value);").append("\r\n");
        //遍历字段
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_comment_all = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
            JSONObject column_comment_json = (JSONObject)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
            String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
            try{
                type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
            }catch (Exception e){
                //e.printStackTrace();
            }
            String power = GeneratorJSONProperties.DEFAULT_power;//设置为默认的权限
            try{
                JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                power = attr.getString(GeneratorJSONProperties.PATH_power);
            }catch (Exception e){
                //e.printStackTrace();
            }
            if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                String ismoney = GeneratorJSONProperties.DEFAULT_ismoney;
                try{
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    ismoney = attr.getString(GeneratorJSONProperties.PATH_ismoney);
                }catch (Exception e){
                    //e.printStackTrace();
                }
                if(ismoney.equals("true")){
                    sb.append("            if (key.equals(\""+column_name_tf+"\") && StringUtils.isNotEmpty(value)) {").append("\r\n");
                    sb.append("                try {").append("\r\n");
                    sb.append("                    result.put(\""+column_name_tf+"\", new BigDecimal(MoneyFormatUtils.format(value, \"###0.##\", \"y2f\")));").append("\r\n");
                    sb.append("                } catch(Exception ex) {").append("\r\n");
                    sb.append("                    logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                    sb.append("                    throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                    sb.append("                }").append("\r\n");
                    sb.append("            }").append("\r\n");
                }
            }
            if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                String formatter_date_char8 = GeneratorJSONProperties.DEFAULT_formatter_char8;
                String formatter_date_char14 = GeneratorJSONProperties.DEFAULT_formatter_char14;
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    formatter_date_char8 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                    formatter_date_char14 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                formatter_date_char8 = formatter_date_char8.split(";")[1];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                formatter_date_char14 = formatter_date_char14.split(";")[1];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                sb.append("            if (key.equals(\""+column_name_tf+"\") && StringUtils.isNotEmpty(value)) {").append("\r\n");
                sb.append("                try {").append("\r\n");
                sb.append("                    java.text.SimpleDateFormat sdf_page = new java.text.SimpleDateFormat(\""+formatter_date_char8+"\");").append("\r\n");
                sb.append("                    java.text.SimpleDateFormat sdf_db = new java.text.SimpleDateFormat(\"yyyyMMdd\");").append("\r\n");
                sb.append("                    String dbDateStr=sdf_db.format(sdf_page.parse(value));").append("\r\n");
                sb.append("                    result.put(\""+column_name_tf+"\", dbDateStr);").append("\r\n");
                sb.append("                } catch(Exception ex) {").append("\r\n");
                sb.append("                    logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                sb.append("                    throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                sb.append("                }").append("\r\n");
                sb.append("            }").append("\r\n");

                //判断是否有queryrange标志
                {
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                        if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                            //如果有queryrange条件，则增加_start和_end查询，闭区间
                            sb.append("            if (key.equals(\""+column_name_tf+"_start\") && StringUtils.isNotEmpty(value)) {").append("\r\n");
                            sb.append("                try {").append("\r\n");
                            sb.append("                    java.text.SimpleDateFormat sdf_page = new java.text.SimpleDateFormat(\""+formatter_date_char8+"\");").append("\r\n");
                            sb.append("                    java.text.SimpleDateFormat sdf_db = new java.text.SimpleDateFormat(\"yyyyMMdd\");").append("\r\n");
                            sb.append("                    String dbDateStr=sdf_db.format(sdf_page.parse(value));").append("\r\n");
                            sb.append("                    result.put(\""+column_name_tf+"_start\", dbDateStr);").append("\r\n");
                            sb.append("                } catch(Exception ex) {").append("\r\n");
                            sb.append("                    logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                            sb.append("                    throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                            sb.append("                }").append("\r\n");
                            sb.append("            }").append("\r\n");
                            sb.append("            if (key.equals(\""+column_name_tf+"_end\") && StringUtils.isNotEmpty(value)) {").append("\r\n");
                            sb.append("                try {").append("\r\n");
                            sb.append("                    java.text.SimpleDateFormat sdf_page = new java.text.SimpleDateFormat(\""+formatter_date_char14+"\");").append("\r\n");
                            sb.append("                    java.text.SimpleDateFormat sdf_db = new java.text.SimpleDateFormat(\"yyyyMMdd\");").append("\r\n");
                            sb.append("                    String dbDateStr=sdf_db.format(sdf_page.parse(value));").append("\r\n");
                            sb.append("                    result.put(\""+column_name_tf+"_end\", dbDateStr);").append("\r\n");
                            sb.append("                } catch(Exception ex) {").append("\r\n");
                            sb.append("                    logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                            sb.append("                    throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                            sb.append("                }").append("\r\n");
                            sb.append("            }").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                    }
                }
            }
            if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                String formatter_date_char8 = GeneratorJSONProperties.DEFAULT_formatter_char8;
                String formatter_date_char14 = GeneratorJSONProperties.DEFAULT_formatter_char14;
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    formatter_date_char8 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                    formatter_date_char14 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                formatter_date_char8 = formatter_date_char8.split(";")[1];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                formatter_date_char14 = formatter_date_char14.split(";")[1];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                sb.append("            if (key.equals(\""+column_name_tf+"\") && StringUtils.isNotEmpty(value)) {").append("\r\n");
                sb.append("                try {").append("\r\n");
                sb.append("                    java.text.SimpleDateFormat sdf_page = new java.text.SimpleDateFormat(\""+formatter_date_char14+"\");").append("\r\n");
                sb.append("                    java.text.SimpleDateFormat sdf_db = new java.text.SimpleDateFormat(\"yyyyMMddHHmmss\");").append("\r\n");
                sb.append("                    String dbDateStr=sdf_db.format(sdf_page.parse(value));").append("\r\n");
                sb.append("                    result.put(\""+column_name_tf+"\", dbDateStr);").append("\r\n");
                sb.append("                } catch(Exception ex) {").append("\r\n");
                sb.append("                    logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                sb.append("                    throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                sb.append("                }").append("\r\n");
                sb.append("            }").append("\r\n");

                //判断是否有queryrange标志
                {
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                        if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                            //如果有queryrange条件，则增加_start和_end查询，闭区间
                            sb.append("            if (key.equals(\""+column_name_tf+"_start\") && StringUtils.isNotEmpty(value)) {").append("\r\n");
                            sb.append("                try {").append("\r\n");
                            sb.append("                    java.text.SimpleDateFormat sdf_page = new java.text.SimpleDateFormat(\""+formatter_date_char8+"\");").append("\r\n");
                            sb.append("                    java.text.SimpleDateFormat sdf_db = new java.text.SimpleDateFormat(\"yyyyMMddHHmmss\");").append("\r\n");
                            sb.append("                    String dbDateStr=sdf_db.format(sdf_page.parse(value));").append("\r\n");
                            sb.append("                    result.put(\""+column_name_tf+"_start\", dbDateStr);").append("\r\n");
                            sb.append("                } catch(Exception ex) {").append("\r\n");
                            sb.append("                    logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                            sb.append("                    throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                            sb.append("                }").append("\r\n");
                            sb.append("            }").append("\r\n");
                            sb.append("            if (key.equals(\""+column_name_tf+"_end\") && StringUtils.isNotEmpty(value)) {").append("\r\n");
                            sb.append("                try {").append("\r\n");
                            sb.append("                    java.text.SimpleDateFormat sdf_page = new java.text.SimpleDateFormat(\""+formatter_date_char14+"\");").append("\r\n");
                            sb.append("                    java.text.SimpleDateFormat sdf_db = new java.text.SimpleDateFormat(\"yyyyMMddHHmmss\");").append("\r\n");
                            sb.append("                    String dbDateStr=sdf_db.format(sdf_page.parse(value));").append("\r\n");
                            sb.append("                    result.put(\""+column_name_tf+"_end\", dbDateStr);").append("\r\n");
                            sb.append("                } catch(Exception ex) {").append("\r\n");
                            sb.append("                    logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                            sb.append("                    throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                            sb.append("                }").append("\r\n");
                            sb.append("            }").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                    }
                }
            }
        }
        sb.append("            //*********************************************************").append("\r\n");
        sb.append("            //可在此处增加对格式的转换").append("\r\n");
        sb.append("            //*********************************************************").append("\r\n");
        sb.append("        }").append("\r\n");
        sb.append("        return result;").append("\r\n");
        sb.append("    }").append("\r\n").append("\r\n");


        //************************************************************************************************************
        //afterLogic方法
        //************************************************************************************************************
        sb.append("    /**").append("\r\n");
        sb.append("     * logic后处理，格式转化：数据库层->页面").append("\r\n");
        sb.append("     * 操作原理为对原对象进行修改，不生成新对象").append("\r\n");
        sb.append("     * 如果对此方法进行修改，请“增量式”的修改，尽量避免对原代码造成不必要影响").append("\r\n");
        sb.append("     * 后处理默认规则：金额、日期、select、radio从数据库层面定义。金钱根据input.attr中是否有\"ismoney\":\"true\"来判断").append("\r\n");
        sb.append("     *          金额、日期为直接替换 、select、radio通过新字段转义").append("\r\n");
        sb.append("     * @param request").append("\r\n");
        sb.append("     * @param result").append("\r\n");
        sb.append("     * @return").append("\r\n");
        sb.append("     */").append("\r\n");
        sb.append("    private Map<String, Object> afterLogicpreCell(HttpServletRequest request, Map<String, Object> result) throws Exception {").append("\r\n");





        //遍历字段
        for(int i=0;i<count;i++){
            String column_name = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
            String column_name_tf = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
            String column_comment = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
            String column_comment_all = (String)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
            JSONObject column_comment_json = (JSONObject)((List)c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
            String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
            try{
                type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
            }catch (Exception e){
                //e.printStackTrace();
            }
            String power = GeneratorJSONProperties.DEFAULT_power;//设置为默认的权限
            try{
                JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                power = attr.getString(GeneratorJSONProperties.PATH_power);
            }catch (Exception e){
                //e.printStackTrace();
            }
            if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                String ismoney = GeneratorJSONProperties.DEFAULT_ismoney;
                try{
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    ismoney = attr.getString(GeneratorJSONProperties.PATH_ismoney);
                }catch (Exception e){
                    //e.printStackTrace();
                }
                if(ismoney.equals("true")){
                    sb.append("            if (result.keySet().contains(\""+column_name_tf+"\")) {").append("\r\n");
                    sb.append("                String key = \""+column_name_tf+"\";").append("\r\n");
                    sb.append("                Object value = result.get(key);").append("\r\n");
                    sb.append("                if(value != null){").append("\r\n");
                    sb.append("                    try {").append("\r\n");
                    sb.append("                        result.put(key, MoneyFormatUtils.format(value.toString(), \"#,##0.##\", \"f2y\"));").append("\r\n");
                    sb.append("                    } catch(Exception ex) {").append("\r\n");
                    sb.append("                        logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                    sb.append("                        throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                    sb.append("                    }").append("\r\n");
                    sb.append("                }").append("\r\n");
                    sb.append("            }").append("\r\n");
                }
            }
            if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                String formatter_date_char8 = GeneratorJSONProperties.DEFAULT_formatter_char8;
                String formatter_date_char14 = GeneratorJSONProperties.DEFAULT_formatter_char14;
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    formatter_date_char8 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                    formatter_date_char14 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                formatter_date_char8 = formatter_date_char8.split(";")[1];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                formatter_date_char14 = formatter_date_char14.split(";")[1];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                sb.append("            if (result.keySet().contains(\""+column_name_tf+"\")) {").append("\r\n");
                sb.append("                String key = \""+column_name_tf+"\";").append("\r\n");
                sb.append("                Object value = result.get(key);").append("\r\n");
                sb.append("                if(StringUtils.isNotEmpty((String)value)){").append("\r\n");
                sb.append("                    try {").append("\r\n");
                sb.append("                        java.text.SimpleDateFormat sdf_page = new java.text.SimpleDateFormat(\""+formatter_date_char8+"\");").append("\r\n");
                sb.append("                        java.text.SimpleDateFormat sdf_db = new java.text.SimpleDateFormat(\"yyyyMMdd\");").append("\r\n");
                sb.append("                        String pageDateStr=sdf_page.format(sdf_db.parse((String)value));").append("\r\n");
                sb.append("                        result.put(key, pageDateStr);").append("\r\n");
                sb.append("                    } catch(Exception ex) {").append("\r\n");
                sb.append("                        logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                sb.append("                        throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                sb.append("                    }").append("\r\n");
                sb.append("                }").append("\r\n");
                sb.append("            }").append("\r\n");
            }
            if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                String formatter_date_char8 = GeneratorJSONProperties.DEFAULT_formatter_char8;
                String formatter_date_char14 = GeneratorJSONProperties.DEFAULT_formatter_char14;
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    formatter_date_char8 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                    formatter_date_char14 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                formatter_date_char8 = formatter_date_char8.split(";")[1];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                formatter_date_char14 = formatter_date_char14.split(";")[1];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                sb.append("            if (result.keySet().contains(\""+column_name_tf+"\")) {").append("\r\n");
                sb.append("                String key = \""+column_name_tf+"\";").append("\r\n");
                sb.append("                Object value = result.get(key);").append("\r\n");
                sb.append("                if(StringUtils.isNotEmpty((String)value)){").append("\r\n");
                sb.append("                    try {").append("\r\n");
                sb.append("                        java.text.SimpleDateFormat sdf_page = new java.text.SimpleDateFormat(\""+formatter_date_char14+"\");").append("\r\n");
                sb.append("                        java.text.SimpleDateFormat sdf_db = new java.text.SimpleDateFormat(\"yyyyMMddHHmmss\");").append("\r\n");
                sb.append("                        String pageDateStr=sdf_page.format(sdf_db.parse((String)value));").append("\r\n");
                sb.append("                        result.put(key, pageDateStr);").append("\r\n");
                sb.append("                    } catch(Exception ex) {").append("\r\n");
                sb.append("                        logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                sb.append("                        throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                sb.append("                    }").append("\r\n");
                sb.append("                }").append("\r\n");
                sb.append("            }").append("\r\n");
            }
            if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                sb.append("            if (result.keySet().contains(\""+column_name_tf+"\")) {").append("\r\n");
                sb.append("                String key = \""+column_name_tf+"\";").append("\r\n");
                sb.append("                Object value = result.get(key);").append("\r\n");
                sb.append("                if(value!=null&&StringUtils.isNotEmpty(value.toString())){").append("\r\n");
                sb.append("                    try {").append("\r\n");
                sb.append("                        String "+column_name_tf+"_name = null;").append("\r\n");
                //静态模式
                try{
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                    if(options_o!=null){
                        sb.append("                        JSONObject json = JSONObject.fromObject(\""+((JSONObject)options_o).toString().replaceAll("\"","'")+"\");").append("\r\n");
                        sb.append("                        "+column_name_tf+"_name = json.get(value.toString())==null?value.toString():(String)json.get(value.toString());").append("\r\n");
                    }
                }catch (Exception e){
                    //e.printStackTrace();
                    System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析SELECT-options异常");
                }
                //动态模式
                try{
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                    Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                    if(l1Code!=null&&l2Code!=null){
                        sb.append("                        "+column_name_tf+"_name = SysDicUtils.getValue(\""+l1Code+"\",\""+l2Code+"\",value.toString());").append("\r\n");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析SELECT-l1Code-l2Code异常");
                }
                sb.append("                        result.put(\""+column_name_tf+"_name\", "+column_name_tf+"_name);").append("\r\n");
                sb.append("                    } catch(Exception ex) {").append("\r\n");
                sb.append("                        logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                sb.append("                        throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                sb.append("                    }").append("\r\n");
                sb.append("                }").append("\r\n");
                sb.append("            }").append("\r\n");
            }
            if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                sb.append("            if (result.keySet().contains(\""+column_name_tf+"\")) {").append("\r\n");
                sb.append("                String key = \""+column_name_tf+"\";").append("\r\n");
                sb.append("                Object value = result.get(key);").append("\r\n");
                sb.append("                if(value!=null&&StringUtils.isNotEmpty(value.toString())){").append("\r\n");
                sb.append("                    try {").append("\r\n");
                sb.append("                        String "+column_name_tf+"_name = null;").append("\r\n");
                //静态模式
                try{
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                    if(options_o!=null){
                        sb.append("                        JSONObject json = JSONObject.fromObject(\""+((JSONObject)options_o).toString().replaceAll("\"","'")+"\");").append("\r\n");
                        sb.append("                        "+column_name_tf+"_name = json.get(value.toString())==null?value.toString():(String)json.get(value.toString());").append("\r\n");
                    }
                }catch (Exception e){
                    //e.printStackTrace();
                    System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析SELECT-options异常");
                }
                //动态模式
                try{
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                    Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                    if(l1Code!=null&&l2Code!=null){
                        sb.append("                        "+column_name_tf+"_name = SysDicUtils.getValue(\""+l1Code+"\",\""+l2Code+"\",value.toString());").append("\r\n");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析SELECT-l1Code-l2Code异常");
                }
                sb.append("                        result.put(\""+column_name_tf+"_name\", "+column_name_tf+"_name);").append("\r\n");
                sb.append("                    } catch(Exception ex) {").append("\r\n");
                sb.append("                        logger.error(\"格式转化失败[\"+key+\"][\"+value+\"]\", ex);").append("\r\n");
                sb.append("                        throw new Exception(\"格式转化失败[\"+key+\"][\"+value+\"]\");").append("\r\n");
                sb.append("                    }").append("\r\n");
                sb.append("                }").append("\r\n");
                sb.append("            }").append("\r\n");
            }
        }

        sb.append("            //*********************************************************").append("\r\n");
        sb.append("            //可在此处增加对格式的转换").append("\r\n");
        sb.append("            //*********************************************************").append("\r\n");
        sb.append("        return result;").append("\r\n");
        sb.append("    }").append("\r\n");

        sb.append("}").append("\r\n");
        pw.println(sb.toString());
        pw.close();
    }
}
