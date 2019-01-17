<!DOCTYPE html>
<html>
  <head>
    <title>收藏夾</title>
	
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
    
     <!-- 下拉框样式 -->
  	<link rel="stylesheet" href=" ${request.contextPath}/styles/search.css?v1.15"/>
    
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
    
     

    
    <!-- 收藏夹js -->
   <script src="${request.contextPath }/js/favorite/favorites.js?v1.44"></script>
    
  </head>
  
  <body >
  <!-- 表单被嵌套在背景中 -->
	<div id="particles-js">
		<nav class="navbar navbar-default navbar-static-top" role="navigation">
		    <div class="container-fluid">
		    <div class="navbar-header">
		        <a class="navbar-brand" href="#">echoWeb</a>
		    </div>
		    <div>
		        <ul class="nav navbar-nav">
		        
		        	 <li class="dropdown">
		            	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
		                 慎思明辨    <b class="caret"></b>
		                </a>
		                <ul class="dropdown-menu">
		                    <li><a href="${request.contextPath}/douyin/videos">TikTok</a></li>
		                </ul>
		            </li>
		        
		            <li class="dropdown">
		            <a href="#" class="dropdown-toggle" data-toggle="dropdown">德才兼備<b class="caret"></b></a> 
		             <ul class="dropdown-menu">
		                    <li><a  href="${request.contextPath}/user/welcome.do">主頁</a></li>
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
		            <li class="active"><a href="${request.contextPath}/go/favorite/all">領袖氣質</a></li>
		        </ul>
		
		        <div>
		        <form class="navbar-form navbar-left" role="search" style="width: 50%;" onsubmit="return false;">
		        <#if (user.userId)??>
				 <input type="hidden" id="userId" value="${user.userId}"/>
				    <#else>
				   <script language="javascript" type="text/javascript"> 
						 $(function () {location.reload();})
					</script>
				</#if>
		           
		             <div class="form-group gover_search_form clearfix" style="width: 70%; margin-left: 10%">
		                <input type="text" class="form-control" placeholder="Search" id="condition" style="width: 100%;" autocomplete="off"> 
		            </div>
		            <button type="button" id="search" class="btn btn-default">搜索</button>
		            <div class="search_suggest" id="suggest">
		                    <ul class="suggest" id="suggestUl">
								
		                    </ul>
		            </div>
		        </form>
		    </div>
		
		        <p style="padding-right: 37px;" class="navbar-text navbar-right"><a href="${request.contextPath}/user/logout.do">註銷</a></p>
		        <#if (user)??>
				 <p style="padding-right: 37px;" class="navbar-text navbar-right">${user.userNickname}</p>
				    <#else>
				   
				</#if>
		    </div>
		    </div>
		    </nav>   
		    
		      <a href="${request.contextPath}/go/favorite/add" id="addWe" class="glyphicon glyphicon-plus-sign btn-lg"></a>

		    <div style="width:70%; margin-right: auto; margin-left: auto;">
		       <table class="table table-light table-hover" >
				    <thead>
				      <tr class="table-info text-dark" style="background-color: #bcc8ee">
				        <th>網站別名</th>
				        <th>網址</th>
				        <th>操作</th>
				      </tr>
				    </thead>
				    <tbody>
				    </tbody>
		
				</table>
		        <div id="pageInfo">
		        	<div style="float: right;" id="paging">
		        	<a id="prev" class="myWebsites glyphicon glyphicon-chevron-left"></a>
		        	<span id='page'></span>
		        	<a id="next" class="myWebsites glyphicon glyphicon-chevron-right"></a>
		        	</div>
		        </div>
		    </div>
		</div>
			
	</div>


		<!-- 背景JS -->
<script src="${request.contextPath}/js/background/particles.js"></script>
<script src="${request.contextPath}/js/background/app.js"></script>
	</body>

</html>

