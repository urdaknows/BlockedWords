package com.github.urdaknows.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.urdaknows.BlockedWords;

public class CustomConfig {
   private BlockedWords plugin;
   private String fileName;
   private FileConfiguration fileConfiguration = null;
   private File file = null;
   private String folderName;

   public CustomConfig(String fileName, String folderName, BlockedWords plugin) {
      this.fileName = fileName;
      this.folderName = folderName;
      this.plugin = plugin;
   }

   public String getPath() {
      return this.fileName;
   }

   public void registerConfig() {
      if (this.folderName != null) {
         this.file = new File(this.plugin.getDataFolder() + File.separator + this.folderName, this.fileName);
      } else {
         this.file = new File(this.plugin.getDataFolder(), this.fileName);
      }

      if (!this.file.exists()) {
         if (this.folderName != null) {
            this.plugin.saveResource(this.folderName + File.separator + this.fileName, false);
         } else {
            this.plugin.saveResource(this.fileName, false);
         }
      }

      this.fileConfiguration = new YamlConfiguration();

      try {
         this.fileConfiguration.load(this.file);
      } catch (IOException var2) {
         var2.printStackTrace();
      } catch (InvalidConfigurationException var3) {
         var3.printStackTrace();
      }

   }

   public void saveConfig() {
      try {
         this.fileConfiguration.save(this.file);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public FileConfiguration getConfig() {
      if (this.fileConfiguration == null) {
         this.reloadConfig();
      }

      return this.fileConfiguration;
   }

   public boolean reloadConfig() {
      if (this.fileConfiguration == null) {
         if (this.folderName != null) {
            this.file = new File(this.plugin.getDataFolder() + File.separator + this.folderName, this.fileName);
         } else {
            this.file = new File(this.plugin.getDataFolder(), this.fileName);
         }
      }

      this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
      if (this.file != null) {
         YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.file);
         this.fileConfiguration.setDefaults(defConfig);
      }

      return true;
   }
}
