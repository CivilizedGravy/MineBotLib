/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CivilizedGravy.MineBotLib.world;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.spacehq.mc.protocol.data.game.Chunk;
import org.spacehq.mc.protocol.data.game.ShortArray3d;

import com.CivilizedGravy.MineBotLib.Minebot;
import com.CivilizedGravy.MineBotLib.entity.Player;
import com.CivilizedGravy.MineBotLib.entity.minesha.Location;
import com.CivilizedGravy.MineBotLib.util.AABB;

/**
 * 
 * @author whats_000
 */
public class World {

	Minebot bot;
	public HashMap<List<Integer>, Chunk[]> chunkCache;
	public HashMap<List<Integer>, Block> blockcache;

	public World(Minebot bot) {
		this.bot = bot;
		chunkCache = new HashMap<List<Integer>, Chunk[]>();
	}

	public String getBlockName(int id) {
		String blockName = "";

		switch (id) {
		case 0:
			blockName = "Air";
			break;
		case 1:
			blockName = "Stone";
			break;
		case 2:
			blockName = "Grass";
			break;
		case 3:
			blockName = "Dirt";
			break;
		case 4:
			blockName = "Cobblestone";
			break;
		default:
			blockName = String.valueOf(id);

		}

		return blockName;
	}

	public Block getPlayerBlock(Player player) {

		return getBlock((int) player.getLocation().getX(), (int) player
				.getLocation().getY(), // minus
				(int) player.getLocation().getZ());// stance =
		// playerY-1.62

	}

	public Block getBlock(int x, int y, int z) { // realworld coords
		int ChunkX = x >> 4;
		int ChunkY = y >> 4;// get the chunk section
		int ChunkZ = z >> 4;
		// System.out.println("ChunkX = " + ChunkX + " ChunkY = " + ChunkY +
		// " ChunkZ = " + ChunkZ + " y=" + y);
		// System.out.println("rChunkX = " + ChunkX * 16 + " rChunkY = " +
		// ChunkY * 16 + " rChunkZ = " + ChunkZ * 16);

		int blockX = x - (ChunkX * 16);
		int blockY = y - (ChunkY * 16);
		int blockZ = z - (ChunkZ * 16);
		// System.out.println("blockX = " + blockX + " blockY = " + blockY +
		// " blockZ = " + blockZ);
		AABB blockBounds = AABB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
		Location location = new Location(x, y, z);
		List<Integer> key = Arrays.asList(ChunkX, ChunkZ);
		if (chunkCache.containsKey(key) && ChunkY <= 15 && ChunkY >= 0) {
			Chunk[] chunks = chunkCache.get(key);
			if (chunks[ChunkY] != null) {
				ShortArray3d blocks = chunks[ChunkY].getBlocks();
				// return blocks.get(blockX, blockY, blockZ);

				return new Block(blocks.get(blockX, blockY, blockZ), location);
			} else {
				return new Block(0, location);
			}
		} else {
			return new Block(0, location);
		}

	}

	public void setBlock(int x, int y, int z, int id) {
		int ChunkX = x >> 4;
		int ChunkY = y >> 4;// get the chunk section
		int ChunkZ = z >> 4;
		// System.out.println("ChunkX = " + ChunkX + " ChunkY = " + ChunkY +
		// " ChunkZ = " + ChunkZ + " y=" + y);
		// System.out.println("rChunkX = " + ChunkX * 16 + " rChunkY = " +
		// ChunkY * 16 + " rChunkZ = " + ChunkZ * 16);

		int blockX = x - (ChunkX * 16);
		int blockY = y - (ChunkY * 16);
		int blockZ = z - (ChunkZ * 16);
		List<Integer> key = Arrays.asList(ChunkX, ChunkZ);
		if (chunkCache.containsKey(key) && ChunkY <= 15 && ChunkY >= 0) {
			Chunk[] prevChunk = chunkCache.get(key);
			prevChunk[ChunkY].getBlocks().set(blockX, blockY, blockZ, id);
			Chunk[] newchunk = prevChunk;
			chunkCache.put(key, newchunk);
		} else {
			// this is bad.
		}
	}

	public void setBlock(Block b) {
		int x = (int) b.getLocation().getX();
		int y = (int) b.getLocation().getY();
		int z = (int) b.getLocation().getZ();
		setBlock(x, y, z, b.getId());
	}

	public void setBlock(Location location, int id) {
		int x = (int) location.getX();
		int y = (int) location.getY();
		int z = (int) location.getZ();

		setBlock(x, y, z, id);
	}

	public HashMap<List<Integer>, Chunk[]> getChunkCache() {
		return chunkCache;
	}

	public void setChunkCache(HashMap<List<Integer>, Chunk[]> chunkCache) {
		this.chunkCache = chunkCache;
	}
}
