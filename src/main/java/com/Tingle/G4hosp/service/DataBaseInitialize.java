package com.Tingle.G4hosp.service;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.repository.ArchiveDiseaseRepository;
import com.Tingle.G4hosp.repository.ArchiveImgRepository;
import com.Tingle.G4hosp.repository.ArchiveRepository;
import com.Tingle.G4hosp.repository.BoardRepository;
import com.Tingle.G4hosp.repository.ChatRoomAccessRepository;
import com.Tingle.G4hosp.repository.ChatRoomRepository;
import com.Tingle.G4hosp.repository.DiseaseRepository;
import com.Tingle.G4hosp.repository.HinfoBoardRepository;
import com.Tingle.G4hosp.repository.HinfoImgRepository;
import com.Tingle.G4hosp.repository.HospitalizeDiseaseRepository;
import com.Tingle.G4hosp.repository.HospitalizeRepository;
import com.Tingle.G4hosp.repository.MedRepository;
import com.Tingle.G4hosp.repository.MemberMedRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.Tingle.G4hosp.repository.QaBoardRepository;
import com.Tingle.G4hosp.repository.ReservationNotAvailableRepository;
import com.Tingle.G4hosp.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataBaseInitialize {
	
	private final ArchiveDiseaseRepository archiveDiseaseRepository;
	private final ArchiveImgRepository archiveImgRepository;
	private final ArchiveRepository archiveRepository;
	private final BoardRepository boardRepository;
	private final ChatRoomAccessRepository chatRoomAccessRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final DiseaseRepository diseaseRepository;
	private final HinfoBoardRepository hinfoBoardRepository;
	private final HinfoImgRepository hinfoImgRepository;
	private final HospitalizeDiseaseRepository hospitalizeDiseaseRepository;
	private final HospitalizeRepository hospitalizeRepository;
	private final MedRepository medRepository;
	private final MemberMedRepository memberMedRepository;
	private final MemberRepository memberRepository;
	private final QaBoardRepository qaBoardRepository;
	private final ReservationNotAvailableRepository reservationNotAvailableRepository;
	private final ReservationRepository reservationRepository;
	
//	데이터 베이스 초기화...
	public void initDB () {
		
	}
}
