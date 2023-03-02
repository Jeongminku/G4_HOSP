package com.Tingle.G4hosp.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.Tingle.G4hosp.constant.Role;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.QuickReservation;
import com.Tingle.G4hosp.service.MemberService;
import com.Tingle.G4hosp.service.QuickReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyWebSocketHandler extends TextWebSocketHandler{
	
    @Autowired
    private MemberService memberService;
    private final QuickReservationService quickReservationService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 클라이언트로부터 전달받은 데이터 처리
        ObjectMapper mapper = new ObjectMapper();
        QuickReservation QR = mapper.readValue(message.getPayload(), QuickReservation.class);
        if(QR.getQryn().equals("N")) {
            // 로그인한 이용자가 'admin'인 경우
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.err.println(authentication.getName());
            Member admin = memberService.findByLoginid(authentication.getName());
             if(admin.getRole().equals(Role.ADMIN)) {
                // WebSocketSession을 이용하여 클라이언트에게 알람 전송
            	System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            	System.err.println("test!!!!!!");
                String alertMessage = "처리되지 않은 비회원 예약건이 있습니다.";
                session.sendMessage(new TextMessage(alertMessage));
            }
        } 
    }

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			List<QuickReservation> Qlist = quickReservationService.QRList();
			if(session.getPrincipal() != null) {
				Member member = memberService.findByLoginid(session.getPrincipal().getName());
				if(member.getRole().equals(Role.ADMIN)) {
					for (QuickReservation QR : Qlist){
						if(QR.getQryn().equals("N")) {
							String alertMessage = "처리되지 않은 비회원 예약건이 있습니다.";
							session.sendMessage(new TextMessage(alertMessage));
						}
					}
				}else {
					session.close();
				}				
			}
		}
	}

