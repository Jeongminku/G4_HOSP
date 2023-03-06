package com.Tingle.G4hosp.dto;

import java.time.format.DateTimeFormatter;
import java.util.*;

import com.Tingle.G4hosp.entity.ChatRoom;

import lombok.Data;

@Data
public class ChatRoomDto {
	private Long id;
	private Long chatRoomAccess;
	private String chatRoomName;
	private String chatRoomAccessName;
	private String chatRoomRegDate;
	
	public static List<ChatRoomDto> createChatRoomDtoList (List<ChatRoom> chatRoomList) {
		List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
		for(ChatRoom room : chatRoomList) {
			ChatRoomDto chatRoomDto = new ChatRoomDto();
			chatRoomDto.setId(room.getId());
			chatRoomDto.setChatRoomAccess(room.getChatRoomAccess().getId());
			chatRoomDto.setChatRoomName(room.getChatRoomName());
			chatRoomDto.setChatRoomRegDate(room.getRegDatetime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일, HH시")));
			chatRoomDtoList.add(chatRoomDto);
		}
		return chatRoomDtoList;
	}
	
	public static ChatRoomDto createChatRoomDto (ChatRoom chatRoom) {
		ChatRoomDto chatRoomDto = new ChatRoomDto();
		chatRoomDto.setChatRoomName(chatRoom.getChatRoomName());
		chatRoomDto.setChatRoomAccessName(chatRoom.getChatRoomAccess().getChatRoomAccessName());
		chatRoomDto.setId(chatRoom.getId());
		return chatRoomDto;
	}
	
	public static ChatRoomDto createChatRoomDto (Long roomId, Long accessId, String roomName) {
		ChatRoomDto chatRoomDto = new ChatRoomDto();
		chatRoomDto.setId(roomId);
		chatRoomDto.setChatRoomAccess(accessId);
		chatRoomDto.setChatRoomName(roomName);
		return chatRoomDto;
	}
}
