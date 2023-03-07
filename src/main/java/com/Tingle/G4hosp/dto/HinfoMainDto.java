package com.Tingle.G4hosp.dto;

import java.time.LocalDateTime;

import com.Tingle.G4hosp.entity.Member;

import lombok.*;


@Getter
@Setter
@ToString
public class HinfoMainDto {
	
	private Long id;
	
	private Member member;

	private String title;
	
	private int view;
	
	private LocalDateTime regTime; //게시글 등록시간
}
