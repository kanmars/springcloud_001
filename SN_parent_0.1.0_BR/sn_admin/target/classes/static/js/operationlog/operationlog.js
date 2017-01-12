$(function(){
    searchOperationLog('');
    $('#queryOperationLogpageSize').on('change',function () {
        var currentPage = $("#queryOperationLogcurrentPage").val();
        searchOperationLog(currentPage);
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
    $('#updateOperationLog').on('shown.bs.modal', function () {
        $('#validateUpdateOperationLogForm').bootstrapValidator('resetForm', false);
    });
    $('#queryOperationLogForm #operationTime').daterangepicker({
        showAfterOpen:false,
        //startDate:"2016-12-03 12:59:59",
        //endDate:"2016-12-06 12:59:59",
        //minDate:"2016-12-02 12:00:00",
        //maxDate:"2016-12-31 12:00:00",
        timePicker: true,
        timePicker24Hour: true,
        timePickerSeconds: true,
        locale:{
            format: 'YYYY-MM-DD HH:mm:ss'
        }
    },function(start, end, label) {
        console.log(start.toISOString(), end.toISOString(), label);
    }).change(function(){
        revalidateDate("queryOperationLogForm","operationTime");
    });
    $('#queryOperationLogForm #createTime').daterangepicker({
        showAfterOpen:false,
        //startDate:"2016-12-03 12:59:59",
        //endDate:"2016-12-06 12:59:59",
        //minDate:"2016-12-02 12:00:00",
        //maxDate:"2016-12-31 12:00:00",
        timePicker: true,
        timePicker24Hour: true,
        timePickerSeconds: true,
        locale:{
            format: 'YYYY-MM-DD HH:mm:ss'
        }
    },function(start, end, label) {
        console.log(start.toISOString(), end.toISOString(), label);
    }).change(function(){
        revalidateDate("queryOperationLogForm","createTime");
    });

    //增加操作日志表校验
    $('#validateAddOperationLogForm').bootstrapValidator({
        fields : {
            operationName1 : {
                validators : {"stringLength":{"message":"操作名称长度不正确","min":1,"max":40},"notEmpty":{"message":"操作名称不能为空"},"regexp":{"message":"操作名称只能是中文、英文、数字与下划线","regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_] $"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addOperationLog();
     });

    //修改操作日志表校验
    $('#validateUpdateOperationLogForm').bootstrapValidator({
        fields : {
            operationName3 : {
                validators : {"stringLength":{"message":"操作名称长度不正确","min":1,"max":40},"notEmpty":{"message":"操作名称不能为空"},"regexp":{"message":"操作名称只能是中文、英文、数字与下划线","regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_] $"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         updateOperationLog();
     });
    //查询操作日志表校验
    $('#queryOperationLogForm').bootstrapValidator({
        fields : {
            operationUser : {
                validators : {"stringLength":{"message":"操作员长度不正确","min":1,"max":40},"regexp":{"message":"操作员只能是中文、英文、数字与下划线","regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]*$"}}
            }
            ,
            operationTime : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})$","message":"时间格式不正确"}}
            }
            ,
            operationName : {
                validators : {"stringLength":{"message":"操作名称长度不正确","min":1,"max":40},"regexp":{"message":"操作名称只能是中文、英文、数字与下划线","regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]*$"}}
            }
            ,
            operationApp : {
                validators : {"stringLength":{"message":"操作应用长度不正确","min":1,"max":40},"regexp":{"message":"操作应用只能是中文、英文、数字与下划线","regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]*$"}}
            }
            ,
            operationClassmethod : {
                validators : {"stringLength":{"message":"操作类与方法长度不正确","min":1,"max":170},"regexp":{"message":"操作类与方法只能是中文、英文、数字与下划线","regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]*$"}}
            }
            ,
            operationInfo : {
                validators : {"stringLength":{"message":"操作信息长度不正确","min":1,"max":650},"regexp":{"message":"操作信息只能是中文、英文、数字与下划线","regexp":"^.*$"}}
            }
            ,
            createTime : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})$","message":"时间格式不正确"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         console.log("查询校验通过");
     });

});

function searchOperationLog(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#queryOperationLogForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#queryOperationLogForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#queryOperationLogForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#queryOperationLogForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#queryOperationLogcurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#queryOperationLogpageSize").val();
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

    params.operationUser = $("#queryOperationLogForm #operationUser").val();
    var operationTime_regexp = new RegExp('^([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})$');
    var operationTime_value = $("#queryOperationLogForm #operationTime").val();
    if(operationTime_regexp.test(operationTime_value)){
        var cs = operationTime_regexp.exec(operationTime_value);
        params.operationTime_start = cs[1]||'';
        params.operationTime_end = cs[4]||'';
    }
    
    params.operationName = $("#queryOperationLogForm #operationName").val();
    params.operationApp = $("#queryOperationLogForm #operationApp").val();
    params.operationClassmethod = $("#queryOperationLogForm #operationClassmethod").val();
    params.operationInfo = $("#queryOperationLogForm #operationInfo").val();
    var createTime_regexp = new RegExp('^([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})$');
    var createTime_value = $("#queryOperationLogForm #createTime").val();
    if(createTime_regexp.test(createTime_value)){
        var cs = createTime_regexp.exec(createTime_value);
        params.createTime_start = cs[1]||'';
        params.createTime_end = cs[4]||'';
    }
    

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/operationLog/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/operationLog/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#operationLogTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#queryOperationLogcurrentPage").val(data.rows[0].startIndex);
                $("#queryOperationLogtotalPage").val(data.rows[0].totalPage);
                $("#queryOperationLogpageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var operationLogTr = $("<tr class=\"operationLogTr\"></tr>");
                    $("#operationLogTR_FIRST").parent().append(operationLogTr);
                    operationLogTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].operationUser+"</td>")
                    .append("<td>"+resultList[i].operationTime+"</td>")
                    .append("<td>"+resultList[i].operationName+"</td>")
                    .append("<td>"+resultList[i].operationApp+"</td>")
                    .append("<td>"+resultList[i].operationClassmethod+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return queryOperationLogObject('"+resultList[i].operationId+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#queryOperationLogcurrentPage").val());
                var totalPage   = Number($("#queryOperationLogtotalPage").val());
                var pageSize    = Number($("#queryOperationLogpageSize").val());
                $("#queryOperationLog_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: queryOperationLoggopage
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

function queryOperationLoggopage(currentPage) {
    searchOperationLog(currentPage);
}

function addOperationLog(){
    var url=webUrl+"/operationLog/insert.dhtml";
    var params={};
    params.operationName = $("#validateAddOperationLogForm #operationName1").val();
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
                $('#addOperationLog').modal('hide');
                searchOperationLog('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddOperationLogForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddOperationLogForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddOperationLogForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddOperationLogForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateOperationLog(){
    var url=webUrl+"/operationLog/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateOperationLogForm #id_key").val();
    params.operationName = $("#validateUpdateOperationLogForm #operationName3").val();
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
                $('#updateOperationLog').modal('hide');
                searchOperationLog('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateOperationLogForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateOperationLogForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateOperationLogForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateOperationLogForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editOperationLog(id_key){
    var url=webUrl+"/operationLog/queryObject.dhtml";
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
                $('#updateOperationLog').modal();
                $("#validateUpdateOperationLogForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblOperationLog;
                $("#validateUpdateOperationLogForm #operationId3").val(objInfo.operationId!=null?objInfo.operationId:'');
                $("#validateUpdateOperationLogForm #operationUser3").val(objInfo.operationUser!=null?objInfo.operationUser:'');
                $("#validateUpdateOperationLogForm #operationTime3").val(objInfo.operationTime!=null?objInfo.operationTime:'');
                $("#validateUpdateOperationLogForm #operationName3").val(objInfo.operationName!=null?objInfo.operationName:'');
                $("#validateUpdateOperationLogForm #operationDesc3").val(objInfo.operationDesc!=null?objInfo.operationDesc:'');
                $("#validateUpdateOperationLogForm #operationApp3").val(objInfo.operationApp!=null?objInfo.operationApp:'');
                $("#validateUpdateOperationLogForm #operationClassmethod3").val(objInfo.operationClassmethod!=null?objInfo.operationClassmethod:'');
                $("#validateUpdateOperationLogForm #operationInfo3").val(objInfo.operationInfo!=null?objInfo.operationInfo:'');
                $("#validateUpdateOperationLogForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
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

function queryOperationLogObject(id_key){
    var url=webUrl+"/operationLog/queryObject.dhtml";
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
                $('#detailOperationLog').modal();
                $("#validateDetailOperationLogForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblOperationLog;
                $("#validateDetailOperationLogForm #operationId2").val(objInfo.operationId!=null?objInfo.operationId:'');
                $("#validateDetailOperationLogForm #operationUser2").val(objInfo.operationUser!=null?objInfo.operationUser:'');
                $("#validateDetailOperationLogForm #operationTime2").val(objInfo.operationTime!=null?objInfo.operationTime:'');
                $("#validateDetailOperationLogForm #operationName2").val(objInfo.operationName!=null?objInfo.operationName:'');
                $("#validateDetailOperationLogForm #operationDesc2").val(objInfo.operationDesc!=null?objInfo.operationDesc:'');
                $("#validateDetailOperationLogForm #operationApp2").val(objInfo.operationApp!=null?objInfo.operationApp:'');
                $("#validateDetailOperationLogForm #operationClassmethod2").val(objInfo.operationClassmethod!=null?objInfo.operationClassmethod:'');
                $("#validateDetailOperationLogForm #operationInfo2").val(objInfo.operationInfo!=null?objInfo.operationInfo:'');
                $("#validateDetailOperationLogForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
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
function delOperationLog(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/operationLog/del.dhtml";
        var params={};
        var currentPage= $("#queryOperationLogcurrentPage").val();
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
                    searchOperationLog(currentPage);
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


