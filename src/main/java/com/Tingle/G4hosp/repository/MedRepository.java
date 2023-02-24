package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.dto.MemberFormDto;
import com.Tingle.G4hosp.entity.Med;

public interface MedRepository extends JpaRepository<Med, Long> {

	//의사 id로 의사가 속해있는 진료 과 정보를 가져오는 Query
	@Query(value = "select * from med a join membermed b on a.med_id = b.med_id join member c on b.member_id = c.member_id where c.member_id = :MEMID", nativeQuery = true)
	Med findMedbyDocid(@Param("MEMID") Long doctorid);
	
	@Query(value = "select * from med where med_id not in(:myMedId)", nativeQuery=true)
	List<Med> getMedListNotMyMed(@Param("myMedId") Long medid);
	
}
