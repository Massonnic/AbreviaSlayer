package fr.masso.abreviaslayer.utils;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.bukkit.command.CommandSender;

public class Permissions {
	
	public static final String PREFIX = "abreviaslayer.",
							   NPREFIX = "-abreviaslayer.";

	public static Boolean hasPermission(CommandSender sender, String perm)
	{
		//AbreviaSlayer.get().console.sendMessage(String.valueOf(sender.hasPermission(NPREFIX + perm)));
		//if (sender.hasPermission(NPREFIX + perm))
		//	return false;
		if (!sender.hasPermission(PREFIX + perm))
			return Permissions.HasParentPermission(sender, perm);
		return true;
	}
	
	private static Boolean HasParentPermission(CommandSender sender, String perm)
	{
		Dictionary<String, Integer> permissions = Permissions.getHierarchy();
		String permInLoop;
		int currentPermLevel = permissions.get(perm),
			levelParent;
		
		for (Enumeration<String> keys = permissions.keys(); keys.hasMoreElements();)
		{
			permInLoop = keys.nextElement();
			levelParent = permissions.get(permInLoop);
			if (levelParent > currentPermLevel && sender.hasPermission(PREFIX + permInLoop) 
					&& !permInLoop.startsWith("bypass"))
				return true;
		}
		return false;
	}
	
	private static Dictionary<String, Integer> getHierarchy()
	{
		Dictionary<String, Integer> permissions = new Hashtable<>();
		
		permissions.put("*", 4);
		permissions.put("admin", 3);
		permissions.put("bypass", 3);
		permissions.put("add", 2);
		permissions.put("rem", 2);
		permissions.put("warns", 2);
		permissions.put("help", 1);
		
		return permissions;
	}
	
}
