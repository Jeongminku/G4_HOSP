package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.Tingle.G4hosp.dto.SearchInputDto;
import com.Tingle.G4hosp.dto.SearchDocListDto;
import com.Tingle.G4hosp.dto.SearchMedListDto;

public interface SearchDocRepositoryCustom {

	List<SearchDocListDto> getDocList(SearchInputDto searchDocDto);
	
	List<SearchMedListDto> getMedList(SearchInputDto searchDocDto);
}
