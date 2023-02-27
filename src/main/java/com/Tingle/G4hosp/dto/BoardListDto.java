package com.Tingle.G4hosp.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.Board;
import com.Tingle.G4hosp.entity.Member;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardListDto {
	
	private Long id;
	
	private Member member;
	
	private String title;
	
	private int view;
	
	private LocalDateTime regTime; //게시글 등록 시간
	
	
	//쿼리dsl사용
	@QueryProjection
	public BoardListDto(Long id, Member member, String title, int view, LocalDateTime regTime) {
		this.id = id;
		this.member = member;
		this.title = title;
		this.view = view;
		this.regTime = regTime;
	}
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public static BoardListDto of(Board board) {
		return modelMapper.map(board, BoardListDto.class);
	}
}
