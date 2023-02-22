package com.Tingle.G4hosp.dto;

import com.Tingle.G4hosp.constant.MessageType;

import lombok.Data;

@Data
public class ChatMessageDto {
	private MessageType type;
//    private String roomId;
    private String sender;
    private String message;
}
