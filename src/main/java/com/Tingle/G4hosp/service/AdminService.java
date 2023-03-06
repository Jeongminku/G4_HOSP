package com.Tingle.G4hosp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.AdminMainDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
	
	private final MemberRepository memberRepository;
	private final MedRepository medRepository;
	
	public AdminMainDto adminpagetest(AdminMainDto adminMainDto) {
	
		List<Med> medlist = medRepository.findAll();
		
		memberRepository.getdoctorcount(adminMainDto);
		memberRepository.gethospitalizedcount(adminMainDto);
		memberRepository.getpatientcount(adminMainDto);
		memberRepository.viewHosptalizedlistMed(medlist, adminMainDto);	
		memberRepository.viewHosptalizedwardlist(adminMainDto);
		
		return adminMainDto;
	}
	
}
