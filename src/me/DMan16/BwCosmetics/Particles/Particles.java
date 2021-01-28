package me.DMan16.BwCosmetics.Particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

public class Particles {
		private final Particle particle;
		private final int amount;
		private final float speed;
		private final int r;
		private final int g;
		private final int b;
		private final float size;
		private final Material material;

		public Particles(Particle effect, int amount, float speed, int r, int g, int b, float size, Material material) {
			if (amount < 0) amount = 0;
			this.particle = effect;
			this.amount = amount;
			this.speed = speed;
			this.r = r;
			this.g = g;
			this.b = b;
			this.size = size;
			this.material = material;
	}

	public void display(Location location) {
		this.display(location,0,0,0);
	}

	public void display(Location location, double x, double y, double z) {
		if (this.particle == Particle.REDSTONE) location.getWorld().spawnParticle(particle,location.getX() + x,location.getY() + y,location.getZ() + z,
				amount,0,0,0,speed, new Particle.DustOptions(Color.fromRGB(r,g,b),size));
		else if (this.particle == Particle.BLOCK_DUST || this.particle == Particle.BLOCK_CRACK) location.getWorld().spawnParticle(particle,location.getX() + x,
				location.getY() + y,location.getZ() + z,amount,0,0,0,speed,material.createBlockData());
		else location.getWorld().spawnParticle(particle,location.getX() + x,location.getY() + y,location.getZ() + z,amount,0,0,0,speed);
	}
}