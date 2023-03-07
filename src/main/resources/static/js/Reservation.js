
let calendar;
let calendatBaseObj = {
    customButtons: {
        customPrev: {
            icon : 'chevron-left',
            click: function () {
                customCalendarButtonPrev('prev');
            }
        },
        customNext: {
            icon: 'chevron-right',
            click: function () {
                customCalendarButtonPrev('next');
            }
        },
        customToday: {
            text: 'today',
            click: function () {
                customCalendarButtonPrev('today');
            }
        },
    },
    headerToolbar: {
        start: "customPrev,customNext customToday",
        center:'',
        end: "title",
    },
    dayMaxEventRows: true,
    initialView: 'dayGridMonth',
    timeZone: 'local',
    locale: 'kr',
    events: typeof calendarEvents == "undefined" ? [] : calendarEvents,
    selectable: true,
    validRange: {
        start: new Date()
    },
    select: function (arg) {},
    eventClick: function (info) { 

    },
    
}
const path = location.pathname.substring(location.pathname.lastIndexOf('/') + 1);

if (path == 'setNotAvailDay') {
    calendatBaseObj.select = (arg) => {
        setNotAvailDay(arg);
    };
} else if(path == 'listView') {
    calendatBaseObj.eventClick = (info) => {
        console.log(info)
        const eventDate = info.event._instance.range.start;
        const eventTitle = info.event._def.title;
        const eventTime = eventTitle.substring(0, 5);
        const eventMonth = eventDate.getMonth() + 1 < 10 ? "0" + (eventDate.getMonth() + 1) : eventDate.getMonth() + 1;
        const deleteCheck = confirm(eventDate.getFullYear() + "년 " + eventMonth + "월 " + eventDate.getDate() + "일\n" + eventTime + "시의 예약을 취소하시겠습니까?")
        if (deleteCheck) {
            const delForm = $(document).find('#deleteForm');
            delForm.find('input[name="reservationId"]').val(info.event._def.publicId)
            console.log( delForm.find('input[name="reservationId"]').val())
            delForm.submit();
        }
    }
} else {
    calendatBaseObj.select = (arg) => {
        selectReservationDate(arg);
    };
}
calendarLoad(calendatBaseObj)

function calendarLoad (calendatBaseObj) {
    document.addEventListener('DOMContentLoaded', function () {
        var calendarEl = document.getElementById('Fcalendar');
        calendar = new FullCalendar.Calendar(calendarEl, calendatBaseObj);
        calendar.render();
        typeof reservationViewPatDto == "undefined" ? '' : disableNotAvailDay();
        typeof notAvail == "undefined" ? '' : setNotAvailDay_exsistView();
    });
}

function disableNotAvailDay() {
    const notAvailList = reservationViewPatDto.notAvailableDay;
    const dayArr = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
    notAvailList.forEach(obj => {
        let idx = dayArr.indexOf(obj.notAvailableDay)
        if (idx != -1) disableNotAvilDay_day(obj.notAvailableDay)
        else disableNotAvilDay_date(obj.notAvailableDay)
    })
}

function disableTakenTime(day) {
    const takenTimeList = reservationViewPatDto.takenTime;
    let timeNum = 9;
    $('label').each(function () {
        $(this).removeClass('taken');
        if (timeNum < 13) {
            $(this).text('오전 ' + timeNum + '시');
            timeNum += 1;
        } else {
            $(this).text('오후 ' + (timeNum - 12) + '시');
            timeNum += 1;
        }
    })
    takenTimeList.forEach(arr => {
        let dayStr = arr.substring(0, arr.indexOf('T'));
        let timeStr = arr.substring(arr.indexOf('T') + 1);
        $('input[name="time"]').each(function () {
            $(this).attr('disabled', false)
        })
        if (day == dayStr) {
            $('input[id="' + timeStr + '"]').attr('disabled', true);
            $('label[for="' + timeStr + '"]').addClass('taken')
            $('label[for="' + timeStr + '"]').text('예약중')
        }
    })
}

function disableNotAvilDay_day(str) {
    const fcClass = ".fc-daygrid-day.fc-day-";
    const currentDays = $(fcClass + str);
    currentDays.each(function () {
        $(this).addClass('fc-day-disabled')   
    })
}

function disableNotAvilDay_date(str) {
    $('td[data-date="' + str + '"]').addClass('fc-day-disabled');
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
        appendNotAvailDateInput(arg.startStr);
    }
};

function appendNotAvailDateInput(date) {
    const notAvailList = $('#notAvailSelectedList');
    notAvailList.children('p').remove();
    let $input = $('<input class="d-none" type="date" name="notAvailDate"/>').val(date);
    let $button = $('<button class=" text-center p-md-3 mb-2 btn btn-outline-secondary w-100 customDay" type="button">' + date + '</button>');
    notAvailList.append($input);
    notAvailList.append($button);
    startStrArr.push(date);
}

function setNotAvailDay_exsistView () {
    const dayArr = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
    notAvail.forEach(str => {
        let idx = dayArr.indexOf(str)
        if (idx != -1) {
            $('input[value="' + str + '"]').prop('checked', true);
            disableNotAvilDay_day(str)
        }
        else {
            appendNotAvailDateInput(str);
            disableNotAvilDay_date(str);
        }
    })
}

let selectDay;
function selectReservationDate(arg) {
    const startDate = new Date(arg.startStr);
    const endDate = new Date(arg.endStr);
    
    if (endDate.getDate() - 1 != startDate.getDate()) {
        calendar.unselect();
    }
    const currentSelect = $('td[data-date="' + arg.startStr + '"]');
    if (currentSelect.hasClass('fc-day-disabled')) calendar.unselect();
    else {
        selectDay = arg.startStr;
        $('td').each(function () {
            $(this).removeClass('selectedDay');
        })
        currentSelect.addClass('selectedDay');
    }

    disableTakenTime(selectDay)
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

// $(document).on('click', '.fc-button', function () {
//     setTimeout(() => {
//         typeof reservationViewPatDto == "undefined" ? '' : disableNotAvailDay();
//         typeof notAvail == "undefined" ? '' : setNotAvailDay_exsistView();
//     }, 1000);
// })

function customCalendarButtonPrev(str) {
    if (str == 'prev') calendar.prev();
    if (str == 'next') calendar.next();
    if (str == 'today') calendar.today();
    typeof reservationViewPatDto == "undefined" ? '' : disableNotAvailDay();
    typeof notAvail == "undefined" ? '' : setNotAvailDay_exsistView();
}

