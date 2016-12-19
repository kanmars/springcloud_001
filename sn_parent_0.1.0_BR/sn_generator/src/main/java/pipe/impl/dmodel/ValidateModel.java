package pipe.impl.dmodel;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baolong on 2016/8/22.
 */
public class ValidateModel extends AbstractModel {
    /**校验规则*/
    protected Map validate = new HashMap();

    public ValidateModel setNotEmpty(String notEmptyMsg){
        if(StringUtils.isNotEmpty(notEmptyMsg)){
            HashMap notEmpty = new HashMap();
            notEmpty.put("message",notEmptyMsg);
            validate.put("notEmpty",notEmpty);
        }
        return this;
    }
    public ValidateModel removeNotEmpty(){
        validate.remove("notEmpty");
        return this;
    }
    public ValidateModel setStringLength(int min,int max,String message){
        HashMap stringLength = new HashMap();
        stringLength.put("min",min);
        stringLength.put("max",max);
        stringLength.put("message",message);
        validate.put("stringLength", stringLength);
        return this;
    }
    public ValidateModel removeStringLength(){
        validate.remove("stringLength");
        return this;
    }
    public ValidateModel setRegexp(String regexpstr,String message){
        HashMap regexp = new HashMap();
        regexp.put("regexp",regexpstr);
        regexp.put("message",message);
        validate.put("regexp", regexp);
        return this;
    }
    public ValidateModel removeRegexp(){
        validate.remove("regexp");
        return this;
    }

    public Map getValidate() {
        return validate;
    }

    public void setValidate(Map validate) {
        this.validate = validate;
    }
}
