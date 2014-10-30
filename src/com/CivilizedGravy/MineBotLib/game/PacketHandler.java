package com.CivilizedGravy.MineBotLib.game;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.ProtocolMode;
import org.spacehq.mc.protocol.data.game.Chunk;
import org.spacehq.mc.protocol.data.game.values.ClientRequest;
import org.spacehq.mc.protocol.data.game.values.PlayerListEntry;
import org.spacehq.mc.protocol.data.game.values.PlayerListEntryAction;
import org.spacehq.mc.protocol.data.game.values.entity.EntityStatus;
import org.spacehq.mc.protocol.data.game.values.world.block.BlockChangeRecord;
import org.spacehq.mc.protocol.packet.ingame.client.ClientRequestPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityHeadLookPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityMovementPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityStatusPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerUpdateHealthPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPlayerPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerMultiChunkDataPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerSpawnParticlePacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.packet.Packet;

import com.CivilizedGravy.MineBotLib.Minebot;
import com.CivilizedGravy.MineBotLib.entity.Entity;
import com.CivilizedGravy.MineBotLib.entity.LivingEntity;
import com.CivilizedGravy.MineBotLib.entity.Player;
import com.CivilizedGravy.MineBotLib.entity.minesha.Location;
import com.CivilizedGravy.MineBotLib.util.AABB;

public class PacketHandler implements SessionListener {

	private Minebot bot;

	public PacketHandler(Minebot bot) {
		this.bot = bot;
	}

