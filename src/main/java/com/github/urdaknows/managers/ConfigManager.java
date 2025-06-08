package com.github.urdaknows.managers;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.urdaknows.BlockedWords;

public class ConfigManager {
   private BlockedWords plugin;
   private CustomConfig configFile;
   private String messageCooldown;
   private List<String> blockedWords;
   private long cooldown;
   private boolean updateNotifications;
   private String messageReplacer;
   private int maxLetters;
   private boolean uppercaseBoolean;

   public ConfigManager(BlockedWords plugin) {
      this.plugin = plugin;
      this.configFile = new CustomConfig("config.yml", null, plugin);
      this.configFile.registerConfig();
      this.reloadConfig();
   }

   public void reloadConfig() {
      this.configFile.registerConfig();
      this.loadConfig();
   }

   public void loadConfig() {
      FileConfiguration config = this.configFile.getConfig();
      this.messageReplacer = config.getString("message-replacer");
      this.cooldown = config.getLong("cooldown");
      this.blockedWords = config.getStringList("blocked-words.chat");
      this.updateNotifications = config.getBoolean("update-notification");
      this.messageCooldown = config.getString("messageCooldown");
      this.uppercaseBoolean = config.getBoolean("enabled");
      this.maxLetters = config.getInt("max-letters");
   }

   public boolean isUppercaseBoolean() {
      return this.uppercaseBoolean;
   }

   public int getMaxLetters() {
      return this.maxLetters;
   }

   public String getMessageReplacer() {
      return this.messageReplacer;
   }

   public long getCooldown() {
      return this.cooldown;
   }

   public String getMessageCooldown() {
      return this.messageCooldown;
   }

   public List<String> getBlockedWords() {
      return this.blockedWords;
   }

   public boolean isUpdateNotifications() {
      return this.updateNotifications;
   }
}
