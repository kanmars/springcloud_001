package pipe.impl.dmodel;

import org.apache.commons.lang.StringUtils;

/**
 * Created by baolong on 2016/8/22.
 */
public class DateModel extends AbstractModel {
    {
        type = TYPE.date.toString();
        attr.put("formatter","yyyy-mm-dd;yyyy-MM-dd");
        attr.put("autoinsert","false");
        attr.put("autoupdate","false");
        attr.put("queryrange","true");
    }

    /**
     * @param pageformat    yyyy-mm-dd
     * @param dbformat      yyyy-MM-dd
     */
    public DateModel setFormatter(String pageformat,String dbformat){
        attr.put("formatter",pageformat+";"+dbformat);
        return this;
    }

    public DateModel setAutoInsert(boolean autoInsert){
        attr.put("autoinsert",((Boolean)autoInsert).toString());
        return this;
    }
    public DateModel removeAutoInsert(){
        attr.remove("autoinsert");
        return this;
    }
    public DateModel setAutoUpdate(boolean autoUpdate){
        attr.put("autoupdate", ((Boolean) autoUpdate).toString());
        return this;
    }
    public DateModel removeAutoUpdate(){
        attr.remove("autoupdate");
        return this;
    }
    public DateModel setQueryRange(boolean queryRange){
        attr.put("queryrange", ((Boolean) queryRange).toString());
        return this;
    }
    public DateModel removeQueryRange(){
        attr.remove("queryrange");
        return this;
    }

    /**
     * 生成一个DateModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param autoInsert    是否允许自动插入生成时间
     * @param autoUpdate    是否允许自动更新
     * @param queryRange    是否允许范围查询
     * @return
     */
    public static DateModel getInstance(String power,boolean autoInsert,boolean autoUpdate,boolean queryRange){
        DateModel dateModel = new DateModel();
        dateModel.setPower(power);
        dateModel.setAutoInsert(autoInsert);
        dateModel.setAutoUpdate(autoUpdate);
        dateModel.setQueryRange(queryRange);
        return dateModel;
    }
}
