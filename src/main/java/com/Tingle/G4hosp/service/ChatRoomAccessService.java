package com.Tingle.G4hosp.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.ObjectUtils;

import com.Tingle.G4hosp.entity.ChatRoomAccess;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.repository.ChatRoomAccessRepository;
import com.Tingle.G4hosp.repository.MemberMedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomAccessService {
	private final ChatRoomAccessRepository chatRoomAccessRepository;
	private final MemberMedRepository memberMedRepository;
	
	public ChatRoomAccess createChatRoomAccess (Med med) {
		ChatRoomAccess chatRoomAccess = ChatRoomAccess.createChatRoomAceess(med);
		return chatRoomAccessRepository.save(chatRoomAccess);
	}
	
	public ChatRoomAccess checkInit () {
		ChatRoomAccess permitAll = chatRoomAccessRepository.findByChatRoomAccessMedId(null);
		if(permitAll == null) {
			permitAll = ChatRoomAccess.createChatRoomAceess(null);
	    	return chatRoomAccessRepository.save(permitAll);
		} else {
			return permitAll;
		}
	}
    
    public ChatRoomAccess findById (Long id) {
    	return chatRoomAccessRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ChatRoomAccess> findAll () {
    	return chatRoomAccessRepository.findAll();
    }
    
    public boolean checkChatRoomAccess (Long roomAccessId, Member enterMember) {
    	ChatRoomAccess chatRoomAccess = findById(roomAccessId);
    	if(chatRoomAccess.getChatRoomAccessMedId() == null) {
    		return true;
    	} else {
    		MemberMed memberMed = memberMedRepository.findByMemberid(enterMember.getId());
    		return chatRoomAccess.getChatRoomAccessMedId().equals(memberMed.getMedId());
    	}
    }
    
}
