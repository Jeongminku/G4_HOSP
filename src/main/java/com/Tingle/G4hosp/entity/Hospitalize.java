
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
@Table(name = "hospitalize")
public class Hospitalize {

	@Id
	@Column(name = "hospitalize_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	private String ward;
	
	@Enumerated(EnumType.STRING)
	private Hosp hospYN;

	@Enumerated(EnumType.STRING)
	private Hosp hasdisease;
	
	//입원날짜
	private String hospitalizeddate;
	
	public static Hospitalize createHospitalize(Member member, String ward, Hosp hosp, String nowdate) {
		Hospitalize hos = new Hospitalize();
		hos.setMember(member);
		hos.setWard(ward);
		hos.setHospYN(Hosp.Y);
		hos.setHasdisease(hosp);
		hos.setHospitalizeddate(nowdate);
		return hos;
	}
	
}
