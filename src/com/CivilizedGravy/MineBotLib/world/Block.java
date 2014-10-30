package com.CivilizedGravy.MineBotLib.world;

import com.CivilizedGravy.MineBotLib.entity.minesha.BoundingBox;
import com.CivilizedGravy.MineBotLib.entity.minesha.Location;

public class Block {

	private int id;
	private BoundingBox bounds;
	private Location location;

	public Block(int id, Location location) {
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();

		this.id = id;
		this.bounds = new BoundingBox(x, y, z, x + 1, y + 1, z + 1);
		this.location = location;
	}

	public Block(int id, int x, int y, int z) {
		this.id = id;
		this.bounds = new BoundingBox(x, y, z, x + 1, y + 1, z + 1);
		this.location = new Location(x, y, z);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BoundingBox getBounds() {
		return bounds;
	}

	public void setBounds(BoundingBox bounds) {
		this.bounds = bounds;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
