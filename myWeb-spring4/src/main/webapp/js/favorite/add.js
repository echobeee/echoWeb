/*表单验证以及提交------------------------start*/
$(function () {
    //bootstrap校验
    $('form').bootstrapValidator(
    {
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	
        	webSiteName: {
                message: '密码验证失败',
                validators: {
                    notEmpty: {
                        message: '別名不能為空'
                    }
                  
                }
            },
            webSiteUrl: {
            	 message: '密码验证失败',
                 validators: {
                     notEmpty: {
                         message: 'url不能為空'
                     }
                 }
            }

        },

        //这个方法只有0.45版本才能有效。
        submitHandler: function (validator, form) {
        	console.log('go');
        	$.ajax({
        		url : path + "/favorites/addWebsite.do",
        		data : {
        			"userId" : $('#userId').val(),
        			"webSiteName" : $('#webSiteName').val(),
        			"webSiteUrl" : $('#webSiteUrl').val()
        		},
        		type : "post",
        		success : function(data) {
        			if(data == 'success') {
        				swal({title:"網站添加成功!",
        			        text:"",
        			        type:"success"},
        			        function(){
        			        	  window.location.href = path + "/go/favorite/all";
        			        	}
        			    );
        			} else if(data == 'existed') {
        				swal({title:"別名已存在!",
        			        text:"換一個名字吧",
        			        type:"error"},
        			        function(){
        			        	});
        			} else {
        				swal({title:"添加失敗",
        			        text:"服務器可能出現了問題,請稍後再試試吧..",
        			        type:"error"},
        			        function(){
        			        	});
        			}
        		}
        	});// end ajax
        	
        }
    });
});


