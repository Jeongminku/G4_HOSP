package com.Tingle.G4hosp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.dto.ChatRoomDto;
import com.Tingle.G4hosp.service.ChatRoomAccessService;
import com.Tingle.G4hosp.service.ChatService;
import com.Tingle.G4hosp.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {
	
    private final ChatService chatService;
    private final ChatRoomAccessService chatRoomAccessService;
    private final MemberService memberService;

    @GetMapping
    public String findAllRoom(Model model, Principal principal, HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	if(principal != null) {
    		if(memberService.getMemberRole(principal.getName()) != Role.CLIENT) {
    			chatRoomAccessService.checkInit();
    			model.addAttribute("AllChatRoom", chatService.findAllChatRoom());
    			model.addAttribute("AllAccessList", chatService.findAllAccessListToMap());
    			if(req.getAttribute("isAdmin") != null) model.addAttribute("isAdmin", true);
    			return "chatPage/webChat";    		    			
    		} else {
    			return MemberCheckMethod.redirectAfterAlert("사용 권한이 없습니다.", "/", resp);
    		}
    	} else {
			return MemberCheckMethod.redirectAfterAlert("로그인이 필요한 서비스 입니다.", "/", resp);
    	}
    }
    
    @PostMapping("/room")
    public String enterChatRoom (@RequestParam Map<String, String> roomData, Model model, Principal principal) {
    	Long roomId = Long.parseLong(roomData.get("roomId"));
    	Long roomAccessId = Long.parseLong(roomData.get("roomAccessId"));
    	System.err.println(roomId + ", " + roomAccessId);
    	try {
    		Map<String, ChatRoomDto> roomInfo = chatService.enterChatRoom(roomId, roomAccessId, principal.getName());
    		model.addAttribute("RoomInfo", roomInfo);
    	} catch (Exception e) {
    		model.addAttribute("ErrorMsg", e.getMessage());
            return "chatPage/webChat :: chatRoomFrag";
    	}
    	return "chatPage/webChat :: chatRoomFrag";
    }
}