$(function(){
    searchDemoInfo('');
    $('#queryDemoInfopageSize').on('change',function () {
        var currentPage = $("#queryDemoInfocurrentPage").val();
        searchDemoInfo(currentPage);
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
    $('#updateDemoInfo').on('shown.bs.modal', function () {
        $('#validateUpdateDemoInfoForm').bootstrapValidator('resetForm', false);
    });
    $('#queryDemoInfoForm #createDate').daterangepicker({
        showAfterOpen:false,
        //startDate:"2016-12-03",
        //endDate:"2016-12-06",
        //minDate:"2016-12-02",
        //maxDate:"2016-12-31",
        timePicker: false,
        timePicker24Hour: false,
        timePickerSeconds: false,
        locale:{
            format: 'YYYY-MM-DD'
        }
    },function(start, end, label) {
        console.log(start.toISOString(), end.toISOString(), label);
    }).change(function(){
        revalidateDate("queryDemoInfoForm","createDate");
    });
    $('#validateAddDemoInfoForm #createDate1').datepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd"
    }).change(function(){
        revalidateDate("validateAddDemoInfoForm","createDate1");
    });
    $('#validateUpdateDemoInfoForm #createDate3').datepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd"
    }).change(function(){
        revalidateDate("validateUpdateDemoInfoForm","createDate3");
    });
    $('#queryDemoInfoForm #createTime').daterangepicker({
        showAfterOpen:false,
        //startDate:"2016-12-03",
        //endDate:"2016-12-06",
        //minDate:"2016-12-02",
        //maxDate:"2016-12-31",
        timePicker: false,
        timePicker24Hour: false,
        timePickerSeconds: false,
        locale:{
            format: 'YYYY-MM-DD'
        }
    },function(start, end, label) {
        console.log(start.toISOString(), end.toISOString(), label);
    }).change(function(){
        revalidateDate("queryDemoInfoForm","createTime");
    });
    $('#validateAddDemoInfoForm #createTime1').datepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd"
    }).change(function(){
        revalidateDate("validateAddDemoInfoForm","createTime1");
    });
    $('#validateUpdateDemoInfoForm #createTime3').datepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd"
    }).change(function(){
        revalidateDate("validateUpdateDemoInfoForm","createTime3");
    });

    //增加DEMO表信息演示校验
    $('#validateAddDemoInfoForm').bootstrapValidator({
        fields : {
            demoNm1 : {
                validators : {"stringLength":{"message":"演示名称长度不正确","min":1,"max":100},"notEmpty":{"message":"演示名称不能为空"},"regexp":{"message":"演示名称只能是英文与数字","regexp":"^[a-zA-Z0-9]*$"}}
            }
            ,
            demoMoney1 : {
                validators : {"stringLength":{"message":"演示金额长度不正确","min":1,"max":100},"notEmpty":{"message":"演示金额不能为空"},"regexp":{"message":"演示金额格式不符","regexp":"^(([1-9][0-9]{0,9})|0)((.[0-9]{1,2})?)$"}}
            }
            ,
            createDate1 : {
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
            selectStatic1 : {
                validators : {"notEmpty":{"message":"请选择一个静态选择框"}}
            }
            ,
            selectDynamic1 : {
                validators : {"notEmpty":{"message":"请选择一个动态选择框"}}
            }
            ,
            radioStatic1 : {
                validators : {"notEmpty":{"message":"请选择一个静态单选按钮"}}
            }
            ,
            radioDynamic1 : {
                validators : {"notEmpty":{"message":"请选择一个动态单选按钮"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         addDemoInfo();
     });

    //修改DEMO表信息演示校验
    $('#validateUpdateDemoInfoForm').bootstrapValidator({
        fields : {
            demoNm3 : {
                validators : {"stringLength":{"message":"演示名称长度不正确","min":1,"max":100},"notEmpty":{"message":"演示名称不能为空"},"regexp":{"message":"演示名称只能是英文与数字","regexp":"^[a-zA-Z0-9]*$"}}
            }
            ,
            demoMoney3 : {
                validators : {"stringLength":{"message":"演示金额长度不正确","min":1,"max":100},"notEmpty":{"message":"演示金额不能为空"},"regexp":{"message":"演示金额格式不符","regexp":"^(([1-9][0-9]{0,9})|0)((.[0-9]{1,2})?)$"}}
            }
            ,
            createDate3 : {
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
            selectStatic3 : {
                validators : {"notEmpty":{"message":"请选择一个静态选择框"}}
            }
            ,
            selectDynamic3 : {
                validators : {"notEmpty":{"message":"请选择一个动态选择框"}}
            }
            ,
            radioStatic3 : {
                validators : {"notEmpty":{"message":"请选择一个静态单选按钮"}}
            }
            ,
            radioDynamic3 : {
                validators : {"notEmpty":{"message":"请选择一个动态单选按钮"}}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         updateDemoInfo();
     });
    //查询DEMO表信息演示校验
    $('#queryDemoInfoForm').bootstrapValidator({
        fields : {
            demoId : {
                validators : {"stringLength":{"message":"表ID长度不正确","min":1,"max":100},"regexp":{"message":"表ID与要求的格式不符","regexp":"^[0-9]*$"}}
            }
            ,
            demoNm : {
                validators : {"stringLength":{"message":"演示名称长度不正确","min":1,"max":100},"regexp":{"message":"演示名称只能是英文与数字","regexp":"^[a-zA-Z0-9]*$"}}
            }
            ,
            demoMoney : {
                validators : {"stringLength":{"message":"演示金额长度不正确","min":1,"max":100},"regexp":{"message":"演示金额格式不符","regexp":"^(([1-9][0-9]{0,9})|0)((.[0-9]{1,2})?)$"}}
            }
            ,
            createDate : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^([0-9]{4}-[0-9]{2}-[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2})$","message":"时间格式不正确"}}
            }
            ,
            createTime : {
                validators : {"stringLength":{"min":1,"max":100,"message":"XX信息长度不正确"},"regexp":{"regexp":"^([0-9]{4}-[0-9]{2}-[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2})$","message":"时间格式不正确"}}
            }
            ,
            selectStatic : {
                validators : {}
            }
            ,
            selectDynamic : {
                validators : {}
            }
            ,
            radioStatic : {
                validators : {}
            }
            ,
            radioDynamic : {
                validators : {}
            }
        }
     }).on('success.form.bv', function(e) {
         e.preventDefault();
         console.log("查询校验通过");
     });

});

function searchDemoInfo(currentPage,queryType,fileName,exportParam){
    //没有bootstrapvalidator，则返回
    if($('#queryDemoInfoForm').data('bootstrapValidator')!=null) {
        //对查询框进行校验
        $('#queryDemoInfoForm').data('bootstrapValidator').validate();
        //校验没有通过，则返回
        if(!$('#queryDemoInfoForm').data('bootstrapValidator').isValid()){
            return;
        }
        //----个性化校验示例开始
        //if($('#query"+cleanentityClassName+"Form #被验证组件name').val()=='#'){
        //    layer.alert('XX禁止查询')
        //    //第三个参数为，显示的错误信息所属的validator规则
        //    $('#queryDemoInfoForm').data('bootstrapValidator').updateStatus('被验证组件name','INVALID','stringLength');
        //    return;
        //}
        //----个性化校验示例结束
    }
    var params ={};
    if(currentPage == "undefined" || currentPage =="" || currentPage == null){
        var currentPage_text = $("#queryDemoInfocurrentPage").val();
        if(currentPage_text =="undefined" || currentPage_text =="" || currentPage_text == null){
            currentPage_text = "1";
        }
        params.startIndex = currentPage_text;
    }else{
        params.startIndex = currentPage;
    }

    var pageSize= $("#queryDemoInfopageSize").val();
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

    params.demoId = $("#queryDemoInfoForm #demoId").val();
    params.demoNm = $("#queryDemoInfoForm #demoNm").val();
    params.demoMoney = $("#queryDemoInfoForm #demoMoney").val();
    var createDate_regexp = new RegExp('^([0-9]{4}-[0-9]{2}-[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2})$');
    var createDate_value = $("#queryDemoInfoForm #createDate").val();
    if(createDate_regexp.test(createDate_value)){
        var cs = createDate_regexp.exec(createDate_value);
        params.createDate_start = cs[1]||'';
        params.createDate_end = cs[4]||'';
    }
    
    var createTime_regexp = new RegExp('^([0-9]{4}-[0-9]{2}-[0-9]{2})( )*至( )*([0-9]{4}-[0-9]{2}-[0-9]{2})$');
    var createTime_value = $("#queryDemoInfoForm #createTime").val();
    if(createTime_regexp.test(createTime_value)){
        var cs = createTime_regexp.exec(createTime_value);
        params.createTime_start = cs[1]||'';
        params.createTime_end = cs[4]||'';
    }
    
    params.selectStatic = $("#queryDemoInfoForm #selectStatic").val();
    params.selectDynamic = $("#queryDemoInfoForm #selectDynamic").val();
    params.radioStatic = $("#queryDemoInfoForm input[name='radioStatic']:checked").val();
    params.radioDynamic = $("#queryDemoInfoForm input[name='radioDynamic']:checked").val();
    //对于checkbox复选框，作为查询条件默认注释掉，否则将导致数据查询不出。如有需要，请根据需求放开
    //params.checkboxStatic = getCheckBoxVal("queryDemoInfoForm","checkboxStatic");

    var jsonStr = JSON.stringify(params);
    jsonStr = encodeURI(jsonStr);
    jsonStr = encodeURI(jsonStr);
    if(queryType!=undefined && queryType =='download'){
        window.location.href=webUrl+"/demoInfo/search.dhtml?jsonStr="+jsonStr+"&v="+Math.random();
        return;
    }
    
    $.ajax({
        type:"POST",
        url:webUrl+"/demoInfo/search.dhtml",
        timeout:60000,
        dataType:'json',
        data:"jsonStr="+jsonStr,
        success:function(data){
            if(data.code == "success"){
                $("#demoInfoTR_FIRST").siblings().remove();
                var  resultList=data.rows[0].pageRecords;

                //分页
                $("#queryDemoInfocurrentPage").val(data.rows[0].startIndex);
                $("#queryDemoInfototalPage").val(data.rows[0].totalPage);
                $("#queryDemoInfopageSize").val(data.rows[0].pageSize);
                for(var i=0;i<resultList.length;i++){
                    var demoInfoTr = $("<tr class=\"demoInfoTr\"></tr>");
                    $("#demoInfoTR_FIRST").parent().append(demoInfoTr);
                    demoInfoTr.append("<td><input style=\"width:23px;\" type=\"checkbox\"/></td><td>"+(i+1)+"</td>")
                    .append("<td>"+resultList[i].demoNm+"</td>")
                    .append("<td>"+resultList[i].demoMoney+"</td>")
                    .append("<td>"+resultList[i].createDate+"</td>")
                    .append("<td>"+resultList[i].createTime+"</td>")
                    .append("<td>"+resultList[i].selectStatic_name+"</td>")
                    .append("<td>"+resultList[i].selectDynamic_name+"</td>")
                    .append("<td>"+resultList[i].radioStatic_name+"</td>")
                    .append("<td>"+resultList[i].radioDynamic_name+"</td>")
                    .append("<td>"+resultList[i].checkboxStatic+"</td>")
                    .append("<td class=\"operateTbl\"><button class=\"btn btn-default btn-sm\" onclick=\"return editDemoInfo('"+resultList[i].demoId+"');\"><i class=\"fa fa-edit\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return  delDemoInfo('"+resultList[i].demoId+"');\"><i class=\"fa fa-trash-o\"></i></button><button class=\"btn btn-default btn-sm\" onclick=\"return queryDemoInfoObject('"+resultList[i].demoId+"');\"><i class=\"fa fa-search\"></i></button></td>");
                }
                var currentPage = Number($("#queryDemoInfocurrentPage").val());
                var totalPage   = Number($("#queryDemoInfototalPage").val());
                var pageSize    = Number($("#queryDemoInfopageSize").val());
                $("#queryDemoInfo_fy .pager").ucPager({
                    currentPage : currentPage,
                    totalPage : totalPage,
                    pageSize : pageSize,
                    clickCallback: queryDemoInfogopage
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

function queryDemoInfogopage(currentPage) {
    searchDemoInfo(currentPage);
}

function addDemoInfo(){
    var url=webUrl+"/demoInfo/insert.dhtml";
    var params={};
    params.demoNm = $("#validateAddDemoInfoForm #demoNm1").val();
    params.demoMoney = $("#validateAddDemoInfoForm #demoMoney1").val();
    params.createDate = $("#validateAddDemoInfoForm #createDate1").val();
    params.createTime = $("#validateAddDemoInfoForm #createTime1").val();
    params.selectStatic = $("#validateAddDemoInfoForm #selectStatic1").val();
    params.selectDynamic = $("#validateAddDemoInfoForm #selectDynamic1").val();
    params.radioStatic = $("#validateAddDemoInfoForm input[name='radioStatic1']:checked").val();
    params.radioDynamic = $("#validateAddDemoInfoForm input[name='radioDynamic1']:checked").val();
    params.checkboxStatic = getCheckBoxVal("validateAddDemoInfoForm","checkboxStatic1");
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
                $('#addDemoInfo').modal('hide');
                searchDemoInfo('');
                layer.msg('添加成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateAddDemoInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateAddDemoInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
                $('#validateAddDemoInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateAddDemoInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function updateDemoInfo(){
    var url=webUrl+"/demoInfo/edit.dhtml";
    var params={};
    params.id_key = $("#validateUpdateDemoInfoForm #id_key").val();
    params.demoNm = $("#validateUpdateDemoInfoForm #demoNm3").val();
    params.demoMoney = $("#validateUpdateDemoInfoForm #demoMoney3").val();
    params.createDate = $("#validateUpdateDemoInfoForm #createDate3").val();
    params.createTime = $("#validateUpdateDemoInfoForm #createTime3").val();
    params.selectStatic = $("#validateUpdateDemoInfoForm #selectStatic3").val();
    params.selectDynamic = $("#validateUpdateDemoInfoForm #selectDynamic3").val();
    params.radioStatic = $("#validateUpdateDemoInfoForm input[name='radioStatic3']:checked").val();
    params.radioDynamic = $("#validateUpdateDemoInfoForm input[name='radioDynamic3']:checked").val();
    params.checkboxStatic = getCheckBoxVal("validateUpdateDemoInfoForm","checkboxStatic3");
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
                $('#updateDemoInfo').modal('hide');
                searchDemoInfo('');
                layer.msg('修改成功', {icon: 1,time: 2000,offset: 'rb' }, function(){});
                $('#validateUpdateDemoInfoForm').bootstrapValidator('resetForm', true);
            }else{
                layer.alert(data.message);
                $('#validateUpdateDemoInfoForm  button[type="submit"]').attr('disabled',false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            layer.alert('系统异常，请稍后重试！');
            $('#validateUpdateDemoInfoForm  button[type="submit"]').attr('disabled',false);
        },
        complete : function(XMLHttpRequest,status){
            if(status == "timeout"){
                ajaxTimeoutTest.abort();
                alert("操作超时！");
                $('#validateUpdateDemoInfoForm  button[type="submit"]').attr('disabled',false);
            }
        }
    });
}

function editDemoInfo(id_key){
    var url=webUrl+"/demoInfo/queryObject.dhtml";
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
                $('#updateDemoInfo').modal();
                $("#validateUpdateDemoInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblDemoInfo;
                $("#validateUpdateDemoInfoForm #demoId3").val(objInfo.demoId!=null?objInfo.demoId:'');
                $("#validateUpdateDemoInfoForm #demoNm3").val(objInfo.demoNm!=null?objInfo.demoNm:'');
                $("#validateUpdateDemoInfoForm #demoMoney3").val(objInfo.demoMoney!=null?objInfo.demoMoney:'');
                $("#validateUpdateDemoInfoForm #createDate3").val(objInfo.createDate!=null?objInfo.createDate:'');
                $("#validateUpdateDemoInfoForm #createTime3").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateUpdateDemoInfoForm #selectStatic3").val(objInfo.selectStatic!=null?objInfo.selectStatic:'');
                $("#validateUpdateDemoInfoForm #selectDynamic3").val(objInfo.selectDynamic!=null?objInfo.selectDynamic:'');
                //使用原生javascript对radio进行操作，避免浏览器不兼容问题
                $("#validateUpdateDemoInfoForm input[name='radioStatic3'][value='"+(objInfo.radioStatic!=null?objInfo.radioStatic:'')+"']")[0].checked=true;
                //使用原生javascript对radio进行操作，避免浏览器不兼容问题
                $("#validateUpdateDemoInfoForm input[name='radioDynamic3'][value='"+(objInfo.radioDynamic!=null?objInfo.radioDynamic:'')+"']")[0].checked=true;
                setCheckBoxVal("validateUpdateDemoInfoForm","checkboxStatic3",(objInfo.checkboxStatic!=null?objInfo.checkboxStatic:''));
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

function queryDemoInfoObject(id_key){
    var url=webUrl+"/demoInfo/queryObject.dhtml";
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
                $('#detailDemoInfo').modal();
                $("#validateDetailDemoInfoForm #id_key").val(data.rows[0].id_key);
                var objInfo = data.rows[0].tblDemoInfo;
                $("#validateDetailDemoInfoForm #demoId2").val(objInfo.demoId!=null?objInfo.demoId:'');
                $("#validateDetailDemoInfoForm #demoNm2").val(objInfo.demoNm!=null?objInfo.demoNm:'');
                $("#validateDetailDemoInfoForm #demoMoney2").val(objInfo.demoMoney!=null?objInfo.demoMoney:'');
                $("#validateDetailDemoInfoForm #createDate2").val(objInfo.createDate!=null?objInfo.createDate:'');
                $("#validateDetailDemoInfoForm #createTime2").val(objInfo.createTime!=null?objInfo.createTime:'');
                $("#validateDetailDemoInfoForm #selectStatic2").val(objInfo.selectStatic_name!=null?objInfo.selectStatic_name:'');
                $("#validateDetailDemoInfoForm #selectDynamic2").val(objInfo.selectDynamic_name!=null?objInfo.selectDynamic_name:'');
                $("#validateDetailDemoInfoForm #radioStatic2").val(objInfo.radioStatic_name!=null?objInfo.radioStatic_name:'');
                $("#validateDetailDemoInfoForm #radioDynamic2").val(objInfo.radioDynamic_name!=null?objInfo.radioDynamic_name:'');
                setCheckBoxVal("validateDetailDemoInfoForm","checkboxStatic2",(objInfo.checkboxStatic!=null?objInfo.checkboxStatic:''));
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
function delDemoInfo(id_key){
    layer.confirm('是否删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var url=webUrl+"/demoInfo/del.dhtml";
        var params={};
        var currentPage= $("#queryDemoInfocurrentPage").val();
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
                    searchDemoInfo(currentPage);
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


