package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.HinfoImg;

public interface HinfoImgRepository extends JpaRepository<HinfoImg, Long>{
	List<HinfoImg> findByHinfoBoardIdOrderByIdAsc(Long HinfoId);

}
