package com.Tingle.G4hosp.dto;

import java.util.*;

import org.springframework.web.socket.WebSocketSession;

import com.Tingle.G4hosp.constant.MessageType;
import com.Tingle.G4hosp.service.ChatService;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatRoomDto {
	private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

//    @Builder
//    public ChatRoomDto(String roomId, String name) {
//        this.roomId = roomId;
//        this.name = name;
//    }
    private ChatRoomDto () {
    	this.roomId = UUID.randomUUID().toString();
    	this.name = "병원 채팅방";
    }

    private static ChatRoomDto onlyRoom = new ChatRoomDto();
    public static ChatRoomDto getChatRoomDto () {
    	return onlyRoom;
    }
    
    public void handlerActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
        if (chatMessageDto.getType().equals(MessageType.ENTER)) {
            sessions.add(session);
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessageDto, chatService);

    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
