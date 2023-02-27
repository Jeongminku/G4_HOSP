package com.Tingle.G4hosp.service;

import org.springframework.stereotype.Service;

import com.Tingle.G4hosp.entity.ChatRoomAccess;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.repository.ChatRoomAccessRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomAccessService {
	private final ChatRoomAccessRepository chatRoomAccessRepository;
	
	public ChatRoomAccess createChatRoomAccess (Med med) {
		ChatRoomAccess chatRoomAccess = ChatRoomAccess.createChatRoomAceess(med);
		return chatRoomAccessRepository.save(chatRoomAccess);
	}
}
