var calendar = {

    init: function(date) {

        /**
         * Get current date
         */
        var d = date;
        var strDate = d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate();

        /**
         * Get current month and set as '.current-month' in title
         */
        var monthNumber = d.getMonth() + 1;

        function GetMonthName(monthNumber) {
            var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
            return months[monthNumber - 1];
        }



        $('.month').text(GetMonthName(monthNumber));
        $('.month').attr('month',monthNumber-1);
        $('.month').attr('year',d.getFullYear());

        // 判断是不是闰年
        function runNian(_year) {
            if(_year%400 === 0 || (_year%4 === 0 && _year%100 !== 0) ) {
                return true;
            }
            else { 
                return false; 
            }
        }

        //判断某年某月的1号是星期几
        function getFirstDay(_year,_month) {
            var allDay = 0, y = _year-1, i = 1;
            allDay = y + Math.floor(y/4) - Math.floor(y/100) + Math.floor(y/400) + 1;
            for ( ; i<_month; i++) {
                switch (i) {
                    case 1: allDay += 31; break;
                    case 2: 
                        if(runNian(_year)) { allDay += 29; }
                        else { allDay += 28; }
                        break;
                    case 3: allDay += 31; break;
                    case 4: allDay += 30; break;
                    case 5: allDay += 31; break;
                    case 6: allDay += 30; break;
                    case 7: allDay += 31; break;
                    case 8: allDay += 31; break;
                    case 9: allDay += 30; break;
                    case 10: allDay += 31; break;
                    case 11: allDay += 30; break;
                    case 12: allDay += 31; break;
                }
            }
            return allDay%7;
        }

        setCalendar(date);

        
        

        function setCalendar(date) {
            var _year = date.getFullYear(),
                _month = date.getMonth() + 1,
                _day = date.getDate();
                firstDay = getFirstDay(_year, _month),
                monthDay = 0;
                cnt = 0;
            var cD = new Date();
            var cpD = new Date();
            switch(_month) {
                    case 1: monthDay = 31; break;
                    case 2:
                        if(runNian(_year)) { monthDay = 29; }
                        else { monthDay = 28; }
                        break;
                    case 3: monthDay = 31; break;
                    case 4: monthDay = 30; break;
                    case 5: monthDay = 31; break;
                    case 6: monthDay = 30; break;
                    case 7: monthDay = 31; break;
                    case 8: monthDay = 31; break;
                    case 9: monthDay = 30; break;
                    case 10: monthDay = 31; break;
                    case 11: monthDay = 30; break;
                    case 12: monthDay = 31; break;
                }
                cnt = monthDay - 1;

                 $('div').remove('.add-event');
                 $('div').remove('.day-event');

                $('tbody td').each(function(index){
                        $(this).removeAttr("date-day");
                        $(this).removeAttr("class");
                        $(this).text('');
                    if(index+1 >= firstDay){
                        if(cnt >= 0) {
                            var _day  = monthDay-cnt;
                            $(this).attr("date-day",_day);
                            $(this).attr("date-month",_month);
                            $(this).attr("class", "tdd");
                            $(this).text(_day);

                            cpD.setFullYear(_year,_month-1,_day);
                            // 给每个日期加上添加备忘录
                            if(cpD >= cD){
                                var a = $("<a href='#' class='close fontawesome-remove'></a>");
                                var h2 = $("<h2 class='title'></h2>").text("添加備忘錄?");
                                var p = $("<p class='date' style='margin: 10px 0 10px;'></p>").text(_year + "-" + _month + "-" +_day);
                                var label = $("<label><span class='addMemo'>添加!</span></label>");
                                var div = $("<div class='add-event' date-month=\""+_month+ "\" date-day=\""+ _day +"\"></div>");
                                div.append(a,h2,p,label);
                                $('.list').append(div);
                            }

                          
                            cnt --;
                        } 
                    }
                })
        }

        /**
         * Get current day and set as '.current-day'
         */
        var cd = new Date();
        if(cd.getMonth() == d.getMonth() && cd.getFullYear() == d.getFullYear()){
            $('tbody td[date-day="' + d.getDate() + '"]').addClass('current-day');
        }
               
        showMemos();

        
        /**
         * Save & Remove to/from personal list
         */
        $('.save').click(function() {
            if (this.checked) {
                $(this).next().text('Remove from personal list');
                var eventHtml = $(this).closest('.day-event').html();
                var eventMonth = $(this).closest('.day-event').attr('date-month');
                var eventDay = $(this).closest('.day-event').attr('date-day');
                var eventNumber = $(this).closest('.day-event').attr('data-number');
                $('.person-list').append('<div class="day" date-month="' + eventMonth + '" date-day="' + eventDay + '" data-number="' + eventNumber + '" style="display:none;">' + eventHtml + '</div>');
                $('.day[date-month="' + eventMonth + '"][date-day="' + eventDay + '"]').slideDown('fast');
                $('.day').find('.close').remove();
                $('.day').find('.save').removeClass('save').addClass('remove');
                $('.day').find('.remove').next().addClass('hidden-print');
                remove();
                sortlist();
            } else {
                $(this).next().text('Save to personal list');
                var eventMonth = $(this).closest('.day-event').attr('date-month');
                var eventDay = $(this).closest('.day-event').attr('date-day');
                var eventNumber = $(this).closest('.day-event').attr('data-number');
                $('.day[date-month="' + eventMonth + '"][date-day="' + eventDay + '"][data-number="' + eventNumber + '"]').slideUp('slow');
                setTimeout(function() {
                    $('.day[date-month="' + eventMonth + '"][date-day="' + eventDay + '"][data-number="' + eventNumber + '"]').remove();
                }, 1500);
            }
        });

        function remove() {
            $('.remove').click(function() {
                if (this.checked) {
                    $(this).next().text('Remove from personal list');
                    var eventMonth = $(this).closest('.day').attr('date-month');
                    var eventDay = $(this).closest('.day').attr('date-day');
                    var eventNumber = $(this).closest('.day').attr('data-number');
                    $('.day[date-month="' + eventMonth + '"][date-day="' + eventDay + '"][data-number="' + eventNumber + '"]').slideUp('slow');
                    $('.day-event[date-month="' + eventMonth + '"][date-day="' + eventDay + '"][data-number="' + eventNumber + '"]').find('.save').attr('checked', false);
                    $('.day-event[date-month="' + eventMonth + '"][date-day="' + eventDay + '"][data-number="' + eventNumber + '"]').find('span').text('Save to personal list');
                    setTimeout(function() {
                        $('.day[date-month="' + eventMonth + '"][date-day="' + eventDay + '"][data-number="' + eventNumber + '"]').remove();
                    }, 1500);
                }
            });
        }

        /*
         * 添加备忘录
         */
        

        $('.addMemo').click(function() {
        	
            // date title
            $('.memoTime').text($(this).parent().prev().text());
            // content
            $('#memoContent').val('');
            // date
            $('#time').val('');

            // title
            $('#myModalLabel').text('添加備忘錄');
            
            // hide modify button
            $('#modifyButton').hide();
            $('#addButton').show();
                

            $('#addMmeoModal').modal('show');
            
        })

        
        /**
         * 获取某个月份的备忘录
         * @return {[type]} [description]
         */
        function showMemos() {
            var month = eval($('.month')[0].getAttribute('month'))+1;
            if(month >=1 && month <= 9) {
            	month = "0" + month;
            }
            var year = $('.month')[0].getAttribute('year');
            var sendObj = {
                'userId' : $('#userId').val(),
                'date' : year + "-" + month
            }
            $.ajax({
                url : path + "/memo/myMonthMemos",
                type : "post",
                data : sendObj,
                async : false,//加上这个，变成同步调用
                success : function(data){
                    var response = eval(data);
                    console.log(response);
                    for (var i = 0; i < response.length; i++) {
                        // 2012-12-12
                    	
                        var _month = response[i].sendTime.substring(5,7);
                        var _day = response[i].sendTime.substring(8,10);
                        
                        if(eval(_month) < 10){
                        	_month = _month.substring(1,2);
                        } 
                        if(eval(_day) < 10){
                        	_day = _day.substring(1,2);
                        }

                        var a = $("<a href='#' class='close fontawesome-remove'></a>");
                        var h2 = $("<h2 class='title'></h2>").text("備忘錄");
                        var p = $("<p class='date' style='margin: 10px 0 10px;'></p>").text(response[i].sendTime);
                        var pMemo = $("<p></p>").text(response[i].memoContent);
                        var label1 = $("<label><span class='modify' state='"+ response[i].state +"' memoId = '" + response[i].memoId + "'>修改</span><span class='delete' memoId = '" + response[i].memoId + "'>刪除</span></label>");
                        var div = $("<div class='day-event' date-month=\""+_month+ "\" date-day=\""+ _day +"\"></div>");
                        div.append(a,h2,p,pMemo,label1);
                        $('.list').append(div);

                    }
                }
            })
            /**
             * Add '.event' class to all days that has an event
             */
            $('.day-event').each(function(i) {
                var eventMonth = $(this).attr('date-month');
                var eventDay = $(this).attr('date-day');
                $('tbody tr td[date-month="' + eventMonth + '"][date-day="' + eventDay + '"]').addClass('event');
            });
        }

        

        /**
         * Sort personal list
         */
        function sortlist() {
            var personList = $('.person-list');

            personList.find('.day').sort(function(a, b) {
                return +a.getAttribute('date-day') - +b.getAttribute('date-day');
            }).appendTo(personList);
        }

        /**
         * Print button
         */
        $('.print-btn').click(function() {
            window.print();
        });



    },
};

