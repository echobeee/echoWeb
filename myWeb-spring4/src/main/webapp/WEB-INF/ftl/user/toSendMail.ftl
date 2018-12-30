<!DOCTYPE html>
<html>
  <head>
    <title>修改密碼</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Register">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
 	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
 	<META HTTP-EQUIV="Expires" CONTENT="0">
    
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
	$(function(){
		 $('form').bootstrapValidator({
	        submitHandler: function (validator, form, submitButton) {
	        	var sendObj = $("#userId").val();
	        	console.log(sendObj);
	            //ajax请求
	            $.ajax({
	                url: path + "/user/forgetPassword.do",
	                type: "post",
	                data: {"userId":sendObj},
	                success: function (data) { 
	                    var responseText = data;
	                    console.log(responseText);
	                    if (responseText == "noUser"){
	                    	sweetAlert("该账号不存在！");
	                    } else if (responseText == "succuss") {
	                    	swal({title:"发送邮件成功！",
							        text:"请尽快到账号指定邮箱修改密码！",
							        type:"success"},
							        function(){window.location = path}
							    );
	                    } else {

	                        //出错了也回到首页吧
	                        window.location.href = path;
	                    }
						$('#sub').removeAttr('disabled');
	
	                },
	                error: function (response, ajaxOptions, thrownError) {
	                    console.log(response);
	                    console.log(ajaxOptions);
	                    console.log(thrownError);
	                    sweetAlert("系统错误");
	                }
	            });
            }
        });
         function go(){  
	    x--;  
	        if(x>0){  
	        document.getElementById("sp").innerHTML=x;  //每次设置的x的值都不一样了。  
	        }else{  
	        location.href='${request.contextPath}/go/user/login';  
	        }  
	    }  
        })
	</script>
	
    
  </head>
  
  <body >
  <!-- 表单被嵌套在背景中 -->
<div id="particles-js">
    <!--登陆-->
    <div class="container" style="position: absolute; top:180px; left:355px;">
		<h1 style="margin-bottom: 45px;left: 200px;font-color:white;position: relative;">修改密码</h1>
		
      <form class="form-inline" role="form" style="margin-bottom: 40px;left: 120px;position: relative;">
	      <h4 class="text-primary">请输入想更改密码的账号</h4>
		 	 <div class="form-group">
			    <input type="text" id="userId"
			    		class="form-control" 
			    		style="width: 300px; margin-right: 15px;" 
			    		placeholder="账号用于登陆">
		 	 </div>
		  <button type="submit" id="sub" class="btn btn-success">确认</button>
		  <br>
		  <div style="margin-top:10px; font-size: 15px"> 
		  	<a href="${request.contextPath}/go/user/login">登陆</a>
		  </div>
		</form>
    </div>
</div>


		<!-- 背景JS -->
<script src="${request.contextPath}/js/background/particles.js"></script>
<script src="${request.contextPath}/js/background/app.js"></script>
	</body>

</html>

