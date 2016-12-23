package pipe.impl.dmodel;

import org.apache.commons.lang.StringUtils;

/**
 * Created by baolong on 2016/8/22.
 */
public class DateTimeModel extends AbstractModel {
    {
        type = TYPE.datetime.toString();
        attr.put("formatter","yyyy-mm-dd hh:ii:ss;yyyy-MM-dd HH:mm:ss");
        attr.put("autoinsert","false");
        attr.put("autoupdate","false");
        attr.put("queryrange","true");
    }

    /**
     * @param pageformat    yyyy-mm-dd hh:ii:ss
     * @param dbformat      yyyy-MM-dd HH:mm:ss
     */
    public DateTimeModel setFormatter(String pageformat,String dbformat){
        attr.put("formatter",pageformat+";"+dbformat);
        return this;
    }

    public DateTimeModel setAutoInsert(boolean autoInsert){
        attr.put("autoinsert",((Boolean)autoInsert).toString());
        return this;
    }
    public DateTimeModel removeAutoInsert(){
        attr.remove("autoinsert");
        return this;
    }
    public DateTimeModel setAutoUpdate(boolean autoUpdate){
        attr.put("autoupdate", ((Boolean) autoUpdate).toString());
        return this;
    }
    public DateTimeModel removeAutoUpdate(){
        attr.remove("autoupdate");
        return this;
    }
    public DateTimeModel setQueryRange(boolean queryRange){
        attr.put("queryrange", ((Boolean) queryRange).toString());
        return this;
    }
    public DateTimeModel removeQueryRange(){
        attr.remove("queryrange");
        return this;
    }


    /**
     * 生成一个DateTimeModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param autoInsert    是否允许自动插入生成时间
     * @param autoUpdate    是否允许自动更新
     * @param queryRange    是否允许范围查询
     * @return
     */
    public static DateTimeModel getInstance(String power,boolean autoInsert,boolean autoUpdate,boolean queryRange){
        DateTimeModel dateTimeModel = new DateTimeModel();
        dateTimeModel.setPower(power);
        dateTimeModel.setAutoInsert(autoInsert);
        dateTimeModel.setAutoUpdate(autoUpdate);
        dateTimeModel.setQueryRange(queryRange);
        return dateTimeModel;
    }
}
