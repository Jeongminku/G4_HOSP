package com.Tingle.G4hosp.config;

import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.Tingle.G4hosp.constant.MessageType;
import com.Tingle.G4hosp.dto.ChatMessageDto;
import com.Tingle.G4hosp.entity.ChatRoom;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;
import com.Tingle.G4hosp.repository.ChatRoomRepository;
import com.Tingle.G4hosp.service.ChatService;
import com.Tingle.G4hosp.service.MemberMedService;
import com.Tingle.G4hosp.service.MemberService;
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
	private final MemberService memberService;
	private final MemberMedService memberMedService;

	private Map<Long, Set<WebSocketSession>> roomSeesionList = new HashMap<>();
	private Set<WebSocketSession> sessions = new HashSet<>();
	
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        ChatRoom connectedRoom = chatRoomRepository.findById(chatMessage.getRoomId()).orElseThrow(EntityNotFoundException::new);
        sessions = connectedRoom.getSessions();
//        System.err.println(sessions);
//        roomSeesionList.keySet().forEach(key -> System.err.println(key));

        if(roomSeesionList.containsKey(connectedRoom.getId())){
        	sessions = roomSeesionList.get(connectedRoom.getId());
        }
        
        if (chatMessage.getType().equals(MessageType.ENTER)) {
        	sessions.add(session);
            roomSeesionList.put(connectedRoom.getId(), sessions);
            connectedRoom.setSessions(sessions);
            for(WebSocketSession s : sessions) {
            	Member member = memberService.findByLoginid(s.getPrincipal().getName());
            	MemberMed med = memberMedService.findMemberMed(member.getId());
            	String division = "관리자";
            	if(med != null) division = med.getMedId().getMedName();
            	chatMessage.getJoinedMember().add("[" + division + "] " + member.getName());
            }
            chatMessage.setMessage(chatMessage.getSender() + " 님이 입장하셨습니다.");
        }
        
        sendMessage(chatMessage);
    }
    
    private <T> void sendMessage(T message) {
        sessions.parallelStream().forEach(session -> {
        	chatService.sendMessage(session, message);
        	});
    }

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String disConUserName = memberService.findByLoginid(session.getPrincipal().getName()).getName();
		for(Long roomId : roomSeesionList.keySet()) {
			if(roomSeesionList.get(roomId).contains(session)) {
				ChatMessageDto chatMessage = new ChatMessageDto();
				chatMessage.setRoomId(roomId);
				chatMessage.setSender(disConUserName);
				chatMessage.setType(MessageType.LEAVE);
				chatMessage.setMessage(chatMessage.getSender() + " 님이 퇴장하셨습니다.");
				roomSeesionList.get(roomId).remove(session);
				sessions = roomSeesionList.get(roomId);
				sendMessage(chatMessage);
			}
		}
	}
	
}
