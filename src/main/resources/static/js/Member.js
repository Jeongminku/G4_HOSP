// 공통
$(document).ready(function () {
    if(errorMessage != null){
        alert(errorMessage);
    }
});

// 회원가입 페이지
function medListEvent() {
    var doctor = $('#doctor');
    if(doctor.is(":checked")) {
        $('.medList').removeClass('d-none');
        $('#profile').removeClass('d-none');
    } else {
        $('.medList').addClass('d-none');
        $('#profile').addClass('d-none');
    }
} 

// 생년월일	birth 유효성 검사
function test(){
    var dateStr = $('#birth').val();	
    console.log(dateStr);

    var year = Number(dateStr.substr(0,4)); // 입력한 값의 0~4자리까지 (연)
    var month = Number(dateStr.substr(4,2)); // 입력한 값의 4번째 자리부터 2자리 숫자 (월)
    var day = Number(dateStr.substr(6,2)); // 입력한 값 6번째 자리부터 2자리 숫자 (일)
    var today = new Date(); // 날짜 변수 선언
    var yearNow = today.getFullYear(); // 올해 연도 가져옴

    if(dateStr.length<8 || dateStr.length>8){
        $('#birth_check').text('생년월일은 19990909형식의 8자로 작성해주세요.');
        $('#birth_check').css('color', 'red');
    } else if (dateStr.length = 8) {
        // 연도의 경우 1900 보다 작거나 yearNow 보다 크다면 false를 반환합니다.
        $('#birth_check').text('생년월일 형식이 일치합니다.');
        $('#birth_check').css('color', 'green');
    } else  if (1900 > year || year > yearNow){	
    } else if (month < 1 || month > 12) {
        $('#birth_check').text('생년월일을 확인해주세요.');
        $('#birth_check').css('color', 'red');
    } else if (day < 1 || day > 31) {
        $('#birth_check').text('생년월일을 확인해주세요.');
        $('#birth_check').css('color', 'red');
    } else if ((month==4 || month==6 || month==9 || month==11) && day==31) {
        $('#birth_check').text('생년월일을 확인해주세요.');
        $('#birth_check').css('color', 'red');
    } else if (month == 2) {
        var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
        if (day>29 || (day==29 && !isleap)) {
            $('#birth_check').text('생년월일을 확인해주세요.');
            $('#birth_check').css('color', 'red');
        } else {
            $('#birth_check').text('');
        }//end of if (day>29 || (day==29 && !isleap))
    }else{
        $('#birth_check').text(''); 
    }//end of if
};

const regexIdPattern = new RegExp("^[a-zA-Z0-9]*$");
//ID 중복체크
function chk() {
    var loginid = $('#loginid').val();
    var url = "/members/new/" + loginid;
    if (loginid == '') {
        $('#chk_result').text('아이디는 필수입력값입니다.');
        $('#chk_result').removeClass('c-confirm-color');
        $('#chk_result').addClass('c-alert-color');  
        $('#loginid').focus();
    } else {
        if (!regexIdPattern.test(loginid)) {
            $('#chk_result').text('아이디는 영문자, 숫자 조합으로만 가능합니다.');
            $('#chk_result').removeClass('c-confirm-color');
            $('#chk_result').addClass('c-alert-color');
        } else {
            $.ajax({
                type: 'GET',
                url : url,
                dataType : 'json',
                cache : false,
                success : function(result) {
                    if (result == 0) {
                        $('#chk_result').text('사용가능한 아이디입니다.');
                        $('#chk_result').removeClass('c-alert-color');
                        $('#chk_result').addClass('c-confirm-color');
                    } else {
                        $('#chk_result').text('이미 사용중인 아이디입니다. 다른 아이디를 입력해주세요.');
                        $('#chk_result').removeClass('c-confirm-color');
                        $('#chk_result').addClass('c-alert-color');
                    }
                },
                error : function(a,b,c) {
                    console.log(a,b,c);
                }
            });
        }
    }
};

//전화번호 중복체크
function chktel() {
    var tel= $('#tel').val();
    var url = "/members/news/" + tel;
    
    if (tel != '') {
        $.ajax({
            type: 'GET',
            url : url,
            dataType : 'json',
            cache : false,
            success : function(telresult) {
                if (telresult == 0) {
                    $('#chkTel_telresult').text('사용가능한 전화번호입니다.');
                    $('#chkTel_telresult').css('color', 'green');
                } else {
                    $('#chkTel_telresult').text('이미 사용중인 전화번호입니다. 다른 전화번호를 입력해주세요.');
                    $('#chkTel_telresult').css('color', 'red');
                }
            },
            error : function(a,b,c) {
                console.log(a,b,c);
            }
        });
    } else {
        $('#chkTel_telresult').text('전화번호는 필수입력값입니다.');
        $('#chkTel_telresult').css('color', 'red');
        $('#tel').focus();
    }
};

// 정보수정 페이지
$('.delMember').on('click', function(){
    $('.delMember').click(function(){
        if (confirm("삭제하시겠습니까?")) {
            $(location).attr("href", "/members/del/"+$(this).attr('id'));				
        }
        return false;
    })
});
