package com.Tingle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table (name = "archive")

public class Archive {

	@Id
	@Column(name = "archive_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String detail;
	
	// MEMBER entity 넣어야 함
	
	// Archive 선언시 disease 테이블 연결 필요.
	
	
}
