package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.ReservationNotAvailable;

public interface ReservationNotAvailableRepository extends JpaRepository<ReservationNotAvailable, Long> {
	List<ReservationNotAvailable> findByDoctor(Member doctor);
}
