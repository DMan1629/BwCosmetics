package me.DMan16.BwCosmetics.Command;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import me.DMan16.BwCosmetics.Main;
import me.DMan16.BwCosmetics.Listeners.EffectListener;
import me.DMan16.BwCosmetics.Listeners.MenuListener;
import me.DMan16.BwCosmetics.Utils.Utils;

public class CommandListener implements CommandExecutor {
	public CommandListener() {
		PluginCommand command = Main.main.getCommand(Main.pluginName);
		command.setExecutor(this);
		command.setTabCompleter(new TabComplete());
		command.setDescription(Utils.chatColors(Main.pluginNameColors + " &fcommand"));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) return false;
			if (!Main.PermissionsManager.hasCommandUsePermission((Player) sender)) return false;
			MenuListener.openMenu((Player) sender);
			return true;
		}
		if ((sender instanceof Player) && !Main.PermissionsManager.hasCommandUsePermission((Player) sender)) return false;
		if (args[0].equalsIgnoreCase(TabComplete.base.get(0))) displayConfig(sender);
		else if (args[0].equalsIgnoreCase(TabComplete.base.get(1))) reloadConfig(sender);
		else if (args[0].equalsIgnoreCase(TabComplete.base.get(2))) {
			if (sender instanceof Player) spawnTest((Player) sender);
		} else if (args[0].equalsIgnoreCase(TabComplete.base.get(3))) commands(sender);
		else commands(sender);
		return true;
	}
	
	private void commands(CommandSender sender) {
		Utils.chatColorsPlugin(sender,"&fAvailable commands:\n" + Main.pluginNameColors + " &e" + TabComplete.base.get(0) +
				" &b- &fdisplay the config options\n" + Main.pluginNameColors + " &e" + TabComplete.base.get(1) + " &b- &freload the config\n" +
				Main.pluginNameColors + " &e" + TabComplete.base.get(2) + " &b- &fsummon a test dummy");
	}
	
	private void spawnTest(Player player) {
		RayTraceResult targetBlockInfo = player.rayTraceBlocks(10.0,FluidCollisionMode.NEVER);
		if (targetBlockInfo == null) return;
		Block block = targetBlockInfo.getHitBlock();
		BlockFace blockFace = targetBlockInfo.getHitBlockFace();
		if (block == null || block.isEmpty()) return;
		if (blockFace == null) return;
		Location loc = (block.isPassable() ? block : block.getRelative(blockFace)).getLocation().add(0.5,0.5,0.5);
		loc.setDirection(player.getEyeLocation().subtract(loc).toVector());
		loc.setPitch(0);
		EffectListener.spawnTestDummy(loc);
	}

	private void displayConfig(CommandSender sender) {
		HashMap<String,Method> methods = new HashMap<String,Method>();
		for (Method method : Main.Config.getClass().getDeclaredMethods()) {
			if (Modifier.isPublic(method.getModifiers())) methods.put(method.getName(),method);
		}
		List<String> config = new ArrayList<String>();
		for (Field field : Main.Config.getClass().getDeclaredFields()) {
			boolean isString = field.getGenericType().getTypeName().toLowerCase().contains("string");
			try {
				config.add("&a" + field.getName() + "&f: " + (isString ? "\"" : "") + methods.get(field.getName()).invoke(Main.Config) + (isString ? "&f\"" : ""));
			} catch (Exception e) {}
		}
		Utils.chatColors(sender,String.join("\n",config));
	}
	
	private void reloadConfig(CommandSender sender) {
		try {
			Main.Config.readConfig();
			Main.PermissionsManager.registerPermissions();
			Utils.chatColorsPlugin(sender,"&aconfig reloaded!");
		} catch (IOException e) {
			Utils.chatColorsPlugin(sender,"&cunable to reload config!");
		}
	}
	
	static List<Particle> getParticles() {
		List<Particle> particles = new ArrayList<Particle>();
		for (Particle particle : Particle.values()) if (particle != Particle.BLOCK_CRACK && particle != Particle.BLOCK_DUST && particle != Particle.FALLING_DUST &&
				particle != Particle.ITEM_CRACK) particles.add(particle);
		return particles;
	}
}