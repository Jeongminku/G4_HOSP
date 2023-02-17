package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import lombok.*;


@Getter
@Setter
@ToString
@Entity
@Table(name="hinfo_Board")
public class HinfoBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hinfo_id")
	private Long id;
	
	@Column(name="hinfo_title")
	private String Title;
	
	@Column(name="hinfo_content")
	private String Content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_nm")
	private Member member;
}
