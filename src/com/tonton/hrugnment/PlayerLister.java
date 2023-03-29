package com.tonton.hrugnment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PlayerLister implements Listener  {
	Random _random=new Random();
	private List<Material> _sendMaterial=new ArrayList<Material>();
		public PlayerLister() {
			_sendMaterial.add(Material.IRON_BLOCK);
			_sendMaterial.add(Material.DIAMOND_BLOCK);
			_sendMaterial.add(Material.GOLD_BLOCK);
		}

	    public int CountNearestPlayers(Player pl) {
	    	int count=0;
	    	List<Entity> near = pl.getNearbyEntities(10.0D, 10.0D, 10.0D);
    		for(Entity entity : near) {
    		    if(entity instanceof Player) {
    		    	count+=1;
    		    	if(count==3)
    		    		return count;
    		        //do stuff here
    		    }
    		}
    		return count;
	    }

	 
	    public int CountItem(Player p,Material ammomat) {
	    	int amount=0;
	    	for(int i = 0; i < p.getInventory().getSize(); i++){
	    	  ItemStack itm = p.getInventory().getItem(i);
	    	  if(itm != null && itm.getType().equals(ammomat)){
	    		  amount+=itm.getAmount();
	    	  }
	    	}
	    	return amount;
	    }
	    @EventHandler
	    public void onInteract(PlayerInteractEvent e){
	        
	        if((e.getAction() == Action.RIGHT_CLICK_AIR)){
	           
	            Player s = e.getPlayer();
	            if(s.getItemInHand()!=null&&s.getItemInHand().getType()==Material.STICK) {
	            Religion rel=ReligionManager.Init().GetReligion(s);
	            TimerPlayer get=TimerPlayerManager.Get().GetTimer(s);
	            if(get==null) {
	            	TimerPlayerManager.Get().CreateTimer(new TimerPlayer(s,200));
	            	get=TimerPlayerManager.Get().GetTimer(s);
	            }
	            if(!get.EndTimer())
	            	return;
	            if(get.EndTimer()) {
	            	
	            	TimerPlayerManager.Get().DeleteTimer(s);
	            	
	            }
	            if(rel!=null&&rel.GetType()==ReligionType.tar) {
	                    Arrow a = Bukkit.getWorld("world").spawnArrow(s.getEyeLocation().add(s.getEyeLocation().getDirection().getX() * 1.5, 0, s.getEyeLocation().getDirection().getZ() * 1.5), new Vector(0,0,0), 1, 0);
	                    a.setVelocity(s.getEyeLocation().getDirection().multiply(2.0D));
	            }
	            }
	        }
	       
	    }
	    @EventHandler
	    public void OnPlayerMove(PlayerMoveEvent event) {
	    	Player pl=event.getPlayer();
	    	Religion rel=ReligionManager.Init().GetReligion(pl);
	    	if(rel==null)
	    		return;
	    	switch(rel.GetType()) {
	    	case mol:
	    		
	    		if(pl.getLocation().getY()>40) {
	    			pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,200,5));
	    			pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,200,1));
	    		}else {
	    			pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,0));
	    			pl.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,200,2));
	    			pl.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1000,3));
	    		}
	    		break;
	    	case tar:
	    	
	    	
               
	    		pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,1));
	    		break;
	    	case com:
	    		int count=CountNearestPlayers(pl);
	    		if(count!=0) {
	    			
	    			pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,count));
	    			pl.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,200,count));
	    		}
	    		break;
			default:
				break;
	    	}
	    }
	    @EventHandler
	    public void OnDrop(PlayerDropItemEvent event) {
	    	Religion rel=ReligionManager.Init().GetReligion(event.getPlayer());
	    	if(event.getItemDrop().getItemStack().getType()==Material.ARROW&&rel!=null&&rel.GetType()==ReligionType.tar) {
	    		event.setCancelled(true);
	    	}
	    }
	    @EventHandler
	    public void OnEat(PlayerItemConsumeEvent event) {
	    	Player pl=event.getPlayer();
	    	Religion rel=ReligionManager.Init().GetReligion(pl);
	    	if(rel==null)
	    		return;
	    	if(event.getItem().getType()==Material.ROTTEN_FLESH&&rel.GetType()==ReligionType.can) {
	    		event.setCancelled(true);
	    		pl.getItemInHand().setAmount(pl.getItemInHand().getAmount()-1);
	    		pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,60,2));
	    	}
	    }
		@EventHandler
		  public void onPlayerChat(PlayerChatEvent event) {
			  String msg=event.getMessage();
			  Player pl=event.getPlayer();
			  if(ReligionManager.Init().GetReligion(pl)==null)
				  event.setMessage("["+ReligionManager.Init().ReligionTypeToNormalString(ReligionType.nil)+"]:"+msg);
			  else
			  event.setMessage("["+ReligionManager.Init().ReligionTypeToNormalString(ReligionManager.Init().GetReligion(pl).GetType())+"]:"+msg);
		  }
}

