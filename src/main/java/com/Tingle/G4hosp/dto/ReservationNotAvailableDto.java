package com.Tingle.G4hosp.dto;

import java.util.*;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.ReservationNotAvailable;

import lombok.Data;

@Data
public class ReservationNotAvailableDto {
	private String notAvailableDay;
	
	private static ModelMapper modelMapper;
	public static List<ReservationNotAvailableDto> createResreAvailableDto (List<ReservationNotAvailable> reservationNotAvailable) {
		List<ReservationNotAvailableDto> ReservationNotAvailableDtoList = new ArrayList<>();
		for(ReservationNotAvailable notAvailable : reservationNotAvailable) {
//			ReservationNotAvailableDtoList.add(modelMapper.map(notAvailable, ReservationNotAvailableDto.class));
			ReservationNotAvailableDto dto = new ReservationNotAvailableDto();
			dto.setNotAvailableDay(notAvailable.getNotAvailableDay());
			ReservationNotAvailableDtoList.add(dto);
		}
		return ReservationNotAvailableDtoList;
	}
}
