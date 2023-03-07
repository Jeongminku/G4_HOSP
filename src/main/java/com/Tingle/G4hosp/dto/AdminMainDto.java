package com.Tingle.G4hosp.dto;


import java.util.List;

import com.Tingle.G4hosp.entity.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMainDto {

	private Long doctorcount;
	
	private Long patientcount;
	
	private Long hospitalizecount;
	
	private List<String> HosptalizedEachMedname;
	private List<Long> HosptalizedEachMed;
		
	private List<String> HosptalizedEachWardname;
	private List<Long> HosptalizedEachWard;
	
//	검색용
	private String searchDocMedName;
	private Long searchDocId;
	private String searchDocName;
	private String searchDocImgUrl;
	private String searchDocImgOri;
	private Boolean[] searchDocNotAvail;
}
