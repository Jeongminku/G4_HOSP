package com.Tingle.G4hosp.dto;

import lombok.Data;

@Data
public class ReservationDto {
	private Long reservationId;
	private Long reservationPatientId;
	private Long reservationDoctorId;
	private String reservationDate;
}
