package pipe.impl.dmodel;

import org.apache.commons.lang.StringUtils;

/**
 * Created by baolong on 2016/8/22.
 */
public class InputModel extends ValidateModel {

    {
        type = TYPE.input.toString();
    }

    public InputModel setIsMoney(boolean isMoney){
        attr.put("ismoney",((Boolean)isMoney).toString());
        return this;
    }
    public InputModel removeIsMoney(boolean isMoney){
        attr.remove("ismoney");
        return this;
    }
    public InputModel setSequence(String sequence){
        if(StringUtils.isNotEmpty(sequence)){
            attr.put("sequence",sequence);
        }
        return this;
    }
    public InputModel removeSequence(String sequence){
        attr.remove("sequence");
        return this;
    }

    /**
     * 生成一个InputModel
     * @param power             权限，有三种写法：
     *                          1、query-condition:Y,query-result:Y,add:Y,detail:Y,update-show:Y,update-update:Y
     *                          2、YYYYYN
     *                          3、yyyynn
     * @param notEmptyMsg      非空消息提示，为空则允许为空
     * @param min               长度最小值，-1为不限制
     * @param max               长度最大值，-1为不限制
     * @param strLengthMsg      长度不正确消息提示
     * @param regExp            正则表达式
     * @param regExpMsg         正则表达式不正确消息提示
     * @param isMoney           是否是金额
     * @param sequence          是否需要序号生成，如果为空则为不需要
     * @return
     */
    public static InputModel getInstance(String power,String notEmptyMsg,int min,int max,String strLengthMsg,String regExp,String regExpMsg,boolean isMoney,String sequence){
        InputModel inputModel = new InputModel();
        inputModel.setPower(power);
        inputModel.setNotEmpty(notEmptyMsg);
        if(min>=0&&max>=0&&StringUtils.isNotEmpty(strLengthMsg)){
            inputModel.setStringLength(min,max,strLengthMsg);
        }
        if(StringUtils.isNotEmpty(regExp)&&StringUtils.isNotEmpty(regExpMsg)){
            inputModel.setRegexp(regExp,regExpMsg);
        }
        inputModel.setIsMoney(isMoney);
        inputModel.setSequence(sequence);
        return inputModel;
    }
}
