$(function(){
    searchSysroleInfo('');
    $('#querySysroleInfopageSize').on('change',function () {
        var currentPage = $("#querySysroleInfocurrentPage").val();
        searchSysroleInfo(currentPage);
    });
    
    //时间框校验函数，时间框在校验失败后，再点击，不会触发校验，因此手工触发
    function revalidateDate(formname,datefieldname){
        if($("#"+formname+"").data('bootstrapValidator')!=null
            && $("#"+formname+"").data('bootstrapValidator').validateField !=null)
        {
            $("#"+formname+"").data('bootstrapValidator').updateStatus(datefieldname,"NOT_VALIDATED",null);
            $("#"+formname+"").data('bootstrapValidator').validateField(datefieldname);
        }
    }
    //在模态框对用户完全可见后，清空所有的校验状态，经验证：
    // show.bs.modal，shown.bs.modal，hide.bs.modal	，hidden.bs.modal仅有  shown.bs.modal在功能上没问题，但是视觉上仍不太满意
    // 理论上说，最合适的是hidden.bs.modal，当模态框关闭时清空，但实际上未能生效，因此使用了shown.bs.modal
    $('#updateSysroleInfo').on('shown.bs.modal', function () {
        $('#validateUpdateSysroleInfoForm').bootstrapValidator('resetForm', false);
    });
    $('#querySysroleInfoForm #createTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysroleInfoForm","createTime");
    });
    $('#validateUpdateSysroleInfoForm #createTime3').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateUpdateSysroleInfoForm","createTime3");
    });

    //增加系统角色表校验
    $('#validateAddSysroleInfoForm').bootstrapValidator({
        fields : {
            roleNo1 : {
                validators : {"notEmpty":{"message":"角色编号不能为空"},"stringLength":{"min":1,"max":10,"message":"角色编号长度不正确"},"regexp":{"regexp":"^[0-9]{4,10}$","message":"角色编号只能是四到十位数字"}}
            }
            ,
            roleName1 : {
                validators : {"notEmpty":{"message":"角色名称不能为空"},"stringLength":{"min":1,"max":20,"message":"角色名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"角色名称只能是中文、英文、数字与下划线"}}
            }
            ,
            applicationCode1 : {
                validators : {"notEmpty":{"message":"请选择一个应用名称"}}
            }
            ,
            createOprNo1 : {
                validators : {"notEmpty":{"message":"创建人编号不能为空"},"stringLength":{"min":1,"max":20,"message":"创建人编号长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]{1,20}$","message":"创建人编号只能填写英文、数字和下划线"}}
            }
            ,
            createOprName1 : {
                validators : {"notEmpty":{"message":"创建人名称不能为空"},"stringLength":{"min":1,"max":20,"message":"创建人名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]{1,20}$","message":"创建人名称只能填写中文、英文、数字和下划线"}}
            }
            ,
            roleOthMsg1 : {
                validators : {"stringLength":{"min":0,"max":160,"message":"角色附属信息长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]*$","message":"角色附属信息只能是中文、英文、数字与下划线"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addSysroleInfo();
     });

    //修改系统角色表校验
    $('#validateUpdateSysroleInfoForm').bootstrapValidator({
        fields : {
            roleNo3 : {
                validators : {"notEmpty":{"message":"角色编号不能为空"},"stringLength":{"min":1,"max":10,"message":"角色编号长度不正确"},"regexp":{"regexp":"^[0-9]{4,10}$","message":"角色编号只能是四到十位数字"}}
            }
            ,
            roleName3 : {
                validators : {"notEmpty":{"message":"角色名称不能为空"},"stringLength":{"min":1,"max":20,"message":"角色名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"角色名称只能是中文、英文、数字与下划线"}}
            }
            ,
            applicationCode3 : {
                validators : {"notEmpty":{"message":"请选择一个应用名称"}}
            }
            ,
            roleOthMsg3 : {
                validators : {"stringLength":{"min":0,"max":160,"message":"角色附属信息长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]*$","message":"角色附属信息只能是中文、英文、数字与下划线"}}
            }
            ,
            createTime3 : {
                validators : {
                    notEmpty : {
                        message : 'XX信息不能为空'
                    },
                    stringLength : {
                        min : 1,
                        max : 100,
                        message : 'XX信息长度不正确'
                    },
                    regexp : {
                        regexp : "^.+$",
                        message : 'XX信息与要求的格式不符'
                    }
                }
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         updateSysroleInfo();
     });
    //查询系统角色表校验
    $('#querySysroleInfoForm').bootstrapValidator({
        fields : {
            roleNo : {
                validators : {"stringLength":{"min":1,"max":10,"message":"角色编号长度不正确"},"regexp":{"regexp":"^[0-9]{4,10}$","message":"角色编号只能是数字"}}
            }
            ,
            roleName : {
                validators : {"stringLength":{"min":1,"max":20,"message":"角色名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"角色名称只能是中文、英文、数字与下划线"}}
            }
            ,
            applicationCode : {
                validators : {}
            }
            ,
            createOprNo : {
                validators : {"stringLength":{"min":1,"max":20,"message":"创建人编号长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]{1,20}$","message":"创建人编号只能填写英文、数字和下划线"}}
            }
            ,
            createOprName : {
                validators : {"stringLength":{"min":1,"max":20,"message":"创建人名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]{1,20}$","message":"创建人名称只能填写中文、英文、数字和下划线"}}
            }
            ,
            roleOthMsg : {
                validators : {"stringLength":{"min":0,"max":160,"message":"角色附属信息长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]*$","message":"角色附属信息只能是中文、英文、数字与下划线"}}
            }
            ,
            createTime : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^.+$","message":"XX信息与要求的格式不符"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         console.log("查询校验通过");
     });

});

function searchSysroleInfo(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#querySysroleInfoForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#querySysroleInfoForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#querySysroleInfoForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#querySysroleInfoForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#querySysrolecurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#querySysroleInfopageSize").val();
    if(pageSize=="undefined" || pageSize =="" || pageSize == null){
        params.pageSize = "10";
    }else{
        params.pageSize = pageSize;
    }
    if(queryType!=undefined && queryType =='download'){
        params.queryType='download';
        params.exportParam=exportParam;
        params.fileName=fileName;
    }

    params.roleNo = $("#querySysroleInfoForm #roleNo").val();
    params.roleName = $("#querySysroleInfoForm #roleName").val();
    params.applicationCode = $("#querySysroleInfoForm #applicationCode").val();
    params.createOprNo = $("#querySysroleInfoForm #createOprNo").val();
    params.createOprName = $("#querySysroleInfoForm #createOprName").val();
    params.roleOthMsg = $("#querySysroleInfoForm #roleOthMsg").val();
    params.createTime = $("#querySysroleInfoForm #createTime").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/sysroleInfo/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/sysroleInfo/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#sysroleInfoTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#querySysroleInfocurrentPage").val(data.rows[0].startIndex);
                $("#querySysroleInfototalPage").val(data.rows[0].totalPage);
                $("#querySysroleInfopageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var sysroleInfoTr = $("<tr class=\"sysroleInfoTr\"></tr>");
                    $("#sysroleInfoTR_FIRST").parent().append(sysroleInfoTr);
                    sysroleInfoTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].roleNo+"</td>")
                    .append("<td>"+resultList[i].roleName+"</td>")
                    .append("<td>"+resultList[i].applicationCode_name+"</td>")
                    .append("<td>"+resultList[i].createOprNo+"</td>")
                    .append("<td>"+resultList[i].createOprName+"</td>")
                    .append("<td>"+resultList[i].roleOthMsg+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editSysroleInfo('"+resultList[i].roleNo+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delSysroleInfo('"+resultList[i].roleNo+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return querySysroleInfoObject('"+resultList[i].roleNo+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#querySysroleInfocurrentPage").val());
                var totalPage   = Number($("#querySysroleInfototalPage").val());
                var pageSize    = Number($("#querySysroleInfopageSize").val());
                $("#querySysroleInfo_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: querySysroleInfogopage
                });
            }else{
                layer.alert(data.message);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                layer.alert("操作超时！");
            }
        }
    });
}

function querySysroleInfogopage(currentPage) {
    searchSysroleInfo(currentPage);
}

function addSysroleInfo(){
    var url=webUrl+"/sysroleInfo/insert.dhtml";
    var params={};
    params.roleNo = $("#validateAddSysroleInfoForm #roleNo1").val();
    params.roleName = $("#validateAddSysroleInfoForm #roleName1").val();
    params.applicationCode = $("#validateAddSysroleInfoForm #applicationCode1").val();
    params.createOprNo = $("#validateAddSysroleInfoForm #createOprNo1").val();
    params.createOprName = $("#validateAddSysroleInfoForm #createOprName1").val();
    params.roleOthMsg = $("#validateAddSysroleInfoForm #roleOthMsg1").val();
    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    $.ajax({
        type:"POST",
        url:url,
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $('#addSysroleInfo').modal('hide');
                searchSysroleInfo('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddSysroleInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddSysroleInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddSysroleInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddSysroleInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateSysroleInfo(){
    var url=webUrl+"/sysroleInfo/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateSysroleInfoForm #id_key").val();
    params.roleNo = $("#validateUpdateSysroleInfoForm #roleNo3").val();
    params.roleName = $("#validateUpdateSysroleInfoForm #roleName3").val();
    params.applicationCode = $("#validateUpdateSysroleInfoForm #applicationCode3").val();
    params.roleOthMsg = $("#validateUpdateSysroleInfoForm #roleOthMsg3").val();
    params.createTime = $("#validateUpdateSysroleInfoForm #createTime3").val();
    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    $.ajax({
        type:"POST",
        url:url,
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $('#updateSysroleInfo').modal('hide');
                searchSysroleInfo('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateSysroleInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateSysroleInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateSysroleInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateSysroleInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editSysroleInfo(id_key){
    var url=webUrl+"/sysroleInfo/queryObject.dhtml";
    var params={};
    params.id_key = id_key;;
    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    $.ajax({
        type:"POST",
        url:url,
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $('#updateSysroleInfo').modal();
                $("#validateUpdateSysroleInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysroleInfo;
                $("#validateUpdateSysroleInfoForm #roleNo3").val(objInfo.roleNo!=null?objInfo.roleNo:'');
                $("#validateUpdateSysroleInfoForm #roleName3").val(objInfo.roleName!=null?objInfo.roleName:'');
                $("#validateUpdateSysroleInfoForm #applicationCode3").val(objInfo.applicationCode!=null?objInfo.applicationCode:'');
                $("#validateUpdateSysroleInfoForm #createOprNo3").val(objInfo.createOprNo!=null?objInfo.createOprNo:'');
                $("#validateUpdateSysroleInfoForm #createOprName3").val(objInfo.createOprName!=null?objInfo.createOprName:'');
                $("#validateUpdateSysroleInfoForm #roleOthMsg3").val(objInfo.roleOthMsg!=null?objInfo.roleOthMsg:'');
                $("#validateUpdateSysroleInfoForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
            }else{
                layer.alert(data.message);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
            }
        }
    });
}

function querySysroleInfoObject(id_key){
    var url=webUrl+"/sysroleInfo/queryObject.dhtml";
    var params={};
    params.id_key = id_key;;
    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    $.ajax({
        type:"POST",
        url:url,
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $('#detailSysroleInfo').modal();
                $("#validateDetailSysroleInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysroleInfo;
                $("#validateDetailSysroleInfoForm #roleNo2").val(objInfo.roleNo!=null?objInfo.roleNo:'');
                $("#validateDetailSysroleInfoForm #roleName2").val(objInfo.roleName!=null?objInfo.roleName:'');
                $("#validateDetailSysroleInfoForm #applicationCode2").val(objInfo.applicationCode_name!=null?objInfo.applicationCode_name:'');
                $("#validateDetailSysroleInfoForm #createOprNo2").val(objInfo.createOprNo!=null?objInfo.createOprNo:'');
                $("#validateDetailSysroleInfoForm #createOprName2").val(objInfo.createOprName!=null?objInfo.createOprName:'');
                $("#validateDetailSysroleInfoForm #roleOthMsg2").val(objInfo.roleOthMsg!=null?objInfo.roleOthMsg:'');
                $("#validateDetailSysroleInfoForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
            }else{
                layer.alert(data.message);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
            }
        }
    });
}
function delSysroleInfo(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/sysroleInfo/del.dhtml";
        var params={};
        var currentPage= $("#querySysroleInfocurrentPage").val();
        params.id_key = id_key;;
        var jsonStr = JSON.stringify(params);
        jsonStr = encodeURI(jsonStr);
        $.ajax({
            type:"POST",
            url:url,
            timeout:60000,
            dataType:'json',
            data:"jsonStr="+jsonStr,
            success:function(data){
                if(data.code == "success"){
                    layer.msg('删除成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                    searchSysroleInfo(currentPage);
                }else{
                    layer.alert(data.message);
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                layer.alert('系统异常，请稍后重试！');
            },
            complete : function(XMLHttpRequest,status){
                if(status == "timeout"){
                    ajaxTimeoutTest.abort();
                    alert("操作超时！");
                }
            }
        });
    });
}


