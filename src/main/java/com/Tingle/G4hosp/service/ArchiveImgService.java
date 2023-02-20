package com.Tingle.G4hosp.service;

import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.entity.Archive;
import com.Tingle.G4hosp.entity.ArchiveImg;
import com.Tingle.G4hosp.repository.ArchiveImgRepository;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArchiveImgService {

	@Value("${itemImgLocation}")
	private String ImgLocation;
	private final FileService fileService;
	private final ArchiveImgRepository archiveImgRepository;
	
	// SAVE ARCHIVE IMG	
	public void saveArchiveImg(ArchiveImg archiveimg, MultipartFile file) throws Exception {	
		String oriImgName = file.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(ImgLocation, oriImgName, file.getBytes());
			imgUrl = "/images/item" + imgName;
		}
		archiveimg.updateArchiveImg(oriImgName, imgName, imgUrl);
		archiveImgRepository.save(archiveimg);
	}
	
	
}
