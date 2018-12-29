<!DOCTYPE html>
<html>
  <head>
    <title>登录页面</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Register">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!-- icon -->
    <link rel = "Shortcut Icon" href="${request.contextPath}/images/echoWeb.ico">
    
    <!--Jquery-->
    <script src="${request.contextPath}/js/jquery-3.2.1.min.js"></script>
    
    <!-- 背景样式 -->
    <link rel="stylesheet" media="screen" href="${request.contextPath}/styles/background.css">
    
      <!--弹出框-->
    <script src="${request.contextPath}/js/sweetalert.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/styles/sweetalert.css">
    
    <!--bootstrap与validation样式和JS-->
    <link href="${request.contextPath}/bootstrap-3.3.7-dist/css/bootstrapValidator.min.css" rel="stylesheet">
    <link href="${request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script src="${request.contextPath}/bootstrap-3.3.7-dist/js/bootstrapValidator.min.js"></script>
    
     <!--使用JS获取项目根路径-->
    <script>
        var path = "";
        $(function () {
            var strFullPath = window.document.location.href;
            var strPath = window.document.location.pathname;
            var pos = strFullPath.indexOf(strPath);
            var prePath = strFullPath.substring(0, pos);
            var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
            path = prePath + postPath;
        });
    </script>
    
    <script src="${request.contextPath}/js/user/login.js?v1.03"></script>
    
  </head>
  
  <body >
  <!-- 表单被嵌套在背景中 -->
<div id="particles-js">
    <!--登陆-->
    <div class="container" style="position: absolute; top:115px; left:355px;">
		<h1 style="margin-bottom: 20px;left: 200px;position: relative;">登录</h1>
       <form id="login" class="form-horizontal" role="form" action="${request.contextPath }/user/login.do" method="post">
       <div class="form-group form row">
		    <label for="firstname" class="col-sm-2 control-label">用户账号：</label>
		    <div class="col-sm-3">
		      <input type="text"  class="form-control col-lg-3" name="username" >
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="lastname" class="col-sm-2 control-label">密码：</label>
		    <div class="col-sm-3">
		      <input type="password" class="form-control col-lg-3" id="password" name="password">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="lastname" class="col-sm-2 control-label">验证码：</label>
		    <div class="col-sm-3">
		      <input type="text" class="form-control col-lg-3" id="inputCaptcha" name="randomcode" />
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <div class="checkbox">
		        <label>
		          <input type="checkbox" name="rememberMe">记住我
		        </label>
		        <img id="captcha" class="col-sm-offset-1" style="margin-left: 3.33333333%;" src="${request.contextPath}/user/getValidCode" alt="验证码找不到了！"/>
		      </div>
		    </div>
		  </div>
		  <div class="form-group">
		   <div class="col-sm-offset-2">
			    <div class="col-sm-9" style="width: 30%;">
			      <button type="submit" id="submitButton" class="form-control btn btn-info pull-right">登录</button>
			    </div>
		    </div>
		  </div>
		   <div class="form-group">
		   <div class="col-sm-offset-2">
			    <div class="col-sm-9" >
			      <a style="padding-right: 175px;" href="${request.contextPath }/go/user/toSendMail">忘记密码</a>
			      <a href="${request.contextPath }/go/user/register">注册</a>
			    </div>
		    </div>
		  </div>
		</form>
    </div>
</div>


		<!-- 背景JS -->
<script src="${request.contextPath}/js/background/particles.js"></script>
<script src="${request.contextPath}/js/background/app.js"></script>
	</body>

</html>

