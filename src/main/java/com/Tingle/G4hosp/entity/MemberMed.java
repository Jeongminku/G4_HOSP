package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name="membermed")
@Getter
@Setter
@ToString
public class MemberMed {
	
	@JoinColumn(name="member_id")
	@OneToOne
	private Member id;
	
	@JoinColumn(name = "med_id")
	@ManyToOne
	private Med medId;
}
