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
    
    <!-- md5Hash -->
    <script src="${request.contextPath}/js/md5/md5.js"></script>
    
    <script src="${request.contextPath}/js/user/reset.js?v0.26"></script>
    
  </head>
  
  <body >
  <!-- 表单被嵌套在背景中 -->
<div id="particles-js">
    <!--登陆-->
    <div class="container" style="position: absolute; top:115px; left:355px;">
		<h1 style="margin-bottom: 20px;left: 200px;position: relative;">密码修改</h1>
       <form class="form-horizontal" role="form" action="${request.contextPath}/user/newPassword.do" method="post">
     	 <input type="hidden" name="userId" value="${userId}" /> 
         <div class="form-group form row">
		    <label for="firstname" class="col-sm-2 control-label">新密码：</label>
		    <div class="col-sm-3">
		      <input type="password"  class="form-control col-lg-3" name="newPassword" >
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="lastname" class="col-sm-2 control-label">密码确认：</label>
		    <div class="col-sm-3">
		      <input type="password" class="form-control col-lg-3"name="confirmPassword">
		    </div>
		  </div>
		  
		  <div class="form-group">
		   <div class="col-sm-offset-2">
			    <div class="col-sm-9" style="width: 13%;">
			      <button type="submit" id="submitButton" class="form-control btn btn-info pull-right">确认</button>
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

