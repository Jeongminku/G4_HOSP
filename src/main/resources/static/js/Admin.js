// 전역
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

// 관리자 알림용 웹소켓
var socket = new WebSocket("ws://localhost:80/websocket");

socket.onopen = () => {
    console.log(socket);
};

socket.onmessage = function (event) {
    cleartest();

    // WebSocket으로 전달받은 알람을 header에 띄움
    $("#alert-icon").css("display", "inline");

    // 관리자 로그인 전용 알림
    var msgTimer = 0;
    var test = $("#alert-message");
    setTimeout(function () {
        test.fadeIn(500, function () {
        msgTimer = setTimeout(function () {
            test.fadeOut(500);
        }, 1000);
        });
    }, 200);
    function cleartest() {
        if (msgTimer != 0) {
        clearTimeout(msgTimer);
        msgTimer = 0;
        }
    }
    $("#alert-message").text(event.data);
};

// 헤더 메뉴 버튼
$(document).on('click', 'nav button', function () {
    const hrefURL = $(this).attr('id');
    location.href = hrefURL;
})

// 비회원 예약 페이지
function deleteqr(value){
    var confir = confirm("취소된 예약이 맞습니까?");
    if(confir){
        alert("취소가 완료되었습니다.");
        location.href = "/admin/deleteqr/"+value;
    }else{
        alert("현 상태를 유지합니다.");
        return false;
    }
}

// 채팅방
$(document).on('click', '#chatRoomDel', function () {
    const selectForm = $(this).children('form[name="deleteId"]');
    console.log(selectForm)
    selectForm.submit();
})

$(document).on('click', '#chatRoomEdit', function () {
    const currentLi = $(this).parent().parent();
    const currentAccessId = currentLi.children('input[name="chatRoomAccessId"]').val();
    const currentRoomId = currentLi.find('input[name="chatRoomId"]').val();
    const currentRoomName = currentLi.children('div[name="chatRoomName"]').text();
    const editForm = $('#chatRoomEditModal').find('form');

    editForm.find('option').each((idx, item) => {
        if ($(item).val() == currentAccessId){
            $(item).prop('selected', true);
        }    
    })
    editForm.find('input[name="eidtChatName"]').val(currentRoomName);
    editForm.find('input[name="editchatRoomId"]').val(currentRoomId);
})

