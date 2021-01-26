package me.DMan16.BwCosmetics.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.World;

import me.DMan16.BwCosmetics.Main;
import me.DMan16.BwCosmetics.Particles.Effect;
import me.DMan16.BwCosmetics.Particles.Effects;

public class Config extends ConfigLoader {
	private String commandUsePermission;
	private boolean commandUseOp;
	private String commandPermission;
	private boolean commandOp;
	private boolean effectsShowBlocked;
	private List<String> effectsBlaklist;
	private List<EffectConfig> effects;
	
	public final List<String> perms;
	public Config() throws IOException  {
		super("config.yml",Main.pluginName + " config file");
		readConfig();
		perms = Arrays.asList();
	}
	
	public void readConfig() throws IOException {
		loadConfig();
		
		String permPrefix = Main.pluginName.toLowerCase();
		//Commands
		String[] startMSG = {"Please read each option's information and input type before changing","Default values are in [], type in ()",
				"Any option that is filled wrong or removed will be replaced by the [default] value!",
				"Disabled permissions will still use the Op option if it is turned on","","",
				"Commands - permissions for using the command"," ",
				"commandUsePermission - the permission required to use the base command to select a kill effect; leave blank to allow everyone to use it (String) []",
				"commandUseOp - do Op players have \"commandUsePermission\" by default (true/false) [true]",
				"commandPermission - the required permission to use the rest of the command's options; leave blank to allow everyone to use it - NOT recommended!!! " +
				"(String) [" + permPrefix + ".command]","commandOp - do Op players have \"commandPermission\" by default (true/false) [true]"};
		commandUsePermission = ((String) addNewConfigOption(config,"commandUsePermission",permPrefix + ".command",startMSG)).toLowerCase();
		commandUseOp = ((Boolean) addNewConfigOption(config,"commandUseOp",Boolean.valueOf(true),null)).booleanValue();
		commandPermission = ((String) addNewConfigOption(config,"commandPermission",permPrefix + ".command",null)).toLowerCase();
		commandOp = ((Boolean) addNewConfigOption(config,"commandOp",Boolean.valueOf(true),null)).booleanValue();
		
		//Effects
		String[] effectsMSG = {"Effects - each effect's setting"," ",
				"effectsShowBlocked - should effects that cannot be applied, due to lack of permission, be displayed in the menu (true/false) [false]",
				"effectsBlaklist - list of blacklisted worlds where the effects don't show (List of String) []",
				"For each effect:","  permission - the required permission to use this specific effect; leave blank to allow everyone to use it (String) []",
				"  Op - do Op players have the permission for this specific effect by default (true/false) [true]",};
		effectsShowBlocked = ((Boolean) addNewConfigOption(config,"effectsShowBlocked",Boolean.valueOf(false),effectsMSG)).booleanValue();
		effectsBlaklist = addNewConfigOption(config,"effectsBlaklist",Arrays.asList("world_blacklisted"),null);
		
		effects = readEffectsConfig(new ConfigOption<List<Map<String,Object>>>(config,"effects",defaultEffectsConfig(),null,false).getValue());
		addNewConfigOption(config,"effects",readEffectsToConfig(effects),null,true);
		
		writeConfig();
	}
	
	private List<Map<String,Object>> readEffectsToConfig(List<EffectConfig> readEffects) {
		List<Map<String,Object>> effects = new ArrayList<Map<String,Object>>();
		for (EffectConfig effect : readEffects) effects.add(effect.toMap());
		return effects;
	}
	
	private List<EffectConfig> readEffectsConfig(List<Map<String,Object>> effects) {
		List<EffectConfig> defaultEffects = new ArrayList<EffectConfig>();
		List<String> notAdded = Effects.names();
		for (Map<String,Object> effect : effects) {
			for (Entry<String,Object> entry : effect.entrySet()) if (entry.getValue() == null) entry.setValue("");
			try {
				EffectConfig temp = new EffectConfig(effect);
				if (notAdded.contains(temp.effect)) {
					defaultEffects.add(temp);
					notAdded.remove(temp.effect);
				}
			} catch (Exception e) {}
		}
		for (String val : notAdded) try {
			defaultEffects.add(defaultEffectConfig(val));
		} catch (Exception e) {}
		return new ArrayList<EffectConfig>(defaultEffects);
	}
	
	private List<Map<String,Object>> defaultEffectsConfig() {
		List<Map<String,Object>> temp = new ArrayList<Map<String,Object>>();
		for (String effect : Effects.names()) try {
			temp.add(defaultEffectConfig(effect).toMap());
		} catch (Exception e) {}
		return temp;
	}
	
	private EffectConfig defaultEffectConfig(String effect) throws Exception {
		return new EffectConfig(effect,effect.equals("Lighting") ? Main.pluginName.toLowerCase() + ".lighting" : "",true);
	}
	
	private EffectConfig defaultEffectConfig(Effect effect) {
		return new EffectConfig(effect,Effects.getEffectName(effect).equals("Lighting") ? Main.pluginName.toLowerCase() + ".lighting" : "",true);
	}
	
	public String commandUsePermission() {
		return commandUsePermission;
	}
	
	public boolean commandUseOp() {
		return commandUseOp;
	}
	
	public String commandPermission() {
		return commandPermission;
	}
	
	public boolean commandOp() {
		return commandOp;
	}
	
	public boolean effectsShowBlocked() {
		return effectsShowBlocked;
	}
	
	public List<String> effectsBlaklist() {
		return effectsBlaklist;
	}
	
	public boolean isBlacklisted(String str) {
		if (str == null || str.trim().isEmpty()) return false;
		return effectsBlaklist.contains(str);
	}
	
	public boolean isBlacklisted(World world) {
		return isBlacklisted(world.getName());
	}

	public List<EffectConfig> effects() {
		return effects;
	}
	
	public EffectConfig getEffectConfig(String str) {
		if (Effects.getEffect(str) == null) return null;
		for (EffectConfig effect : effects) if (Effects.getEffect(effect.effect).equals(Effects.getEffect(str))) return effect;
		try {
			return defaultEffectConfig(str);
		} catch (Exception e) {}
		return null;
	}
	
	public EffectConfig getEffectConfig(Effect Effect) {
		for (EffectConfig effect : effects) if (Effects.getEffect(effect.effect).equals(Effect)) return effect;
		return defaultEffectConfig(Effect);
	}
}