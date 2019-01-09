$(function(){
	 $("#captcha").click(function () {
        $(this).attr("src", path + "/user/getValidCode?time=" + new Date().getTime());
    });
	 // 截取回车
	 function sliceStr(str){
		    var temp = str.split(/[\n,]/g);
		        for(var i =0;i<temp.length;i++){
		        if(temp[i] == ""){
		            temp.splice(i, 1);
		            //删除数组索引位置应保持不变
		            i--;
		        }
		    }
		   	return temp;
		}
	 
	 $('form').bootstrapValidator({
	        submitHandler: function (validator, form, submitButton) {
	        	var sendObj = $("#login").serialize();
	            //ajax请求
	            $.ajax({
	                url: path + "/user/login.do",
	                type: "post",
	                data: sendObj,
	                success: function (data) { 
	                    var responseText = data;
	                    console.log(responseText);
	                    if (responseText == "captcha") {
	                        sweetAlert("验证码错误");
	                    } else if (responseText == "password") {
	                        sweetAlert("用户名或密码错误");
	                    } else if (responseText == "noExist") {
	                        sweetAlert("账号不存在或未激活");
	                    } else {
	                        // 登錄通過，返回上一鏈接
	                    	// responseText 為  /myWeb/.....
	                    	// second '/' index
	                        window.location.href = path + responseText.substring(responseText.indexOf("/",responseText.indexOf("/")+1));
	                    }

	                    //只要错误了，就设置验证码为空，同时更新验证码
	                    $("#inputCaptcha").val("");
	                    $("#captcha").attr("src", path + "/user/getValidCode?time=" + new Date().getTime());
	                    $("#submitButton").removeAttr("disabled");

	                },
	                error: function (response, ajaxOptions, thrownError) {
	                    console.log(response);
	                    console.log(ajaxOptions);
	                    console.log(thrownError);
	                    sweetAlert("系统错误");
	                }
	            });

	            //validator.defaultSubmit();
	        }
	    });
})