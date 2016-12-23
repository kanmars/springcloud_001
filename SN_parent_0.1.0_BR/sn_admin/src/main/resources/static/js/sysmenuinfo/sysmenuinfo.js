$(function(){
    searchSysmenuInfo('');
    $('#querySysmenuInfopageSize').on('change',function () {
        var currentPage = $("#querySysmenuInfocurrentPage").val();
        searchSysmenuInfo(currentPage);
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
    $('#updateSysmenuInfo').on('shown.bs.modal', function () {
        $('#validateUpdateSysmenuInfoForm').bootstrapValidator('resetForm', false);
    });
    $('#querySysmenuInfoForm #createTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysmenuInfoForm","createTime");
    });
    $('#validateAddSysmenuInfoForm #createTime1').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateAddSysmenuInfoForm","createTime1");
    });
    $('#validateUpdateSysmenuInfoForm #createTime3').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateUpdateSysmenuInfoForm","createTime3");
    });
    $('#querySysmenuInfoForm #upTime').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("querySysmenuInfoForm","upTime");
    });
    $('#validateAddSysmenuInfoForm #upTime1').datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd hh:ii:ss",
        minuteStep:1
    }).change(function(){
        revalidateDate("validateAddSysmenuInfoForm","upTime1");
    });

    //增加系统菜单表校验
    $('#validateAddSysmenuInfoForm').bootstrapValidator({
        fields : {
            menuNo1 : {
                validators : {"notEmpty":{"message":"菜单编号不能为空"},"stringLength":{"min":4,"max":8,"message":"菜单编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"菜单编号只能是数字"}}
            }
            ,
            menuName1 : {
                validators : {"notEmpty":{"message":"菜单名称不能为空"},"stringLength":{"min":1,"max":20,"message":"菜单名称度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"菜单名称只能是中文、英文与数字"}}
            }
            ,
            menuType1 : {
                validators : {"notEmpty":{"message":"请选择一个菜单类型"}}
            }
            ,
            parentMenuCode1 : {
                validators : {"notEmpty":{"message":"菜单编号不能为空"},"stringLength":{"min":4,"max":8,"message":"父菜单编码长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"父菜单编码只能是数字"}}
            }
            ,
            menuUrl1 : {
                validators : {"notEmpty":{"message":"菜单url不能为空"},"stringLength":{"min":1,"max":100,"message":"菜单url长度不正确"},"regexp":{"regexp":"^.*$","message":"菜单url格式不正确"}}
            }
            ,
            menuIdx1 : {
                validators : {"notEmpty":{"message":"菜单排序不能为空"},"stringLength":{"min":1,"max":10,"message":"菜单排序长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"菜单排序必须是正整数"}}
            }
            ,
            applicationCode1 : {
                validators : {"notEmpty":{"message":"请选择一个静态单选按钮"}}
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
         addSysmenuInfo();
     });

    //修改系统菜单表校验
    $('#validateUpdateSysmenuInfoForm').bootstrapValidator({
        fields : {
            menuNo3 : {
                validators : {"notEmpty":{"message":"菜单编号不能为空"},"stringLength":{"min":4,"max":8,"message":"菜单编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"菜单编号只能是数字"}}
            }
            ,
            menuName3 : {
                validators : {"notEmpty":{"message":"菜单名称不能为空"},"stringLength":{"min":1,"max":20,"message":"菜单名称度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"菜单名称只能是中文、英文与数字"}}
            }
            ,
            menuType3 : {
                validators : {"notEmpty":{"message":"请选择一个菜单类型"}}
            }
            ,
            parentMenuCode3 : {
                validators : {"notEmpty":{"message":"菜单编号不能为空"},"stringLength":{"min":4,"max":8,"message":"父菜单编码长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"父菜单编码只能是数字"}}
            }
            ,
            menuUrl3 : {
                validators : {"notEmpty":{"message":"菜单url不能为空"},"stringLength":{"min":1,"max":100,"message":"菜单url长度不正确"},"regexp":{"regexp":"^.*$","message":"菜单url格式不正确"}}
            }
            ,
            menuIdx3 : {
                validators : {"notEmpty":{"message":"菜单排序不能为空"},"stringLength":{"min":1,"max":10,"message":"菜单排序长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"菜单排序必须是正整数"}}
            }
            ,
            applicationCode3 : {
                validators : {"notEmpty":{"message":"请选择一个静态单选按钮"}}
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
         updateSysmenuInfo();
     });
    //查询系统菜单表校验
    $('#querySysmenuInfoForm').bootstrapValidator({
        fields : {
            menuNo : {
                validators : {"stringLength":{"min":4,"max":8,"message":"菜单编号长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"菜单编号只能是数字"}}
            }
            ,
            menuName : {
                validators : {"stringLength":{"min":1,"max":20,"message":"菜单名称度不正确"},"regexp":{"regexp":"^[\\u4E00-\\u9FA5A-Za-z0-9_]+$","message":"菜单名称只能是中文、英文与数字"}}
            }
            ,
            menuType : {
                validators : {}
            }
            ,
            parentMenuCode : {
                validators : {"stringLength":{"min":4,"max":8,"message":"父菜单编码长度不正确"},"regexp":{"regexp":"^[0-9]*$","message":"父菜单编码只能是数字"}}
            }
            ,
            menuUrl : {
                validators : {"stringLength":{"min":1,"max":100,"message":"菜单url长度不正确"},"regexp":{"regexp":"^.*$","message":"菜单url格式不正确"}}
            }
            ,
            menuIdx : {
                validators : {"stringLength":{"min":1,"max":10,"message":"菜单排序长度不正确"},"regexp":{"regexp":"^[1-9]+[0-9]*$","message":"菜单排序必须是正整数"}}
            }
            ,
            applicationCode : {
                validators : {}
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

function searchSysmenuInfo(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#querySysmenuInfoForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#querySysmenuInfoForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#querySysmenuInfoForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#querySysmenuInfoForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#querySysmenuInfocurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#querySysmenuInfopageSize").val();
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

    params.menuNo = $("#querySysmenuInfoForm #menuNo").val();
    params.menuName = $("#querySysmenuInfoForm #menuName").val();
    params.menuType = $("#querySysmenuInfoForm #menuType").val();
    params.parentMenuCode = $("#querySysmenuInfoForm #parentMenuCode").val();
    params.menuUrl = $("#querySysmenuInfoForm #menuUrl").val();
    params.menuIdx = $("#querySysmenuInfoForm #menuIdx").val();
    params.applicationCode = $("#querySysmenuInfoForm input[name='applicationCode']:checked").val();
    params.createTime = $("#querySysmenuInfoForm #createTime").val();
    params.upTime = $("#querySysmenuInfoForm #upTime").val();

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/sysmenuInfo/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/sysmenuInfo/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#sysmenuInfoTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#querySysmenuInfocurrentPage").val(data.rows[0].startIndex);
                $("#querySysmenuInfototalPage").val(data.rows[0].totalPage);
                $("#querySysmenuInfopageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var sysmenuInfoTr = $("<tr class=\"sysmenuInfoTr\"></tr>");
                    $("#sysmenuInfoTR_FIRST").parent().append(sysmenuInfoTr);
                    sysmenuInfoTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].menuNo+"</td>")
                    .append("<td>"+resultList[i].menuName+"</td>")
                    .append("<td>"+resultList[i].menuType_name+"</td>")
                    .append("<td>"+resultList[i].parentMenuCode+"</td>")
                    .append("<td>"+resultList[i].menuUrl+"</td>")
                    .append("<td>"+resultList[i].menuIdx+"</td>")
                    .append("<td>"+resultList[i].applicationCode_name+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td>"+resultList[i].upTime+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editSysmenuInfo('"+resultList[i].menuNo+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delSysmenuInfo('"+resultList[i].menuNo+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return querySysmenuInfoObject('"+resultList[i].menuNo+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#querySysmenuInfocurrentPage").val());
                var totalPage   = Number($("#querySysmenuInfototalPage").val());
                var pageSize    = Number($("#querySysmenuInfopageSize").val());
                $("#querySysmenuInfo_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: querySysmenuInfogopage
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

function querySysmenuInfogopage(currentPage) {
    searchSysmenuInfo(currentPage);
}

function addSysmenuInfo(){
    var url=webUrl+"/sysmenuInfo/insert.dhtml";
    var params={};
    params.menuNo = $("#validateAddSysmenuInfoForm #menuNo1").val();
    params.menuName = $("#validateAddSysmenuInfoForm #menuName1").val();
    params.menuType = $("#validateAddSysmenuInfoForm #menuType1").val();
    params.parentMenuCode = $("#validateAddSysmenuInfoForm #parentMenuCode1").val();
    params.menuUrl = $("#validateAddSysmenuInfoForm #menuUrl1").val();
    params.menuIdx = $("#validateAddSysmenuInfoForm #menuIdx1").val();
    params.applicationCode = $("#validateAddSysmenuInfoForm input[name='applicationCode1']:checked").val();
    params.createTime = $("#validateAddSysmenuInfoForm #createTime1").val();
    params.upTime = $("#validateAddSysmenuInfoForm #upTime1").val();
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
                $('#addSysmenuInfo').modal('hide');
                searchSysmenuInfo('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddSysmenuInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddSysmenuInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddSysmenuInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddSysmenuInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateSysmenuInfo(){
    var url=webUrl+"/sysmenuInfo/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateSysmenuInfoForm #id_key").val();
    params.menuNo = $("#validateUpdateSysmenuInfoForm #menuNo3").val();
    params.menuName = $("#validateUpdateSysmenuInfoForm #menuName3").val();
    params.menuType = $("#validateUpdateSysmenuInfoForm #menuType3").val();
    params.parentMenuCode = $("#validateUpdateSysmenuInfoForm #parentMenuCode3").val();
    params.menuUrl = $("#validateUpdateSysmenuInfoForm #menuUrl3").val();
    params.menuIdx = $("#validateUpdateSysmenuInfoForm #menuIdx3").val();
    params.applicationCode = $("#validateUpdateSysmenuInfoForm input[name='applicationCode3']:checked").val();
    params.createTime = $("#validateUpdateSysmenuInfoForm #createTime3").val();
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
                $('#updateSysmenuInfo').modal('hide');
                searchSysmenuInfo('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateSysmenuInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateSysmenuInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateSysmenuInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateSysmenuInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editSysmenuInfo(id_key){
    var url=webUrl+"/sysmenuInfo/queryObject.dhtml";
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
                $('#updateSysmenuInfo').modal();
                $("#validateUpdateSysmenuInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysmenuInfo;
                $("#validateUpdateSysmenuInfoForm #menuNo3").val(objInfo.menuNo!=null?objInfo.menuNo:'');
                $("#validateUpdateSysmenuInfoForm #menuName3").val(objInfo.menuName!=null?objInfo.menuName:'');
                $("#validateUpdateSysmenuInfoForm #menuType3").val(objInfo.menuType!=null?objInfo.menuType:'');
                $("#validateUpdateSysmenuInfoForm #parentMenuCode3").val(objInfo.parentMenuCode!=null?objInfo.parentMenuCode:'');
                $("#validateUpdateSysmenuInfoForm #menuUrl3").val(objInfo.menuUrl!=null?objInfo.menuUrl:'');
                $("#validateUpdateSysmenuInfoForm #menuIdx3").val(objInfo.menuIdx!=null?objInfo.menuIdx:'');
                //使用原生javascript对radio进行操作，避免浏览器不兼容问题
                $("#validateUpdateSysmenuInfoForm input[name='applicationCode3'][value='"+(objInfo.applicationCode!=null?objInfo.applicationCode:'')+"']")[0].checked=true;
                $("#validateUpdateSysmenuInfoForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateUpdateSysmenuInfoForm #upTime3").val(objInfo.upTime!=null?objInfo.upTime:'');
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

function querySysmenuInfoObject(id_key){
    var url=webUrl+"/sysmenuInfo/queryObject.dhtml";
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
                $('#detailSysmenuInfo').modal();
                $("#validateDetailSysmenuInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblSysmenuInfo;
                $("#validateDetailSysmenuInfoForm #menuNo2").val(objInfo.menuNo!=null?objInfo.menuNo:'');
                $("#validateDetailSysmenuInfoForm #menuName2").val(objInfo.menuName!=null?objInfo.menuName:'');
                $("#validateDetailSysmenuInfoForm #menuType2").val(objInfo.menuType_name!=null?objInfo.menuType_name:'');
                $("#validateDetailSysmenuInfoForm #parentMenuCode2").val(objInfo.parentMenuCode!=null?objInfo.parentMenuCode:'');
                $("#validateDetailSysmenuInfoForm #menuUrl2").val(objInfo.menuUrl!=null?objInfo.menuUrl:'');
                $("#validateDetailSysmenuInfoForm #menuIdx2").val(objInfo.menuIdx!=null?objInfo.menuIdx:'');
                $("#validateDetailSysmenuInfoForm #applicationCode2").val(objInfo.applicationCode_name!=null?objInfo.applicationCode_name:'');
                $("#validateDetailSysmenuInfoForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateDetailSysmenuInfoForm #upTime2").val(objInfo.upTime!=null?objInfo.upTime:'');
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
function delSysmenuInfo(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/sysmenuInfo/del.dhtml";
        var params={};
        var currentPage= $("#querySysmenuInfocurrentPage").val();
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
                    searchSysmenuInfo(currentPage);
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


