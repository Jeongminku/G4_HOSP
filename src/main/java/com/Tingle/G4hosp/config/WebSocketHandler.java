package com.Tingle.G4hosp.config;

import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.Tingle.G4hosp.dto.ChatMessageDto;
import com.Tingle.G4hosp.entity.ChatRoom;
import com.Tingle.G4hosp.repository.ChatRoomRepository;
import com.Tingle.G4hosp.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
	private final ObjectMapper objectMapper;
    private final ChatService chatService;
	private final ChatRoomRepository chatRoomRepository;
	
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        ChatRoom connectedRoom = chatRoomRepository.findById(chatMessage.getRoomId()).orElseThrow(EntityNotFoundException::new);
        connectedRoom.handlerActions(session, chatMessage, chatService);
    }
	
}
