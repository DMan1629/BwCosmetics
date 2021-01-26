package me.DMan16.BwCosmetics;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.DMan16.BwCosmetics.Command.CommandListener;
import me.DMan16.BwCosmetics.Config.Config;
import me.DMan16.BwCosmetics.Listeners.EffectListener;
import me.DMan16.BwCosmetics.Listeners.MenuListener;
import me.DMan16.BwCosmetics.Utils.Permissions;
import me.DMan16.BwCosmetics.Utils.PlayersData;
import me.DMan16.BwCosmetics.Utils.Utils;

public class Main extends JavaPlugin {
	public static Main main;
	public static final String pluginName = "BwCosmetics";
	public static final String pluginNameColors = "&c&lBw&b&lCosmetics";
	public static Config Config;
	public static Permissions PermissionsManager;
	public static PlayersData PlayersDataManager;

	public void onEnable() {
		main = this;
		String versionMC = Bukkit.getServer().getVersion().split("\\(MC:")[1].split("\\)")[0].trim().split(" ")[0].trim();
		if (Integer.parseInt(versionMC.split("\\.")[0]) < 1 || Integer.parseInt(versionMC.split("\\.")[1]) < 16) {
			Utils.chatColorsLogPlugin("&cunsupported version! Please use version 1.16+");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		try {
			Config = new Config();
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE,Utils.chatColorsPlugin("Could not save config file! Error:"));
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		try {
			PlayersDataManager = new PlayersData();
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE,Utils.chatColorsPlugin("Could not access players data folder! Error:"));
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		PermissionsManager = new Permissions();
		registerListeners();
		new CommandListener();
		Utils.chatColorsLogPlugin("&aLoaded, running on version: &f" + versionMC + "&a, Java version: &f" + javaVersion());
	}
	
	private String javaVersion() {
		String javaVersion = "";
		Iterator<Entry<Object,Object>> systemProperties = System.getProperties().entrySet().iterator();
		while (systemProperties.hasNext() && javaVersion.isEmpty()) {
			Entry<Object,Object> property = (Entry<Object,Object>) systemProperties.next();
			if (property.getKey().toString().equalsIgnoreCase("java.version")) javaVersion = property.getValue().toString();
		}
		return javaVersion;
	}

	public void onDisable() {
		Utils.chatColorsLogPlugin("&aDisabling...");
		PermissionsManager.unregisterPermissions();
		Utils.chatColorsLogPlugin("&aDisabed successfully!");
	}

	private void registerListeners() {
		PluginManager manager = Bukkit.getPluginManager();
		manager.registerEvents(new EffectListener(),this);
		manager.registerEvents(new MenuListener(),this);
	}
}