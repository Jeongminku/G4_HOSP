package com.Tingle.G4hosp.dto;

import com.Tingle.G4hosp.entity.Disease;
import com.Tingle.G4hosp.entity.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArchiveFormDto {

	
	private Disease disease;
	
	private String doctorname;
	
	private String detail;
	
	
	
}
