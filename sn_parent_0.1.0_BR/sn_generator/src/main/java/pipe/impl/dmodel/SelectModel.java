package pipe.impl.dmodel;

import java.util.HashMap;

/**
 * Created by baolong on 2016/8/22.
 */
public class SelectModel extends ValidateModel {
    {
        type = TYPE.select.toString();
        this.setNotEmpty("请选择一个动态选择框");
    }

    public SelectModel setNeedAll(boolean needAll){
        attr.put("needAll",((Boolean)needAll).toString());
        return this;
    }

    public SelectModel removeNeedAll(){
        attr.remove("needAll");
        return this;
    }

    public SelectModel setOptions(String ... strs){
        if(strs.length%2!=0)throw new RuntimeException("使用options时，strs的个数必须为偶数");
        attr.remove("l1Code");
        attr.remove("l2Code");
        HashMap options = new HashMap();
        for(int i=0;i<strs.length;i+=2){
            options.put(strs[i],strs[i+1]);
        }
        attr.put("options", options);
        return this;
    }

    public SelectModel removeOptions(){
        attr.remove("options");
        return this;
    }

    public SelectModel setL1CodeL2Code(String l1Code,String l2Code){
        attr.remove("options");
        attr.put("l1Code",l1Code);
        attr.put("l2Code", l2Code);
        return this;
    }

    public SelectModel removeL1CodeL2Code(){
        attr.remove("options");
        return this;
    }
    /**
     *生成一个SelectModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @return
     */
    public static SelectModel getInstance(String power,String notEmptyMsg){
        SelectModel selectModel = new SelectModel();
        selectModel.setPower(power);
        selectModel.setNotEmpty(notEmptyMsg);
        return selectModel;
    }

    /**
     *生成一个SelectModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @param optionstrs    单选框的选项列表，例如:010,票据,020，P2P，030，众筹等数组形式
     * @return
     */
    public static SelectModel getInstanceByOptions(String power,String notEmptyMsg,String... optionstrs){
        SelectModel selectModel = new SelectModel();
        selectModel.setPower(power);
        selectModel.setNotEmpty(notEmptyMsg);
        selectModel.setOptions(optionstrs);
        return selectModel;
    }

    /**
     *生成一个SelectModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @param l1Code    数据字典中的l1Code
     * @param l2Code    数据字典中的l2Code
     * @return
     */
    public static SelectModel getInstanceByL1CodeL2Code(String power,String notEmptyMsg,String l1Code,String l2Code){
        SelectModel selectModel = new SelectModel();
        selectModel.setPower(power);
        selectModel.setNotEmpty(notEmptyMsg);
        selectModel.setL1CodeL2Code(l1Code, l2Code);
        return selectModel;
    }
}
