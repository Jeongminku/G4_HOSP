package com.Tingle.G4hosp.entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Entity
@Table(name="membermed")
@Getter
@Setter
@ToString
public class MemberMed {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name="member_id")
	@OneToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Member memberId;
	
	@JoinColumn(name = "med_id")
	@ManyToOne
	private Med medId;
	
	public static MemberMed createMemberMed (Member doctor, Med med) {
		MemberMed memberMed = new MemberMed();
		memberMed.setMemberId(doctor);
		memberMed.setMedId(med);
		return memberMed;
	}
	
	public void updateMemberMed(Med medId) {
		this.medId = medId;
	}
}
