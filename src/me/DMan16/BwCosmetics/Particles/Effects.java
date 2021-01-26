package me.DMan16.BwCosmetics.Particles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import me.DMan16.BwCosmetics.Main;

public class Effects {
	private static HashMap<String,Effect> effects;
	
	static Effect Slash = new Effect("&#c4c4c4Slash", new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	Particles particle = new Particles(Particle.SWEEP_ATTACK,1,0.2f,0,0,0,0);
			new Particles(Particle.FLASH,1,1,0,0,0,0).display(loc,0,1,0);
        	particle.display(loc,0,0.5,0);
        	particle.display(loc,0,1,0);
        	particle.display(loc,0,1.5,0);
        }
	});
	
	static Effect BloodBurst = new Effect("&#ff0000Blood &cBurst", new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			for (int i = 0; i < 200; i++) new Particles(Particle.REDSTONE,1,1,255,0,0,1).display(loc,ThreadLocalRandom.current().nextDouble(-1.5,1.5),
					ThreadLocalRandom.current().nextDouble(0.85,1.15),ThreadLocalRandom.current().nextDouble(-1.5,1.5));
        	for (int i = 1; i <= 5; i++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i < 200; i++) new Particles(Particle.REDSTONE,1,1,255,0,0,1).display(loc,ThreadLocalRandom.current().nextDouble(-1.5,1.5),
        					ThreadLocalRandom.current().nextDouble(0.85,1.15),ThreadLocalRandom.current().nextDouble(-1.5,1.5));
				}
			}.runTaskLater(Main.main,i * 3);
        }
	});
	
	static Effect Explosion = new Effect("&#ffc469Explosion", new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			new Particles(Particle.EXPLOSION_HUGE,2,0.5f,0,0,0,0).display(loc,0,1,0);
			new Particles(Particle.FLASH,1,1,0,0,0,0).display(loc,0,1,0);
			new Particles(Particle.CLOUD,200,1,0,0,0,0).display(loc,0,1,0);
        }
	});
	
	static Effect Burn = new Effect("&#ffaa00B&#ff7000u&#ff5500r&#ff0000n",new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			new Particles(Particle.FLAME,1000,1,0,0,0,0).display(loc,0,0.5,0);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0).display(loc,0,1,0);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0).display(loc,0.5,1,0);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0).display(loc,1,1,0);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0).display(loc,0,1,0.5);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0).display(loc,0,1,1);
			new Particles(Particle.SMOKE_LARGE,200,1.5f,0,0,0,0).display(loc,0,1,0);
        }
	});
	
	static Effect Lighting = new Effect("&eLighting",new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	loc.getWorld().strikeLightningEffect(loc);
        }
	});
	
	static Effect IntoTheVoid = new Effect("&fInto The &8Void",new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	loc.getWorld().playEffect(loc.clone(),org.bukkit.Effect.ENDER_SIGNAL,null);
        	loc.getWorld().playEffect(loc.clone().add(0,1,0),org.bukkit.Effect.ENDER_SIGNAL,null);
        	loc.getWorld().playEffect(loc.clone().add(0,2,0),org.bukkit.Effect.ENDER_SIGNAL,null);
        	for (int i = 1; i <= 5; i++) new BukkitRunnable() {
        		public void run() {
        			loc.getWorld().playEffect(loc.clone(),org.bukkit.Effect.ENDER_SIGNAL,null);
		        	loc.getWorld().playEffect(loc.clone().add(0,1,0),org.bukkit.Effect.ENDER_SIGNAL,null);
		        	loc.getWorld().playEffect(loc.clone().add(0,2,0),org.bukkit.Effect.ENDER_SIGNAL,null);
				}
			}.runTaskLater(Main.main,i * 4);
        }
	});
	
	static Effect Flames = new Effect("&6Flames",new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	for (double i = 0; i <= 2; i += 0.5) loc.getWorld().playEffect(loc.clone().add(0,i,0),org.bukkit.Effect.MOBSPAWNER_FLAMES,null);
			new Particles(Particle.FLAME,100,1,0,0,0,0).display(loc,0,1,0);
        }
	});
	
	static Effect FadeAway = new Effect("&dFade&5Away",new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	for (double i = 0; i <= 2; i += 0.5) loc.getWorld().playEffect(loc.clone().add(0,i,0),org.bukkit.Effect.DRAGON_BREATH,null);
        }
	});
	
	static Effect Hearts = new Effect("&cHearts",new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			for (int i = 0; i < 10; i++) new Particles(Particle.HEART,1,0.75f,0,0,0,0).display(loc,ThreadLocalRandom.current().nextDouble(-0.5,0.5),
					ThreadLocalRandom.current().nextDouble(1,2),ThreadLocalRandom.current().nextDouble(-0.5,0.5));
        	for (int i = 1; i <= 5; i++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i < 10; i++) new Particles(Particle.HEART,1,0.75f,0,0,0,0).display(loc,ThreadLocalRandom.current().nextDouble(-0.5,0.5),
        					ThreadLocalRandom.current().nextDouble(1,2),ThreadLocalRandom.current().nextDouble(-0.5,0.5));
				}
			}.runTaskLater(Main.main,i * 5);
        }
	});
	
	static Effect Music = new Effect("&dM&eu&as&bi&6c",new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			for (int i = 0; i < 5; i++) new Particles(Particle.NOTE,1,1,ThreadLocalRandom.current().nextInt(0,255),0,0,0).display(loc,
					ThreadLocalRandom.current().nextDouble(-1,1),ThreadLocalRandom.current().nextDouble(1.5,2.5),ThreadLocalRandom.current().nextDouble(-1,1));
        	for (int i = 1; i <= 4; i++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i < 5; i++) new Particles(Particle.NOTE,1,1,ThreadLocalRandom.current().nextInt(0,255),0,0,0).display(loc,
        					ThreadLocalRandom.current().nextDouble(-1,1),ThreadLocalRandom.current().nextDouble(1.5,2.5),ThreadLocalRandom.current().nextDouble(-1,1));
				}
			}.runTaskLater(Main.main,i * 2);
        }
	});
	
	static {
		effects = new HashMap<String,Effect>();
		effects.put("Slash",Slash);
		effects.put("BloodBurst",BloodBurst);
		effects.put("Explosion",Explosion);
		effects.put("Burn",Burn);
		effects.put("Lighting",Lighting);
		effects.put("IntoTheVoid",IntoTheVoid);
		effects.put("Flames",Flames);
		effects.put("FadeAway",FadeAway);
		effects.put("Hearts",Hearts);
		effects.put("Music",Music);
	}
	
	public static Effect getEffect(String str) {
		if (str == null || str.isEmpty()) return null;
		for (Entry<String,Effect> effect : effects.entrySet()) if (effect.getKey().equalsIgnoreCase(str.replace(" ","_"))) return effect.getValue();
		return null;
	}
	
	public static String getEffectName(String str) {
		if (str == null || str.isEmpty()) return null;
		for (Entry<String,Effect> effect : effects.entrySet()) if (effect.getKey().equalsIgnoreCase(str.replace(" ","_"))) return effect.getKey();
		return null;
	}
	
	public static String getEffectName(Effect Effect) {
		if (Effect == null) return null;
		for (Entry<String,Effect> effect : effects.entrySet()) if (effect.getValue().equals(Effect)) return effect.getKey();
		return null;
	}
	
	public static List<String> names() {
		List<String> list = new ArrayList<String>(effects.keySet());
		Collections.sort(list);
		return list;
	}
}
