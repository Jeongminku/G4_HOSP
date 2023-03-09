package com.Tingle.G4hosp.dto;

import java.time.LocalDate;
import java.util.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@lombok.ToString
public class MemberFormDto {
	@NotBlank(message = "아이디는 필수 입력값입니다.")
	@Pattern(regexp="^[A-Za-z0-9-_]{3,10}$", message="아이디는 3자 이상, 10이하의 한글,숫자로만 입력해주세요.")
	private String loginid; //ID
	
	@NotBlank(message="이름은 필수 입력값입니다.")
	private String name; //이름 
	
	@NotBlank(message="비밀번호는 필수 입력값입니다.")
	@Length(min=6, max=12, message = " 비밀번호는 6자 이상, 12자 이하로 입력해주세요") 
	private String pwd; //비밀번호 
	
	@NotBlank(message="전화번호는 필수 입력값입니다.")
	@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "[01012345678] 형식의 전화번호로 입력해주세요.")
	private String tel;
	
	@NotBlank(message="생년월일은 필수 입력값입니다. [19990909]형식의 8자리로 입력해주세요.")
	private String birth;
	
	private Role role;
	
	private String imgName;
	
	private String imgOri;
	
	private String imgUrl;

	private List<Med> med;
	
	private Long medId;
	
	// 마이페이지 접속시 회원 내원일자, 내원 과 조회
	private List<String> archivedate;
	private List<String> medname;
 	private Map<String, String> archive;
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public Member createMember() {
		return modelMapper.map(this, Member.class);
	}
}
