package com.Tingle.G4hosp.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Tingle.G4hosp.dto.AdminMainDto;
import com.Tingle.G4hosp.dto.SearchInputDto;
import com.Tingle.G4hosp.service.SearchDocService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final SearchDocService searchDocService;
	
	@GetMapping(value = "/")
	public String main(Model model) {
		SearchInputDto searchInputDto = new SearchInputDto();
		model.addAttribute("searchInputDto", searchInputDto);
		return "main";
	}
	
	
	@GetMapping("/searchresult")
	public String searchresult(SearchInputDto searchInputDto, Model model){
		List<AdminMainDto> adminMainDtolist = searchDocService.getDoctoryBySearch(searchInputDto);
		model.addAttribute("docList", adminMainDtolist);
		model.addAttribute("searchABC", searchInputDto.getSearchQuery());
		model.addAttribute("searchSize", adminMainDtolist.size());
		
		return "searchPage/SearchResult"; 
	}
	
}
