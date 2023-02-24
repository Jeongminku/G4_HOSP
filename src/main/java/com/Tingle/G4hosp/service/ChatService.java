package com.Tingle.G4hosp.service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.Tingle.G4hosp.constant.MessageType;
import com.Tingle.G4hosp.dto.ChatMessageDto;
import com.Tingle.G4hosp.dto.ChatRoomDto;
import com.Tingle.G4hosp.entity.ChatRoom;
import com.Tingle.G4hosp.entity.ChatRoomAccess;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.repository.ChatRoomAccessRepository;
import com.Tingle.G4hosp.repository.ChatRoomRepository;
import com.Tingle.G4hosp.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomAccessRepository chatRoomAccessRepository;
    private final MemberService memberService;
    private final MemberMedService memberMedService;
    
    public ChatRoom createChatRoom (ChatRoomDto chatRoomDto) {
    	ChatRoomAccess chatRoomAccess = chatRoomAccessRepository.findById(chatRoomDto.getChatRoomAccess()).orElseThrow(EntityNotFoundException::new);
    	ChatRoom chatRoom = ChatRoom.createChatRoom(chatRoomAccess, chatRoomDto.getChatRoomName());
    	return chatRoomRepository.save(chatRoom);
    }
    
    public void changeName (Long chatRoomId, String name) {
    	ChatRoom currentRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(EntityNotFoundException::new);
    	currentRoom.updateChatRoom(name);
    }
    
    public void deleteChatRoom (Long chatRoomId) {
    	ChatRoom currentRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(EntityNotFoundException::new);
    	chatRoomRepository.delete(currentRoom);
    }
    
    public List<ChatRoomDto> findAllChatRoom () {
    	List<ChatRoom> allChatRoom = chatRoomRepository.findAll();
    	return ChatRoomDto.createChatRoomDtoList(allChatRoom);
    }
    
    public Map<Long, String> findAllAccessListToMap () {
    	List<ChatRoomAccess> accessList = chatRoomAccessRepository.findAll();
    	Map<Long, String> accessListMap = new HashMap<>();
    	for(ChatRoomAccess access : accessList) {
    		accessListMap.put(access.getId(), access.getChatRoomAccessName());
    	}
    	return accessListMap;
    }
    
    public boolean checkChatRoomAccess (Long roomAccessId, Member sender) {
    	ChatRoomAccess chatRoomAccess = chatRoomAccessRepository.findById(roomAccessId).orElseThrow(EntityNotFoundException::new);
    	if(chatRoomAccess.getChatRoomAccessMedId() == null) {
    		return true;
    	} else {
    		MemberMed memberMed = memberMedService.findMemberMed(sender.getId());
    		return chatRoomAccess.getChatRoomAccessMedId() == memberMed.getMedId() ? true : false;
    	}
    }
    
    public Map<Long, String> enterChatRoom (Long roomId, Long roomAccessId, String memberLoginId) throws AccessDeniedException {
    	Member sender = memberService.findByLoginid(memberLoginId);
    	if(checkChatRoomAccess(roomAccessId, sender)) {
    		Map<Long, String> senderMap = new HashMap<>();
    		senderMap.put(roomId, sender.getName());
    		return senderMap;
    	} else {
    		throw new AccessDeniedException("공개범위 다름");
    	}    	
    }
    
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
        	session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
        	System.err.println("error: " + e.getMessage());
            log.error(e.getMessage(), e);
            
        }
    }
}