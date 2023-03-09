package com.Tingle.G4hosp.service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.ReservationDoctorDto;
import com.Tingle.G4hosp.dto.ReservationDto;
import com.Tingle.G4hosp.dto.ReservationViewDto;
import com.Tingle.G4hosp.dto.ReservationViewPatDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
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
			List<ReservationDoctorDto> docList = new ArrayList<>();
			List<Member> medDoctorList = memberMedRepository.findDoctorsByMed(med);
			for(Member doctor : medDoctorList) {
				docList.add(ReservationDoctorDto.of(doctor));
			}
			doctorListByMed.put(med.getMedName(), docList);
		}
		return doctorListByMed;
	}
	
	public ReservationViewPatDto initDto (Long doctorId) {
		Member doctor = memberRepository.findById(doctorId).orElseThrow(EntityNotFoundException::new);
		List<ReservationNotAvailable> notAvailableDay = reservationNotAvailableRepository.findByDoctor(doctor);
		List<Reservation> takenReservation = reservationRepository.findByReservationDoctorOrderByReservationDate(doctor);
		return ReservationViewPatDto.createReservationViewPatDto(notAvailableDay, takenReservation);
	}
	
	public List<ReservationViewDto> findAllReservationByMember (String memberLoginId) {
		Member member = memberRepository.findByLoginid(memberLoginId);
		List<Reservation> allReservation = new ArrayList<>();
		if(member.getRole() == Role.DOCTOR) {
			allReservation = reservationRepository.findByReservationDoctorOrderByReservationDate(member);			
			return ReservationViewDto.createReservationViewDtoList(allReservation, true);			
		} else {
			allReservation = reservationRepository.findByReservationPatientOrderByReservationDate(member);
			return ReservationViewDto.createReservationViewDtoList(allReservation, false);
		}
	}
	
	public List<ReservationViewDto> findAllReservation () {
		List<Reservation> allReservation = reservationRepository.findAll();
		return ReservationViewDto.createReservationViewDtoList(allReservation, false);
	}
	
}
