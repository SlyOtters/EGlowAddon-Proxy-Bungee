package me.eplugins.eglowaddon;

import lombok.Getter;
import lombok.Setter;
import me.eplugins.eglowaddon.expansion.TABExpansion;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public final class EGlowAddon extends Plugin implements Listener {
	@Getter
	public static EGlowAddon instance;

	@Getter
	@Setter
	private TABExpansion tabExpansion;

	private final String IDENTIFIER = "eglow:bungee";

	public ProxyServer server;
	public CommandSender logger;

	@Override
	public void onEnable() {
		this.server = ProxyServer.getInstance();
		this.logger = server.getConsole();

		logger.sendMessage(TextComponent.fromLegacy("[eGlow-Addon] Enabling ProxyExtension."));
		server.registerChannel(IDENTIFIER);
		instance = this;

		server.getPluginManager().registerListener(this, this);
		server.registerChannel(IDENTIFIER);

		if (server.getPluginManager().getPlugin("TAB") != null)
			setTabExpansion(new TABExpansion());
	}

	@EventHandler
	public void onPluginMessageFromBackend(PluginMessageEvent event) {
		if (!event.getTag().equalsIgnoreCase("eglow:bungee") || !(event.getSender() instanceof ProxyServer))
			return;

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));

		try {
			if (in.readUTF().equals("TABProxyUpdateRequest")) {
				if (EGlowAddon.getInstance().getTabExpansion() != null)
					EGlowAddon.getInstance().getTabExpansion().updateColor(UUID.fromString(in.readUTF()), in.readUTF());
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}