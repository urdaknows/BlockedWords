package com.github.urdaknows.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.github.urdaknows.BlockedWords;
import com.github.urdaknows.managers.ConfigManager;
import com.github.urdaknows.managers.MessageManager;
import com.github.urdaknows.utils.Color;

public class ConfigCommand implements CommandExecutor, TabCompleter {
	private BlockedWords plugin;

	public ConfigCommand(BlockedWords plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			this.sendCommandList(sender);
			return true;
		} else if (args[0].equalsIgnoreCase("reload")) {
			return this.handleReloadCommand(sender);
		} else {
			return args[0].equalsIgnoreCase("version") ? this.handleVersionCommand(sender) : false;
		}
	}

	private boolean handleReloadCommand(CommandSender sender) {
		if (sender instanceof Player && !sender.hasPermission("blockedwords.reload")) {
			sender.sendMessage(Color.formatColor("&cYou do not have permission to run this command."));
			return true;
		} else {
			ConfigManager configManager = this.plugin.getConfigManager();
			MessageManager messageManager = this.plugin.getMessageManager();
			configManager.reloadConfig();
			messageManager.reloadConfig();
			sender.sendMessage(Color.formatColor(messageManager.reloadMessage));
			return true;
		}
	}

	private boolean handleVersionCommand(CommandSender sender) {
		if (sender instanceof Player && !sender.hasPermission("blockedwords.version")) {
			sender.sendMessage(Color.formatColor("&cYou do not have permission to run this command."));
			return true;
		} else {
			sender.sendMessage(
					Color.formatColor("&7The current version of Blocked Words is &av" + this.plugin.pluginVersion));
			return true;
		}
	}

	private void sendCommandList(CommandSender sender) {
		List<String> message = this.plugin.getMessageManager().commandList;
		for (String msg : message) {
			sender.sendMessage(Color.formatColor(msg));
		}
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> completions = new ArrayList<String>();
		if (args.length == 1) {
			completions.add("reload");
			completions.add("version");
		}

		return completions;
	}
}
