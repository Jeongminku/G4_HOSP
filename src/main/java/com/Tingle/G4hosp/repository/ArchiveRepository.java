package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.Archive;

public interface ArchiveRepository extends JpaRepository<Archive, Long>{

	@Query(value = "select * from archive where archive.patient_id = :PAID", nativeQuery = true)
	List<Archive> findArchivelistbypatientid(@Param("PAID") Long paid);
	
}
