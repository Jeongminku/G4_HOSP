package com.Tingle.G4hosp.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTime{
	
	@CreatedBy
	@Column(updatable = false)
	private String createBy; // 등록자
	
	@LastModifiedBy
	private String modfiedBy; //수정자
}
