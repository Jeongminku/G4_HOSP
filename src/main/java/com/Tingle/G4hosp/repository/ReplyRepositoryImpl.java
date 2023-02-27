package com.Tingle.G4hosp.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.dto.PageSerchDto;
import com.Tingle.G4hosp.dto.QReplyDto;
import com.Tingle.G4hosp.dto.ReplyDto;
import com.Tingle.G4hosp.entity.QBoard;
import com.Tingle.G4hosp.entity.QReply;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ReplyRepositoryImpl implements ReplyRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public ReplyRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression ReplyNmLike(String searchQurey) {
		return StringUtils.isEmpty(searchQurey) ? null : QReply.reply.replyContent.like("%" + searchQurey + "%");
	}

	@Override
	public Page<ReplyDto> getReplyPage(PageSerchDto pageSerchDto, Pageable pageable, Long BoardId) {
		QReply reply = QReply.reply;
		QBoard board = QBoard.board;
		
		List<ReplyDto> content = queryFactory
				.select(
						new QReplyDto(
								reply.id,
								reply.replyContent,
								reply.regDatetime,
								reply.board,
								reply.member
								)
						).from(reply)
						.join(reply.board,board)
						.where(reply.board.id.eq(BoardId))
						.where(ReplyNmLike(pageSerchDto.getSearchQuery()))
						.orderBy(reply.id.desc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();

		long total = queryFactory
				.select(Wildcard.count)
				.from(reply)
				.join(reply.board,board)
				.where(reply.board.id.eq(BoardId))
				.where(ReplyNmLike(pageSerchDto.getSearchQuery()))
				.fetchOne();
						
						
	
				
		
		return new PageImpl<>(content,pageable,total);
	}

}
