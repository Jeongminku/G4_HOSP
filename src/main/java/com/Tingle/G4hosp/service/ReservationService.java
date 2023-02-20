package com.Tingle.G4hosp.service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.dto.ReservationDoctorDto;
import com.Tingle.G4hosp.dto.ReservationDto;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.Reservation;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	
	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public Reservation createReservation (ReservationDto reservationDto) {
		Member patient = memberRepository.findById(reservationDto.getReservationPatientId()).orElseThrow(EntityNotFoundException::new);
		Member doctor = memberRepository.findById(reservationDto.getReservationDoctorId()).orElseThrow(EntityNotFoundException::new);
		Reservation reservation = Reservation.createReservation(reservationDto.getReservationDate(), patient, doctor, formatter);
		return reservationRepository.save(reservation);
	}
	
	public void updateReservation (ReservationDto reservationDto) {
		Reservation reservation = reservationRepository.findById(reservationDto.getReservationId()).orElseThrow(EntityNotFoundException::new);
		Member doctor = memberRepository.findById(reservationDto.getReservationDoctorId()).orElseThrow(EntityNotFoundException::new);
		reservation.updateReservation(reservationDto.getReservationDate(), doctor, formatter);
	}

	public void deleteReservation (Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
		reservationRepository.delete(reservation);
	}
	
	public List<ReservationDoctorDto> findAllDoctor () {
		Map<String, List<ReservationDoctorDto>> test = null;
		
		return null;
	}
}
