package pipe.impl.dmodel;

import org.apache.commons.lang.StringUtils;

/**
 * Created by baolong on 2016/8/22.
 */
public class TextareaModel extends InputModel {
    {
        type = TYPE.textarea.toString();
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
     * @return
     */
    public static TextareaModel getInstance(String power,String notEmptyMsg,int min,int max,String strLengthMsg,String regExp,String regExpMsg){
        TextareaModel textareaModel = new TextareaModel();
        textareaModel.setPower(power);
        textareaModel.setNotEmpty(notEmptyMsg);
        if(min>=0&&max>=0&& StringUtils.isNotEmpty(strLengthMsg)){
            textareaModel.setStringLength(min,max,strLengthMsg);
        }
        if(StringUtils.isNotEmpty(regExp)&&StringUtils.isNotEmpty(regExpMsg)){
            textareaModel.setRegexp(regExp,regExpMsg);
        }
        return textareaModel;
    }
}
