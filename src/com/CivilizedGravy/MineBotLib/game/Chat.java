/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CivilizedGravy.MineBotLib.game;

import org.spacehq.mc.protocol.data.game.values.MessageType;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;

import com.CivilizedGravy.MineBotLib.Minebot;

/**
 *
 * @author whats_000
 */
public final class Chat {

	Minebot bot;

	public Chat(Minebot bot) {

		this.bot = bot;
	}

	public void say(String msg) {
		bot.getClient().getSession().send(new ClientChatPacket(msg));
	}

	public void HandleMsg(ServerChatPacket scp) {

		String translate = scp.getMessage().toJson().getAsJsonObject()
				.get("translate").getAsString();
		if (scp.getType() == MessageType.CHAT) {
			String message = scp.getMessage().toJson().getAsJsonObject()
					.get("with").getAsJsonArray().get(1).getAsString();
			String speaker = scp.getMessage().toJson().getAsJsonObject()
					.get("with").getAsJsonArray().get(0).getAsJsonObject()
					.get("insertion").getAsString();
			
			if (message.startsWith("..m") || message.startsWith("Minesha")
					|| message.startsWith("minesha")) {
				String[] commandRaw = message.split(" ");// 1 = command; 2... =
															// args
				if (commandRaw.length >= 2) {

				}
			} else
				System.out.println("<" + speaker + "> " + message);
		}
	}
}
