package com.tonton.hrugnment;

import org.bukkit.entity.Player;

public class TimerPlayer {
private Player _player;
private long _time;
private long _delay;
public TimerPlayer(Player pl,int delay) {
_player=pl;
_time=System.currentTimeMillis();
_delay=delay;
}
public Player GetPlayer() {
	return _player;
}
public boolean EndTimer() {

		return System.currentTimeMillis()-_time>_delay;
}
}
