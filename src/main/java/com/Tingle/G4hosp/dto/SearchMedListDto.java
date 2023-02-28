package com.Tingle.G4hosp.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMedListDto {

	private String medName;
	
	@QueryProjection
	public SearchMedListDto(String medName) {
		this.medName = medName;
	}
}
