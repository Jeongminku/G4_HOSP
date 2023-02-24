package com.Tingle.G4hosp.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.Tingle.G4hosp.dto.ChatRoomDto;
import com.Tingle.G4hosp.entity.ChatRoom;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    
    public ChatRoom createChatRoom (Med med) {
    	return ChatRoom.createChatRoom(med);
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
    
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
        	session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}