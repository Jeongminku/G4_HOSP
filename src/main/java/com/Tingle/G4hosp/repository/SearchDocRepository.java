package com.Tingle.G4hosp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tingle.G4hosp.entity.Member;

public interface SearchDocRepository extends JpaRepository<Member, Long>, SearchDocRepositoryCustom
{

}
