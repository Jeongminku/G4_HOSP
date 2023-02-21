let selectDay;
let calendatBaseObj = {
    headerToolbar: {
        left: "prev,next today",
        center: "title",
    },
    initialView: 'dayGridMonth',
    timeZone: 'local',
    locale: 'kr',
    events: typeof calendarEvents == "undefined" ? [] : calendarEvents,
    selectable: true,
    validRange: {
        start: new Date()
    },
    select: function (arg) {
        
    },
    eventClick: function (info) {
        
    },
}
const path = location.pathname.substring(location.pathname.lastIndexOf('/') + 1);

if (path == 'setNotAvailDay') {
    calendatBaseObj.select = (arg) => {
        setNotAvailDay(arg);
    };
    calendarLoad(calendatBaseObj)
} else if(path == 'listView') {
    
} else {
    calendatBaseObj.select = (arg) => {
        selectReservationDate(arg);
    };
}
calendarLoad(calendatBaseObj)

function calendarLoad (calendatBaseObj) {
    document.addEventListener('DOMContentLoaded', function () {
        var calendarEl = document.getElementById('Fcalendar');
        var calendar = new FullCalendar.Calendar(calendarEl, calendatBaseObj);
            calendar.render();
    }); 
}

let startStrArr = [];
function setNotAvailDay (arg) {
    let check = confirm(arg.startStr + '을 휴일로 지정하시겠습니까?');
    if (check) {
        let idx = $.inArray(arg.startStr, startStrArr);
        if (idx != -1) {
            alert("이미 추가한 일자입니다.");
            return false;
        }
        const notAvailList = $('#notAvailSelectedList');
        notAvailList.children('p').remove();
        let $input = $('<input class="d-none" type="date" name="notAvailDate"/>').val(arg.startStr);
        let $button = $('<button class=" text-center p-md-3 mb-2 btn btn-outline-secondary w-100 customDay" type="button">' + arg.startStr + '</button>');
        notAvailList.append($input);
        notAvailList.append($button);
        startStrArr.push(arg.startStr);
    }
};

function selectReservationDate(arg) {
    const startDate = new Date(arg.startStr);
    const endDate = new Date(arg.endStr);
    selectDay = arg.startStr;
    
    if (endDate.getDate() - 1 != startDate.getDate()) {
        calendar.unselect();
    }
    $('td').each(function () {
        $(this).removeClass('selectedDay');
    })
    $('td[data-date="' + selectDay + '"]').addClass('selectedDay');
}

$('input[name="time"]').each(function () {
    $(this).on('change', function () {
        const doctorId = parseInt(location.pathname.substring(location.pathname.lastIndexOf('/') + 1));
        $('input[name="reservationDoctorId"]').val(doctorId);
        if ($(this).prop('checked')) {
            const selectTime = $(this).attr('id');
            const finalReservDate = selectDay + 'T' + selectTime + ':00';
            $('input[name="reservationDate"]').val(finalReservDate);
        }
    })
})

$(document).on('click', 'button.customDay', function () {
    const deleteConfirm = confirm($(this).text() + ' 을 휴일에서 제외합니까?');
    if (deleteConfirm) {
        $(this).prev('input').remove();
        $(this).remove();
    }
})
