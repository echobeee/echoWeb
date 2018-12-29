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
        	
            newPassword: {
                message: '密码验证失败',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '密码长度必须在6到16位之间'
                    },
                   remote : {
                	   message: '新密码不能与旧密码相同',
                	   url: path + '/user/checkPassword',
//                	   data:{}
                	   delay: 3000
                   }
                }
            },
            confirmPassword: {
            	 message: '密码验证失败',
                 validators: {
                     notEmpty: {
                         message: '密码不能为空'
                     },
                     identical: {
                         field: 'newPassword',
                         message: '两次密码不同请重新输入'
                     }
                 }
            },

        },

        //这个方法只有0.45版本才能有效。
        //邮箱去做后台唯一性校验
        submitHandler: function (validator, form, submitButton) {
        	console.log('???');
        	
        	swal({title:"密码修改成功",
		        text:"",
		        type:"success"},
		        function(){
		        	validator.defaultSubmit()
		        	}
		    );
        	
        }
    });
    
});


