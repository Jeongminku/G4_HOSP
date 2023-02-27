package com.Tingle.G4hosp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.Tingle.G4hosp.dto.QuickReservationDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table (name = "quickreservation")
public class QuickReservation {
	
	@Id
	@Column(name = "qr_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "qr_name")
	private String cusname;
	
	@Column(name = "qr_tel")	
	private String custel;
	
	@Column(name = "qr_time")
	private String requesttime;
	
	@Column(name = "qr_symptom")	
	private String symptom;
	
	@Column(name = "qr_callyn")
	private String qryn;
	
	public static QuickReservation createQR(QuickReservationDto quickReservationDto, String hopetime)
	{
		QuickReservation quickReservation = new QuickReservation();
		String qrbasic = "N";
		quickReservation.setCusname(quickReservationDto.getCusname());
		quickReservation.setCustel(quickReservationDto.getCustel());
		quickReservation.setRequesttime(hopetime);
		quickReservation.setSymptom(quickReservationDto.getSymptom());
		quickReservation.setQryn(qrbasic);
		return quickReservation;
	}
	public void updateqryn(String callyn) {
		this.qryn = callyn;
	}
	
}
