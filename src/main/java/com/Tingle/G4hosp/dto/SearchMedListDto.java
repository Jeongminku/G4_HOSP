package com.Tingle.G4hosp.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMedListDto {

	private String medName;
	
	private String medInfo;
	
	private Long medId;
	
	@QueryProjection
	public SearchMedListDto(String medName, String medInfo, Long medId) {
		this.medName = medName;
		this.medInfo = medInfo;
		this.medId = medId;
	}
}
