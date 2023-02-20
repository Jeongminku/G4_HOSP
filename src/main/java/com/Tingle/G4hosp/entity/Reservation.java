package com.Tingle.G4hosp.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Data
@Entity
public class Reservation extends BaseTime{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reservationId;
	
	@JoinColumn(name = "reservationPatient")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member reservationPatient;
	
	@JoinColumn(name = "reservationDoctor")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member reservationDoctor;
	
	@Column(nullable = false)
	private LocalDateTime reservationDate;
	
	public static Reservation createReservation (String date , Member patient, Member doctor, DateTimeFormatter formatter) {
		Reservation reservation = new Reservation();
		
		reservation.setReservationPatient(patient);
		reservation.setReservationDoctor(doctor);
		reservation.setReservationDate(LocalDateTime.parse(date, formatter));

		return reservation;
	}
	
	public void updateReservation (String date, Member doctor, DateTimeFormatter formatter) {
		this.reservationDoctor = doctor;
		this.reservationDate = LocalDateTime.parse(date, formatter);
	}
}
