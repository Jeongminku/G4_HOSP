package com.Tingle.G4hosp.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public class BaseTime {
	@Column(updatable = false)
	@CreatedDate
	private LocalDateTime regDatetime;
	
	@LastModifiedDate
	private LocalDateTime updateDatetime;
}
