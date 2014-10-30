package com.CivilizedGravy.MineBotLib.entity;

import com.CivilizedGravy.MineBotLib.entity.minesha.Location;

public class Entity {

	Location location;
	Location prevLocation;
	int entityId;
	boolean onGround = false;

	public Entity() {

	}

	public Entity(int entityID) {
		this.entityId = entityID;

	}

	public Entity(int entityID, Location location) {
		this.entityId = entityID;
		this.location = location;

	}


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setLocation(double x, double y, double z, float yaw, float pitch) {

		this.location = new Location(x, y, z, yaw, pitch);
	}

	public void setLocation(double x, double y, double z) {
		if (this.location != null) {
			this.location.setX(x);
			this.location.setY(y);
			this.location.setZ(z);
		} else {
			this.location = new Location(x, y, z);
		}
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
}
