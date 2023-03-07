package com.Tingle.G4hosp.dto;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.entity.Member;

import lombok.Data;

@Data
public class AdminMemberDto {
	private Long id;
	private String memberName;
	private String memberBirth;
	private String memberTel;
	private String memberRole;
	private String memberRegDate;
	
	private String memberHospDate;
	private String memberHospDocName;
	private String memberHospDiseName;
	private String memberHospMedName;
	private String memberHospRoom;
	
	public static List<AdminMemberDto> createMemberDtoList (List<Member> memberList, Boolean isHosp) {
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
		}
		return null;
	}
}
