<!DOCTYPE html>
<html>
  <head>
    <title>首页</title>
	
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
    
	 
	<!-- popper.min.js 用于弹窗、提示、下拉菜单 -->
	<script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
	 
	<!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
	<script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
    
     
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
    
    <style type="text/css">
	body {
		background-color: black;
		color: white;
		
	}
	.videos {
		position: relative;
		left: 40%;
	}

	.datas {
		margin-left: 2%;
		

	}
	.list-group-item {
		background-color: black;
		border: 1px solid black;
		font-size: 14px;
	}
	</style>
      
  </head>
  
  <body >
  <!-- 表单被嵌套在背景中 -->
	<div >
		<nav class="navbar navbar-default navbar-static-top" role="navigation">
		    <div class="container-fluid">
		    <div class="navbar-header">
		        <a class="navbar-brand" href="#">echoWeb</a>
		    </div>
		    <div>
		        <ul class="nav navbar-nav">
		            
		            
		            <li class="active"><a href=""> 慎思明辨</a></li>
		            
		            <li class="dropdown">
            	<a href="#" class="dropdown-toggle" data-toggle="dropdown">德才兼備<b class="caret"></b></a> 
             <ul class="dropdown-menu">
                    <li><a  href="${request.contextPath }/user/welcome.do">主頁</a></li>
                </ul>
            
            </li>
		            
		            <li class="dropdown">
		                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
		                 家國情懷    <b class="caret"></b>
		                </a>
		                <ul class="dropdown-menu">
		                    <li><a href="${request.contextPath}/go/memo/memos">備忘錄</a></li>
		                </ul>
		            </li>
		            <li class="dropdown">
		            <a href="#" class="dropdown-toggle" data-toggle="dropdown">領袖氣質<b class="caret"></b></a> 
		             <ul class="dropdown-menu">
		                    <li><a  href="${request.contextPath}/go/favorite/all">收藏夾</a></li>
		                </ul>
		            
		            </li>
		            
		        </ul>
		
		        <div>
		        
		    </div>
				
		        <p style="padding-right: 37px;" class="navbar-text navbar-right"><a href="${request.contextPath}/user/logout.do">註銷</a></p>
		        <#if (user)??>
				 <p style="padding-right: 37px;" class="navbar-text navbar-right">${user.userNickname}</p>
				    <#else>
				   
				</#if>
		    </div>
		    </div>
		    </nav> 
		    
		    <h1 style="margin-bottom: 20px;left: 200px;position: relative;">TikTok Video</h1>
			
			<ul class="list-group">
				
				<#if videos??>
					<#list videos as video> 
						<#if video??>
						<li class="list-group-item">
						    <div class="videos">
								<video width="25%" height="400" controls autoplay name="media">
									<source src="${video.videoUrl}" type="video/mp4">
								</video>
								<div style="width:25%;">
								<span class="glyphicon glyphicon-heart-empty">${video.digg_count}</span>
								<span class="datas glyphicon glyphicon-share">${video.share_count}</span>
								<span class="datas glyphicon glyphicon-comment">${video.comment_count}/span>
								<span class="datas glyphicon glyphicon-expand">${video.forward_count}</span>
								<p>${video.desc}</p>
								<p style="float: right;margin-top: -15px">${video.nickname}</p>
								</div>
							</div>
					    </li>
					    </#if>>
					</#list>
				</#if> 
			</ul>
		
	</div>


		<!-- 背景JS 
<script src="${request.contextPath}/js/background/particles.js"></script>
<script src="${request.contextPath}/js/background/app.js"></script> --!>
	</body>

</html>

