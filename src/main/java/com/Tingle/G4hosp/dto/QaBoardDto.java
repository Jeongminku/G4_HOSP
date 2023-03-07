package com.Tingle.G4hosp.dto;

import com.Tingle.G4hosp.constant.QaCategory;
import com.Tingle.G4hosp.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QaBoardDto {
	
	private Long id;
	
	private String title;
	
	private String content;
	
	//QA 카테고리 enum
	private QaCategory Category;
	
}
