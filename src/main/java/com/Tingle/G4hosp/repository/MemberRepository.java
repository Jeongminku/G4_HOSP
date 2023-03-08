package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.entity.Hospitalize;
import com.Tingle.G4hosp.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{
	Member findByLoginid(String loginid);
	
	// select * from member where role = ?
	// 관리자 페이지 -> 고객/의사 목록 조회
	List<Member> findByRole(Role role);
	
	@Query(value = "select * from member where member.role = 'doctor' and name = :MemberName",nativeQuery = true)
	Member findbyNameindoctor(@Param("MemberName") String membername);
	
	@Query(value = "select * from member where member.role = 'doctor' and member_loginid = :MID", nativeQuery = true)
	Member findDocbyMid(@Param("MID") String doctorid);
	
	@Query(value = "select * from member where member.name = :MNAME and member.tel = :MTEL", nativeQuery = true)
	Member findbyMnameandMtel(@Param("MNAME") String mname, @Param("MTEL") String mtel);

	//비밀번호찾기(변경)
	@Query(value = "select * from member where member.member_loginid = :MID and member.name = :MNAME and member.tel = :MTEL", nativeQuery = true)
	Member findPwd(@Param("MID") String mid, @Param("MNAME") String mname, @Param("MTEL") String mtel);

	//입원상태인 환자의 정보 가져오기.
	@Query(value = "select * from member b join hospitalize a on b.member_id = a.member_id where a.hospyn = 'Y'" , nativeQuery = true)
	List<Member> FindHosMemListByHosStatus ();

	//role이 환자인 사람의 이름으로 member객체가져오기.
	@Query(value = "select * from member where member.role = 'client' and member.name like %:MNAME%",nativeQuery = true)
	List<Member> findMListbyMname(@Param("MNAME") String mname);

	Member findByName(String name);

}
