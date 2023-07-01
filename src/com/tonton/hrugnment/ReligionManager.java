package com.tonton.hrugnment;

import java.awt.desktop.UserSessionEvent.Reason;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import org.bukkit.entity.Player;

public class ReligionManager {
private static ReligionManager _main;
private File _mainFile;
private List<Religion> _religions=new ArrayList<Religion>();
public static ReligionManager Init() {
	if(_main==null)
		_main=new ReligionManager();
	return _main;
}
public ReligionManager() {
	String path="";

	String pathMain=ReligionManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	
	for(int i=0;i<pathMain.split("/").length-1;i+=1) {
		
		path+=pathMain.split("/")[i]+File.separator;
	}
	path+=File.separator+"Hrugnment";
	
	File dir=new File(path);
	
	if(dir.exists()==false) {
		dir.mkdir();
		
	}
	_mainFile=new File(path+File.separator+"Religions.txt");
	try {
		_mainFile.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
	
		System.out.println(e.getMessage());
	}
}
public int GetIndexReligion(Player pl) {
int i=0;
	for(Religion rel:_religions) {
		if(rel.GetNamePlayer().equals(pl.getName()))
			return i;
		i+=1;
	}
	return -1;	
}
public void Load() {
	_religions=new ArrayList<Religion>();
	try {
		List<String> religions=Files.readAllLines(_mainFile.toPath());
		for(String relig:religions) {
			String[] data=relig.split(":");
			Religion reg=new Religion(ReligionType.valueOf(data[1]),Bukkit.getOfflinePlayer(data[0]),Integer.parseInt(data[2]));
			_religions.add(reg);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void ReloadFile() {
	_mainFile.delete();
	try {
		_mainFile.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void Save() {
	try {
		
		ReloadFile();
		for(Religion religion:_religions) {
			
			String st=religion.GetNamePlayer()+":"+religion.GetType().toString()+":"+religion.GetPerentance()+"\n";
		
			Files.writeString(_mainFile.toPath(), st, StandardOpenOption.APPEND);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}
}
public Religion GetReligion(Player pl) {
	if(pl==null)
		return null;
	for(Religion rel:_religions) {
		if(rel.GetNamePlayer().equals(pl.getName())) {
			rel.UpdatePlayer(pl);
		
			return rel;
		}
	}
	
	return null;
}
public String ReligionTypeToNormalString(ReligionType rel) {
	switch(rel) {
	case vam:
		return "Вампир";
	case com:
		return "Коммунист";
	case mol:
		return "Кротист";	
	case tar:
		return "Татарист";
	case naz:
		return "Нацист";
	case sub:
		return "Рыба";
	default:
		return "Лохист";
	}
}
public void CreateReligion(Player pl) {
	
}
public void SetReligion(Player pl,ReligionType relig) {
	if(pl==null) {
		
		return;
	}
		
	Religion findReligion=GetReligion(pl);
	if(findReligion==null) {
		_religions.add(new Religion(relig,pl));
		Save();
		
	}else {
		_religions.get(GetIndexReligion(pl)).TryChangeReligion(relig);
		Save();
	}
	
}
}
