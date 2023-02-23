package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.Hospitalize;

public interface HospitalizeRepository extends JpaRepository<Hospitalize, Long>{

	// RETURN NOW HOSPITALIZED LIST 
	@Query(value = "select * from hospitalize a join member b on a.member_id = b.member_id where a.hospyn = 'Y'" , nativeQuery = true)
	List<Hospitalize> FindHosListByHosStatus ();
	
	
	
	@Query(value = "select * from hospitalize a where a.member_id = :MEMID", nativeQuery = true)
	Hospitalize FindHosbymemid(@Param("MEMID") Long memid);
	

	
}
