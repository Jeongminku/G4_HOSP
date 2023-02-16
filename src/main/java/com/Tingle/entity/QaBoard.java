package com.Tingle.entity;

import javax.persistence.*;

import com.Tingle.constant.QaCategory;

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
}
