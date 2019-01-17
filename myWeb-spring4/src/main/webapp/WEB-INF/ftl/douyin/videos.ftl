<!DOCTYPE html>
<html>
  <head>
    <title>抖音</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Register">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!-- icon -->
    <link rel = "Shortcut Icon" href="${request.contextPath}/images/echoWeb.ico">
    
    <!--Jquery-->
    <script src="${request.contextPath}/js/jquery-3.2.1.min.js"></script>
    
    <!-- 背景样式 -->
   <!-- <link rel="stylesheet" media="screen" href="${request.contextPath}/styles/background.css"> -->
    
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
    
    <link href="${request.contextPath}/styles/douyin/loading.css?v1.4" rel="stylesheet">
     
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
    
    <script src="${request.contextPath}/js/douyin/video.js?v1.15"></script>
    
    <style type="text/css">
	body {
		background-color: black;
		color: white;
		
	}
	
	h1 {
		margin-bottom: 15px;
		left: 44%;
		position: relative;
		color: #333;
	}
	
	.videos {
		position: relative;
		left: 40%;
	}

	.datas {
		margin-left: 2%;
	}
	.datass {
		margin-left: 10%;
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

		            <li class="active"><a href="">慎思明辨</a></li>
		            
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
		    
		    <h1>TikTok視頻</h1>
			
			<ul class="list-group">
				
				<#if videos??>
				<#assign keys = videos?values>
					<#list keys as video> 
						<#if video??>
						<li class="list-group-item">
						    <div class="videos">
								<video width="25%" height="400" controls name="media" preload="none" poster="${video.poster}">
									<source type="video/mp4" src="${request.contextPath}/douyin/getVideo/${video.aweme_id}"/>
									您的浏览器不支持 video 标签。
								</video>
								<div style="width:25%;">
								<span class="glyphicon glyphicon-heart" style="color: red">
								<#if video.digg_count?length gt 5 >
									${video.digg_count?number / 10000}w
								<#else>
									${video.digg_count}	
								</#if>
								</span>
								<span class="datas glyphicon glyphicon-share" style="color: limegreen">
								<#if video.share_count?length gt 5 >
									${video.share_count?number / 10000}w
								<#else>
									${video.share_count}	
								</#if>
								</span>
								<span class="datas glyphicon glyphicon-comment" style="color: #5bc0de">
								<#if video.comment_count?length gt 5 >
									${video.comment_count?number / 10000}w
								<#else>
									${video.comment_count}	
								</#if>
								</span>
								<span class="datas glyphicon glyphicon-expand" style="color: khaki">
								<#if video.forward_count?length gt 5 >
									${video.forward_count?number / 10000}w
								<#else>
									${video.forward_count}	
								</#if>
								</span>
								<p style="margin-top: 4px">${video.desc}</p>
								<p style="float: right;margin-top: -12px">${video.nickname}</p>
								</div>
							</div>
					    </li>
					    </#if>
					</#list>
				</#if> 
			</ul>
		
	</div>
	
	<div id="fountainG">
					<div style="position: relative; top:-2px; color:#0099cc">加载中</div>
                    <div id="fountainG_1" class="fountainG">
                    </div>
                    <div id="fountainG_2" class="fountainG">
                    </div>
                    <div id="fountainG_3" class="fountainG">
                    </div>
                    <div id="fountainG_4" class="fountainG">
                    </div>
                    <div id="fountainG_5" class="fountainG">
                    </div>
                    <div id="fountainG_6" class="fountainG">
                    </div>
                    <div id="fountainG_7" class="fountainG">
                    </div>
                    <div id="fountainG_8" class="fountainG">
                    </div>
	</div>

	</body>

</html>

