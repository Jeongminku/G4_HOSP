package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.Tingle.G4hosp.constant.BoardSecret;
import com.Tingle.G4hosp.dto.BoardFormDto;

import lombok.*;

@DynamicInsert
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
	
	@Enumerated(EnumType.STRING)
	private BoardSecret secret;
	
	//Member fk
	@JoinColumn(name = "member_id")
	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Member member;
	
	public static Board createBoard(BoardFormDto boardFormDto) { 
		Board board = new Board();
		
		board.setTitle(boardFormDto.getTitle());
		board.setContent(boardFormDto.getContent());
		
		if(boardFormDto.getSecret() == null) {
			board.setSecret(BoardSecret.False);
		}else {
			board.setSecret(boardFormDto.getSecret());
		}
		
		return board;
	
	}

	
	
}