	@Override
	public void packetReceived(PacketReceivedEvent event) {
		Packet recv = ((PacketReceivedEvent) event).getPacket();
		Player master = bot.getMaster();
		Chat chat = bot.getChat();

		if (recv instanceof ServerSpawnPlayerPacket) {

			// event.getSession().send(new ClientChatPacket("Hello, " +
			// ((ServerSpawnPlayerPacket) recv).getProfile().getName()));
			int id = ((ServerSpawnPlayerPacket) recv).getEntityId();
			UUID uuid = ((ServerSpawnPlayerPacket) recv).getUUID();
			double X = ((ServerSpawnPlayerPacket) recv).getX();
			double Y = ((ServerSpawnPlayerPacket) recv).getY();
			double Z = ((ServerSpawnPlayerPacket) recv).getZ();
			float yaw = ((ServerSpawnPlayerPacket) recv).getYaw();
			float pitch = ((ServerSpawnPlayerPacket) recv).getPitch();
			Player pe = new Player(id, new Location(X, Y, Z, yaw, pitch));
			pe.setHeldItem(((ServerSpawnPlayerPacket) recv).getCurrentItem());
			pe.setMetadata(((ServerSpawnPlayerPacket) recv).getMetadata());

			if (bot.getPlayers().containsKey(uuid)) {
				bot.getPlayers().get(uuid).setLocation(X, Y, X, yaw, pitch);

			} else {
				bot.getPlayers().put(uuid, pe);
			}

			if (bot.isMasterFound()) {
				if (uuid.equals(master.getProfile().getId())) {
					master.setEntityId(((ServerSpawnPlayerPacket) recv)
							.getEntityId());
					master.setLocation(X, Y, Z, yaw, pitch);
					chat.say("Hello, " + master.getProfile().getName() + "!");
				}
			}

		}

		else if (recv instanceof ServerJoinGamePacket) {

			ServerJoinGamePacket jgp = ((ServerJoinGamePacket) recv);
			bot.setProtocol((MinecraftProtocol) event.getSession()
					.getPacketProtocol());
			bot.setDifficulty(jgp.getDifficulty());
			bot.setGamemode(jgp.getGameMode());

			bot.setDimension(jgp.getDimension());// dimension enum?
			bot.setWorldtype(jgp.getWorldType());
			bot.setHardcore(jgp.getHardcore());
			bot.setEntityId(jgp.getEntityId());
			bot.setInit(true);

		} else if (recv instanceof ServerSpawnPositionPacket) {

			double x = ((ServerSpawnPositionPacket) recv).getPosition().getX();
			double y = ((ServerSpawnPositionPacket) recv).getPosition().getY();
			double z = ((ServerSpawnPositionPacket) recv).getPosition().getZ();
			bot.setLocation(x, y, z);
			bot.setMovement(0, 0, 0);
			bot.playerbounds = AABB.getBoundingBox(x - 0.3, y + 1.8, z - 0.3,
					0.6, 1.8, 0.6);
			event.getSession().send(
					new ClientPlayerPositionPacket(bot.isOnGround(), x, y, z));

			if (bot.getProtocol().getMode() == ProtocolMode.GAME) {// Game
																	// starts
																	// here
				bot.update(0.05f);

			}

		} else if (recv instanceof ServerPlayerListEntryPacket) {

			PlayerListEntry[] pleArr = ((ServerPlayerListEntryPacket) recv)
					.getEntries();
			PlayerListEntryAction act = ((ServerPlayerListEntryPacket) recv)
					.getAction();

			for (PlayerListEntry ple : pleArr) {
				UUID id = ple.getProfile().getId();
				if (act.equals(PlayerListEntryAction.ADD_PLAYER)) {

					if (ple.getProfile().getName().equals(bot.getMasterName())) {

						System.out.println("Master found: "
								+ ple.getProfile().getIdAsString());
					}

					Player p = new Player(ple.getProfile());
					if (!bot.getPlayers().containsKey(id)) {
						bot.getPlayers().put(id, p);
					}

				} else if (act.equals(PlayerListEntryAction.REMOVE_PLAYER)) {

					if (ple.getProfile().getName().equals(bot.getMasterName())) {

						System.out.println("Master has Disconnected!");

					}
					if (bot.getPlayers().containsKey(id)) {
						bot.getPlayers().remove(ple.getProfile().getId());
					}
				}
			}
		} else if (recv instanceof ServerPlayerPositionRotationPacket) {
			// System.out.println("Sorry Server!");
			((ServerPlayerPositionRotationPacket) recv).getRelativeElements();
			double x = ((ServerPlayerPositionRotationPacket) recv).getX();
			double y = ((ServerPlayerPositionRotationPacket) recv).getY();
			double z = ((ServerPlayerPositionRotationPacket) recv).getZ();
			float yaw = ((ServerPlayerPositionRotationPacket) recv).getYaw();
			float pitch = ((ServerPlayerPositionRotationPacket) recv)
					.getPitch();
			bot.setLocation(x, y, z, yaw, pitch);
			event.getSession().send(
					new ClientPlayerPositionRotationPacket(bot.isOnGround(),
							bot.getLocation().getX(), bot.getLocation().getY(),
							bot.getLocation().getZ(), bot.getLocation()
									.getYaw(), bot.getLocation().getPitch()));

		} else if (recv instanceof ServerChatPacket) {

			chat.HandleMsg(((ServerChatPacket) recv));

		} else if (recv instanceof ServerEntityPositionPacket) {
			int id = ((ServerEntityPositionPacket) recv).getEntityId();
			double vx = ((ServerEntityPositionPacket) recv).getMovementX();
			double vy = ((ServerEntityPositionPacket) recv).getMovementY();
			double vz = ((ServerEntityPositionPacket) recv).getMovementZ();
			if (id == master.getEntityId()) {
				master.setLocation(master.getLocation().getX() + vx, master
						.getLocation().getY() + vy, master.getLocation().getZ()
						+ vz);
			}

			if (bot.getVisibleEntities().containsKey(id)) {

				Entity e = bot.getVisibleEntities().get(id);
				double ex = e.getLocation().getX() + vx;
				double ey = e.getLocation().getY() + vy;
				double ez = e.getLocation().getZ() + vz;

				e.setLocation(ex, ey, ez);
			} else {
				// thats odd...
			}

		} else if (recv instanceof ServerEntityPositionRotationPacket) {
			int id = ((ServerEntityPositionRotationPacket) recv).getEntityId();
			double vx = ((ServerEntityPositionRotationPacket) recv)
					.getMovementX();
			double vy = ((ServerEntityPositionRotationPacket) recv)
					.getMovementY();
			double vz = ((ServerEntityPositionRotationPacket) recv)
					.getMovementZ();
			if (id == master.getEntityId()) {
				master.setLocation(master.getLocation().getX() + vx, master
						.getLocation().getY() + vy, master.getLocation().getZ()
						+ vz);
			}

			if (bot.getVisibleEntities().containsKey(id)) {

				LivingEntity e = (LivingEntity) bot.getVisibleEntities()
						.get(id);
				double ex = e.getLocation().getX() + vx;
				double ey = e.getLocation().getY() + vy;
				double ez = e.getLocation().getZ() + vz;
				float eyaw = ((ServerEntityPositionRotationPacket) recv)
						.getYaw();
				float epitch = ((ServerEntityPositionRotationPacket) recv)
						.getPitch();

				e.setLocation(ex, ey, ez, eyaw, epitch);
			} else {
				// thats odd...
			}

		} else if (recv instanceof ServerEntityMovementPacket) {
			int id = ((ServerEntityMovementPacket) recv).getEntityId();
			double vx = ((ServerEntityMovementPacket) recv).getMovementX();
			double vy = ((ServerEntityMovementPacket) recv).getMovementY();
			double vz = ((ServerEntityMovementPacket) recv).getMovementZ();
			float yaw = ((ServerEntityMovementPacket) recv).getYaw();
			float pitch = ((ServerEntityMovementPacket) recv).getPitch();
			boolean onGrnd = ((ServerEntityMovementPacket) recv).isOnGround();
			if (id == master.getEntityId()) {
				master.setLocation(master.getLocation().getX() + vx, master
						.getLocation().getY() + vy, master.getLocation().getZ()
						+ vz, yaw, pitch);
			}
			if (bot.getVisibleEntities().containsKey(id)) {

				Entity e = bot.getVisibleEntities().get(id);
				double ex = e.getLocation().getX() + vx;
				double ey = e.getLocation().getY() + vy;
				double ez = e.getLocation().getZ() + vz;
				e.setLocation(ex, ey, ez, yaw, pitch);
				e.setOnGround(onGrnd);
			} else {
				// thats odd...
			}

		} else if (recv instanceof ServerRespawnPacket) {
			// event.getSession().send(new
			// ClientRequestPacket(Request.RESPAWN));

		} else if (recv instanceof ServerEntityHeadLookPacket) {

			int id = ((ServerEntityHeadLookPacket) recv).getEntityId();
			if (id == master.getEntityId()) {
				master.getLocation().setYaw(
						((ServerEntityHeadLookPacket) recv).getHeadYaw());
			}
			if (bot.getPlayers().containsKey(id)) {
				float yaw = ((ServerEntityHeadLookPacket) recv).getHeadYaw();
				bot.getPlayers().get(id).getLocation().setYaw(yaw);
			}

		} else if (recv instanceof ServerEntityStatusPacket) {
			EntityStatus s = ((ServerEntityStatusPacket) recv).getStatus();
			int id = ((ServerEntityStatusPacket) recv).getEntityId();
			if (bot.getPlayers().containsKey(id)) {
				bot.getPlayers().get(id).setEntityStatus(s);

			}
		} else if (recv instanceof ServerUpdateHealthPacket) {
			float health = ((ServerUpdateHealthPacket) recv).getHealth();
			int food = ((ServerUpdateHealthPacket) recv).getFood();
			bot.setHealth(health);
			bot.setFood(food);

			// System.out.println(playerHealth);
			if (health <= 0) {
				event.getSession().send(
						new ClientRequestPacket(ClientRequest.RESPAWN));
			}
			if (food <= 7) {

				// you hungry
			}
		} else if (recv instanceof ServerMultiChunkDataPacket) {
			int columns = ((ServerMultiChunkDataPacket) recv).getColumns();

			for (int i = 0; i < columns; i++) {
				Chunk[] chunks = ((ServerMultiChunkDataPacket) recv)
						.getChunks(i);
				int chunkX = ((ServerMultiChunkDataPacket) recv).getX(i);
				int chunkZ = ((ServerMultiChunkDataPacket) recv).getZ(i);
				List<Integer> key = Arrays.asList(chunkX, chunkZ);
				if (((ServerMultiChunkDataPacket) recv).getBiomeData(i) != null) {
					bot.getWorld().getChunkCache().put(key, chunks);
				} else {
					bot.getWorld().getChunkCache().remove(key);
				}

			}

			// System.out.println(World.getChunkCache().size());
		} else if (recv instanceof ServerChunkDataPacket) {
			Chunk[] chunks = ((ServerChunkDataPacket) recv).getChunks();
			int chunkX = ((ServerChunkDataPacket) recv).getX();
			int chunkZ = ((ServerChunkDataPacket) recv).getZ();
			List<Integer> key = Arrays.asList(chunkX, chunkZ);
			if (((ServerChunkDataPacket) recv).isFullChunk()) {
				bot.getWorld().getChunkCache().put(key, chunks);
			} else {
				bot.getWorld().getChunkCache().remove(key);
			}

		} else if (recv instanceof ServerSpawnParticlePacket) {

		} else if (recv instanceof ServerBlockChangePacket) {
			((ServerBlockChangePacket) recv).getRecord().getBlock();
			int blockID = ((ServerBlockChangePacket) recv).getRecord()
					.getBlock();
			int blockX = ((ServerBlockChangePacket) recv).getRecord()
					.getPosition().getX();
			int blockY = ((ServerBlockChangePacket) recv).getRecord()
					.getPosition().getY();
			int blockZ = ((ServerBlockChangePacket) recv).getRecord()
					.getPosition().getZ();
			bot.getWorld().setBlock(blockX, blockY, blockZ, blockID);

		} else if (recv instanceof ServerMultiBlockChangePacket) {
			BlockChangeRecord[] bcr = ((ServerMultiBlockChangePacket) recv)
					.getRecords();

			for (BlockChangeRecord b : bcr) {
				bot.getWorld().setBlock(b.getPosition().getX(),
						b.getPosition().getY(), b.getPosition().getZ(),
						b.getBlock());
			}
		}

	}

	@Override
	public void packetSent(PacketSentEvent event) {

	}

	@Override
	public void connected(ConnectedEvent event) {

		Logger.getLogger(Minebot.class.getName()).log(Level.INFO, "Connected!");
	}

	@Override
	public void disconnecting(DisconnectingEvent event) {

	}

	@Override
	public void disconnected(DisconnectedEvent event) {
		Logger.getLogger(Minebot.class.getName()).log(Level.INFO,
				"Disconnected: " + event.getReason());
	}

}
