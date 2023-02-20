package com.Tingle.G4hosp.constant;

import lombok.Getter;

@Getter
public enum NotAvailableDay {
	MON ("fc-day-mon"),
	TUE ("fc-day-tue"), 
	WED ("fc-day-wed"), 
	THU ("fc-day-thu"), 
	FRI ("fc-day-fri"), 
	SAT ("fc-day-sat"), 
	SUN ("fc-day-sun"),
	CUSTOM("");

	private String dayClassName;
	
	NotAvailableDay (String day) {
		this.dayClassName = day;
	}
}
