package com.saurav.apifootball.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saurav.apifootball.dto.TeamStandingDto;
import com.saurav.apifootball.dto.TeamStandingRequest;
import com.saurav.apifootball.httpclient.FootballApiHttpClient;
import com.saurav.apifootball.model.Country;
import com.saurav.apifootball.model.Leagues;
import com.saurav.apifootball.model.TeamStanding;

@Service
public class TeamStandingServiceImpl implements TeamStandingService {

	@Autowired
	FootballApiHttpClient footballApiHttpClient;

	private Logger log = LoggerFactory.getLogger(TeamStandingServiceImpl.class);

	@Override
	public TeamStandingDto getTeamStanding(TeamStandingRequest teamStandingRequest) {

		log.info("fetching team standings!");

		TeamStanding currentTeamStanding = new TeamStanding();
		currentTeamStanding.setTeamName(teamStandingRequest.getTeamName());
		currentTeamStanding.setCountryName(teamStandingRequest.getCountryName());
		currentTeamStanding.setLeagueName(teamStandingRequest.getLeagueName());
		List<Country> countries = getCountries();
		Country country = getCountryByName(teamStandingRequest, countries);
		if (Objects.isNull(country) || country.getId() == 0) {

			log.info("No Data found for requested country, returning!");
			return TeamStandingDto.from(currentTeamStanding);
		}
		currentTeamStanding.setCountryId(country.getId());

		List<Leagues> leaguesList = getLeagues(country.getId());
		Leagues leagues = getLeaguesByName(teamStandingRequest, leaguesList);
		if (Objects.isNull(leagues) || leagues.getLeagueId() == 0) {

			log.info("No Data found for requested league, returning!");
			return (TeamStandingDto.from(currentTeamStanding));
		}

		currentTeamStanding.setLeagueId(leagues.getLeagueId());
		List<TeamStanding> teamStandings = getTeamStanding(leagues.getLeagueId());
		TeamStanding teamStandingsFiltered = getFilteredTeamStanding(teamStandingRequest, teamStandings);

		if (Objects.isNull(teamStandingsFiltered) || teamStandingsFiltered.getTeamId() == 0) {
			log.info("No Data found for requested standing, returning!");
			return TeamStandingDto.from(currentTeamStanding);
		}

		teamStandingsFiltered.setCountryId(country.getId());
		log.info("returning fetched standing!");
		return TeamStandingDto.from(teamStandingsFiltered);
	}

	private List<Country> getCountries() {
		return new ArrayList<>(Arrays.asList(footballApiHttpClient.getCountries()));
	}

	private List<Leagues> getLeagues(int countryId) {
		return new ArrayList<>(Arrays.asList(footballApiHttpClient.getLeagues(countryId)));
	}

	private List<TeamStanding> getTeamStanding(int leagueId) {
		return new ArrayList<>(Arrays.asList(footballApiHttpClient.getTeamStanding(leagueId)));
	}

	private Country getCountryByName(TeamStandingRequest teamStandingRequest, List<Country> countries) {
		return countries.stream().filter(c -> teamStandingRequest.getCountryName().equalsIgnoreCase(c.getName()))
				.findFirst().orElse(null);
	}

	private Leagues getLeaguesByName(TeamStandingRequest teamStandingRequest, List<Leagues> leaguesList) {
		return leaguesList.stream().filter(l -> teamStandingRequest.getLeagueName().equalsIgnoreCase(l.getLeagueName()))
				.findFirst().orElse(null);
	}

	private TeamStanding getFilteredTeamStanding(TeamStandingRequest teamStandingRequest,
			List<TeamStanding> teamStandings) {
		return teamStandings.stream().filter(t -> teamStandingRequest.getTeamName().equalsIgnoreCase(t.getTeamName()))
				.findFirst().orElse(null);
	}

}
