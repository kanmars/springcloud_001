package pipe.impl.dmodel;

import java.util.HashMap;

/**
 * Created by baolong on 2016/8/22.
 */
public class RadioModel extends ValidateModel {
    {
        type = TYPE.radio.toString();
        this.setNotEmpty("请选择一个单选按钮");
    }

    public RadioModel setOptions(String ... strs){
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

    public RadioModel removeOptions(){
        attr.remove("options");
        return this;
    }

    public RadioModel setL1CodeL2Code(String l1Code,String l2Code){
        attr.remove("options");
        attr.put("l1Code",l1Code);
        attr.put("l2Code", l2Code);
        return this;
    }

    public RadioModel removeL1CodeL2Code(){
        attr.remove("options");
        return this;
    }

    /**
     *生成一个RadioModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @return
     */
    public static RadioModel getInstance(String power,String notEmptyMsg){
        RadioModel radioModel = new RadioModel();
        radioModel.setPower(power);
        radioModel.setNotEmpty(notEmptyMsg);
        return radioModel;
    }

    /**
     *生成一个RadioModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @param optionstrs    单选框的选项列表，例如:010,票据,020，P2P，030，众筹等数组形式
     * @return
     */
    public static RadioModel getInstanceByOptions(String power,String notEmptyMsg,String... optionstrs){
        RadioModel radioModel = new RadioModel();
        radioModel.setPower(power);
        radioModel.setNotEmpty(notEmptyMsg);
        radioModel.setOptions(optionstrs);
        return radioModel;
    }

    /**
     *生成一个RadioModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @param l1Code    数据字典中的l1Code
     * @param l2Code    数据字典中的l2Code
     * @return
     */
    public static RadioModel getInstanceByL1CodeL2Code(String power,String notEmptyMsg,String l1Code,String l2Code){
        RadioModel radioModel = new RadioModel();
        radioModel.setPower(power);
        radioModel.setNotEmpty(notEmptyMsg);
        radioModel.setL1CodeL2Code(l1Code,l2Code);
        return radioModel;
    }
}
