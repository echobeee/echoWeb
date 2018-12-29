$(document).ready(function() {
        
	// initial
	$.ajax({
		  	type: "POST",
		     url: path + "/favorites/myWebsites.do",
		     data :  {"userId" : $('#userId').val() },
		     dataType : "json",
		     success :function(data) {
	       // 获取到返回的
	         console.log(data);
	        setTable(data);
	     }
	});
	
	function setTable(data) {
		 $('tbody').empty();
         var json = eval(data);
         var currentPage = json.currentPage;
         var pages = json.pages;
         var records = json.records;
         var websites = json.websiteList;
         
         if(websites == undefined) {
        	 var prev = $("#prev");
        	 var next = $("#next");
        	 websites = json;
        	 prev.hide();
        	 next.hide();
        	 var html = $('#pageInfo').children().clone(); 
        	 $('#pageInfo').html('共'+ websites.length +'条记录').append(html);
         }
         if(currentPage != undefined) {
        	 var html = $('#pageInfo').children().clone(); 
        	 if(records == 0){
        		 $('#pageInfo').html('共0条记录，共0页').append(html);
        	 } else {
        		 $('#pageInfo').html('共'+ records +'条记录，共'+ pages +'页，第'+ currentPage +'页').append(html); 
        	 }
        	 $('#pageInfo').attr('records',records);
        	 
        	 var prev = $("#prev");
        	 var next = $("#next");
        	 
        	 next.show();
        	 prev.show();
        	 next.attr('page',eval(currentPage)+1);
        	 prev.attr('page',eval(currentPage)-1);
        	if(currentPage == pages || pages == 0) {
        		next.hide();
        	}
        	
        	if(currentPage == 1) {
        		prev.hide();
        	} 
        	
        	$('#page').text(currentPage);
        	
         }
    	
         
         
         for(var i = 0; i <websites.length; i++ ){
        	 var tr = $("<tr fid='" + websites[i].id + "'></tr>");
        	 var reg = new RegExp("\"","g");
        	 
        	 var td = $("<td></td>").html("<span>" + websites[i].webSiteName.replace(reg,'\'') + "</span>" + // 文字
        	 		"<input type='text'  style='display: none; color: black' value=" + websites[i].webSiteName.replace(reg,'\'') + " />"); // 输入框
        	 
        	 var td2 = $('<td></td>').html("<a href='"+ websites[i].webSiteUrl.replace(reg,'\'') +"' target='_blank'>" +websites[i].webSiteUrl.replace(reg,'\'') + "</a>" +
        	 		"<input type='text' style='display: none; color: black; width:80%' value="+ websites[i].webSiteUrl.replace(reg,'\'') + " />");
        	 
        	 var td3 = $('<td></td>');
        	 var button1 = $("<span class='glyphicon glyphicon-edit'><a>编辑</a></span><span>|<span>");	
             var button2 = $("<span class='glyphicon glyphicon-trash'><a>删除</a></span>");
             var button3 = $("<span class='glyphicon glyphicon-ok' style='display: none'><a>确认</a></span>"); 	
             
        	 td3.append(button1, button2, button3);
        	 tr.append(td, td2,td3);
        	 $('tbody').append(tr);
        	 
         }
	}
	
	
  //------------------------------- 搜索  ----------------------------------------//
          // 按下回车键进行搜索
          $('#condition').keydown(function() {
            var toSearch = $('#condition').val();
            if(event.keyCode == 13 && $.trim(toSearch).length > 0) {
              goSearch();
            }
          });

          // 点击按钮进行搜索
          $("#search").click(function() {
            var toSearch = $('#condition').val();
            if($.trim(toSearch).length > 0) {
              goSearch();
            }
          });

          // 搜索函数
          function goSearch() {
            var toSearch = $('#condition').val();
            var sendObj = {
              "userId" : $('#userId').val(),
              "content" : $('#condition').val()
            }
            $.ajax({
                type: "POST",
                url: path + "/favorites/search.do",
                data : sendObj,
                dataType : "json",
                success :function(data) {
                  // 获取到返回的数据
                  console.log(data);
                  /**
                   * 放置数据到页面中
                   */
                  setTable(data);
                },
                error: function () {
                    sweetAlert("系统错误了，请重新搜索试试看~");
                }
            });
          }
      
 //------------------------------ 自动补全  -------------------------------------------//
          
          // 自动补全js
          function autoComplete() {
            var input = $('#condition');
            var suggest = $('#suggestUl');
            
            var key = "";// 装载用户前一次输入的值，用于判断用户有无输入
          
            var setup = function() {
//              input.bind('keyup', sendPrefix);
              // 当按键松下时发送此时的输入前缀
              input.keyup(showCompletion);
              input.off('keyup').on('keyup', showCompletion); // 绑定事件之前解绑事件
              //input.bind('blur', )
              input.blur(function() {   
                suggest.hide();
              })
            } // end setup
          setup();  
            // 键盘上下选择
            function showCompletion(event) {
                if(suggest.css('display') == 'block' && (event.keyCode == 38 || event.keyCode == 40)) {// 上箭头或者下箭头
                      var current = suggest.find('li.hover');
                      if(event.keyCode == 38) { // 向上
                        if(current.length > 0) {  // 有正在被选中的
                          var prevLi = current.removeClass('hover').prev();
    
                          if( prevLi.length > 0 ){ // 判断位置是否在第一位 有无前兄弟结点
                            prevLi.addClass('hover');
                            input.val(prevLi.html());
                          } else { // 若在第一位，则显示原输入文本
                            input.val(key);
                          }
                        } else {
                            var last = suggest.find('li:last');// 上移到最后一位
                                last.addClass('hover');
                                input.val(last.html());
                          }   
                      } else if(event.keyCode == 40) { // 向下按键
                        if(current.length > 0) { // 有正在被选中的
                          var nextLi = current.removeClass('hover').next();
    
                          if( nextLi.length > 0 ){ // 判断位置是否在最后一位 有无前兄弟结点
                            nextLi.addClass('hover');
                            input.val(nextLi.html());
                          } else { // 若在最后一位，则显示原输入文本
                            input.val(key);
                          }
                        } else {
                            var first = suggest.find('li:first');// 上移到最后一位
                                first.addClass('hover');
                                input.val(first.html());
                          }
                      }
                    event.stopPropagation();
                      }else { // 用户正在输入字符
                          var inputText = $.trim(input.val());
                          if(inputText == '' || inputText == key) {
                            return;
                          }
                       
                          getData();
                          key = inputText;
                    }
              } // end showCompletion

              // 获取自动补全的数据
            function getData() {
              var sendObj = {
                "toComplete" : $("#condition").val(),
                  "userId" : $("#userId").val()
                };
              $.ajax({
                type: "POST",
                url: path + "/favorites/autoComplete.do",
                data : sendObj,
                dataType : "json",
                success :function(data) {
                  // 获取到返回的自动补全列表
                  console.log(data);
                  setComplete(data);
                }
              }); 
              
            }// end getData

            function setComplete(data) {
                if(data.length <= 0) {
                  suggest.hide();
                  return;
                }

                //往搜索框下拉建议显示栏中添加条目并显示
                var li;
                var fragment = document.createDocumentFragment();
                
                suggest.empty();
                
                for (var i = 0; i < data.length; i++) {
                  li = document.createElement('li');
                  li.innerText = data[i];
                  fragment.appendChild(li);
                }
                // 把自动补全添加到dom中
                var ul = $('.suggest');
                ul[0].appendChild(fragment);
                suggest.show();

                suggest.find('li').hover(function() {
                  suggest.find('li').removeClass('hover'); // 去除所有的li的hover样式
                  $(this).addClass('hover'); // 为当前hover到的添加样式
                 input.val(this.innerHTML);
                }, function(){
                  $(this).removeClass('hover');// hover完后去除样式
                }).click(function() {
                  input.val(this.innerHTML);
                  suggest.hide();
                })

                setup();

            }// end setComplete

          } // end autoComplete
          var suggest1 = new autoComplete();
          
          
 //------------------------------------添加网站---------------------------------------------//

          $('#addWeb').click(function() {
        	 
        	  
              var name = $('#webSiteName').val();
              var url = $('#webSiteUrl').val();
              if($.trim(name).length > 0 && $.trim(url).length > 0) {
                addWebsite();
              } else {
                sweetAlert("网站名/网站地址不能为空！");
              }
              
          });

          function addWebsite() {
            var sendObj = {
              "userId" : $('#userId').val(),
              "webSiteName" : $('#webSiteName').val(),
              "webSiteUrl" : $('#webSiteUrl').val()
            }
            $.ajax({
                type: "POST",
                url: path + "/favorites/addWebsite.do",
                data : sendObj,
                dataType : "json",
                success :function(data) {
                  // 获取到返回的自动补全列表
                    console.log(data);
                    if(data == "success") { // 创建成功

                    } else if(data == "existed"){ // 别名已存在

                    } else if(data == "fail"){ // 创建失败

                    }
                }
            })
          }

//---------------------------------删除网址-> 直接由controller跳转-----------------------------------//
          $(document).on("click", '.glyphicon-trash', function() {
        	  var thisOne = $(this);
        	  console.log(thisOne.parent().parent());
        	 swal({
        		 title: '你確定要刪除嗎?',
                 text: '刪了就沒了喔!',
                 type: 'warning',
                 showCancelButton: true,
                 confirmButtonColor: "#DD6B55",
                 confirmButtonText: '刪了吧!',
                 cancelButtonText: '按錯了..',
        	 	},function(isConfirm){
        	 		if (isConfirm) {
        	 			 $.ajax({
        	                 type: "POST",
        	                 url: path + "/favorites/delete.do",
        	                 data : {"id" : thisOne.parent().parent().attr('fid')},
        	                 success :function(data) {
        	                  if(data == "success") {
        	                	  swal({
        	                		  title : "删除成功!",
        	                		  text: "",
        	                		  type : "success"},function(){
        	                			  // 判断是不是少了一页
        	                			  var currentrecord = eval($('#pageInfo').attr('records'))-1;
        	                			  if(currentrecord % 10 == 0 && currentrecord != 10 && currentrecord != 0){
        	                				  toMyWebs(eval($('#prev').attr('page')));
        	                			  } else {
        	                				  toMyWebs(eval($('#prev').attr('page'))+1);
        	                			  }
        	                		  }
        	                	 );
        	                  } else {
        	                	  swal({
        	                		  title : "删除失败",
        	                		  text: "可能服务器出现了问题，稍后再试试吧...",
        	                		  type : "error"},function(){});
        	                  }
        	                 }
        	               }); 
        	 		}
        	 	});
        	 
         })
         
         function toMyWebs(page){
        	  var sendObj = {
                      "userId" : $('#userId').val(),
                      "page" : page
                    };
		        console.log(sendObj);
		        $.ajax({
		      	  	type: "POST",
		              url: path + "/favorites/myWebsites.do",
		              data : sendObj,
		              dataType : "json",
		              success :function(data) {
		                // 获取到返回的
		                  console.log(data);
//		                  if(data == )
		                 setTable(data);
		                  
		              },
			          error: function (response, ajaxOptions, thrownError) {
			            console.log(response);
			            console.log(ajaxOptions);
			            console.log(thrownError);
			            
			        }  
		        });
          }
         

//---------------------------------------我的收藏夹-----------------------------------------------//
       // 分页的点击
          $(document).on("click", '.myWebsites', function() {
        	toMyWebs($(this).attr('page'));
         
        })
        
//-------------------------------編輯網站--------------------------------------//
        $(document).on("click", '.glyphicon-edit', function() {
        	// td 
        	// td a
        	// td <this>
        	
        	// 点击之后变成了文本框
        	
        	var a = $(this).parent().prev().find('a')[0];
        	var input = a.nextSibling;
        	a.style.display = 'none';
        	input.style.display = 'block';
        	
        //	var td = $(this).parent().prev().prev();
        	var span = $(this).parent().prev().prev().find('span')[0];
        	var input2 = span.nextSibling;
        	span.style.display = 'none';
        	input2.style.display = 'block';
        	
        	var button1 = $(this);
        	var button2 = $(this).next();
        	var button3 = $(this).next().next().next();
        	button1.hide();
        	button2.hide();
        	button2.next().hide();
        	button3.show();
        	
         
        })
        
        $(document).on("click", '.glyphicon-ok',function() {
        	var input1 = $(this).parent().prev().find('input')[0];
        	var input2 = $(this).parent().prev().prev().find('input')[0];
        	var id = $(this).parent().parent()[0];
        	var sendObj = {
        			"id" : id.getAttribute('fid'),
        			"webSiteName" : input2.value,
        			"webSiteUrl" : input1.value,
        			"userId" : $('#userId').val()
        	}
        	console.log(sendObj);
        	$.ajax({
        		url : path + "/favorites/update.do",
        		type : "post",
        		data : sendObj,
        		success : function(data) {
        			if(data == 'OK'){
        				swal({
        					  title : "修改成功!",
	                		  text: "",
	                		  type : "success"
            			},function(){
            				toMyWebs(eval($('#prev').attr('page'))+1);
            			});
        			} else if(data == 'existed') {
        				sweetAlert('別名已存在');
        			} else {
        				swal({
      					  	  title : "修改失敗..",
	                		  text: "可能服务器出现了问题，稍后再试试吧..",
	                		  type : "error"
	          			},function(){
	          				
	          			});
        			}
        			
        			
        		}
        	});
        })

})
     