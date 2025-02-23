package me.eplugins.eglowaddon.expansion;

import lombok.Getter;
import lombok.Setter;
import me.eplugins.eglowaddon.EGlowAddon;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.shared.chat.EnumChatFormat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

@Setter
@Getter
public class TABExpansion {
	private boolean expansionEnabled;

	public TABExpansion() {
		EGlowAddon.getInstance().logger.sendMessage(TextComponent.fromLegacy("[Glow-Addon] Enabling TABExtension."));
		setExpansionEnabled(true);
	}

	public void updateColor(UUID uuid, String glowColor) {
		TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(uuid);

		if (tabPlayer == null)
			return;

		if (TabAPI.getInstance().getNameTagManager() != null) {
			String tagPrefix = TabAPI.getInstance().getNameTagManager().getOriginalPrefix(tabPlayer);

			if (tagPrefix.contains("<")) {
				for (EnumChatFormat colorName : EnumChatFormat.values()) {
					if (tagPrefix.toLowerCase().contains("<" + colorName.toString().toLowerCase())) {
						glowColor = "<" + glowColor + ">";
						break;
					}
				}
			} else {
				switch (glowColor.toLowerCase()) {
					case "red" -> glowColor = color("&c");
					case "dark_red" -> glowColor = color("&4");
					case "gold" -> glowColor = color("&6");
					case "yellow" -> glowColor = color("&e");
					case "aqua" -> glowColor = color("&b");
					case "dark_aqua" -> glowColor = color("&3");
					case "blue" -> glowColor = color("&9");
					case "dark_blue" -> glowColor = color("&1");
					case "green" -> glowColor = color("&a");
					case "dark_green" -> glowColor = color("&2");
					case "light_purple" -> glowColor = color("&d");
					case "dark_purple" -> glowColor = color("&5");
					case "white" -> glowColor = color("&f");
					case "gray" -> glowColor = color("&7");
					case "dark_gray" -> glowColor = color("&8");
					case "black" -> glowColor = color("&0");
					case "reset" -> glowColor = "";
				}
			}
			if (glowColor.isEmpty()) {
				TabAPI.getInstance().getNameTagManager().setPrefix(tabPlayer, null);
			} else {
				TabAPI.getInstance().getNameTagManager().setPrefix(tabPlayer, ((!tagPrefix.isEmpty()) ? tagPrefix : "") + glowColor);
			}
		}

		if (TabAPI.getInstance().getTabListFormatManager() != null) {
			String tabPrefix = TabAPI.getInstance().getTabListFormatManager().getOriginalPrefix(tabPlayer);
			TabAPI.getInstance().getTabListFormatManager().setPrefix(tabPlayer, tabPrefix);
		}
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}