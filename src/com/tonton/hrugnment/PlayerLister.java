package com.tonton.hrugnment;
import org.bukkit.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerLister implements Listener  {
	Random _random=new Random();
	private List<Material> _sendMaterial=new ArrayList<Material>();
		public PlayerLister() {
			_sendMaterial.add(Material.IRON_BLOCK);
			_sendMaterial.add(Material.DIAMOND_BLOCK);
			_sendMaterial.add(Material.GOLD_BLOCK);
		}
	    @EventHandler
	    public void OnExit(PlayerQuitEvent event) {
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
	    public int CountItemInInventory(Material get,Player pl) {
	    int amount=0;
	    	for(ItemStack item:pl.getInventory().getContents()) {
	    		   if (item!=null&&item.getType() == get)
	    		    	amount += item.getAmount();
	    	}
	    	return amount;
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
	    		pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 50, 1));
	    		if(CountItemInInventory(Material.ARROW,pl)==0){
	    			pl.getInventory().addItem(new ItemStack(Material.ARROW));
	    		}
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

