package com.Tingle.G4hosp.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Tingle.G4hosp.dto.ReservationDto;
import com.Tingle.G4hosp.dto.ReservationViewDto;
import com.Tingle.G4hosp.entity.Reservation;
import com.Tingle.G4hosp.service.ReservationNotAvailableService;
import com.Tingle.G4hosp.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
	
	private final ReservationService reservationService;
	private final ReservationNotAvailableService reservationNotAvailableService;
	
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
	public String createReservation (Model model, ReservationDto reservationDto, Principal principal) {
		try {
			Reservation reservationed = reservationService.createReservation(reservationDto, principal.getName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 HH시");
			model.addAttribute("ReservationedDate", reservationed.getReservationDate().format(formatter));
		} catch (Exception e) {
			model.addAttribute("Error", e.getMessage());
		}
		return "ReservationPage/ReservationComplete";
	}
	
	@GetMapping("/listView")
	public String reservationListView (Model model, Principal principal) {
//		if(principal == null) {
//			model.addAttribute("Error", "Need Login");
//			return "member/memberLoginForm";
//		}
		try {
			List<ReservationViewDto> viewList = reservationService.findAllReservationByDoctor(principal.getName());
			model.addAttribute("ViewList", viewList);
		} catch (Exception e) {
			model.addAttribute("Error", e.getMessage());
		}
		return "ReservationPage/ViewReservation";
	}
	
	@GetMapping("/setNotAvailDay")
	public String setNotAvailableDay (Model model, Principal principal) {
		return "ReservationPage/SetNotAvailableDay";
	}
	
	@PostMapping("/setNotAvailDay")
	public String setNotAvailableDayToDB (@RequestParam(name = "notAvailDate", required = false) List<String> notAvailDateList,
										  @RequestParam("dayCheck") List<String> notAvailDayList,
										  Principal principal, Model model) {
		try {
			reservationNotAvailableService.createReservationNotAvailable(principal.getName(), notAvailDateList, notAvailDayList);
			List<String> notAvail = reservationNotAvailableService.findAllNotAvailByDoctor(principal.getName());
			List<ReservationViewDto> viewList = reservationService.findAllReservationByDoctor(principal.getName());
			model.addAttribute("NotAvail", notAvail);
			model.addAttribute("ViewList", viewList);
		} catch (Exception e) {
			model.addAttribute("Error", e.getMessage());
		}
		return "ReservationPage/SetNotAvailableDay";
	}
}
