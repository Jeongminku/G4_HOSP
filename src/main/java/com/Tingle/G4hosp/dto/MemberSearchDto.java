package com.Tingle.G4hosp.dto;

import com.Tingle.G4hosp.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchDto {

	private Role role;
	private String searchBy;
	private String searchQuery = "";
	
}
