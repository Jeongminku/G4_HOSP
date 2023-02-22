package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long>{

	@Query(value = "select * from disease where disease.disease_name = :DNAME", nativeQuery = true)
	Disease findDiseasebyDiseasename(@Param("DNAME") String Diseasename);

}
