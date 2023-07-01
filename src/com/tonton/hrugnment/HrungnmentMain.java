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
				case "reload":
					if(pl.isOp())
						ReligionManager.Init().ReloadFile();
					else
						pl.sendMessage("У вас нет доступа к команде");
				case "help":
					pl.sendMessage("/religion change tar/com/vam/sub/naz/mol"
							+ "\n tar(Татарист)-имеет 5 сердец. имеет скорость 2 уровня при беге. может стрелять бесконечными стрелми, если нажата левая кнопка мыши и в руках находится палка"
							+ "\n com(Коммунист)-если рядом с ним есть игроки религии коммунист, то даётся скорость и спешка(уровень эффетов прямо пропорционален колличеству игроков рядом с религией коммунист)"
							+ "\n vam(Вампир)-горит, если не на улице день и не имеет под собой крыши. Так же горит в аду и энде(не зависимо от времени суток). При ударе сущности востанавливает хп, равный 20% от нанесённого урона"
							+ "\n sub(Рыба)-на суше получает иссушение. В воде получает грацию дельфина 1 уровня и подводное дыхание"
							+ "\n naz(Нацист)-имеет медлительность 2 уровня, но при этом имеет 15 сердец и при убийстве моба с некоторым шансом может получить регенерацию и силу"
							+ "\n mol(Кротист)-если находится выше 40 высоты, то получает медлительность 2 уровня и слепоту, иначе получает скорость 1 уровня, спешу 2, ночное зрение"
							+"\n Для смены религии нужно получить урон в размере 10 сердец(в общей сумме). Если изначально у вас нет религии, то вы можете её поменять сразу"
							+"\n Если вы умрёте, то счётчик урона сбросится"
							+"\n /religion info - узнать информацию о вас. Если не работает, то выбирете религию");
					
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
		System.out.println("ALL OKEY");
		 getServer().getPluginManager().registerEvents(new PlayerLister(), this);
		 getServer().getPluginManager().registerEvents(new EntityLister(), this);
		 ReligionManager.Init().Load();
	}

	public void onDisable(){
		
	}
}
