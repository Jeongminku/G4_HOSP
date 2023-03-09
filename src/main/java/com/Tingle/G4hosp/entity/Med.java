package com.Tingle.G4hosp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.Tingle.G4hosp.dto.MedFormDto;

import lombok.*;

@Entity
@Table (name = "med")
@Getter
@Setter
@ToString
public class Med {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "med_id")
	private Long medId;

	@Column(name = "med_name")
	private String medName;
	
	@Column(name = "med_info")
	private String medInfo;
	
//	public void editMed(MedFormDto medFormDto) {
//		this.medId = medFormDto.getMedId();
//		this.medName = medFormDto.getMedName();
//		this.medInfo = medFormDto.getMedInfo();
//	}
}
