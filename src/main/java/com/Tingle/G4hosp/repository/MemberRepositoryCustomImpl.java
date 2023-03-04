package com.Tingle.G4hosp.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.AdminMainDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.entity.QHospitalize;
import com.Tingle.G4hosp.entity.QMed;
import com.Tingle.G4hosp.entity.QMember;
import com.Tingle.G4hosp.entity.QMemberMed;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public MemberRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	@Override
	public AdminMainDto getdoctorcount() {
		AdminMainDto adminMainDto = new AdminMainDto();
		QMember member = QMember.member;
		Long count =queryFactory.select(member.id.count())
				.from(member)
				.where(member.role.eq(Role.DOCTOR))
				.fetchOne();
		
		adminMainDto.setDoctorcount(count);
		return adminMainDto;
	}

	@Override
	public AdminMainDto getpatientcount() {
		AdminMainDto adminMainDto = new AdminMainDto();	
		QMember member = QMember.member;
		
		Long count = queryFactory.select(member.id.count())
				.from(member)
				.where(member.role.eq(Role.CLIENT))
				.fetchOne();
		
		adminMainDto.setPatientcount(count);
		
		return adminMainDto;
	}

	@Override
	public AdminMainDto gethospitalizedcount() {
		AdminMainDto adminMainDto = new AdminMainDto();
		QHospitalize hospitalize = QHospitalize.hospitalize;
		Long count = queryFactory.select(hospitalize.id.count())
				.from(hospitalize)
				.fetchOne();
		
		adminMainDto.setHospitalizecount(count);
		return adminMainDto;
	}

	

	@Override
	public List<Member> getdoctorbysearch(String searchquery) {

		QMember member = QMember.member;
		
		List<Member> memlist = queryFactory.select(member).from(member)
				.where(member.role.eq(Role.DOCTOR))
				.where(member.name.like("%"+searchquery+"%"))
				.fetch();
		
		return memlist;
	}

	
	
	
	
	
}
