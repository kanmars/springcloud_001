<#--
    name：名称
    label:显示标签
    options:数组型数据，防止标签名称
    value:与数组型数据相同长度的101010字符串
    readOnly:是否可修改
    //内置三个js函数
    flushCheckBox2FieldVal(form,checkBoxName):将页面的ID为form的表单中 ID为checkBoxName的checkbox框的值强制刷新到隐藏域中
    setCheckBoxVal(form,checkBoxName,checkBoxValue);将页面的ID为form的表单中 ID为checkBoxName的checkbox框的值设置为checkBoxValue
    getCheckBoxVal(form,checkBoxName);将页面的ID为form的表单中 ID为checkBoxName的checkbox框的值强制刷新到隐藏域，并返回隐藏域的值

    使用示例：
        <#import "/component/register.ftl" as  register/>
        //静态类型模式
		<@register.checkbox form="f" name="cb1" label="测试标题" options=['A_LABEL','B_LABEL','C_LABEL','D_LABEL'] value="1011" readOnly="false"/>
        //数据码表模式，dicCheckbox为在用户登陆时绑定到session中的Helper对象
		<@register.checkbox form="f" name="cb2" label="测试标题" options=dicCheckbox['0001,0001'] value="01010000" readOnly="false"/>

		在页面上获取值：可以使用
		    var v1 = getCheckBoxVal('f','cb1');
		    var v2 = getCheckBoxVal('f','cb2');
		给该多选框设置值：可以使用
		    setCheckBoxVal('f','cb1','010101010101');//长度不限，但仅根据options进行显示
-->

<#macro checkbox form name label options value
    class1="col-md-3"
    class2="col-md-4 control-label"
    class3="col-md-8"
    class4="checkbox-inline"
    class5="inline1"
    readOnly="false">
    <div class="checkbox_${form}_${name} ${class1}">
        <label class="checkbox_${form}_${name} ${class2} ">${label}：</label>
        <div class="checkbox_${form}_${name} ${class3}">
            <input type="text" id="${form}_${name}" name="${form}_${name}" style="display:none" value="" >
            <#if  options?is_sequence>
                <#assign idx=0/>
                <#list options as item  >
                    <label class="${class4}"><input type="checkbox"
                                  class="${class5}"
                                  id="checkedBox_${form}_${name}_${idx}"
                                  name="${form}_${name}"<#--此处使用原生的name而非checkedBox_${name}_${idx}，是为了校验框通用-->
                                  <#if readOnly=="true">onclick="return false"<#else> onclick="flushCheckBox2FieldVal('${form}','${name}')"</#if>
                            />${options[idx]}</label>
                    <#assign idx=idx+1/>
                </#list>
            </#if>
            <div class="clearfix"></div>
        </div>
    </div>
    <script>
        var getCheckBoxVal=null;
        var setCheckBoxVal=null;
        var flushCheckBox2FieldVal=null;
        if(flushCheckBox2FieldVal==undefined||flushCheckBox2FieldVal==null){
            flushCheckBox2FieldVal = function(formname,checkBoxName){
                var oldStr = $("#"+formname+"_"+checkBoxName).val();//记录原数据，为了避免数据库中为32位，但实际只使用了5位的情况
                var hideStr="";
                $(".checkbox_"+formname+"_"+checkBoxName+" input[type='checkbox']").each(function(){
                    hideStr+=$(this).is(":checked")?"1":"0";
                });
                //如果原数据的长度大于新数据的长度，则将原数据的后半部分加到新数据的后面，防止数据丢失
                if($.trim(oldStr)!="" && oldStr.length > hideStr.length ){
                    hideStr = hideStr + oldStr.substr(hideStr.length)
                }
                $("#"+formname+"_"+checkBoxName).val(hideStr);
            }
        }
        if(getCheckBoxVal==undefined||getCheckBoxVal==null){
            getCheckBoxVal = function(formname,checkBoxName){
                flushCheckBox2FieldVal(formname,checkBoxName)
                return $("#"+formname+"_"+checkBoxName).val();
            }

        }

        if(setCheckBoxVal==undefined||setCheckBoxVal==null){
            setCheckBoxVal = function(formname,checkBoxName,checkBoxval){
                if($.trim(checkBoxval)=="")return;
                $("#"+formname+" #"+checkBoxName).val(checkBoxval);
                var checkBoxIdx = 0;
                for(var checkBoxIdx=0;checkBoxIdx<$(".checkbox_"+formname+"_"+checkBoxName+" input[type='checkbox']").size();checkBoxIdx++){
                    if(checkBoxval.length > checkBoxIdx && checkBoxval.substr(checkBoxIdx,1)=="0"){
                        //console.log("checkBoxIdx "+checkBoxIdx + "false");
                        //采用原生javascript避免checkbox在不同浏览器兼容问题
                        document.getElementById("checkedBox_"+formname+"_"+checkBoxName+"_"+checkBoxIdx).checked = false;
                        //console.log("        赋值后的值为"+$("#checkedBox_"+formname+"_"+checkBoxName+"_"+checkBoxIdx).is(":checked"))
                    }else{
                        //console.log("checkBoxIdx "+checkBoxIdx + "true");
                        //$("#checkedBox_"+formname+"_"+checkBoxName+"_"+checkBoxIdx).attr("checked", "checked");
                        document.getElementById("checkedBox_"+formname+"_"+checkBoxName+"_"+checkBoxIdx).checked = true;
                        //console.log("        赋值后的值为"+$("#checkedBox_"+formname+"_"+checkBoxName+"_"+checkBoxIdx).is(":checked"))
                    }
                }
            }
        }
        setCheckBoxVal('${form}','${name}','${value}');
        //alert(getCheckBoxVal('${form}','${name}'));
    </script>
</#macro>