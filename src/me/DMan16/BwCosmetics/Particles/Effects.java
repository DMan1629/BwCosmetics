package me.DMan16.BwCosmetics.Particles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.DMan16.BwCosmetics.Main;
import me.DMan16.BwCosmetics.Listeners.EffectListener;
import me.DMan16.BwCosmetics.Utils.Utils;

public class Effects {
	private static HashMap<String,Effect> effects;
	
	static Effect Slash = new Effect("&#c4c4c4Slash",Material.IRON_SWORD, new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	Particles particle = new Particles(Particle.SWEEP_ATTACK,1,0.2f,0,0,0,0,null);
			new Particles(Particle.FLASH,1,1,0,0,0,0,null).display(loc,0,1,0);
        	particle.display(loc,0,0.5,0);
        	particle.display(loc,0,1,0);
        	particle.display(loc,0,1.5,0);
        }
	});
	
	static Effect BloodBurst = new Effect("&#ff0000Blood &cBurst",Material.REDSTONE, new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			for (int i = 0; i < 200; i++) new Particles(Particle.REDSTONE,1,1,255,0,0,1,null).display(loc,ThreadLocalRandom.current().nextDouble(-1.5,1.5),
					ThreadLocalRandom.current().nextDouble(0.85,1.15),ThreadLocalRandom.current().nextDouble(-1.5,1.5));
        	for (int i = 1; i <= 5; i++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i < 200; i++) new Particles(Particle.REDSTONE,1,1,255,0,0,1,null).display(loc,ThreadLocalRandom.current().nextDouble(-1.5,1.5),
        					ThreadLocalRandom.current().nextDouble(0.85,1.15),ThreadLocalRandom.current().nextDouble(-1.5,1.5));
				}
			}.runTaskLater(Main.main,i * 3);
        }
	});
	
	static Effect Explosion = new Effect("&#ffc469Explosion",Material.TNT, new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			new Particles(Particle.EXPLOSION_HUGE,2,0.5f,0,0,0,0,null).display(loc,0,1,0);
			new Particles(Particle.FLASH,1,1,0,0,0,0,null).display(loc,0,1,0);
			new Particles(Particle.CLOUD,200,1,0,0,0,0,null).display(loc,0,1,0);
        }
	});
	
	static Effect Burn = new Effect("&#ffaa00B&#ff7000u&#ff5500r&#ff0000n",Material.FIRE_CHARGE,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			new Particles(Particle.FLAME,1000,1,0,0,0,0,null).display(loc,0,0.5,0);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0,null).display(loc,0,1,0);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0,null).display(loc,0.5,1,0);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0,null).display(loc,1,1,0);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0,null).display(loc,0,1,0.5);
			new Particles(Particle.VILLAGER_ANGRY,1,1,0,0,0,0,null).display(loc,0,1,1);
			new Particles(Particle.SMOKE_LARGE,200,1.5f,0,0,0,0,null).display(loc,0,1,0);
        }
	});
	
	static Effect Lighting = new Effect("&eLighting",Material.YELLOW_DYE,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	loc.getWorld().strikeLightningEffect(loc);
        }
	});
	
	static Effect IntoTheVoid = new Effect("&fInto The &8Void",Material.BEDROCK,new Consumer<Location>() {
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
	
	static Effect Flames = new Effect("&6Flames",Material.CAMPFIRE,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	for (double i = 0; i <= 2; i += 0.5) loc.getWorld().playEffect(loc.clone().add(0,i,0),org.bukkit.Effect.MOBSPAWNER_FLAMES,null);
			new Particles(Particle.FLAME,100,1,0,0,0,0,null).display(loc,0,1,0);
        }
	});
	
	static Effect FadeAway = new Effect("&dFade&5Away",Material.PURPLE_DYE,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	for (double i = 0; i <= 2; i += 0.5) loc.getWorld().playEffect(loc.clone().add(0,i,0),org.bukkit.Effect.DRAGON_BREATH,null);
        }
	});
	
	static Effect Hearts = new Effect("&cHearts",Material.RED_TULIP,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			for (int i = 0; i < 10; i++) new Particles(Particle.HEART,1,0.75f,0,0,0,0,null).display(loc,ThreadLocalRandom.current().nextDouble(-0.5,0.5),
					ThreadLocalRandom.current().nextDouble(1,2),ThreadLocalRandom.current().nextDouble(-0.5,0.5));
        	for (int i = 1; i <= 5; i++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i < 10; i++) new Particles(Particle.HEART,1,0.75f,0,0,0,0,null).display(loc,ThreadLocalRandom.current().nextDouble(-0.5,0.5),
        					ThreadLocalRandom.current().nextDouble(1,2),ThreadLocalRandom.current().nextDouble(-0.5,0.5));
				}
			}.runTaskLater(Main.main,i * 5);
        }
	});
	
	static Effect Music = new Effect("&dM&eu&as&bi&6c",Material.NOTE_BLOCK,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			for (int i = 0; i < 5; i++) new Particles(Particle.NOTE,1,1,ThreadLocalRandom.current().nextInt(0,255),0,0,0,null).display(loc,
					ThreadLocalRandom.current().nextDouble(-1,1),ThreadLocalRandom.current().nextDouble(1.5,2.5),ThreadLocalRandom.current().nextDouble(-1,1));
        	for (int i = 1; i <= 4; i++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i < 5; i++) new Particles(Particle.NOTE,1,1,ThreadLocalRandom.current().nextInt(0,255),0,0,0,null).display(loc,
        					ThreadLocalRandom.current().nextDouble(-1,1),ThreadLocalRandom.current().nextDouble(1.5,2.5),ThreadLocalRandom.current().nextDouble(-1,1));
				}
			}.runTaskLater(Main.main,i * 2);
        }
	});
	
	static Effect BlackHole = new Effect("&8Black &0Hole",Material.BLACK_DYE,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	double r = 1;
			for (int i = 0; i <= 360; i += 8) for (int j = 0; j <= 360; j += 8) new Particles(Particle.REDSTONE,1,1,0,0,0,1,null).display(loc,
					r * Math.sin(Math.toRadians(i)) * Math.cos(Math.toRadians(j)),1 + r * Math.cos(Math.toRadians(i)),
					r * Math.sin(Math.toRadians(i)) * Math.sin(Math.toRadians(j)));
        	for (int i = 1; i <= 12; i++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i <= 360; i += 8) for (int j = 0; j <= 360; j += 8) new Particles(Particle.REDSTONE,1,1,0,0,0,1,null).display(loc,
        					r * Math.sin(Math.toRadians(i)) * Math.cos(Math.toRadians(j)),1 + r * Math.cos(Math.toRadians(i)),
        					r * Math.sin(Math.toRadians(i)) * Math.sin(Math.toRadians(j)));
				}
			}.runTaskLater(Main.main,i * 3);
        }
	});
	
	static Effect Terminator = new Effect("&1Terminator",Material.WITHER_ROSE,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			for (int i = 0; i < 20; i++) new Particles(Particle.BLOCK_DUST,1,0.5f,0,0,0,0,Material.IRON_BLOCK).display(loc,
					ThreadLocalRandom.current().nextDouble(-1,1),ThreadLocalRandom.current().nextDouble(1.5,2.5),ThreadLocalRandom.current().nextDouble(-1,1));
        	for (int i = 1; i <= 6; i++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i < 100; i++) new Particles(Particle.BLOCK_DUST,1,0.5f,0,0,0,0,Material.IRON_BLOCK).display(loc,
        					ThreadLocalRandom.current().nextDouble(-1,1),ThreadLocalRandom.current().nextDouble(0.5,2.5),ThreadLocalRandom.current().nextDouble(-1,1));
				}
			}.runTaskLater(Main.main,i * 3);
        }
	});
	
	static Effect DoddletSplosion = new Effect("&dDoddlet&eSplosion",Material.EGG,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			for (int i = 0; i < 10; i++) {
				LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc.clone().add(0,1.5,0),EntityType.CHICKEN);
				entity.setCustomName(Utils.chatColors("&dDoddlet &fthe &eDuck"));
				entity.setVelocity(new Vector(ThreadLocalRandom.current().nextDouble(-1,1),ThreadLocalRandom.current().nextDouble(-1,1),
						ThreadLocalRandom.current().nextDouble(-1,1)));
				entity.setInvulnerable(true);
				entity.setCollidable(false);
				new BukkitRunnable() {
	        		public void run() {
	        			entity.remove();
					}
				}.runTaskLater(Main.main,60);
			}
        }
	});
	
	static Effect RainCloud = new Effect("&bRain &fCloud",Material.WHITE_DYE,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	for (int i = 0; i < 5; i++) {
        		new Particles(Particle.WATER_DROP,10,1,0,0,0,0,null).display(loc,ThreadLocalRandom.current().nextDouble(-0.3,0.3),2.8,
					ThreadLocalRandom.current().nextDouble(-0.3,0.3));
        			new Particles(Particle.CLOUD,20,0.02f,0,0,0,0,null).display(loc,ThreadLocalRandom.current().nextDouble(-0.3,0.3),3,
        					ThreadLocalRandom.current().nextDouble(-0.3,0.3));
        	}
        	for (int i = 1; i <= 25; i++) new BukkitRunnable() {
        		public void run() {
                	for (int i = 0; i < 5; i++) {
                		new Particles(Particle.WATER_DROP,10,1,0,0,0,0,null).display(loc,ThreadLocalRandom.current().nextDouble(-0.3,0.3),2.8,
        					ThreadLocalRandom.current().nextDouble(-0.3,0.3));
                			new Particles(Particle.CLOUD,20,0.02f,0,0,0,0,null).display(loc,ThreadLocalRandom.current().nextDouble(-0.3,0.3),3,
                					ThreadLocalRandom.current().nextDouble(-0.3,0.3));
                	}
				}
			}.runTaskLater(Main.main,i * 4);
        }
	});
	
	static Effect MoneyShower = new Effect("&eMoney &fShower",Material.GOLD_INGOT,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
        	for (int k = 0; k < 4; k++) new BukkitRunnable() {
        		public void run() {
        			for (int i = 0; i < 3; i++) {
        				Material material;
        				if (i == 0) material = Material.GOLD_NUGGET;
        				else if (i == 1) material = Material.GOLD_INGOT;
        				else material = Material.GOLD_BLOCK;
        				for (int j = 0; j < 5; j++) {
        					Item item = EffectListener.effectItemDrop(material,loc.clone().add(ThreadLocalRandom.current().nextDouble(-0.2,0.2),
        							ThreadLocalRandom.current().nextDouble(0.5,2),ThreadLocalRandom.current().nextDouble(-0.2,0.2)));
        					item.setVelocity(new Vector(ThreadLocalRandom.current().nextDouble(-0.75,0.75),ThreadLocalRandom.current().nextDouble(0,0.75),
        							ThreadLocalRandom.current().nextDouble(-0.75,0.75)));
        					new BukkitRunnable() {
        		        		public void run() {
        		        			item.remove();
        						}
        					}.runTaskLater(Main.main,40);
        				}
        			}
				}
			}.runTaskLater(Main.main,(k * 5) + 1);
        }
	});
	
	static Effect Soul = new Effect("&#503931Soul",Material.SOUL_SAND,new Consumer<Location>() {
        @Override
        public void accept(Location loc) {
			new Particles(Particle.SOUL,100,1,0,0,0,0,null).display(loc,0,1,0);
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
		effects.put("BlackHole",BlackHole);
		effects.put("Terminator",Terminator);
		effects.put("DoddletSplosion",DoddletSplosion);
		effects.put("RainCloud",RainCloud);
		effects.put("MoneyShower",MoneyShower);
		effects.put("Soul",Soul);
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
