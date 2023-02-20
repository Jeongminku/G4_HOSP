package com.Tingle.G4hosp.dto;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.Med;

import lombok.Getter;
import lombok.Setter;

//진료과 입력 페이지 보여주는 dto
@Getter
@Setter
public class MedFormDto {

	@NotBlank(message = "진료과 이름은 필수 입력값입니다.")
	private String medName; //ID
	
	@NotBlank(message="진료과 설명은 필수 입력값입니다.")
	private String medInfo; //이름 
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public Med createMed() {
		return modelMapper.map(this, Med.class);
	}
	
}
