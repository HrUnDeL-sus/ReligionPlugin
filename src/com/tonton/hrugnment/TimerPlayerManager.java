package com.tonton.hrugnment;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class TimerPlayerManager {
private static TimerPlayerManager _main;
private List<TimerPlayer> _timersPlayer=new ArrayList<TimerPlayer>();
public static TimerPlayerManager Get() {
	if(_main==null)
		_main=new TimerPlayerManager();
	return _main;
}
public int FindTimerIndex(Player pl) {
	for(int i=0;i<_timersPlayer.size();i+=1) {
		if(_timersPlayer.get(i).GetPlayer().getName().equals(pl.getName()))
			return i;
	}
	return -1;
}
public void CreateTimer(TimerPlayer tp) {
	_timersPlayer.add(tp);
}
public void DeleteTimer(Player pl) {
	int index=FindTimerIndex(pl);
	if(index==-1)
		return;
	TimerPlayer tp=_timersPlayer.get(index);
	_timersPlayer.remove(index);	
}
public TimerPlayer GetTimer(Player pl) {
	int index=FindTimerIndex(pl);
	if(index==-1)
		return null;
	return _timersPlayer.get(index);
}
}
