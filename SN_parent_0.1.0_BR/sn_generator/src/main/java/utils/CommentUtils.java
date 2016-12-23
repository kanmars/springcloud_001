package utils;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by baolong on 2016/1/14.
 */
public class CommentUtils {
    /**一级目录：类型，input,datetime,select,radio*/
    public static final String PATH_TYPE = "type";
    /**一级目录：校验规则*/
    public static final String PATH_VALIDATE = "validate";
    /**一级目录：附加属性*/
    public static final String PATH_ATTR = "attr";
    /**二级目录：附加属性-值，通用*/
    public static final String PATH_ATTR_VALUE = "attr.value";
    /**二级目录：附加属性-读写权限，通用，可用值为select-query,select-result,insert,update-show,update-update*/
    public static final String PATH_ATTR_POWER = "attr.power";

    /**二级目录：附加属性-序列，通用，新增时，调用该序列并填写，可用值为序列表的序列名*/
    public static final String PATH_ATTR_SEQUENCE = "attr.sequence";
    /**二级目录：附加属性-开始时间，datetime框专用，开始时间*/
    public static final String PATH_ATTR_DATETIME_BEGIN = "attr.begin";
    /**二级目录：附加属性-结束时间，datetime框专用，结束时间*/
    public static final String PATH_ATTR_DATETIME_END = "attr.end";
    /**二级目录：附加属性-时间格式，datetime框专用，结束时间*/
    public static final String PATH_ATTR_DATETIME_FORMATTER = "attr.formatter";

    /**二级目录：附加属性-下拉框选项，option来源，static，静态；dynamic数据字典*/
    public static final String PATH_ATTR_SELECT_OPTION_TYPE = "attr.optiontype";
    /**二级目录：附加属性-下拉框选项，options内容："options":{"010":"票据","020":"P2P","030":"众筹"},或者"options":{"l1Code":"TBL_ORDER_INFO","l2Code":"ORDER_TYPE"}*/
    public static final String PATH_ATTR_SELECT_OPTIONS = "attr.options";
    /**二级目录：附加属性-下拉框选项，是否支持“全部”选项*/
    public static final String PATH_ATTR_SELECT_NEEDALL = "attr.needall";

    /**二级目录：附加属性-复选框选项，option来源，static，静态；dynamic数据字典*/
    public static final String PATH_ATTR_RADIO_OPTION_TYPE = "attr.optiontype";
    /**二级目录：附加属性-复选框选项，options内容："options":["是否运行A任务","是否运行B任务","是否运行C任务"],或者"options":{"l1Code":"ccccc","l2Code":"dddd"}*/
    public static final String PATH_ATTR_RADIO_OPTIONS = "attr.options";
    /**二级目录：附加属性-复选框选项，是否支持“全部”选项*/
    public static final String PATH_ATTR_RADIO_NEEDALL = "attr.onlyone";


    /*
    示例：
	tbl_order_info

	order_no:
		订单编号:{
			"type":"input",
			"validate":"regex:^[0-9]{20}$",
			"attr":{
				"value":"",
				"power":"select-query,select-result,update-show",
				"sequence":"TBL_ORDER_INFO"
			}
		}
	order_type
		订单类型:{
			"type":"select",
			"validate":"regex:^[0-9]{3}$",
			"attr":{
				"optiontype":"static",
				"options":{"010":"票据","020":"P2P","030":"众筹"},
				"value":"010",
				"needAll":"true",
				"power":"select-query,select-result,insert,update-update"
			}
		}

	order_type
		订单类型:{
			"type":"select",
			"validate":"regex:^[0-9]{3}$",
			"attr":{
				"optiontype":"dynamic",
				"options":{"l1Code":"TBL_ORDER_INFO","l2Code":"ORDER_TYPE"},
				"value":"010",
				"needAll":"true",
				"power":"select-query,select-result,insert,update-update"
			}
		}
	create_time
		创建时间:{
			"type":"datetime",
			"validate":"inchecker:datetime",
			"attr":{
				"value":"20160114115900",
				"begin":"20160114115900",
				"end":"20160214115900",
				"formatter":"yyyy/MM/dd HH:mm:ss"
				"power":"select-query,select-result,insert,update-update"
			}

		}
	runflag:
		运行标记:{
			"type":"radio",
			"validate":"regex:^[0-1]*$",
			"attr":{
				"optiontype":"static",
				"options":["是否运行A任务","是否运行B任务","是否运行C任务"],
				"length":"30",
				"value":"010",
				"onlyOne":"false",
				"power":"select-query,select-result,insert,update-update"
			}
		}
	runflag:
		运行标记:{
			"type":"radio",
			"validate":"regex:^[0-1]*$",
			"attr":{
				"optiontype":"dynamic",
				"options":{"l1Code":"ccccc","l2Code":"dddd"},
				"length":"30",
				"value":"010",
				"onlyOne":"false",
				"power":"select-query,select-result,insert,update-update"
			}
		}
     */
    /**
     * 从comment中获取描述
     * @param comment
     * @return
     */
    public static String getFieldDesc(String comment){
        if(comment==null || comment.trim().equals("")){
            return comment;
        }
        if(comment.indexOf(":")<=0){
            //格式不符，直接返回
            return comment;
        }
        try{
            String jsonStr = comment.substring(comment.indexOf(":")+1);
            JSONObject json = JSONObject.fromObject(jsonStr);
            return comment.substring(0,comment.indexOf(":"));
        }catch (Exception e){

        }
        return comment;
    }

