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
	       <!--setInterval(go, 1000); -->   
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
	    	color: darkblue;
	    }
	    .container {
		    position: relative;
		    left: 22%;
		    top: 28%;
		    width: 100%;
    	}
    	h1 {
   		    top: 42%;
		    left: 44%;
		    position: fixed;
		}

    </style>
    
  </head>
  
  <body >

    <!--登陆-->
    <div class="container">
        <#switch "${message}">
        <#case "noUser">
            <h1 >Oops...没有该用户哦</h1>
            <img src="${request.contextPath}/images/Oops.jpg"/>
            <#break>
        <#case "acitvated">
            <h1 >该用户已经激活啦</h1>
		    <img src="${request.contextPath}/images/success.jpg"/>
            <#break>
         <#case "disabled">
            <h1 >Oops~该链接已经失效</h1>
            <img src="${request.contextPath}/images/Oops.jpg"/>
            <#break>
         <#case "incorrect">
            <h1 >Oops~这个链接本客栈没有哟...</h1>
            <img src="${request.contextPath}/images/404.jpg"/>
            <#break>
        </#switch>
       
    </div>
	</body>

</html>

