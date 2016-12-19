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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * Created by baolong on 2016/1/14.
 */
public class Create_007_Js implements Pipe {

    public void exec(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_JS))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_JS)+"]创建不成功");
        }
        //准备数据
        String tableName = (String)c.get(Context.CURR_TABLE);//tbl_user_info
        String entityVarName = FormatUtils.formatDBNameToVarName(tableName);//tblUserInfo
        String entityClassName = FormatUtils.firstBig(entityVarName);//TblUserInfo
        String cleanentityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//UserInfo

        //去掉Tbl的头部
        if(cleanentityClassName.startsWith("Tbl")){
            cleanentityClassName = cleanentityClassName.substring(3);
        }
        String cleanentityClassNameFirstSmall = FormatUtils.firstSmall(cleanentityClassName);//userInfo

        String ftldirectory = cleanentityClassName.toLowerCase();//userinfo
        //创建子文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_JS) + File.separator + ftldirectory)){
            throw new Exception("文件夹["+(String) c.get(Context.PATH_JS) + File.separator + ftldirectory + "]创建不成功");
        }
        //创造
        String jsname = ftldirectory;
        String controllerPath = FormatUtils.firstSmall(cleanentityClassName);
        String fileName = jsname+".js";//文件名与父路径名相同
        File filePath = new File((String)c.get(Context.PATH_JS) + File.separator+ftldirectory+File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath),(String)c.get(Context.FILE_CHARSET)));

        //准备内容querySysmenuInfopageSize
        StringBuilder sb = new StringBuilder();
        sb.append("$(function(){").append("\r\n");
        sb.append("    search"+cleanentityClassName+"('');").append("\r\n");
        sb.append("    $('#query"+cleanentityClassName+"pageSize').on('change',function () {").append("\r\n");
        sb.append("        var currentPage = $(\"#query"+cleanentityClassName+"currentPage\").val();").append("\r\n");
        sb.append("        search"+cleanentityClassName+"(currentPage);").append("\r\n");
        sb.append("    });").append("\r\n");
        sb.append("    ").append("\r\n");


        {//----------------------------------------------------------------------------------------------------------------

            sb.append("    //时间框校验函数，时间框在校验失败后，再点击，不会触发校验，因此手工触发").append("\r\n");
            sb.append("    function revalidateDate(formname,datefieldname){").append("\r\n");
            sb.append("        if($(\"#\"+formname+\"\").data('bootstrapValidator')!=null").append("\r\n");
            sb.append("            && $(\"#\"+formname+\"\").data('bootstrapValidator').validateField !=null)").append("\r\n");
            sb.append("        {").append("\r\n");
            sb.append("            $(\"#\"+formname+\"\").data('bootstrapValidator').updateStatus(datefieldname,\"NOT_VALIDATED\",null);").append("\r\n");
            sb.append("            $(\"#\"+formname+\"\").data('bootstrapValidator').validateField(datefieldname);").append("\r\n");
            sb.append("        }").append("\r\n");
            sb.append("    }").append("\r\n");
            sb.append("    //在模态框对用户完全可见后，清空所有的校验状态，经验证：").append("\r\n");
            sb.append("    // show.bs.modal，shown.bs.modal，hide.bs.modal\t，hidden.bs.modal仅有  shown.bs.modal在功能上没问题，但是视觉上仍不太满意").append("\r\n");
            sb.append("    // 理论上说，最合适的是hidden.bs.modal，当模态框关闭时清空，但实际上未能生效，因此使用了shown.bs.modal").append("\r\n");
            sb.append("    $('#update" + cleanentityClassName+"').on('shown.bs.modal', function () {").append("\r\n");
            sb.append("        $('#validateUpdate"+cleanentityClassName+"Form').bootstrapValidator('resetForm', false);").append("\r\n");
            sb.append("    });").append("\r\n");

            //设置时间框开始-start
            int count = (Integer) c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
            for (int i = 0; i < count; i++) {
                String column_name = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_comment_all = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
                JSONObject column_comment_json = (JSONObject) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
                String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
                try {
                    type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                String power = GeneratorJSONProperties.DEFAULT_power;//设置为默认的权限
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    power = attr.getString(GeneratorJSONProperties.PATH_power);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                String formatter_date_char8 = GeneratorJSONProperties.DEFAULT_formatter_char8;
                String formatter_date_char14 = GeneratorJSONProperties.DEFAULT_formatter_char14;
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    formatter_date_char8 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                    formatter_date_char14 = attr.getString(GeneratorJSONProperties.PATH_formatter);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                formatter_date_char8 = formatter_date_char8.split(";")[0];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个
                formatter_date_char14 = formatter_date_char14.split(";")[0];//第0个为bootstrap的日期格式，第1个为java的日期格式，用分号分割，此处使用第0个

                //如果权限中包含“允许作为增加”//query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
                if (power.indexOf("query-condition:Y") >= 0) {
                    //列-作为查询条件-start
                    if (type.equals(GeneratorJSONProperties.FIELD_type_date)) {
                        //判断是否有queryrange标志
                        {
                            boolean hasout = false;
                            try{
                                JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                                Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                                if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                                    //如果有queryrange条件，则增加_start和_end查询，闭区间
                                    sb.append("    $('#query"+cleanentityClassName+"Form #" + column_name_tf + "').daterangepicker({").append("\r\n");
                                    sb.append("        showAfterOpen:false,").append("\r\n");
                                    sb.append("        //startDate:\"2016-12-03\",").append("\r\n");
                                    sb.append("        //endDate:\"2016-12-06\",").append("\r\n");
                                    sb.append("        //minDate:\"2016-12-02\",").append("\r\n");
                                    sb.append("        //maxDate:\"2016-12-31\",").append("\r\n");
                                    sb.append("        timePicker: false,").append("\r\n");
                                    sb.append("        timePicker24Hour: false,").append("\r\n");
                                    sb.append("        timePickerSeconds: false,").append("\r\n");
                                    sb.append("        locale:{").append("\r\n");
                                    sb.append("            format: 'YYYY-MM-DD'").append("\r\n");
                                    sb.append("        }").append("\r\n");
                                    sb.append("    },function(start, end, label) {").append("\r\n");
                                    sb.append("        console.log(start.toISOString(), end.toISOString(), label);").append("\r\n");
                                    sb.append("    }).change(function(){").append("\r\n");
                                    sb.append("        revalidateDate(\"query"+cleanentityClassName+"Form\",\""+column_name_tf+"\");").append("\r\n");
                                    sb.append("    });").append("\r\n");
                                    hasout=true;
                                }
                            }catch (Exception e){
                                //e.printStackTrace();
                                System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                            }
                            if(!hasout){
                                sb.append("    $('#query"+cleanentityClassName+"Form #" + column_name_tf + "').datepicker({").append("\r\n");
                                sb.append("        language:\"zh-CN\",").append("\r\n");
                                sb.append("        format:\""+formatter_date_char8+"\"").append("\r\n");
                                sb.append("    }).change(function(){").append("\r\n");
                                sb.append("        revalidateDate(\"query"+cleanentityClassName+"Form\",\""+column_name_tf+"\");").append("\r\n");
                                sb.append("    });").append("\r\n");
                            }
                        }
                    }
                    if (type.equals(GeneratorJSONProperties.FIELD_type_datetime)) {

                        //判断是否有queryrange标志
                        {
                            boolean hasout = false;
                            try{
                                JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                                Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                                if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                                    //如果有queryrange条件，则增加_start和_end查询，闭区间
                                    sb.append("    $('#query"+cleanentityClassName+"Form #" + column_name_tf + "').daterangepicker({").append("\r\n");
                                    sb.append("        showAfterOpen:false,").append("\r\n");
                                    sb.append("        //startDate:\"2016-12-03 12:59:59\",").append("\r\n");
                                    sb.append("        //endDate:\"2016-12-06 12:59:59\",").append("\r\n");
                                    sb.append("        //minDate:\"2016-12-02 12:00:00\",").append("\r\n");
                                    sb.append("        //maxDate:\"2016-12-31 12:00:00\",").append("\r\n");
                                    sb.append("        timePicker: true,").append("\r\n");
                                    sb.append("        timePicker24Hour: true,").append("\r\n");
                                    sb.append("        timePickerSeconds: true,").append("\r\n");
                                    sb.append("        locale:{").append("\r\n");
                                    sb.append("            format: 'YYYY-MM-DD HH:mm:ss'").append("\r\n");
                                    sb.append("        }").append("\r\n");
                                    sb.append("    },function(start, end, label) {").append("\r\n");
                                    sb.append("        console.log(start.toISOString(), end.toISOString(), label);").append("\r\n");
                                    sb.append("    }).change(function(){").append("\r\n");
                                    sb.append("        revalidateDate(\"query"+cleanentityClassName+"Form\",\""+column_name_tf+"\");").append("\r\n");
                                    sb.append("    });").append("\r\n");
                                    hasout=true;
                                }
                            }catch (Exception e){
                                //e.printStackTrace();
                                System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                            }
                            if(!hasout){
                                sb.append("    $('#query"+cleanentityClassName+"Form #" + column_name_tf + "').datetimepicker({").append("\r\n");
                                sb.append("        language:\"zh-CN\",").append("\r\n");
                                sb.append("        format:\""+formatter_date_char14+"\",").append("\r\n");
                                sb.append("        minuteStep:1").append("\r\n");
                                sb.append("    }).change(function(){").append("\r\n");
                                sb.append("        revalidateDate(\"query"+cleanentityClassName+"Form\",\""+column_name_tf+"\");").append("\r\n");
                                sb.append("    });").append("\r\n");
                            }
                        }
                    }
                }
                if (power.indexOf("add:Y") >= 0) {
                    //列-作为查询条件-start
                    if (type.equals(GeneratorJSONProperties.FIELD_type_date)) {
                        sb.append("    $('#validateAdd"+cleanentityClassName+"Form #" + column_name_tf + "1').datepicker({").append("\r\n");
                        sb.append("        language:\"zh-CN\",").append("\r\n");
                        sb.append("        format:\""+formatter_date_char8+"\"").append("\r\n");
                        sb.append("    }).change(function(){").append("\r\n");
                        sb.append("        revalidateDate(\"validateAdd"+cleanentityClassName+"Form\",\""+column_name_tf+"1\");").append("\r\n");
                        sb.append("    });").append("\r\n");
                    }
                    if (type.equals(GeneratorJSONProperties.FIELD_type_datetime)) {
                        sb.append("    $('#validateAdd"+cleanentityClassName+"Form #" + column_name_tf + "1').datetimepicker({").append("\r\n");
                        sb.append("        language:\"zh-CN\",").append("\r\n");
                        sb.append("        format:\""+formatter_date_char14+"\",").append("\r\n");
                        sb.append("        minuteStep:1").append("\r\n");
                        sb.append("    }).change(function(){").append("\r\n");
                        sb.append("        revalidateDate(\"validateAdd"+cleanentityClassName+"Form\",\""+column_name_tf+"1\");").append("\r\n");
                        sb.append("    });").append("\r\n");
                    }
                }
                if (power.indexOf("update-show:Y") >= 0 && power.indexOf("update-update:Y") >= 0) {
                    //列-作为查询条件-start
                    if (type.equals(GeneratorJSONProperties.FIELD_type_date)) {
                        sb.append("    $('#validateUpdate"+cleanentityClassName+"Form #" + column_name_tf + "3').datepicker({").append("\r\n");
                        sb.append("        language:\"zh-CN\",").append("\r\n");
                        sb.append("        format:\""+formatter_date_char8+"\"").append("\r\n");
                        sb.append("    }).change(function(){").append("\r\n");
                        sb.append("        revalidateDate(\"validateUpdate"+cleanentityClassName+"Form\",\""+column_name_tf+"3\");").append("\r\n");
                        sb.append("    });").append("\r\n");
                    }
                    if (type.equals(GeneratorJSONProperties.FIELD_type_datetime)) {
                        sb.append("    $('#validateUpdate"+cleanentityClassName+"Form #" + column_name_tf + "3').datetimepicker({").append("\r\n");
                        sb.append("        language:\"zh-CN\",").append("\r\n");
                        sb.append("        format:\""+formatter_date_char14+"\",").append("\r\n");
                        sb.append("        minuteStep:1").append("\r\n");
                        sb.append("    }).change(function(){").append("\r\n");
                        sb.append("        revalidateDate(\"validateUpdate"+cleanentityClassName+"Form\",\""+column_name_tf+"3\");").append("\r\n");
                        sb.append("    });").append("\r\n");
                    }
                }
            }
            //设置时间框结束-end
        }//----------------------------------------------------------------------------------------------------------------
        sb.append("").append("\r\n");
        {//----------------------------------------------------------------------------------------------------------------
            //增加校验开始-start
            sb.append("    //增加" + ((String) c.get(Context.CURR_TABLE_COMMENT)) + "校验").append("\r\n");
            sb.append("    $('#validateAdd" + cleanentityClassName + "Form').bootstrapValidator({").append("\r\n");
            sb.append("        fields : {").append("\r\n");
            int count = (Integer) c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
            boolean isBegin = true;//判断是否是开始
            for (int i = 0; i < count; i++) {
                String column_name = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_comment_all = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
                JSONObject column_comment_json = (JSONObject) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
                String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
                try {
                    type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                if(type.equals("checkbox")){
                    continue;//checkbox不需要校验，checkbox在页面上的存在，是一种：已存在，已默认为全部不选的状态，因此在add和update和query的时候不需要校验
                }
                String power = GeneratorJSONProperties.DEFAULT_power;//设置为默认的权限
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    power = attr.getString(GeneratorJSONProperties.PATH_power);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                String validators = GeneratorJSONProperties.DEFAULT_validators;
                try {
                    Object validate_comment = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_validate);
                    if(validate_comment != null && !((JSONObject)validate_comment).isNullObject()){
                        validators = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_validate).toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析validate异常");
                }
                //如果权限中包含“允许作为增加”，并且规则存在
                if (power.indexOf("add:Y") >= 0 && StringUtils.isNotEmpty(validators)) {
                    if(isBegin==false){//如果不是第一次，则在field前加一个逗号
                        sb.append("            ,").append("\r\n");
                    }
                    isBegin = false;
                    sb.append("            " + column_name_tf + "1 : {").append("\r\n");
                    sb.append("                validators : " +validators).append("\r\n");
                    sb.append("            }").append("\r\n");
                }
            }
            sb.append("        }").append("\r\n");
            sb.append("     }).on('success.form.bv', function(e) {").append("\r\n");
            sb.append("         e.preventDefault();").append("\r\n");
            sb.append("         add"+cleanentityClassName+"();").append("\r\n");
            sb.append("     });").append("\r\n");

            //增加校验结束-end
        }//----------------------------------------------------------------------------------------------------------------
        sb.append("").append("\r\n");
        {//----------------------------------------------------------------------------------------------------------------
            //修改校验开始-start
            sb.append("    //修改" + ((String) c.get(Context.CURR_TABLE_COMMENT)) + "校验").append("\r\n");
            sb.append("    $('#validateUpdate" + cleanentityClassName + "Form').bootstrapValidator({").append("\r\n");
            sb.append("        fields : {").append("\r\n");
            int count = (Integer) c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
            boolean isBegin = true;//判断是否是开始
            for (int i = 0; i < count; i++) {
                String column_name = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_comment_all = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
                JSONObject column_comment_json = (JSONObject) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
                String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
                try {
                    type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                if(type.equals("checkbox")){
                    continue;//checkbox不需要校验，checkbox在页面上的存在，是一种：已存在，已默认为全部不选的状态，因此在add和update和query的时候不需要校验
                }
                String power = GeneratorJSONProperties.DEFAULT_power;//设置为默认的权限
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    power = attr.getString(GeneratorJSONProperties.PATH_power);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                String validators = GeneratorJSONProperties.DEFAULT_validators;
                try {
                    Object validate_comment = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_validate);
                    if(validate_comment != null && !((JSONObject)validate_comment).isNullObject()){
                        validators = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_validate).toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析validate异常");
                }
                //如果权限中包含“允许作为增加”，并且规则存在
                if (power.indexOf("update-update:Y") >= 0 && StringUtils.isNotEmpty(validators)) {
                    if(isBegin==false){//如果不是第一次，则在field前加一个逗号
                        sb.append("            ,").append("\r\n");
                    }
                    isBegin = false;
                    sb.append("            " + column_name_tf + "3 : {").append("\r\n");
                    sb.append("                validators : " +validators).append("\r\n");
                    sb.append("            }").append("\r\n");
                }
            }
            sb.append("        }").append("\r\n");
            sb.append("     }).on('success.form.bv', function(e) {").append("\r\n");
            sb.append("         e.preventDefault();").append("\r\n");
            sb.append("         update"+cleanentityClassName+"();").append("\r\n");
            sb.append("     });").append("\r\n");
            //修改校验结束-end
        }//----------------------------------------------------------------------------------------------------------------

        {//----------------------------------------------------------------------------------------------------------------
            //查询校验开始-start
            sb.append("    //查询" + ((String) c.get(Context.CURR_TABLE_COMMENT)) + "校验").append("\r\n");
            sb.append("    $('#query" + cleanentityClassName + "Form').bootstrapValidator({").append("\r\n");
            sb.append("        fields : {").append("\r\n");
            int count = (Integer) c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
            boolean isBegin = true;//判断是否是开始
            for (int i = 0; i < count; i++) {
                String column_name = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME)).get(i);
                String column_name_tf = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_NAME_TF)).get(i);
                String column_comment = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT)).get(i);
                String column_comment_all = (String) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_ALL)).get(i);
                JSONObject column_comment_json = (JSONObject) ((List) c.get(Context.CURR_TABLE_COLUMNS_COMMENT_JSON)).get(i);
                String type = GeneratorJSONProperties.DEFAULT_type;//设置为默认的Type
                try {
                    type = column_comment_json.getString(GeneratorJSONProperties.PATH_type);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                if(type.equals("checkbox")){
                    continue;//checkbox不需要校验，checkbox在页面上的存在，是一种：已存在，已默认为全部不选的状态，因此在add和update和query的时候不需要校验
                }
                String power = GeneratorJSONProperties.DEFAULT_power;//设置为默认的权限
                try {
                    JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                    power = attr.getString(GeneratorJSONProperties.PATH_power);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                String validators = GeneratorJSONProperties.DEFAULT_validators;
                try {
                    Object validate_comment = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_validate);
                    if(validate_comment != null && !((JSONObject)validate_comment).isNullObject()){
                        validators = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_validate).toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析validate异常");
                }
                //尝试去除为空的限制notEmpty
                try{
                    JSONObject validators_json = JSONObject.fromObject(validators);
                    validators_json.remove("notEmpty");
                    validators = validators_json.toString();
                }catch(Exception e){
                    e.printStackTrace();
                }
                //如果是date范围查询，则需要设置校验规则
                try{

                    if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                        if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                            JSONObject validators_json = JSONObject.fromObject(validators);
                            JSONObject json_regexp = JSONObject.fromObject(GeneratorJSONProperties.DEFAULT_range_regexp_char8);
                            validators_json.put("regexp",json_regexp);
                            validators = validators_json.toString();
                        }
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                        if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")) {
                            JSONObject validators_json = JSONObject.fromObject(validators);
                            JSONObject json_regexp = JSONObject.fromObject(GeneratorJSONProperties.DEFAULT_range_regexp_char14);
                            validators_json.put("regexp", json_regexp);
                            validators = validators_json.toString();
                        }
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }


                //如果权限中包含“允许作为增加”，并且规则存在
                if (power.indexOf("query-condition:Y") >= 0 && StringUtils.isNotEmpty(validators)) {
                    if(isBegin==false){//如果不是第一次，则在field前加一个逗号
                        sb.append("            ,").append("\r\n");
                    }
                    isBegin = false;
                    sb.append("            " + column_name_tf + " : {").append("\r\n");
                    sb.append("                validators : " +validators).append("\r\n");
                    sb.append("            }").append("\r\n");
                }
            }
            sb.append("        }").append("\r\n");
            sb.append("     }).on('success.form.bv', function(e) {").append("\r\n");
            sb.append("         e.preventDefault();").append("\r\n");
            sb.append("         console.log(\"查询校验通过\");").append("\r\n");
            sb.append("     });").append("\r\n");

            //增加校验结束-end
        }//----------------------------------------------------------------------------------------------------------------
        sb.append("").append("\r\n");

        {//----------------------------------------------------------------------------------------------------------------
            //jquery的结束页面加载后执行
            sb.append("});").append("\r\n");
        }//----------------------------------------------------------------------------------------------------------------


        sb.append("").append("\r\n");
        {//----------------------------------------------------------------------------------------------------------------

            //查询数据开始-start
            sb.append("function search"+cleanentityClassName+"(currentPage,queryType,fileName,exportParam){").append("\r\n");
            sb.append("    //没有bootstrapvalidator，则返回").append("\r\n");
            sb.append("    if($('#query"+cleanentityClassName+"Form').data('bootstrapValidator')!=null) {").append("\r\n");
            sb.append("        //对查询框进行校验").append("\r\n");
            sb.append("        $('#query"+cleanentityClassName+"Form').data('bootstrapValidator').validate();").append("\r\n");
            sb.append("        //校验没有通过，则返回").append("\r\n");
            sb.append("        if(!$('#query"+cleanentityClassName+"Form').data('bootstrapValidator').isValid()){").append("\r\n");
            sb.append("            return;").append("\r\n");
            sb.append("        }").append("\r\n");
            sb.append("        //----个性化校验示例开始").append("\r\n");
            sb.append("        //if($('#query\"+cleanentityClassName+\"Form #被验证组件name').val()=='#'){").append("\r\n");
            sb.append("        //    layer.alert('XX禁止查询')").append("\r\n");
            sb.append("        //    //第三个参数为，显示的错误信息所属的validator规则").append("\r\n");
            sb.append("        //    $('#query"+cleanentityClassName+"Form').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');").append("\r\n");
            sb.append("        //    return;").append("\r\n");
            sb.append("        //}").append("\r\n");
            sb.append("        //----个性化校验示例结束").append("\r\n");
            sb.append("    }").append("\r\n");


            sb.append("    var params ={};").append("\r\n");
            sb.append("    if(currentPage == \"undefined\" || currentPage ==\"\" || currentPage == null){").append("\r\n");
            sb.append("        var currentPage_text = $(\"#query"+cleanentityClassName+"currentPage\").val();").append("\r\n");
            sb.append("        if(currentPage_text ==\"undefined\" || currentPage_text ==\"\" || currentPage_text == null){").append("\r\n");
            sb.append("            currentPage_text = \"1\";").append("\r\n");
            sb.append("        }").append("\r\n");
            sb.append("        params.startIndex = currentPage_text;").append("\r\n");
            sb.append("    }else{").append("\r\n");
            sb.append("        params.startIndex = currentPage;").append("\r\n");
            sb.append("    }").append("\r\n");
            sb.append("").append("\r\n");
            sb.append("    var pageSize= $(\"#query"+cleanentityClassName+"pageSize\").val();").append("\r\n");
            sb.append("    if(pageSize==\"undefined\" || pageSize ==\"\" || pageSize == null){").append("\r\n");
            sb.append("        params.pageSize = \"10\";").append("\r\n");
            sb.append("    }else{").append("\r\n");
            sb.append("        params.pageSize = pageSize;").append("\r\n");
            sb.append("    }").append("\r\n");
            sb.append("    if(queryType!=undefined && queryType =='download'){").append("\r\n");
            sb.append("        params.queryType='download';").append("\r\n");
            sb.append("        params.exportParam=exportParam;").append("\r\n");
            sb.append("        params.fileName=fileName;").append("\r\n");
            sb.append("    }").append("\r\n");
            sb.append("").append("\r\n");


            //设置查询条件
            //字段遍历开始
            int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
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
                //如果权限中包含“允许作为增加”
                if(power.indexOf("query-condition:Y")>=0){
                    //列-作为查询条件-start
                    if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                        sb.append("    params." + column_name_tf + " = $(\"#query" + cleanentityClassName + "Form #"+column_name_tf+"\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                        boolean hasout=false;
                        try{
                            JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                            Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                            if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                                //如果有queryrange条件，则增加_start和_end查询，闭区间
                                sb.append("    var "+column_name_tf+"_regexp = new RegExp('^([0-9]{4}-[0-9]{2}-[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2})$');").append("\r\n");
                                sb.append("    var "+column_name_tf+"_value = $(\"#query"+cleanentityClassName+"Form #"+column_name_tf+"\").val();").append("\r\n");
                                sb.append("    if("+column_name_tf+"_regexp.test("+column_name_tf+"_value)){").append("\r\n");
                                sb.append("        var cs = "+column_name_tf+"_regexp.exec("+column_name_tf+"_value);").append("\r\n");
                                sb.append("        params."+column_name_tf+"_start = cs[1]||'';").append("\r\n");
                                sb.append("        params."+column_name_tf+"_end = cs[4]||'';").append("\r\n");
                                sb.append("    }").append("\r\n");
                                sb.append("    ").append("\r\n");
                                hasout = true;
                            }
                        }catch (Exception e){
                            //e.printStackTrace();
                            System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                        }
                        if(!hasout){
                            sb.append("    params." + column_name_tf + " = $(\"#query" + cleanentityClassName + "Form #"+column_name_tf+"\").val();").append("\r\n");
                        }
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                        boolean hasout=false;
                        try{
                            JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                            Object queryrange_o = attr.get(GeneratorJSONProperties.PATH_queryrange);
                            if(queryrange_o!=null  && ((String)queryrange_o).trim().equals("true")){
                                //如果有queryrange条件，则增加_start和_end查询，闭区间
                                sb.append("    var "+column_name_tf+"_regexp = new RegExp('^([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})$');").append("\r\n");
                                sb.append("    var "+column_name_tf+"_value = $(\"#query"+cleanentityClassName+"Form #"+column_name_tf+"\").val();").append("\r\n");
                                sb.append("    if("+column_name_tf+"_regexp.test("+column_name_tf+"_value)){").append("\r\n");
                                sb.append("        var cs = "+column_name_tf+"_regexp.exec("+column_name_tf+"_value);").append("\r\n");
                                sb.append("        params."+column_name_tf+"_start = cs[1]||'';").append("\r\n");
                                sb.append("        params."+column_name_tf+"_end = cs[4]||'';").append("\r\n");
                                sb.append("    }").append("\r\n");
                                sb.append("    ").append("\r\n");
                                hasout = true;
                            }
                        }catch (Exception e){
                            //e.printStackTrace();
                            System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析DATE或者DATETIME是否含有queryrange异常");
                        }
                        if(!hasout){
                            sb.append("    params." + column_name_tf + " = $(\"#query" + cleanentityClassName + "Form #"+column_name_tf+"\").val();").append("\r\n");
                        }
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                        sb.append("    params." + column_name_tf + " = $(\"#query" + cleanentityClassName + "Form #"+column_name_tf+"\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                        sb.append("    params." + column_name_tf + " = $(\"#query" + cleanentityClassName + "Form input[name='"+column_name_tf+"']:checked\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                        sb.append("    //对于checkbox复选框，作为查询条件默认注释掉，否则将导致数据查询不出。如有需要，请根据需求放开").append("\r\n");
                        sb.append("    //params." + column_name_tf + " = getCheckBoxVal(\"query"+cleanentityClassName+"Form\",\""+column_name_tf+"\");").append("\r\n");
                    }
                }
            }
            sb.append("").append("\r\n");
            sb.append("    var jsonStr = JSON.stringify(params);").append("\r\n");
            sb.append("    jsonStr = encodeURI(jsonStr);").append("\r\n");
            sb.append("    jsonStr = encodeURI(jsonStr);").append("\r\n");//编码两次，处理服务器转码问题
            sb.append("    if(queryType!=undefined && queryType =='download'){").append("\r\n");
            sb.append("        window.location.href=webUrl+\"/"+cleanentityClassNameFirstSmall+"/search.dhtml?jsonStr=\"+jsonStr+\"&v=\"+Math.random();").append("\r\n");
            sb.append("        return;").append("\r\n");
            sb.append("    }").append("\r\n");
            sb.append("    ").append("\r\n");







            sb.append("    $.ajax({").append("\r\n");
            sb.append("        type:\"POST\",").append("\r\n");
            sb.append("        url:webUrl+\"/"+cleanentityClassNameFirstSmall+"/search.dhtml\",").append("\r\n");
            sb.append("        timeout:60000,").append("\r\n");
            sb.append("        dataType:'json',").append("\r\n");
            sb.append("        data:\"jsonStr=\"+jsonStr,").append("\r\n");
            sb.append("        success:function(data){").append("\r\n");
            sb.append("            if(data.code == \"success\"){").append("\r\n");
            sb.append("                $(\"#"+cleanentityClassNameFirstSmall+"TR_FIRST\").siblings().remove();").append("\r\n");
            sb.append("                var  resultList=data.rows[0].pageRecords;").append("\r\n");
            sb.append("").append("\r\n");
            sb.append("                //分页").append("\r\n");
            sb.append("                $(\"#query"+cleanentityClassName+"currentPage\").val(data.rows[0].startIndex);").append("\r\n");
            sb.append("                $(\"#query"+cleanentityClassName+"totalPage\").val(data.rows[0].totalPage);").append("\r\n");
            sb.append("                $(\"#query"+cleanentityClassName+"pageSize\").val(data.rows[0].pageSize);").append("\r\n");
            sb.append("                for(var i=0;i<resultList.length;i++){").append("\r\n");
            sb.append("                    var "+cleanentityClassNameFirstSmall+"Tr = $(\"<tr class=\\\""+cleanentityClassNameFirstSmall+"Tr\\\"></tr>\");").append("\r\n");
            sb.append("                    $(\"#"+cleanentityClassNameFirstSmall+"TR_FIRST\").parent().append("+cleanentityClassNameFirstSmall+"Tr);").append("\r\n");
            sb.append("                    "+cleanentityClassNameFirstSmall+"Tr.append(\"" +
                    "<td><input style=\\\"width:23px;\\\" type=\\\"checkbox\\\"/></td>" +
                    //对查询结果进行遍历
                    "<td>\"+(i+1)+\"</td>\")\r\n");
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
                        //如果权限中包含“允许作为增加”
                        if(power.indexOf("query-result:Y")>=0){
                            //列-作为查询结果-start
                            if(type.equals(GeneratorJSONProperties.FIELD_type_select) || type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                                sb.append("                    .append(\"<td>\"+resultList[i]."+column_name_tf+"_name+\"</td>\")").append("\r\n");
                            }else{
                                sb.append("                    .append(\"<td>\"+resultList[i]."+column_name_tf+"+\"</td>\")").append("\r\n");
                            }

                        }
                    }
                    //计算主键字符串
                    String primaryKeyJS = "";
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
                            primaryKeyJS = "resultList[i]."+column_name_tf;
                        }
                        if(isPrimaryKey&&primaryKeyColumn.size()>1){
                            //联合主键
                            if(StringUtils.isNotEmpty(primaryKeyJS)){
                                primaryKeyJS += "+\",\" + ";
                            }
                            primaryKeyJS = primaryKeyJS + " resultList[i]."+column_name_tf;
                        }
                    }
                    //计算出的primaryKeyJS为主键在js层面的获取方式
            sb.append("                    .append(\"<td class=\\\"operateTbl\\\">");
            sb.append("<button class=\\\"btn btn-default btn-sm\\\" onclick=\\\"return edit" + cleanentityClassName + "('\"+" + primaryKeyJS + "+\"');\\\"><i class=\\\"fa fa-edit\\\"></i></button>");
            sb.append("<button class=\\\"btn btn-default btn-sm\\\" onclick=\\\"return  del" + cleanentityClassName + "('\"+" + primaryKeyJS + "+\"');\\\"><i class=\\\"fa fa-trash-o\\\"></i></button>");
            sb.append("<button class=\\\"btn btn-default btn-sm\\\" onclick=\\\"return query" + cleanentityClassName + "Object('\"+" + primaryKeyJS + "+\"');\\\"><i class=\\\"fa fa-search\\\"></i></button>");
            sb.append("</td>\");").append("\r\n");
            sb.append("                }").append("\r\n");
            sb.append("                var currentPage = Number($(\"#query"+cleanentityClassName+"currentPage\").val());").append("\r\n");
            sb.append("                var totalPage   = Number($(\"#query"+cleanentityClassName+"totalPage\").val());").append("\r\n");
            sb.append("                var pageSize    = Number($(\"#query"+cleanentityClassName+"pageSize\").val());").append("\r\n");
            sb.append("                $(\"#query"+cleanentityClassName+"_fy .pager\").ucPager({").append("\r\n");
            sb.append("                    currentPage : currentPage,").append("\r\n");
            sb.append("                    totalPage : totalPage,").append("\r\n");
            sb.append("                    pageSize : pageSize,").append("\r\n");
            sb.append("                    clickCallback: query"+cleanentityClassName+"gopage").append("\r\n");
            sb.append("                });").append("\r\n");
            sb.append("            }else{").append("\r\n");
            sb.append("                layer.alert(data.message);").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        error: function(XMLHttpRequest, textStatus, errorThrown){").append("\r\n");
            sb.append("            layer.alert('系统异常，请稍后重试！');").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        complete : function(XMLHttpRequest,status){").append("\r\n");
            sb.append("            if(status == \"timeout\"){").append("\r\n");
            sb.append("                ajaxTimeoutTest.abort();").append("\r\n");
            sb.append("                layer.alert(\"操作超时！\");").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        }").append("\r\n");
            sb.append("    });").append("\r\n");
            sb.append("}").append("\r\n");
            //查询数据结束-end
        }//----------------------------------------------------------------------------------------------------------------
        sb.append("").append("\r\n");
        {//----------------------------------------------------------------------------------------------------------------
            //gopage开始-start
            sb.append("function query"+cleanentityClassName+"gopage(currentPage) {").append("\r\n");
            sb.append("    search"+cleanentityClassName+"(currentPage);").append("\r\n");
            sb.append("}").append("\r\n");
            //gopage结束-end
        }//----------------------------------------------------------------------------------------------------------------
        sb.append("").append("\r\n");
        {//----------------------------------------------------------------------------------------------------------------
            //增加信息函数开始
            sb.append("function add"+cleanentityClassName+"(){").append("\r\n");
            sb.append("    var url=webUrl+\"/"+controllerPath+"/insert.dhtml\";").append("\r\n");
            sb.append("    var params={};").append("\r\n");
            //属性进行遍历获取
            int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
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
                //如果权限中包含“允许作为增加”
                if(power.indexOf("add:Y")>=0){
                    //列-作为查询条件-start
                    if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateAdd" + cleanentityClassName + "Form #"+column_name_tf+"1\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateAdd" + cleanentityClassName + "Form #"+column_name_tf+"1\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateAdd" + cleanentityClassName + "Form #"+column_name_tf+"1\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateAdd" + cleanentityClassName + "Form #"+column_name_tf+"1\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateAdd" + cleanentityClassName + "Form input[name='"+column_name_tf+"1']:checked\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                        sb.append("    params." + column_name_tf + " = getCheckBoxVal(\"validateAdd"+cleanentityClassName+"Form\",\""+column_name_tf+"1\");").append("\r\n");
                    }
                }
            }

            sb.append("    var jsonStr = JSON.stringify(params);").append("\r\n");
            sb.append("    jsonStr = encodeURI(jsonStr);").append("\r\n");
            sb.append("    $.ajax({").append("\r\n");
            sb.append("        type:\"POST\",").append("\r\n");
            sb.append("        url:url,").append("\r\n");
            sb.append("        timeout:60000,").append("\r\n");
            sb.append("        dataType:'json',").append("\r\n");
            sb.append("        data:\"jsonStr=\"+jsonStr,").append("\r\n");
            sb.append("        success:function(data){").append("\r\n");
            sb.append("            if(data.code == \"success\"){").append("\r\n");
            sb.append("                $('#add"+cleanentityClassName+"').modal('hide');").append("\r\n");
            sb.append("                search"+cleanentityClassName+"('');").append("\r\n");
            sb.append("                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});").append("\r\n");
            sb.append("                $('#validateAdd"+cleanentityClassName+"Form').bootstrapValidator('resetForm', true);").append("\r\n");
            sb.append("            }else{").append("\r\n");
            sb.append("                layer.alert(data.message);").append("\r\n");
            sb.append("                $('#validateAdd" + cleanentityClassName + "Form  button[type=\"submit\"]').attr('disabled',false);").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        error: function(XMLHttpRequest, textStatus, errorThrown){").append("\r\n");
            sb.append("            layer.alert('系统异常，请稍后重试！');").append("\r\n");
            sb.append("                $('#validateAdd"+cleanentityClassName+"Form  button[type=\"submit\"]').attr('disabled',false);").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        complete : function(XMLHttpRequest,status){").append("\r\n");
            sb.append("            if(status == \"timeout\"){").append("\r\n");
            sb.append("                ajaxTimeoutTest.abort();").append("\r\n");
            sb.append("                alert(\"操作超时！\");").append("\r\n");
            sb.append("                $('#validateAdd"+cleanentityClassName+"Form  button[type=\"submit\"]').attr('disabled',false);").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        }").append("\r\n");
            sb.append("    });").append("\r\n");
            sb.append("}").append("\r\n");
            //增加信息函数结束
        }//----------------------------------------------------------------------------------------------------------------
        sb.append("").append("\r\n");
        {//----------------------------------------------------------------------------------------------------------------
            //修改信息函数开始
            sb.append("function update"+cleanentityClassName+"(){").append("\r\n");
            sb.append("    var url=webUrl+\"/"+controllerPath+"/edit.dhtml\";").append("\r\n");
            sb.append("    var params={};").append("\r\n");
            sb.append("    params.id_key = $(\"#validateUpdate" + cleanentityClassName + "Form #id_key\").val();").append("\r\n");
            //属性进行遍历获取
            int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
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
                //如果权限中包含“允许作为修改”
                if(power.indexOf("update-update:Y")>=0){
                    //列-作为查询条件-start
                    if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateUpdate" + cleanentityClassName + "Form #"+column_name_tf+"3\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateUpdate" + cleanentityClassName + "Form #"+column_name_tf+"3\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateUpdate" + cleanentityClassName + "Form #"+column_name_tf+"3\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateUpdate" + cleanentityClassName + "Form #"+column_name_tf+"3\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                        sb.append("    params." + column_name_tf + " = $(\"#validateUpdate" + cleanentityClassName + "Form input[name='"+column_name_tf+"3']:checked\").val();").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                        sb.append("    params." + column_name_tf + " = getCheckBoxVal(\"validateUpdate"+cleanentityClassName+"Form\",\""+column_name_tf+"3\");").append("\r\n");
                    }
                }
            }

            sb.append("    var jsonStr = JSON.stringify(params);").append("\r\n");
            sb.append("    jsonStr = encodeURI(jsonStr);").append("\r\n");
            sb.append("    $.ajax({").append("\r\n");
            sb.append("        type:\"POST\",").append("\r\n");
            sb.append("        url:url,").append("\r\n");
            sb.append("        timeout:60000,").append("\r\n");
            sb.append("        dataType:'json',").append("\r\n");
            sb.append("        data:\"jsonStr=\"+jsonStr,").append("\r\n");
            sb.append("        success:function(data){").append("\r\n");
            sb.append("            if(data.code == \"success\"){").append("\r\n");
            sb.append("                $('#update"+cleanentityClassName+"').modal('hide');").append("\r\n");
            sb.append("                search"+cleanentityClassName+"('');").append("\r\n");
            sb.append("                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});").append("\r\n");
            sb.append("                $('#validateUpdate"+cleanentityClassName+"Form').bootstrapValidator('resetForm', true);").append("\r\n");
            sb.append("            }else{").append("\r\n");
            sb.append("                layer.alert(data.message);").append("\r\n");
            sb.append("                $('#validateUpdate"+cleanentityClassName+"Form  button[type=\"submit\"]').attr('disabled',false);").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        error: function(XMLHttpRequest, textStatus, errorThrown){").append("\r\n");
            sb.append("            layer.alert('系统异常，请稍后重试！');").append("\r\n");
            sb.append("            $('#validateUpdate"+cleanentityClassName+"Form  button[type=\"submit\"]').attr('disabled',false);").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        complete : function(XMLHttpRequest,status){").append("\r\n");
            sb.append("            if(status == \"timeout\"){").append("\r\n");
            sb.append("                ajaxTimeoutTest.abort();").append("\r\n");
            sb.append("                alert(\"操作超时！\");").append("\r\n");
            sb.append("                $('#validateUpdate"+cleanentityClassName+"Form  button[type=\"submit\"]').attr('disabled',false);").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        }").append("\r\n");
            sb.append("    });").append("\r\n");
            sb.append("}").append("\r\n");
            //修改信息函数结束
        }//----------------------------------------------------------------------------------------------------------------


        sb.append("").append("\r\n");


        {//----------------------------------------------------------------------------------------------------------------
            //跳入修改页面-开始
            sb.append("function edit"+cleanentityClassName+"(id_key){").append("\r\n");

            sb.append("    var url=webUrl+\"/"+controllerPath+"/queryObject.dhtml\";").append("\r\n");
            sb.append("    var params={};").append("\r\n");
            sb.append("    params.id_key = id_key;;").append("\r\n");
            sb.append("    var jsonStr = JSON.stringify(params);").append("\r\n");
            sb.append("    jsonStr = encodeURI(jsonStr);").append("\r\n");
            sb.append("    $.ajax({").append("\r\n");
            sb.append("        type:\"POST\",").append("\r\n");
            sb.append("        url:url,").append("\r\n");
            sb.append("        timeout:60000,").append("\r\n");
            sb.append("        dataType:'json',").append("\r\n");
            sb.append("        data:\"jsonStr=\"+jsonStr,").append("\r\n");
            sb.append("        success:function(data){").append("\r\n");
            sb.append("            if(data.code == \"success\"){").append("\r\n");
            sb.append("                $('#update"+cleanentityClassName+"').modal();").append("\r\n");
            sb.append("                $(\"#validateUpdate"+cleanentityClassName+"Form #id_key\").val(data.rows[0].id_key);").append("\r\n");
            sb.append("                var objInfo = data.rows[0]."+entityVarName+";").append("\r\n");
            //对属性仅遍历赋值-开始
            int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
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
                //如果权限中包含“允许作为增加”
                if(power.indexOf("update-show:Y")>=0){
                    //列-作为查询条件-start
                    if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                        sb.append("                $(\"#validateUpdate" + cleanentityClassName + "Form #"+column_name_tf+"3\").val(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                        sb.append("                $(\"#validateUpdate" + cleanentityClassName + "Form #"+column_name_tf+"3\").val(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                        sb.append("                $(\"#validateUpdate" + cleanentityClassName + "Form #"+column_name_tf+"3\").val(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                        sb.append("                $(\"#validateUpdate" + cleanentityClassName + "Form #"+column_name_tf+"3\").val(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                        sb.append("                //使用原生javascript对radio进行操作，避免浏览器不兼容问题").append("\r\n");
                        sb.append("                $(\"#validateUpdate" + cleanentityClassName + "Form input[name='"+column_name_tf+"3'][value='\"+(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":'')+\"']\")[0].checked=true;").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                        sb.append("                setCheckBoxVal(\"validateUpdate" + cleanentityClassName + "Form\",\""+column_name_tf+"3\",(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":''));").append("\r\n");
                    }
                }
            }
            //对属性仅遍历赋值-结束
            sb.append("            }else{").append("\r\n");
            sb.append("                layer.alert(data.message);").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        error: function(XMLHttpRequest, textStatus, errorThrown){").append("\r\n");
            sb.append("            layer.alert('系统异常，请稍后重试！');").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        complete : function(XMLHttpRequest,status){").append("\r\n");
            sb.append("            if(status == \"timeout\"){").append("\r\n");
            sb.append("                ajaxTimeoutTest.abort();").append("\r\n");
            sb.append("                alert(\"操作超时！\");").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        }").append("\r\n");
            sb.append("    });").append("\r\n");
            sb.append("}").append("\r\n");
            //跳入修改页面-结束
        }//----------------------------------------------------------------------------------------------------------------









        sb.append("").append("\r\n");

        {//----------------------------------------------------------------------------------------------------------------
            //跳入detail页面-开始
            sb.append("function query"+cleanentityClassName+"Object(id_key){").append("\r\n");

            sb.append("    var url=webUrl+\"/"+controllerPath+"/queryObject.dhtml\";").append("\r\n");
            sb.append("    var params={};").append("\r\n");
            sb.append("    params.id_key = id_key;;").append("\r\n");
            sb.append("    var jsonStr = JSON.stringify(params);").append("\r\n");
            sb.append("    jsonStr = encodeURI(jsonStr);").append("\r\n");
            sb.append("    $.ajax({").append("\r\n");
            sb.append("        type:\"POST\",").append("\r\n");
            sb.append("        url:url,").append("\r\n");
            sb.append("        timeout:60000,").append("\r\n");
            sb.append("        dataType:'json',").append("\r\n");
            sb.append("        data:\"jsonStr=\"+jsonStr,").append("\r\n");
            sb.append("        success:function(data){").append("\r\n");
            sb.append("            if(data.code == \"success\"){").append("\r\n");
            sb.append("                $('#detail"+cleanentityClassName+"').modal();").append("\r\n");
            sb.append("                $(\"#validateDetail"+cleanentityClassName+"Form #id_key\").val(data.rows[0].id_key);").append("\r\n");
            sb.append("                var objInfo = data.rows[0]."+entityVarName+";").append("\r\n");
            //对属性仅遍历赋值-开始
            int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
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
                //如果权限中包含“允许作为增加”
                if(power.indexOf("detail:Y")>=0){
                    //列-作为查询条件-start
                    if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                        sb.append("                $(\"#validateDetail" + cleanentityClassName + "Form #"+column_name_tf+"2\").val(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                        sb.append("                $(\"#validateDetail" + cleanentityClassName + "Form #"+column_name_tf+"2\").val(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                        sb.append("                $(\"#validateDetail" + cleanentityClassName + "Form #"+column_name_tf+"2\").val(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                        sb.append("                $(\"#validateDetail" + cleanentityClassName + "Form #"+column_name_tf+"2\").val(objInfo."+column_name_tf+"_name!=null?objInfo."+column_name_tf+"_name:'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                        sb.append("                $(\"#validateDetail" + cleanentityClassName + "Form #"+column_name_tf+"2\").val(objInfo."+column_name_tf+"_name!=null?objInfo."+column_name_tf+"_name:'');").append("\r\n");
                    }
                    if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                        sb.append("                setCheckBoxVal(\"validateDetail" + cleanentityClassName + "Form\",\""+column_name_tf+"2\",(objInfo."+column_name_tf+"!=null?objInfo."+column_name_tf+":''));").append("\r\n");
                    }
                }
            }
            //对属性仅遍历赋值-结束
            sb.append("            }else{").append("\r\n");
            sb.append("                layer.alert(data.message);").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        error: function(XMLHttpRequest, textStatus, errorThrown){").append("\r\n");
            sb.append("            layer.alert('系统异常，请稍后重试！');").append("\r\n");
            sb.append("        },").append("\r\n");
            sb.append("        complete : function(XMLHttpRequest,status){").append("\r\n");
            sb.append("            if(status == \"timeout\"){").append("\r\n");
            sb.append("                ajaxTimeoutTest.abort();").append("\r\n");
            sb.append("                alert(\"操作超时！\");").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        }").append("\r\n");
            sb.append("    });").append("\r\n");
            sb.append("}").append("\r\n");
            //跳入detail页面-结束
        }//----------------------------------------------------------------------------------------------------------------


        {//----------------------------------------------------------------------------------------------------------------
            //删除-开始
            sb.append("function del"+cleanentityClassName+"(id_key){").append("\r\n");
            sb.append("    layer.confirm('是否删除？', {").append("\r\n");
            sb.append("        btn: ['确定','取消'] //按钮").append("\r\n");
            sb.append("    }, function(){").append("\r\n");
            sb.append("        var url=webUrl+\"/"+controllerPath+"/del.dhtml\";").append("\r\n");
            sb.append("        var params={};").append("\r\n");
            sb.append("        var currentPage= $(\"#query"+cleanentityClassName+"currentPage\").val();").append("\r\n");
            sb.append("        params.id_key = id_key;;").append("\r\n");
            sb.append("        var jsonStr = JSON.stringify(params);").append("\r\n");
            sb.append("        jsonStr = encodeURI(jsonStr);").append("\r\n");
            sb.append("        $.ajax({").append("\r\n");
            sb.append("            type:\"POST\",").append("\r\n");
            sb.append("            url:url,").append("\r\n");
            sb.append("            timeout:60000,").append("\r\n");
            sb.append("            dataType:'json',").append("\r\n");
            sb.append("            data:\"jsonStr=\"+jsonStr,").append("\r\n");
            sb.append("            success:function(data){").append("\r\n");
            sb.append("                if(data.code == \"success\"){").append("\r\n");
            sb.append("                    layer.msg('删除成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});").append("\r\n");
            sb.append("                    search"+cleanentityClassName+"(currentPage);").append("\r\n");
            sb.append("                }else{").append("\r\n");
            sb.append("                    layer.alert(data.message);").append("\r\n");
            sb.append("                }").append("\r\n");
            sb.append("            },").append("\r\n");
            sb.append("            error: function(XMLHttpRequest, textStatus, errorThrown){").append("\r\n");
            sb.append("                layer.alert('系统异常，请稍后重试！');").append("\r\n");
            sb.append("            },").append("\r\n");
            sb.append("            complete : function(XMLHttpRequest,status){").append("\r\n");
            sb.append("                if(status == \"timeout\"){").append("\r\n");
            sb.append("                    ajaxTimeoutTest.abort();").append("\r\n");
            sb.append("                    alert(\"操作超时！\");").append("\r\n");
            sb.append("                }").append("\r\n");
            sb.append("            }").append("\r\n");
            sb.append("        });").append("\r\n");
            sb.append("    });").append("\r\n");
            sb.append("}").append("\r\n");
            //删除-结束
        }//----------------------------------------------------------------------------------------------------------------
        sb.append("").append("\r\n");
        pw.println(sb.toString());
        pw.close();
    }
}
