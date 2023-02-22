package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="board")
public class Board extends BaseEntity{
	
	@Id
	@Column(name = "board_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "board_title")
	private String title;
	
	@Column(name= "board_content")
	private String content;
	
	@Column(name="board_content_view")
	@ColumnDefault("0")
	private int view;
	
	//Member fk
	@JoinColumn(name = "member_id")
	@ManyToOne
	private Member member;
		
	
}
