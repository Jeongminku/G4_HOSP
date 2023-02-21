package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import com.Tingle.G4hosp.constant.QaCategory;
import com.Tingle.G4hosp.dto.QaBoardDto;

import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name="qa_board")
public class QaBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "qa_id")
	private Long id;
	
	@Column(name="qa_title")
	private String qaTitle; //자주묻는질문 게시판제목
	
	@Column(name="qa_content")
	private String qaContent;
	
	@Enumerated(EnumType.STRING)
	@Column(name="qa_category")
	private QaCategory qaCategory;
	
	@JoinColumn(name = "member_nm")
	@ManyToOne
	private Member member;
	
	public static QaBoard createQaBoard (QaBoardDto qaBoardDto, Member member) {
		
		QaBoard qaBoard = new QaBoard();
		qaBoard.setQaCategory(qaBoardDto.getCategory());
		qaBoard.setQaTitle(qaBoardDto.getTitle());
		qaBoard.setQaContent(qaBoardDto.getContent());
		
		qaBoard.setMember(member);
		
		return qaBoard;
	}
	
	
	//Test용
	public static QaBoard createQaTest (QaBoardDto qaBoardDto) {
		
		QaBoard qaBoard = new QaBoard();
		qaBoard.setQaCategory(qaBoardDto.getCategory());
		qaBoard.setQaTitle(qaBoardDto.getTitle());
		qaBoard.setQaContent(qaBoardDto.getContent());
		
		return qaBoard;
	}

	public void modifyQaBoard(QaBoardDto qaBoardDto) {
		this.qaTitle = qaBoardDto.getTitle();
		this.qaContent = qaBoardDto.getContent();
		this.qaCategory = qaBoardDto.getCategory();
	}
}
