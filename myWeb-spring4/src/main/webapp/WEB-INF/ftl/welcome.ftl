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
    
    <!--获取天气预报-->
    <script>
        $(function () {
            /*先获取位置、再获取天气预报信息*/
            $.ajax({
                url: "http://restapi.amap.com/v3/ip?key=11ddf2f282ee4c39d35a46c3a4dae845",
                type: "get",
                success: function (responseText1) {
                    $.ajax({
                        url: "https://free-api.heweather.com/s6/weather/forecast?key=d66df9e9bec5484da78f88a5bb58d092&location=" + responseText1.city,
                        type: "get",
                        success: function (responseText2) {

                            $(".jumbotron h1").html(responseText1.city + "近三日天气");

                            var jsonObj = responseText2.HeWeather6[0].daily_forecast;
                            for (var i = 0; i < jsonObj.length; i++) {


                                var date = jsonObj[i].date;
                                var weather = jsonObj[i].cond_txt_d;
                                var low = jsonObj[i].tmp_min;
                                var hight = jsonObj[i].tmp_max;

                                $(".jumbotron").append("<p>" + date + "：" + weather + "，温度：" + low + "～" + hight + "℃</p>");

                            }
                        },
                        error: function () {
                            sweetAlert("获取天气失败...")
                        }
                    });

                },
                error: function () {
                    sweetAlert("定位失败...")
                }
            });
        });
    </script>

    <!--天气预报样式-->
    <style>
        .jumbotron {
            padding-top: 115px;
            padding-bottom: 30px;
            margin-bottom: 30px;
            color: #c7c7c7;
            background-color: black;
        }
        .jumbotron h1 {
            color: inherit;
            margin-bottom: inherit;
        }
    </style>
      
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
		            
		            <li class="active"><a href="">德才兼備</a></li>
		            
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
			
			<!--天气预报-->
    <div class="jumbotron text-center" style="position: absolute;width: 100%" id="weather">
        <h1></h1>

    </div>

			
	</div>


		<!-- 背景JS -->
<script src="${request.contextPath}/js/background/particles.js"></script>
<script src="${request.contextPath}/js/background/app.js"></script>
	</body>

</html>

