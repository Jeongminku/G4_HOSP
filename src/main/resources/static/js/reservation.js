let selectDay;
document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('Fcalendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: "prev,next today",
            center: "title",
        },
        initialView: 'dayGridMonth',
        timeZone: 'local',
        locale: 'kr',
        events: [], 
			selectable: true,
            select: function (arg) {
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
			},
			eventClick: function (info) {
                
            },
        });
        calendar.render();
});

$('input[name="time"]').each(function () {
    $(this).on('change', function () {
        const doctorId = location.pathname.substring(location.pathname.lastIndexOf('/') + 1);
        $('input[name="doctorId"]').val(doctorId);
        if ($(this).prop('checked')) {
            const selectTime = $(this).attr('id');
            const finalReservDate = selectDay + 'T' + selectTime;
            $('input[name="reservationDate"]').val(finalReservDate);
        }
    })
})
