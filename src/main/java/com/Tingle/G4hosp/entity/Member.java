package com.Tingle.G4hosp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

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
	@Column(name="member_nm")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //회원번호
	
	@Column(nullable = false, unique = true, name="member_id")
	private String memberid; //로그인시 사용할 아이디
	
	@Column(nullable = false, name="member_name")
	private String name; //이름

	@Column(nullable = false, name="member_pwd")
	private String pwd; //비밀번호 

	@Column(nullable = false, name="member_tel")
	private String tel; //전화번호 

	@Column(nullable = false, name="member_birth")
	private String birth;  //생년월일 

	@Enumerated(EnumType.STRING)
	private Role role;  //가입유저타입 

	@Column(nullable = false, name="memberimg_name")
	private String imgname; //이미지원본파일명  
	
	@Column(nullable = false, name="memberimg_ori")
	private String imgori; //이미지파일명 
	
	@Column(nullable = false, name="memberimg_url")
	private String imgurl; //이미지URL
	
	public static Member createUser(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		
		member.setMemberid(memberFormDto.getMemberid());
		member.setName(memberFormDto.getName());
		String pwd = passwordEncoder.encode(memberFormDto.getPwd());
		member.setPwd(pwd);
		member.setTel(memberFormDto.getTel());;
		member.setBirth(memberFormDto.getBirth());
		member.setRole(Role.USER);
		member.setImgname(memberFormDto.getImgname());
		member.setImgori(memberFormDto.getImgori());
		member.setImgurl(memberFormDto.getImgurl());
		
		return member;
		
		
	}
}
