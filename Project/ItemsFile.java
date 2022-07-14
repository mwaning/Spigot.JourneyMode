package com.draconequus.JourneyMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ItemsFile {
	private JourneyMode plugin;
	private static File file = null;
	private static FileConfiguration config = null;
	
	public ItemsFile(JourneyMode plugin) {
		this.plugin = plugin;
		saveDefault();
	}
	
	public static void reload() {
		if (file == null) {
			file = new File(Bukkit.getServer().getPluginManager().getPlugin("JourneyMode").getDataFolder(), "items.yml");
		}
		
		config = YamlConfiguration.loadConfiguration(file);
		
		InputStream defaultStream = (Bukkit.getServer().getPluginManager().getPlugin("VillagerInteraction").getResource("items.yml"));
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			config.setDefaults(defaultConfig);
		}
	}
	
	public static FileConfiguration get() {
		if (config == null) {
			reload();
		}
		return config;
	}

	public static void save() {
		if (config == null || file == null) {
			return;
		}
			
		try {
			get().save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveDefault() {
		if (file == null) {
			file = new File(this.plugin.getDataFolder(), "items.yml");
		}
		
		if (!file.exists()) {
			this.plugin.saveResource("items.yml", false);
		}
	}
	
	public static ArrayList<String> load(String keyName) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(get().getStringList(keyName));
		return list;
	}
}