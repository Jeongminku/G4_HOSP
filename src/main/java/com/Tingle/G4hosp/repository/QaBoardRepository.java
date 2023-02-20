package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.QaBoard;

public interface QaBoardRepository extends JpaRepository<QaBoard, Long> {
	//QA게시판
	
}
