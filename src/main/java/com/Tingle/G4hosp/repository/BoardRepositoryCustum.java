package com.Tingle.G4hosp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Tingle.G4hosp.dto.BoardListDto;
import com.Tingle.G4hosp.dto.BoardSerchDto;

public interface BoardRepositoryCustum {
	//게시판의 뿌려줄 게시글을 가져옴, 페이징
	Page<BoardListDto> getMainBoard(BoardSerchDto boardSerchDto,Pageable pagealble);
	
	//게시글 조회수 증가
	long updateView(Long id);
}
