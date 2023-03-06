package com.Tingle.G4hosp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tingle.G4hosp.entity.HinfoBoard;

public interface HinfoBoardRepository extends JpaRepository<HinfoBoard, Long>,HinfoBoardRepositoryCustum{
	//건강정보게시판 Repository
	
	@Transactional
	@Query(value = "select * from hinfo_board order by hinfo_id desc limit 4" , nativeQuery = true)
	List<HinfoBoard> getHinfoBoardList();
	
}
