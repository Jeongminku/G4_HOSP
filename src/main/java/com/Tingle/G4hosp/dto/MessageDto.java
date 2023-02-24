package com.Tingle.G4hosp.dto;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MessageDto {
	private String alertMessage;
	private String returnUrl;
}
