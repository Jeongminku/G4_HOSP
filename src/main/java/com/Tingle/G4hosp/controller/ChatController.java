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
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.service.ChatService;
import com.Tingle.G4hosp.service.MedService;
import com.Tingle.G4hosp.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {
	
    private final ChatService chatService;
    private final MedService medService;
    private final MemberService memberService;

    @PostMapping
    public String createRoom(ChatRoomDto chatRoomDto, Model model) {
    	try {
    		chatService.createChatRoom(chatRoomDto);
    	} catch(Exception e) {
    		model.addAttribute("ErrorMsg", e.getMessage());
    	}
    	model.addAttribute("AllChatRoom", chatService.findAllChatRoom());
    	model.addAttribute("AllAccessList", chatService.findAllAccessListToMap());
        return "redirect:/chat";
    }

    @GetMapping
    public String findAllRoom(Model model, Principal principal) {
    	model.addAttribute("AllChatRoom", chatService.findAllChatRoom());
    	model.addAttribute("AllAccessList", chatService.findAllAccessListToMap());
        return "ChatPage/ChatRoomSelect";
    }
    
    @PostMapping("/room")
    public String enterChatRoom (@RequestParam("roomAccessId") Long roomAccessId, 
    							 @RequestParam("roomId") Long roomId, Model model,
    							 Principal principal) {
    	try {
    		Map<Long, String> senderMap = chatService.enterChatRoom(roomId, roomAccessId, principal.getName());
    		model.addAttribute("SenderData", senderMap);
    	} catch (Exception e) {
    		model.addAttribute("ErrorMsg", e.getMessage());
    	}
    	return "ChatPage/WebChatTesting";
    }
    
//    @PostMapping("/send")
//    public void test (ChatMessageDto chatMessageDto, WebSocketSession webSocketSession) {
//    	System.err.println(chatMessageDto);
//    	chatService.sendMessage(webSocketSession, chatMessageDto);
//    }
}