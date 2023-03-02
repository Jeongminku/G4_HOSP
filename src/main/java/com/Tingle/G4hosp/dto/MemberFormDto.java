package com.Tingle.G4hosp.dto;

import java.time.LocalDate;
import java.util.List;

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
	private String loginid; //ID
	
	@NotBlank(message="이름은 필수 입력값입니다.")
	private String name; //이름 
	
	@NotBlank(message="비밀번호는 필수 입력값입니다.")
	//@Length(min=6, max=12, message = " 비밀번호는 6자 이상, 12자 이하로 입력해주세요") 
	private String pwd; //비밀번호 
	
	@NotBlank(message="전화번호는 필수 입력값입니다.")
	private String tel;
	
	@NotBlank(message="생년월일은 필수 입력값입니다. [19990909]형식의 8자리로 입력해주세요.")
	//@Pattern(regexp = "/^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/", message="생년월일은 [19990909]형식의 8자리로 입력해주세요.")
	private String birth;
	
	private Role role;
	
	private String imgName;
	
	private String imgOri;
	
	private String imgUrl;

	private List<Med> med;
	
	private Long medId;
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public Member createMember() {
		return modelMapper.map(this, Member.class);
	}
}
