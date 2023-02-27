package com.Tingle.G4hosp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Data
@Entity
@Table(name = "chatRoomAccess")
public class ChatRoomAccess {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(name = "chatRoomAccessMedId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Med chatRoomAccessMedId;
	
	private String chatRoomAccessName;
	
	public static ChatRoomAccess createChatRoomAceess (Med med) {
		ChatRoomAccess chatRoomAccess = new ChatRoomAccess();
		if(med == null) {
			chatRoomAccess.setChatRoomAccessMedId(null);
			chatRoomAccess.setChatRoomAccessName("전체");
		} else {
			chatRoomAccess.setChatRoomAccessMedId(med);
			chatRoomAccess.setChatRoomAccessName(med.getMedName());			
		}
		return chatRoomAccess;
	}
}
