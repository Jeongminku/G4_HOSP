package com.Tingle.G4hosp.service;

import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberImgService {
	@Value("${itemImgLocation}")
	private String imgLocation;
	
	private final FileService fileService;
	private final MemberRepository memberRepository;
	
	public void saveMemberImg(Member member, MultipartFile imgfile) throws Exception {
		String imgOri = imgfile.getOriginalFilename();
		String imgName = "default";
//		String imgUrl = "/images/item/b45699ae-8cc7-468b-9ce2-6c9ea5c99e8a.png";
		String imgUrl = "/img/userdefault_l.jpg";
		if(!StringUtils.isEmpty(imgOri)) {
			imgName = fileService.uploadFile(imgLocation, imgOri, imgfile.getBytes());
			imgUrl = "/images/item/" + imgName;
		} else {
			imgOri = "NoImg";
		}
		member.updateImg(imgName, imgUrl, imgOri);
	}
}
