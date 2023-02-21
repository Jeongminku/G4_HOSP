package com.Tingle.G4hosp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Tingle.G4hosp.dto.HinfoBoardDto;
import com.Tingle.G4hosp.dto.HinfoListDto;
import com.Tingle.G4hosp.dto.HinfoSerchDto;
import com.Tingle.G4hosp.entity.HinfoBoard;

public interface HinfoBoardRepositoryCustum {
	//건강정보게시판에 뿌려줄 게시글들
	Page<HinfoListDto> getMainHinfoMain(HinfoSerchDto hinfoSerchDto,Pageable pageable);
	
	
	//조회수 증가 테스트2
	long updateViewtest(Long HinfoId);
	
	long updateHinfo(Long hinfoId,HinfoBoardDto hinfoBoardDto);

}
