<#macro contentHtml>
<script type="text/javascript">
	$(function(){
	    $('#tabs').addtabs();
	})
	//加载主页面
	$(function(){
		$("#mainPan").load("../welcome.html");
	});
	//切换标签
	function switchTab(contentId,url){
        //在标签页切换时，对webUrl变量进行修改，进而修改各个环境的默认请求地址
        var protocol = "";
        var domain = "";
        var port="";
        var regex = new RegExp("^(((http://)|(https://))([^/:]*)(:([0-9]*))?)(/)?");
        var p_d_p = regex.exec(url);//解析出后，位置2，5，7分别为protocol，domain,port
        //有如下几种情况
        //1、无domain，则设置webUrl为""空字符串
        //2、有domain，则设置weburl为protocol+domain+port
        //                          如果没有protocol，则默认设置为http://
        //                          如果没有port，则默认设置为80
        if(p_d_p==null || p_d_p ==undefined || p_d_p[5]==undefined || p_d_p[5]=="" ){
            webUrl="";
        }else{
            protocol = "http://";
            if(p_d_p[2]!=undefined && p_d_p[2]!=""){
                protocol = p_d_p[2];
            }
            domain = "localhost";
            if(p_d_p[5]!=undefined && p_d_p[5]!=""){
                domain = p_d_p[5];
            }
            port = "80";
            if(p_d_p[7]!=undefined && p_d_p[7]!=""){
                port = p_d_p[7];
            }
            webUrl = protocol+domain+port;
        }

        console.log("[menu.ftl] tab发生切换，webUrl更新为["+webUrl+"]");
        //样式切换
		$(".tab_content").slideUp();//none
		$("#"+contentId+"").slideDown();//show
	}
</script>
<div style="min-height: 916px;" class="content-wrapper">
    <div class="col-md-12" style="float:none;">
        <div id="tabs" >
            <!-- Nav tabs -->
            <ul  id="tabul" class="nav nav-tabs" role="tablist">
               <li  value="tab_content_home" onclick="switchTab('tab_content_home','/welcome.html')"  class="active" role="presentation" id="tab_home"><a href="javascript:void(0)" aria-controls="tab_home" role="tab" data-toggle="tab">Home</a></li>  
          		 <!--<li class="" role="presentation" id="tab_tab_message" onclick="switchTab('tab_tab_message')"><a aria-expanded="true" href="#tab_message" aria-controls="tab_message" role="tab" data-toggle="tab">我的消息</a> <i class="close-tab glyphicon glyphicon-remove"></i></li>                           
                 <li class="" role="presentation" id="tab_tab_mail"    onclick="switchTab('tab_tab_mail')"   ><a aria-expanded="true" href="#tab_mail"    aria-controls="tab_mail"    role="tab" data-toggle="tab">我的邮件</a> <i class="close-tab glyphicon glyphicon-remove"></i></li> -->
            </ul>
            <!-- Tab panes -->
            <div class="tab-content" id="mainPan">
            </div>
        </div>
    </div>
</div>
<style>
    #tabul li, #tabul li .close-tab{padding-top:10px;}
</style>
</#macro>
