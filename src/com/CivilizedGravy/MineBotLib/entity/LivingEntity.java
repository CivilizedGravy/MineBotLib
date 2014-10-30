/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CivilizedGravy.MineBotLib.entity;

import org.spacehq.mc.protocol.data.game.values.entity.EntityStatus;

import com.CivilizedGravy.MineBotLib.entity.minesha.Location;
import com.CivilizedGravy.MineBotLib.entity.minesha.Movement;

/**
 * 
 * @author whats_000
 */
public class LivingEntity extends Entity {

	protected EntityStatus status;
	protected Movement movement;

	public LivingEntity() {
	}

	public LivingEntity(int entityId) {
		super(entityId);
	}

	public LivingEntity(int entityId, Location location) {
		super(entityId, location);
	}

	public LivingEntity(int entityId, Location location, Movement movement) {
		super(entityId, location);
		this.movement = movement;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public EntityStatus getEntityStatus() {
		return status;
	}

	public void setEntityStatus(EntityStatus status) {
		this.status = status;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(double vx, double vy, double vz) {
		this.movement = new Movement(vx, vy, vz);
	}



}
