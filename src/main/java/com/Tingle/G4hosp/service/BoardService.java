package com.Tingle.G4hosp.service;

import java.security.Principal;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.dto.BoardFormDto;
import com.Tingle.G4hosp.dto.BoardListDto;
import com.Tingle.G4hosp.dto.BoardSerchDto;
import com.Tingle.G4hosp.entity.Board;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.BoardRepository;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	
	//게시판에 글을 저장하는 메소드
	public void saveBoardForm(BoardFormDto boardFormDto,Principal principal) {
		//게시글을 작성한 멤버엔티티 생성
		Member member = memberRepository.findByLoginid(principal.getName());
		//Dto로 엔티티 생성
		Board board = boardFormDto.createBoard();
		board.setMember(member);
		
		boardRepository.save(board);
	}
	
	//게시판에 게시글을 뿌려줌
	public Page<BoardListDto> getBoardMain(BoardSerchDto boardserchDto
			, Pageable pageable){
		return boardRepository.getMainBoard(boardserchDto, pageable);
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
}
