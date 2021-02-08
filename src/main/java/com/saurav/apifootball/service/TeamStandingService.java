package com.saurav.apifootball.service;

import org.springframework.stereotype.Service;

import com.saurav.apifootball.dto.TeamStandingDto;
import com.saurav.apifootball.dto.TeamStandingRequest;

@Service
public interface TeamStandingService {

	public TeamStandingDto getTeamStanding(TeamStandingRequest teamStandingRequest);

}
