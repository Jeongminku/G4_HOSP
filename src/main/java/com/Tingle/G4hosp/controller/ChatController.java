package com.Tingle.G4hosp.controller;

import java.net.Socket;
import java.security.Principal;
import java.util.*;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.WebSocketSession;

import com.Tingle.G4hosp.dto.ChatMessageDto;
import com.Tingle.G4hosp.dto.ChatRoomDto;
import com.Tingle.G4hosp.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {
	
    private final ChatService chatService;

    @PostMapping
    public String createRoom(@RequestParam("roomName") String name, Model model) {
//    	model.addAttribute("RoomDto", chatService.createRoom(name));
//    	System.err.println(chatService.createRoom(name));
        return "ReservationPage/WebChatTesting";
    }

    @GetMapping
    public String findAllRoom(Model model, Principal principal) {
//    	List<ChatRoomDto> allRooms = chatService.findAllRoom();
//    	model.addAttribute("allRooms", allRooms);
        return "ReservationPage/WebChatTesting";
    }
    
//    @PostMapping("/send")
//    public void test (ChatMessageDto chatMessageDto, WebSocketSession webSocketSession) {
//    	System.err.println(chatMessageDto);
//    	chatService.sendMessage(webSocketSession, chatMessageDto);
//    }
}