package com.CivilizedGravy.MineBotLib.world;

import com.CivilizedGravy.MineBotLib.entity.minesha.Location;
import com.CivilizedGravy.MineBotLib.util.AABB;

public class Block {

	private int id;
	private AABB bounds;
	private Location location;

	public Block(int id, Location location) {
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();

		this.id = id;
		this.bounds = AABB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
		this.location = location;
	}

	public Block(int id, int x, int y, int z) {
		this.id = id;
		this.bounds = AABB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
		this.location = new Location(x, y, z);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AABB getBounds() {
		return bounds;
	}

	public void setBounds(AABB bounds) {
		this.bounds = bounds;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
