package com.Tingle.G4hosp.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Tingle.G4hosp.dto.HinfoBoardDto;
import com.Tingle.G4hosp.dto.HinfoImgDto;
import com.Tingle.G4hosp.dto.HinfoListDto;
import com.Tingle.G4hosp.dto.HinfoSerchDto;
import com.Tingle.G4hosp.entity.HinfoBoard;
import com.Tingle.G4hosp.entity.HinfoImg;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.repository.HinfoBoardRepository;
import com.Tingle.G4hosp.repository.HinfoImgRepository;
import com.Tingle.G4hosp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HinfoBoardService {

	private final HinfoBoardRepository hinfoBoardRepository;
	private final HinfoImgRepository hinfoImgRepository;
	private final HinfoImgService hinfoImgService;
	private final MemberRepository memberRepository;
	
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
	public void saveHinfoContent(HinfoBoardDto hinfoBoardDto,List<MultipartFile> hinfoImgFileList,Principal principal) throws Exception {	
		Member member = memberRepository.findByLoginid(principal.getName());	 
		HinfoBoard hinfoBoard = hinfoBoardDto.createHinfoBoard();
		hinfoBoard.setMember(member);
		hinfoBoardRepository.save(hinfoBoard); 
		saveHinfoImg(hinfoImgFileList,hinfoBoard);
		
	
	}
	
	//건강정보게시판의 게시글 뿌려줌
	public Page<HinfoListDto> getHinfoMain(HinfoSerchDto hinfoSerchDto,Pageable pageable){
		return hinfoBoardRepository.getMainHinfoMain(hinfoSerchDto,pageable);
	}
	
	//건강정보게시판의 게시글 조회수 증가
	@Transactional
	public void updateViewtest(Long HinfoId) {
		//3.조회수 증가
		hinfoBoardRepository.updateView(HinfoId);
	}
	
	//건강정보게시판 게시글 확인
	@Transactional
	public HinfoBoardDto getHinfoDtl(Long HinfoId) {
		//1. 의학정보게시판테이블의 이미지를 가져온다
		List<HinfoImg> hinfoImgList = hinfoImgRepository.findByHinfoBoardIdOrderByIdAsc(HinfoId);
		List<HinfoImgDto> HinfoImgDtoList = new ArrayList<>();
		
		//2. 엔티티를 dto에 맵핑
		for(HinfoImg hinfoImg : hinfoImgList) {
			HinfoImgDto hinfoImgDto = HinfoImgDto.of(hinfoImg);
			HinfoImgDtoList.add(hinfoImgDto);
		}
		
		
		//3. 의학정보게시판의 id를 이용해서 엔티티에 데이터를 저장
		HinfoBoard hinfoBoard = hinfoBoardRepository.findById(HinfoId)
			.orElseThrow(EntityNotFoundException::new);
		
		
		//4. 엔티티 객체 - > dto객체로 변환
		HinfoBoardDto hinfoBoardDto = HinfoBoardDto.of(hinfoBoard);
		
		
		//5. 게시글의 이미지 정보를 넣어준다.
		hinfoBoardDto.setHinfoImgDtoList(HinfoImgDtoList);
		
		return hinfoBoardDto;
	}
	
	//의학정보게시판 게시글 수정
	@Transactional
	public Long HinfoUpdate(Long hinfoId,List<MultipartFile> hinFoImgFileList,HinfoBoardDto hinfoBoardDto) throws Exception {
	  HinfoBoard hinfoBoard = hinfoBoardRepository.findById(hinfoId).orElseThrow(EntityNotFoundException::new);
	
	  hinfoBoard.updateHinfo(hinfoBoardDto.getContent(), hinfoBoardDto.getTitle());
	  
	  List<HinfoImg> hinfoImg = hinfoImgRepository.findByHinfoBoardIdOrderByIdAsc(hinfoId);
	  List<Long> hinfoImgIds = new ArrayList<>();
	  
	  for(int i = 0; i < hinfoImg.size(); i++) {
		  hinfoImgIds.add(hinfoImg.get(i).getId());
	  }
	  
	  for(int i = 0; i < hinFoImgFileList.size(); i++) {
		  hinfoImgService.updateHinfoImg(hinfoImgIds.get(i), hinFoImgFileList.get(i));
	  }
	  
	  return hinfoBoard.getId();
	}
	
	//의학정보게시판 게시글 삭제
	@Transactional
	public void HinfoDelete(Long hinfoId) {
		
		List<HinfoImg> hinfoImgList = hinfoImgRepository.findByHinfoBoardIdOrderByIdAsc(hinfoId);
		for(HinfoImg hinfoImg : hinfoImgList) {
			hinfoImgRepository.delete(hinfoImg);
		}
		
		HinfoBoard hinfoboard = hinfoBoardRepository
				.findById(hinfoId)
				.orElseThrow(EntityNotFoundException::new);
		
		hinfoBoardRepository.delete(hinfoboard);
	}
}
