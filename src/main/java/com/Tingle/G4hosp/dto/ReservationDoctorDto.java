package com.Tingle.G4hosp.dto;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.Member;

import lombok.Data;

@Data
public class ReservationDoctorDto {
	private Long id;
	private String name;
	private String tel;
	private String imgName;
	private String imgUrl;
	
	private static ModelMapper mapper;
	
	public static ReservationDoctorDto of (Member doctor) {
		ReservationDoctorDto dto = new ReservationDoctorDto();
		dto.setId(doctor.getId());
		dto.setImgName(doctor.getImgName());
		dto.setImgUrl(doctor.getImgUrl());
		dto.setName(doctor.getName());
		dto.setTel(doctor.getTel());
		
		return dto;
	}
}
