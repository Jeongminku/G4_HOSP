package com.Tingle.G4hosp.repository;

import java.util.List;

import javax.persistence.EntityManager;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.dto.BoardListDto;
import com.Tingle.G4hosp.dto.BoardSerchDto;
import com.Tingle.G4hosp.dto.QBoardListDto;
import com.Tingle.G4hosp.entity.QBoard;
import com.Tingle.G4hosp.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;


public class BoardRepositoryImpl implements BoardRepositoryCustum{

	
	private JPAQueryFactory queryFactory;
	
	public BoardRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression boardNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ?
				null : QBoard.board.title.like("%" + searchQuery +"%");
	}
	
	//페이징
	@Override
	public Page<BoardListDto> getMainBoard(BoardSerchDto boardSerchDto, Pageable pageable) {
		QBoard board = QBoard.board;
		QMember member = QMember.member;
		
		List<BoardListDto> content = queryFactory
				.select(
						new QBoardListDto(
								board.id,
								board.member,
								board.title,
								board.view,
								board.regDatetime,
								board.secret
								)
						).from(board)
						.join(board.member,member)
						.where(member.id.eq(board.member.id))
						.where(boardNmLike(boardSerchDto.getSearchQuery()))
						.orderBy(board.id.desc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(board)
				.join(board.member,member)
				.where(member.id.eq(board.member.id))
				.where(boardNmLike(boardSerchDto.getSearchQuery()))
				.fetchOne();
						
		
		return new PageImpl<>(content,pageable,total);
	}
	
	//조회수증가
	@Override
	public long updateView(Long id) {
		QBoard board = QBoard.board;
		
		long content = queryFactory
			.update(board)
			.set(board.view, board.view.add(1))
			.where(board.id.eq(id))
			.execute();
		
		return content;
	}




	
	

	 
}
