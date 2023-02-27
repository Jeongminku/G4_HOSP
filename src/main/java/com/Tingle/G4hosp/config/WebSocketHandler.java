package com.Tingle.G4hosp.config;

import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.thymeleaf.util.StringUtils;

import com.Tingle.G4hosp.constant.MessageType;
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

	private Map<Long, Set<WebSocketSession>> roomSeesionList = new HashMap<>();
	private Set<WebSocketSession> sessions = new HashSet<>();
	
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.err.println(session);
        log.info("{}", payload);
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        ChatRoom connectedRoom = chatRoomRepository.findById(chatMessage.getRoomId()).orElseThrow(EntityNotFoundException::new);
        
        if(roomSeesionList.containsKey(connectedRoom.getId())){
        	sessions = roomSeesionList.get(connectedRoom.getId());
        } 
        
        if (chatMessage.getType().equals(MessageType.ENTER)) {
            sessions.add(session);
            roomSeesionList.put(connectedRoom.getId(), sessions);
            chatMessage.setMessage(chatMessage.getSender() + " 님이 입장하셨습니다.");
        }
        
        if (chatMessage.getType().equals(MessageType.LEAVE)) {
            sessions.remove(session);
            roomSeesionList.put(connectedRoom.getId(), sessions);
            chatMessage.setMessage(chatMessage.getSender() + " 님이 퇴장하셨습니다.");
        }
        
        sendMessage(chatMessage);
    }
    
//    public void handlerActions(WebSocketSession webSocketSession, ChatMessageDto chatMessageDto) {
//        if (StringUtils.equals(chatMessageDto.getType(), MessageType.ENTER)) {
//            sessions.add(webSocketSession);
//            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장했습니다.");
//        }
//        sendMessage(chatMessageDto, chatService);
//    }
    
    private <T> void sendMessage(T message) {
        sessions.parallelStream().forEach(session -> {
        	chatService.sendMessage(session, message);
        	});
    }

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		for(Long roomId : roomSeesionList.keySet()) {
			if(roomSeesionList.get(roomId).contains(session)) roomSeesionList.get(roomId).remove(session);
		}
	}


//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		System.err.println("handler : " + session);
//	}
    
	
}
