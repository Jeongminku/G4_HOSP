package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.constant.QaCategory;
import com.Tingle.G4hosp.entity.QaBoard;

public interface QaBoardRepository extends JpaRepository<QaBoard, Long> {
	//QA게시판
	List<QaBoard> findByQaCategory(QaCategory qaCategory);
	
	@Query(value = "select * from qa_board where qa_board.qa_category = :category",nativeQuery = true)
	List<QaBoard> findQAFromCAT(@Param("category") String category);
	
}
