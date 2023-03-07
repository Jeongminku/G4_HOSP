package com.Tingle.G4hosp.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.Tingle.G4hosp.entity.Reservation;

import lombok.Data;

@Data
public class ReservationViewDto {
	private Long id;
	private String title;
	private String start;
	private String end;
	private boolean allDay = true;
	private boolean durationEditable = false;
	
	public static List<ReservationViewDto> createReservationViewDtoList (List<Reservation> reservationList, Boolean isDoctor) {
		List<ReservationViewDto> reservationViewDtoList = new ArrayList<>();
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		for(Reservation reservation : reservationList) {
			ReservationViewDto reservationViewDto = new ReservationViewDto();
			reservationViewDto.setId(reservation.getReservationId());
			if(isDoctor) reservationViewDto.setTitle(reservation.getReservationDate().format(timeFormatter) + " " + reservation.getReservationPatient().getName());
			else reservationViewDto.setTitle(reservation.getReservationDate().format(timeFormatter) + " " + reservation.getReservationDoctor().getName());
			reservationViewDto.setStart(reservation.getReservationDate().format(dayFormatter));
			LocalDateTime calcEndDay = reservation.getReservationDate().plusDays(1);
			reservationViewDto.setEnd(calcEndDay.format(dayFormatter));
			reservationViewDtoList.add(reservationViewDto);
		}
		return reservationViewDtoList;
	}
}
