package com.Tingle.G4hosp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReservationDto {
	private Long reservationId;
	private Long reservationPatientId;
	private Long reservationDoctorId;
	private String reservationDate;
	private List<ReservationNotAvailableDto> notAvailableDay;
}
