package com.Tingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findById(String memberid); //아이디 중복 확인 
}
