package com.Tingle.G4hosp.dto;

import java.util.*;

import com.Tingle.G4hosp.constant.MessageType;

import lombok.Data;

@Data
public class ChatMessageDto {
	private MessageType type;
    private Long roomId;
    private String sender;
    private String message;
    private Set<String> joinedMember = new HashSet<>();
}
