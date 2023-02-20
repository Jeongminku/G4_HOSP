package com.Tingle.G4hosp.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.MedFormDto;
import com.Tingle.G4hosp.dto.MemberFormDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MemberMedRepository;
import com.Tingle.G4hosp.repository.MemberRepository;

@SpringBootTest
//@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ReservationControllerTest {

	@Autowired
	private MedRepository medRepository;
	@Autowired
	private MemberMedRepository memberMedRepository;
	@Autowired
	private MemberRepository memberRepository;
	
	public Med createMed(String name, String info) {
		MedFormDto dto = new MedFormDto();
		dto.setMedInfo(info);
		dto.setMedName(name);
		return medRepository.save(dto.createMed()); 
	}
	
	public Member createDoc (String id, String name, String pw, String tel, String birth) {
		MemberFormDto dto = new MemberFormDto();
		dto.setBirth(birth);
		dto.setLoginid(id);
		dto.setName(name);
		dto.setPwd(pw);
		dto.setTel(tel);
		dto.setRole(Role.DOCTOR);
		dto.setImgUrl("test");
		dto.setImgOri("test");
		dto.setImgName("test");
		return memberRepository.save(dto.createMember());
	}
	
	public MemberMed connectMedMember (Member doc, Med div) {
		return memberMedRepository.save(MemberMed.createMemberMed(doc, div));
	}
	
	
	@Test
	@DisplayName("createTest")
	void test() {
		for(int i = 1; i < 6; i++) {
			Med med = createMed(i + " div", "info");
			for(int j = 1; j < 3; j++) {
				Member doc = createDoc("id " + (j+(i*10)), "name " + j, "pw", "tel", "birth");
				MemberMed memberMed = connectMedMember(doc, med);
				System.err.println(memberMed);
			}			
		}
		
		List<Med> medList = medRepository.findAll();
		for(Med med : medList) {
			System.err.println("====================");
			System.err.println("과" + med.getMedName());
			System.err.println("====================");
			List<Member> doc = memberMedRepository.findDoctorsByMed(med);
			for(Member doctor : doc) {
				System.err.println(doctor.getName());
			}
			System.err.println("====================");			
		}
	}

}
