package com.Tingle.G4hosp.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.HinfoBoard;
import com.Tingle.G4hosp.entity.Member;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HinfoListDto {

	private Long id;
	
	private Member member;

	private String title;
	
	private int view;
	
	private LocalDateTime regTime; //게시글 등록시간
	
	private String content;
	
	private HinfoBoardDto hinfoBoardDto;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public HinfoBoard HinfoListview(HinfoBoard hinfoBoard) {
		return modelMapper.map(hinfoBoard, HinfoBoard.class);
	}
	
	public static HinfoListDto of(HinfoBoard hinfoBoard) {
		return modelMapper.map(hinfoBoard, HinfoListDto.class);
	}
	
	@QueryProjection
	public HinfoListDto(Long id,Member member,String title,int view,LocalDateTime regTime, String content) {
		this.id =id;
		this.member = member;
		this.title = title;
		this.view = view;
		this.regTime = regTime;
		this.content = content;
	}
}
