package com.Tingle.G4hosp.service;

import java.util.*;

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
	private final ReservationNotAvailableService reservationNotAvailableService;
	
	public List<SearchDocListDto> getDocList(SearchInputDto searchDocDto) {
		return searchDocRepository.getDocList(searchDocDto);
	}
	
	public List<SearchMedListDto> getMedlist(SearchInputDto searchDocDto) {
		return searchDocRepository.getMedList(searchDocDto);
	}
	
	public List<AdminMainDto> getDoctoryBySearch(SearchInputDto searchInputDto){
		List<Member> searchDocList = memberRepository.getdoctorbysearch(searchInputDto.getSearchQuery());
		List<AdminMainDto> searchDocDtoList = new ArrayList<>();
		for(Member member : searchDocList) {
			AdminMainDto adminMainDto = new AdminMainDto();
			adminMainDto.setSearchDocId(member.getId());
			adminMainDto.setSearchDocImgOri(member.getImgOri());
			adminMainDto.setSearchDocImgUrl(member.getImgUrl());
			adminMainDto.setSearchDocName(member.getName());
			
			adminMainDto.setSearchDocMedName(findMedByDocid(member.getId()).getMedName());
			
			adminMainDto.setSearchDocNotAvail(reservationNotAvailableService.notAvailByDoctorTF(member));
			searchDocDtoList.add(adminMainDto);
		}
		return searchDocDtoList;
	}
	
	public Med findMedByDocid(Long doctorId) {
		return medRepository.findMedbyDocid(doctorId);
	}
}
