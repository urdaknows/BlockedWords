package com.github.urdaknows.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Color {
	public static String formatColor(String text) {
		if (text.contains("&")) {
			return ChatColor.translateAlternateColorCodes('&', text);
		} else {
			return text.contains("§") ? ChatColor.translateAlternateColorCodes('§', text) : text;
		}
	}

	public static List<String> formatColor(List<String> textList) {
		List<String> formattedTextList = new ArrayList<>();
		for (String text : textList) {
			if (text.contains("&")) {
				formattedTextList.add(ChatColor.translateAlternateColorCodes('&', text));
			} else if (text.contains("§")) {
				formattedTextList.add(ChatColor.translateAlternateColorCodes('§', text));
			} else {
				formattedTextList.add(text);
			}
		}
		return formattedTextList;
	}

}
