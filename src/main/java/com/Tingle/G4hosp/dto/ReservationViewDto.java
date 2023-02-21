package com.Tingle.G4hosp.dto;

import java.time.LocalDateTime;
import java.util.*;

import com.Tingle.G4hosp.entity.Reservation;

import lombok.Data;

@Data
public class ReservationViewDto {
	private Long id;
	private String title;
	private String url;
	private LocalDateTime start;
	private LocalDateTime end;
	private boolean allDay = true;
	private boolean durationEditable = false;

	public static List<ReservationViewDto> createReservationViewDtoList (List<Reservation> reservationList) {
		List<ReservationViewDto> reservationViewDtoList = new ArrayList<>();
		for(Reservation reservation : reservationList) {
			ReservationViewDto reservationViewDto = new ReservationViewDto();
			reservationViewDto.setId(reservation.getReservationId());
			reservationViewDto.setTitle(reservation.getReservationPatient().getName());
			reservationViewDto.setUrl("temp");
			reservationViewDto.setStart(reservation.getReservationDate());
			LocalDateTime calcEndDay = reservationViewDto.getStart().plusDays(1);
			reservationViewDto.setEnd(calcEndDay);
			reservationViewDtoList.add(reservationViewDto);
		}
		return reservationViewDtoList;
	}
}