    /**
     * 获取属性参数，是全量的Object
     * @param comment
     * @return
     */
    public static JSONObject getFieldParamJSON(String comment){
        if(comment==null || comment.trim().equals("")){
            return null;
        }
        if(comment.indexOf(":")<=0){
            //格式不符，直接返回
            return null;
        }
        try{
            String jsonStr = comment.substring(comment.indexOf(":")+1);
            JSONObject json = JSONObject.fromObject(jsonStr);
            return json;
        }catch (Exception e){

        }
        return null;
    }

    /**
     * 获取某个路径下的值
     * @param comment
     * @param path
     * @return
     */
    public static String getFieldParamValueString(String comment,String path){

        JSONObject json = getFieldParamJSON(comment);
        if(json == null){
            return null;
        }
        String[] paths = path.split("\\.");
        JSONObject tmp = json;
        try{
            for(int i=0;i<paths.length;i++){
                if(i==paths.length-1){
                    return tmp.getString(paths[i]);
                }
                tmp = tmp.getJSONObject(paths[i]);
            }
        }catch (Exception e){
            System.out.println("comment :"+comment);
            System.out.println("path :"+path);
            return null;
        }
        return null;
    }

    /**
     * 获取某个路径下的值
     * @param comment
     * @param path
     * @return
     */
    public static JSONObject getFieldParamValueJSONObject(String comment,String path){

        JSONObject json = getFieldParamJSON(comment);
        if(json == null){
            return null;
        }
        String[] paths = path.split("\\.");
        JSONObject tmp = json;
        try{
            for(int i=0;i<paths.length;i++){
                if(i==paths.length-1){
                    return tmp.getJSONObject(paths[i]);
                }
                tmp = tmp.getJSONObject(paths[i]);
            }
        }catch (Exception e){
            System.out.println("comment :"+comment);
            System.out.println("path :"+path);
            return null;
        }
        return null;
    }

    /**
     * 获取某个路径下的值
     * @param comment
     * @param path
     * @return
     */
    public static List getFieldParamValueJSONList(String comment,String path){

        JSONObject json = getFieldParamJSON(comment);
        if(json == null){
            return null;
        }
        String[] paths = path.split("\\.");
        JSONObject tmp = json;
        try{
            for(int i=0;i<paths.length;i++){
                if(i==paths.length-1){
                    return tmp.getJSONArray(paths[i]);
                }
                tmp = tmp.getJSONObject(paths[i]);
            }
        }catch (Exception e){
            System.out.println("comment :"+comment);
            System.out.println("path :"+path);
            return null;
        }
        return null;
    }

    public static void main(String[] args) {
        String s = "任务说明:{\n" +
                    "\t\t\t\"type\":\"radio-static\",\n" +
                    "\t\t\t\"validate\":\"regex:^[0-1]*$\",\n" +
                    "\t\t\t\"attr\":{\n" +
                    "\t\t\t\t\"options\":[\"是否运行A任务\",\"是否运行B任务\",\"是否运行C任务\"],\n" +
                    "\t\t\t\t\"length\":\"30\",\n" +
                    "\t\t\t\t\"value\":\"010\",\n" +
                    "\t\t\t\t\"onlyOne\":\"false\",\n" +
                    "\t\t\t\t\"power\":\"select-query,select-result,insert,update-update\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}";
        String ss = "aaa:{\"c\":\"c\"}";

        System.out.println(getFieldParamJSON(s));
        System.out.println(getFieldParamValueString(s, "attr.length"));
        System.out.println(getFieldParamValueJSONObject(s, "attr"));
        System.out.println(getFieldParamValueJSONList(s, "attr.options"));
    }

}
