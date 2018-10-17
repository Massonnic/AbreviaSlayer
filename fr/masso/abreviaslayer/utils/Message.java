package fr.masso.abreviaslayer.utils;

import org.bukkit.ChatColor;

public class Message
{
  public String colorize(String msg)
  {
	  return ChatColor.translateAlternateColorCodes('&', msg);	
  }
  
  public String longLine()
  {
	  return colorize("&7&m-----------------------------------------------------");
  }
}
