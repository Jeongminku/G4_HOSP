package com.Tingle.G4hosp.service;

import java.util.*;
import java.util.stream.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.ReservationNotAvailable;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.repository.ReservationNotAvailableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationNotAvailableService {
	private final ReservationNotAvailableRepository reservationNotAvailableRepository;
	private final MemberRepository memberRepository;
	
	public List<ReservationNotAvailable> createReservationNotAvailable (String doctorLoginId, List<String> notAvailDateList, List<String> notAvailDayList) {
		Member doctor = memberRepository.findByLoginid(doctorLoginId);
		List<String> notAvailableStrList = Stream.concat(notAvailDateList.stream(), notAvailDayList.stream()).collect(Collectors.toList());
		List<ReservationNotAvailable> reservationNotAvailableList = new ArrayList<>();
		for(String dateDayStr : notAvailableStrList) {
			ReservationNotAvailable reservationNotAvailable = ReservationNotAvailable.createReservationNotAvailable(doctor, dateDayStr);
			reservationNotAvailableList.add(reservationNotAvailable);
		}
		return reservationNotAvailableRepository.saveAll(reservationNotAvailableList);
	}
	
	public List<String> findAllNotAvailByDoctor (Long DocotrId) {
		Member doctor = memberRepository.findById(DocotrId).orElseThrow(EntityNotFoundException::new);
		return findAllNotAvailByDoctor(doctor);
	}
	
	public List<String> findAllNotAvailByDoctor (String DocotrLoginId) {
		Member doctor = memberRepository.findByLoginid(DocotrLoginId);
		return findAllNotAvailByDoctor(doctor);
	}
	
	private List<String> findAllNotAvailByDoctor (Member doctor) {
		List<ReservationNotAvailable> notAvailList = reservationNotAvailableRepository.findByDoctor(doctor);
		List<String> notAvailStr = new ArrayList<>();
		for(ReservationNotAvailable notAvail : notAvailList) {
			notAvailStr.add(notAvail.getNotAvailableDay());
		}
		return notAvailStr;
	}
}
