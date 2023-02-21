package com.Tingle.G4hosp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.constant.QaCategory;
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
	
	public List<QaBoard> findAllQaBoard() {
		return qaBoardRepository.findAll();
	}
	
	public List<QaBoard> findQaFromCategory(String category) {
		return qaBoardRepository.findQAFromCAT(category);
	}
	
	public Optional<QaBoard> findQaBoard(Long qaId) {
		Optional<QaBoard> qaBoard = qaBoardRepository.findById(qaId);
		return qaBoard;
	}
	
}
