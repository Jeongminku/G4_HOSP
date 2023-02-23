package com.Tingle.G4hosp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.MemberFormDto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "member")
@Getter
@Setter
@ToString
public class Member {

	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //회원번호
	
	@Column(nullable = false, unique = true, name="member_loginid")
	private String loginid; //로그인시 사용할 아이디
	
	@Column(nullable = false)
	private String name; //이름

	@Column(nullable = false)
	private String pwd; //비밀번호 

	@Column(nullable = false)
	private String tel; //전화번호 

	@Column(nullable = false)
	private String birth;  //생년월일 

	@Enumerated(EnumType.STRING)
	private Role role;  //가입유저타입 

	@Column(nullable = false, name="img_name")
	private String imgName; //이미지원본파일명  
	
	@Column(nullable = false, name="img_ori")
	private String imgOri; //이미지파일명 
	
	@Column(nullable = false, name="img_url")
	private String imgUrl; //이미지URL
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		
		member.setLoginid(memberFormDto.getLoginid());
		String pwd = passwordEncoder.encode(memberFormDto.getPwd());
		member.setPwd(pwd);
		member.setName(memberFormDto.getName());
		member.setTel(memberFormDto.getTel());;
		member.setBirth(memberFormDto.getBirth());
		member.setRole(memberFormDto.getRole());
		
		return member;
	}
	
	public void updateImg(String imgName, String imgUrl, String imgOri) {
		this.imgName = imgName;
		this.imgUrl = imgUrl;
		this.imgOri = imgOri;
	}
	
	public void updateMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		if(!StringUtils.equals(memberFormDto.getPwd(), "")) { //빈값이면 True -> ! false
			String pwd = passwordEncoder.encode(memberFormDto.getPwd());
			this.pwd = pwd;			
		}
		if(memberFormDto.getTel() != null) {
			this.tel = memberFormDto.getTel();			
		}
	}
}