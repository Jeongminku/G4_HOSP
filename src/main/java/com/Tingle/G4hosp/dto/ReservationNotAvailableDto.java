package com.Tingle.G4hosp.dto;

import com.Tingle.G4hosp.entity.ReservationNotAvailable;

import lombok.Data;

@Data
public class ReservationNotAvailableDto {
	private String dayClass;
	private String customDate;
	
	public static ReservationNotAvailableDto createResreAvailableDto (ReservationNotAvailable reservationNotAvailable) {
		return null;
	}
}
