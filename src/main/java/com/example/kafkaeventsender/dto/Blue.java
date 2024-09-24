package com.example.kafkaeventsender.dto;


import java.util.Map;

public class Blue{
	private Map<String, String> players;
	private String name;

	public Blue() {
	}

	public Map<String, String> getPlayers() {
		return players;
	}

	public Blue setPlayers(Map<String, String> players) {
		this.players = players;
		return this;
	}

	public String getName() {
		return name;
	}

	public Blue setName(String name) {
		this.name = name;
		return this;
	}
}
