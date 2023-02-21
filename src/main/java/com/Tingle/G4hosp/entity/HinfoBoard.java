package com.Tingle.G4hosp.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import lombok.*;


@Getter
@Setter
@ToString
@Entity
@Table(name="hinfo_Board")
public class HinfoBoard extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hinfo_id")
	private Long id;
	
	@Column(name="hinfo_title")
	private String Title;
	
	@Column(name="hinfo_content")
	private String Content;
	
	@Column(name="hinfo_content_view")
	@ColumnDefault("0")
	private int view;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_nm")
	private Member member;
	
	
	public void updateHinfo(String content,String title) {
		this.Content = content;
		this.Title  = title;
	}
}
