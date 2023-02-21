package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByLoginid(String loginid);
	
	// select * from member where role = ?
	// 관리자 페이지 -> 고객/의사 목록 조회
	List<Member> findByRole(Role role);
	
	@Query(value = "select * from member where member.role = 'doctor' and name = :MemberName",nativeQuery = true)
	Member findbyNameindoctor(@Param("MemberName") String membername);
	
	@Query(value = "select * from member where member.name = :MNAME and member.tel = :MTEL", nativeQuery = true)
	Member findbtMnameandMtel(@Param("MNAME") String mname, @Param("MTEL") String mtel);
	
}
