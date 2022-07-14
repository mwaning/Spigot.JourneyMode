package com.draconequus.JourneyMode;

import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command_JourneyMode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String B = ChatColor.AQUA + "";
		String R = ChatColor.RESET + "";
		String JM = B + "[JM]" + R + " ";
		
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(JM + "Sorry, this command is only available to players!");
			return true;
		}
		
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].toLowerCase();
		}
		
		Player player = (Player) sender;
		String playerName = player.getDisplayName();
				
		if (args.length == 0) {
			List<String> itemsList = ItemsFile.get().getStringList(playerName);
			NavigableSet<String> itemsSet = new TreeSet<>();
			
			for (String item : itemsList) {
				itemsSet.add(item.toLowerCase());
			}
			
			player.sendMessage(JM + "Your Item Collection:");
			player.sendMessage(itemsSet + "");
		}
		else if (args.length == 1) {
			if (args[0].equals("help") || args[0].equals("h")) {
				player.sendMessage(JM + "JourneyMode Help Section:\n" + 
			    B + "/jm" + R + " - Get a list of your Item Collection.\n" +
				B + "/jm <itemname>" + R + " - Summons a stack of collected items\n" +
				B + "/jm st <playername" + R + " - Send your Item Collection to someone.\n" +
			    B + "/jm e" + R + " - Get a list of items excepted from collection.\n" +
				B + "/jm e <itemname>" + R + " - Add or remove exceptions (Admin only)\n" +
			    "- Item names with spaces use an underscore (_) instead.");
			}
			else if (args[0].equals("exceptions") || args[0].equals("e")) {
				List<String> exceptionsList = ExceptionsFile.get().getStringList("Exceptions");
				NavigableSet<String> exceptionsSet = new TreeSet<>();
				
				for (String exception : exceptionsList) {
					exceptionsSet.add(exception.toLowerCase());
				}
				
				player.sendMessage(JM + "Exceptions List:");
				player.sendMessage(exceptionsSet + "");
			}
			else {
				if (Material.matchMaterial(args[0].toUpperCase()) != null) {
					List<String> itemList = ItemsFile.get().getStringList(playerName);
					if (itemList.contains(args[0].toUpperCase())) {
						Material material = Material.matchMaterial(args[0].toUpperCase());
						ItemStack item = new ItemStack(material, 64);
						player.getInventory().addItem(item);
						player.sendMessage(JM + "Item stack " + B + args[0] + R + " has been added to your inventory.");
					}					
				}
				else {
					player.sendMessage(JM + "Item " + B + args[0] + R + " is not part of your collection.");
				}
			}
		}
		else if (args.length == 2) {
			if (args[0].equals("exceptions") || args[0].equals("e")) {
				List<String> exceptionsList = ExceptionsFile.get().getStringList("Exceptions");
				if (exceptionsList.contains(args[1].toUpperCase())) {
					exceptionsList.remove(args[1].toUpperCase());
					player.sendMessage(JM + "Entry " + B + args[1] + R + " was removed from the Exceptions list.");
				}
				else {
					exceptionsList.add(args[1].toUpperCase());
					player.sendMessage(JM + "Entry " + B + args[1] + R + " was added to the Exceptions list.");

				}
				ExceptionsFile.get().set("Exceptions", exceptionsList);
				ExceptionsFile.save();
			}
			else if (args[0].equals("sendto") || args[0].equals("st")) {
				if (Bukkit.getPlayer(args[1]) != null) {
					player.sendMessage(JM + "Sending your Item Collection to " + B + args[1] + R + ".");
					
					player = Bukkit.getPlayer(args[1]);
					List<String> itemsList = ItemsFile.get().getStringList(playerName);
					NavigableSet<String> itemsSet = new TreeSet<>();
					
					for (String item : itemsList) {
						itemsSet.add(item.toLowerCase());
					}
					
					player.sendMessage(JM + "Player " + B + playerName + R + " sent you their Item Collection:");
					player.sendMessage(itemsSet + "");
				}
				else {
					player.sendMessage(JM + "Player " + B + args[1] + R + " was not found.");
				}
			}
		}
		else {
			player.sendMessage(JM + "That's too many arguments!");
		}
 		
		
		return false;
	}

}
