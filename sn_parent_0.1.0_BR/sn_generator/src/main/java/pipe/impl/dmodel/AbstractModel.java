package pipe.impl.dmodel;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baolong on 2016/8/22.
 */
public class AbstractModel {
    enum TYPE {
        input,date,datetime,select,radio,checkbox,textarea
    }
    /**有如下五种类型*/
    protected String type;
    /**属性*/
    protected Map attr = new HashMap();
    /**描述*/
    protected String desc;

    /**获取JSON信息*/
    public JSONObject getModelJSON (){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.setExcludes(new String[]{
                "modelJSON"
        });
        return JSONObject.fromObject(this,jsonConfig);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map getAttr() {
        return attr;
    }

    public void setAttr(Map attr) {
        this.attr = attr;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 设置权限"power":"query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y"
     * @param queryCondition    是否作为查询条件
     * @param queryResult       是否作为查询结果
     * @param add               是否作为新增内容
     * @param detail            是否作为详情内容
     * @param updateShow        是否在更新时显示
     * @param updateUpdate      是否更新
     */
    public AbstractModel setPower(boolean queryCondition,boolean queryResult,boolean add,boolean detail,boolean updateShow,boolean updateUpdate){
        StringBuilder sb = new StringBuilder();
        sb.append("query-condition:");
        if(queryCondition){sb.append("Y");}else{sb.append("N");}
        sb.append(",");
        sb.append("query-result:");
        if(queryResult){sb.append("Y");}else{sb.append("N");}
        sb.append(",");
        sb.append("add:");
        if(add){sb.append("Y");}else{sb.append("N");}
        sb.append(",");
        sb.append("detail:");
        if(detail){sb.append("Y");}else{sb.append("N");}
        sb.append(",");
        sb.append("update-show:");
        if(updateShow){sb.append("Y");}else{sb.append("N");}
        sb.append(",");
        sb.append("update-update:");
        if(updateUpdate){sb.append("Y");}else{sb.append("N");}
        attr.put("power",sb.toString());
        return this;
    }

    /**
     * 设置权限"power":"query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y"
     * @param str
     */
    public AbstractModel setPower(String str){
        if(str.trim().length()!=6){
            attr.put("power",str);
        }else{
            return setPower(
                    str.charAt(0)=='Y'||str.charAt(0)=='y',
                    str.charAt(1)=='Y'||str.charAt(1)=='y',
                    str.charAt(2)=='Y'||str.charAt(2)=='y',
                    str.charAt(3)=='Y'||str.charAt(3)=='y',
                    str.charAt(4)=='Y'||str.charAt(4)=='y',
                    str.charAt(5)=='Y'||str.charAt(5)=='y'
                    );
        }
        return this;
    }
}
