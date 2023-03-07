package com.Tingle.G4hosp.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.Tingle.G4hosp.controller.MemberCheckMethod;
import com.Tingle.G4hosp.dto.BoardFormDto;
import com.Tingle.G4hosp.dto.BoardListDto;
import com.Tingle.G4hosp.dto.BoardSerchDto;
import com.Tingle.G4hosp.dto.ReplyDto;
import com.Tingle.G4hosp.dto.ReplyJsonDto;
import com.Tingle.G4hosp.entity.Board;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.Reply;
import com.Tingle.G4hosp.repository.BoardRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final ReplyRepository replyRepository;
	
	
	//게시판에 글을 저장하는 메소드
//	public Long saveBoardForm(BoardFormDto boardFormDto,Principal principal) {
//		//게시글을 작성한 멤버엔티티 생성
//		Member member = memberRepository.findByLoginid(principal.getName());
//		//Dto로 엔티티 생성
//		System.out.println(boardFormDto);
//		Board board = boardFormDto.createBoard();
//		
//		board.setMember(member);
//		
//		boardRepository.save(board);
//		
//		return board.getId();
//	}
	
	public Long saveBoardForm(BoardFormDto boardFormDto,Principal principal) {
		//게시글을 작성한 멤버엔티티 생성
		Member member = memberRepository.findByLoginid(principal.getName());
		//Dto로 엔티티 생성
		System.out.println(boardFormDto);
		Board board = Board.createBoard(boardFormDto);
		
		board.setMember(member);
		
		boardRepository.save(board);
		
		return board.getId();
	}
	
	//게시판에 게시글을 뿌려줌
	public Page<BoardListDto> getBoardMain(BoardSerchDto boardserchDto
			, Pageable pageable){
		Page<BoardListDto> result = boardRepository.getMainBoard(boardserchDto, pageable);
		
		//리플곗수 
		result.forEach(dto -> {
			Long count = replyRepository.countByBoardId(dto.getId());
			dto.setReplyCount(count);
		});
		return result;
	}
	
	//게시글조회수 업데이트
	@Transactional
	public void updateview(Long boardId) {
		boardRepository.updateView(boardId);
	}
	
	//게시글 내용확인
	@Transactional
	public BoardFormDto getBoardDtl(Long boardId) {
		//아이디로 찾은 데이터 entity에 담기
		Board board = boardRepository.findById(boardId)
						.orElseThrow(EntityNotFoundException::new);
		
		BoardFormDto boardFormDto = BoardFormDto.of(board);
		
		return boardFormDto;
	}
	
	//게시글 저장 메소드
	@Transactional
	public void saveReply(ReplyJsonDto replyJsonDto,Principal principal) {
		Long BoardId = replyJsonDto.getBoard(); // 게시판의 id를 가져온다
		
		Member member = memberRepository.findByLoginid(principal.getName());
		//현재 게시글을 작성한 멤버의 아이디를 가져온다.
		
		
		Board board = boardRepository.findById(BoardId)
				.orElseThrow(EntityNotFoundException::new);
		
		Reply reply = new Reply();
		
		reply.createReply(replyJsonDto.getReplyContent(), board, member);
		//리플저장
		replyRepository.save(reply);
	}
	
	//게시글삭제
	@Transactional
	public String delBoard(Long boardId , HttpServletResponse resp, Authentication authentication, Model model) {
		Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
		
		if(authentication == null) {
			return MemberCheckMethod.redirectAfterAlert("게시글 삭제권한이 없습니다 로그인을 해주세요.",   "/members/login"  , resp);
		}
		
		
		if(!board.getMember().getLoginid().equals(authentication.getName())
			   	&& !authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")
		    	&& !authentication.getAuthorities().toString().equals("[ROLE_DOCTOR]")) {
			return MemberCheckMethod.redirectAfterAlert("게시글 삭제권한이 없습니다.",   "/board/" + board.getId() , resp);
		}
		
		 boardRepository.delete(board);
		 
		 return board.getMember().getLoginid();
	}
	
	//수정할 게시판정보를 넘겨줌
	@Transactional
	public BoardFormDto getboardDto(Long boardId) {
		Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
		BoardFormDto boardFormDto = BoardFormDto.of(board);
		return boardFormDto;
	}
	//게시글수정
	public int upDateBoard(BoardFormDto boardFormDto) {
		int succes = boardRepository.upDateBoard(boardFormDto.getContent(), boardFormDto.getId(), boardFormDto.getTitle());
		return succes;
	}
	
	//메인페이지에 게시글을 뿌려줌
	public List<BoardListDto> getMainBoard() {
		List<Board> boardList = boardRepository.mainViewBoard();
		List<BoardListDto> boardListdtoList = new ArrayList<>();
		
		for(int i = 0; i < boardList.size(); i++) {
			BoardListDto boardListdto = new BoardListDto();
			boardListdto.setId(boardList.get(i).getId());
			boardListdto.setMember(boardList.get(i).getMember());
			boardListdto.setRegTime(boardList.get(i).getRegDatetime());
			boardListdto.setView(boardList.get(i).getView());
			boardListdto.setTitle(boardList.get(i).getTitle());
			
			boardListdtoList.add(boardListdto);
		}

		return boardListdtoList;
	}
}
