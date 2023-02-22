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
		return mapper.map(doctor, ReservationDoctorDto.class);
	}
}
