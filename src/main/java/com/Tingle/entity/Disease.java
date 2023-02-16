package com.Tingle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="disease") 
@Getter
@Setter
@ToString
public class Disease {

	@Id
	@Column(name = "disease_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long diseaseId;
	
	private String diseaseName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "med_id")
	private Med med;
}
