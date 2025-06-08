package com.github.urdaknows;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.urdaknows.commands.ConfigCommand;
import com.github.urdaknows.listeners.ChatFilter;
import com.github.urdaknows.listeners.PlayerListener;
import com.github.urdaknows.managers.ConfigManager;
import com.github.urdaknows.managers.MessageManager;
import com.github.urdaknows.managers.UpdateCheckerManager;
import com.github.urdaknows.utils.Color;
import com.github.urdaknows.utils.UpdateCheckerResult;

public class BlockedWords extends JavaPlugin {
	private ConfigManager configManager;
	private MessageManager messageManager;
	private UpdateCheckerManager updateCheckerManager;
	public String pluginVersion = this.getDescription().getVersion();
	public static BlockedWords instance;

	public static BlockedWords getInstance() {
		return instance;
	}

	public void onEnable() {
		this.configManager = new ConfigManager(this);
		this.messageManager = new MessageManager(this);
		this.updateCheckerManager = new UpdateCheckerManager(this.pluginVersion);
		this.updateMessage(this.updateCheckerManager.check());
		this.registerListeners();
		this.registerCommand();
		Bukkit.getConsoleSender()
				.sendMessage(Color.formatColor("&7Blocked Words has started correctly in &av" + this.pluginVersion));
	}

	public void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new ChatFilter(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}

	public void registerCommand() {
		this.getCommand("blocked").setExecutor(new ConfigCommand(this));
	}

	public ConfigManager getConfigManager() {
		return this.configManager;
	}

	public MessageManager getMessageManager() {
		return this.messageManager;
	}

	public UpdateCheckerManager getUpdateCheckerManager() {
		return this.updateCheckerManager;
	}

	public void updateMessage(UpdateCheckerResult result) {
		if (!result.isError()) {
			String latestVersion = result.getLatestVersion();
			if (latestVersion != null) {
				Bukkit.getConsoleSender().sendMessage(Color.formatColor("&7New update available. Your version: &c"
						+ this.pluginVersion + "&7, latest version: &a" + latestVersion));
				Bukkit.getConsoleSender().sendMessage(Color.formatColor(
						"&7Download plugin here: &ahttps://www.spigotmc.org/resources/blocked-words-1-8-1-20-2.113125/updates"));
			}
		} else {
			Bukkit.getConsoleSender().sendMessage(Color.formatColor("&cNo update available."));
		}

	}
}
