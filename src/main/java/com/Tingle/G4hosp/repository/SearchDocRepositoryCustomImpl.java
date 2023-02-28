package com.Tingle.G4hosp.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.groovy.parser.antlr4.util.StringUtils;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.QSearchDocListDto;
import com.Tingle.G4hosp.dto.QSearchMedListDto;
import com.Tingle.G4hosp.dto.SearchInputDto;
import com.Tingle.G4hosp.dto.SearchDocListDto;
import com.Tingle.G4hosp.dto.SearchMedListDto;
import com.Tingle.G4hosp.entity.QMed;
import com.Tingle.G4hosp.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class SearchDocRepositoryCustomImpl implements SearchDocRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public SearchDocRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression SearchMedNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QMed.med.medName.like("%" + searchQuery + "%");
	}
	
	private BooleanExpression SearchDocNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QMember.member.name.like("%"+searchQuery+"%");
	}

	@Override //의사 검색을 하여 리스트를 만들고 뿌림.
	public List<SearchDocListDto> getDocList(SearchInputDto searchDocDto) {
		QMember member = QMember.member;
				
		List<SearchDocListDto> content = queryFactory.select(new QSearchDocListDto(member.name,member.imgOri,member.imgUrl))
										.from(member)
										.where(SearchDocNmLike(searchDocDto.getSearchQuery()))
										.where(member.role.eq(Role.DOCTOR))
										.fetch();
		return content;
	}

	@Override //Med 검색을 하여 리스트를 만들고 뿌림
	public List<SearchMedListDto> getMedList(SearchInputDto searchDocDto) {
		QMed med = QMed.med;
		
		List<SearchMedListDto> content = queryFactory.select(new QSearchMedListDto(med.medName))
										.from(med)
										.where(SearchMedNmLike(searchDocDto.getSearchQuery()))
										.fetch();
		return content;
	}
	
	
}
