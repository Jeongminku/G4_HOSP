package com.Tingle.G4hosp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.constant.Hosp;
import com.Tingle.G4hosp.constant.Ward;
import com.Tingle.G4hosp.dto.HospitalizeFormDto;
import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Hospitalize;
import com.Tingle.G4hosp.entity.HospitalizeDisease;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.DiseaseRepository;
import com.Tingle.G4hosp.repository.HospitalizeDiseaseRepository;
import com.Tingle.G4hosp.repository.HospitalizeRepository;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalizeService {
	private final MemberService memberService;
	private final DiseaseService diseaseService;
	private final HospitalizeRepository hospitalizeRepository;
	private final HospitalizeDiseaseRepository hospitalizeDiseaseRepository;
	private final DiseaseRepository diseaseRepository;
	
	public Long CreateHospitalize(HospitalizeFormDto hospitalizeFormDto, Long patientid) {
		Member patient = memberService.findByMemberid(patientid);
		Member doctor = memberService.findDoctorbydoctorname(hospitalizeFormDto.getDoctorname());
		String getward = hospitalizeFormDto.getWard();
		Hospitalize hospitalize;
		HospitalizeDisease hospitalizeDisease;
		Disease disease;
		Hosp hosp = null;	
 
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		
		if(hospitalizeFormDto.getNonsymptom() == true) {
			hosp = Hosp.N;	
			hospitalize = Hospitalize.createHospitalize(patient, getward, hosp, now, doctor);
			hospitalizeRepository.save(hospitalize);
		}else {
			hosp = Hosp.Y;
			hospitalize = Hospitalize.createHospitalize(patient, getward, hosp, now, doctor);
			hospitalizeRepository.save(hospitalize);
			disease = diseaseRepository.findDiseasebyDiseasename(hospitalizeFormDto.getSymptom());
			hospitalizeDisease = HospitalizeDisease.createHospitalize(disease, hospitalize);
			hospitalizeDiseaseRepository.save(hospitalizeDisease);
		}
		return hospitalize.getId();
	}
	
	public String deleteHospitalize(Long patientid) {
		Hospitalize hospitalize = FindHosbymemid(patientid);
		if(hospitalize.getHasdisease() == Hosp.Y) {
			HospitalizeDisease hospitalizeDisease = hospitalizeDiseaseRepository.findHoDisbyHosId(hospitalize.getId());
			hospitalizeDiseaseRepository.delete(hospitalizeDisease);
			hospitalizeRepository.delete(hospitalize);
		}else {
			hospitalizeRepository.delete(hospitalize);
		}
		return "삭제가 완료되었습니다!";
	}
	
	public List<Hospitalize> FindHosListByHosStatus() {
		return hospitalizeRepository.FindHosListByHosStatus();
	}
	
	public Hospitalize FindHosbymemid(Long memberid) {
		return hospitalizeRepository.FindHosbymemid(memberid);
	}
}
