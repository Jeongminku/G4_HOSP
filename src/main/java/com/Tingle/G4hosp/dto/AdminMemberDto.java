package com.Tingle.G4hosp.dto;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.entity.Hospitalize;
import com.Tingle.G4hosp.entity.HospitalizeDisease;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;

import lombok.Data;

@Data
public class AdminMemberDto {
	private Long id;
	private String memberName;
	private String memberBirth;
	private String memberTel;
	private String memberRole;
	private String memberRegDate;
	
	private String memberMedName;
	
	private String memberHospDate;
	private String memberHospDocName;
	private String memberHospDiseName;
	private String memberHospMedName;
	private String memberHospRoom;
	
	public static List<AdminMemberDto> createByMemberList (List<Member> memberList) {
		List<AdminMemberDto> adminMemberDtoList = new ArrayList<>();
		for(Member member : memberList) {
			AdminMemberDto adminMemberDto = new AdminMemberDto();
			adminMemberDto.setId(member.getId());
			adminMemberDto.setMemberName(member.getName());
			adminMemberDto.setMemberBirth(member.getBirth());
			adminMemberDto.setMemberTel(member.getTel());
			if(StringUtils.equals(member.getRole(), Role.CLIENT)) adminMemberDto.setMemberRole("일반회원");
			if(StringUtils.equals(member.getRole(), Role.DOCTOR)) adminMemberDto.setMemberRole("의사");
			if(StringUtils.equals(member.getRole(), Role.ADMIN)) adminMemberDto.setMemberRole("관리자");				
			adminMemberDto.setMemberRegDate(member.getRegDatetime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
			adminMemberDtoList.add(adminMemberDto);
		}
		return adminMemberDtoList;
	}
	
	public static AdminMemberDto createByMemberMed (MemberMed memberMed) {
		AdminMemberDto adminMemberDto = new AdminMemberDto();
		adminMemberDto.setId(memberMed.getMemberId().getId());
		adminMemberDto.setMemberName(memberMed.getMemberId().getName());
		adminMemberDto.setMemberBirth(memberMed.getMemberId().getBirth());
		adminMemberDto.setMemberTel(memberMed.getMemberId().getTel());
		adminMemberDto.setMemberRole("의사");
		adminMemberDto.setMemberMedName(memberMed.getMedId().getMedName());
		adminMemberDto.setMemberRegDate(memberMed.getMemberId().getRegDatetime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
		return adminMemberDto;
	}
	
	public static AdminMemberDto createByHospitalize (Hospitalize hospitalize, HospitalizeDisease hospitalizeDisease) {
		AdminMemberDto adminMemberDto = new AdminMemberDto();
		adminMemberDto.setId(hospitalize.getMember().getId());
		adminMemberDto.setMemberName(hospitalize.getMember().getName());
		adminMemberDto.setMemberHospDate(hospitalize.getHospitalizeddate());
		adminMemberDto.setMemberHospDocName(hospitalize.getDoctor().getName());
		if(hospitalizeDisease == null) {
			adminMemberDto.setMemberHospDiseName("-");
			adminMemberDto.setMemberHospMedName("-");				
		} else {
			adminMemberDto.setMemberHospDiseName(hospitalizeDisease.getDisease().getDiseaseName());
			adminMemberDto.setMemberHospMedName(hospitalizeDisease.getDisease().getMed().getMedName());			
		}
		adminMemberDto.setMemberHospRoom(hospitalize.getWard());
		return adminMemberDto;
	}
}
