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
    selectForm.submit();
})

$(document).on('click', '#chatRoomEdit', function () {
    const currentTr = $(this).parent().parent();
    const currentAccessId = currentTr.find('input[name="chatRoomAccessId"]').val();
    const currentRoomId = currentTr.find('input[name="chatRoomId"]').val();
    const currentRoomName = currentTr.find('td[name="chatRoomName"]').text();
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

window.addEventListener('DOMContentLoaded', function () {
    const path = location.pathname;
    const adminPath = '/admin';
    let originHref;
    console.log(path.substring(0, 12))
    if (path == '/admin/memberList') {
        const param = location.search.substring(location.search.indexOf('=') + 1);
        if (param != '') {
            history.pushState(null, null, adminPath + '/memberList')
            $('.btn-group').children('#' + param).click();
        }
    }
    if (path.substring(0, 12) == '/admin/board' || path == '/admin/board/main') {
        const subPath = path.substring(12);
        if (subPath == '/write') {
            originHref = $('button').attr('formaction');
            $('button').attr('formaction', adminPath + originHref);
            $('#toList').on('click', function() {
                location.href = adminPath + '/board/main';
            })
        }
        if (subPath == '' || subPath == '/main') {
            $('#boardWrite').on('click', function () {
                location.href = adminPath + '/board/write';
            })
            originHref = $('.message-body').find('a').attr('href');
            $('.message-body').find('a').attr('href', adminPath + originHref);
            originHref = $('#searchForm').attr('action');
            $('#searchForm').attr('action', adminPath + originHref);
        }
        if (Number(subPath) != NaN) {
            $('#toList').on('click', function() {
                location.href = adminPath + '/board/main';
            })
            $('#postBtnGroup a').each((idx, item) => {
                originHref = $(item).attr('href');
                $(item).attr('href', adminPath + originHref);
            })
            $('nav li.page-item a').each((idx, item) => {
                originHref = $(item).attr('href');
                $(item).attr('href', adminPath + originHref);
            })
        }
    }
})