package me.DMan16.BwCosmetics.Config;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import me.DMan16.BwCosmetics.Main;
import me.DMan16.BwCosmetics.Particles.Effect;
import me.DMan16.BwCosmetics.Particles.Effects;

public class EffectConfig {
	public final String effect;
	private final String permission;
	private final boolean Op;
	
	public EffectConfig(String effect, String permission, boolean Op) throws Exception {
		this.effect = Effects.getEffectName(effect).toString();
		this.permission = permission == null ? "" : permission.toLowerCase().trim().replace(" ","");
		this.Op = Op;
	}
	
	public EffectConfig(Effect effect, String permission, boolean Op) {
		this.effect = Effects.getEffectName(effect).toString();
		this.permission = permission == null ? "" : permission.toLowerCase().trim().replace(" ","");
		this.Op = Op;
	}
	
	public EffectConfig(Map<String,Object> map) throws Exception {
		effect = Effects.getEffectName(((String) map.get("effect"))).toString();
		permission = ((String) map.get("permission")) == null ? "" : ((String) map.get("permission")).toLowerCase().trim().replace(" ","");
		boolean OP;
		try{
			OP = Boolean.parseBoolean((String) map.get("Op"));
		} catch (Exception e) {
			OP = (boolean) map.get("Op");
		}
		Op = OP;
	}
	
	public boolean canApply(Player player) {
		if (permission.isEmpty()) return true;
		return Main.PermissionsManager.hasPermission(player,permission,Op);
	}
	
	public Effect getEffect() {
		return Effects.getEffect(effect);
	}
	
	public Map<String,Object> toMap() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("effect",effect);
		map.put("permission",permission);
		map.put("Op",Op);
		return map;
	}
	
	@Override
	public String toString() {
		return toMap().toString();
	}
}