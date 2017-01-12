$(function(){
    searchSysroleMenuInfo('');
    $('#querySysroleMenuInfopageSize').on('change',function () {
        var currentPage = $("#querySysroleMenuInfocurrentPage").val();
        searchSysroleMenuInfo(currentPage);
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
    $('#updateSysroleMenuInfo').on('shown.bs.modal', function () {
        $('#validateUpdateSysroleMenuInfoForm').bootstrapValidator('resetForm', false);
    });
    $('#querySysroleMenuInfoForm #createTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysroleMenuInfoForm","createTime");
    });
    $('#validateUpdateSysroleMenuInfoForm #createTime3').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateUpdateSysroleMenuInfoForm","createTime3");
    });
    $('#querySysroleMenuInfoForm #upTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysroleMenuInfoForm","upTime");
    });

    //增加系统角色菜单绑定表校验
    $('#validateAddSysroleMenuInfoForm').bootstrapValidator({
        fields : {
            roleNo1 : {
                validators : {"notEmpty":{"message":"角色编号不能为空"},"stringLength":{"min":1,"max":10,"message":"角色编号长度不正确"},"regexp":{"regexp":"^[0-9]{4,10}$","message":"角色编号只能是数字"}}
            }
            ,
            menuNo1 : {
                validators : {"notEmpty":{"message":"菜单编号不能为空"},"stringLength":{"min":4,"max":8,"message":"菜单编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"菜单编号只能是数字"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addSysroleMenuInfo();
     });

    //修改系统角色菜单绑定表校验
    $('#validateUpdateSysroleMenuInfoForm').bootstrapValidator({
        fields : {
            roleNo3 : {
                validators : {"notEmpty":{"message":"角色编号不能为空"},"stringLength":{"min":1,"max":10,"message":"角色编号长度不正确"},"regexp":{"regexp":"^[0-9]{4,10}$","message":"角色编号只能是数字"}}
            }
            ,
            menuNo3 : {
                validators : {"notEmpty":{"message":"菜单编号不能为空"},"stringLength":{"min":4,"max":8,"message":"菜单编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"菜单编号只能是数字"}}
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
         updateSysroleMenuInfo();
     });
    //查询系统角色菜单绑定表校验
    $('#querySysroleMenuInfoForm').bootstrapValidator({
        fields : {
            roleNo : {
                validators : {"stringLength":{"min":1,"max":10,"message":"角色编号长度不正确"},"regexp":{"regexp":"^[0-9]{4,10}$","message":"角色编号只能是数字"}}
            }
            ,
            menuNo : {
                validators : {"stringLength":{"min":4,"max":8,"message":"菜单编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"菜单编号只能是数字"}}
            }
            ,
            createTime : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^.+$","message":"XX信息与要求的格式不符"}}
            }
            ,
            upTime : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^.+$","message":"XX信息与要求的格式不符"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         console.log("查询校验通过");
     });

});

function searchSysroleMenuInfo(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#querySysroleMenuInfoForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#querySysroleMenuInfoForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#querySysroleMenuInfoForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#querySysroleMenuInfoForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#querySysroleMenuInfocurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#querySysroleMenuInfopageSize").val();
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

    params.roleNo = $("#querySysroleMenuInfoForm #roleNo").val();
    params.menuNo = $("#querySysroleMenuInfoForm #menuNo").val();
    params.createTime = $("#querySysroleMenuInfoForm #createTime").val();
    params.upTime = $("#querySysroleMenuInfoForm #upTime").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/sysroleMenuInfo/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/sysroleMenuInfo/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#sysroleMenuInfoTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#querySysroleMenuInfocurrentPage").val(data.rows[0].startIndex);
                $("#querySysroleMenuInfototalPage").val(data.rows[0].totalPage);
                $("#querySysroleMenuInfopageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var sysroleMenuInfoTr = $("<tr class=\"sysroleMenuInfoTr\"></tr>");
                    $("#sysroleMenuInfoTR_FIRST").parent().append(sysroleMenuInfoTr);
                    sysroleMenuInfoTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].roleNo+"</td>")
                    .append("<td>"+resultList[i].menuNo+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td>"+resultList[i].upTime+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editSysroleMenuInfo('"+ resultList[i].roleNo+"," +  resultList[i].menuNo+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delSysroleMenuInfo('"+ resultList[i].roleNo+"," +  resultList[i].menuNo+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return querySysroleMenuInfoObject('"+ resultList[i].roleNo+"," +  resultList[i].menuNo+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#querySysroleMenuInfocurrentPage").val());
                var totalPage   = Number($("#querySysroleMenuInfototalPage").val());
                var pageSize    = Number($("#querySysroleMenuInfopageSize").val());
                $("#querySysroleMenuInfo_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: querySysroleMenuInfogopage
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

function querySysroleMenuInfogopage(currentPage) {
    searchSysroleMenuInfo(currentPage);
}

function addSysroleMenuInfo(){
    var url=webUrl+"/sysroleMenuInfo/insert.dhtml";
    var params={};
    params.roleNo = $("#validateAddSysroleMenuInfoForm #roleNo1").val();
    params.menuNo = $("#validateAddSysroleMenuInfoForm #menuNo1").val();
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
                $('#addSysroleMenuInfo').modal('hide');
                searchSysroleMenuInfo('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddSysroleMenuInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddSysroleMenuInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddSysroleMenuInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddSysroleMenuInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateSysroleMenuInfo(){
    var url=webUrl+"/sysroleMenuInfo/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateSysroleMenuInfoForm #id_key").val();
    params.roleNo = $("#validateUpdateSysroleMenuInfoForm #roleNo3").val();
    params.menuNo = $("#validateUpdateSysroleMenuInfoForm #menuNo3").val();
    params.createTime = $("#validateUpdateSysroleMenuInfoForm #createTime3").val();
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
                $('#updateSysroleMenuInfo').modal('hide');
                searchSysroleMenuInfo('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateSysroleMenuInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateSysroleMenuInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateSysroleMenuInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateSysroleMenuInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editSysroleMenuInfo(id_key){
    var url=webUrl+"/sysroleMenuInfo/queryObject.dhtml";
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
                $('#updateSysroleMenuInfo').modal();
                $("#validateUpdateSysroleMenuInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysroleMenuInfo;
                $("#validateUpdateSysroleMenuInfoForm #roleNo3").val(objInfo.roleNo!=null?objInfo.roleNo:'');
                $("#validateUpdateSysroleMenuInfoForm #menuNo3").val(objInfo.menuNo!=null?objInfo.menuNo:'');
                $("#validateUpdateSysroleMenuInfoForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateUpdateSysroleMenuInfoForm #upTime3").val(objInfo.upTime!=null?objInfo.upTime:'');
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

function querySysroleMenuInfoObject(id_key){
    var url=webUrl+"/sysroleMenuInfo/queryObject.dhtml";
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
                $('#detailSysroleMenuInfo').modal();
                $("#validateDetailSysroleMenuInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysroleMenuInfo;
                $("#validateDetailSysroleMenuInfoForm #roleNo2").val(objInfo.roleNo!=null?objInfo.roleNo:'');
                $("#validateDetailSysroleMenuInfoForm #menuNo2").val(objInfo.menuNo!=null?objInfo.menuNo:'');
                $("#validateDetailSysroleMenuInfoForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateDetailSysroleMenuInfoForm #upTime2").val(objInfo.upTime!=null?objInfo.upTime:'');
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
function delSysroleMenuInfo(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/sysroleMenuInfo/del.dhtml";
        var params={};
        var currentPage= $("#querySysroleMenuInfocurrentPage").val();
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
                    searchSysroleMenuInfo(currentPage);
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