// 회원목록페이지
$(document).on('click', '.btn-group > .btn.btn-outline-primary', function () {
    $('.btn-group > .btn.btn-outline-primary').removeClass('active');
    $(this).addClass('active')
    const optStr = $(this).attr('id');

    $.ajax({
        url: '/admin/memberList/' + optStr,
        type: 'POST',
        dataType: 'text',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function (frag) {
            $('#memberListTable').replaceWith(frag);
        },
        error: function(jqXHR, status, error) {
            alert('error')
            console.log(jqXHR)
        },
    })
})
$(document).ready(function () {
	console.log(param)
	if(param != null) {
		$('.btn-group > .btn.btn-outline-primary').removeClass('active');
	    $('.btn-group > .btn.btn-outline-primary').each(btn => {
			if(btn.attr('id') == param.opt[0]) {
				 const optStr = btn.attr('id');
		
			    $.ajax({
			        url: '/admin/memberList/' + optStr,
			        type: 'POST',
			        dataType: 'text',
			        beforeSend: function (xhr) {
			            xhr.setRequestHeader(header, token);
			        },
			        cache: false,
			        success: function (frag) {
			            $('#memberListTable').replaceWith(frag);
			        },
			        error: function(jqXHR, status, error) {
			            alert('error')
			            console.log(jqXHR)
			        },
			    })
			}
		})
	}
})
  

	// 관리자페이지 구글차트 통계관련

		$(document).ready(function(){
			  /* alert("준비중입니다."); */
			  test();
		});
		
		/* 변수선언 */
		
		 var t1 = null;
		 var t2 = null;
		 var t3 = null;
		 var t4 = null;
		
      	/* 데이터 가져오기 */
	 	function test() {
	        var token = $("meta[name='_csrf']").attr("content");
	        var header = $("meta[name='_csrf_header']").attr("content");
	       
	        var url = "/admin/chart";
	       
	        $.ajax({
	        	url : url,
	        	type : 'POST',
	        	data : {
	           
	        	},
	            beforeSend : function(xhr){
	                /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
	                xhr.setRequestHeader(header, token);
	            },
	        	success : function(chartData) { // controllor에서 list를 return 받았음
	        		
	        		/*	console.log(chartData.hosptalizedEachWard)	
	          			console.log(chartData.hosptalizedEachWardname)	*/
	          			console.log(chartData.doctorcount + '의사수?')
	        			console.log(chartData.patientcount + '환자수?')
	          			$("#doctorCount").text(chartData.doctorcount)
	          			$("#memberCount").text(chartData.patientcount)
	        			
	        			
	                    t1 = chartData.hosptalizedEachMedname
	                    t2 = chartData.hosptalizedEachMed
	                   
	                    t3 = chartData.hosptalizedEachWardname
						t4 = chartData.hosptalizedEachWard
	                   
	          			google.charts.setOnLoadCallback(drawChart);
	          		 	google.charts.setOnLoadCallback(drawBasic);
	          		 	google.charts.setOnLoadCallback(drawBasic2);
	        			console.log("????????????????????????????????????")
	             },
	            
	        	error : function() {
	        		alert("error");
	        	}
	            
	        });
		}
      	
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);
      
      	/* 원 차트 */
		google.charts.load('current', {'packages':['corechart']});
	    function drawChart() {
			
	        // Create the data table.
	        var data = new google.visualization.DataTable();
	       
	        data.addColumn('string', 'Topping');
	        data.addColumn('number', 'Slices');
	       
	        for( var i = 0; i < t2.length; i ++){
	            data.addRows([
	                [t1[i] , t2[i]]
	              ]);
	        }
	       
	        // Set chart options
	        var options = {'title':'진료과별 입원 환자 수',
	                       'width':500,
	                       'height':400};
	        // Instantiate and draw our chart, passing in some options.
	        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
	        chart.draw(data, options);
	      }
     	
	 	/* 막대 차트 */
	 	
     	google.charts.load('current', {packages: ['corechart', 'bar']});
     	function drawBasic() {
     		console.log(t3.length)
     		
          var data = new google.visualization.DataTable();
        	data.addColumn('string', 'DataName');
       		data.addColumn('number', 'Value');
       		
             for( var i = 0; i < t3.length; i ++) {
                 data.addRows([
                     [t3[i] , t4[i]]
                   ]);
             }
             
     	      var options = {
     	        title: '각 호실별 입원 현황',
     	        chartArea: {width: '100%'},
     	        hAxis: {
     	          title: '병실 당 수용 인원 수',
     	          minValue: 10
     	        },
     	        vAxis: {
     	          title: 'City'
     	        }
     	      };
     	      var chart = new google.visualization.BarChart(document.getElementById('chart_div3'));
			  chart.draw(data, options);
     	    }
     	
     	/* 수정본 세로차트  */
		
		function drawBasic2() {
		var data = new google.visualization.DataTable();
		data.addColumn('string', '입원실(호)');
		data.addColumn('number', '입원환자 수(명)');
		
        for( var i = 0; i < t3.length; i ++) {
            data.addRows([
                [t3[i] , t4[i]]
              ]);
        }
		
			/* data.addRows([
			
				['일', 1],
				['월', 2],
				['화', 3],
				['수', 4],
				['일', 5],
				['금', 6],
				['토', 7],
			
			]); */

			var options = {
				title: '각 호실별 입원 현황',
				hAxis: {
				title: '입원실(호)',
				viewWindow: {
					min: [7, 30, 0],
					max: [17, 30, 0]
					}
				},
				
				vAxis: {
					title: '입원환자 수(명)',
					minValue: 10
				}
			
			};
			
			 
			
			var chart = new google.visualization.ColumnChart(
			document.getElementById('chart_div5'));
		
			chart.draw(data, options);
		
		}