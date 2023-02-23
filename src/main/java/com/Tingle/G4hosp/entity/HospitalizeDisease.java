
package com.Tingle.G4hosp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.Tingle.G4hosp.constant.Hosp;
import com.Tingle.G4hosp.constant.Ward;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "hospitalize_disease")
public class HospitalizeDisease {

	@Id
	@Column(name = "hospitalizedisease_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disease_id", nullable = false)
	private Disease disease;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospitalize_id", nullable = false)
	private Hospitalize hospitalize;

	public static HospitalizeDisease createHospitalize(Disease disease, Hospitalize hospitalize) {
		HospitalizeDisease hosd = new HospitalizeDisease();
		hosd.setDisease(disease);
		hosd.setHospitalize(hospitalize);
		return hosd;
	}
	
}
