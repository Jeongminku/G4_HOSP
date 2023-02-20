package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.Med;

public interface MedRepository extends JpaRepository<Med, Long> {
	
}
