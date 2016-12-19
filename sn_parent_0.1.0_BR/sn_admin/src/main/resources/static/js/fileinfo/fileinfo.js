$(function(){
    searchFileInfo('');
    $('#queryFileInfopageSize').on('change',function () {
        var currentPage = $("#queryFileInfocurrentPage").val();
        searchFileInfo(currentPage);
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
    $('#updateFileInfo').on('shown.bs.modal', function () {
        $('#validateUpdateFileInfoForm').bootstrapValidator('resetForm', false);
    });
    $('#queryFileInfoForm #createTm').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("queryFileInfoForm","createTm");
    });

    //增加文件信息校验
    $('#validateAddFileInfoForm').bootstrapValidator({
        fields : {
            businessType1 : {
                validators : {"notEmpty":{"message":"请选择一个业务类型"}}
            }
            ,
            businessNo1 : {
                validators : {"notEmpty":{"message":"业务编号不能为空"},"stringLength":{"min":1,"max":40,"message":"业务编号长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"业务编号只能是中文、英文、数字与下划线"}}
            }
            ,
            fileName1 : {
                validators : {"notEmpty":{"message":"文件名称不能为空"},"stringLength":{"min":1,"max":100,"message":"文件名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_.]+$","message":"文件名称只能是中文、英文、数字、下划线和英文小数点"}}
            }
            ,
            filePath1 : {
                validators : {"notEmpty":{"message":"文件路径不能为空"},"stringLength":{"min":1,"max":40,"message":"文件路径长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_./]+$","message":"文件路径只能是中文、英文、数字、下划线、斜杠和英文小数点"}}
            }
            ,
            fileRootPath1 : {
                validators : {"notEmpty":{"message":"文件根路径不能为空"},"stringLength":{"min":1,"max":20,"message":"文件根路径长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_\\./]+$","message":"文件根路径只能是中文、英文、数字、下划线、斜杠和英文小数点"}}
            }
            ,
            fileStat1 : {
                validators : {"notEmpty":{"message":"请选择一个文件状态"}}
            }
            ,
            fileSize1 : {
                validators : {"notEmpty":{"message":"文件大小不能为空"},"stringLength":{"min":1,"max":30,"message":"文件大小长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9.]+$","message":"文件大小只能是英文、数字与英文小数点"}}
            }
            ,
            fileDesc1 : {
                validators : {"notEmpty":{"message":"文件描述不能为空"},"stringLength":{"min":1,"max":30,"message":"文件描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"文件描述只能是中文、英文、数字与下划线"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addFileInfo();
     });

    //修改文件信息校验
    $('#validateUpdateFileInfoForm').bootstrapValidator({
        fields : {
            businessType3 : {
                validators : {"notEmpty":{"message":"请选择一个业务类型"}}
            }
            ,
            businessNo3 : {
                validators : {"notEmpty":{"message":"业务编号不能为空"},"stringLength":{"min":1,"max":40,"message":"业务编号长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"业务编号只能是中文、英文、数字与下划线"}}
            }
            ,
            fileName3 : {
                validators : {"notEmpty":{"message":"文件名称不能为空"},"stringLength":{"min":1,"max":100,"message":"文件名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_.]+$","message":"文件名称只能是中文、英文、数字、下划线和英文小数点"}}
            }
            ,
            filePath3 : {
                validators : {"notEmpty":{"message":"文件路径不能为空"},"stringLength":{"min":1,"max":40,"message":"文件路径长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_./]+$","message":"文件路径只能是中文、英文、数字、下划线、斜杠和英文小数点"}}
            }
            ,
            fileRootPath3 : {
                validators : {"notEmpty":{"message":"文件根路径不能为空"},"stringLength":{"min":1,"max":20,"message":"文件根路径长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_\\./]+$","message":"文件根路径只能是中文、英文、数字、下划线、斜杠和英文小数点"}}
            }
            ,
            fileStat3 : {
                validators : {"notEmpty":{"message":"请选择一个文件状态"}}
            }
            ,
            fileSize3 : {
                validators : {"notEmpty":{"message":"文件大小不能为空"},"stringLength":{"min":1,"max":30,"message":"文件大小长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9.]+$","message":"文件大小只能是英文、数字与英文小数点"}}
            }
            ,
            fileDesc3 : {
                validators : {"notEmpty":{"message":"文件描述不能为空"},"stringLength":{"min":1,"max":30,"message":"文件描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"文件描述只能是中文、英文、数字与下划线"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         updateFileInfo();
     });
    //查询文件信息校验
    $('#queryFileInfoForm').bootstrapValidator({
        fields : {
            fileNo : {
                validators : {"stringLength":{"min":1,"max":10,"message":"文件编号长度不正确"},"regexp":{"regexp":"^[0-9]{0,10}$","message":"文件编号与要求的格式不符"}}
            }
            ,
            businessType : {
                validators : {}
            }
            ,
            businessNo : {
                validators : {"stringLength":{"min":1,"max":40,"message":"业务编号长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"业务编号只能是中文、英文、数字与下划线"}}
            }
            ,
            fileName : {
                validators : {"stringLength":{"min":1,"max":100,"message":"文件名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_.]+$","message":"文件名称只能是中文、英文、数字、下划线和英文小数点"}}
            }
            ,
            filePath : {
                validators : {"stringLength":{"min":1,"max":40,"message":"文件路径长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_./]+$","message":"文件路径只能是中文、英文、数字、下划线、斜杠和英文小数点"}}
            }
            ,
            fileRootPath : {
                validators : {"stringLength":{"min":1,"max":20,"message":"文件根路径长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_\\./]+$","message":"文件根路径只能是中文、英文、数字、下划线、斜杠和英文小数点"}}
            }
            ,
            createTm : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^.+$","message":"XX信息与要求的格式不符"}}
            }
            ,
            fileStat : {
                validators : {}
            }
            ,
            fileSize : {
                validators : {"stringLength":{"min":1,"max":30,"message":"文件大小长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9.]+$","message":"文件大小只能是英文、数字与英文小数点"}}
            }
            ,
            fileDesc : {
                validators : {"stringLength":{"min":1,"max":30,"message":"文件描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"文件描述只能是中文、英文、数字与下划线"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         console.log("查询校验通过");
     });

});

function searchFileInfo(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#queryFileInfoForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#queryFileInfoForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#queryFileInfoForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#queryFileInfoForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#queryFileInfocurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#queryFileInfopageSize").val();
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

    params.fileNo = $("#queryFileInfoForm #fileNo").val();
    params.businessType = $("#queryFileInfoForm #businessType").val();
    params.businessNo = $("#queryFileInfoForm #businessNo").val();
    params.fileName = $("#queryFileInfoForm #fileName").val();
    params.filePath = $("#queryFileInfoForm #filePath").val();
    params.fileRootPath = $("#queryFileInfoForm #fileRootPath").val();
    params.createTm = $("#queryFileInfoForm #createTm").val();
    params.fileStat = $("#queryFileInfoForm #fileStat").val();
    params.fileSize = $("#queryFileInfoForm #fileSize").val();
    params.fileDesc = $("#queryFileInfoForm #fileDesc").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/fileInfo/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/fileInfo/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#fileInfoTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#queryFileInfocurrentPage").val(data.rows[0].startIndex);
                $("#queryFileInfototalPage").val(data.rows[0].totalPage);
                $("#queryFileInfopageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var fileInfoTr = $("<tr class=\"fileInfoTr\"></tr>");
                    $("#fileInfoTR_FIRST").parent().append(fileInfoTr);
                    fileInfoTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].fileNo+"</td>")
                    .append("<td>"+resultList[i].businessType_name+"</td>")
                    .append("<td>"+resultList[i].businessNo+"</td>")
                    .append("<td>"+resultList[i].fileName+"</td>")
                    .append("<td>"+resultList[i].filePath+"</td>")
                    .append("<td>"+resultList[i].fileRootPath+"</td>")
                    .append("<td>"+resultList[i].createTm+"</td>")
                    .append("<td>"+resultList[i].fileStat_name+"</td>")
                    .append("<td>"+resultList[i].fileSize+"</td>")
                    .append("<td>"+resultList[i].fileDesc+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editFileInfo('"+resultList[i].fileNo+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delFileInfo('"+resultList[i].fileNo+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return queryFileInfoObject('"+resultList[i].fileNo+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#queryFileInfocurrentPage").val());
                var totalPage   = Number($("#queryFileInfototalPage").val());
                var pageSize    = Number($("#queryFileInfopageSize").val());
                $("#queryFileInfo_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: queryFileInfogopage
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

function queryFileInfogopage(currentPage) {
    searchFileInfo(currentPage);
}

function addFileInfo(){
    var url=webUrl+"/fileInfo/insert.dhtml";
    var params={};
    params.businessType = $("#validateAddFileInfoForm #businessType1").val();
    params.businessNo = $("#validateAddFileInfoForm #businessNo1").val();
    params.fileName = $("#validateAddFileInfoForm #fileName1").val();
    params.filePath = $("#validateAddFileInfoForm #filePath1").val();
    params.fileRootPath = $("#validateAddFileInfoForm #fileRootPath1").val();
    params.fileStat = $("#validateAddFileInfoForm #fileStat1").val();
    params.fileSize = $("#validateAddFileInfoForm #fileSize1").val();
    params.fileDesc = $("#validateAddFileInfoForm #fileDesc1").val();
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
                $('#addFileInfo').modal('hide');
                searchFileInfo('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddFileInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddFileInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddFileInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddFileInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateFileInfo(){
    var url=webUrl+"/fileInfo/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateFileInfoForm #id_key").val();
    params.businessType = $("#validateUpdateFileInfoForm #businessType3").val();
    params.businessNo = $("#validateUpdateFileInfoForm #businessNo3").val();
    params.fileName = $("#validateUpdateFileInfoForm #fileName3").val();
    params.filePath = $("#validateUpdateFileInfoForm #filePath3").val();
    params.fileRootPath = $("#validateUpdateFileInfoForm #fileRootPath3").val();
    params.fileStat = $("#validateUpdateFileInfoForm #fileStat3").val();
    params.fileSize = $("#validateUpdateFileInfoForm #fileSize3").val();
    params.fileDesc = $("#validateUpdateFileInfoForm #fileDesc3").val();
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
                $('#updateFileInfo').modal('hide');
                searchFileInfo('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateFileInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateFileInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateFileInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateFileInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editFileInfo(id_key){
    var url=webUrl+"/fileInfo/queryObject.dhtml";
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
                $('#updateFileInfo').modal();
                $("#validateUpdateFileInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblFileInfo;
                $("#validateUpdateFileInfoForm #fileNo3").val(objInfo.fileNo!=null?objInfo.fileNo:'');
                $("#validateUpdateFileInfoForm #businessType3").val(objInfo.businessType!=null?objInfo.businessType:'');
                $("#validateUpdateFileInfoForm #businessNo3").val(objInfo.businessNo!=null?objInfo.businessNo:'');
                $("#validateUpdateFileInfoForm #fileName3").val(objInfo.fileName!=null?objInfo.fileName:'');
                $("#validateUpdateFileInfoForm #filePath3").val(objInfo.filePath!=null?objInfo.filePath:'');
                $("#validateUpdateFileInfoForm #fileRootPath3").val(objInfo.fileRootPath!=null?objInfo.fileRootPath:'');
                $("#validateUpdateFileInfoForm #createTm3").val(objInfo.createTm!=null?objInfo.createTm:'');
                $("#validateUpdateFileInfoForm #fileStat3").val(objInfo.fileStat!=null?objInfo.fileStat:'');
                $("#validateUpdateFileInfoForm #fileSize3").val(objInfo.fileSize!=null?objInfo.fileSize:'');
                $("#validateUpdateFileInfoForm #fileDesc3").val(objInfo.fileDesc!=null?objInfo.fileDesc:'');
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

function queryFileInfoObject(id_key){
    var url=webUrl+"/fileInfo/queryObject.dhtml";
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
                $('#detailFileInfo').modal();
                $("#validateDetailFileInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblFileInfo;
                $("#validateDetailFileInfoForm #fileNo2").val(objInfo.fileNo!=null?objInfo.fileNo:'');
                $("#validateDetailFileInfoForm #businessType2").val(objInfo.businessType_name!=null?objInfo.businessType_name:'');
                $("#validateDetailFileInfoForm #businessNo2").val(objInfo.businessNo!=null?objInfo.businessNo:'');
                $("#validateDetailFileInfoForm #fileName2").val(objInfo.fileName!=null?objInfo.fileName:'');
                $("#validateDetailFileInfoForm #filePath2").val(objInfo.filePath!=null?objInfo.filePath:'');
                $("#validateDetailFileInfoForm #fileRootPath2").val(objInfo.fileRootPath!=null?objInfo.fileRootPath:'');
                $("#validateDetailFileInfoForm #createTm2").val(objInfo.createTm!=null?objInfo.createTm:'');
                $("#validateDetailFileInfoForm #fileStat2").val(objInfo.fileStat_name!=null?objInfo.fileStat_name:'');
                $("#validateDetailFileInfoForm #fileSize2").val(objInfo.fileSize!=null?objInfo.fileSize:'');
                $("#validateDetailFileInfoForm #fileDesc2").val(objInfo.fileDesc!=null?objInfo.fileDesc:'');
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
function delFileInfo(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/fileInfo/del.dhtml";
        var params={};
        var currentPage= $("#queryFileInfocurrentPage").val();
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
                    searchFileInfo(currentPage);
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


