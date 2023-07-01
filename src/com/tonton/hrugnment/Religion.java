package com.tonton.hrugnment;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Religion {
private ReligionType _religion;
private Player _player;
private OfflinePlayer _playerOffline;
private int _repentance=10;
private final int _repentanceMax=10;
private boolean _isRepented=false;
public int GetPerentance() {
	return _repentance;
}
public Religion(ReligionType religion,OfflinePlayer player,int repentance) {
	_repentance=repentance;
	if(_repentance<=0)
		_isRepented=true;
	_religion=religion;
	_playerOffline=player;
}
public void UpdatePlayer(Player pl) {
	_player=pl;
	
}
public Religion(ReligionType religion,Player player) {
	_religion=religion;
	_playerOffline=Bukkit.getOfflinePlayer(player.getName());
	_player=player;
	SendMessage("Теперь ваша религия "+ReligionManager.Init().ReligionTypeToNormalString(religion));
	Update();
}
public ReligionType GetType() {
	return _religion;
}
public void Update() {
	
	_player.removePotionEffect(PotionEffectType.SPEED);
	_player.removePotionEffect(PotionEffectType.WATER_BREATHING);
	_player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
	
	switch(_religion) {
	case tar:
		_player.setMaxHealth(10);
		
		break;
	case naz:
		_player.setMaxHealth(30);
		break;
	default:
		_player.setMaxHealth(20);
		break;
	}
}
public void SendMessage(String msg) {
	if(_player!=null) {
		_player.sendMessage(msg);
	}
}
public String GetNamePlayer() {
	if(_player==null) {
		return _playerOffline.getName();
	}

	return _player.getName();
}
public void TryChangeReligion(ReligionType religion) {
	if(_isRepented) {
		SendMessage("Вы сменили религию на "+ReligionManager.Init().ReligionTypeToNormalString(religion));
		_isRepented=false;
		_religion=religion;
		_repentance=_repentanceMax;
		Update();
	}else {
		SendMessage("Вы недостаточно каялись!!!");
	}
}
public void ResetRepent() {
	if(_isRepented)
		return;
	_repentance=_repentanceMax;
	ReligionManager.Init().Save();
	SendMessage("Вы слишком хорошо каялись.За такое упорство бог обнулил ваши покаяния");
}
public void Repent() {
	if(_repentance<=0)
		return;
	_repentance-=1;
	ReligionManager.Init().Save();
	if(_repentance<=0) {
		_isRepented=true;
		SendMessage("Вы достаточно покаялись. Теперь вы можете сменить религию");
	}
}
}
