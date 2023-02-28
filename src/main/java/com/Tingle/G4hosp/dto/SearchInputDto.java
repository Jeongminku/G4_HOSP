package com.Tingle.G4hosp.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SearchInputDto {
	private String searchBy;
	private String searchQuery = "";
}
