package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.ArchiveDisease;

public interface ArchiveDiseaseRepository extends JpaRepository<ArchiveDisease, Long>{

	@Query(value = "select * from archive_disease a where a.archive_id = :ARCID", nativeQuery = true)
	ArchiveDisease FindADbyARCid(@Param("ARCID") Long arcid);
	
}
