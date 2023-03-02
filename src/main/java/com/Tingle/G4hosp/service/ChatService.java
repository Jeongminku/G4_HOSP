package com.Tingle.G4hosp.service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private final ChatRoomAccessService chatRoomAccessService;
    private final MemberService memberService;

    public ChatRoom createChatRoom (ChatRoomDto chatRoomDto) {
    	ChatRoomAccess chatRoomAccess = chatRoomAccessService.findById(chatRoomDto.getChatRoomAccess());
    	ChatRoom chatRoom = ChatRoom.createChatRoom(chatRoomAccess, chatRoomDto.getChatRoomName());
    	return chatRoomRepository.save(chatRoom);
    }
    
    public void updateChatRoom (ChatRoomDto chatRoomDto) {
    	ChatRoom currentRoom = findbyId(chatRoomDto.getId());
    	ChatRoomAccess newAccess = chatRoomAccessService.findById(chatRoomDto.getChatRoomAccess());
    	currentRoom.updateChatRoom(newAccess, chatRoomDto.getChatRoomName());
    }
    
    public void deleteChatRoom (Long chatRoomId) {
    	ChatRoom currentRoom = findbyId(chatRoomId);
    	chatRoomRepository.delete(currentRoom);
    }
    
    public List<ChatRoomDto> findAllChatRoom () {
    	List<ChatRoom> allChatRoom = chatRoomRepository.findAll();
    	List<ChatRoomDto> allChatRoomDto = ChatRoomDto.createChatRoomDtoList(allChatRoom);
    	for(ChatRoomDto chatRoomDto : allChatRoomDto) {
    		chatRoomDto.setChatRoomAccessName(chatRoomAccessService.findById(chatRoomDto.getChatRoomAccess()).getChatRoomAccessName());
    	}
    	return allChatRoomDto;
    }
    
    public Map<Long, String> findAllAccessListToMap () {
    	List<ChatRoomAccess> accessList = chatRoomAccessService.findAll();
    	Map<Long, String> accessListMap = new HashMap<>();
    	for(ChatRoomAccess access : accessList) {
    		accessListMap.put(access.getId(), access.getChatRoomAccessName());
    	}
    	return accessListMap;
    }

    public Map<String, ChatRoomDto> enterChatRoom (Long chatRoomId, Long roomAccessId, String memberLoginId) throws AccessDeniedException {
    	Member enterMember = memberService.findByLoginid(memberLoginId);
    	if(chatRoomAccessService.checkChatRoomAccess(roomAccessId, enterMember)) {
    		Map<String, ChatRoomDto> senderMap = new HashMap<>();
    		ChatRoomDto currentRoomDto = ChatRoomDto.createChatRoomDto(chatRoomRepository.findById(chatRoomId).orElseThrow(EntityNotFoundException::new));
    		senderMap.put(enterMember.getName(), currentRoomDto);
    		return senderMap;
    	} else {
    		throw new AccessDeniedException("공개범위 다름");
    	}    	
    }
    
    public ChatRoom findbyId (Long id) {
    	return chatRoomRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
        	session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}