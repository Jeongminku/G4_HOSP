package com.Tingle.G4hosp.Service;

import java.io.IOException;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

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
			imgUrl = "/images/item" + imgName;
		}
		hinfoImg.updateHinfoImg(oriImgName, imgName, imgUrl);
		hinfoImgRepository.save(hinfoImg);
		//hinfoImgRepository.save(hinfoImg);
	}
}
