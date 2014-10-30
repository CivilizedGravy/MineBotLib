package com.CivilizedGravy.MineBotLib;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.spacehq.mc.auth.GameProfile;
import org.spacehq.mc.auth.exception.AuthenticationException;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.ProtocolConstants;
import org.spacehq.mc.protocol.ProtocolMode;
import org.spacehq.mc.protocol.data.game.values.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.values.setting.Difficulty;
import org.spacehq.mc.protocol.data.game.values.world.WorldType;
import org.spacehq.mc.protocol.data.status.ServerStatusInfo;
import org.spacehq.mc.protocol.data.status.handler.ServerInfoHandler;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerMovementPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import com.CivilizedGravy.MineBotLib.entity.Entity;
import com.CivilizedGravy.MineBotLib.entity.Player;
import com.CivilizedGravy.MineBotLib.game.Chat;
import com.CivilizedGravy.MineBotLib.game.PacketHandler;
import com.CivilizedGravy.MineBotLib.util.AABB;
import com.CivilizedGravy.MineBotLib.world.World;

public class Minebot extends Player {

	public String host;
	public int port;
	public String username;
	public String password;
	public MinecraftProtocol protocol;
	public Client client;
	public Player master;
	public String masterName;
	public HashMap<UUID, Player> players;
	public HashMap<Integer, Entity> visibleEntities;
	public Difficulty difficulty = Difficulty.EASY;
	public GameMode gamemode = GameMode.SURVIVAL;
	public int dimension;
	public boolean isHardcore;
	public int maxPlayers;
	public WorldType worldtype;
	public Chat chat;
	public World world;
	public boolean isMasterFound = false;
	public ServerStatusInfo serverInfo;
	public boolean isInitialized = false;
	public float health = 10;
	public int food = 10;
	public AABB playerbounds;

	// Figure out

	public Minebot(String server, int port, String username, String password,
			String masterName) {
		this.host = server;
		this.port = port;
		this.username = username;
		this.password = password;
		this.masterName = masterName;

	}

	public Minebot(String server, int port, String username, String masterName) {
		this.host = server;
		this.port = port;
		this.username = username;
		this.masterName = masterName;

	}

	public boolean isInit() {
		return isInitialized;
	}

