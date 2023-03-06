package com.Tingle.G4hosp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.COUNT;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.constant.Ward;
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
	
	// 전체 의사 수 조회
	@Override
	public AdminMainDto getdoctorcount(AdminMainDto adminMainDto) {
		QMember member = QMember.member;
		Long count =queryFactory.select(member.id.count())
				.from(member)
				.where(member.role.eq(Role.DOCTOR))
				.fetchOne();
		
		adminMainDto.setDoctorcount(count);
		return adminMainDto;
	}
	
	// 전체 환자 수 조회
	@Override
	public AdminMainDto getpatientcount(AdminMainDto adminMainDto) {
		QMember member = QMember.member;
		
		Long count = queryFactory.select(member.id.count())
				.from(member)
				.where(member.role.eq(Role.CLIENT))
				.fetchOne();
		
		adminMainDto.setPatientcount(count);
		
		return adminMainDto;
	}

	// 입원 환자수 조회
	@Override
	public AdminMainDto gethospitalizedcount(AdminMainDto adminMainDto) {
		QHospitalize hospitalize = QHospitalize.hospitalize;
		Long count = queryFactory.select(hospitalize.id.count())
				.from(hospitalize)
				.fetchOne();
		
		adminMainDto.setHospitalizecount(count);
		return adminMainDto;
	}

	
	// 검색어로 의사명 검색
	@Override
	public List<Member> getdoctorbysearch(String searchquery) {
		QMember member = QMember.member;	
		List<Member> memlist = queryFactory.select(member).from(member)
				.where(member.role.eq(Role.DOCTOR))
				.where(member.name.like("%"+searchquery+"%"))
				.fetch();
		
		return memlist;
	}
	
	// 검색어로 과 검색
	@Override
	public List<MemberMed> getMedDoctorbysearch(String searchquery) {
		QMed med = QMed.med;
		QMemberMed memberMed = QMemberMed.memberMed;
		
		List<MemberMed> memmedlist = queryFactory.select(memberMed).from(med).join(memberMed)
							.on(med.medId.eq(memberMed.medId.medId))
							.where(med.medName.like("%"+searchquery+"%"))
							.fetch();
				
		return memmedlist;
	}
	
	

	//과별 입원 환자 현황 조회 
	@Override
	public AdminMainDto viewHosptalizedlistMed(List<Med> medlist,AdminMainDto adminMainDto){
		QMemberMed memberMed = QMemberMed.memberMed;
		QHospitalize hospitalize = QHospitalize.hospitalize;
		List<String> hospnamelist = new ArrayList<>();
		List<Long> hospcountlist = new ArrayList<>();
		Long hospcount;
		
		for(Med med : medlist) {
			try {
				hospcount = queryFactory.select(memberMed.medId.count()).from(hospitalize)
						.join(memberMed).on(hospitalize.doctor.id.eq(memberMed.memberId.id))
						.where(memberMed.medId.medId.eq(med.getMedId()))
						.fetchOne();	
			} catch (NullPointerException e) {
				hospcount = (long) 0;
			}
			hospnamelist.add(med.getMedName());
			hospcountlist.add(hospcount);
		}
		adminMainDto.setHosptalizedEachMed(hospcountlist);
		adminMainDto.setHosptalizedEachMedname(hospnamelist);
		
		return adminMainDto;
	}


	// 호실별 입원 환자 현황 조회
	@Override
	public AdminMainDto viewHosptalizedwardlist(AdminMainDto adminMainDto) {
		QHospitalize hospitalize = QHospitalize.hospitalize;
		Long wardcount;	
		List<Long> wardcountlist = new ArrayList<>();
		List<String> wardnamelist = new ArrayList<>();
		
		for(int i=301; i<=310; i++) {
			try {
				wardcount = queryFactory.select(hospitalize.ward.count()).from(hospitalize)
						.where(hospitalize.ward.eq(Integer.toString(i)))
						.fetchOne();
			} catch (NullPointerException e) {
				wardcount = (long) 0;
			}
			wardnamelist.add(Integer.toString(i));
			wardcountlist.add(wardcount);
		}
		adminMainDto.setHosptalizedEachWard(wardcountlist);
		adminMainDto.setHosptalizedEachWardname(wardnamelist);
		
		return adminMainDto;
	}
	
	
	
	
	
}
