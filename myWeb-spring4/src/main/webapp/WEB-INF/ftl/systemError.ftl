<!DOCTYPE html>
<html>
  <head>
    <title>echoWeb</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
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
  
  <body>
  <div class="container">
  	<h1>服务器好像出现了点状况...</h1>
    <img src="${request.contextPath}/images/Oops.jpg"/>
    </div>
  </body>
</html>
