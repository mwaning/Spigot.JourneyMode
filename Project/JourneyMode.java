package com.draconequus.JourneyMode;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class JourneyMode extends JavaPlugin {
	Plugin plugin = this;
	public ItemsFile itemsFile;
	public ExceptionsFile exceptionsFile;

	@Override
	public void onEnable() {
		new Events(this);
		
		this.getCommand("JourneyMode").setExecutor(new Command_JourneyMode());
		this.getCommand("JM").setExecutor(new Command_JourneyMode());
		this.itemsFile = new ItemsFile(this);
		this.exceptionsFile = new ExceptionsFile(this);
		
	}
	
	@Override
	public void onDisable() {
		
	}
}
