$(function(){
    searchSysuserInfo('');
    $('#querySysuserInfopageSize').on('change',function () {
        var currentPage = $("#querySysuserInfocurrentPage").val();
        searchSysuserInfo(currentPage);
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
    $('#updateSysuserInfo').on('shown.bs.modal', function () {
        $('#validateUpdateSysuserInfoForm').bootstrapValidator('resetForm', false);
    });
    $('#querySysuserInfoForm #lastLoginTm').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysuserInfoForm","lastLoginTm");
    });
    $('#querySysuserInfoForm #createTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysuserInfoForm","createTime");
    });
    $('#validateAddSysuserInfoForm #createTime1').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateAddSysuserInfoForm","createTime1");
    });
    $('#validateUpdateSysuserInfoForm #createTime3').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateUpdateSysuserInfoForm","createTime3");
    });

    //增加系统用户表校验
    $('#validateAddSysuserInfoForm').bootstrapValidator({
        fields : {
            userName1 : {
                validators : {"notEmpty":{"message":"用户姓名不能为空"},"stringLength":{"min":1,"max":20,"message":"用户姓名长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"用户姓名只能是中文、英文、数字与下划线"}}
            }
            ,
            userNickname1 : {
                validators : {"notEmpty":{"message":"用户昵称不能为空"},"stringLength":{"min":1,"max":20,"message":"用户昵称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"用户昵称只能是中文、英文、数字与下划线"}}
            }
            ,
            loginName1 : {
                validators : {"notEmpty":{"message":"用户登录帐号不能为空"},"stringLength":{"min":3,"max":40,"message":"用户登录帐号长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_\\-]+$","message":"用户登录帐号只能是英文、数字、下划线、中划线"}}
            }
            ,
            password1 : {
                validators : {"notEmpty":{"message":"用户密码不能为空"},"stringLength":{"min":3,"max":16,"message":"用户密码长度不正确"},"regexp":{"regexp":"^[^`;\\$\\.]{3,16}$","message":"用户密码不能包含特殊字符"}}
            }
            ,
            userStatus1 : {
                validators : {"notEmpty":{"message":"请选择一个用户状态"}}
            }
            ,
            applicationCode1 : {
                validators : {"notEmpty":{"message":"请选择一个应用名称"}}
            }
            ,
            createTime1 : {
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
         addSysuserInfo();
     });

    //修改系统用户表校验
    $('#validateUpdateSysuserInfoForm').bootstrapValidator({
        fields : {
            userName3 : {
                validators : {"notEmpty":{"message":"用户姓名不能为空"},"stringLength":{"min":1,"max":20,"message":"用户姓名长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"用户姓名只能是中文、英文、数字与下划线"}}
            }
            ,
            userNickname3 : {
                validators : {"notEmpty":{"message":"用户昵称不能为空"},"stringLength":{"min":1,"max":20,"message":"用户昵称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"用户昵称只能是中文、英文、数字与下划线"}}
            }
            ,
            password3 : {
                validators : {"notEmpty":{"message":"用户密码不能为空"},"stringLength":{"min":3,"max":16,"message":"用户密码长度不正确"},"regexp":{"regexp":"^[^`;\\$\\.]{3,16}$","message":"用户密码不能包含特殊字符"}}
            }
            ,
            userStatus3 : {
                validators : {"notEmpty":{"message":"请选择一个用户状态"}}
            }
            ,
            applicationCode3 : {
                validators : {"notEmpty":{"message":"请选择一个应用名称"}}
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
         updateSysuserInfo();
     });
    //查询系统用户表校验
    $('#querySysuserInfoForm').bootstrapValidator({
        fields : {
            userNo : {
                validators : {"stringLength":{"min":1,"max":100,"message":"用户编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"用户编号与要求的格式不符"}}
            }
            ,
            userName : {
                validators : {"stringLength":{"min":1,"max":20,"message":"用户姓名长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"用户姓名只能是中文、英文、数字与下划线"}}
            }
            ,
            userNickname : {
                validators : {"stringLength":{"min":1,"max":20,"message":"用户昵称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"用户昵称只能是中文、英文、数字与下划线"}}
            }
            ,
            loginName : {
                validators : {"stringLength":{"min":3,"max":40,"message":"用户登录帐号长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_\\-]+$","message":"用户登录帐号只能是英文、数字、下划线、中划线"}}
            }
            ,
            userStatus : {
                validators : {}
            }
            ,
            applicationCode : {
                validators : {}
            }
            ,
            lastLoginTm : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^.+$","message":"XX信息与要求的格式不符"}}
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

function searchSysuserInfo(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#querySysuserInfoForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#querySysuserInfoForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#querySysuserInfoForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#querySysuserInfoForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#querySysuserInfocurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#querySysuserInfopageSize").val();
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

    params.userNo = $("#querySysuserInfoForm #userNo").val();
    params.userName = $("#querySysuserInfoForm #userName").val();
    params.userNickname = $("#querySysuserInfoForm #userNickname").val();
    params.loginName = $("#querySysuserInfoForm #loginName").val();
    params.userStatus = $("#querySysuserInfoForm #userStatus").val();
    params.applicationCode = $("#querySysuserInfoForm #applicationCode").val();
    params.lastLoginTm = $("#querySysuserInfoForm #lastLoginTm").val();
    params.createTime = $("#querySysuserInfoForm #createTime").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/sysuserInfo/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/sysuserInfo/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#sysuserInfoTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#querySysuserInfocurrentPage").val(data.rows[0].startIndex);
                $("#querySysuserInfototalPage").val(data.rows[0].totalPage);
                $("#querySysuserInfopageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var sysuserInfoTr = $("<tr class=\"sysuserInfoTr\"></tr>");
                    $("#sysuserInfoTR_FIRST").parent().append(sysuserInfoTr);
                    sysuserInfoTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].userNo+"</td>")
                    .append("<td>"+resultList[i].userName+"</td>")
                    .append("<td>"+resultList[i].userNickname+"</td>")
                    .append("<td>"+resultList[i].loginName+"</td>")
                    .append("<td>"+resultList[i].userStatus_name+"</td>")
                    .append("<td>"+resultList[i].applicationCode_name+"</td>")
                    .append("<td>"+resultList[i].lastLoginIp+"</td>")
                    .append("<td>"+resultList[i].lastLoginTm+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editSysuserInfo('"+resultList[i].userNo+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delSysuserInfo('"+resultList[i].userNo+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return querySysuserInfoObject('"+resultList[i].userNo+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#querySysuserInfocurrentPage").val());
                var totalPage   = Number($("#querySysuserInfototalPage").val());
                var pageSize    = Number($("#querySysuserInfopageSize").val());
                $("#querySysuserInfo_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: querySysuserInfogopage
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

function querySysuserInfogopage(currentPage) {
    searchSysuserInfo(currentPage);
}

function addSysuserInfo(){
    var url=webUrl+"/sysuserInfo/insert.dhtml";
    var params={};
    params.userName = $("#validateAddSysuserInfoForm #userName1").val();
    params.userNickname = $("#validateAddSysuserInfoForm #userNickname1").val();
    params.loginName = $("#validateAddSysuserInfoForm #loginName1").val();
    params.password = $("#validateAddSysuserInfoForm #password1").val();
    params.userStatus = $("#validateAddSysuserInfoForm #userStatus1").val();
    params.applicationCode = $("#validateAddSysuserInfoForm #applicationCode1").val();
    params.createTime = $("#validateAddSysuserInfoForm #createTime1").val();
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
                $('#addSysuserInfo').modal('hide');
                searchSysuserInfo('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddSysuserInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddSysuserInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddSysuserInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddSysuserInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateSysuserInfo(){
    var url=webUrl+"/sysuserInfo/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateSysuserInfoForm #id_key").val();
    params.userName = $("#validateUpdateSysuserInfoForm #userName3").val();
    params.userNickname = $("#validateUpdateSysuserInfoForm #userNickname3").val();
    params.password = $("#validateUpdateSysuserInfoForm #password3").val();
    params.userStatus = $("#validateUpdateSysuserInfoForm #userStatus3").val();
    params.applicationCode = $("#validateUpdateSysuserInfoForm #applicationCode3").val();
    params.createTime = $("#validateUpdateSysuserInfoForm #createTime3").val();
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
                $('#updateSysuserInfo').modal('hide');
                searchSysuserInfo('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateSysuserInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateSysuserInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateSysuserInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateSysuserInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editSysuserInfo(id_key){
    var url=webUrl+"/sysuserInfo/queryObject.dhtml";
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
                $('#updateSysuserInfo').modal();
                $("#validateUpdateSysuserInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysuserInfo;
                $("#validateUpdateSysuserInfoForm #userNo3").val(objInfo.userNo!=null?objInfo.userNo:'');
                $("#validateUpdateSysuserInfoForm #userName3").val(objInfo.userName!=null?objInfo.userName:'');
                $("#validateUpdateSysuserInfoForm #userNickname3").val(objInfo.userNickname!=null?objInfo.userNickname:'');
                $("#validateUpdateSysuserInfoForm #loginName3").val(objInfo.loginName!=null?objInfo.loginName:'');
                $("#validateUpdateSysuserInfoForm #password3").val(objInfo.password!=null?objInfo.password:'');
                $("#validateUpdateSysuserInfoForm #userStatus3").val(objInfo.userStatus!=null?objInfo.userStatus:'');
                $("#validateUpdateSysuserInfoForm #applicationCode3").val(objInfo.applicationCode!=null?objInfo.applicationCode:'');
                $("#validateUpdateSysuserInfoForm #lastLoginIp3").val(objInfo.lastLoginIp!=null?objInfo.lastLoginIp:'');
                $("#validateUpdateSysuserInfoForm #lastLoginTm3").val(objInfo.lastLoginTm!=null?objInfo.lastLoginTm:'');
                $("#validateUpdateSysuserInfoForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
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

function querySysuserInfoObject(id_key){
    var url=webUrl+"/sysuserInfo/queryObject.dhtml";
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
                $('#detailSysuserInfo').modal();
                $("#validateDetailSysuserInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysuserInfo;
                $("#validateDetailSysuserInfoForm #userNo2").val(objInfo.userNo!=null?objInfo.userNo:'');
                $("#validateDetailSysuserInfoForm #userName2").val(objInfo.userName!=null?objInfo.userName:'');
                $("#validateDetailSysuserInfoForm #userNickname2").val(objInfo.userNickname!=null?objInfo.userNickname:'');
                $("#validateDetailSysuserInfoForm #loginName2").val(objInfo.loginName!=null?objInfo.loginName:'');
                $("#validateDetailSysuserInfoForm #userStatus2").val(objInfo.userStatus_name!=null?objInfo.userStatus_name:'');
                $("#validateDetailSysuserInfoForm #applicationCode2").val(objInfo.applicationCode_name!=null?objInfo.applicationCode_name:'');
                $("#validateDetailSysuserInfoForm #lastLoginIp2").val(objInfo.lastLoginIp!=null?objInfo.lastLoginIp:'');
                $("#validateDetailSysuserInfoForm #lastLoginTm2").val(objInfo.lastLoginTm!=null?objInfo.lastLoginTm:'');
                $("#validateDetailSysuserInfoForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
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
function delSysuserInfo(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/sysuserInfo/del.dhtml";
        var params={};
        var currentPage= $("#querySysuserInfocurrentPage").val();
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
                    searchSysuserInfo(currentPage);
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


