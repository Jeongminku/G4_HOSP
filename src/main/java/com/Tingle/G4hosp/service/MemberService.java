package com.Tingle.G4hosp.service;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.constant.Hosp;
import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.AdminMemberDto;
import com.Tingle.G4hosp.dto.MemberFormDto;
import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Hospitalize;
import com.Tingle.G4hosp.entity.HospitalizeDisease;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.repository.DiseaseRepository;
import com.Tingle.G4hosp.repository.HospitalizeDiseaseRepository;
import com.Tingle.G4hosp.repository.HospitalizeRepository;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MemberMedRepository;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	 private final PasswordEncoder passwordEncoder;
	 private final MemberRepository memberRepository;
	 private final MemberImgService memberImgService;
	 private final MedRepository medRepository;
	 private final MemberMedRepository memberMedRepository;
	 private final HospitalizeRepository hospitalizeRepository;
	 private final HospitalizeDiseaseRepository hospitalizeDiseaseRepository;

	@Override
	public UserDetails loadUserByUsername(String loginid) throws UsernameNotFoundException {
		Member member = memberRepository.findByLoginid(loginid);
		
		if(member == null) {
			throw new UsernameNotFoundException(loginid);
		}
		return User.builder()
				.username(member.getLoginid())
				.password(member.getPwd())
				.roles(member.getRole().toString())
				.build();
	}
	 
	 public Member saveMember(MemberFormDto memberFormDto, MultipartFile file) throws Exception {
		 Member member = Member.createMember(memberFormDto, passwordEncoder);
		 memberImgService.saveMemberImg(member, file);
		 Member doctor = memberRepository.save(member);
		 if(memberFormDto.getMedId() != null) {
			 Med med = medRepository.findById(memberFormDto.getMedId()).orElseThrow(EntityNotFoundException::new);
			 MemberMed memberMed = MemberMed.createMemberMed(doctor, med);
			 memberMedRepository.save(memberMed);			 
		 }
		 //System.out.println(member);
		 return doctor;
	 }

	 // 관리자 페이지 -> 고객/의사 목록 조회
	 public List<Member> getMemberList(Role role){
		 return memberRepository.findByRole(role);
	 }
	
	 public Member findByLoginid(String loginid) {
		return memberRepository.findByLoginid(loginid);
	}	
	 
	 public Member findByMemberid(Long memid) {
		 return memberRepository.findById(memid).orElseThrow(EntityNotFoundException::new);
	 }
	
	 public Member findByMnameMtel(String memberName, String memberTel) {
		 return memberRepository.findbyMnameandMtel(memberName, memberTel);
	 }
	 
	 public Member findDoctorbydoctorname(String doctorname) {
		 return memberRepository.findbyNameindoctor(doctorname);
	 }
	 
	// find PWD
		 public Member findByPwd(String loginId, String memberName, String memberTel) {
			 return memberRepository.findPwd(loginId, memberName, memberTel);
		 }
		 
		 public String updatenewPwd(MemberFormDto memberFormDto, String loginId, String memberName, String memberTel) {
			 Member member = findByPwd(loginId, memberName, memberTel);
			 String randomPwd = UUID.randomUUID().toString();
			 member.updatePwd(randomPwd, passwordEncoder);
			 return randomPwd;
		 }
	 
	
	//아이디중복체크
		 private void vaildateDuplicateMember(Member member) {
				Member findMember = memberRepository.findByLoginid(member.getLoginid());
				if (findMember != null) {
					throw new IllegalStateException("중복된 아이디입니다.");
				}
			}
			
			@org.springframework.transaction.annotation.Transactional(readOnly = true)
			public Member getMember(String loginid) {
				Member member = memberRepository.findByLoginid(loginid);
				return member;
			}
			
			@org.springframework.transaction.annotation.Transactional(readOnly = true)
			public int vaildateDuplicateId(String loginid) {
				Member member = memberRepository.findByLoginid(loginid);
				int chk;
				
				if (member == null) {
					return chk = 0;
				}
				return chk = 1;
			}
		
		 
		 
	 public void updateMember(MemberFormDto memberFormDto, String loginId, MultipartFile file) {
		 Member member = memberRepository.findByLoginid(loginId);
		 if (member.getRole() == Role.DOCTOR) {
			 Med med = medRepository.findById(memberFormDto.getMedId()).orElseThrow(EntityNotFoundException::new);//med값이 나옴.
			 MemberMed memberMed = memberMedRepository.findByMemberid(member.getId());
			 memberMed.updateMemberMed(med);
			 try {
				memberImgService.saveMemberImg(member, file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		 member.updateMember(memberFormDto, passwordEncoder);
	 }
	 
	 public void updateMember(MemberFormDto memberFormDto, String loginId) {
		 Member member = memberRepository.findByLoginid(loginId);
		 if (member.getRole() == Role.DOCTOR) {
			 Med med = medRepository.findById(memberFormDto.getMedId()).orElseThrow(EntityNotFoundException::new);//med값이 나옴.
			 MemberMed memberMed = memberMedRepository.findByMemberid(member.getId());
			 memberMed.updateMemberMed(med);
		 }
		 member.updateMember(memberFormDto, passwordEncoder);
	 }
	 
	 public MemberFormDto checkARdateandMedname(MemberFormDto memberFormDto, Member member) {
		 Long memid = member.getId();
		 memberRepository.viewpatientArDateandMedname(memberFormDto, memid);
		 return memberFormDto;
	 }
	 
	 public List<Member> findMListbyMname(String memberName){
		 return memberRepository.findMListbyMname(memberName);
	 }
	 
	 public Member findDocbyMid(String doctorid) {
		 return memberRepository.findDocbyMid(doctorid);
	 }
	 
	 public Member findMembymemid(String memid) {
		 return memberRepository.findByLoginid(memid);
	 }
	 
	 public String deleteMember(Long memberId) {
			Member member = memberRepository.getReferenceById(memberId);
			try {
				memberRepository.delete(member);
				return "정상적으로 탈퇴되었습니다.";
			} catch (Exception e) {
				return "탈퇴하지 못하였습니다.";
			}
	}
	 
	 public Member findByName(String name) {
		 return memberRepository.findByName(name);
		 
	 }
	 
	 public Role getMemberRole (String loginId) {
		 return findByLoginid(loginId).getRole();
	 }
	 
	 public List<AdminMemberDto> findMemberList (String opt) {
		 List<AdminMemberDto> adminMemberDtoList = new ArrayList<>();
		 if(StringUtils.equals(opt, "HOSPITALIZE")) {
			 List<Hospitalize> hospitalized = hospitalizeRepository.FindHosListByHosStatus();
			 for(Hospitalize hosp : hospitalized) {
				 HospitalizeDisease hospDisease = null;
				 if(StringUtils.equals(hosp.getHasdisease(), Hosp.Y)) hospDisease = hospitalizeDiseaseRepository.findByHospitalize(hosp);
				 adminMemberDtoList.add(AdminMemberDto.createByHospitalize(hosp, hospDisease));
			 }
			 return adminMemberDtoList;
		 }

		 List<Member> matchedMember = new ArrayList<>();
		 if(StringUtils.equals(opt, "ALL")) matchedMember = memberRepository.findAll();
		 if(StringUtils.equals(opt, "CLIENT")) matchedMember = memberRepository.findByRole(Role.CLIENT);
		 if(StringUtils.equals(opt, "DOCTOR")) {
			 matchedMember = memberRepository.findByRole(Role.DOCTOR);
			 for(Member member : matchedMember) {
				 MemberMed memberMed = memberMedRepository.findByMemberId(member);
				 adminMemberDtoList.add(AdminMemberDto.createByMemberMed(memberMed));
			 }
			 return adminMemberDtoList;
		 }
		 if(StringUtils.equals(opt, "ADMIN")) matchedMember = memberRepository.findByRole(Role.ADMIN);
		 return AdminMemberDto.createByMemberList(matchedMember);
	 }
}
