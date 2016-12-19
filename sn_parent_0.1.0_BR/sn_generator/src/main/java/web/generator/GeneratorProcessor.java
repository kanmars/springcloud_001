package web.generator;

import context.Context;
import exec.Generator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import pipe.impl.A_GetTableInfo;
import pipe.impl.dmodel.*;
import pipe.impl.meta.SQLTYPE;
import pipe.impl.meta.TableInfoMetaCreater;
import sun.misc.BASE64Decoder;
import utils.FormatUtils;
import web.AbstractProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by baolong on 2016/8/23.
 */
public class GeneratorProcessor extends AbstractProcessor {
    @Override

    public void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String type=httpServletRequest.getParameter("type");
        if(type.equals("a_01_selectDBINFO")){
            exec_a_01_selectDBINFO(httpServletRequest,httpServletResponse);
        }else if(type.equals("a_02_buildFiles")){
            exec_a_02_buildFiles(httpServletRequest,httpServletResponse);
        }
    }
    public void exec_a_01_selectDBINFO(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String dbtype=null;
        String driver_class = null;
        String url=null;
        String username=null;
        String schema=null;
        String password=null;
        String dbDriverInfo = httpServletRequest.getParameter("dbDriverInfo");
        try {
            dbDriverInfo = new String(new BASE64Decoder().decodeBuffer(dbDriverInfo),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String tableName = httpServletRequest.getParameter("tableName");
        StringReader sr = new StringReader(dbDriverInfo);
        BufferedReader br = new BufferedReader(sr);
        String temp = null;
        try {
            while((temp=br.readLine())!=null){
                if(temp.startsWith("//")){
                    continue;
                }
                if(temp.startsWith("sn.jdbc.dbtype=")){
                    dbtype = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.driverClass=")){
                    driver_class = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.url=")){
                    url = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.username=")){
                    username = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.schema=")){
                    schema = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.password=")){
                    password = temp.substring(temp.indexOf("=")+1);
                }
            }
            JSONObject result = new JSONObject();
            result.put("resCode", "0000");

            if(!StringUtils.isEmpty(driver_class)&&!StringUtils.isEmpty(url)){
                try {
                    Class.forName(driver_class);
                    Connection conn = DriverManager.getConnection(url,username,password);
                    if(conn == null){
                        throw new Exception("创建数据库连接不成功");
                    }

                    ResultSet tableSet = null;
                    if(dbtype.equals("MYSQL")){
                        tableName = tableName;
                    }else if(dbtype.equals("ORACLE")){
                        tableName = tableName.toUpperCase();
                    }else if(dbtype.equals("SQLITE")){
                        tableName = tableName;
                    }else{
                        throw new Exception("暂不支持的数据类型");
                    }

                    tableSet = conn.getMetaData().getTables(schema, "%", tableName, new String[]{"TABLE"});
                    if(!tableSet.next()){
                        throw new Exception("该表不存在");
                    }
                    Context c = new Context();
                    c.put(Context.JDBC_CONNECTION,conn);
                    c.put(Context.JDBC_SCHEMA,schema);
                    JSONArray jsonArray = new JSONArray();
                    ResultSet primaryKeyResultSet = conn.getMetaData().getPrimaryKeys(null, "%", tableName);
                    Set primarySet = new HashSet<String>();
                    while(primaryKeyResultSet.next()){
                        primarySet.add(primaryKeyResultSet.getString(4));
                    }
                    ResultSet columnSet = conn.getMetaData().getColumns(schema, "%", tableName, "%");
                    int count = 0;
                    while(columnSet.next()){
                        count++;
                        String columnName = columnSet.getString("COLUMN_NAME");
                        String columnComment_ALL = columnSet.getString("REMARKS");

                        columnComment_ALL = new A_GetTableInfo(dbtype).queryColumnComment(c,tableName,columnName,columnComment_ALL);
                        String sqlType = columnSet.getString("TYPE_NAME");
                        String column_size = columnSet.getString("COLUMN_SIZE");
                        String decimal_digits = columnSet.getString("DECIMAL_DIGITS");
                        String is_nullable = columnSet.getString("IS_NULLABLE").equals("YES")?"true":"false";

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(Context.CURR_TABLE_COLUMNS_NAME,columnName);
                        jsonObject.put(Context.CURR_TABLE_COLUMNS_NAME_TF, FormatUtils.formatDBNameToVarName(columnName));
                        jsonObject.put(Context.CURR_TABLE_COLUMNS_COMMENT_ALL, columnComment_ALL);
                        //如果是描述+:+标准JSON格式COMMENT
                        if(columnComment_ALL!=null && columnComment_ALL.indexOf(":")>0 && columnComment_ALL.substring(columnComment_ALL.indexOf(":")+1).trim().charAt(0)=='{'){
                            try {
                                JSONObject json = JSONObject.fromObject(columnComment_ALL.substring(columnComment_ALL.indexOf(":") + 1).trim());
                                jsonObject.put(Context.CURR_TABLE_COLUMNS_COMMENT, columnComment_ALL.substring(0, columnComment_ALL.indexOf(":")));
                                jsonObject.put(Context.CURR_TABLE_COLUMNS_COMMENT_JSON, json);
                            }catch(Exception e){
                                jsonObject.put(Context.CURR_TABLE_COLUMNS_COMMENT, columnComment_ALL);
                                jsonObject.put(Context.CURR_TABLE_COLUMNS_COMMENT_JSON, new JSONObject());
                                System.out.println("运行异常：在解析数据库Comment的时候，发现JSON字符串报错，错误信息为");
                                System.out.println("tablename["+tableName+"],columnName["+columnName+"],columnComment_ALL["+columnComment_ALL+"]");
                                throw new Exception("运行异常：在解析数据库Comment的时候，发现JSON字符串报错。" +"此处Exception 可以删除，删除后则将报错的字段视作Input处理，但不建议删除，建议按照严格JSON字符串设置报错处理");
                            }
                        } else {
                            jsonObject.put(Context.CURR_TABLE_COLUMNS_COMMENT, columnComment_ALL);
                            jsonObject.put(Context.CURR_TABLE_COLUMNS_COMMENT_JSON, new JSONObject());
                        }


                        jsonObject.put(Context.CURR_TABLE_COLUMNS_TYPE, sqlType);
                        jsonObject.put(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_SIZE, column_size);
                        jsonObject.put(Context.CURR_TABLE_COLUMNS_TYPE_COLUMN_DECIMAL_DIGITS, decimal_digits);
                        jsonObject.put("isNullAble", is_nullable);

                        if(primarySet.contains(columnName)){
                            jsonObject.put("isPrimaryKey","true");
                        }else{
                            jsonObject.put("isPrimaryKey","false");
                        }

                        jsonArray.add(jsonObject);
                    }
                    c.close();
                    result.put("resDesc", "成功");
                    result.put("columnsJsonArray", jsonArray);
                } catch (Exception e) {
                    throw e;
                }
            }
            try {
                httpServletResponse.getOutputStream().write(result.toString().getBytes("utf-8"));
                httpServletResponse.getOutputStream().flush();
                httpServletResponse.getOutputStream().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("resCode","0002");
            result.put("resDesc", e.getMessage());
            try {
                httpServletResponse.getOutputStream().write(result.toString().getBytes("utf-8"));
                httpServletResponse.getOutputStream().flush();
                httpServletResponse.getOutputStream().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void exec_a_02_buildFiles(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String dbtype=null;
        String driver_class = null;
        String url=null;
        String username=null;
        String schema=null;
        String password=null;

        String dbDriverInfo = httpServletRequest.getParameter("dbDriverInfo");
        try {
            dbDriverInfo = new String(new BASE64Decoder().decodeBuffer(dbDriverInfo),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String basePath=httpServletRequest.getParameter("basePath");
        try {
            basePath = new String(new BASE64Decoder().decodeBuffer(basePath),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String tableName = httpServletRequest.getParameter("tableName");
        String tableComment = httpServletRequest.getParameter("tableComment");
        try {
            tableComment = URLDecoder.decode(new String(new BASE64Decoder().decodeBuffer(tableComment),"utf-8"),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        StringReader sr = new StringReader(dbDriverInfo);
        BufferedReader br = new BufferedReader(sr);
        String temp = null;
        try {
            while((temp=br.readLine())!=null){
                if(temp.startsWith("//")){
                    continue;
                }
                if(temp.startsWith("sn.jdbc.dbtype=")){
                    dbtype = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.driverClass=")){
                    driver_class = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.url=")){
                    url = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.username=")){
                    username = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.schema=")){
                    schema = temp.substring(temp.indexOf("=")+1);
                }
                if(temp.startsWith("sn.jdbc.password=")){
                    password = temp.substring(temp.indexOf("=")+1);
                }
            }
            String buildParam = httpServletRequest.getParameter("buildParam");
            buildParam = URLDecoder.decode(buildParam);
            final JSONArray buildParamJson = JSONArray.fromObject(buildParam);
            JSONObject result = new JSONObject();
            result.put("resCode", "0000");

            if(!StringUtils.isEmpty(driver_class)&&!StringUtils.isEmpty(url)){
                try {
                    Class.forName(driver_class);
                    Connection conn = DriverManager.getConnection(url,username,password);
                    if(conn == null){
                        throw new Exception("创建数据库连接不成功");
                    }

                    ResultSet tableSet = null;
                    if(dbtype.equals("MYSQL")){
                        tableName = tableName;
                    }else if(dbtype.equals("ORACLE")){
                        tableName = tableName.toUpperCase();
                    }else if(dbtype.equals("SQLITE")){
                        tableName = tableName;
                    }else{
                        throw new Exception("暂不支持的数据类型");
                    }


                    Context c = new Context();
                    c.put(Context.JDBC_CONNECTION,conn);
                    c.put(Context.JDBC_SCHEMA, schema);

                    Generator g = new Generator();
                    boolean entity = new Boolean(httpServletRequest.getParameter("entity"));
                    boolean dao = new Boolean(httpServletRequest.getParameter("dao"));
                    boolean mapper = new Boolean(httpServletRequest.getParameter("mapper"));
                    boolean logic = new Boolean(httpServletRequest.getParameter("logic"));
                    boolean controller = new Boolean(httpServletRequest.getParameter("controller"));
                    boolean ftl = new Boolean(httpServletRequest.getParameter("ftl"));
                    boolean js = new Boolean(httpServletRequest.getParameter("js"));
                    final String finalTableName = tableName;
                    final String finalTableComment = tableComment;
                    final String finalDbType = dbtype;
                    final StringBuilder sb = new StringBuilder();
                    g.exec(dbtype,basePath,driver_class,url,username,password,schema,new String[]{},new TableInfoMetaCreater(){
                        {
                            this.A_001_addTable(finalTableName,finalTableComment);
                            //输出COMMENT语句
                            if(finalDbType.equals("MYSQL")){
                                sb.append("ALTER TABLE `"+finalTableName+"` COMMENT '"+finalTableComment+"';").append("\r\n");
                            }
                            if(finalDbType.equals("ORACLE")){
                                sb.append("COMMENT ON table "+finalTableName+" IS '"+finalTableComment+"';").append("\r\n");
                            }

                            for(JSONObject json : (List<JSONObject>)buildParamJson){
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
                                JSONObject columnJSON = null;

                                if(json.getString("type").equals("input")){
                                    columnJSON = InputModel.getInstance(
                                            json.getString("power"),
                                            json.getString("notEmptyMsg"),
                                            Integer.parseInt(json.getString("min")),
                                            Integer.parseInt(json.getString("max")),
                                            json.getString("strLengthMsg"),
                                            json.getString("regExp"),
                                            json.getString("regExpMsg"),
                                            new Boolean(json.getString("isMoney")),
                                            json.getString("sequence")).getModelJSON();
                                }else if(json.getString("type").equals("date")){
                                    columnJSON = DateModel.getInstance(
                                            json.getString("power"),
                                            new Boolean(json.getString("autoInsert")),
                                            new Boolean(json.getString("autoUpdate")),
                                            new Boolean(json.getString("queryRange"))
                                    ).getModelJSON();
                                }else if(json.getString("type").equals("datetime")){
                                    columnJSON = DateTimeModel.getInstance(
                                            json.getString("power"),
                                            new Boolean(json.getString("autoInsert")),
                                            new Boolean(json.getString("autoUpdate")),
                                            new Boolean(json.getString("queryRange"))
                                    ).getModelJSON();
                                }else if(json.getString("type").equals("radio")){
                                    if(utils.StringUtils.isNotEmpty(json.getString("options"))){
                                        String options_str = json.getString("options");
                                        options_str = options_str.replace("{","");
                                        options_str = options_str.replace("}","");
                                        options_str = options_str.replace("'","");
                                        options_str = options_str.replace("\"","");
                                        options_str = options_str.replace(":",",");
                                        String[] options =  options_str.split(",");
                                        columnJSON = RadioModel.getInstanceByOptions(json.getString("power"), json.getString("notEmptyMsg"),options).getModelJSON();
                                    }else{
                                        String l1Code = json.getString("l1Code");
                                        String l2Code = json.getString("l2Code");
                                        columnJSON = RadioModel.getInstanceByL1CodeL2Code(json.getString("power"), json.getString("notEmptyMsg"),l1Code,l2Code).getModelJSON();
                                    }
                                }else if(json.getString("type").equals("select")){
                                    if(utils.StringUtils.isNotEmpty(json.getString("options"))){
                                        String options_str = json.getString("options");
                                        options_str = options_str.replace("{","");
                                        options_str = options_str.replace("}","");
                                        options_str = options_str.replace("'","");
                                        options_str = options_str.replace("\"","");
                                        options_str = options_str.replace(":",",");
                                        String[] options =  options_str.split(",");
                                        columnJSON = SelectModel.getInstanceByOptions(json.getString("power"), json.getString("notEmptyMsg"),options).getModelJSON();
                                    }else{
                                        String l1Code = json.getString("l1Code");
                                        String l2Code = json.getString("l2Code");
                                        columnJSON = SelectModel.getInstanceByL1CodeL2Code(json.getString("power"), json.getString("notEmptyMsg"),l1Code,l2Code).getModelJSON();
                                    }

                                }else if(json.getString("type").equals("checkbox")){
                                    if(utils.StringUtils.isNotEmpty(json.getString("options"))){
                                        String options_str = json.getString("options");
                                        options_str = options_str.replace("[","");
                                        options_str = options_str.replace("]","");
                                        options_str = options_str.replace("'","");
                                        options_str = options_str.replace("\"","");
                                        options_str = options_str.replace(":",",");
                                        String[] options =  options_str.split(",");
                                        columnJSON = CheckboxModel.getInstanceByOptions(json.getString("power"), json.getString("notEmptyMsg"),options).getModelJSON();
                                    }else{
                                        String l1Code = json.getString("l1Code");
                                        String l2Code = json.getString("l2Code");
                                        columnJSON = CheckboxModel.getInstanceByL1CodeL2Code(json.getString("power"), json.getString("notEmptyMsg"),l1Code,l2Code).getModelJSON();
                                    }
                                }else if(json.getString("type").equals("textarea")){
                                    columnJSON = TextareaModel.getInstance(
                                            json.getString("power"),
                                            json.getString("notEmptyMsg"),
                                            Integer.parseInt(json.getString("min")),
                                            Integer.parseInt(json.getString("max")),
                                            json.getString("strLengthMsg"),
                                            json.getString("regExp"),
                                            json.getString("regExpMsg")).getModelJSON();
                                }
                                SQLTYPE sqltype = null;
                                if(json.getString("sqlType").equals("INT"))sqltype=SQLTYPE.INT;
                                if(json.getString("sqlType").equals("BIGINT"))sqltype=SQLTYPE.BIGINT;
                                if(json.getString("sqlType").equals("NUMBER"))sqltype=SQLTYPE.NUMBER;
                                if(json.getString("sqlType").equals("CHAR"))sqltype=SQLTYPE.CHAR;
                                if(json.getString("sqlType").equals("VARCHAR"))sqltype=SQLTYPE.VARCHAR;
                                this.addColumn(
                                        finalTableName,
                                        json.getString("column"),
                                        json.getString("columnName"),
                                        new Boolean(json.getString("isPrimaryKey")),
                                        sqltype,
                                        json.getString("columnTypeColumnSize"),
                                        json.getString("columnTypeColumnDecimalDigits"),
                                        columnJSON
                                        );

                                //输出COMMENT语句
                                if(finalDbType.equals("MYSQL")){
                                    sb.append("ALTER TABLE `"+finalTableName+"` MODIFY COLUMN `"+json.getString("column")+"` "+sqltype.toString()+"("+Integer.parseInt(json.getString("columnTypeColumnSize"))+")" +(json.getString("isNullAble").equals("YES")|json.getString("isNullAble").equals("true")?"NULL":"NOT NULL") +" COMMENT '"+json.getString("columnName")+ ":" + columnJSON.toString()+"';").append("\r\n");
                                }
                                if(finalDbType.equals("ORACLE")){
                                    sb.append("COMMENT ON COLUMN "+finalTableName+"."+json.getString("column")+" '"+json.getString("columnName")+":"+columnJSON.toString() + "';").append("\r\n");
                                }
                            }
                        }
                    },entity,dao,mapper,logic,controller,ftl,js);
                    System.out.println("需要执行的AlterComment语句为:\r\n" + sb.toString());
                    Statement state = conn.createStatement();
                    String sqls = sb.toString();
                    for(String sql:sqls.split(";\r\n")) {
                        if (StringUtils.isNotEmpty(sql)) {
                            boolean rs = state.execute(sql);
                            System.out.println("Comment语句更新结果:" + rs);
                        }
                    }
                    c.close();
                    result.put("resDesc", "成功");
                } catch (Exception e) {
                    throw e;
                }
            }
            try {
                httpServletResponse.getOutputStream().write(result.toString().getBytes("utf-8"));
                httpServletResponse.getOutputStream().flush();
                httpServletResponse.getOutputStream().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("resCode","0002");
            result.put("resDesc", e.getMessage());
            try {
                httpServletResponse.getOutputStream().write(result.toString().getBytes("utf-8"));
                httpServletResponse.getOutputStream().flush();
                httpServletResponse.getOutputStream().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
