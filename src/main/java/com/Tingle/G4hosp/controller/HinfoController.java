package com.Tingle.G4hosp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.dto.HinfoBoardDto;
import com.Tingle.G4hosp.dto.HinfoListDto;
import com.Tingle.G4hosp.dto.HinfoSerchDto;
import com.Tingle.G4hosp.service.HinfoBoardService;

import javassist.expr.NewArray;
import lombok.RequiredArgsConstructor;

@RequestMapping("/Hinfo")
@Controller
@RequiredArgsConstructor
public class HinfoController {
	
	private final HinfoBoardService hinfoBoardService;
	
	//메인페이지를 보여줌
	@GetMapping(value = "/HinfoMain")
	public String viewHinfoList(HinfoSerchDto hinfoSerchDto,Optional<Integer> page,Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 6);
		Page<HinfoListDto> list = hinfoBoardService.getHinfoMain(hinfoSerchDto,pageable);

				
		model.addAttribute("lists", list);
		model.addAttribute("maxPage",5);
		model.addAttribute("hinfoSerchDto", hinfoSerchDto);
		
		return "HinfoPage/hinfoMain";
		
	}
	
	//글쓰기 페이지 입장
	@GetMapping(value = "/write")
	public String mainview(Model model) {
		model.addAttribute("hinfoBoardDto",new HinfoBoardDto());
		return "HinfoPage/HinfoForm";
	}
	
	
	//글쓰기 완료
	@PostMapping(value = "/Hinfo")
	public String saveContent(@Valid HinfoBoardDto hinfoBoardDto  , BindingResult bindingResult ,@RequestParam("HinfoImg") List<MultipartFile> hinfoImg
			,Principal principal,Model model) {
		
		if(bindingResult.hasErrors()) {
			return "HinfoPage/HinfoForm";
		}
		
		try {
		hinfoBoardService.saveHinfoContent(hinfoBoardDto, hinfoImg,principal);

		} catch (Exception e) {
			System.out.println("확인해보자");
		}
		return "redirect:/Hinfo/HinfoMain"; //경로 수정필요합니다.
	}
	
	//게시글 상세조회 그리고 조회수증가
	@GetMapping(value={"/{hinfoId}"})
	public String hinfoview(Model model, @PathVariable("hinfoId") Long hinfoIdId,HttpServletRequest request, HttpServletResponse response) {
		
		 Cookie oldCookie = null;
		 Cookie[] cookies = request.getCookies();
		 
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if (cookie.getName().equals("postView")) {
		                oldCookie = cookie;
		            }
		        }
		    }

		    if (oldCookie != null) {
		        if (!oldCookie.getValue().contains("[" + hinfoIdId.toString() + "]")) {
		        	hinfoBoardService.updateViewtest(hinfoIdId);
		            oldCookie.setValue(oldCookie.getValue() + "_[" + hinfoIdId + "]");
		            oldCookie.setPath("/");
		            oldCookie.setMaxAge(60 * 60 * 24);
		            response.addCookie(oldCookie);
		        }
		    } else {
		    	hinfoBoardService.updateViewtest(hinfoIdId);
		        Cookie newCookie = new Cookie("postView","[" + hinfoIdId + "]");
		        newCookie.setPath("/");
		        newCookie.setMaxAge(60 * 60 * 24);
		        response.addCookie(newCookie);
		    }// 쿠키를 이용한 게시글 중복조회 방지 
		
		
		HinfoBoardDto hinfoBoardDto = hinfoBoardService.getHinfoDtl(hinfoIdId);
		model.addAttribute("HinfoBoard",hinfoBoardDto);
			
		
		return "HinfoPage/hinfoDtl";
	}
	
	//의학정보게시판의 글내용 수정 페이지로 넘겨줌
	@GetMapping("/updatepage/{hinfoId}")
	public String HinfoUpdateForm(@PathVariable("hinfoId") Long hinfoIdId,Model model) {
		HinfoBoardDto hinfoBoardDto = hinfoBoardService.getHinfoDtl(hinfoIdId);
		model.addAttribute("hinfoBoardDto",hinfoBoardDto);
		return "HinfoPage/HinfoForm";
	}
	
	//의학정보게시판 글내용 수정
	@PostMapping("/updatepage/{hinfoId}")
	public String HinfoUpdate(@PathVariable("hinfoId") Long hinfoId,@Valid HinfoBoardDto hinfoBoardDto,@RequestParam("HinfoImg") List<MultipartFile> itemImgFileList,BindingResult bindingResult,Model model) {

		try {
			 hinfoBoardService.HinfoUpdate(hinfoId,itemImgFileList, hinfoBoardDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  "redirect:/Hinfo/{hinfoId}";
	}
	
	@GetMapping("/deletepage/{hinfoId}")
	public String HinfoDelete(@PathVariable("hinfoId") Long hinfoId,HinfoSerchDto hinfoSerchDto,Optional<Integer> page,Model model) {

		hinfoBoardService.HinfoDelete(hinfoId);
		
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 6);
		Page<HinfoListDto> list = hinfoBoardService.getHinfoMain(hinfoSerchDto,pageable);

				
		model.addAttribute("lists", list);
		model.addAttribute("maxPage",5);
		model.addAttribute("hinfoSerchDto", hinfoSerchDto);
		
		return "HinfoPage/hinfoMain";
	}
}
