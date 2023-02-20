package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="board")
public class Board {
	
	@Id
	@Column(name = "board_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String boardTitle;
	
	private String boardContent;
	
	//Member fk
	@JoinColumn(name = "member_nm")
	@ManyToOne
	private Member member;
	
}
