package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findByReservationDoctorOrderByReservationDate (Member doctor);
}
