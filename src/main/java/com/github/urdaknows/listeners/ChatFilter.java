package com.github.urdaknows.listeners;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.urdaknows.BlockedWords;
import com.github.urdaknows.utils.Color;

public class ChatFilter implements Listener {
   private final BlockedWords plugin;
   private final Pattern uppercasePattern;
   private long lastMessageTime;
   private final List<String> blockedWords;

   public ChatFilter(BlockedWords plugin) {
      this.plugin = plugin;
      this.uppercasePattern = Pattern.compile("(?i)[A-Z]{" + plugin.getConfigManager().getMaxLetters() + ",}");
      this.lastMessageTime = 0L;
      this.blockedWords = plugin.getConfigManager().getBlockedWords();
   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void onPlayerChat(AsyncPlayerChatEvent event) {
      if (!this.shouldBypass(event.getPlayer())) {
         String message = event.getMessage();
         long currentTime = System.currentTimeMillis();
         if (this.isCooldownActive(currentTime)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Color.formatColor(this.plugin.getConfigManager().getMessageCooldown()));
         } else {
            String word;
            for(Iterator var5 = this.blockedWords.iterator(); var5.hasNext(); message = this.filterBlockedWord(message, word)) {
               word = (String)var5.next();
            }

            if (this.plugin.getConfigManager().isUppercaseBoolean() && this.containsExcessiveUppercase(message)) {
               event.setCancelled(true);
               event.getPlayer().sendMessage(Color.formatColor(this.plugin.getMessageManager().uppercaseMessage));
            } else {
               event.setMessage(message);
               this.lastMessageTime = currentTime;
            }
         }
      }
   }

   private boolean shouldBypass(Player player) {
      return player.hasPermission("blockedwords.bypass") || player.isOp();
   }

   private boolean isCooldownActive(long currentTime) {
      long cooldown = this.plugin.getConfigManager().getCooldown() * 1000L;
      return currentTime - this.lastMessageTime < cooldown;
   }

   private String filterBlockedWord(String message, String word) {
      String lowercaseMessage = message.toLowerCase();
      String lowercaseWord = word.toLowerCase();
      if (lowercaseMessage.contains(lowercaseWord)) {
         String replacement = this.repeat(Color.formatColor(this.plugin.getConfigManager().getMessageReplacer()), word.length());
         message = message.replaceAll("(?i)" + Pattern.quote(word), replacement);
      }

      return message;
   }

   private boolean containsExcessiveUppercase(String message) {
      return this.uppercasePattern.matcher(message).find();
   }

   public String repeat(String str, int times) {
      StringBuilder repeated = new StringBuilder();

      for(int i = 0; i < times; ++i) {
         repeated.append(str);
      }

      return repeated.toString();
   }
}
