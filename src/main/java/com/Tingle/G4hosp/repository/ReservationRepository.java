package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
