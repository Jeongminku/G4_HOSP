package com.Tingle.G4hosp.dto;

import java.util.*;

import com.Tingle.G4hosp.entity.ChatRoom;

import lombok.Data;

@Data
public class ChatRoomDto {
	private Long id;
	private Long chatRoomAccess;
	private String chatRoomName;
	
	public static List<ChatRoomDto> createChatRoomDtoList (List<ChatRoom> chatRoomList) {
		List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
		for(ChatRoom room : chatRoomList) {
			ChatRoomDto chatRoomDto = new ChatRoomDto();
			chatRoomDto.setId(room.getId());
			chatRoomDto.setChatRoomAccess(room.getChatRoomAccess().getId());
			chatRoomDto.setChatRoomName(room.getChatRoomName());
			chatRoomDtoList.add(chatRoomDto);
		}
		return chatRoomDtoList;
	}
}
