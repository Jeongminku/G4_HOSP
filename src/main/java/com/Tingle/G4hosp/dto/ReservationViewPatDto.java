package com.Tingle.G4hosp.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.Tingle.G4hosp.entity.Reservation;
import com.Tingle.G4hosp.entity.ReservationNotAvailable;

import lombok.Data;

@Data
public class ReservationViewPatDto {
	private List<ReservationNotAvailableDto> notAvailableDay;
	private List<String> takenTime;
	
	public static ReservationViewPatDto createReservationViewPatDto (List<ReservationNotAvailable> notAvailableDay, List<Reservation> takenReservation) {
		ReservationViewPatDto reservationViewPatDto = new ReservationViewPatDto();
		List<ReservationNotAvailableDto> notAvailableDayDto = ReservationNotAvailableDto.createResreAvailableDto(notAvailableDay);
		reservationViewPatDto.setNotAvailableDay(notAvailableDayDto);
		List<String> takenTime = takenReservation.stream().map(entity -> entity.getReservationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))).collect(Collectors.toList());
		reservationViewPatDto.setTakenTime(takenTime);
		return reservationViewPatDto;
	}
}
