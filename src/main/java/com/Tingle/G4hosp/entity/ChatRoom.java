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
	
	@JoinColumn(name = "medId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Med medId;
	
	private String chatRoomName;
	
	@Transient
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoom createChatRoom (Med med) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setMedId(med);
		chatRoom.setChatRoomName(med.getMedName());
		return chatRoom;
	}
	
	public void updateChatRoom (String name) {
		this.chatRoomName = name;
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
