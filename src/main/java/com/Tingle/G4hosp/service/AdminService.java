package com.Tingle.G4hosp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

//	// MemberService 에 있던거 뺌
	
//	private final MemberRepository memberRepository;
//	
//	 // 관리자 페이지 -> 고객/의사 목록 조회
//	 public List<Member> getMemberList(Role role){
//		 return memberRepository.findByRole(role);
//	 }
	
}
