$(function(){
    searchTaskBasicConfig('');
    $('#queryTaskBasicConfigpageSize').on('change',function () {
        var currentPage = $("#queryTaskBasicConfigcurrentPage").val();
        searchTaskBasicConfig(currentPage);
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
    $('#updateTaskBasicConfig').on('shown.bs.modal', function () {
        $('#validateUpdateTaskBasicConfigForm').bootstrapValidator('resetForm', false);
    });
    $('#queryTaskBasicConfigForm #configTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("queryTaskBasicConfigForm","configTime");
    });
    $('#validateAddTaskBasicConfigForm #configTime1').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateAddTaskBasicConfigForm","configTime1");
    });
    $('#validateUpdateTaskBasicConfigForm #configTime3').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateUpdateTaskBasicConfigForm","configTime3");
    });

    //增加任务基本信息配置表校验
    $('#validateAddTaskBasicConfigForm').bootstrapValidator({
        fields : {
            taskName1 : {
                validators : {"notEmpty":{"message":"任务名称不能为空"},"stringLength":{"min":1,"max":60,"message":"任务名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"任务名称只能是汉字英文与数字"}}
            }
            ,
            parentId1 : {
                validators : {"notEmpty":{"message":"推送地址不能为空"},"stringLength":{"min":1,"max":100,"message":"推送地址长度不正确"},"regexp":{"regexp":"^.*$","message":"推送地址格式不正确"}}
            }
            ,
            runRule1 : {
                validators : {"notEmpty":{"message":"执行规则不能为空"},"stringLength":{"min":1,"max":100,"message":"执行规则长度不正确"},"regexp":{"regexp":"^.*$","message":"执行规则格式不正确"}}
            }
            ,
            runStatus1 : {
                validators : {"notEmpty":{"message":"请选择一个静态单选按钮"}}
            }
            ,
            status1 : {
                validators : {"notEmpty":{"message":"请选择是否启用"}}
            }
            ,
            businessObjName1 : {
                validators : {"notEmpty":{"message":"业务对象名称"},"stringLength":{"min":1,"max":100,"message":"业务对象名称长度不正确"},"regexp":{"regexp":"^[a-zA-Z0-9]*$","message":"业务对象名称只能是英文与数字"}}
            }
            ,
            taskCount1 : {
                validators : {"notEmpty":{"message":"任务数必须填写"},"stringLength":{"min":1,"max":1,"message":"任务数长度不正确"},"regexp":{"regexp":"^[1-9]{1}$","message":"任务数只能是1到9的数字"}}
            }
            ,
            internetName1 : {
                validators : {"notEmpty":{"message":"网卡名称不能为空"},"stringLength":{"min":1,"max":100,"message":"网卡名称长度不正确"},"regexp":{"regexp":"^.*$","message":"网卡名称只能是英文与数字"}}
            }
            ,
            serverIp1 : {
                validators : {"notEmpty":{"message":"服务器IP不能为空"},"stringLength":{"min":1,"max":100,"message":"服务器IP长度不正确"},"regexp":{"regexp":"^.*$","message":"服务器IP格式不正确"}}
            }
            ,
            businessInfo1 : {
                validators : {"stringLength":{"min":0,"max":100,"message":"业务参数长度不正确"},"regexp":{"regexp":"^.*$","message":"业务参数格式不正确"}}
            }
            ,
            configTime1 : {
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
            upPerson1 : {
                validators : {"stringLength":{"message":"修改人长度不正确","min":1,"max":100},"notEmpty":{"message":"修改人不能为空"},"regexp":{"message":"修改人格式不正确","regexp":"^.*$"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addTaskBasicConfig();
     });

    //修改任务基本信息配置表校验
    $('#validateUpdateTaskBasicConfigForm').bootstrapValidator({
        fields : {
            taskName3 : {
                validators : {"notEmpty":{"message":"任务名称不能为空"},"stringLength":{"min":1,"max":60,"message":"任务名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"任务名称只能是汉字英文与数字"}}
            }
            ,
            parentId3 : {
                validators : {"notEmpty":{"message":"推送地址不能为空"},"stringLength":{"min":1,"max":100,"message":"推送地址长度不正确"},"regexp":{"regexp":"^.*$","message":"推送地址格式不正确"}}
            }
            ,
            runRule3 : {
                validators : {"notEmpty":{"message":"执行规则不能为空"},"stringLength":{"min":1,"max":100,"message":"执行规则长度不正确"},"regexp":{"regexp":"^.*$","message":"执行规则格式不正确"}}
            }
            ,
            runStatus3 : {
                validators : {"notEmpty":{"message":"请选择一个静态单选按钮"}}
            }
            ,
            status3 : {
                validators : {"notEmpty":{"message":"请选择是否启用"}}
            }
            ,
            businessObjName3 : {
                validators : {"notEmpty":{"message":"业务对象名称"},"stringLength":{"min":1,"max":100,"message":"业务对象名称长度不正确"},"regexp":{"regexp":"^[a-zA-Z0-9]*$","message":"业务对象名称只能是英文与数字"}}
            }
            ,
            taskCount3 : {
                validators : {"notEmpty":{"message":"任务数必须填写"},"stringLength":{"min":1,"max":1,"message":"任务数长度不正确"},"regexp":{"regexp":"^[1-9]{1}$","message":"任务数只能是1到9的数字"}}
            }
            ,
            internetName3 : {
                validators : {"notEmpty":{"message":"网卡名称不能为空"},"stringLength":{"min":1,"max":100,"message":"网卡名称长度不正确"},"regexp":{"regexp":"^.*$","message":"网卡名称只能是英文与数字"}}
            }
            ,
            serverIp3 : {
                validators : {"notEmpty":{"message":"服务器IP不能为空"},"stringLength":{"min":1,"max":100,"message":"服务器IP长度不正确"},"regexp":{"regexp":"^.*$","message":"服务器IP格式不正确"}}
            }
            ,
            businessInfo3 : {
                validators : {"stringLength":{"min":0,"max":100,"message":"业务参数长度不正确"},"regexp":{"regexp":"^.*$","message":"业务参数格式不正确"}}
            }
            ,
            configTime3 : {
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
            upPerson3 : {
                validators : {"stringLength":{"message":"修改人长度不正确","min":1,"max":100},"notEmpty":{"message":"修改人不能为空"},"regexp":{"message":"修改人格式不正确","regexp":"^.*$"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         updateTaskBasicConfig();
     });
    //查询任务基本信息配置表校验
    $('#queryTaskBasicConfigForm').bootstrapValidator({
        fields : {
            id : {
                validators : {"stringLength":{"min":1,"max":100,"message":"主键id长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"主键id与要求的格式不符"}}
            }
            ,
            taskName : {
                validators : {"stringLength":{"min":1,"max":60,"message":"任务名称长度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"任务名称只能是汉字英文与数字"}}
            }
            ,
            parentId : {
                validators : {"stringLength":{"min":1,"max":100,"message":"推送地址长度不正确"},"regexp":{"regexp":"^.*$","message":"推送地址格式不正确"}}
            }
            ,
            runRule : {
                validators : {"stringLength":{"min":1,"max":100,"message":"执行规则长度不正确"},"regexp":{"regexp":"^.*$","message":"执行规则格式不正确"}}
            }
            ,
            runStatus : {
                validators : {}
            }
            ,
            status : {
                validators : {}
            }
            ,
            businessObjName : {
                validators : {"stringLength":{"min":1,"max":100,"message":"业务对象名称长度不正确"},"regexp":{"regexp":"^[a-zA-Z0-9]*$","message":"业务对象名称只能是英文与数字"}}
            }
            ,
            taskCount : {
                validators : {"stringLength":{"min":1,"max":1,"message":"任务数长度不正确"},"regexp":{"regexp":"^[1-9]{1}$","message":"任务数只能是1到9的数字"}}
            }
            ,
            internetName : {
                validators : {"stringLength":{"min":1,"max":100,"message":"网卡名称长度不正确"},"regexp":{"regexp":"^.*$","message":"网卡名称只能是英文与数字"}}
            }
            ,
            serverIp : {
                validators : {"stringLength":{"min":1,"max":100,"message":"服务器IP长度不正确"},"regexp":{"regexp":"^.*$","message":"服务器IP格式不正确"}}
            }
            ,
            businessInfo : {
                validators : {"stringLength":{"min":0,"max":100,"message":"业务参数长度不正确"},"regexp":{"regexp":"^.*$","message":"业务参数格式不正确"}}
            }
            ,
            configTime : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^.+$","message":"XX信息与要求的格式不符"}}
            }
            ,
            upPerson : {
                validators : {"stringLength":{"message":"修改人长度不正确","min":1,"max":100},"regexp":{"message":"修改人格式不正确","regexp":"^.*$"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         console.log("查询校验通过");
     });

});

function searchTaskBasicConfig(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#queryTaskBasicConfigForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#queryTaskBasicConfigForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#queryTaskBasicConfigForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#queryTaskBasicConfigForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#queryTaskBasicConfigcurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#queryTaskBasicConfigpageSize").val();
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

    params.id = $("#queryTaskBasicConfigForm #id").val();
    params.taskName = $("#queryTaskBasicConfigForm #taskName").val();
    params.parentId = $("#queryTaskBasicConfigForm #parentId").val();
    params.runRule = $("#queryTaskBasicConfigForm #runRule").val();
    params.runStatus = $("#queryTaskBasicConfigForm input[name='runStatus']:checked").val();
    params.status = $("#queryTaskBasicConfigForm input[name='status']:checked").val();
    params.businessObjName = $("#queryTaskBasicConfigForm #businessObjName").val();
    params.taskCount = $("#queryTaskBasicConfigForm #taskCount").val();
    params.internetName = $("#queryTaskBasicConfigForm #internetName").val();
    params.serverIp = $("#queryTaskBasicConfigForm #serverIp").val();
    params.businessInfo = $("#queryTaskBasicConfigForm #businessInfo").val();
    params.configTime = $("#queryTaskBasicConfigForm #configTime").val();
    params.upPerson = $("#queryTaskBasicConfigForm #upPerson").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/taskBasicConfig/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/taskBasicConfig/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#taskBasicConfigTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#queryTaskBasicConfigcurrentPage").val(data.rows[0].startIndex);
                $("#queryTaskBasicConfigtotalPage").val(data.rows[0].totalPage);
                $("#queryTaskBasicConfigpageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var taskBasicConfigTr = $("<tr class=\"taskBasicConfigTr\"></tr>");
                    $("#taskBasicConfigTR_FIRST").parent().append(taskBasicConfigTr);
                    taskBasicConfigTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].id+"</td>")
                    .append("<td>"+resultList[i].taskName+"</td>")
                    .append("<td>"+resultList[i].parentId+"</td>")
                    .append("<td>"+resultList[i].runRule+"</td>")
                    .append("<td>"+resultList[i].runStatus_name+"</td>")
                    .append("<td>"+resultList[i].status_name+"</td>")
                    .append("<td>"+resultList[i].businessObjName+"</td>")
                    .append("<td>"+resultList[i].taskCount+"</td>")
                    .append("<td>"+resultList[i].internetName+"</td>")
                    .append("<td>"+resultList[i].serverIp+"</td>")
                    .append("<td>"+resultList[i].businessInfo+"</td>")
                    .append("<td>"+resultList[i].configTime+"</td>")
                    .append("<td>"+resultList[i].upPerson+"</td>")
                    .append("<td>"+resultList[i].upTime+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editTaskBasicConfig('"+resultList[i].id+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delTaskBasicConfig('"+resultList[i].id+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return queryTaskBasicConfigObject('"+resultList[i].id+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#queryTaskBasicConfigcurrentPage").val());
                var totalPage   = Number($("#queryTaskBasicConfigtotalPage").val());
                var pageSize    = Number($("#queryTaskBasicConfigpageSize").val());
                $("#queryTaskBasicConfig_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: queryTaskBasicConfiggopage
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

function queryTaskBasicConfiggopage(currentPage) {
    searchTaskBasicConfig(currentPage);
}

function addTaskBasicConfig(){
    var url=webUrl+"/taskBasicConfig/insert.dhtml";
    var params={};
    params.taskName = $("#validateAddTaskBasicConfigForm #taskName1").val();
    params.parentId = $("#validateAddTaskBasicConfigForm #parentId1").val();
    params.runRule = $("#validateAddTaskBasicConfigForm #runRule1").val();
    params.runStatus = $("#validateAddTaskBasicConfigForm input[name='runStatus1']:checked").val();
    params.status = $("#validateAddTaskBasicConfigForm input[name='status1']:checked").val();
    params.businessObjName = $("#validateAddTaskBasicConfigForm #businessObjName1").val();
    params.taskCount = $("#validateAddTaskBasicConfigForm #taskCount1").val();
    params.internetName = $("#validateAddTaskBasicConfigForm #internetName1").val();
    params.serverIp = $("#validateAddTaskBasicConfigForm #serverIp1").val();
    params.businessInfo = $("#validateAddTaskBasicConfigForm #businessInfo1").val();
    params.configTime = $("#validateAddTaskBasicConfigForm #configTime1").val();
    params.upPerson = $("#validateAddTaskBasicConfigForm #upPerson1").val();
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
                $('#addTaskBasicConfig').modal('hide');
                searchTaskBasicConfig('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddTaskBasicConfigForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddTaskBasicConfigForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddTaskBasicConfigForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddTaskBasicConfigForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateTaskBasicConfig(){
    var url=webUrl+"/taskBasicConfig/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateTaskBasicConfigForm #id_key").val();
    params.taskName = $("#validateUpdateTaskBasicConfigForm #taskName3").val();
    params.parentId = $("#validateUpdateTaskBasicConfigForm #parentId3").val();
    params.runRule = $("#validateUpdateTaskBasicConfigForm #runRule3").val();
    params.runStatus = $("#validateUpdateTaskBasicConfigForm input[name='runStatus3']:checked").val();
    params.status = $("#validateUpdateTaskBasicConfigForm input[name='status3']:checked").val();
    params.businessObjName = $("#validateUpdateTaskBasicConfigForm #businessObjName3").val();
    params.taskCount = $("#validateUpdateTaskBasicConfigForm #taskCount3").val();
    params.internetName = $("#validateUpdateTaskBasicConfigForm #internetName3").val();
    params.serverIp = $("#validateUpdateTaskBasicConfigForm #serverIp3").val();
    params.businessInfo = $("#validateUpdateTaskBasicConfigForm #businessInfo3").val();
    params.configTime = $("#validateUpdateTaskBasicConfigForm #configTime3").val();
    params.upPerson = $("#validateUpdateTaskBasicConfigForm #upPerson3").val();
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
                $('#updateTaskBasicConfig').modal('hide');
                searchTaskBasicConfig('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateTaskBasicConfigForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateTaskBasicConfigForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateTaskBasicConfigForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateTaskBasicConfigForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editTaskBasicConfig(id_key){
    var url=webUrl+"/taskBasicConfig/queryObject.dhtml";
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
                $('#updateTaskBasicConfig').modal();
                $("#validateUpdateTaskBasicConfigForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblTaskBasicConfig;
                $("#validateUpdateTaskBasicConfigForm #id3").val(objInfo.id!=null?objInfo.id:'');
                $("#validateUpdateTaskBasicConfigForm #taskName3").val(objInfo.taskName!=null?objInfo.taskName:'');
                $("#validateUpdateTaskBasicConfigForm #parentId3").val(objInfo.parentId!=null?objInfo.parentId:'');
                $("#validateUpdateTaskBasicConfigForm #runRule3").val(objInfo.runRule!=null?objInfo.runRule:'');
                //使用原生javascript对radio进行操作，避免浏览器不兼容问题
                $("#validateUpdateTaskBasicConfigForm input[name='runStatus3'][value='"+(objInfo.runStatus!=null?objInfo.runStatus:'')+"']")[0].checked=true;
                //使用原生javascript对radio进行操作，避免浏览器不兼容问题
                $("#validateUpdateTaskBasicConfigForm input[name='status3'][value='"+(objInfo.status!=null?objInfo.status:'')+"']")[0].checked=true;
                $("#validateUpdateTaskBasicConfigForm #businessObjName3").val(objInfo.businessObjName!=null?objInfo.businessObjName:'');
                $("#validateUpdateTaskBasicConfigForm #taskCount3").val(objInfo.taskCount!=null?objInfo.taskCount:'');
                $("#validateUpdateTaskBasicConfigForm #internetName3").val(objInfo.internetName!=null?objInfo.internetName:'');
                $("#validateUpdateTaskBasicConfigForm #serverIp3").val(objInfo.serverIp!=null?objInfo.serverIp:'');
                $("#validateUpdateTaskBasicConfigForm #businessInfo3").val(objInfo.businessInfo!=null?objInfo.businessInfo:'');
                $("#validateUpdateTaskBasicConfigForm #configTime3").val(objInfo.configTime!=null?objInfo.configTime:'');
                $("#validateUpdateTaskBasicConfigForm #upPerson3").val(objInfo.upPerson!=null?objInfo.upPerson:'');
                $("#validateUpdateTaskBasicConfigForm #upTime3").val(objInfo.upTime!=null?objInfo.upTime:'');
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

function queryTaskBasicConfigObject(id_key){
    var url=webUrl+"/taskBasicConfig/queryObject.dhtml";
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
                $('#detailTaskBasicConfig').modal();
                $("#validateDetailTaskBasicConfigForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblTaskBasicConfig;
                $("#validateDetailTaskBasicConfigForm #id2").val(objInfo.id!=null?objInfo.id:'');
                $("#validateDetailTaskBasicConfigForm #taskName2").val(objInfo.taskName!=null?objInfo.taskName:'');
                $("#validateDetailTaskBasicConfigForm #parentId2").val(objInfo.parentId!=null?objInfo.parentId:'');
                $("#validateDetailTaskBasicConfigForm #runRule2").val(objInfo.runRule!=null?objInfo.runRule:'');
                $("#validateDetailTaskBasicConfigForm #runStatus2").val(objInfo.runStatus_name!=null?objInfo.runStatus_name:'');
                $("#validateDetailTaskBasicConfigForm #status2").val(objInfo.status_name!=null?objInfo.status_name:'');
                $("#validateDetailTaskBasicConfigForm #businessObjName2").val(objInfo.businessObjName!=null?objInfo.businessObjName:'');
                $("#validateDetailTaskBasicConfigForm #taskCount2").val(objInfo.taskCount!=null?objInfo.taskCount:'');
                $("#validateDetailTaskBasicConfigForm #internetName2").val(objInfo.internetName!=null?objInfo.internetName:'');
                $("#validateDetailTaskBasicConfigForm #serverIp2").val(objInfo.serverIp!=null?objInfo.serverIp:'');
                $("#validateDetailTaskBasicConfigForm #businessInfo2").val(objInfo.businessInfo!=null?objInfo.businessInfo:'');
                $("#validateDetailTaskBasicConfigForm #configTime2").val(objInfo.configTime!=null?objInfo.configTime:'');
                $("#validateDetailTaskBasicConfigForm #upPerson2").val(objInfo.upPerson!=null?objInfo.upPerson:'');
                $("#validateDetailTaskBasicConfigForm #upTime2").val(objInfo.upTime!=null?objInfo.upTime:'');
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
function delTaskBasicConfig(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/taskBasicConfig/del.dhtml";
        var params={};
        var currentPage= $("#queryTaskBasicConfigcurrentPage").val();
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
                    searchTaskBasicConfig(currentPage);
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


