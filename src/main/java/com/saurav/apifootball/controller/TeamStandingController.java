package com.saurav.apifootball.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.apifootball.dto.TeamStandingDto;
import com.saurav.apifootball.dto.TeamStandingRequest;
import com.saurav.apifootball.service.TeamStandingService;

@RestController
public class TeamStandingController {

	private Logger log = LoggerFactory.getLogger(TeamStandingController.class);

	@Autowired
	TeamStandingService teamStandingService;

	@GetMapping(value = "/getTeamStanding")
	public ResponseEntity<TeamStandingDto> getTeamStanding(@Valid TeamStandingRequest teamStandingRequest) {
		log.info("Request payload {}", teamStandingRequest);
		return new ResponseEntity<>(teamStandingService.getTeamStanding(teamStandingRequest), HttpStatus.OK);

	}

}
