package com.Tingle.G4hosp.dto;

import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDocListDto {

	
	private String docName;
	
	private String docMed;
	
	private String docImg;
	
	private String docImgUrl;
	
	@QueryProjection
	public SearchDocListDto(String docName, String docImg, String docImgUrl) {
		this.docName = docName;
		this.docImg = docImg;
		this.docImgUrl = docImgUrl;
	}
}
