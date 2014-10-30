package com.CivilizedGravy.MineBotLib.entity.minesha;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.spacehq.mc.protocol.data.game.ItemStack;
import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.data.game.values.Face;
import org.spacehq.mc.protocol.data.game.values.entity.player.InteractAction;
import org.spacehq.mc.protocol.data.game.values.entity.player.PlayerAction;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerInteractEntityPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPlaceBlockPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientSwingArmPacket;

import com.CivilizedGravy.MineBotLib.Minebot;
import com.CivilizedGravy.MineBotLib.entity.Player;

public class Actions {

	double mineshaX;
	double mineshaY;
	double mineshaZ;
	double mineshaVX;
	double mineshaVY;// v for velocity!
	double mineshaVZ;
	double mineshaAccel;
	float mineshaYaw;
	float mineshaPitch;
	float mineshaHealth;
	float mineshaFood;
	double mineshaGravity;
	Minebot bot;
	Player master;

	public Actions(Minebot bot) {

		this.bot = bot;
		this.master = bot.getMaster();

	}

	public void lookAt(double x, double y, double z) {
		double l;
		double w;
		double c;
		l = x - bot.getLocation().getX();
		w = z - bot.getLocation().getZ();
		c = Math.sqrt(l * l + w * w);
		double alpha1 = -Math.asin(l / c) / Math.PI * 180;
		double alpha2 = Math.acos(w / c) / Math.PI * 180;
		if (alpha2 > 90) { // try doubling all of the numbers
			bot.getLocation().setYaw(180 - (float) alpha1);

		} else {
			bot.getLocation().setYaw((float) alpha1);

		}

		double dX = bot.getLocation().getX() - x;
		double dY = bot.getLocation().getY() - y;
		double dZ = bot.getLocation().getZ() - z;
		// System.out.println(playerYaw);
		bot.getLocation().setPitch((float) (Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI));

	}

	public void moveForward(float speedMps, float delta) {
		float speed = 4.317f;
		bot.getMovement().setVx(-Math.cos(0) * Math.sin(bot.getLocation().getYaw()) * (speed * delta));
		// playerVY = -Math.sin(0) * 0.216;
		bot.getMovement().setVz(Math.cos(0) * Math.cos(bot.getLocation().getYaw()) * (speed * delta));

	}

	public void attackEntity(int id) {

		bot.getClient()
				.getSession()
				.send(new ClientPlayerInteractEntityPacket(id,
						InteractAction.ATTACK));
		bot.getClient().getSession().send(new ClientSwingArmPacket());
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			Logger.getLogger(Minebot.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void setCurrentItem(int id) {
		int slot = 0;
//
//		for (ItemStack stack : bot.getInventory()) {
//			if (stack != null) {
//				int invId = stack.getId();
//				if (invId == id) {
//					bot.getClient()
//							.getSession()
//							.send(new ClientWindowActionPacket(0, 1, slot,
//									new ItemStack(id),
//									WindowAction.MOVE_TO_HOTBAR_SLOT,
//									MoveToHotbarParam.SLOT_1));
//
//					break;
//				}
//			}
//			slot++;
//
//		}
		bot.getClient().getSession().send(new ClientChangeHeldItemPacket(0));

	}

	public void follow(float delta, double x, double y, double z) {
		double vecX = bot.getLocation().getX() - x;
		// double vecY = bot.getY() - y;
		double vecZ = bot.getLocation().getZ() - z;
		float speed = 4.317f;// Meters per second
		bot.getMovement().setVx(vecX * (speed * delta));
		// mn.setVY(vecY);
		bot.getMovement().setVz(vecZ * (speed * delta));
	}

	public void jump(double velocity) {
		System.out.println("Jumping! " + bot.isOnGround());
		if (bot.isOnGround()) {
			bot.getMovement().setVy(velocity);// speed of falling player: v(t) = (0.98t - 1) ×
								// 3.92
			}
	}

	public void checkInventory() {
//		if (bot.getInventory() != null) {
//			if (bot.getInventory()[36] != null) {
//				bot.setItem(bot.getInventory()[36].getId());
//			}
//			int mineshaItem = bot.getItem();
//			for (ItemStack inv : bot.getInventory()) {
//
//				if ((mineshaItem != 267 || mineshaItem != 268
//						|| mineshaItem != 272 || mineshaItem != 276 || mineshaItem != 283)
//						&& bot.getMode() == PROTECT && inv != null) {
//
//					if (inv.getId() == 267 || inv.getId() == 268
//							|| inv.getId() == 272 || inv.getId() == 276
//							|| inv.getId() == 283) {
//						setCurrentItem(inv.getId());
//					}
//				} else if (inv != null && bot.getMode() == Mode.HUNGER) {
//					if (bot.isFood(mineshaItem)) {
//						eat(inv.getId(), inv);
//					} else {
//						//find food
//					}
//				}
//			}
//		}
	}

	public void eat(int id, ItemStack held) {
		System.out.println("Attempting to eat!");
		Position p = new Position(0, 0, 0);
		setCurrentItem(id);
		bot.getClient()
				.getSession()
				.send(new ClientPlayerPlaceBlockPacket(p, Face.INVALID, held,
						-1, 255, -1));
		bot.getClient()
				.getSession()
				.send(new ClientPlayerInteractEntityPacket(id,
						InteractAction.INTERACT));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			Logger.getLogger(Minebot.class.getName()).log(Level.SEVERE, null, ex);
		}
		bot.getClient()
				.getSession()
				.send(new ClientPlayerActionPacket(
						PlayerAction.RELEASE_USE_ITEM, p, Face.INVALID));
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			Logger.getLogger(Minebot.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	

}
