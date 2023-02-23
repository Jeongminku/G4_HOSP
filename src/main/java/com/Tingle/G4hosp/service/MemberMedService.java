package com.Tingle.G4hosp.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.repository.MemberMedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberMedService {
	private final MemberMedRepository memberMedRepository;
	
	
	public MemberMed findMemberMed(Long memberId) {
		MemberMed memberMed = memberMedRepository.findByMemberid(memberId);
		return memberMed;
	}
}
