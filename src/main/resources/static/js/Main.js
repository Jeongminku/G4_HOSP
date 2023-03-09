//swiper 슬라이드 효과
var swiper = new Swiper(".mySwiper", {
    cssMode : true,
    autoplay : { //오토플레이 설정
        delay : 2000, //딜레이 시간 설정
    },
    navigation : {
        nextEl : ".swiper-button-next",
        prevEl : ".swiper-button-prev",
    },

    pagination : {
        el : ".swiper-pagination",
    },
    mousewheel : true,
    keyboard : true,
});

/*$(function(){
		var searchStr = $('.doc_searchbar').val();
		if(searchStr.length<2) {
			$('.doc_searchbar').attr('oninvalid',"this.setCustomValidity('2글자 이상 입력하라')")
		}
})*/

$('.table tbody tr').on('click', function () {
    const boardId = $(this).children('td:first-child').text();
    const targetBoard = $(this).parents('table').attr('id');
    location.href = '/' + targetBoard + '/' + boardId;
})