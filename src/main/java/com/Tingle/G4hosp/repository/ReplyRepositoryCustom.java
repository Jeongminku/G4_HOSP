package com.Tingle.G4hosp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Tingle.G4hosp.dto.PageSerchDto;
import com.Tingle.G4hosp.dto.ReplyDto;

public interface ReplyRepositoryCustom {
	
	//댓글의 페이징
	Page<ReplyDto> getReplyPage(PageSerchDto pageSerchDto,Pageable pageable, Long BoardId);
}
