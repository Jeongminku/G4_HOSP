package com.Tingle.G4hosp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.socket.WebSocketSession;

import com.Tingle.G4hosp.constant.MessageType;
import com.Tingle.G4hosp.dto.ChatMessageDto;
import com.Tingle.G4hosp.service.ChatService;

import lombok.Data;

@Data
@Entity
@Table(name = "chatRoom")
public class ChatRoom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name = "chatRoomAccess")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ChatRoomAccess chatRoomAccess;
	
	private String chatRoomName;
	
//	@Transient
//	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoom createChatRoom (ChatRoomAccess chatRoomAccess, String chatRoomName) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChatRoomAccess(chatRoomAccess);
		chatRoom.setChatRoomName(chatRoomName);
		return chatRoom;
	}
	
	public void updateChatRoom (String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
	
//	public void handlerActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
//        if (chatMessageDto.getType().equals(MessageType.ENTER)) {
//            sessions.add(session);
//            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장했습니다.");
//        }
//        sendMessage(chatMessageDto, chatService);
//
//    }
//
//    private <T> void sendMessage(T message, ChatService chatService) {
//        sessions.parallelStream()
//                .forEach(session -> chatService.sendMessage(session, message));
//    }
}
