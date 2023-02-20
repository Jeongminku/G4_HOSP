package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.Tingle.G4hosp.constant.NotAvailableDay;

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

	@Enumerated(EnumType.STRING)
	private NotAvailableDay notAvailableDay;
	
	public static ReservationNotAvailable createReservationNotAvailable (Member doctor, NotAvailableDay notAvailableDay) {
		ReservationNotAvailable reservationNotAvailable = new ReservationNotAvailable();
		reservationNotAvailable.setDoctor(doctor);
		reservationNotAvailable.setNotAvailableDay(notAvailableDay);
		return reservationNotAvailable;
	}
}
