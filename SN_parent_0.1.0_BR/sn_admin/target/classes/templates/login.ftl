<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${systemName!'神农后台管理系统'}</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${props('resourceUrl')}/css/bootstrap/bootstrapValidator.css">
    
    <style type="text/css">
    html, body, div,p{margin:0;padding:0;font-weight:400;}
    body{background:url('${props('resourceUrl')}${imgUrl!""}') no-repeat;width:100%;}
    /*-webkit-opacity:0.5;-moz-opacity:0.5;-khtml-opacity:0.5;opacity:0.5;filter:alpha(opacity=50);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=50)";filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=50);*/
    .login-header,.login-footer{width:100%;height:50px;color:#FFF;font-size:12px;line-height:50px;}
    .login-header .logo-img{display:block;float:left;height:50px;width:50px;background:url('${props('resourceUrl')}/img/user-lock.png') no-repeat scroll 0px -5px;}
    .login-header .logo-title{display:block;float:left;height:50px;font-size:20px;font-weight:bold;}
    .login-box{position:absolute;margin:200px 710px;width:500px;height:350px;background:black;background:url('${props('resourceUrl')}/img/login-box.png') no-repeat;
	    padding:35px 65px 35px 65px;}
	.userInfo{width:370px;height:70px;overflow:hidden;margin-bottom:0px;}
	.userInfo .colName{float:left;display:inline-block;width:60px;height:40px;margin-bottom:0px;padding:0px;color:#fff;line-height:65px;text-align:left;}
	.userInfo .userName{background:url('${props('resourceUrl')}/img/user-lock.png') no-repeat scroll 0px -5px;}
	.userInfo .userPwd{background:url('${props('resourceUrl')}/img/user-lock.png') no-repeat scroll 0px -70px;}
	.userInfo .code-text{float:left;display:inline-block;width:60px;height:40px;color:#fff;line-height:55px;}
	.userInfo .code{background:none;border:1px solid #fff;color:#fff;width:80px;height:32px;line-height:34px;text-align:center;display:block;float:right;letter-spacing:4px;font-size:14px;font-family:Arial;margin-top:8px;border-radius:20px;cursor:pointer;}
	.userInfo input{float:left;height:40px;width:100%;background:none;border:none;border-bottom:1px solid #fff;color: #fff;padding-top:15px;font-size:14px;}
	.userInfo .btn-login{width:100%;height:40px;background:none;border:1px solid #fff;color:#fff;-moz-border-radius:20px;-webkit-border-radius:20px;border-radius:20px;}
    </style>
  </head>
  <body>
  	<div class="login-header">
  		<p class="logo-img"></p>
  		<p class="logo-title">${systemName!'神农后台管理系统'}</p>
  	</div>
	<form action="loginCheck.dhtml" method="post" id="validateLogin">
	  	<input type="hidden" name="goUrl" value="${goUrl!''}">
	  	<input type="hidden" name="checkCodeOld" value="${checkCode!""}">
	  
	  	<div class="login-box">
			<div class="form-group userInfo">
				<label class="col-lg-3 colName userName"></label>
				<div class="col-lg-9" style="width:310px;padding:0px;">
                    <input type="text" name="userAccount" value='${userAccount!""}'
                    data-bv-message="The username is not valid"
    
                    data-bv-notempty="true"
                    data-bv-notempty-message="用户名不能为空"
    
                    data-bv-regexp="true"
                    data-bv-regexp-regexp="[a-zA-Z0-9_\.]+"
                    data-bv-regexp-message="The username can only consist of alphabetical, number, dot and underscore"
    
                    data-bv-stringlength="true"
                    data-bv-stringlength-min="3"
                    data-bv-stringlength-max="30"
                    data-bv-stringlength-message="用户名在3-30个字符之间"
    
                    />
                </div>
			</div>
			<div class="form-group userInfo">
				<label class="col-lg-3 colName userPwd"></label>
				<div class="col-lg-9" style="width:310px;padding:0px;">
					 <input type="password" name="password"
		                data-bv-notempty="true"
		                data-bv-notempty-message="密码不能为空"
		                
		                data-bv-stringlength="true"
	                    data-bv-stringlength-min="3"
	                    data-bv-stringlength-max="30"
	                    data-bv-stringlength-message="密码在3-30个字符之间"
		                />
				</div>
			</div>
			<div class="form-group userInfo">
				<label class="col-lg-3 colName">验证码</label>
				<div class="col-lg-6" style="padding:0px;width:230px;">
					<input type="text"  name="checkCode" value="">
				</div>
				<div class="col-lg-3" style="padding:0px;width:80px;">
            		<span id="checkCodeBtn" class="code" onclick="refreskCheckCode()" >${checkCode!""}</span>
            	</div>
			</div>
			<div class="userInfo" style="padding:20px 0px 0px;">
				<button type="submit" class="btn-login">登录</button>
			</div>
			<span id="messageId" class="code" style="color: red">${message!""}</span>
		</div>
	</form>
	<div class="login-footer" style="position:fixed; bottom:0;">
		<p style="padding-left:20px;">@2000-2016  KANMARS</p>
	</div>
    <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrap.min.js"></script>
    <script type="text/javascript" src="${props('resourceUrl')}/js/plugins/bootstrapValidator.js"></script>
    <script>
	$(function () {
		$('#validateLogin').bootstrapValidator();
	});
        
    function refreskCheckCode(){
        $.ajax({
            type:"POST",
            url:"${props('resourceUrl')}/login/refreshANewCheckCode.dhtml",
            timeout:10000,
            dataType:'json',
            success:function(data){
                $("#checkCodeBtn").text(data);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                //alert('您的点击过于频繁！');
            },
            complete : function(XMLHttpRequest,status){
                if(status == "timeout"){
                    ajaxTimeoutTest.abort();
                    alert("获取验证码！");
                }
            }
        });
    }
    </script>
  </body>
</html>
