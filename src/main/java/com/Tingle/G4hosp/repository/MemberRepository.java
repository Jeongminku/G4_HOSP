package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByMemberId(String memberid); //아이디 중복 확인 
}
