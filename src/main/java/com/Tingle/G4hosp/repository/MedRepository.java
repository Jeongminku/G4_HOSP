package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.Med;

public interface MedRepository extends JpaRepository<Med, Long> {
	
	@Query(value = "select * from med a join membermed b on a.med_id = b.med_id join member c on b.member_id = c.member_id where c.member_id = :MEMID", nativeQuery = true)
	Med findMedbyDocid(@Param("MEMID") Long doctorid);
}
