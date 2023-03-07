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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;


@Getter
@Setter
@ToString
@Entity
@Table(name="hinfo_img")
public class HinfoImg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="hinfoimg_id")
	private Long id;
	
	private String imgname;
	
	private String imgUrl;
	
	private String oriImgName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hinfo_id")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private HinfoBoard hinfoBoard;
	
	//원본이미지 파일명 , 업데이트할 이미지 파일명 , 이미지 경로를 파라메터로 받아서 이미지 정보를 업데이트 하는 메소드.
	public void updateHinfoImg(String oriImgName,String imgname,String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgUrl = imgUrl;
		this.imgname = imgname;
	}
}
