package me.DMan16.BwCosmetics.Listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import me.DMan16.BwCosmetics.Main;
import me.DMan16.BwCosmetics.Particles.Effect;
import me.DMan16.BwCosmetics.Particles.Effects;
import me.DMan16.BwCosmetics.Utils.Utils;

public class EffectListener implements Listener {
	public static NamespacedKey dummyKey = Utils.namespacedKey("test_dummy");
	
	boolean isTestDummy(LivingEntity entity) {
		return entity != null && (entity instanceof Villager) && entity.getPersistentDataContainer().has(dummyKey,PersistentDataType.STRING);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onDummyDeath(EntityDeathEvent event) {
		if (!isTestDummy(event.getEntity())) return;
		event.setDroppedExp(0);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDeath(EntityDeathEvent event) {
		if (Main.Config.isBlacklisted(event.getEntity().getWorld())) return;
		if (!(event.getEntity() instanceof Player) && !isTestDummy(event.getEntity())) return;
		Player killer = event.getEntity().getKiller();
		if (killer == null) {
			Bukkit.broadcastMessage("Killer null");
			return;
		}
		String name = Main.PlayersDataManager.get(killer);
		if (name == null) return;
		Effect effect = Effects.getEffect(name);
		if (effect == null) {
			Main.PlayersDataManager.update(killer,null);
			return;
		}
		effect.display(event.getEntity());
	}

	public static void spawnTestDummy(Location loc) {
		Villager dummy = (Villager) loc.getWorld().spawnEntity(loc,EntityType.VILLAGER);
		dummy.setCustomName(Utils.chatColors("&6Test &bDummy"));
		dummy.setCustomNameVisible(true);
		dummy.setGlowing(true);
		dummy.setSilent(true);
		dummy.setCanPickupItems(false);
		dummy.setCollidable(true);
		AttributeInstance health = dummy.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		if (health != null) {
			ArrayList<AttributeModifier> modifiers = new ArrayList<AttributeModifier>(health.getModifiers());
			modifiers.forEach(mod -> health.removeModifier(mod));
			health.setBaseValue(0.5);
		}
		dummy.setHealth(0.5);
		dummy.getPersistentDataContainer().set(EffectListener.dummyKey,PersistentDataType.STRING,"");
		dummy.setAdult();
		dummy.setAgeLock(true);
		dummy.setAware(false);
		dummy.setLootTable(null);
		dummy.setProfession(Profession.NITWIT);
		dummy.setGravity(true);
	}
}