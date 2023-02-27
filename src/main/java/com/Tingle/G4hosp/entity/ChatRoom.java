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
	
	@Transient
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoom createChatRoom (ChatRoomAccess chatRoomAccess, String chatRoomName) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChatRoomAccess(chatRoomAccess);
		chatRoom.setChatRoomName(chatRoomName);
		return chatRoom;
	}
	
	public void updateChatRoom (String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
	
	public Set<WebSocketSession> addSession (WebSocketSession session) {
		this.sessions.add(session);
		return this.sessions;
	}
	
	public Set<WebSocketSession> removeSession (WebSocketSession session) {
		this.sessions.remove(session);
		return this.sessions;
	}
}
