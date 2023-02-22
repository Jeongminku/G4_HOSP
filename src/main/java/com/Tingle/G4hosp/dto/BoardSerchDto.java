package com.Tingle.G4hosp.dto;

import lombok.*;

@Getter
@Setter
public class BoardSerchDto {
	private String searchDateType;
	private String searchBy;
	private String searchQuery = "";
}
