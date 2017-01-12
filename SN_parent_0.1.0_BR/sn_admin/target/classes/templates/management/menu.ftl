<#macro menuHtml>
<script>
    $(function(){
        var menuList=${menuList!''};
        var node=$('.sidebar-menu');
        setMenu(menuList,node);
    });

    function needJqueryLoadPage(domain,port){
        //在此处列出需要用jqueryLoad的地址的列表
        if(domain =="10.58.52.64" && port =="9999"){
            return true;
        }
        return false;
    }


    function addTab(id,menuName,menuUrl){
        $(".tab_content").hide();
        $("#tabul li").removeClass('active');
        //alert(id);
        //--------------解析当前域信息----------------------
        var regex = new RegExp("^(((http://)|(https://))([^/:]*)(:([0-9]*))?)(/)?");
        var currWindowUrl = window.location.href;
        var currprotocol = "";
        var currdomain = "";
        var currport="";
        var currp_d_p = regex.exec(currWindowUrl);//解析出后，位置2，5，7分别为protocol，domain,port
        currprotocol = currp_d_p[2];
        currdomain = currp_d_p[5];
        currport = currp_d_p[7];
        //--------------解析menuUrl域信息----------------------
        var protocol = "";
        var domain = "";
        var port="";
        var p_d_p = regex.exec(menuUrl);//解析出后，位置2，5，7分别为protocol，domain,port
        //有如下几种情况
        //1、无domain，则设置webUrl为""空字符串
        //2、有domain，则设置weburl为protocol+domain+port
        //                          如果没有protocol，则默认设置为http://
        //                          如果没有port，则默认设置为80
        var isSameDomain = true;
        if(p_d_p==null || p_d_p ==undefined || p_d_p[5]==undefined || p_d_p[5]=="" ){
            webUrl="";
        }else{
            //protocol = "http://";
            protocol = currprotocol;
            if(p_d_p[2]!=undefined && p_d_p[2]!=""){
                protocol = p_d_p[2];
            }
            domain = currdomain;
            if(p_d_p[5]!=undefined && p_d_p[5]!=""){
                domain = p_d_p[5];
            }
            port = "80";
            if(p_d_p[7]!=undefined && p_d_p[7]!=""){
                port = p_d_p[7];
            }
            webUrl = protocol+domain+port;
            if(currprotocol!=protocol||currport!=port) {
                isSameDomain = false;
            }
            if(currprotocol==protocol&&currport==port) {
                for(var i=currdomain.length-1;i>=0;i--){
                    if(currdomain.charAt(i)!=domain.charAt(i)){
                        isSameDomain = false;
                    }
                }
            }
        }

        if($("#tab_"+id).text() == ''){
            $('#tabul').append(
                    "<li  value=\"tab_content"+id+"\"   class=\"active\" role=\"presentation\" id=\"tab_"+id+"\"><a  onclick=\"switchTab('tab_content"+id+"','"+menuUrl+"')\" href=\"#tab_"+id+"\" aria-controls=\"tab_"+id+"\" role=\"tab\" data-toggle=\"tab\">"+menuName+"</a> <i class=\"close-tab glyphicon glyphicon-remove\"></i></li>"
            );
            if(isSameDomain||needJqueryLoadPage(currdomain,currport)){
                //如果同域
                $("#mainPan").append($("<div id='tab_content"+id+"' class='tab_content' style='display:block'></div>"));
                $("#tab_content"+id).load(menuUrl,function(responseText,textStatus,XMLHttpRequest){
                    if(textStatus == 'error'){
                        $("#tab_content"+id).load('../err.html');
                    }
                });
            }else{
                //如果不同域
                $("#mainPan").append($("<div id='tab_content"+id+"' class='tab_content' style='display:block;height:1280px'><iframe style='width: 100%;height:100%;border:0px;' src='"+menuUrl+"'></iframe></div>"));
                $("#tab_content"+id).show();
            }


        }else{
            $("#tab_"+id).addClass("active");
            $("#tab_content"+id+"").slideDown();//show
        }
        //在标签页切换时，对webUrl变量进行修改，进而修改各个环境的默认请求地址
        console.log("[menu.ftl] tab发生切换，webUrl更新为["+webUrl+"]");

    }


    function setMenu(obj,node){
        if(obj){
            for(var i=0; i<obj.length;i++){
                var menuUrl = obj[i].menuUrl ;
                if(menuUrl==null||menuUrl=="null"){
                    menuUrl = "#" ;
                }
                var menuType = obj[i].menuType ;
                if(menuType=="010"){
                    //空菜单
                    node.append("<li id=\""+"treeview"+i+"\" class=\"treeview\"><a href=\""+menuUrl+"\"><i class=\"fa fa-plus-square\"></i> <span>"+obj[i].menuName+"</span><i class=\"fa fa-angle-left pull-right\"></i></a>");
                }else if(menuType="020"){
                    //带资源的菜单
                    node.append("<li id=\""+"treeview"+i+"\" class=\"treeview\" onclick=\"addTab('"+"li-menu-"+i+"','"+obj[i].menuName+"','"+menuUrl+"')\"   ><a   href=\""+"javascript:void(0)"+"\"><i class=\"fa fa-plus-square\"></i><span>"	+ obj[i].menuName + "</span><i class=\"fa fa-angle-left pull-right\"></i></a></li>");
                }else{
                    //不做操作
                }
                var child= obj[i].child;
                if(typeof(child) !="undefined"){
                    var nodeNew = $('#treeview'+i);
                    setChildMenu(child,nodeNew,"ul-menu-"+i);
                }
                $('.sidebar-menu').append("</li>");
            }
        }
    }
    function setChildMenu(menuChild, nodeNew,menuId) {
        var menuChildObj;
        nodeNew.append("<ul  class=\"treeview-menu\" id=\""+menuId+"\"></ul>");
        for (var j = 0; j < menuChild.length; j++) {
            menuChildObj= menuChild[j].child;
            var menuUrl = menuChild[j].menuUrl;
            if(menuUrl==null||menuUrl=="null"){
                menuUrl = "#" ;
            }
            var menuType = menuChild[j].menuType ;
            if(menuType=="010"){
                //空菜单
                var menu = $("#"+menuId);
                menu.append("<li id=\"treeview-menu"+ menuChild[j].menuNo+"_"+j +"\"><a href=\""+menuUrl+"\"><i class=\"fa fa-file-o\"></i>"	+ menuChild[j].menuName + "<i class=\"fa fa-angle-left pull-right\"></i></a></li>");
                var nodeNew1 = $('#treeview-menu'+menuChild[j].menuNo+"_"+j);
                setChildMenu(menuChildObj, nodeNew1,menuChild[j].menuNo+"_"+j);
            }else if(menuType=="020"){
                //带资源的菜单
                var menu = $("#"+menuId);
                menu.append("<li   onclick=\"addTab('"+menuId+j+"','"+menuChild[j].menuName+"','"+menuUrl+"')\"   id=\"treeview-menu"+ menuChild[j].menuNo+"_"+j +"\"><a   href=\""+"javascript:void(0)"+"\"><i class=\"fa fa-file-o\"></i>"	+ menuChild[j].menuName + "</a></li>");
            }else {
                //不做任何操作
            }
        }
    }
</script>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${user.userName!''}</p>
                <a href="javascript:void(0)"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
        <!--
        <form action="#" method="get" class="sidebar-form">
          <div class="input-group">
            <input type="text" name="q" class="form-control" placeholder="Search...">
            <span class="input-group-btn">
              <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
            </span>
          </div>
        </form>
        -->
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单导航</li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

</#macro>