<#--
    模块组建注册表
    还缺乏的模块包括
        筛选框
        select
        树形结构
        文本域
        时间框
        等
    此处有两种实现方式：
        1、指定input，通过jquery方法将该Input变成一个筛选框、复选框、时间框等......
        2、使用ftl，直接在后台生成目标代码
    建议用前者，但是由于时间以及思路的关系，目前用的是后者
-->
<#import "/component/checkbox/checkbox.ftl" as  checkbox_component/>


<#macro checkbox form name label options value
    class1="col-md-3"
    class2="col-md-4 control-label"
    class3="col-md-8"
    class4="checkbox-inline"
    class5="inline1" readOnly="false">

    <@checkbox_component.checkbox form=form name=name label=label options=options value=value
    class1=class1
    class2=class2
    class3=class3
    class4=class4
    class5=class5
    readOnly=readOnly/>
</#macro>

