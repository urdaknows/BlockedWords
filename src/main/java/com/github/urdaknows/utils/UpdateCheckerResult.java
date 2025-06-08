package com.github.urdaknows.utils;

public class UpdateCheckerResult {
   private String latestVersion;
   private boolean error;

   public UpdateCheckerResult(String latestVersion, boolean error) {
      this.latestVersion = latestVersion;
      this.error = error;
   }

   public String getLatestVersion() {
      return this.latestVersion;
   }

   public boolean isError() {
      return this.error;
   }

   public static UpdateCheckerResult noErrors(String latestVersion) {
      return new UpdateCheckerResult(latestVersion, false);
   }

   public static UpdateCheckerResult error() {
      return new UpdateCheckerResult((String)null, true);
   }
}
