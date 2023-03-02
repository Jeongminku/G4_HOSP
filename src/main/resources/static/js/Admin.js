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

$(document).on('click', '#chatRoomDel', function () {
    const selectForm = $(this).children('form[name="deleteId"]');
    selectForm.submit();
})

$(document).on('click', '#chatRoomEdit', function () {
    const currentLi = $(this).parent().parent();
    const currentAccessId = currentLi.children('input[name="chatRoomAccessId"]').val();
    const currentRoomId = currentLi.children('input[name="chatRoomId"]').val();
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