package com.Tingle.G4hosp.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.entity.HinfoBoard;
import com.Tingle.G4hosp.entity.HinfoImg;
import com.Tingle.G4hosp.repository.HinfoBoardRepository;
import com.Tingle.G4hosp.repository.HinfoImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HinfoBoardService {

	private final HinfoBoardRepository hinfoBoardRepository;
	private final HinfoImgRepository hinfoImgRepository;
	private final HinfoImgService hinfoImgService;
	
	//건강정보게시판의 이미지 저장 
	public Long saveHinfoImg(List<MultipartFile> hinfoImgFileList, HinfoBoard hinfoBoard) throws Exception {
		for(int i = 0; i < hinfoImgFileList.size(); i++) {
			HinfoImg hinfoImg = new HinfoImg();
			hinfoImg.setHinfoBoard(hinfoBoard); //외래키 등록
			hinfoImgService.saveImg(hinfoImg, hinfoImgFileList.get(i));
		}
		return hinfoBoard.getId();
	}
	
	//건강정보게시판의 게시글 저장
	public void saveHinfoContent(HinfoBoard hinfoboard,List<MultipartFile> hinfoImgFileList) throws Exception {
		hinfoBoardRepository.save(hinfoboard); // git stash
		saveHinfoImg(hinfoImgFileList,hinfoboard);
	}
}