	public void setInit(boolean isConnected) {
		this.isInitialized = isConnected;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public ServerStatusInfo getServerInfo() {
		return serverInfo;
	}

	public Player getMaster() {
		return master;
	}

	public void setMaster(Player master) {
		this.master = master;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public MinecraftProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(MinecraftProtocol protocol) {
		this.protocol = protocol;
	}

	public Client getClient() {
		return client;
	}

	public HashMap<UUID, Player> getPlayers() {
		return players;
	}

	public HashMap<Integer, Entity> getVisibleEntities() {
		return visibleEntities;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int i) {
		this.dimension = i;
	}

	public boolean isHardcore() {
		return isHardcore;
	}

	public void setHardcore(boolean isHardcore) {
		this.isHardcore = isHardcore;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public WorldType getWorldtype() {
		return worldtype;
	}

	public void setWorldtype(WorldType worldtype) {
		this.worldtype = worldtype;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public boolean isMasterFound() {
		return isMasterFound;
	}

	public Chat getChat() {
		return chat;
	}

	public World getWorld() {
		return world;
	}


	public void findMaster() {
		while (!isMasterFound) {
			if (!players.isEmpty()) {
				for (UUID id : players.keySet()) {
					if (players.get(id) != null) {

						String name = players.get(id).getProfile().getName();

						if (name.equals(masterName)) {
							isMasterFound = true;
							master = players.get(id);
							System.out.println("Master Found: " + name);
						}
					}
				}

			}

		}
	}

	public void Connect() {
		if (password != null) {
			try {
				System.out.println("Online!");
				protocol = new MinecraftProtocol(username, password, false);//
			} catch (AuthenticationException ex) {
				Logger.getLogger(Minebot.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		} else {
			System.out.println("Offline!");
			protocol = new MinecraftProtocol(username);//
		}
		chat = new Chat(this);
		world = new World(this);
		players = new HashMap<UUID, Player>();
		visibleEntities = new HashMap<Integer, Entity>();
		Status();
		findMaster();
		client = new Client(host, port, protocol, new TcpSessionFactory());
		client.getSession().addListener(new PacketHandler(this));
		client.getSession().connect();
		while (!isInitialized) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void Status() {
		MinecraftProtocol protocol = new MinecraftProtocol(ProtocolMode.STATUS);
		Client client = new Client(host, port, protocol,
				new TcpSessionFactory());
		client.getSession().setFlag(ProtocolConstants.SERVER_INFO_HANDLER_KEY,
				new ServerInfoHandler() {
					@Override
					public void handle(Session arg0, ServerStatusInfo info) {

						serverInfo = info;
						for (GameProfile profile : info.getPlayerInfo()
								.getPlayers()) {
							UUID id = profile.getId();
							Player p = new Player(profile);
							players.put(id, p);

						}

					}
				});

		client.getSession().connect();
		while (client.getSession().isConnected()) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(final float delta) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				double px, py, pz;
				float pyaw, ppitch;
				px = getLocation().getX() + (getMovement().getVx() * delta);
				py = getLocation().getY() + (getMovement().getVy() * delta);
				pz = getLocation().getZ() + (getMovement().getVz() * delta);
				pyaw = getLocation().getYaw();
				ppitch = getLocation().getPitch();
				while (client.getSession().isConnected()) {
					double x, y, z;
					float yaw, pitch;

					x = getLocation().getX() + (getMovement().getVx() * delta);
					y = getLocation().getY() + (getMovement().getVy() * delta);
					z = getLocation().getZ() + (getMovement().getVz() * delta);
					yaw = getLocation().getYaw();
					pitch = getLocation().getPitch();

					if (playerbounds != null) {
						playerbounds.minX = x - 0.3;
						playerbounds.minY = y + 1.8;
						playerbounds.minZ = z - 0.3;
					}

					if (world.getChunkCache().size() > 200) {// 0.6×1.8
						if (getWorld().getPlayerBlock(getPlayer()).getId() != 0
								&& !getWorld().getPlayerBlock(getPlayer())
										.getBounds().intersectsWith(playerbounds)) {
							setOnGround(true);
						} else {
							setOnGround(false);
						}
					}

					if (!isOnGround()) {
						if (getMovement().getVy() < 3.92) {// add collision
															// detection
							getMovement().setVy(// add accelleraton to gravity
									getMovement().getVy() - (0.08 * delta));// 0.08
						}
					} else {
						getMovement().setVy(0);

					}

					setLocation(x, y, z, yaw, pitch);
					System.out.println(x + ", " + y + ", " + z + ", " + yaw
							+ ", " + pitch + ", " + isOnGround());
					if (px - x == 0 && py - y == 0 && pz - z == 0) {

						client.getSession().send(
								new ClientPlayerRotationPacket(isOnGround(),
										yaw, pitch));
					} else if (pyaw - yaw == 0 && ppitch - pitch == 0) {
						System.out.println("pos");
						client.getSession().send(
								new ClientPlayerPositionPacket(isOnGround(), x,
										y, z));
					} else if (px - x == 0 && py - y == 0 && pz - z == 0
							&& pyaw - yaw == 0 && ppitch - pitch == 0) {
						System.out.println("neither");
						client.getSession().send(
								new ClientPlayerMovementPacket(isOnGround()));
					} else {
						System.out.println("both");
						client.getSession().send(
								new ClientPlayerPositionRotationPacket(
										isOnGround(), x, y, z, yaw, pitch));
					}

					px = x;
					py = y;
					pz = z;
					pyaw = yaw;
					ppitch = pitch;

					try {
						Thread.sleep((long) (delta * 1000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		t.start();
	}

	public Player getPlayer() {
		return this;
	}

}
