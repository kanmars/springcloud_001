<#if !isLogin && s!="login" && s!="logout">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>SN-taskadmin管理登录</title>
    </head>
    <body>
    <form action="/?type=login" method="post">
        帐号:<input name = "userName" type="text"><br>
        密码:<input name = "password" type="password"><br>
        <input type="submit" value="登录" >
    </form>
    </body>
    </html>
</#if>

<#if !isLogin && s=="login" >
    <#if passwordCheck>
    <html><script>window.location.href="./";</script></html>
    <#else>
    <html><script>window.location.href="./?type=logout";</script></html>
    </#if>
</#if>

<#if s=="logout" >
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>SN-taskadmin管理登录</title>
    </head>
    <body>
    <form action="/?type=login" method="post">
        帐号:<input name = "userName" type="text"><br>
        密码:<input name = "password" type="password"><br>
        <input type="submit" value="登录" >
    </form>
    </body>
    </html>
</#if>

<#if isLogin && s=="" >
    <html>
    <body>
    <style>
        table{ border:solid 1px black}
        tr{ border:solid 1px black;padding: 0;margin: 0;}
        td{ border:solid 1px black;margin:0;cellspacing:0;}
    </style>
    <script src="/js/jquery-1.7.1.min.js"></script>
    <script>
        $(function(){
            $("table thead tr").css("background-color","#006030");
            $("table thead tr").css("color","#FFFFFF");
            $("table thead tr").css("font-weight","800");
            $("table tbody tr:even").css("background-color","#DFFFDF");
            $("table tbody tr:odd").css("background-color","#ECFFFF");
            $("table tbody tr").mouseover(function() {
                $("table tbody tr:even").css("background-color","#DFFFDF");
                $("table tbody tr:odd").css("background-color","#ECFFFF");
                $(this).css("background-color","#FFFF37");
            });
            $("table tbody tr").mouseout(function() {
                $("table tbody tr:even").css("background-color","#DFFFDF");
                $("table tbody tr:odd").css("background-color","#ECFFFF");
            });
        })
    </script>
    <span><h2>全局定时任务监控页面</h2><a href="/?type=logout"><h6>退出登录</h6></a></span>

    <h5>任务执行情况</h5>
    <table>
        <tr>
            <td>搜索条件:</td>
            <td><input id="searchStr"></td>
            <td>排序类型:</td>
            <td>
                <select id="paixuType">
                    <option value="1" selected="selected">任务名称</option>
                    <option value="2">分组</option>
                    <option value="3">执行IP</option>
                    <option value="4">全局序号</option>
                    <option value="5">最后更新时间</option>
                </select>
                <select id="ascFlg">
                    <option value="1" selected="selected">正序</option>
                    <option value="2">倒序</option>
                </select>
            </td>
            <td><input id="searchButn" type="button" value="查询" onclick="searchPage()"></td>
        </tr>
    </table>
    <script>
        //更新查询条件
        function getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
        var searchStr_url = getQueryString("searchStr");
        var paixuType_url = getQueryString("paixuType");
        var ascFlg_url = getQueryString("ascFlg");
        if(searchStr_url!=null)$("#searchStr").val(decodeURIComponent(searchStr_url));
        if(paixuType_url!=null)$("#paixuType").val(decodeURIComponent(paixuType_url));
        if(ascFlg_url!=null)$("#ascFlg").val(decodeURIComponent(ascFlg_url));

        //绑定查询事件
        function searchPage(){
            var searchStr = $("#searchStr").val();
            var paixuType = $("#paixuType").val();
            var ascFlg = $("#ascFlg").val();
            window.location.href="/?searchStr="+encodeURIComponent(searchStr)+"&paixuType="+encodeURIComponent(paixuType)+"&ascFlg="+encodeURIComponent(ascFlg);
        }
    </script>
    ${tableInfo}
    </body>
    </html>
</#if>

<#if isLogin && s=="taskTriger">
    ${trigerMsg}
</#if>


