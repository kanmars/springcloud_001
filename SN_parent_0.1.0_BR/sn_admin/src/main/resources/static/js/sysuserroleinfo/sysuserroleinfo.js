$(function(){
    searchSysuserRoleInfo('');
    $('#querySysuserRoleInfopageSize').on('change',function () {
        var currentPage = $("#querySysuserRoleInfocurrentPage").val();
        searchSysuserRoleInfo(currentPage);
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
    $('#updateSysuserRoleInfo').on('shown.bs.modal', function () {
        $('#validateUpdateSysuserRoleInfoForm').bootstrapValidator('resetForm', false);
    });

    //增加系统用户角色绑定表校验
    $('#validateAddSysuserRoleInfoForm').bootstrapValidator({
        fields : {
            userNo1 : {
                validators : {"notEmpty":{"message":"用户编号不能为空"},"stringLength":{"min":1,"max":100,"message":"用户编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"用户编号与要求的格式不符"}}
            }
            ,
            roleNo1 : {
                validators : {"notEmpty":{"message":"角色编号不能为空"},"stringLength":{"min":1,"max":10,"message":"角色编号长度不正确"},"regexp":{"regexp":"^[0-9]{4,10}$","message":"角色编号只能是数字"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addSysuserRoleInfo();
     });

    //修改系统用户角色绑定表校验
    $('#validateUpdateSysuserRoleInfoForm').bootstrapValidator({
        fields : {
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         updateSysuserRoleInfo();
     });
    //查询系统用户角色绑定表校验
    $('#querySysuserRoleInfoForm').bootstrapValidator({
        fields : {
            userNo : {
                validators : {"stringLength":{"min":1,"max":100,"message":"用户编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"用户编号与要求的格式不符"}}
            }
            ,
            roleNo : {
                validators : {"stringLength":{"min":1,"max":10,"message":"角色编号长度不正确"},"regexp":{"regexp":"^[0-9]{4,10}$","message":"角色编号只能是数字"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         console.log("查询校验通过");
     });

});

function searchSysuserRoleInfo(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#querySysuserRoleInfoForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#querySysuserRoleInfoForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#querySysuserRoleInfoForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#querySysuserRoleInfoForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#querySysuserRoleInfocurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#querySysuserRoleInfopageSize").val();
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

    params.userNo = $("#querySysuserRoleInfoForm #userNo").val();
    params.roleNo = $("#querySysuserRoleInfoForm #roleNo").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/sysuserRoleInfo/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/sysuserRoleInfo/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#sysuserRoleInfoTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#querySysuserRoleInfocurrentPage").val(data.rows[0].startIndex);
                $("#querySysuserRoleInfototalPage").val(data.rows[0].totalPage);
                $("#querySysuserRoleInfopageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var sysuserRoleInfoTr = $("<tr class=\"sysuserRoleInfoTr\"></tr>");
                    $("#sysuserRoleInfoTR_FIRST").parent().append(sysuserRoleInfoTr);
                    sysuserRoleInfoTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].userNo+"</td>")
                    .append("<td>"+resultList[i].roleNo+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td>"+resultList[i].upTime+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editSysuserRoleInfo('"+ resultList[i].userNo+"," +  resultList[i].roleNo+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delSysuserRoleInfo('"+ resultList[i].userNo+"," +  resultList[i].roleNo+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return querySysuserRoleInfoObject('"+ resultList[i].userNo+"," +  resultList[i].roleNo+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#querySysuserRoleInfocurrentPage").val());
                var totalPage   = Number($("#querySysuserRoleInfototalPage").val());
                var pageSize    = Number($("#querySysuserRoleInfopageSize").val());
                $("#querySysuserRoleInfo_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: querySysuserRoleInfogopage
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

function querySysuserRoleInfogopage(currentPage) {
    searchSysuserRoleInfo(currentPage);
}

function addSysuserRoleInfo(){
    var url=webUrl+"/sysuserRoleInfo/insert.dhtml";
    var params={};
    params.userNo = $("#validateAddSysuserRoleInfoForm #userNo1").val();
    params.roleNo = $("#validateAddSysuserRoleInfoForm #roleNo1").val();
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
                $('#addSysuserRoleInfo').modal('hide');
                searchSysuserRoleInfo('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddSysuserRoleInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddSysuserRoleInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddSysuserRoleInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddSysuserRoleInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateSysuserRoleInfo(){
    var url=webUrl+"/sysuserRoleInfo/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateSysuserRoleInfoForm #id_key").val();
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
                $('#updateSysuserRoleInfo').modal('hide');
                searchSysuserRoleInfo('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateSysuserRoleInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateSysuserRoleInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateSysuserRoleInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateSysuserRoleInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editSysuserRoleInfo(id_key){
    var url=webUrl+"/sysuserRoleInfo/queryObject.dhtml";
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
                $('#updateSysuserRoleInfo').modal();
                $("#validateUpdateSysuserRoleInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysuserRoleInfo;
                $("#validateUpdateSysuserRoleInfoForm #userNo3").val(objInfo.userNo!=null?objInfo.userNo:'');
                $("#validateUpdateSysuserRoleInfoForm #roleNo3").val(objInfo.roleNo!=null?objInfo.roleNo:'');
                $("#validateUpdateSysuserRoleInfoForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
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

function querySysuserRoleInfoObject(id_key){
    var url=webUrl+"/sysuserRoleInfo/queryObject.dhtml";
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
                $('#detailSysuserRoleInfo').modal();
                $("#validateDetailSysuserRoleInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysuserRoleInfo;
                $("#validateDetailSysuserRoleInfoForm #userNo2").val(objInfo.userNo!=null?objInfo.userNo:'');
                $("#validateDetailSysuserRoleInfoForm #roleNo2").val(objInfo.roleNo!=null?objInfo.roleNo:'');
                $("#validateDetailSysuserRoleInfoForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
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
function delSysuserRoleInfo(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/sysuserRoleInfo/del.dhtml";
        var params={};
        var currentPage= $("#querySysuserRoleInfocurrentPage").val();
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
                    searchSysuserRoleInfo(currentPage);
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


