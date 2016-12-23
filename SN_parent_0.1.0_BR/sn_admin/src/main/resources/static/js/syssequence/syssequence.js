$(function(){
    searchSysSequence('');
    $('#querySysSequencepageSize').on('change',function () {
        var currentPage = $("#querySysSequencecurrentPage").val();
        searchSysSequence(currentPage);
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
    $('#updateSysSequence').on('shown.bs.modal', function () {
        $('#validateUpdateSysSequenceForm').bootstrapValidator('resetForm', false);
    });
    $('#querySysSequenceForm #createTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysSequenceForm","createTime");
    });
    $('#querySysSequenceForm #upTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysSequenceForm","upTime");
    });

    //增加系统ID表校验
    $('#validateAddSysSequenceForm').bootstrapValidator({
        fields : {
            keyValue1 : {
                validators : {"notEmpty":{"message":"序列Key不能为空"},"stringLength":{"min":1,"max":32,"message":"序列Key长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"序列Key只能是英文、数字、下划线"}}
            }
            ,
            seqDesc1 : {
                validators : {"notEmpty":{"message":"序列描述不能为空"},"stringLength":{"min":1,"max":20,"message":"序列描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"序列描述只能是中文、英文与数字"}}
            }
            ,
            top1 : {
                validators : {}
            }
            ,
            suffix1 : {
                validators : {}
            }
            ,
            currValue1 : {
                validators : {"notEmpty":{"message":"当前值必须填写"},"stringLength":{"min":1,"max":10,"message":"当前值长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"当前值只能是正整数"}}
            }
            ,
            batchSize1 : {
                validators : {"notEmpty":{"message":"递增数量必须填写"},"stringLength":{"min":1,"max":10,"message":"递增数量长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"递增数量只能是正整数"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addSysSequence();
     });

    //修改系统ID表校验
    $('#validateUpdateSysSequenceForm').bootstrapValidator({
        fields : {
            keyValue3 : {
                validators : {"notEmpty":{"message":"序列Key不能为空"},"stringLength":{"min":1,"max":32,"message":"序列Key长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"序列Key只能是英文、数字、下划线"}}
            }
            ,
            seqDesc3 : {
                validators : {"notEmpty":{"message":"序列描述不能为空"},"stringLength":{"min":1,"max":20,"message":"序列描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"序列描述只能是中文、英文与数字"}}
            }
            ,
            top3 : {
                validators : {}
            }
            ,
            suffix3 : {
                validators : {}
            }
            ,
            currValue3 : {
                validators : {"notEmpty":{"message":"当前值必须填写"},"stringLength":{"min":1,"max":10,"message":"当前值长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"当前值只能是正整数"}}
            }
            ,
            batchSize3 : {
                validators : {"notEmpty":{"message":"递增数量必须填写"},"stringLength":{"min":1,"max":10,"message":"递增数量长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"递增数量只能是正整数"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         updateSysSequence();
     });
    //查询系统ID表校验
    $('#querySysSequenceForm').bootstrapValidator({
        fields : {
            id : {
                validators : {"stringLength":{"min":1,"max":100,"message":"表ID长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"表ID与要求的格式不符"}}
            }
            ,
            keyValue : {
                validators : {"stringLength":{"min":1,"max":32,"message":"序列Key长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"序列Key只能是英文、数字、下划线"}}
            }
            ,
            seqDesc : {
                validators : {"stringLength":{"min":1,"max":20,"message":"序列描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"序列描述只能是中文、英文与数字"}}
            }
            ,
            top : {
                validators : {}
            }
            ,
            suffix : {
                validators : {}
            }
            ,
            currValue : {
                validators : {"stringLength":{"min":1,"max":10,"message":"当前值长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"当前值只能是正整数"}}
            }
            ,
            batchSize : {
                validators : {"stringLength":{"min":1,"max":10,"message":"递增数量长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"递增数量只能是正整数"}}
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

function searchSysSequence(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#querySysSequenceForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#querySysSequenceForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#querySysSequenceForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#querySysSequenceForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#querySysSequencecurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#querySysSequencepageSize").val();
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

    params.id = $("#querySysSequenceForm #id").val();
    params.keyValue = $("#querySysSequenceForm #keyValue").val();
    params.seqDesc = $("#querySysSequenceForm #seqDesc").val();
    params.top = $("#querySysSequenceForm #top").val();
    params.suffix = $("#querySysSequenceForm #suffix").val();
    params.currValue = $("#querySysSequenceForm #currValue").val();
    params.batchSize = $("#querySysSequenceForm #batchSize").val();
    params.createTime = $("#querySysSequenceForm #createTime").val();
    params.upTime = $("#querySysSequenceForm #upTime").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/sysSequence/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/sysSequence/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#sysSequenceTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#querySysSequencecurrentPage").val(data.rows[0].startIndex);
                $("#querySysSequencetotalPage").val(data.rows[0].totalPage);
                $("#querySysSequencepageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var sysSequenceTr = $("<tr class=\"sysSequenceTr\"></tr>");
                    $("#sysSequenceTR_FIRST").parent().append(sysSequenceTr);
                    sysSequenceTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].keyValue+"</td>")
                    .append("<td>"+resultList[i].seqDesc+"</td>")
                    .append("<td>"+resultList[i].top+"</td>")
                    .append("<td>"+resultList[i].suffix+"</td>")
                    .append("<td>"+resultList[i].currValue+"</td>")
                    .append("<td>"+resultList[i].batchSize+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td>"+resultList[i].upTime+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editSysSequence('"+resultList[i].id+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delSysSequence('"+resultList[i].id+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return querySysSequenceObject('"+resultList[i].id+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#querySysSequencecurrentPage").val());
                var totalPage   = Number($("#querySysSequencetotalPage").val());
                var pageSize    = Number($("#querySysSequencepageSize").val());
                $("#querySysSequence_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: querySysSequencegopage
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

function querySysSequencegopage(currentPage) {
    searchSysSequence(currentPage);
}

function addSysSequence(){
    var url=webUrl+"/sysSequence/insert.dhtml";
    var params={};
    params.keyValue = $("#validateAddSysSequenceForm #keyValue1").val();
    params.seqDesc = $("#validateAddSysSequenceForm #seqDesc1").val();
    params.top = $("#validateAddSysSequenceForm #top1").val();
    params.suffix = $("#validateAddSysSequenceForm #suffix1").val();
    params.currValue = $("#validateAddSysSequenceForm #currValue1").val();
    params.batchSize = $("#validateAddSysSequenceForm #batchSize1").val();
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
                $('#addSysSequence').modal('hide');
                searchSysSequence('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddSysSequenceForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddSysSequenceForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddSysSequenceForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddSysSequenceForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateSysSequence(){
    var url=webUrl+"/sysSequence/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateSysSequenceForm #id_key").val();
    params.keyValue = $("#validateUpdateSysSequenceForm #keyValue3").val();
    params.seqDesc = $("#validateUpdateSysSequenceForm #seqDesc3").val();
    params.top = $("#validateUpdateSysSequenceForm #top3").val();
    params.suffix = $("#validateUpdateSysSequenceForm #suffix3").val();
    params.currValue = $("#validateUpdateSysSequenceForm #currValue3").val();
    params.batchSize = $("#validateUpdateSysSequenceForm #batchSize3").val();
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
                $('#updateSysSequence').modal('hide');
                searchSysSequence('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateSysSequenceForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateSysSequenceForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateSysSequenceForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateSysSequenceForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editSysSequence(id_key){
    var url=webUrl+"/sysSequence/queryObject.dhtml";
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
                $('#updateSysSequence').modal();
                $("#validateUpdateSysSequenceForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysSequence;
                $("#validateUpdateSysSequenceForm #id3").val(objInfo.id!=null?objInfo.id:'');
                $("#validateUpdateSysSequenceForm #keyValue3").val(objInfo.keyValue!=null?objInfo.keyValue:'');
                $("#validateUpdateSysSequenceForm #seqDesc3").val(objInfo.seqDesc!=null?objInfo.seqDesc:'');
                $("#validateUpdateSysSequenceForm #top3").val(objInfo.top!=null?objInfo.top:'');
                $("#validateUpdateSysSequenceForm #suffix3").val(objInfo.suffix!=null?objInfo.suffix:'');
                $("#validateUpdateSysSequenceForm #currValue3").val(objInfo.currValue!=null?objInfo.currValue:'');
                $("#validateUpdateSysSequenceForm #batchSize3").val(objInfo.batchSize!=null?objInfo.batchSize:'');
                $("#validateUpdateSysSequenceForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateUpdateSysSequenceForm #upTime3").val(objInfo.upTime!=null?objInfo.upTime:'');
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

function querySysSequenceObject(id_key){
    var url=webUrl+"/sysSequence/queryObject.dhtml";
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
                $('#detailSysSequence').modal();
                $("#validateDetailSysSequenceForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysSequence;
                $("#validateDetailSysSequenceForm #id2").val(objInfo.id!=null?objInfo.id:'');
                $("#validateDetailSysSequenceForm #keyValue2").val(objInfo.keyValue!=null?objInfo.keyValue:'');
                $("#validateDetailSysSequenceForm #seqDesc2").val(objInfo.seqDesc!=null?objInfo.seqDesc:'');
                $("#validateDetailSysSequenceForm #top2").val(objInfo.top!=null?objInfo.top:'');
                $("#validateDetailSysSequenceForm #suffix2").val(objInfo.suffix!=null?objInfo.suffix:'');
                $("#validateDetailSysSequenceForm #currValue2").val(objInfo.currValue!=null?objInfo.currValue:'');
                $("#validateDetailSysSequenceForm #batchSize2").val(objInfo.batchSize!=null?objInfo.batchSize:'');
                $("#validateDetailSysSequenceForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateDetailSysSequenceForm #upTime2").val(objInfo.upTime!=null?objInfo.upTime:'');
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
function delSysSequence(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/sysSequence/del.dhtml";
        var params={};
        var currentPage= $("#querySysSequencecurrentPage").val();
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
                    searchSysSequence(currentPage);
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


