package com.tonton.hrugnment;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HrungnmentMain extends JavaPlugin {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		
		if(cmd.getName().equalsIgnoreCase("religion")){ 
			Player pl=(Player)sender;
			if(args.length==0)
				return true;
				switch(args[0]) {
				case"change":
					try {
						if(args.length!=2)
							return true;
						ReligionManager.Init().SetReligion(pl,ReligionType.valueOf(args[1]));
					}catch(Exception ex) {
						ex.printStackTrace();
						pl.sendMessage("Неверное название религии!!!");
					}
					break;
				case"info":
					Religion reg=ReligionManager.Init().GetReligion(pl);
					if(reg!=null)
					pl.sendMessage("Религия:"+ReligionManager.Init().ReligionTypeToNormalString(reg.GetType())+"\nСколько каяться:"+reg.GetPerentance());
					break;
				}
				
			}
		return true;
	}
		
	
	public void onEnable(){
		System.out.println("Your plugin has been enabled!");
		 getServer().getPluginManager().registerEvents(new PlayerLister(), this);
		 getServer().getPluginManager().registerEvents(new EntityLister(), this);
		 ReligionManager.Init().Load();
	}

	public void onDisable(){
		
	}
}
