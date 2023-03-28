package com.tonton.hrugnment;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class EntityLister implements Listener {
	
private	Random _randomGenerator = new Random();
@EventHandler
public void OnEntityDamage(EntityDamageEvent event) {
	if(event.getEntity() instanceof Player) {
		Player pl =(Player)event.getEntity();
		Religion rel=ReligionManager.Init().GetReligion(pl);
		if(rel!=null) {
			rel.Repent();
		}
	}
}
@EventHandler
public void OnEntityDie(EntityDeathEvent event) {
	if(event.getEntity() instanceof Player) {
		Religion rel=ReligionManager.Init().GetReligion((Player)event.getEntity());
		if(rel!=null)
			rel.ResetRepent();
	}
		Player pl=event.getEntity().getKiller();
		Religion rel=ReligionManager.Init().GetReligion(pl);
		if(pl==null||rel==null)
			return;
	switch(rel.GetType()) {
	case can:
		if(_randomGenerator.nextInt(0,100)>90)
				pl.getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.ROTTEN_FLESH));
		
		
		break;
	case naz:
		if(_randomGenerator.nextInt(0,100)>50) {
			
			pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,100,2));
			pl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
		}
	default:
		break;
	}
	
	
}
}
