package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Data
@Entity
@Table(name = "reservationNotAvailable")
public class ReservationNotAvailable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reservationNotAvailableId;
	
	@JoinColumn(name = "doctor")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member doctor;

//	@Enumerated(EnumType.STRING)
//	private NotAvailableDay notAvailableDay;
	
	@Column(nullable = false)
	private String notAvailableDay;
	
	public static ReservationNotAvailable createReservationNotAvailable (Member doctor, String notAvailalbe) {
		ReservationNotAvailable reservationNotAvailable = new ReservationNotAvailable();
		reservationNotAvailable.setDoctor(doctor);
		reservationNotAvailable.setNotAvailableDay(notAvailalbe);
		return reservationNotAvailable;
	}
}
