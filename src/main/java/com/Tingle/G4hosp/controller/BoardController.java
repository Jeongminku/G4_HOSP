package com.Tingle.G4hosp.controller;


import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Tingle.G4hosp.constant.BoardSecret;
import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.BoardFormDto;
import com.Tingle.G4hosp.dto.BoardListDto;
import com.Tingle.G4hosp.dto.BoardSerchDto;
import com.Tingle.G4hosp.dto.PageSerchDto;
import com.Tingle.G4hosp.dto.ReplyDto;
import com.Tingle.G4hosp.dto.ReplyJsonDto;
import com.Tingle.G4hosp.entity.Board;
import com.Tingle.G4hosp.service.BoardService;
import com.Tingle.G4hosp.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardSerivce;
	private final ReplyService replyService;
	
	//현재 환자정보게시판의 메인화면을 보여줍니다.
	@GetMapping(value = "/main")
	public String boardView(BoardSerchDto boardserchDto, Optional<Integer> page, Model model, HttpServletRequest req) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 5);
		Page<BoardListDto> list = boardSerivce.getBoardMain(boardserchDto, pageable);
		
		
		model.addAttribute("lists", list);
		model.addAttribute("maxPage", 5);
		model.addAttribute("boardserchDto" , boardserchDto);
		
		if(req.getAttribute("isAdmin") != null) model.addAttribute("isAdmin", true);
		
		return "boardPage/boardMain";
	}
	
	//글쓰기 페이지로 이동
	@GetMapping(value ="/write")
	public String boardForm(Model model, HttpServletRequest req) {
		model.addAttribute("boardFormDto",new BoardFormDto());
		if(req.getAttribute("isAdmin") != null) model.addAttribute("isAdmin", true);
		return "boardPage/boardForm";
	}
	
	//게시글 등록
	@PostMapping(value = "/boardsave")
	public String saveBoardForm(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,Principal pricipal,Model model, HttpServletRequest req) {
		
		if(bindingResult.hasErrors()) {
			return "boardPage/boardForm";
		}
		
		try {
			 boardSerivce.saveBoardForm(boardFormDto, pricipal);	
		} catch (Exception e) {
			model.addAttribute("errorMessage","로그인을 해주세요");
			return "boardPage/boardForm";
		}
		if(req.getAttribute("isAdmin") != null) return "redirect:/admin/board/main";
		return "redirect:/board/main";
	}
	
	//게시글 상세 보기
	@GetMapping(value = "/{boardId}")
	public String boardview(Model model, @PathVariable("boardId") Long boardId,
			HttpServletRequest request, HttpServletResponse response ,
			PageSerchDto pageSerchDto,Optional<Integer> page,
			Authentication authentication) {
		    
		    
		
		//쿠키를 통한 게시글 조회수 중복 카운트 방지
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
		    		
		        if (!oldCookie.getValue().contains("[" + boardId.toString() + "]")) {
		    
		        	boardSerivce.updateview(boardId);
		            oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
		            oldCookie.setPath("/");
		            oldCookie.setMaxAge(60 * 60 * 24);
		            response.addCookie(oldCookie);
		        }
		    } else {
		 
		    	boardSerivce.updateview(boardId);
		        Cookie newCookie = new Cookie("postView","[" + boardId + "]");
		        newCookie.setPath("/");
		        newCookie.setMaxAge(60 * 60 * 24);
		        response.addCookie(newCookie);
		    }
		    
		    
		    BoardFormDto boardFormDto = boardSerivce.getBoardDtl(boardId);
		    //List<ReplyDto> ReplyDtoList = replyService.viewReply(boardId);
		    //model.addAttribute("ReplyDtoList" ,ReplyDtoList);
		    
		    if(boardFormDto.getSecret().equals(BoardSecret.True) && authentication == null) {
		    	return MemberCheckMethod.redirectAfterAlert("비밀글입니다 로그인을 해주세요.",  "/members/login" , response);
		    }
		    
		    if(boardFormDto.getSecret().equals(BoardSecret.True)
		    	&&	!boardFormDto.getMember().getLoginid().equals(authentication.getName())
		    	&& !authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")
		    	&& !authentication.getAuthorities().toString().equals("[ROLE_DOCTOR]")
		    	&& authentication.getAuthorities() != null) {
		    	
		    	return MemberCheckMethod.redirectAfterAlert("비밀글입니다.",  "/board/main" , response);
		    }
		    
	
		    
		    
			Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 5);
			Page<ReplyDto> list = replyService.getReplyPage(pageSerchDto, pageable,boardId);
			
			model.addAttribute("lists" , list);
			model.addAttribute("pageSerchDto" , pageSerchDto);
			model.addAttribute("maxPage", 5);
		    
		    model.addAttribute("boardFormDto",boardFormDto);

		    if(request.getAttribute("isAdmin") != null)  model.addAttribute("isAdmin", true);
		    return "boardPage/boardDtl";
	}
	
	//댓글 저장
	@PostMapping(value = "saveReply")
	@ResponseBody public ResponseEntity saveReply(@RequestBody ReplyJsonDto replyJsonDto,Principal principal) {
		System.err.println(replyJsonDto);
		boardSerivce.saveReply(replyJsonDto, principal);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	//테스트 댓글 목록 보내기
	@RequestMapping(value = "/test/{boardId}"  , method = { RequestMethod.POST })
	@ResponseBody List<ReplyDto> test(@PathVariable("boardId") Long boardId) {
		  List<ReplyDto> ReplyDtoList = replyService.viewReply(boardId);
		  
		  return ReplyDtoList;
	}

	
	//페이지 리로딩
	@PostMapping("/reload/{boardId}")
	public String replacePost (@PathVariable("boardId") Long boardId , Model model,PageSerchDto pageSerchDto,Optional<Integer> page) {
		
		BoardFormDto boardFormDto = boardSerivce.getBoardDtl(boardId);
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 5);
		Page<ReplyDto> list = replyService.getReplyPage(pageSerchDto, pageable,boardId);
		
		model.addAttribute("lists" , list);
		model.addAttribute("pageSerchDto" , pageSerchDto);
		model.addAttribute("maxPage", 5);
		model.addAttribute("boardFormDto",boardFormDto);
		 
		return "boardPage/boardDtl :: reply";
	}
	
	//댓글삭제
	@PostMapping(value = "/replydel")
	@ResponseBody public ResponseEntity delReply(@RequestBody ReplyDto replyDto) {

		replyService.delReply(replyDto.getId());
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping(value = "/upRe")
	@ResponseBody public ResponseEntity upReply(@RequestBody ReplyDto replyDto) {

		replyService.upReply(replyDto);
		return new ResponseEntity(HttpStatus.OK);
	}
	
//	//테스트 페이징
//	@RequestMapping(value = "/page/{boardId}"  , method = { RequestMethod.POST })
//	@ResponseBody Page<ReplyDto> repaging(PageSerchDto pageSerchDto,Optional<Integer> page,Model model) {
//		
//		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 10);
//		Page<ReplyDto> list = replyService.getReplyPage(pageSerchDto, pageable);
//		model.addAttribute("pageSerchDto" , pageSerchDto);
//		model.addAttribute("h", "h");
//		System.out.println(list.getContent()+"?");
//		return list;
//	}
	
	//게시글 삭제
	@GetMapping("/delBoard/{boardId}")
	public String deleteBoard(@PathVariable("boardId") Long boardId,BoardSerchDto boardserchDto,Optional<Integer> page,Model model,
			HttpServletRequest req, HttpServletResponse resp, Authentication authentication) {	
		
		BoardFormDto boardFormDto	= boardSerivce.getboardDto(boardId);

		
		if(authentication == null) {
			return MemberCheckMethod.redirectAfterAlert("게시글 삭제권한이 없습니다 로그인을 해주세요.",   "/members/login"  , resp);
		}
		
		if(!boardFormDto.getMember().getLoginid().equals(authentication.getName())
			   	&& !authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")
		    	&& !authentication.getAuthorities().toString().equals("[ROLE_DOCTOR]")) {
			return MemberCheckMethod.redirectAfterAlert("게시글 삭제권한이 없습니다.",   "/board/" + boardId , resp);
		}
		
		boardSerivce.delBoard(boardId, resp, authentication, model);
		
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 5);
		
		
		Page<BoardListDto> list = boardSerivce.getBoardMain(boardserchDto, pageable);
		model.addAttribute("lists", list);
		model.addAttribute("maxPage", 5);
		model.addAttribute("boardserchDto" , boardserchDto);
		if(req.getAttribute("isAdmin") != null) return MemberCheckMethod.redirectAfterAlert("게시글을 삭제했습니다.",  "/admin/board/main" , resp);
		return MemberCheckMethod.redirectAfterAlert("게시글을 삭제했습니다.",  "/board/main" , resp);
	}
	
	//게시글 수정페이지로 가기
	@GetMapping(value = "/upDateForm/{boardId}")
	public String boardUpdateForm(@PathVariable("boardId") Long boardId,Model model,
			Authentication authentication, HttpServletRequest req, HttpServletResponse resp) {
		
		BoardFormDto boardFormDto	= boardSerivce.getboardDto(boardId);
		
		if(authentication == null) {
			return MemberCheckMethod.redirectAfterAlert("게시글 수정권한이 없습니다 로그인을 해주세요.",   "/members/login"  , resp);
		}
		
		
		if(!boardFormDto.getMember().getLoginid().equals(authentication.getName())
			   	&& !authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")
		    	&& !authentication.getAuthorities().toString().equals("[ROLE_DOCTOR]")) {
			return MemberCheckMethod.redirectAfterAlert("게시글 수정권한이 없습니다.",   "/board/" + boardFormDto.getId() , resp);
		}
		
		model.addAttribute("boardFormDto", boardFormDto);
		if(req.getAttribute("isAdmin") != null) model.addAttribute("isAdmin", true);
		return "boardPage/boardForm";
	}
	
	//게시글 수정하기
	@PostMapping(value = "/updateB")
	public String boardUpdate(@Valid BoardFormDto boardFormDto,BindingResult bindingResult,Model model,HttpServletResponse resp, HttpServletRequest req ) {
	
		if(bindingResult.hasErrors()) {
			return "boardPage/boardForm";
		}
		
		try {
			boardSerivce.upDateBoard(boardFormDto);
			
		} catch (Exception e) {
			
			return MemberCheckMethod.redirectAfterAlert("게시글을 수정을 실패 했습니다.",  "/board/" + boardFormDto.getId() , resp);
		}
		if(req.getAttribute("isAdmin") != null) return MemberCheckMethod.redirectAfterAlert("게시글을 수정했습니다.",  "/admin/board/" + boardFormDto.getId() , resp);
		return MemberCheckMethod.redirectAfterAlert("게시글을 수정했습니다.",  "/board/" + boardFormDto.getId() , resp);
	}
}
