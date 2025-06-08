package com.github.urdaknows.managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.github.urdaknows.utils.UpdateCheckerResult;

public class UpdateCheckerManager {
   private String version;
   private String latestVersion;

   public UpdateCheckerManager(String version) {
      this.version = version;
   }

   public UpdateCheckerResult check() {
      try {
         HttpURLConnection https = (HttpURLConnection)(new URL("https://api.spigotmc.org/legacy/update.php?resource=113125")).openConnection();
         int timedOut = 1500;
         https.setConnectTimeout(timedOut);
         https.setReadTimeout(timedOut);
         this.latestVersion = (new BufferedReader(new InputStreamReader(https.getInputStream()))).readLine();
         return this.latestVersion.length() <= 7 && !this.version.equals(this.latestVersion) ? UpdateCheckerResult.noErrors(this.latestVersion) : UpdateCheckerResult.noErrors((String)null);
      } catch (Exception var3) {
         return UpdateCheckerResult.error();
      }
   }

   public String getLatestVersion() {
      return this.latestVersion;
   }
}