$(document).ready(function() {

     /**
         * Add class '.active' on calendar date
         */
     $('tbody td').on('click', function(e) {
            if ($(this).hasClass('event')) {
                $('tbody td').removeClass('active');
                $(this).addClass('active');
            } else {
                $('tbody td').removeClass('active');
            };
        });

     /**
         * Get current day on click in calendar
         * and find day-event to display
         */
        $('tbody td').on('click', function(e) {
            $('.add-event').slideUp('fast');
            $('.day-event').slideUp('fast');
            var monthEvent = $(this).attr('date-month');
            var dayEvent = $(this).text();
            console.log(monthEvent + " " + dayEvent);

            $('.memoTime').text($('.month').attr('year') + "-" + monthEvent + "-" + dayEvent);
            
            $('.day-event[date-month="' + monthEvent + '"][date-day="' + dayEvent + '"]').slideDown('fast');
            $('.add-event[date-month="' + monthEvent + '"][date-day="' + dayEvent + '"]').slideDown('fast');
        });

        /**
         * Close day-event
         */
        $('.close').on('click', function(e) {
            $(this).parent().slideUp('fast');
        });


    var daty = new Date();
    console.log(daty);
    calendar.init(daty);

    /*
     * previous month
     */
    $('.btn-prev').click(function() {
            var date = new Date();
            date.setFullYear($('.month').attr('year'));
            date.setMonth($('.month').attr('month')-1);
            
            console.log(date);
            calendar.init(date);
        })

    /**
     * next month
     */
    $('.btn-next').click(function() {
            var date = new Date();
            // if(eval($('.month').attr('month'))+1 >= 12){
            //     date.setFullYear($('.month').attr('year'));
            // }
            date.setFullYear($('.month').attr('year'));
            date.setMonth( eval($('.month').attr('month'))+1);
            console.log(date);
            calendar.init(date);
        })

     /**
      * dest: 刪除備忘錄事件，一定要用on方法才能讓動態產生的元素也能執行
      * @param  {[type]} )                   {                                           var ccc [description]
      * @param  
      * @return {[type]}                     [description]
      */
    $(document).on("click", '.delete', function() {
        
         var ccc = $(this);

        swal({
          title: '你確定要刪除嗎?',
          text: '刪了就沒了喔!',
          type: 'warning',
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: '我刪!',
          cancelButtonText: '不不不按錯了..',
        },
        function(isConfirm) {
          if (isConfirm) {
            $.ajax({
            url : path +  "/memo/removeMemo.do/" + ccc.attr('memoId'),
                    type : "get", 
                    async : true,
                    success : function (data) {
                        if(data == "success") {
                            swal({
                                title : "删除備忘錄成功!",
                                text: "",
                                type : "success"},
                                function() {
                                    $('#addMmeoModal').modal('hide');
                                    var setDat = new Date();
                                    var tt = ccc.parent().prev().prev().text().split('-');
                                    setDat.setFullYear(tt[0]);
                                    setDat.setMonth(tt[1]-1);
                                    calendar.init(setDat);
                                    
                                })
                        } else {
                            sweetAlert("刪除失敗..");
                        }
                    }
            })
          
          } else if (!isConfirm) {
            // do nothing
          } 
        }); 
    })

    $(document).on("click", '.modify', function() {
    	if($(this).attr('state') == "1") {
    		sweetAlert("過去的備忘錄不能再修改啦，請重新添加一個吧!");
    		return ;
    	}
        $.ajax({
            url : path + '/memo/getMemo',
            type : 'post',
            data : {'res' : $(this).attr('memoId')},
            success : function(data) {
                var response = eval(data);
                $('#myModalLabel').text('修改備忘錄');
                $('#memoContent').val(response.memoContent);
                $('#time').val(response.sendTime.substring(11));
                $('#memoId').val(response.memoId);

                $('#addButton').hide();
                $('#modifyButton').show();
                $('#addMmeoModal').modal('show');

            }
        })
        
    })


        $('#modifyButton').click(function() {
            ajaxGo('modify');
        })

        /**
         * dest: 添加備忘錄
         * @param  {String} ) {                       if( $('tbody td[date-day [description]
         * @return {[type]}   [description]
         */
        $('#addButton').click(function() {
            ajaxGo('add');
        })

        function ajaxGo(type) {
            if( $('tbody td[date-day="' + $('.memoTime')[0].outerText.split('-')[2] + '"]').hasClass('current-day')){
                var date = new Date();
                var getTi = $('#time').val().split(':');
                date.setHours(getTi[0]);
                date.setMinutes(getTi[1]);
                if(date < new Date()) {
                    sweetAlert("過去的備忘錄，我們沒法存下來呢");
                    return;
                    // swal();
                }
            }
            var sendObj ;
            
            var urlPath;
            var typeTitle;
            if(type == 'add'){
                urlPath = path +  "/memo/addMemo.do";
                typeTitle = "添加備忘錄成功!";
                sendObj = {
                'memoContent' : $('#memoContent').val(),
                'sendTime' : $('.memoTime')[0].outerText + ' ' + $('#time').val(),
//                'userId' : '1'
                 'userId' : $('#userId').val()
                };
            } else if(type == 'modify') {
                urlPath = path + "/memo/updateMemo.do";
                typeTitle = "修改備忘錄成功!";
                sendObj = {
                'memoContent' : $('#memoContent').val(),
                'sendTime' : $('.memoTime')[0].outerText + ' ' + $('#time').val(),
                'memoId' : $('#memoId').val()
                };
            }
            console.log(sendObj);
            $.ajax({
                    url : urlPath,
                    type : "post", 
                    data : sendObj,
                    async : false,
                    success : function (data) {
                        console.log(data);
                        if(data == "NORMAL") {
                            swal({
                                title : typeTitle,
                                text: "",
                                type : "success"},
                                function() {
                                    $('#addMmeoModal').modal('hide');
                                    var setDat = new Date();
                                    var tt = $('.memoTime')[0].outerText.split('-');
                                    setDat.setFullYear(tt[0]);
                                    setDat.setMonth(tt[1]-1);
                                    setTimeout(calendar.init(setDat), 1000);
                                    
                                }
                            );
                        } else {
                            swal({
                                title : "失敗了..",
                                text: "是不是寫了過去式的備忘錄哦?",
                                type : "error"},
                                function() {
                                   // do nothing
                                }
                            );
                        }
                        
                    },
                    error: function (response, ajaxOptions, thrownError) {
                        console.log(response);
                        console.log(ajaxOptions);
                        console.log(thrownError);
                        sweetAlert("系統錯誤了..");
                    }    
                })
        }

});