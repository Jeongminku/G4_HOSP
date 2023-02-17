package com.Tingle.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Table (name = "med")
@Getter
@Setter
@ToString
public class Med {
	@Id
	@Column(name="med_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(name="med_name")
	String medName;
	
	@Column(name="med_info")
	String medInfo;
}
