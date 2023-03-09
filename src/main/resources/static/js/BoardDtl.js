function saveReply() {
	
    if(!$("#replyContent").val()) {
     	alert("댓글을 입력하세요");
     	return false;
     }
	
 var token = $("meta[name='_csrf']").attr("content");
 var header = $("meta[name='_csrf_header']").attr("content");
 
 var url = "/board/saveReply";
 
 var paramData = {
 		replyContent : $("#replyContent").val(),
 		board :  $("#boardId").val(),       		
                  };
 console.log(paramData);
 var param = JSON.stringify(paramData);
 
 $.ajax({
     url      : url,
     type     : "POST",
     contentType : "application/json",
     data     : param,
     async:false,
     beforeSend : function(xhr){
         /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
         xhr.setRequestHeader(header, token);
     },
     cache   : false,
     success  : function(result, status){
     	alert("댓글을 입력했습니다.");
     	
  		document.getElementById("replyContent").value ='';
        
         postPageReload()
    
        // test()
         
     },
     error : function(jqXHR, status, error){
     		alert("로그인을 해주세요");
             location.href='/members/login';
     }
 });
 
	
}




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function deletReply(e) {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	url = "/board/replydel"
	
	var replyIdIndex = $(event.target).attr("id");
	
	console.log(replyIdIndex);	
	
	var replyId = $("#replyId"+replyIdIndex).val();
	
	
 var paramData = {
 		id : replyId,    		
                  };
 console.log(paramData);
 var param = JSON.stringify(paramData);
 console.log(param);
	
   $.ajax({
         url      : url,
         type     : "POST",
         contentType : "application/json",
         data     : param,
         async:false,
         beforeSend : function(xhr){
             /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
             xhr.setRequestHeader(header, token);
         },
         cache   : false,
         success  : function(result, status){
         	postPageReload();
        		alert("댓글 삭제를 완료 했습니다.");
         },
         error : function(jqXHR, status, error){
         	alert("error");
         	  console.log(jqXHR);
               console.log(status);
               console.log(error);
                 location.href='/members/login';
         }
     });
}
//수정창만들기
function createinput(e) {

var replyIdIndex = $(event.target).attr("id");
console.log(replyIdIndex);
console.log(replyIdIndex.substr(2));

var content = $("#con"+replyIdIndex.substr(2)).val();
	
//댓글창 none
$("#conview"+replyIdIndex.substr(2)).css('display', 'none');

//버튼 none
$("#reply [class*='btn']").not("[class*='up-btn" + replyIdIndex.substr(2) + "']").css('display', 'none');


//인풋창 생성
let input = document.createElement("input")
input.setAttribute("type","text")
input.setAttribute("class","upContent")
input.setAttribute("id","upContent")
input.setAttribute("value", content )
document.querySelector("#ReId"+replyIdIndex.substr(2)).append(input);

$("#upRe").css('display', 'block');


let button = document.getElementById(replyIdIndex)
button.setAttribute("onClick","upRe()") 


}
///댓글수정
function upRe(e) {
 var token = $("meta[name='_csrf']").attr("content");
 var header = $("meta[name='_csrf_header']").attr("content");
 
 var url = "/board/upRe";
 
 
 if(!$("#upContent").val()) {
 	alert("수정할 댓글을 입력하세요");
 	return false;
 }
 

	var replyIdIndex = $(event.target).attr("id");
	replyIdIndex = replyIdIndex.substr(2)
	
	var replyId = $("#replyId"+replyIdIndex).val();
 
 var paramData = {
 		id :  replyId,       		
 		replyContent : $("#upContent").val(),
                  };
 console.log(paramData);
 var param = JSON.stringify(paramData);
 
 $.ajax({
     url      : url,
     type     : "POST",
     contentType : "application/json",
     data     : param,
     async:false,
     beforeSend : function(xhr){
         /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
         xhr.setRequestHeader(header, token);
     },
     cache   : false,
     success  : function(result, status){
     	alert("댓글이 수정 되었습니다")
     	postPageReload()
         
     },
     error : function(jqXHR, status, error){
     		alert("회원가입을 해주세요");
             alert(jqXHR.responseText);
             location.href='/members/login';
     }
 });
}