package pipe.impl.dmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by baolong on 2016/8/22.
 */
public class CheckboxModel extends AbstractModel {
    {
        type = TYPE.checkbox.toString();
    }

    public CheckboxModel setOptions(String ... strs){
        attr.remove("l1Code");
        attr.remove("l2Code");
        List options = new ArrayList<String>();
        for(int i=0;i<strs.length;i++){
            options.add(strs[i]);
        }
        attr.put("options", options);
        return this;
    }

    public CheckboxModel removeOptions(){
        attr.remove("options");
        return this;
    }

    public CheckboxModel setL1CodeL2Code(String l1Code,String l2Code){
        attr.remove("options");
        attr.put("l1Code",l1Code);
        attr.put("l2Code",l2Code);
        return this;
    }

    public CheckboxModel removeL1CodeL2Code(){
        attr.remove("options");
        return this;
    }

    /**
     *生成一个CheckboxModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @return
     */
    public static CheckboxModel getInstance(String power,String notEmptyMsg){
        CheckboxModel checkboxModel = new CheckboxModel();
        checkboxModel.setPower(power);
        return checkboxModel;
    }

    /**
     *生成一个CheckboxModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @param optionstrs    单选框的选项列表，例如:票据,P2P，众筹等数组形式
     * @return
     */
    public static CheckboxModel getInstanceByOptions(String power,String notEmptyMsg,String... optionstrs){
        CheckboxModel checkboxModel = new CheckboxModel();
        checkboxModel.setPower(power);
        checkboxModel.setOptions(optionstrs);
        return checkboxModel;
    }

    /**
     *生成一个CheckboxModel
     * @param power     权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg   非空消息提示
     * @param l1Code    数据字典中的l1Code
     * @param l2Code    数据字典中的l2Code
     * @return
     */
    public static CheckboxModel getInstanceByL1CodeL2Code(String power,String notEmptyMsg,String l1Code,String l2Code){
        CheckboxModel checkboxModel = new CheckboxModel();
        checkboxModel.setPower(power);
        checkboxModel.setL1CodeL2Code(l1Code, l2Code);
        return checkboxModel;
    }
}
