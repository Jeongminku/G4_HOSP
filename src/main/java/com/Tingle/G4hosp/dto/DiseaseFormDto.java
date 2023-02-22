package com.Tingle.G4hosp.dto;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Med;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 병명 입력 페이지 보여주는 dto
@Getter
@Setter
@Data
public class DiseaseFormDto {

	private Long diseaseId;
	
//	@NotBlank(message = "병 코드는 필수 입력값입니다.")
//	private String diseaseCode; //ID
	
	@NotBlank(message="이름은 필수 입력값입니다.")
	private String diseaseName; //이름 
	
	private Med med;
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public Disease createDisease() {
		return modelMapper.map(this, Disease.class);
	}
	
}
