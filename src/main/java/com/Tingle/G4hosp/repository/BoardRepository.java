package com.Tingle.G4hosp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.dto.BoardFormDto;
import com.Tingle.G4hosp.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustum{
	
	@Query(value = "update from board set board.board_content_view = board.board_content_view + 1 where board.member_id = :Id", nativeQuery = true)
	BoardFormDto updateViewQ(@Param("Id") Long Id);
	
	@Transactional
	@Modifying
	@Query(value = "Update board set board_content = :content , board_title = :title where board_id = :id",nativeQuery = true)
	int upDateBoard(@Param("content") String content, @Param("id") Long id, @Param("title") String title);
}
