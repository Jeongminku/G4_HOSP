package com.Tingle.G4hosp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessorOrder;

import com.Tingle.G4hosp.dto.ArchiveFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table (name = "archive")

public class Archive extends BaseTime{

	@Id
	@Column(name = "archive_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String detail;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	private Member member;
	
	@Column(name = "doctor_name")
	private String doctorname;

	public static Archive createArchive(Member doctor, Member patient, ArchiveFormDto archiveFormDto) {
		Archive archive = new Archive();
		archive.setDetail(archiveFormDto.getDetail());
		archive.setMember(patient);
		archive.setDoctorname(doctor.getName());
		return archive;
	}
	
	// UPDATE ARCHIVE (ARCHIVE DETAIL, DOCTOR)
	public void updateArchive(Member doctor, ArchiveFormDto archiveFormDto) {
		this.detail = archiveFormDto.getDetail();
		this.doctorname = doctor.getName();
	}
	
}
