package com.Tingle.G4hosp.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.Board;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.Reply;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
public class ReplyDto {
	
	private Long id;
	
	private String replyContent;
	
	private LocalDateTime regTime;
	

	private Board board; //게시글의 pk
	
	private Member member; // 회원의 pk
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ReplyDto of(Reply reply) {
		return modelMapper.map(reply, ReplyDto.class);
	}
	
	
	
	
	
	
}