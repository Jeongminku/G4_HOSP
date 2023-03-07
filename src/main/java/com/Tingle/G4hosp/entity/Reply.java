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
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Reply")
public class Reply extends BaseEntity{
	
	@Id
	@Column(name = "reply_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String replyContent;
	
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="board_id")
	@JsonIgnore
	private Board board;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Member member;
	
	public void createComment(String replyContent,Board board,Member member) {
		this.replyContent = replyContent;
		this.board = board;
		this.member = member;
	}
	

}
