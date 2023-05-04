package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class PlayerList {
    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public ValidationResult validatePlayerList() {
        if (players.size() > 150) {
            return new ValidationResult(false, "There are more than 150 players.");
        }



        Map<String, Integer> teeTimeCount = new HashMap<>();

        for (Player player : players) {
            String teeTime = player.getTeeTime();
            teeTimeCount.put(teeTime, teeTimeCount.getOrDefault(teeTime, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : teeTimeCount.entrySet()) {
            if (entry.getValue() < 2) {
                return new ValidationResult(false, "Tee time " + entry.getKey() + " has less than 2 players.");
            }

            if (entry.getValue() > 4) {
                return new ValidationResult(false, "Tee time " + entry.getKey() + " has more than 4 players.");
            }
        }

        return new ValidationResult(true, "Player list is valid.");
    }

    public void assignSupervisors() {
        Map<String, List<Player>> teeTimeGroups = new HashMap<>();

        for (Player player : players) {
            String teeTime = player.getTeeTime();
            teeTimeGroups.computeIfAbsent(teeTime, k -> new ArrayList<>()).add(player);
        }

        for (List<Player> group : teeTimeGroups.values()) {
            int groupSize = group.size();
            for (int i = 0; i < groupSize; i++) {
                Player currentPlayer = group.get(i);
                Player supervisor;
                if (groupSize == 2) {
                    supervisor = group.get((i + 1) % groupSize); // The other player in the group
                } else {
                    supervisor = group.get((i + 1) % groupSize); // The next player in the group
                }
                currentPlayer.setSupervisor(supervisor.getUsername());
            }
        }
    }
}

class Player {
    private String username;
    private String teeTime;
    private String supervisor;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeeTime() {
        return teeTime;
    }

    public void setTeeTime(String teeTime) {
        this.teeTime = teeTime;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
}

