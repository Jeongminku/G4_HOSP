package com.Tingle.G4hosp.dto;

import java.util.*;

import com.Tingle.G4hosp.entity.ChatRoom;
import com.Tingle.G4hosp.entity.Member;

import lombok.Data;

@Data
public class ChatRoomDto {
	private Long id;
	private Long chatRoomAccess;
	private String chatRoomName;
	private String chatRoomAccessName;
	
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
	
	public static ChatRoomDto createChatRoomDto (ChatRoom chatRoom) {
		ChatRoomDto chatRoomDto = new ChatRoomDto();
		chatRoomDto.setChatRoomName(chatRoom.getChatRoomName());
		chatRoomDto.setChatRoomAccessName(chatRoom.getChatRoomAccess().getChatRoomAccessName());
		chatRoomDto.setId(chatRoom.getId());
		return chatRoomDto;
	}
}
