package io.github.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Group {

    //global

    public static List<Group> getGroups() {
        return groups;
    }

    private static final List<Group> groups = new ArrayList<>();

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    private static final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    //local

    public Team getTeam() {
        return team;
    }

    private final Team team;

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    private final Scoreboard scoreboard;

    //methods

    public void allowFriendlyFire() {
        this.team.allowFriendlyFire();
    }

    public void setAllowFriendlyFire(boolean allow) {
        this.team.setAllowFriendlyFire(allow);
    }

    public void setDisplayName(String name) {
        this.team.setDisplayName(name);
    }

    public String getDisplayName() {
        return this.team.getDisplayName();
    }

    public void addPlayer(Player player) {
        this.team.addEntry(player.getName());
    }

    public void removePlayer(Player player) {
        this.team.removeEntry(player.getName());
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (String entry : this.team.getEntries()) {
            players.add(Bukkit.getPlayer(entry));
        }
        return players;
    }

    public void setNameTagVisibility(OptionStatus visibility) {
        this.team.setOption(Team.Option.NAME_TAG_VISIBILITY, convertStatus(visibility));
    }

    public void setCollisionRule(OptionStatus collisionRule) {
        this.team.setOption(Team.Option.COLLISION_RULE, convertStatus(collisionRule));
    }

    public void setDeathMessageVisibility(OptionStatus visibility) {
        this.team.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, convertStatus(visibility));
    }

    public void unregister() {
        this.team.unregister();
    }

    public boolean hasPlayer(Player player) {
        return this.getPlayers().contains(player);
    }

    //constructors

    public Group(String name, List<Player> players) {
        scoreboard = scoreboardManager.getNewScoreboard();
        this.team = scoreboard.registerNewTeam(name);
        for (Player player : players) {
            this.team.addEntry(player.getName());
        }
    }

    public Group(String name, Collection<? extends Player> players) {
        scoreboard = scoreboardManager.getNewScoreboard();
        this.team = scoreboard.registerNewTeam(name);
        for (Player player : players) {
            this.team.addEntry(player.getName());
        }
    }

    public Group(String name) {
        scoreboard = scoreboardManager.getNewScoreboard();
        this.team = scoreboard.registerNewTeam(name);
    }

    public enum OptionStatus {
        EVERYBODY,
        NOBODY,
        TEAM_ONLY,
        OTHER_TEAMS_ONLY
    }

    //util

    private Team.OptionStatus convertStatus(OptionStatus status) {
        return switch (status) {
            case EVERYBODY -> Team.OptionStatus.ALWAYS;
            case NOBODY -> Team.OptionStatus.NEVER;
            case TEAM_ONLY -> Team.OptionStatus.FOR_OWN_TEAM;
            case OTHER_TEAMS_ONLY -> Team.OptionStatus.FOR_OTHER_TEAMS;
        };
    }
}
