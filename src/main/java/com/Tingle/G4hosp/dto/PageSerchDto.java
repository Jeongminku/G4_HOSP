package com.Tingle.G4hosp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSerchDto {
	private String searchDateType;
	private String searchBy;
	private String searchQuery = "";
}
