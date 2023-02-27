package com.Tingle.G4hosp.repository;

import java.util.List;
import javax.persistence.EntityManager;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.QMed;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MedRepositoryCustomImpl implements MedRepository2{
	
	private JPAQueryFactory queryFactory;
	
	private MedRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<Med> testMedListNotMyMed(Long myMedId) {
		List<Med> testList = queryFactory.selectFrom(QMed.med)
									.where(QMed.med.medId.notIn(myMedId))
									.fetch();
		return testList;
	}

	
}
