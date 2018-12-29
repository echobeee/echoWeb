<!DOCTYPE html>
<html>
  <head>
    <title>echoWeb</title>
	
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
    
    <script type="text/javascript">  
	    onload=function(){  
	    	if("${message}" == "activated"){
	    		sweetAlert("您的账号激活成功！");
	    	} else if("${message}" == "reset"){
	    		sweetAlert("邮件发送成功，请尽快到您指定的邮箱修改密码！");
	    	} else {
	    		sweetAlert("Uh oh..")
	    	}
	        setInterval(go, 1000);  
	    };  
	    var x=3; //利用了全局变量来执行  
	    function go(){  
	    x--;  
	        if(x>0){  
	        document.getElementById("sp").innerHTML=x;  //每次设置的x的值都不一样了。  
	        }else{  
	        location.href='${request.contextPath}/go/user/login';  
	        }  
	    }  
	</script>  
    
    <style type="text/css">
    body {
    	background-color:#f6f6f6;
    }
    </style>
    
  </head>
  
  <body >
  <!-- 表单被嵌套在背景中 -->
<div id="particles-js">
    <!--登陆-->
    <div class="container" style="position: absolute; top:115px; left:355px;">
		<h1 style="margin-bottom: 20px;left: 200px;position: relative;">
		在<font id="sp">3</font>s后跳转到登陆页面....
		</h1>
       
    </div>
</div>


		<!-- 背景JS -->
<script src="${request.contextPath}/js/background/particles.js"></script>
<script src="${request.contextPath}/js/background/app.js"></script>
	</body>

</html>

