package com.Tingle.G4hosp.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.Tingle.G4hosp.dto.BoardListDto;
import com.Tingle.G4hosp.dto.HinfoMainDto;
import com.Tingle.G4hosp.dto.AdminMainDto;
import com.Tingle.G4hosp.dto.SearchInputDto;
import com.Tingle.G4hosp.service.BoardService;
import com.Tingle.G4hosp.service.HinfoBoardService;
import com.Tingle.G4hosp.service.MedService;
import com.Tingle.G4hosp.service.MemberMedService;
import com.Tingle.G4hosp.service.MemberService;
import com.Tingle.G4hosp.service.SearchDocService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final SearchDocService searchDocService;
	private final BoardService boardService;
	private final HinfoBoardService hinfoBoardService;
	
	@GetMapping(value = "/")
	public String main(Model model) {
		SearchInputDto searchInputDto = new SearchInputDto();
		
		List<BoardListDto> boardList = boardService.getMainBoard();
		System.err.println(boardList);
		List<HinfoMainDto> hinfoList = hinfoBoardService.getMainHinfoview();
		System.err.println(hinfoList);
		
		model.addAttribute("boardList" , boardList);
		model.addAttribute("hinfoList" , hinfoList);
		model.addAttribute("searchInputDto", searchInputDto);
		return "main";
	}
	
	
	@GetMapping("/searchresult")
	public String searchresult(SearchInputDto searchInputDto, Model model){
		List<AdminMainDto> adminMainDtolist = searchDocService.getDoctoryBySearch(searchInputDto);
		model.addAttribute("docList", adminMainDtolist);
		model.addAttribute("searchABC", searchInputDto.getSearchQuery());
		model.addAttribute("searchSize", adminMainDtolist.size());
		
		return "searchPage/searchResult"; 
	}
	
	
}
