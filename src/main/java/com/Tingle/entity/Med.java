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
@Table(name="med") 
@Getter
@Setter
@ToString
public class Med {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "med_id")
	private Long medId;
	
	private String medName;
	
	private String medInfo;
	
}
