package com.Tingle.G4hosp.dto;


import com.Tingle.G4hosp.constant.Hosp;
import com.Tingle.G4hosp.constant.Ward;
import com.Tingle.G4hosp.entity.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HospitalizeFormDto {
	
	private String symptom;

	private Boolean nonsymptom;

	private String ward;
	
	private Hosp hosp;
	
	
}
