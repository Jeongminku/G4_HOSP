package com.Tingle.G4hosp.repository;


import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.dto.HinfoBoardDto;
import com.Tingle.G4hosp.dto.HinfoListDto;
import com.Tingle.G4hosp.dto.HinfoSerchDto;
import com.Tingle.G4hosp.dto.QHinfoListDto;
import com.Tingle.G4hosp.entity.QHinfoBoard;
import com.Tingle.G4hosp.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class HinfoBoardRepositoryImpl implements HinfoBoardRepositoryCustum{
	
	private JPAQueryFactory queryFactory;
	
	public HinfoBoardRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression hinfoNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QHinfoBoard.hinfoBoard.Title.like("%"+ searchQuery +"%");
	}

	@Override
	public Page<HinfoListDto> getMainHinfoMain(HinfoSerchDto hinfoSerchDto, Pageable pageable) {
		QHinfoBoard hinfoBoard = QHinfoBoard.hinfoBoard;
		QMember member = QMember.member;
		
		List<HinfoListDto> content = queryFactory
				.select(
						new QHinfoListDto(
								hinfoBoard.id,
								hinfoBoard.member,
								hinfoBoard.Title,
								hinfoBoard.view,
								hinfoBoard.regDatetime,
								hinfoBoard.Content
								)
						).from(hinfoBoard)
						.join(hinfoBoard.member,member)
						.where(member.id.eq(hinfoBoard.member.id))
						.where(hinfoNmLike(hinfoSerchDto.getSearchQuery()))
						.orderBy(hinfoBoard.id.desc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();
					
				long total = queryFactory
						.select(Wildcard.count)
						.from(hinfoBoard)
						.join(hinfoBoard.member,member)
						.where(member.id.eq(hinfoBoard.member.id))
						.where(hinfoNmLike(hinfoSerchDto.getSearchQuery()))
						.fetchOne();
	

		return new PageImpl<>(content,pageable,total);
	}

	//조회수증가
	@Override
	public long updateView(Long HinfoId) {
		QHinfoBoard hinfoBoard = QHinfoBoard.hinfoBoard;
		
		long content = queryFactory.update(hinfoBoard).set(hinfoBoard.view, hinfoBoard.view.add(1)).where(hinfoBoard.id.eq(HinfoId)).execute();
		return content;
	}




}
