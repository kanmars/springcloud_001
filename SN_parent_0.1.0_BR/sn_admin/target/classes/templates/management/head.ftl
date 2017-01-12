<#macro headHtml>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>神农后台管理系统</title>
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"	name="viewport">
		<!-- jQuery 2.1.4 -->
		<#include "/management/resourceofhead.ftl"/>
        <script language="JavaScript">
			//避免后退键
            javascript:window.history.forward(1);
            //var webUrl = "${props('resourceUrl')}";
            //在menu.ftl的增加标签页的动作中，或者在content.ftl中每切换一个标签页tab，则修改一次webUrl-》用于支持多点登录。
			//警告，要谨慎跨域问题，因此在url中必须是同域的地址，包含：协议，域名，端口
			//此功能是为了解决联合登录问题。有此功能，则可以在不同地方登录，所有的系统之间可以互通
        	var webUrl = "";


	</script>
        <script language="JavaScript">
            //登录超时的自动退出
			function checkIsLogin(){
                console.log("校验用户是否登录--start")
				var isLogin = true;
                $.ajax({
                    type:"POST",
                    url:webUrl+"/login/isLogin.dhtml",
                    timeout:60000,
                    dataType:'json',
                    data:"rd="+Math.random(),
                    success:function(data){
                        if(data.rows[0].result == "true"){
                            isLogin = true;
                        }else{
							//没有登录时，跳转到登录页面
                            window.location.href="/login/login.dhtml"
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown){
						console.log("校验用户是否登录异常")
                    },
                    complete : function(XMLHttpRequest,status){
                        if(status == "timeout"){
                            ajaxTimeoutTest.abort();
                            console.log("校验用户是否登录超时")
                        }
                    }
                });
			}
            window.setInterval(checkIsLogin,10000);
        </script>
       
	</head>
	<body class="hold-transition skin-red sidebar-mini">
		<div class="wrapper">
			<!--header begin-->
			<header class="main-header">
				<a href="javascript:void()" class="logo">
					<span class="logo-mini"><b>A</b>LT</span>
					<span class="logo-lg"><b>Admin</b>LTE</span>
				</a>
				<nav class="navbar navbar-static-top" role="navigation">
					<a href="#" class="sidebar-toggle" data-toggle="offcanvas"	role="button"> 
						<span class="sr-only">Toggle navigation</span> 
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</a>
					<div class="navbar-custom-menu">
						<ul class="nav navbar-nav">
							<li class="dropdown user user-menu">
								<a aria-expanded="true" href="#" class="dropdown-toggle" data-toggle="dropdown"  >
									<img src="/img/user2-160x160.jpg" class="user-image" alt="User Image">
									<span class="hidden-xs">${user.userAccount!''}</span>
								</a>
								<ul class="dropdown-menu" id="loginOut">
									<!-- User image -->
									<li class="user-header" style="height:210px;">
										<img src="/img/user2-160x160.jpg" class="img-circle" alt="User Image">
										<p>${user.userAccount!''}<small>${user.userAccount!''}</small></p>
									</li>
									<li class="user-footer">
										<div class="pull-right"><a href="${props('resourceUrl')}/login/logout.dhtml" class="btn btn-default btn-flat">退出</a></div>
									</li>
								</ul>
							</li>
						</ul>
					</div>
				</nav>
			</header>
			<!--header end-->
</#macro>