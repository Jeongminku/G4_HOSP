package com.Tingle.G4hosp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Tingle.G4hosp.dto.ReservationDto;
import com.Tingle.G4hosp.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	@GetMapping("/selectDoc")
	public String selectDocPage (Model model) {
		model.addAttribute("DocList", reservationService.findAllDoctor());
		return "ReservationPage/SelectDoc";
	}
	
	@GetMapping("/{doctorId}")
	public String selectDayPage (Model model, @PathVariable("doctorId") Long doctorId) {
		model.addAttribute("ReservationDto", new ReservationDto());
		return "ReservationPage/SelectDate";
	}
	
	@PostMapping("/checkIn")
	public String createReservation (ReservationDto reservationDto, Principal principal) {
		System.err.println(reservationDto);
		return "ReservationPage/SelectDate";
	}
}
