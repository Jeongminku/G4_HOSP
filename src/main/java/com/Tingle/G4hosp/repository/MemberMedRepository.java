package com.Tingle.G4hosp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;

public interface MemberMedRepository extends JpaRepository<MemberMed, Long> {
	@Query("SELECT mm.memberId FROM MemberMed mm WHERE mm.medId = :medId ORDER BY mm.memberId")
	List<Member> findDoctorsByMed(@Param("medId")Med medId);
	
	@Query(value = "select * from membermed where member_id = :memberId", nativeQuery = true)
	MemberMed findByMemberid(@Param("memberId") Long memberId);
}
