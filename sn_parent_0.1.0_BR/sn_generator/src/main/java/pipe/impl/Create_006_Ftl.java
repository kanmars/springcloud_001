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
import java.util.List;

/**
 * Created by baolong on 2016/1/14.
 */
public class Create_006_Ftl implements Pipe {

    public void exec(Context c) throws Exception {
        //创建查询页
        exec_create_001_QUERYPAGE(c);
        //创建新增页
        exec_create_002_ADDPAGE(c);
        //创建详情页
        exec_create_003_DETAILPAGE(c);
        //创建修改页
        exec_create_004_UPDATEPAGE(c);
    }

    public void exec_create_001_QUERYPAGE(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_FTL))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_FTL)+"]创建不成功");
        }
        //准备数据
        String tableName = (String)c.get(Context.CURR_TABLE);//tbl_user_info
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//TblUserInfo
        String cleanentityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//UserInfo
        //去掉Tbl的头部
        if(cleanentityClassName.startsWith("Tbl")){
            cleanentityClassName = cleanentityClassName.substring(3);
        }
        String cleanentityClassNameFirstSmall = FormatUtils.firstSmall(cleanentityClassName);//userInfo
        String ftldirectory = cleanentityClassName.toLowerCase();//userinfo
        //创建子文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_FTL) + File.separator + ftldirectory)){
            throw new Exception("文件夹["+(String) c.get(Context.PATH_FTL) + File.separator + ftldirectory + "]创建不成功");
        }
        String ftlname = ftldirectory;
        String fileName = ftlname+".ftl";//文件名与父路径名相同
        File filePath = new File((String)c.get(Context.PATH_FTL) + File.separator+ftldirectory+File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("<#import \"/component/register.ftl\" as  register/>").append("\r\n");
        sb.append("<#import \"/"+ftldirectory+"/"+ftlname+".ftl\" as  "+ftlname+"/>").append("\r\n");
        sb.append("<#import \"/"+ftldirectory + "/add"+cleanentityClassName+".ftl\" as  add"+cleanentityClassName + "/>").append("\r\n");
        sb.append("<#import \"/"+ftldirectory+"/update"+cleanentityClassName+".ftl\" as  update"+cleanentityClassName+"/>").append("\r\n");
        sb.append("<#import \"/"+ftldirectory+"/detail"+cleanentityClassName+".ftl\" as  detail"+cleanentityClassName+"/>").append("\r\n");
        sb.append("<#include \"/management/resource.ftl\"/>").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("<@"+ftlname+"."+ftlname+"Html/>").append("\r\n");
        sb.append("<@add"+cleanentityClassName+".add"+cleanentityClassName+"Html/>").append("\r\n");
        sb.append("<@detail"+cleanentityClassName+".detail"+cleanentityClassName+"Html/>").append("\r\n");
        sb.append("<@update"+cleanentityClassName+".update"+cleanentityClassName+"Html/>").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("<#macro "+ftlname+"Html>").append("\r\n");
        sb.append("<div style=\"\" class=\"\">").append("\r\n");
        sb.append("    <section class=\"content-header\">").append("\r\n");
        sb.append("        <h1>").append("\r\n");
        sb.append("                "+((String)c.get(Context.CURR_TABLE_COMMENT))+"<small></small>").append("\r\n");
        sb.append("        </h1>").append("\r\n");
        sb.append("    </section>").append("\r\n");
        sb.append("    <!-- Main content -->").append("\r\n");
        sb.append("    <section class=\"content\">").append("\r\n");
        sb.append("        <div class=\"box\">").append("\r\n");

        //头部box-header开始
        sb.append("            <div class=\"box-header\">").append("\r\n");
        sb.append("                <form id=query"+cleanentityClassName+"Form class=\"form-horizontal form-inline\">").append("\r\n");
        //此处需要针对数据类型添加查询条件--start
        //行-start
        sb.append("                    <div class=\"row dis-top\">").append("\r\n");

        //遍历字段
        int count = (Integer)c.get(Context.CURR_TABLE_COLUMN_COUNT);//字段总数
        int count_used = 0;//查询字段的数量
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
            //如果权限中包含“允许作为查询条件”
            if(power.indexOf("query-condition:Y")>=0){
                //对作为列的查询数量进行递增
                count_used++;
                //列-作为查询条件-start
                if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                    sb.append("                        <div class=\"col-md-3\">").append("\r\n");
                    sb.append("                            <label class=\"queryTitle\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-md-8\">").append("\r\n");
                    sb.append("                                <input type=\"text\" class=\"form-control\" placeholder=\"\" id=\""+column_name_tf+"\" name=\""+column_name_tf+"\">").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                    sb.append("                        <div class=\"col-md-3\">").append("\r\n");
                    sb.append("                            <label class=\"queryTitle\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-md-8\">").append("\r\n");
                    sb.append("                                <input type=\"text\" class=\"form-control\" placeholder=\"\" id=\""+column_name_tf+"\" name=\""+column_name_tf+"\">").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                    sb.append("                        <div class=\"col-md-3\">").append("\r\n");
                    sb.append("                            <label class=\"queryTitle\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-md-8\">").append("\r\n");
                    sb.append("                                <input type=\"text\" class=\"form-control\" placeholder=\"\" id=\""+column_name_tf+"\" name=\""+column_name_tf+"\">").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                    sb.append("                        <div class=\"col-md-3\">").append("\r\n");
                    sb.append("                            <label class=\"queryTitle\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-md-8\">").append("\r\n");
                    sb.append("                                <select class=\"form-control select2\" id=\""+column_name_tf+"\" name=\""+column_name_tf+"\">").append("\r\n");
                    //是否有全部查询选择
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object needAll_o = attr.get(GeneratorJSONProperties.PATH_needAll);
                        if(needAll_o!=null  && ((String)needAll_o).trim().equals("true")){
                            sb.append("                                    <option selected=\"selected\" value=\"\">全部</option>").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析SELECT是否含有全部查询按钮needAll异常");
                    }
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                                    <#--静态模式-start-->").append("\r\n");
                            sb.append("                                    <#assign options="+((JSONObject)options_o).toString()+"/>").append("\r\n");
                            sb.append("                                    <#list options?keys as key >").append("\r\n");
                            sb.append("                                        <option value=\"${key}\">${options[key]}</option>").append("\r\n");
                            sb.append("                                    </#list>").append("\r\n");
                            sb.append("                                    <#--静态模式- end -->").append("\r\n");
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
                            sb.append("                                    <#--动态模式-start-->").append("\r\n");
                            sb.append("                                    <#assign options=dicList['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                                    <#list options as item >").append("\r\n");
                            sb.append("                                        <option value=\"${item.codeParam}\">${item.codeValue}</option>").append("\r\n");
                            sb.append("                                    </#list>").append("\r\n");
                            sb.append("                                    <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析SELECT-l1Code-l2Code异常");
                    }
                    sb.append("                                </select>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                    sb.append("                        <div class=\"col-md-3\">").append("\r\n");
                    sb.append("                            <label class=\"queryTitle\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-md-8\">").append("\r\n");
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                                <#--静态模式-start-->").append("\r\n");
                            sb.append("                                <#assign options="+((JSONObject)options_o).toString()+"/>").append("\r\n");
                            sb.append("                                <#list options?keys as key >").append("\r\n");
                            sb.append("                                <label class=\"radio-inline\">").append("\r\n");
                            sb.append("                                    <input type=\"radio\" id=\""+column_name_tf+"_${key}\" name=\""+column_name_tf+"\" value=\"${key}\">${options[key]}").append("\r\n");
                            sb.append("                                </label>").append("\r\n");
                            sb.append("                                </#list>").append("\r\n");
                            sb.append("                                <#--静态模式- end -->").append("\r\n");
                            sb.append("                                <div class=\"clearfix\"></div>").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析radio-options异常");
                    }
                    //动态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                        Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                        if(l1Code!=null&&l2Code!=null){
                            sb.append("                                <#--动态模式-start-->").append("\r\n");
                            sb.append("                                <#assign options=dicList['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                                <#list options as item >").append("\r\n");
                            sb.append("                                <label class=\"radio-inline\">").append("\r\n");
                            sb.append("                                    <input type=\"radio\" id=\""+column_name_tf+"_${item.codeParam}\" name=\""+column_name_tf+"\" value=\"${item.codeParam}\">${item.codeValue}").append("\r\n");
                            sb.append("                                </label>").append("\r\n");
                            sb.append("                                </#list>").append("\r\n");
                            sb.append("                                <#--动态模式- end -->").append("\r\n");
                            sb.append("                                <div class=\"clearfix\"></div>").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析radio-l1Code-l2Code异常");
                    }
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                        <#--静态模式-start-->").append("\r\n");
                            sb.append("                        <#assign options="+((JSONArray)options_o).toString()+"/>").append("\r\n");
                            sb.append("                        <@register.checkbox form=\"query"+cleanentityClassName+"Form\" name=\""+column_name_tf+"\" label=\""+column_comment+"\" options=options value=\"\"").append("\r\n");
                            sb.append("                        class1=\"col-md-3\" " +
                                    "class2=\"queryTitle\" " +
                                    "class3=\"col-md-8\" " +
                                    "class4=\"checkbox-inline\" " +
                                    "class5=\"inline1\" " +
                                    "readOnly=\"false\"/>").append("\r\n");
                            sb.append("                        <#--静态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析checkbox-options异常");
                    }
                    //动态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                        Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                        if(l1Code!=null&&l2Code!=null){
                            sb.append("                        <#--动态模式-start-->").append("\r\n");
                            sb.append("                        <#assign options=dicCheckbox['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                        <@register.checkbox form=\"query"+cleanentityClassName+"Form\" name=\""+column_name_tf+"\" label=\""+column_comment+"\" options=options value=\"\"").append("\r\n");
                            sb.append("                        class1=\"col-md-3\" " +
                                    "class2=\"queryTitle\" " +
                                    "class3=\"col-md-8\" " +
                                    "class4=\"checkbox-inline\" " +
                                    "class5=\"inline1\" " +
                                    "readOnly=\"false\"/>").append("\r\n");
                            sb.append("                        <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析checkbox-l1Code-l2Code异常");
                    }
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_textarea)){
                    sb.append("                        <div class=\"col-md-3\">").append("\r\n");
                    sb.append("                            <label class=\"queryTitle\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-md-8\">").append("\r\n");
                    sb.append("                                <input type=\"text\" class=\"form-control\" placeholder=\"\" id=\""+column_name_tf+"\" name=\""+column_name_tf+"\">").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                //列-作为查询条件-end
                //如果是当前数量已经是第四个，并且不是0，并且不是最后一个，则显示一个换行
                if(count_used%4==0 && count_used>0 && (count_used <=count)){
                    sb.append("                    </div>").append("\r\n");
                    sb.append("                    <div class=\"row dis-top\">").append("\r\n");
                }
            }
        }
        //插入查询按钮//count为列的总数，count_used为已作为查询条件的列的总数
        for(int i=0;i<(4-1-count_used%4);i++){
            sb.append("                        <div class=\"col-md-3\">").append("\r\n");
            sb.append("                        </div>").append("\r\n");
        }
        //添加查询按钮
        sb.append("                        <div class=\"col-md-3\">").append("\r\n");
        sb.append("                            <div class=\"searchDiv\">").append("\r\n");
        sb.append("                                <button type=\"button\" class=\"btn btn-info searchBtn\" onclick=\"search"+cleanentityClassName+"('')\">搜索</button>").append("\r\n");
        String downloadStr = "";
        //生成下载字符串
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
            //如果权限中包含“允许作为查询条件”
            if(power.indexOf("query-result:Y")>=0){
                if(StringUtils.isNotEmpty(downloadStr)){
                    downloadStr+=",";
                }
                downloadStr+=column_name_tf+":"+column_comment;
            }
        }
        sb.append("                                <button type=\"button\" class=\"btn btn-info searchBtn\" onclick=\"search"+cleanentityClassName+"('','download','download.xls','"
                +downloadStr
                +"')\">下载</button>").append("\r\n");


        sb.append("                            </div>").append("\r\n");
        sb.append("                        </div>").append("\r\n");

        sb.append("                    </div>").append("\r\n");
        //行-end
        //此处需要针对数据类型添加查询条件--end
        sb.append("                </form>").append("\r\n");
        sb.append("            </div>").append("\r\n");
        sb.append("").append("\r\n");
        //头部box-header结束

        //查询结果-box-body开始
        sb.append("            <div class=\"box-body\">").append("\r\n");
        sb.append("                <div style=\"width:50px;margin:10px 0px;\"><button class=\"btn btn-block btn-success\" data-toggle=\"modal\" data-target=\"#add"+cleanentityClassName+"\">添加</button></div>").append("\r\n");
        sb.append("                <table id=\""+cleanentityClassNameFirstSmall+"Tbl\" class=\"table table-bordered\">").append("\r\n");
        sb.append("                    <tr id=\""+cleanentityClassNameFirstSmall+"TR_FIRST\">").append("\r\n");
        sb.append("                        <th></th>").append("\r\n");
        sb.append("                        <th>序号</th>").append("\r\n");

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
            //如果权限中包含“允许作为查询条件”
            if(power.indexOf("query-result:Y")>=0){
                //列-作为查询条件-start
                sb.append("                        <th>"+column_comment+"</th>").append("\r\n");
                //列-作为查询条件-end
            }
        }
        sb.append("                        <th>操作</th>").append("\r\n");
        sb.append("                    </tr>").append("\r\n");
        sb.append("                </table>").append("\r\n");
        sb.append("                <div id=\"page1\"></div>").append("\r\n");
        sb.append("            </div>").append("\r\n");
        sb.append("            <!-- /.box-body -->").append("\r\n");
        //查询结果-box-body结束

        //分页-start
        sb.append("").append("\r\n");
        sb.append("            <!--分页 start-->").append("\r\n");
        sb.append("            <div id=\"query"+cleanentityClassName+"_fy\" style=\"width: 100%;\" class=\"gmPagination gmPagination-mini\">").append("\r\n");
        sb.append("                <div style=\"float:left;margin: 10px;\">").append("\r\n");
        sb.append("                    <select id=\"query"+cleanentityClassName+"pageSize\">").append("\r\n");
        sb.append("                        <option selected=\"selected\">10</option>").append("\r\n");
        sb.append("                        <option>20</option>").append("\r\n");
        sb.append("                        <option>50</option>").append("\r\n");
        sb.append("                    </select>").append("\r\n");
        sb.append("                </div>").append("\r\n");
        sb.append("                <div style=\"margin: 10px; float: right\" class=\"pager clearfix\"\tid=\"commonPager\">").append("\r\n");
        sb.append("                    <a href=\"javascript:void(0);\" class=\"prev disable\">&nbsp;&lt;<s\tclass=\"icon-prev\"></s><i></i></a> ").append("\r\n");
        sb.append("                    <span class=\"cur\">1</span> <a href=\"javascript:void(0);\" class=\"next disable\">&gt;<s class=\"icon-next\"></s><i></i></a> ").append("\r\n");
        sb.append("                    <label class=\"txt-wrap\"\tfor=\"pagenum\">到<input type=\"text\" value=\"1\" bnum=\"0\" class=\"txt\" id=\"pNum\">页</label> ").append("\r\n");
        sb.append("                    <a class=\"btn\" zdx=\"nBtn\" href=\"javascript:void(0)\">确定</a>").append("\r\n");
        sb.append("                </div>").append("\r\n");
        sb.append("            </div>").append("\r\n");
        sb.append("            <!--分页 end-->").append("\r\n");
        sb.append("").append("\r\n");
        //分页-end


        sb.append("        </div>").append("\r\n");//box-DIV的结尾
        sb.append("        <!-- /.box -->").append("\r\n");
        sb.append("    </section>").append("\r\n");
        sb.append("</div>").append("\r\n");
        sb.append("<div>").append("\r\n");
        sb.append("    <input type=\"hidden\" id=\"query"+cleanentityClassName+"currentPage\" />").append("\r\n");
        sb.append("    <input type=\"hidden\" id=\"query"+cleanentityClassName+"totalPage\" />").append("\r\n");
        sb.append("</div>").append("\r\n");


        sb.append("<script type=\"text/javascript\" src=\"${props('resourceUrl')}/js/"+ftldirectory+"/"+ftldirectory+".js\"></script>").append("\r\n");
        sb.append("</#macro>").append("\r\n");
        sb.append("<style>").append("\r\n");
        sb.append("    #"+cleanentityClassNameFirstSmall+"Tbl tr .btn{display:left;margin-right:5px;padding:2.5px 6px;}").append("\r\n");
        sb.append("    .title{padding-right:0px;text-align:right;margin:0px;height:34px;line-height:34px;}").append("\r\n");
        sb.append("    .redFont{color:red;display:inline-block;width:15px;text-align:center;}").append("\r\n");
        sb.append("    .modal-dialog{margin:165px auto;}").append("\r\n");
        sb.append("</style>").append("\r\n");

        pw.println(sb.toString());
        pw.close();
    }

    public void exec_create_002_ADDPAGE(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_FTL))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_FTL)+"]创建不成功");
        }
        //准备数据
        String tableName = (String)c.get(Context.CURR_TABLE);//tbl_user_info
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//TblUserInfo
        String cleanentityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//UserInfo
        //去掉Tbl的头部
        if(cleanentityClassName.startsWith("Tbl")){
            cleanentityClassName = cleanentityClassName.substring(3);
        }
        String cleanentityClassNameFirstSmall = FormatUtils.firstSmall(cleanentityClassName);//userInfo
        String ftldirectory = cleanentityClassName.toLowerCase();//userinfo
        //创建子文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_FTL) + File.separator + ftldirectory)){
            throw new Exception("文件夹["+(String) c.get(Context.PATH_FTL) + File.separator + ftldirectory + "]创建不成功");
        }
        String ftlname = "add"+cleanentityClassName;
        String fileName = ftlname+".ftl";//文件名与父路径名相同
        File filePath = new File((String)c.get(Context.PATH_FTL) + File.separator+ftldirectory+File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("<#import \"/component/register.ftl\" as  register/>").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("<#macro add"+cleanentityClassName+"Html>").append("\r\n");
        sb.append("    <div class=\"modal fade\" id=\"add"+cleanentityClassName+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">").append("\r\n");
        sb.append("        <div class=\"modal-dialog\">").append("\r\n");
        sb.append("            <div class=\"modal-content\">").append("\r\n");
        sb.append("                <form id=\"validateAdd"+cleanentityClassName+"Form\" method=\"post\" class=\"form-horizontal\">").append("\r\n");
        sb.append("                    <div class=\"modal-header\">").append("\r\n");
        sb.append("                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>").append("\r\n");
        sb.append("                        <h4 class=\"modal-title\" id=\"myModalLabel\">新增"+((String)c.get(Context.CURR_TABLE_COMMENT))+"</h4>").append("\r\n");
        sb.append("                    </div>").append("\r\n");
        sb.append("                    <div class=\"modal-body\">").append("\r\n");

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
            if(power.indexOf("add:Y")>=0){
                //列-作为查询条件-start
                if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"1\" name=\""+column_name_tf+"1\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"1\" name=\""+column_name_tf+"1\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"1\" name=\""+column_name_tf+"1\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <select class=\"form-control select2\" id=\""+column_name_tf+"1\" name=\""+column_name_tf+"1\">").append("\r\n");
                    //是否有全部查询选择
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object needAll_o = attr.get(GeneratorJSONProperties.PATH_needAll);
                        if(needAll_o!=null  && ((String)needAll_o).trim().equals("true")){
                            sb.append("                                    <option selected=\"selected\" value=\"\">全部</option>").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析SELECT是否含有全部查询按钮needAll异常");
                    }
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                                    <#--静态模式-start-->").append("\r\n");
                            sb.append("                                    <#assign options="+((JSONObject)options_o).toString()+"/>").append("\r\n");
                            sb.append("                                    <#list options?keys as key >").append("\r\n");
                            sb.append("                                        <option value=\"${key}\">${options[key]}</option>").append("\r\n");
                            sb.append("                                    </#list>").append("\r\n");
                            sb.append("                                    <#--静态模式- end -->").append("\r\n");
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
                            sb.append("                                    <#--动态模式-start-->").append("\r\n");
                            sb.append("                                    <#assign options=dicList['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                                    <#list options as item >").append("\r\n");
                            sb.append("                                        <option value=\"${item.codeParam}\">${item.codeValue}</option>").append("\r\n");
                            sb.append("                                    </#list>").append("\r\n");
                            sb.append("                                    <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析SELECT-l1Code-l2Code异常");
                    }
                    sb.append("                                </select>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                                <#--静态模式-start-->").append("\r\n");
                            sb.append("                                <#assign options="+((JSONObject)options_o).toString()+"/>").append("\r\n");
                            sb.append("                                <#list options?keys as key >").append("\r\n");
                            sb.append("                                <label class=\"radio-inline\">").append("\r\n");
                            sb.append("                                    <input type=\"radio\" id=\""+column_name_tf+"1_${key}\" name=\""+column_name_tf+"1\" value=\"${key}\">${options[key]}").append("\r\n");
                            sb.append("                                </label>").append("\r\n");
                            sb.append("                                </#list>").append("\r\n");
                            sb.append("                                <#--静态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析radio-options异常");
                    }
                    //动态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                        Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                        if(l1Code!=null&&l2Code!=null){
                            sb.append("                                <#--动态模式-start-->").append("\r\n");
                            sb.append("                                <#assign options=dicList['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                                <#list options as item >").append("\r\n");
                            sb.append("                                <label class=\"radio-inline\">").append("\r\n");
                            sb.append("                                    <input type=\"radio\" id=\""+column_name_tf+"1_${item.codeParam}\" name=\""+column_name_tf+"1\" value=\"${item.codeParam}\">${item.codeValue}").append("\r\n");
                            sb.append("                                </label>").append("\r\n");
                            sb.append("                                </#list>").append("\r\n");
                            sb.append("                                <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析radio-l1Code-l2Code异常");
                    }
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                        <#--静态模式-start-->").append("\r\n");
                            sb.append("                        <#assign options="+((JSONArray)options_o).toString()+"/>").append("\r\n");
                            sb.append("                        <@register.checkbox form=\"validateAdd"+cleanentityClassName+"Form\" name=\""+column_name_tf+"1\" label=\""+column_comment+"\" options=options value=\"\"").append("\r\n");
                            sb.append("                        class1=\"form-group\" " +
                                    "class2=\"col-lg-3 title\" " +
                                    "class3=\"col-lg-9\" " +
                                    "class4=\"checkbox-inline\" " +
                                    "class5=\"inline1\" " +
                                    "readOnly=\"false\"/>").append("\r\n");
                            sb.append("                        <#--静态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析checkbox-options异常");
                    }
                    //动态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                        Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                        if(l1Code!=null&&l2Code!=null){
                            sb.append("                        <#--动态模式-start-->").append("\r\n");
                            sb.append("                        <#assign options=dicCheckbox['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                        <@register.checkbox form=\"validateAdd"+cleanentityClassName+"Form\" name=\""+column_name_tf+"1\" label=\""+column_comment+"\" options=options value=\"\"").append("\r\n");
                            sb.append("                        class1=\"form-group\" " +
                                    "class2=\"col-lg-3 title\" " +
                                    "class3=\"col-lg-9\" " +
                                    "class4=\"checkbox-inline\" " +
                                    "class5=\"inline1\" " +
                                    "readOnly=\"false\"/>").append("\r\n");
                            sb.append("                        <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析checkbox-l1Code-l2Code异常");
                    }
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_textarea)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <textarea id=\""+column_name_tf+"1\" name=\""+column_name_tf+"1\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\"></textarea>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
            }
        }
        //字段遍历结束

        sb.append("                    </div>").append("\r\n");
        sb.append("                    <div class=\"modal-footer\">").append("\r\n");
        sb.append("                        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>").append("\r\n");
        sb.append("                        <button type=\"submit\" class=\"btn btn-primary\">提交</button>").append("\r\n");
        sb.append("                    </div>").append("\r\n");
        sb.append("                </form>").append("\r\n");
        sb.append("            </div>").append("\r\n");
        sb.append("        </div>").append("\r\n");
        sb.append("    </div>").append("\r\n");
        sb.append("</#macro>").append("\r\n");
        sb.append("").append("\r\n");
        pw.println(sb.toString());
        pw.close();
    }

    public void exec_create_003_DETAILPAGE(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_FTL))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_FTL)+"]创建不成功");
        }
        //准备数据
        String tableName = (String)c.get(Context.CURR_TABLE);//tbl_user_info
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//TblUserInfo
        String cleanentityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//UserInfo
        //去掉Tbl的头部
        if(cleanentityClassName.startsWith("Tbl")){
            cleanentityClassName = cleanentityClassName.substring(3);
        }
        String cleanentityClassNameFirstSmall = FormatUtils.firstSmall(cleanentityClassName);//userInfo
        String ftldirectory = cleanentityClassName.toLowerCase();//userinfo
        //创建子文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_FTL) + File.separator + ftldirectory)){
            throw new Exception("文件夹["+(String) c.get(Context.PATH_FTL) + File.separator + ftldirectory + "]创建不成功");
        }
        String ftlname = "detail"+cleanentityClassName;
        //String controllerPath = FormatUtils.firstSmall(cleanentityClassName);
        //String logicClassName = cleanentityClassName + "Logic";
        //String logicVarName = FormatUtils.firstSmall(cleanentityClassName + "Logic");
        String fileName = ftlname+".ftl";//文件名与父路径名相同
        File filePath = new File((String)c.get(Context.PATH_FTL) + File.separator+ftldirectory+File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("<#import \"/component/register.ftl\" as  register/>").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("<#macro detail"+cleanentityClassName+"Html>").append("\r\n");
        sb.append("    <div class=\"modal fade\" id=\"detail"+cleanentityClassName+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">").append("\r\n");
        sb.append("        <div class=\"modal-dialog\">").append("\r\n");
        sb.append("            <div class=\"modal-content\">").append("\r\n");
        sb.append("                <form id=\"validateDetail"+cleanentityClassName+"Form\" method=\"post\" class=\"form-horizontal\">").append("\r\n");
        sb.append("                    <input type=\"hidden\" id=\"id_key\" name=\"id_key\" value=\"\" />").append("\r\n");
        sb.append("                    <div class=\"modal-header\">").append("\r\n");
        sb.append("                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>").append("\r\n");
        sb.append("                        <h4 class=\"modal-title\" id=\"myModalLabel\">查看"+((String)c.get(Context.CURR_TABLE_COMMENT))+"</h4>").append("\r\n");
        sb.append("                    </div>").append("\r\n");
        sb.append("                    <div class=\"modal-body\">").append("\r\n");

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
            if(power.indexOf("detail:Y")>=0){
                //列-作为查询条件-start
                if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"2\" name=\""+column_name_tf+"2\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" disabled=\"disabled\"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"2\" name=\""+column_name_tf+"2\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" disabled=\"disabled\"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"2\" name=\""+column_name_tf+"2\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" disabled=\"disabled\"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"2\" name=\""+column_name_tf+"2\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" disabled=\"disabled\"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"2\" name=\""+column_name_tf+"2\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" disabled=\"disabled\"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                        <#--静态模式-start-->").append("\r\n");
                            sb.append("                        <#assign options="+((JSONArray)options_o).toString()+"/>").append("\r\n");
                            sb.append("                        <@register.checkbox form=\"validateDetail"+cleanentityClassName+"Form\" name=\""+column_name_tf+"2\" label=\""+column_comment+"\" options=options value=\"\"").append("\r\n");
                            sb.append("                        class1=\"form-group\" " +
                                    "class2=\"col-lg-3 title\" " +
                                    "class3=\"col-lg-9\" " +
                                    "class4=\"checkbox-inline\" " +
                                    "class5=\"inline1\" " +
                                    "readOnly=\"true\"/>").append("\r\n");
                            sb.append("                        <#--静态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析checkbox-options异常");
                    }
                    //动态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                        Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                        if(l1Code!=null&&l2Code!=null){
                            sb.append("                        <#--动态模式-start-->").append("\r\n");
                            sb.append("                        <#assign options=dicCheckbox['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                        <@register.checkbox form=\"validateDetail"+cleanentityClassName+"Form\" name=\""+column_name_tf+"2\" label=\""+column_comment+"\" options=options value=\"\"").append("\r\n");
                            sb.append("                        class1=\"form-group\" " +
                                    "class2=\"col-lg-3 title\" " +
                                    "class3=\"col-lg-9\" " +
                                    "class4=\"checkbox-inline\" " +
                                    "class5=\"inline1\" " +
                                    "readOnly=\"true\"/>").append("\r\n");
                            sb.append("                        <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析checkbox-l1Code-l2Code异常");
                    }
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_textarea)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\">"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <textarea id=\""+column_name_tf+"2\" name=\""+column_name_tf+"2\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" disabled=\"disabled\"></textarea>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
            }
        }
        //字段遍历结束

        sb.append("                    </div>").append("\r\n");
        sb.append("                    <div class=\"modal-footer\">").append("\r\n");
        sb.append("                        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>").append("\r\n");
        sb.append("                    </div>").append("\r\n");
        sb.append("                </form>").append("\r\n");
        sb.append("            </div>").append("\r\n");
        sb.append("        </div>").append("\r\n");
        sb.append("    </div>").append("\r\n");
        sb.append("</#macro>").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("").append("\r\n");


        pw.println(sb.toString());
        pw.close();
    }

    public void exec_create_004_UPDATEPAGE(Context c) throws Exception {
        //创建文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_FTL))){
            throw new Exception("文件夹["+(String)c.get(Context.PATH_FTL)+"]创建不成功");
        }
        //准备数据
        String tableName = (String)c.get(Context.CURR_TABLE);//tbl_user_info
        String entityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//TblUserInfo
        String cleanentityClassName = FormatUtils.firstBig(FormatUtils.formatDBNameToVarName(tableName));//UserInfo
        //去掉Tbl的头部
        if(cleanentityClassName.startsWith("Tbl")){
            cleanentityClassName = cleanentityClassName.substring(3);
        }
        String cleanentityClassNameFirstSmall = FormatUtils.firstSmall(cleanentityClassName);//userInfo
        String ftldirectory = cleanentityClassName.toLowerCase();//userinfo
        //创建子文件夹
        if(!FileUtils.mkdirs((String) c.get(Context.PATH_FTL) + File.separator + ftldirectory)){
            throw new Exception("文件夹["+(String) c.get(Context.PATH_FTL) + File.separator + ftldirectory + "]创建不成功");
        }
        String ftlname = "update"+cleanentityClassName;
        //String controllerPath = FormatUtils.firstSmall(cleanentityClassName);
        //String logicClassName = cleanentityClassName + "Logic";
        //String logicVarName = FormatUtils.firstSmall(cleanentityClassName + "Logic");
        String fileName = ftlname+".ftl";//文件名与父路径名相同
        File filePath = new File((String)c.get(Context.PATH_FTL) + File.separator+ftldirectory+File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath),(String)c.get(Context.FILE_CHARSET)));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("<#import \"/component/register.ftl\" as  register/>").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("<#macro update"+cleanentityClassName+"Html>").append("\r\n");
        sb.append("    <div class=\"modal fade\" id=\"update"+cleanentityClassName+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">").append("\r\n");
        sb.append("        <div class=\"modal-dialog\">").append("\r\n");
        sb.append("            <div class=\"modal-content\">").append("\r\n");
        sb.append("                <form id=\"validateUpdate"+cleanentityClassName+"Form\" method=\"post\" class=\"form-horizontal\">").append("\r\n");
        sb.append("                    <input type=\"hidden\" id=\"id_key\" name=\"id_key\" value=\"\" />").append("\r\n");
        sb.append("                    <div class=\"modal-header\">").append("\r\n");
        sb.append("                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>").append("\r\n");
        sb.append("                        <h4 class=\"modal-title\" id=\"myModalLabel\">修改"+((String)c.get(Context.CURR_TABLE_COMMENT))+"</h4>").append("\r\n");
        sb.append("                    </div>").append("\r\n");
        sb.append("                    <div class=\"modal-body\">").append("\r\n");

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
            if(power.indexOf("update-show:Y")>=0||power.indexOf("update-update:Y")>=0){
                boolean canupdate = false;//默认不可修改
                if(power.indexOf("update-update:Y")>=0)canupdate=true;
                //列-作为查询条件-start
                if(type.equals(GeneratorJSONProperties.FIELD_type_input)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"3\" name=\""+column_name_tf+"3\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" "+(canupdate?"":"disabled=\"disabled\"")+"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_date)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"3\" name=\""+column_name_tf+"3\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" "+(canupdate?"":"disabled=\"disabled\"")+"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_datetime)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <input id=\""+column_name_tf+"3\" name=\""+column_name_tf+"3\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" "+(canupdate?"":"disabled=\"disabled\"")+"/>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_select)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <select class=\"form-control select2\" id=\""+column_name_tf+"3\" name=\""+column_name_tf+"3\"  "+(canupdate?"":"disabled=\"disabled\"")+">").append("\r\n");
                    //是否有全部查询选择
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object needAll_o = attr.get(GeneratorJSONProperties.PATH_needAll);
                        if(needAll_o!=null  && ((String)needAll_o).trim().equals("true")){
                            sb.append("                                    <option selected=\"selected\" value=\"\">全部</option>").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename["+tableName+"],columnName["+column_name+"],columnComment_ALL["+column_comment_all+"]--解析SELECT是否含有全部查询按钮needAll异常");
                    }
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                                    <#--静态模式-start-->").append("\r\n");
                            sb.append("                                    <#assign options="+((JSONObject)options_o).toString()+"/>").append("\r\n");
                            sb.append("                                    <#list options?keys as key >").append("\r\n");
                            sb.append("                                        <option value=\"${key}\">${options[key]}</option>").append("\r\n");
                            sb.append("                                    </#list>").append("\r\n");
                            sb.append("                                    <#--静态模式- end -->").append("\r\n");
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
                            sb.append("                                    <#--动态模式-start-->").append("\r\n");
                            sb.append("                                    <#assign options=dicList['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                                    <#list options as item >").append("\r\n");
                            sb.append("                                        <option value=\"${item.codeParam}\">${item.codeValue}</option>").append("\r\n");
                            sb.append("                                    </#list>").append("\r\n");
                            sb.append("                                    <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析SELECT-l1Code-l2Code异常");
                    }
                    sb.append("                                </select>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_radio)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                                <#--静态模式-start-->").append("\r\n");
                            sb.append("                                <#assign options="+((JSONObject)options_o).toString()+"/>").append("\r\n");
                            sb.append("                                <#list options?keys as key >").append("\r\n");
                            sb.append("                                <label class=\"radio-inline\">").append("\r\n");
                            sb.append("                                    <input type=\"radio\" id=\""+column_name_tf+"3_${key}\" name=\""+column_name_tf+"3\" value=\"${key}\" "+(canupdate?"":"disabled=\"disabled\"")+">${options[key]}").append("\r\n");
                            sb.append("                                </label>").append("\r\n");
                            sb.append("                                </#list>").append("\r\n");
                            sb.append("                                <#--静态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析radio-options异常");
                    }
                    //动态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                        Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                        if(l1Code!=null&&l2Code!=null){
                            sb.append("                                <#--动态模式-start-->").append("\r\n");
                            sb.append("                                <#assign options=dicList['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                                <#list options as item >").append("\r\n");
                            sb.append("                                <label class=\"radio-inline\">").append("\r\n");
                            sb.append("                                    <input type=\"radio\" id=\""+column_name_tf+"3_${item.codeParam}\" name=\""+column_name_tf+"3\" value=\"${item.codeParam}\" "+(canupdate?"":"disabled=\"disabled\"")+">${item.codeValue}").append("\r\n");
                            sb.append("                                </label>").append("\r\n");
                            sb.append("                                </#list>").append("\r\n");
                            sb.append("                                <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析radio-l1Code-l2Code异常");
                    }
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_checkbox)){
                    //静态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object options_o = attr.get(GeneratorJSONProperties.PATH_options);
                        if(options_o!=null){
                            sb.append("                        <#--静态模式-start-->").append("\r\n");
                            sb.append("                        <#assign options="+((JSONArray)options_o).toString()+"/>").append("\r\n");
                            sb.append("                        <@register.checkbox form=\"validateUpdate"+cleanentityClassName+"Form\" name=\""+column_name_tf+"3\" label=\""+column_comment+"\" options=options value=\"\"").append("\r\n");
                            sb.append("                        class1=\"form-group\" " +
                                    "class2=\"col-lg-3 title\" " +
                                    "class3=\"col-lg-9\" " +
                                    "class4=\"checkbox-inline\" " +
                                    "class5=\"inline1\" " +
                                    "readOnly=\""+(canupdate?"false":"true")+"\"/>").append("\r\n");
                            sb.append("                        <#--静态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析checkbox-options异常");
                    }
                    //动态模式
                    try{
                        JSONObject attr = column_comment_json.getJSONObject(GeneratorJSONProperties.PATH_attr);
                        Object l1Code = attr.get(GeneratorJSONProperties.PATH_l1Code);
                        Object l2Code = attr.get(GeneratorJSONProperties.PATH_l2Code);
                        if(l1Code!=null&&l2Code!=null){
                            sb.append("                        <#--动态模式-start-->").append("\r\n");
                            sb.append("                        <#assign options=dicCheckbox['"+l1Code+","+l2Code+"']/>").append("\r\n");
                            sb.append("                        <@register.checkbox form=\"validateUpdate"+cleanentityClassName+"Form\" name=\""+column_name_tf+"3\" label=\""+column_comment+"\" options=options value=\"\"").append("\r\n");
                            sb.append("                        class1=\"form-group\" " +
                                    "class2=\"col-lg-3 title\" " +
                                    "class3=\"col-lg-9\" " +
                                    "class4=\"checkbox-inline\" " +
                                    "class5=\"inline1\" " +
                                    "readOnly=\""+(canupdate?"false":"true")+"\"/>").append("\r\n");
                            sb.append("                        <#--动态模式- end -->").append("\r\n");
                        }
                    }catch (Exception e){
                        //e.printStackTrace();
                        System.out.println("tablename[" + tableName + "],columnName[" + column_name + "],columnComment_ALL[" + column_comment_all + "]--解析checkbox-l1Code-l2Code异常");
                    }
                }
                if(type.equals(GeneratorJSONProperties.FIELD_type_textarea)){
                    sb.append("                        <div class=\"form-group\">").append("\r\n");
                    sb.append("                            <label class=\"col-lg-3 title\"><i class=\"redFont\">*</i>"+column_comment+"：</label>").append("\r\n");
                    sb.append("                            <div class=\"col-lg-9\">").append("\r\n");
                    sb.append("                                <textarea id=\""+column_name_tf+"3\" name=\""+column_name_tf+"3\"  class=\"form-control\" placeholder=\""+column_comment+"\" type=\"text\" "+(canupdate?"":"disabled=\"disabled\"")+"></textarea>").append("\r\n");
                    sb.append("                            </div>").append("\r\n");
                    sb.append("                        </div>").append("\r\n");
                }
            }
        }
        //字段遍历结束

        sb.append("                    </div>").append("\r\n");
        sb.append("                    <div class=\"modal-footer\">").append("\r\n");
        sb.append("                        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>").append("\r\n");
        sb.append("                        <button type=\"submit\" class=\"btn btn-primary\">提交更改</button>").append("\r\n");
        sb.append("                    </div>").append("\r\n");
        sb.append("                </form>").append("\r\n");
        sb.append("            </div>").append("\r\n");
        sb.append("        </div>").append("\r\n");
        sb.append("    </div>").append("\r\n");
        sb.append("</#macro>").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("").append("\r\n");


        pw.println(sb.toString());
        pw.close();
    }
}
