package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.HinfoBoard;

public interface HinfoBoardRepository extends JpaRepository<HinfoBoard, Long>{
	//건강정보게시판 Repository
}
