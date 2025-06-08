package com.github.urdaknows.managers;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.urdaknows.BlockedWords;

public class MessageManager {
   private BlockedWords plugin;
   private CustomConfig configFile;
   public String reloadMessage;
   public String uppercaseMessage;
   public List<String> commandList;

   public MessageManager(BlockedWords plugin) {
      this.plugin = plugin;
      this.configFile = new CustomConfig("messages.yml", (String)null, plugin);
      this.configFile.registerConfig();
      this.reloadConfig();
   }

   public void reloadConfig() {
      this.configFile.registerConfig();
      this.loadConfig();
   }

   public void loadConfig() {
      FileConfiguration config = this.configFile.getConfig();
      this.reloadMessage = config.getString("reloadMessage");
      this.uppercaseMessage = config.getString("uppercaseMessage");
      this.commandList = config.getStringList("command-list-message");
   }
}
