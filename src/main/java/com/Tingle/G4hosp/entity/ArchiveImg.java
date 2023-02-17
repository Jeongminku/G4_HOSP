package com.Tingle.G4hosp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table (name = "archiveimg")

public class ArchiveImg {
	
	@Id
	@Column(name = "archiveimg_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String imgname;
	
	private String imgori;
	
	private String imgurl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "archive_id", nullable = false)
	private Archive archive;
	
	
	public void updateArchiveImg(String imgname, String imgori, String imgurl) {
		this.imgname = imgname;
		this.imgori = imgori;
		this.imgurl = imgurl;
	}
	
}
