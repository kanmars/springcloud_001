$(function(){
    searchSysDic('');
    $('#querySysDicpageSize').on('change',function () {
        var currentPage = $("#querySysDiccurrentPage").val();
        searchSysDic(currentPage);
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
    $('#updateSysDic').on('shown.bs.modal', function () {
        $('#validateUpdateSysDicForm').bootstrapValidator('resetForm', false);
    });
    $('#querySysDicForm #createTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysDicForm","createTime");
    });
    $('#validateAddSysDicForm #createTime1').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateAddSysDicForm","createTime1");
    });
    $('#validateUpdateSysDicForm #createTime3').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateUpdateSysDicForm","createTime3");
    });
    $('#querySysDicForm #upTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysDicForm","upTime");
    });
    $('#validateAddSysDicForm #upTime1').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateAddSysDicForm","upTime1");
    });
    $('#validateUpdateSysDicForm #upTime3').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateUpdateSysDicForm","upTime3");
    });

    //增加系统字典表校验
    $('#validateAddSysDicForm').bootstrapValidator({
        fields : {
            l1Code1 : {
                validators : {"notEmpty":{"message":"一级字典码不能为空"},"stringLength":{"min":1,"max":32,"message":"一级字典码长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"一级字典码长只能是英文与数字"}}
            }
            ,
            l1Desc1 : {
                validators : {"notEmpty":{"message":"一级字典描述不能为空"},"stringLength":{"min":1,"max":100,"message":"一级字典描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"一级字典描述只能是中文、英文与数字"}}
            }
            ,
            l2Code1 : {
                validators : {"notEmpty":{"message":"二级字典码不能为空"},"stringLength":{"min":1,"max":32,"message":"二级字典码长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"二级字典码长只能是英文与数字"}}
            }
            ,
            l2Desc1 : {
                validators : {"notEmpty":{"message":"二级字典描述不能为空"},"stringLength":{"min":1,"max":100,"message":"二级字典描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"二级字典描述只能是中文、英文与数字"}}
            }
            ,
            codeParam1 : {
                validators : {"notEmpty":{"message":"码表参数不能为空"},"stringLength":{"min":1,"max":64,"message":"码表参数长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"码表参数只能是中文、英文与数字"}}
            }
            ,
            codeValue1 : {
                validators : {"notEmpty":{"message":"实际值不能为空"},"stringLength":{"min":1,"max":160,"message":"码表参数长度不正确"},"regexp":{"regexp":"^[^']+$","message":"实际值中有特殊字符"}}
            }
            ,
            codeIdx1 : {
                validators : {"notEmpty":{"message":"排序不能为空"},"stringLength":{"min":1,"max":10,"message":"排序长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"排序只能是正整数"}}
            }
            ,
            liveCount1 : {
                validators : {"notEmpty":{"message":"存活时间不能为空"},"stringLength":{"min":1,"max":10,"message":"存活时间长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"存活时间只能是正整数"}}
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
            ,
            upTime1 : {
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
         addSysDic();
     });

    //修改系统字典表校验
    $('#validateUpdateSysDicForm').bootstrapValidator({
        fields : {
            l1Code3 : {
                validators : {"notEmpty":{"message":"一级字典码不能为空"},"stringLength":{"min":1,"max":32,"message":"一级字典码长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"一级字典码长只能是英文与数字"}}
            }
            ,
            l1Desc3 : {
                validators : {"notEmpty":{"message":"一级字典描述不能为空"},"stringLength":{"min":1,"max":100,"message":"一级字典描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"一级字典描述只能是中文、英文与数字"}}
            }
            ,
            l2Code3 : {
                validators : {"notEmpty":{"message":"二级字典码不能为空"},"stringLength":{"min":1,"max":32,"message":"二级字典码长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"二级字典码长只能是英文与数字"}}
            }
            ,
            l2Desc3 : {
                validators : {"notEmpty":{"message":"二级字典描述不能为空"},"stringLength":{"min":1,"max":100,"message":"二级字典描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"二级字典描述只能是中文、英文与数字"}}
            }
            ,
            codeParam3 : {
                validators : {"notEmpty":{"message":"码表参数不能为空"},"stringLength":{"min":1,"max":64,"message":"码表参数长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"码表参数只能是中文、英文与数字"}}
            }
            ,
            codeValue3 : {
                validators : {"notEmpty":{"message":"实际值不能为空"},"stringLength":{"min":1,"max":160,"message":"码表参数长度不正确"},"regexp":{"regexp":"^[^']+$","message":"实际值中有特殊字符"}}
            }
            ,
            codeIdx3 : {
                validators : {"notEmpty":{"message":"排序不能为空"},"stringLength":{"min":1,"max":10,"message":"排序长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"排序只能是正整数"}}
            }
            ,
            liveCount3 : {
                validators : {"notEmpty":{"message":"存活时间不能为空"},"stringLength":{"min":1,"max":10,"message":"存活时间长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"存活时间只能是正整数"}}
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
            ,
            upTime3 : {
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
         updateSysDic();
     });
    //查询系统字典表校验
    $('#querySysDicForm').bootstrapValidator({
        fields : {
            id : {
                validators : {"stringLength":{"min":1,"max":100,"message":"ID长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"ID与要求的格式不符"}}
            }
            ,
            l1Code : {
                validators : {"stringLength":{"min":1,"max":32,"message":"一级字典码长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"一级字典码长只能是英文与数字"}}
            }
            ,
            l1Desc : {
                validators : {"stringLength":{"min":1,"max":100,"message":"一级字典描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"一级字典描述只能是中文、英文与数字"}}
            }
            ,
            l2Code : {
                validators : {"stringLength":{"min":1,"max":32,"message":"二级字典码长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"二级字典码长只能是英文与数字"}}
            }
            ,
            l2Desc : {
                validators : {"stringLength":{"min":1,"max":100,"message":"二级字典描述长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"二级字典描述只能是中文、英文与数字"}}
            }
            ,
            codeParam : {
                validators : {"stringLength":{"min":1,"max":64,"message":"码表参数长度不正确"},"regexp":{"regexp":"^[A-Za-z0-9_]+$","message":"码表参数只能是中文、英文与数字"}}
            }
            ,
            codeValue : {
                validators : {"stringLength":{"min":1,"max":160,"message":"码表参数长度不正确"},"regexp":{"regexp":"^[^']+$","message":"实际值中有特殊字符"}}
            }
            ,
            codeIdx : {
                validators : {"stringLength":{"min":1,"max":10,"message":"排序长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"排序只能是正整数"}}
            }
            ,
            liveCount : {
                validators : {"stringLength":{"min":1,"max":10,"message":"存活时间长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"存活时间只能是正整数"}}
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

function searchSysDic(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#querySysDicForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#querySysDicForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#querySysDicForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#querySysDicForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#querySysDiccurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#querySysDicpageSize").val();
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

    params.id = $("#querySysDicForm #id").val();
    params.l1Code = $("#querySysDicForm #l1Code").val();
    params.l1Desc = $("#querySysDicForm #l1Desc").val();
    params.l2Code = $("#querySysDicForm #l2Code").val();
    params.l2Desc = $("#querySysDicForm #l2Desc").val();
    params.codeParam = $("#querySysDicForm #codeParam").val();
    params.codeValue = $("#querySysDicForm #codeValue").val();
    params.codeIdx = $("#querySysDicForm #codeIdx").val();
    params.liveCount = $("#querySysDicForm #liveCount").val();
    params.createTime = $("#querySysDicForm #createTime").val();
    params.upTime = $("#querySysDicForm #upTime").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/sysDic/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/sysDic/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#sysDicTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#querySysDiccurrentPage").val(data.rows[0].startIndex);
                $("#querySysDictotalPage").val(data.rows[0].totalPage);
                $("#querySysDicpageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var sysDicTr = $("<tr class=\"sysDicTr\"></tr>");
                    $("#sysDicTR_FIRST").parent().append(sysDicTr);
                    sysDicTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].l1Code+"</td>")
                    .append("<td>"+resultList[i].l1Desc+"</td>")
                    .append("<td>"+resultList[i].l2Code+"</td>")
                    .append("<td>"+resultList[i].l2Desc+"</td>")
                    .append("<td>"+resultList[i].codeParam+"</td>")
                    .append("<td>"+resultList[i].codeValue+"</td>")
                    .append("<td>"+resultList[i].codeIdx+"</td>")
                    .append("<td>"+resultList[i].liveCount+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td>"+resultList[i].upTime+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editSysDic('"+resultList[i].id+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delSysDic('"+resultList[i].id+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return querySysDicObject('"+resultList[i].id+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#querySysDiccurrentPage").val());
                var totalPage   = Number($("#querySysDictotalPage").val());
                var pageSize    = Number($("#querySysDicpageSize").val());
                $("#querySysDic_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: querySysDicgopage
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

function querySysDicgopage(currentPage) {
    searchSysDic(currentPage);
}

function addSysDic(){
    var url=webUrl+"/sysDic/insert.dhtml";
    var params={};
    params.l1Code = $("#validateAddSysDicForm #l1Code1").val();
    params.l1Desc = $("#validateAddSysDicForm #l1Desc1").val();
    params.l2Code = $("#validateAddSysDicForm #l2Code1").val();
    params.l2Desc = $("#validateAddSysDicForm #l2Desc1").val();
    params.codeParam = $("#validateAddSysDicForm #codeParam1").val();
    params.codeValue = $("#validateAddSysDicForm #codeValue1").val();
    params.codeIdx = $("#validateAddSysDicForm #codeIdx1").val();
    params.liveCount = $("#validateAddSysDicForm #liveCount1").val();
    params.createTime = $("#validateAddSysDicForm #createTime1").val();
    params.upTime = $("#validateAddSysDicForm #upTime1").val();
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
                $('#addSysDic').modal('hide');
                searchSysDic('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddSysDicForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddSysDicForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddSysDicForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddSysDicForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateSysDic(){
    var url=webUrl+"/sysDic/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateSysDicForm #id_key").val();
    params.l1Code = $("#validateUpdateSysDicForm #l1Code3").val();
    params.l1Desc = $("#validateUpdateSysDicForm #l1Desc3").val();
    params.l2Code = $("#validateUpdateSysDicForm #l2Code3").val();
    params.l2Desc = $("#validateUpdateSysDicForm #l2Desc3").val();
    params.codeParam = $("#validateUpdateSysDicForm #codeParam3").val();
    params.codeValue = $("#validateUpdateSysDicForm #codeValue3").val();
    params.codeIdx = $("#validateUpdateSysDicForm #codeIdx3").val();
    params.liveCount = $("#validateUpdateSysDicForm #liveCount3").val();
    //params.createTime = $("#validateUpdateSysDicForm #createTime3").val();
    //params.upTime = $("#validateUpdateSysDicForm #upTime3").val();
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
                $('#updateSysDic').modal('hide');
                searchSysDic('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateSysDicForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateSysDicForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateSysDicForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateSysDicForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editSysDic(id_key){
    var url=webUrl+"/sysDic/queryObject.dhtml";
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
                $('#updateSysDic').modal();
                $("#validateUpdateSysDicForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysDic;
                $("#validateUpdateSysDicForm #id3").val(objInfo.id!=null?objInfo.id:'');
                $("#validateUpdateSysDicForm #l1Code3").val(objInfo.l1Code!=null?objInfo.l1Code:'');
                $("#validateUpdateSysDicForm #l1Desc3").val(objInfo.l1Desc!=null?objInfo.l1Desc:'');
                $("#validateUpdateSysDicForm #l2Code3").val(objInfo.l2Code!=null?objInfo.l2Code:'');
                $("#validateUpdateSysDicForm #l2Desc3").val(objInfo.l2Desc!=null?objInfo.l2Desc:'');
                $("#validateUpdateSysDicForm #codeParam3").val(objInfo.codeParam!=null?objInfo.codeParam:'');
                $("#validateUpdateSysDicForm #codeValue3").val(objInfo.codeValue!=null?objInfo.codeValue:'');
                $("#validateUpdateSysDicForm #codeIdx3").val(objInfo.codeIdx!=null?objInfo.codeIdx:'');
                $("#validateUpdateSysDicForm #liveCount3").val(objInfo.liveCount!=null?objInfo.liveCount:'');
                $("#validateUpdateSysDicForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateUpdateSysDicForm #upTime3").val(objInfo.upTime!=null?objInfo.upTime:'');
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

function querySysDicObject(id_key){
    var url=webUrl+"/sysDic/queryObject.dhtml";
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
                $('#detailSysDic').modal();
                $("#validateDetailSysDicForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysDic;
                $("#validateDetailSysDicForm #id2").val(objInfo.id!=null?objInfo.id:'');
                $("#validateDetailSysDicForm #l1Code2").val(objInfo.l1Code!=null?objInfo.l1Code:'');
                $("#validateDetailSysDicForm #l1Desc2").val(objInfo.l1Desc!=null?objInfo.l1Desc:'');
                $("#validateDetailSysDicForm #l2Code2").val(objInfo.l2Code!=null?objInfo.l2Code:'');
                $("#validateDetailSysDicForm #l2Desc2").val(objInfo.l2Desc!=null?objInfo.l2Desc:'');
                $("#validateDetailSysDicForm #codeParam2").val(objInfo.codeParam!=null?objInfo.codeParam:'');
                $("#validateDetailSysDicForm #codeValue2").val(objInfo.codeValue!=null?objInfo.codeValue:'');
                $("#validateDetailSysDicForm #codeIdx2").val(objInfo.codeIdx!=null?objInfo.codeIdx:'');
                $("#validateDetailSysDicForm #liveCount2").val(objInfo.liveCount!=null?objInfo.liveCount:'');
                $("#validateDetailSysDicForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateDetailSysDicForm #upTime2").val(objInfo.upTime!=null?objInfo.upTime:'');
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
function delSysDic(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/sysDic/del.dhtml";
        var params={};
        var currentPage= $("#querySysDiccurrentPage").val();
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
                    searchSysDic(currentPage);
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


