package com.Tingle.G4hosp.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.HinfoBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HinfoBoardDto {
	
	private Long id; //게시글의 번호
	
	@NotBlank(message = "제목을 입력해 주세요")
	private String Title; // 게시글의 제목
	
	@NotBlank(message = "내용을 입력해 주세요")
	private String content; // 게시글의 내용
	
	private List<HinfoImgDto> hinfoImgDtoList = new ArrayList<>(); //게시글의 이미지가 담길 리스트
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public HinfoBoard createHinfoBoard() {
		return modelMapper.map(this, HinfoBoard.class);
	}
	
	public static HinfoBoardDto of(HinfoBoard hinfoBoard) {
		return modelMapper.map(hinfoBoard, HinfoBoardDto.class);
	}
}
