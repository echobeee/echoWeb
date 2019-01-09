<!doctype html>
<html lang="zh">
<head>
	 <title>備忘錄</title>

	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- icon -->
    <link rel = "Shortcut Icon" href="${request.contextPath}/images/echoWeb.ico">
	
	<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link href="${request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
	
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/styles/memo/default.css">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/styles/memo/style.css?v1.1">
	
	<!--[if IE]>
		<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]-->
	
	 <!--弹出框-->
    <script src="${request.contextPath}/js/sweetalert.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/styles/sweetalert.css">
	
	<script src="${request.contextPath}/js/jquery-3.2.1.min.js" type="text/javascript"></script>
	
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
	

</head>
<body>

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
		                    <li><a href="${request.contextPath}/go/douyin/videos">TikTok</a></li>
		                </ul>
		            </li>
        
            <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">德才兼備<b class="caret"></b></a> 
             <ul class="dropdown-menu">
                    <li><a  href="${request.contextPath }/user/welcome.do">主頁</a></li>
                </ul>
            
            </li>
            <li class="active"><a href="#">家國情懷</a></li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                	領袖氣質 <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${request.contextPath}/go/favorite/all">收藏夾</a></li>
                </ul>
            </li>
            
        </ul>
        <p style="padding-right: 37px;" class="navbar-text navbar-right"><a href="${request.contextPath}/user/logout.do">註銷</a></p>
        <#if (user)??>
				 <p style="padding-right: 37px;" class="navbar-text navbar-right">${user.userNickname}</p>
				    <#else>
				   
				</#if>
    </div>
    </div>
    </nav>
	
	<div class="container">
		<header class="htmleaf-header">
			<h1>echoWeb 備忘錄</h1>
		</header>
			<div class="calendar-container">
				<div class="calendar">	
					<header>
						<h2 class="month"></h2>
						<a class="btn-prev fontawesome-angle-left" ></a>
						<a class="btn-next fontawesome-angle-right"></a>
					</header>
					<table>
						<thead>
							<tr><td>壹</td><td>貳</td><td>叁</td><td>肆</td><td>伍</td><td>陸</td><td>柒</td></tr>
						</thead>
						<tbody>
							<tr>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
							</tr>
							<tr>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
							</tr >
							<tr>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
							</tr>
							<tr>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
							</tr>
							<tr>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
							</tr>
							<tr>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
								<td date-month="12" ></td>
							</tr>
						</tbody>
					</table>
					<div class="list">
						
					</div>
				</div>
			</div>
		</div>


	<!-- 模态框（Modal） -->
<div class="modal fade" id="addMmeoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 class="modal-title text-danger" id="myModalLabel">添加备忘录</h3>
            </div>
            <div class="modal-body">
            	 <input type="hidden" id="userId" value="${user.userId }"> 
               <input type="hidden" id="memoId" > 
				<div class="form-group form row">
			    <label for="firstname" class="col-sm-2 control-label">备忘录：</label>
			    <div class="col-sm-3">
			      	<textarea type="text" class="form-control" id="memoContent" style="width: 450px;height: 300px" >
			    	</textarea>
			  	</div>
        		</div>
        	</div>
        	<div class="modal-body">
        	<div class="form-group form row">
			    <label for="firstname" class="col-sm-2 control-label">时间：</label>
			    <div class="col-sm-2" style="width:30%">
					<label class="memoTime">1</label>
			      	<input type="time" id="time" class="form-control" style="position: relative;left: 102px;top: -33px;" />  
			    	
			  	</div>
			  </div>
			  <div class="form-group form row">
			    <label for="firstname" class="col-sm-9 control-label text-muted">当备忘录时间到达，echoWeb将会发送邮件提醒你</label>
			    
			  </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="addButton" class="btn  btn-success">添加</button>
                <button type="button" id="modifyButton" class="btn  btn-success">修改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->


	
	<script type="text/javascript" src="${request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	<script src="${request.contextPath}/js/memo/simplecalendar.js?v0.55" type="text/javascript"></script>

<div style="text-align:center;margin:450px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
</html>
