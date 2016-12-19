package context;

/**
 * Created by Administrator on 2/21/16.
 * 在数据库comment中填写的JSON字符串key以及其默认值的信息
 */
public class GeneratorJSONProperties {

    /**********************************************************************************************
     * TYPE属性，第一级
     **********************************************************************************************/
    public static final String PATH_type = "type";
    public static final String DEFAULT_type = "input";

    public static final String FIELD_type_input = "input";//文本框
    public static final String FIELD_type_date = "date";//日期框
    public static final String FIELD_type_datetime = "datetime";//时间框
    public static final String FIELD_type_select = "select";//选择框
    public static final String FIELD_type_radio = "radio";//选择radio
    public static final String FIELD_type_checkbox = "checkbox";//复选框
    public static final String FIELD_type_textarea = "textarea";//文本域

    /**********************************************************************************************
     * VALIDATE属性，第一级
     **********************************************************************************************/
    public static final String PATH_validate = "validate";//校验规则
    public static final String DEFAULT_validators = "{\r\n" +
            "                    notEmpty : {\r\n" +
            "                        message : 'XX信息不能为空'\r\n" +
            "                    },\r\n" +
            "                    stringLength : {\r\n" +
            "                        min : 1,\r\n"+
            "                        max : 100,\r\n"+
            "                        message : 'XX信息长度不正确'\r\n" +
            "                    },\r\n" +
            "                    regexp : {\r\n" +
            "                        regexp : \"^.+$\",\r\n" +
            "                        message : 'XX信息与要求的格式不符'\r\n" +
            "                    }\r\n" +
            "                }";//默认校验规则

    /**********************************************************************************************
     * ATTR属性，第一级
     **********************************************************************************************/
    public static final String PATH_attr = "attr";

    //power，通用属性，位于attr下一级
    public static final String PATH_power = "power";
    public static final String DEFAULT_power = "query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y";

    //ismoney，input框专用属性，位于attr下一级(input是否是钱，是否需要转化为金额)
    public static final String PATH_ismoney = "ismoney";
    public static final String DEFAULT_ismoney = "false";

    //sequence，input框专用属性，位于attr下一级(input是否是序列，是否需要插入一个自增长的值)
    public static final String PATH_sequence = "sequence";
    public static final String DEFAULT_sequence = "dual_app_seq";

    //formatter，date框和datetime框属性，位于attr下一级(date框和datetime的格式)
    public static final String PATH_formatter = "formatter";
    public static final String DEFAULT_formatter_char8 = "yyyy-mm-dd;yyyy-MM-dd";//第一个为jqueryUi的格式，第二个为JAVA的格式，使用分号分割
    public static final String DEFAULT_formatter_char14 = "yyyy-mm-dd hh:ii:ss;yyyy-MM-dd HH:mm:ss";//第一个为jqueryUi的格式，第二个为JAVA的格式，使用分号分割

    public static final String DEFAULT_range_regexp_char8 = "{'regexp':'^([0-9]{4}-[0-9]{2}-[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2})$','message':'时间格式不正确'}";//char8日期校验格式
    public static final String DEFAULT_range_regexp_char14 = "{'regexp':'^([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})$','message':'时间格式不正确'}";//char14日期校验格式

    //autoinsert，date框和datetime框属性，位于attr下一级(date框和datetime是否自动插入当前时间)
    public static final String PATH_autoinsert = "autoinsert";
    public static final String DEFAULT_autoinsert = "false";

    //autoupdate，date框和datetime框属性，位于attr下一级(date框和datetime是否在页面修改时自动更新)
    public static final String PATH_autoupdate = "autoupdate";
    public static final String DEFAULT_autoupdate = "false";

    //queryrange，date框和datetime框属性，位于attr下一级(date框和datetime是否在查询页面为范围查询)
    public static final String PATH_queryrange = "queryrange";
    public static final String DEFAULT_queryrange = "false";

    //select、radio、checkbox专用属性，用于储存显示内容
    public static final String PATH_options = "options";

    //select、radio、checkbox专用属性，用于储存显示内容的字典码表
    public static final String PATH_l1Code = "l1Code";
    public static final String PATH_l2Code = "l2Code";

    //select专用，用于是否显示“全部”选项
    public static final String PATH_needAll = "needAll";

}
