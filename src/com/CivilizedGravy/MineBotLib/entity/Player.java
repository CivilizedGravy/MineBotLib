/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CivilizedGravy.MineBotLib.entity;

import org.spacehq.mc.auth.GameProfile;
import org.spacehq.mc.protocol.data.game.EntityMetadata;

import com.CivilizedGravy.MineBotLib.entity.minesha.Location;

/**
 *
 * @author whats_000
 */
public class Player extends LivingEntity {

	GameProfile profile;
	int heldItem;
	EntityMetadata[] metadata;
	private String displayName;

	public Player() {

	}

	public Player(int entityId) {
		super(entityId);
		this.entityId = entityId;
	}

	public Player(GameProfile profile) {
		this.profile = profile;
	}

	public Player(int entityId, Location location) {
		super(entityId, location);
	}

	public Player(int entityId, GameProfile profile) {
		super(entityId);

		this.profile = profile;
	}

	public Player(int entityId, GameProfile profile, Location location) {
		super(entityId, location);
		this.profile = profile;

	}

	public Player(int entityId, GameProfile profile, Location location,
			String displayName) {
		super(entityId, location);
		this.profile = profile;
		this.displayName = displayName;

	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public GameProfile getProfile() {
		return profile;
	}

	public void setProfile(GameProfile profile) {
		this.profile = profile;
	}

	public int getHeldItem() {
		return heldItem;
	}

	public void setHeldItem(int heldItem) {
		this.heldItem = heldItem;
	}

	public EntityMetadata[] getMetadata() {
		return metadata;
	}

	public void setMetadata(EntityMetadata[] metadata) {
		this.metadata = metadata;
	}

}
