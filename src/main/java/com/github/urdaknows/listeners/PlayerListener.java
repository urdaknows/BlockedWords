package com.github.urdaknows.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.urdaknows.BlockedWords;
import com.github.urdaknows.utils.Color;

public class PlayerListener implements Listener {
   private BlockedWords plugin;
   private final String URL_RESOURCE = "https://www.spigotmc.org/resources/blocked-words-1-8-1-20-2.113125/updates";

   public PlayerListener(BlockedWords plugin) {
      this.plugin = plugin;
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      String latestVersion = this.plugin.getUpdateCheckerManager().getLatestVersion();
      if (player.isOp() && !this.plugin.pluginVersion.equals(latestVersion) && this.plugin.getConfigManager().isUpdateNotifications()) {
         player.sendMessage(Color.formatColor("&7New update available. Your version: &c" + this.plugin.pluginVersion + "&7, latest version: &a" + latestVersion));
         player.sendMessage(Color.formatColor("&7Download plugin here: &ahttps://www.spigotmc.org/resources/blocked-words-1-8-1-20-2.113125/updates"));
      }

   }
}
