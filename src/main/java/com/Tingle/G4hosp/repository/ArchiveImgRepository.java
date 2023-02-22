package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.ArchiveImg;

public interface ArchiveImgRepository extends JpaRepository<ArchiveImg, Long> {

	@Query(value = "select * from archiveimg where archiveimg.archiveimg_id = :ARCIMGID ",nativeQuery = true)
	List<ArchiveImg> findbyid(@Param("ARCIMGID") Long archiveimgid);
	
	@Query(value = "select * from archiveimg where archiveimg.archive_id = :ARCID ",nativeQuery = true)
	List<ArchiveImg> findbyARCid(@Param("ARCID") Long archiveid);
	
	
}
