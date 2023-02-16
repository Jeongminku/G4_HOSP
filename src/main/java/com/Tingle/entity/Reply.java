package com.Tingle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Reply")
public class Reply {
	
	@Id
	@Column(name = "reply_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String replyContent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Board board;
}
