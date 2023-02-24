package com.Tingle.G4hosp.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.Tingle.G4hosp.entity.Board;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.Reply;

import lombok.*;

@Getter
@Setter
public class BoardFormDto {
	
	private Long id; //게시글 번호
	
	@NotBlank(message = "게시글 제목을 입력하세요")
	private String title;
	
	@NotBlank(message = "게시글 내용을 입력하세요")
	private String content;
	
	private int view;  //게시글의 조회수
	
	private Member member; // 게시글을 쓴 회원의 정보가 담길 필드
	
	private LocalDateTime regTime;
	
	private List<Reply> reply;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Board createBoard() {// 게시글을 저장(만들어줌)
		return modelMapper.map(this, Board.class);
	}
	
	public static BoardFormDto of(Board board) { //entity랑 Dto랑 맵핑
		return modelMapper.map(board, BoardFormDto.class);
	}
	
}
