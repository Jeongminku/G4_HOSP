package com.Tingle.G4hosp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplyRepositoryCustom{
	List<Reply> findByBoardId(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "Update Reply set reply_content = :content  where reply_id = :id",nativeQuery = true)
	int upDateReply(@Param("content") String content,@Param("id") Long id);
	
    


}
