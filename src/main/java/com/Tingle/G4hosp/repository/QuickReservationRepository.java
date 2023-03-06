package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tingle.G4hosp.entity.QuickReservation;

public interface QuickReservationRepository extends JpaRepository<QuickReservation, Long>{
	
	@Query(value = "select * from quickreservation a where a.qr_callyn = 'N'", nativeQuery = true)
	List<QuickReservation> qrListN();
	
	
}
