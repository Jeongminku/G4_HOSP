package com.Tingle.G4hosp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
	
	@GetMapping("/selectDoc")
	public String test(Model model) {
		
		return "ReservationPage/SelectDoc";
	}
}
