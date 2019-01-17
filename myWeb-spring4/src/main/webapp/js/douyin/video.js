$(document).ready(function() {
	
	// 文档高度
	function getDocumentTop() {
		 var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
		 
		if(document.body) {
			 bodyScrollTop = document.body.scrollTop;
		 }

		 if(document.documentElement) {
		 	documentScrollTop = document.documentElement.scrollTop;
		 }

		 scrollTop = (bodyScrollTop- documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;

		 return scrollTop;
	}
	

	// 滚动条高度
	function getScrollHeight() {
		var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;

		if(document.body) {
			bodyScrollHeight = document.body.scrollHeight;
		}

		if(document.documentElement) {
			documentScrollHeight = document.documentElement.scrollHeight;
		}

		scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
		return scrollHeight;
	}

	// 可视窗高度
	function getWindowHeight() {
		var windowHeight = 0;
		if(document.compatMode == "CSS1Compat") {
			windowHeight = document.documentElement.clientHeight;
		} else {
			windowHeight = document.body.clientHeight;
		}
		return windowHeight;
	}
	var date = new Date();
	window.onscroll = function() {
		
		if(getScrollHeight() == getWindowHeight() + getDocumentTop()) {
			var now = new Date();
			if(now - date < 3000) {
				return ;
			}
			date = now;
			console.log("bottom");
			$.ajax({
				url : path + "/douyin/getBatchVideos",
				type : "get",
				success : function(data) {
					if(data.length == 0) {
						return ;
					}
					var json = eval(data);
					console.log(json);
					for(var i = 0; i < json.length; i++) {
						var digg = json[i].digg_count.toString().length > 5 ? (eval(json[i].digg_count)/10000).toFixed(2) + "w" : json[i].digg_count;
						var share = json[i].share_count.toString().length > 5 ? (eval(json[i].share_count)/10000).toFixed(2) + "w" : json[i].share_count;
						var comment = json[i].comment_count.toString().length > 5 ? (eval(json[i].comment_count)/10000).toFixed(2) + "w" : json[i].comment_count;
						var forward = json[i].forward_count.toString().length > 5 ? (eval(json[i].forward_count)/10000).toFixed(2) + "w" : json[i].forward_count;
						
						var li = $("<li class='list-group-item'></li>");
						var div = $("<div class='videos'></div>");
						var video = $("<video width='25%' height='400' controls name='media' preload='none'  poster='" + json[i].poster + "'>" 
						+ "<source type='video/mp4' src='" + path + "/douyin/getVideo/" + json[i].aweme_id + "'/>"		
						+"</video>");
						var div2 = $("<div style='width:25%;'></div>");
						var span1 = $("<span class='glyphicon glyphicon-heart' style='color: red'>" + digg + "</span>");
						var span2 = $("<span class='datass glyphicon glyphicon-share' style='color: limegreen'>" + share + "</span>");
						var span3 = $("<span class='datass glyphicon glyphicon-comment' style='color: #5bc0de'>" + comment + "</span>");
						var span4 = $("<span class='datass glyphicon glyphicon-expand' style='color: khaki'>" + forward + "</span>");
						var p1 = $("<p style='margin-top: 4px'>"+ json[i].desc +"</p>"); 
						var p2 = $("<p style='float: right;margin-top: -12px'>" + json[i].nickname + "</p>");
						
						div2.append(span1, span2, span3, span4, p1, p2);
						div.append(video, div2);
						li.append(div);
						$('.list-group').append(li);
						
					}
					
				}, 
				error: function (response, ajaxOptions, thrownError) {
                    console.log(response);
                    console.log(ajaxOptions);
                    console.log(thrownError);
                    sweetAlert("系统错误");
                }
			});
		}
	}
	
//	function unScroll() {
//	    var top = $(document).scrollTop();
//	    $(document).on('scroll.unable',function (e) {
//	        $(document).scrollTop(top);
//	    })
//	}
//	
//	function loadingEffect() {
//	    $(document).ajaxStart(function () {
//	        unScroll();
//	    }).ajaxStop(function () {
//	    	$(document).unbind("scroll.unable");
//	        
//	    });
//	}
//	loadingEffect();
})