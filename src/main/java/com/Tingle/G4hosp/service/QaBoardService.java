package com.Tingle.G4hosp.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.entity.QaBoard;
import com.Tingle.G4hosp.repository.QaBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class QaBoardService {

	private final QaBoardRepository qaBoardRepository;
	
	public void saveQaBoard(QaBoard qaBoard) {
		qaBoardRepository.save(qaBoard);
	}
}
