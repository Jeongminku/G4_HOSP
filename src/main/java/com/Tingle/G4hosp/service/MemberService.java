package com.Tingle.G4hosp.service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	
	 private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		
		Member member = memberRepository.findByMemberId(memberId);
		
		if(member == null) {
		 throw new UsernameNotFoundException(memberId);	
		}
			
			
		return User.builder()
				   .username(member.getMemberid())
				   .password(member.getPwd())
				   .roles(member.getRole().toString())
					.build();
	
	}

	
	public Member saveMember(Member member) {
		return memberRepository.save(member);
	}
		
		
	
	 
	 
}
