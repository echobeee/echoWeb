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
        	userId: {
                 message: '用户id验证失败',
                 validators: {
                     notEmpty: {
                         message: '用户账号不能为空'
                     },
                     stringLength: {
                         min: 6,
                         max: 12,
                         message: '用户账号长度必须在6到12位之间'
                     },
                     regexp: {
                         regexp: /^[a-zA-Z0-9_]+$/,
                         message: '用户名只能包含大小写英文字母、数字和下划线'
                     }
                 }
             },
            userEmail: {
                message: '邮箱验证失败',
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    emailAddress: {
                        message: '邮箱地址格式有误' 
                    }
                }
            },
            userPassword: {
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
                }
            },
            confirmPassword: {
            	 message: '密码验证失败',
                 validators: {
                     notEmpty: {
                         message: '确认密码不能为空'
                     },
                     identical: {
                         field: 'userPassword',
                         message: '两次密码不同请重新输入'
                     }
                 }
            },
            userNickname: {
                message: '昵称验证失败',
                validators: {
                    notEmpty: {
                        message: '昵称不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 12,
                        message: '昵称长度必须在3到12位之间'
                    },
                }
            },

        },

        //这个方法只有0.45版本才能有效。
        //邮箱去做后台唯一性校验
        submitHandler: function (validator, form, submitButton) {
            var $email = $("#userEmail").val();
            $.ajax({
                url: path + "/user/checkReg",
                type: "post",
                async: false,
                data: {
                    "userId": $("#userId").val(),
                    "userEmail" : $email 
                },
                success: function (responseText) {
                    if (responseText == "Registable") {
                    	
                        validator.defaultSubmit();
                        sweetAlert("注册成功!请到您指定的邮箱完成激活");
                        window.location.href = path;
                    } else if(responseText == "UserID") {
                        sweetAlert("该用户账号已被使用");
                    } else if(responseText == "email") {
                    	sweetAlert("您的邮箱已注册过了");
                    } else {
                    	sweetAlert("系统错误！");
                    }
                },
                error: function () {
                    sweetAlert("系统错误！");
                }
            });
        }
    });
    
});

/*表单验证以及提交------------------------end*/
