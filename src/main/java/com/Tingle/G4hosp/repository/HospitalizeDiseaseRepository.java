package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.ArchiveDisease;
import com.Tingle.G4hosp.entity.HospitalizeDisease;

public interface HospitalizeDiseaseRepository extends JpaRepository<HospitalizeDisease, Long>{

	@Query(value = "select * from hospitalize_disease a where a.hospitalize_id = :HOSID", nativeQuery = true)
	HospitalizeDisease findHoDisbyHosId (@Param("HOSID") Long hosid);
}
