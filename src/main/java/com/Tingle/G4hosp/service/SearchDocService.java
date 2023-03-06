package com.Tingle.G4hosp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.dto.SearchInputDto;
import com.Tingle.G4hosp.dto.AdminMainDto;
import com.Tingle.G4hosp.dto.SearchDocListDto;
import com.Tingle.G4hosp.dto.SearchMedListDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.repository.SearchDocRepository;
import com.Tingle.G4hosp.repository.SearchDocRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchDocService {
	private final SearchDocRepository searchDocRepository;
	private final MedRepository medRepository;
	private final MemberRepository memberRepository;
	
	public List<SearchDocListDto> getDocList(SearchInputDto searchDocDto) {
		return searchDocRepository.getDocList(searchDocDto);
	}
	
	public List<SearchMedListDto> getMedlist(SearchInputDto searchDocDto) {
		return searchDocRepository.getMedList(searchDocDto);
	}
	
	public List<Member> getDoctoryBySearch(String searchQuery){
		return memberRepository.getdoctorbysearch(searchQuery);
	}
	
	public Med findMedByDocid(Long doctorId) {
		return medRepository.findMedbyDocid(doctorId);
	}
}
