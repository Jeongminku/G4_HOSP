package com.Tingle.G4hosp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.dto.PageSerchDto;
import com.Tingle.G4hosp.dto.ReplyDto;
import com.Tingle.G4hosp.entity.Reply;
import com.Tingle.G4hosp.repository.BoardRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
	private final ReplyRepository replyRepository;
	
	@Transactional
	public List<ReplyDto> viewReply(Long boardId){
		List<Reply> replyList = replyRepository.findByBoardId(boardId);
		
		ArrayList<ReplyDto> replyDtoList = new ArrayList<>();
		
		for(Reply reply : replyList) {
			ReplyDto replydto = ReplyDto.of(reply);
			replyDtoList.add(replydto);
		}
		
		return replyDtoList;
	}
	
	//리플삭제
	@Transactional
	public void delReply(Long replyId) {
		Reply reply = replyRepository.findById(replyId).orElseThrow(EntityNotFoundException:: new);
		replyRepository.delete(reply);
	}
	
	//리플수정
	public void upReply(ReplyDto replyDto) {
		Long replyId = replyDto.getId();
		String content = replyDto.getReplyContent();
		replyRepository.upDateReply(content, replyId);
		
	}
	
	//리플 페이징
	public Page<ReplyDto> getReplyPage(PageSerchDto pageSerchDto,Pageable pageable,Long boardId) {
		return replyRepository.getReplyPage(pageSerchDto, pageable,boardId);
	}
}
