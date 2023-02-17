package com.Tingle.entity;

<<<<<<< HEAD
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="med") 
=======
import javax.persistence.*;

import lombok.*;

@Entity
@Table (name = "med")
>>>>>>> a7afb25556fee92da7cbc1006d1791634ead9c80
@Getter
@Setter
@ToString
public class Med {
<<<<<<< HEAD

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "med_id")
	private Long medId;
	
	private String medName;
	
	private String medInfo;
	
=======
	@Id
	@Column(name="med_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(name="med_name")
	String medName;
	
	@Column(name="med_info")
	String medInfo;
>>>>>>> a7afb25556fee92da7cbc1006d1791634ead9c80
}
