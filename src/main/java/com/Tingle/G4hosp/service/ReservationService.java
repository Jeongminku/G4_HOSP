package com.Tingle.G4hosp.service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.dto.ReservationDoctorDto;
import com.Tingle.G4hosp.dto.ReservationDto;
import com.Tingle.G4hosp.dto.ReservationNotAvailableDto;
import com.Tingle.G4hosp.dto.ReservationViewDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.entity.Reservation;
import com.Tingle.G4hosp.entity.ReservationNotAvailable;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MemberMedRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.repository.ReservationNotAvailableRepository;
import com.Tingle.G4hosp.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	
	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;
	private final MedRepository medRepository;
	private final MemberMedRepository memberMedRepository;
	private final ReservationNotAvailableRepository reservationNotAvailableRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	
	public Reservation createReservation (ReservationDto reservationDto, String patientLoginId) {
		Member patient = memberRepository.findByLoginid(patientLoginId);
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
	
	public Map<String, List<ReservationDoctorDto>> findAllDoctor () {
		Map<String, List<ReservationDoctorDto>> doctorListByMed = new HashMap<>();
		List<Med> medList = medRepository.findAll();
		for(Med med : medList) {
			System.err.println(med);
			List<ReservationDoctorDto> docList = new ArrayList<>();
			List<Member> medDoctorList = memberMedRepository.findDoctorsByMed(med);
			for(Member doctor : medDoctorList) {
				System.err.println(doctor.getName());
				docList.add(ReservationDoctorDto.of(doctor));
			}
			doctorListByMed.put(med.getMedName(), docList);
		}
		return doctorListByMed;
	}
	
	public ReservationDto initDto (Long doctorId) {
		ReservationDto reservationDto = new ReservationDto();
		Member doctor = memberRepository.findById(doctorId).orElseThrow(EntityNotFoundException::new);
		List<ReservationNotAvailable> notAvailableDay = reservationNotAvailableRepository.findByDoctor(doctor);
		List<ReservationNotAvailableDto> notAvailableDayDto = ReservationNotAvailableDto.createResreAvailableDto(notAvailableDay);
		reservationDto.setReservationDoctorId(doctor.getId());
		reservationDto.setNotAvailableDay(notAvailableDayDto);
		return reservationDto;
	}
	
	public List<ReservationViewDto> findAllReservationByDoctor (String doctorLoginId) {
		Member doctor = memberRepository.findByLoginid(doctorLoginId);
		List<Reservation> allReservation = reservationRepository.findByReservationDoctorOrderByReservationDate(doctor);
		return ReservationViewDto.createReservationViewDtoList(allReservation);
	}
	
}
