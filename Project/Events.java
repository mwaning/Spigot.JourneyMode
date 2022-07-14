package com.draconequus.JourneyMode;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {
	private JourneyMode plugin;
	String B = ChatColor.AQUA + "";
	String R = ChatColor.RESET + "";
	String JM = B + "[JM]" + R + " ";

	public Events(JourneyMode plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getDisplayName();
		
		if (!ItemsFile.get().contains(playerName)) {
			ItemsFile.get().set(playerName, "");
			ItemsFile.save();
			
			player.sendMessage(JM + "Welcome to JourneyMode, " + B + playerName + R + "!\n" +
			"This is a game mode inspired by Terraria - once you get a block, you have the power to summon copies of it.\n" +
			"To learn more about this plugin, type " + B + "/jm h");
		}
	}
	
	@EventHandler
	public void onLoot(EntityPickupItemEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		String playerName = player.getDisplayName();
		Material item = event.getItem().getItemStack().getType();
		
		List<String> itemList = ItemsFile.get().getStringList(playerName);
		List<String> exceptionsList = ExceptionsFile.get().getStringList("Exceptions");
		
		if (!itemList.contains(String.valueOf(item)) && !exceptionsList.contains(String.valueOf(item))) {
			itemList.add(String.valueOf(item));
			ItemsFile.get().set(playerName, itemList);
			ItemsFile.save();
			player.sendMessage(JM + "New item " + B + String.valueOf(item).toLowerCase() + R + " was added to your collection.");
		}
	}
}
