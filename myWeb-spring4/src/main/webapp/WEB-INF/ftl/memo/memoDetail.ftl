<!DOCTYPE html>
<html>
  <head>
    <title>备忘录</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
 <!--Jquery-->
    <script src="${request.contextPath}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		function getQueryString(name) {
		    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		    var reg_rewrite = new RegExp("(^|/)" + name + "/([^/]*)(/|$)", "i");
		    var r = window.location.search.substr(1).match(reg);
		    var q = window.location.pathname.substr(1).match(reg_rewrite);
		    if(r != null){
		        return unescape(r[2]);
		    }else if(q != null){
		        return unescape(q[2]);
		    }else{
		        return null;
		    }
		}
		
		var id ;
		
		$.ajax({
			type : "post",
			data : {"res":getQueryString("res")},
			url : "${request.contextPath}/memo/getMemo",
			dataType : "json",
			success : function(data){
				var json = eval(data);
				
				$('#content').html(json.memoContent);
				$('#editTime').html(json.editTime);
				$('#sendTime').html(json.sendTime);
				id = json.memoId;
				
			}
		})
		$('#change').click(function(){
			$("#sendTime").hide();
			$(this).attr("type","datetime-local");
			$(this).attr("id","changeTime");
			$("#button").attr("type","button");
			$("#memo").attr("type","text");
			$(this).val($("#sendTime").html().replace(" ", "T"));
			$("#memo").val($("#content").html());
			$("#content").hide();
		});
		$("#button").click(function () {
			var sendObj = {
							"memoId" : id,
							"sendTime" : $("#changeTime").val().replace("T", " "),
							"memoContent" : $("#memo").val()
						};
			
			$.ajax({
				type : "post",
				data : sendObj,
				url : "${request.contextPath}/memo/updateMemo.do",
				dataType : "json",
				success:function(data){
					console.log(data);
					window.location.reload();
				}
			})
		})
	})
</script>

  </head>
  
  <body>
   
  备忘录：<span id="content"></span><input type="hidden" id="memo" /><br>
  编辑时间：<span id="editTime"></span><br>
 <div> 提醒时间：<span id="sendTime"> </div><input type="button" id="change" value="修改？"/> </span>
  <input type="hidden" id="button" value="确认修改">
  </body>
</html>
