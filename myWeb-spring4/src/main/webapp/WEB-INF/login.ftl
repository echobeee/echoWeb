<!DOCTYPE html>
<html>
  <head>
    <title>d登录页面</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

  </head>
  
  <body>
  <form action="${request.contextPath}/user/register.do" method="post">
    帐号:<input type="text" name="username" /><br>
    密码：<input type="password" name="password" /><br>
  <input type="checkbox" value="rememberMe"><br>
    <input type="submit" name="注册"/>
    </form>
  </body>
</html>
