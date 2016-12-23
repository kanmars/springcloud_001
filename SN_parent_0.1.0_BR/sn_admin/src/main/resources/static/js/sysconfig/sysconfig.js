$(function(){
    searchSysConfig('');
    $('#querySysConfigpageSize').on('change',function () {
        var currentPage = $("#querySysConfigcurrentPage").val();
        searchSysConfig(currentPage);
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
    $('#updateSysConfig').on('shown.bs.modal', function () {
        $('#validateUpdateSysConfigForm').bootstrapValidator('resetForm', false);
    });
    $('#querySysConfigForm #createTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysConfigForm","createTime");
    });

    //增加系统配置表校验
    $('#validateAddSysConfigForm').bootstrapValidator({
        fields : {
            version1 : {
                validators : {"notEmpty":{"message":"版本号不能为空"},"stringLength":{"min":1,"max":32,"message":"版本号长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_\\.]+$","message":"版本号只能是英文、数字与下划线"}}
            }
            ,
            systemName1 : {
                validators : {"notEmpty":{"message":"系统名称不能为空"},"stringLength":{"min":1,"max":30,"message":"系统名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"系统名称只能是中文、英文、数字与下划线"}}
            }
            ,
            imgUrl1 : {
                validators : {"notEmpty":{"message":"图片路径不能为空"},"stringLength":{"min":1,"max":100,"message":"图片路径长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_/\\-:\\.]+$","message":"图片路径只能是中文、英文、数字、下划线、斜杠、点"}}
            }
            ,
            status1 : {
                validators : {"notEmpty":{"message":"请选择一个静态选择框"}}
            }
            ,
            createName1 : {
                validators : {"notEmpty":{"message":"创建人名称不能为空"},"stringLength":{"min":1,"max":20,"message":"创建人名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]{1,20}$","message":"创建人名称只能填写中文、英文、数字和下划线"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addSysConfig();
     });

    //修改系统配置表校验
    $('#validateUpdateSysConfigForm').bootstrapValidator({
        fields : {
            version3 : {
                validators : {"notEmpty":{"message":"版本号不能为空"},"stringLength":{"min":1,"max":32,"message":"版本号长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_\\.]+$","message":"版本号只能是英文、数字与下划线"}}
            }
            ,
            systemName3 : {
                validators : {"notEmpty":{"message":"系统名称不能为空"},"stringLength":{"min":1,"max":30,"message":"系统名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"系统名称只能是中文、英文、数字与下划线"}}
            }
            ,
            imgUrl3 : {
                validators : {"notEmpty":{"message":"图片路径不能为空"},"stringLength":{"min":1,"max":100,"message":"图片路径长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_/\\-:\\.]+$","message":"图片路径只能是中文、英文、数字、下划线、斜杠、点"}}
            }
            ,
            status3 : {
                validators : {"notEmpty":{"message":"请选择一个静态选择框"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         updateSysConfig();
     });
    //查询系统配置表校验
    $('#querySysConfigForm').bootstrapValidator({
        fields : {
            id : {
                validators : {"stringLength":{"min":1,"max":10,"message":"主键id长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"主键id与要求的格式不符"}}
            }
            ,
            version : {
                validators : {"stringLength":{"min":1,"max":32,"message":"版本号长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_\\.]+$","message":"版本号只能是英文、数字与下划线"}}
            }
            ,
            systemName : {
                validators : {"stringLength":{"min":1,"max":30,"message":"系统名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"系统名称只能是中文、英文、数字与下划线"}}
            }
            ,
            imgUrl : {
                validators : {"stringLength":{"min":1,"max":100,"message":"图片路径长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_/\\-:\\.]+$","message":"图片路径只能是中文、英文、数字、下划线、斜杠、点"}}
            }
            ,
            status : {
                validators : {}
            }
            ,
            createTime : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^.+$","message":"XX信息与要求的格式不符"}}
            }
            ,
            createName : {
                validators : {"stringLength":{"min":1,"max":20,"message":"创建人名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]{1,20}$","message":"创建人名称只能填写中文、英文、数字和下划线"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         console.log("查询校验通过");
     });

});

function searchSysConfig(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#querySysConfigForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#querySysConfigForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#querySysConfigForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#querySysConfigForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#querySysConfigcurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#querySysConfigpageSize").val();
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

    params.id = $("#querySysConfigForm #id").val();
    params.version = $("#querySysConfigForm #version").val();
    params.systemName = $("#querySysConfigForm #systemName").val();
    params.imgUrl = $("#querySysConfigForm #imgUrl").val();
    params.status = $("#querySysConfigForm #status").val();
    params.createTime = $("#querySysConfigForm #createTime").val();
    params.createName = $("#querySysConfigForm #createName").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/sysConfig/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/sysConfig/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#sysConfigTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#querySysConfigcurrentPage").val(data.rows[0].startIndex);
                $("#querySysConfigtotalPage").val(data.rows[0].totalPage);
                $("#querySysConfigpageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var sysConfigTr = $("<tr class=\"sysConfigTr\"></tr>");
                    $("#sysConfigTR_FIRST").parent().append(sysConfigTr);
                    sysConfigTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].id+"</td>")
                    .append("<td>"+resultList[i].version+"</td>")
                    .append("<td>"+resultList[i].systemName+"</td>")
                    .append("<td>"+resultList[i].imgUrl+"</td>")
                    .append("<td>"+resultList[i].status_name+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td>"+resultList[i].createName+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editSysConfig('"+resultList[i].id+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delSysConfig('"+resultList[i].id+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return querySysConfigObject('"+resultList[i].id+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#querySysConfigcurrentPage").val());
                var totalPage   = Number($("#querySysConfigtotalPage").val());
                var pageSize    = Number($("#querySysConfigpageSize").val());
                $("#querySysConfig_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: querySysConfiggopage
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

function querySysConfiggopage(currentPage) {
    searchSysConfig(currentPage);
}

function addSysConfig(){
    var url=webUrl+"/sysConfig/insert.dhtml";
    var params={};
    params.version = $("#validateAddSysConfigForm #version1").val();
    params.systemName = $("#validateAddSysConfigForm #systemName1").val();
    params.imgUrl = $("#validateAddSysConfigForm #imgUrl1").val();
    params.status = $("#validateAddSysConfigForm #status1").val();
    params.createName = $("#validateAddSysConfigForm #createName1").val();
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
                $('#addSysConfig').modal('hide');
                searchSysConfig('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddSysConfigForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddSysConfigForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddSysConfigForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddSysConfigForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateSysConfig(){
    var url=webUrl+"/sysConfig/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateSysConfigForm #id_key").val();
    params.version = $("#validateUpdateSysConfigForm #version3").val();
    params.systemName = $("#validateUpdateSysConfigForm #systemName3").val();
    params.imgUrl = $("#validateUpdateSysConfigForm #imgUrl3").val();
    params.status = $("#validateUpdateSysConfigForm #status3").val();
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
                $('#updateSysConfig').modal('hide');
                searchSysConfig('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateSysConfigForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateSysConfigForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateSysConfigForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateSysConfigForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editSysConfig(id_key){
    var url=webUrl+"/sysConfig/queryObject.dhtml";
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
                $('#updateSysConfig').modal();
                $("#validateUpdateSysConfigForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysConfig;
                $("#validateUpdateSysConfigForm #id3").val(objInfo.id!=null?objInfo.id:'');
                $("#validateUpdateSysConfigForm #version3").val(objInfo.version!=null?objInfo.version:'');
                $("#validateUpdateSysConfigForm #systemName3").val(objInfo.systemName!=null?objInfo.systemName:'');
                $("#validateUpdateSysConfigForm #imgUrl3").val(objInfo.imgUrl!=null?objInfo.imgUrl:'');
                $("#validateUpdateSysConfigForm #status3").val(objInfo.status!=null?objInfo.status:'');
                $("#validateUpdateSysConfigForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateUpdateSysConfigForm #createName3").val(objInfo.createName!=null?objInfo.createName:'');
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

function querySysConfigObject(id_key){
    var url=webUrl+"/sysConfig/queryObject.dhtml";
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
                $('#detailSysConfig').modal();
                $("#validateDetailSysConfigForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysConfig;
                $("#validateDetailSysConfigForm #id2").val(objInfo.id!=null?objInfo.id:'');
                $("#validateDetailSysConfigForm #version2").val(objInfo.version!=null?objInfo.version:'');
                $("#validateDetailSysConfigForm #systemName2").val(objInfo.systemName!=null?objInfo.systemName:'');
                $("#validateDetailSysConfigForm #imgUrl2").val(objInfo.imgUrl!=null?objInfo.imgUrl:'');
                $("#validateDetailSysConfigForm #status2").val(objInfo.status_name!=null?objInfo.status_name:'');
                $("#validateDetailSysConfigForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateDetailSysConfigForm #createName2").val(objInfo.createName!=null?objInfo.createName:'');
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
function delSysConfig(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/sysConfig/del.dhtml";
        var params={};
        var currentPage= $("#querySysConfigcurrentPage").val();
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
                    searchSysConfig(currentPage);
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


