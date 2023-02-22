package com.Tingle.G4hosp.entity;

import java.util.Optional;

import javax.persistence.CascadeType;
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
@Getter
@Setter
@ToString
@Table(name = "archive_disease")
public class ArchiveDisease {
	
	@Id
	@Column(name = "archivedisease_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "archive_id", nullable = false)
	private Archive archive;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disease_id", nullable = false)
	private Disease disease;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospitalize_id", nullable = true)
	private Hospitalize hospitalize;
	
	 public static ArchiveDisease createAD(Archive archive, Disease disease) {
		 ArchiveDisease archiveDisease = new ArchiveDisease();
		 archiveDisease.setArchive(archive);
		 archiveDisease.setDisease(disease); 
		 return archiveDisease;
	 }
	
}
