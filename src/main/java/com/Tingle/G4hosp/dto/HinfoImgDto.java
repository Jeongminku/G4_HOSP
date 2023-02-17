package com.Tingle.G4hosp.dto;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.HinfoImg;

import lombok.*;

@Getter
@Setter
public class HinfoImgDto {
	
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static HinfoImgDto of(HinfoImg hinfoImg) {//dto와 entity 맵핑
		return modelMapper.map(hinfoImg, HinfoImgDto.class);
	}
}
