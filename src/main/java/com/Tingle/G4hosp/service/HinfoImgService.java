package com.Tingle.G4hosp.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.entity.HinfoBoard;
import com.Tingle.G4hosp.entity.HinfoImg;
import com.Tingle.G4hosp.repository.HinfoImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HinfoImgService {
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	
	private final HinfoImgRepository hinfoImgRepository;
	
	private final FileService fileService;
	
	//이미지 저장 메소드
	public void saveImg(HinfoImg hinfoImg,MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		hinfoImg.updateHinfoImg(oriImgName, imgName, imgUrl);
		hinfoImgRepository.save(hinfoImg);
	}
	
	//이미지 업데이트 메소드
	public void updateHinfoImg(Long HinfoId, MultipartFile hinfoImgFile) throws Exception{
		
		if(!hinfoImgFile.isEmpty()) { //파일이 있으면
			HinfoImg savedHinfoImg = hinfoImgRepository.findById(HinfoId)
					.orElseThrow(EntityNotFoundException::new);
			// 기존 이미지 파일 삭제
			if(!StringUtils.isEmpty(savedHinfoImg.getImgname())) {
				fileService.deleteFile(itemImgLocation + "/" + savedHinfoImg.getImgname());
			}
			//수정된 이미지 파일 업로드
			String oriImgName = hinfoImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, hinfoImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			savedHinfoImg.updateHinfoImg(oriImgName, imgName, imgUrl);
		}		
	}
}
