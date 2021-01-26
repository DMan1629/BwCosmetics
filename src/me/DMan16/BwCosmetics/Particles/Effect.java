package me.DMan16.BwCosmetics.Particles;

import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class Effect {
	public final String nameColors;
	private final Consumer<Location> method;
	
	public Effect(String nameColors, Consumer<Location> method) {
		this.nameColors = nameColors;
		this.method = method;
	}
	
	public void display(Location loc) {
		if (method == null) return;
		method.accept(loc);
	}
	
	public void display(LivingEntity entity) {
		display(entity.getLocation());
	}
	
	public void display(LivingEntity entity, double x, double y, double z) {
		display(entity.getLocation().clone().add(x,y,z));
	}
}