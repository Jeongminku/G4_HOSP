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

import com.Tingle.G4hosp.dto.ChatRoomDto;

import lombok.Data;

@Data
@Entity
@Table(name = "chatRoom")
public class ChatRoom extends BaseTime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name = "chatRoomAccess")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ChatRoomAccess chatRoomAccess;
	
	private String chatRoomName;
	
	@Transient
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoom createChatRoom (ChatRoomAccess chatRoomAccess, String chatRoomName) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChatRoomAccess(chatRoomAccess);
		chatRoom.setChatRoomName(chatRoomName);
		return chatRoom;
	}
	
	public void updateChatRoom (ChatRoomAccess chatRoomAccess, String chatRoomName) {
		this.chatRoomName = chatRoomName;
		this.chatRoomAccess = chatRoomAccess;
		System.err.println(this);
	}

}
