package com.saurav.apifootball.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.saurav.apifootball.model.Country;
import com.saurav.apifootball.model.Leagues;
import com.saurav.apifootball.model.TeamStanding;

@Service
public class FootballApiHttpClient {

	private static final String KEY = "APIkey";
	private static final String ACTION = "action";
	private static final String STANDINGSACTION = "get_standings";
	private static final String COUNTRIESACTION = "get_countries";
	private static final String LEAGUESACTION = "get_leagues";
	private final RestTemplate restTemplate;

	@Value("${apifootball.base.url}")
	private String baseUrl;

	@Value("${apifootball.apikey}")
	private String apiKey;

	@Autowired
	public FootballApiHttpClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Country[] getCountries() {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(ACTION, COUNTRIESACTION);
		UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
		queryParams.forEach(builder::queryParam);
		return this.restTemplate
				.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()), Country[].class)
				.getBody();
	}

	public Leagues[] getLeagues(int countryId) {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(ACTION, LEAGUESACTION);
		queryParams.put("country_id", String.valueOf(countryId));
		UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
		return this.restTemplate
				.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()), Leagues[].class)
				.getBody();
	}

	public TeamStanding[] getTeamStanding(int leagueId) {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(ACTION, STANDINGSACTION);
		queryParams.put("league_id", String.valueOf(leagueId));
		UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
		return this.restTemplate
				.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()), TeamStanding[].class)
				.getBody();
	}

	private UriComponentsBuilder getUriComponentsBuilder(String url, Map<String, String> queryParams) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam(KEY, apiKey);
		queryParams.forEach(builder::queryParam);
		return builder;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
