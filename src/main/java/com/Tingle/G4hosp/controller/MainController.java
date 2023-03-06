package com.Tingle.G4hosp.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.BoardListDto;
import com.Tingle.G4hosp.dto.HinfoMainDto;
import com.Tingle.G4hosp.dto.SearchDocListDto;
import com.Tingle.G4hosp.dto.SearchInputDto;
import com.Tingle.G4hosp.dto.SearchMedListDto;
import com.Tingle.G4hosp.entity.Board;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.SearchDocRepository;
import com.Tingle.G4hosp.service.BoardService;
import com.Tingle.G4hosp.service.HinfoBoardService;
import com.Tingle.G4hosp.service.MedService;
import com.Tingle.G4hosp.service.MemberMedService;
import com.Tingle.G4hosp.service.MemberService;
import com.Tingle.G4hosp.service.SearchDocService;

import javassist.expr.NewArray;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final SearchDocService searchDocService;
	private final MemberMedService memberMedService;
	private final MemberService memberService;
	private final MedService medService;
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
	
	@GetMapping(value ="/searchresult")
	public String searchresult(Model model, SearchInputDto searchInputDto) {
		 String searchABC = searchInputDto.getSearchQuery();
		 model.addAttribute("searchABC",searchABC);
		 List<SearchDocListDto> docList = searchDocService.getDocList(searchInputDto);
		 
		 for(SearchDocListDto Doctorlist : docList) {
			 Long loginid = memberService.findByName(Doctorlist.getDocName()).getId();
			 //System.err.println(memberMedService.findMemberMed(loginid));
			 String medName = memberMedService.findMemberMed(loginid).getMedId().getMedName();
			 System.err.println("@@@@@@@@@@string test  : "+ medName);
			 Doctorlist.setDocMed(medName);
		 }
		 
		 //System.err.println(memlist); //의사인 사람의 이름으로 member객체를 집어온 List값.
		 model.addAttribute("docList", docList);
		 System.err.println("docList의 객체: " + docList);
		 //System.err.println("닥메드 : " + docList.get(0).getDocMed());
		 List<SearchMedListDto> medList = searchDocService.getMedlist(searchInputDto);
		 model.addAttribute("medList", medList);
		 
		 
		 Integer searchSize = docList.size() + medList.size();
		 Integer searchDocSize = docList.size();
		 Integer searchMedSize = medList.size();
		 model.addAttribute("searchSize", searchSize);
		 model.addAttribute("searchDocSize", searchDocSize);
		 model.addAttribute("searchMedSize", searchMedSize);
		 
		return "searchPage/SearchResult";
	}
}
