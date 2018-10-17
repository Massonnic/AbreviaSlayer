package fr.masso.abreviaslayer.commands.bases;

import java.io.IOException;

import fr.masso.abreviaslayer.utils.Message;
import org.bukkit.command.CommandSender;

import fr.masso.abreviaslayer.AbreviaSlayer;

import org.bukkit.configuration.file.FileConfiguration;

public class Admin {

	static Message msg = AbreviaSlayer.get().message;
	static FileConfiguration script = AbreviaSlayer.get().getScript();

	public static void prevent(CommandSender sender, String[] args) throws IOException
	{
		if (args[1].equalsIgnoreCase("insults"))
		{
			switch (args[2].toLowerCase())
			{
			case "true":
				AbreviaSlayer.get().getConfig().set("prevent.insults", true);
				AbreviaSlayer.get().saveConfig();
				sender.sendMessage(msg.colorize(script.getString("info.enable_insults")));
				break;
			case "false":
				AbreviaSlayer.get().getConfig().set("prevent.insults", false);
				AbreviaSlayer.get().saveConfig();
				sender.sendMessage(msg.colorize(script.getString("info.disable_insults")));
				break;
			}
		}
		else if (args[1].equalsIgnoreCase("abbreviations"))
		{
			switch (args[2].toLowerCase())
			{
			case "true":
				AbreviaSlayer.get().getConfig().set("prevent.abbreviations", true);
				AbreviaSlayer.get().saveConfig();
				sender.sendMessage(msg.colorize(script.getString("info.enable_abbreviations")));
				break;
			case "false":
				AbreviaSlayer.get().getConfig().set("prevent.abbreviations", false);
				AbreviaSlayer.get().saveConfig();
				sender.sendMessage(msg.colorize(script.getString("info.disable_abbreviations")));
				break;
			}
		}
		else if (args[1].equalsIgnoreCase("uppercases"))
		{
			switch (args[2].toLowerCase())
			{
			case "true":
				AbreviaSlayer.get().getConfig().set("prevent.uppercases.enable", true);
				AbreviaSlayer.get().saveConfig();
				sender.sendMessage(msg.colorize(script.getString("info.enable_uppercases")));
				break;
			case "false":
				AbreviaSlayer.get().getConfig().set("prevent.uppercases.enable", false);
				AbreviaSlayer.get().saveConfig();
				sender.sendMessage(msg.colorize(script.getString("info.disable_uppercases")));
				break;
			}
		}
	}
	
	public static void sanction(CommandSender sender, String[] args) throws IOException
	{
		switch (args[1].toLowerCase())
		{
		case "true":
			AbreviaSlayer.get().getConfig().set("sanction.enable", true);
			AbreviaSlayer.get().saveConfig();
			sender.sendMessage(msg.colorize(script.getString("info.enable_sanction")));
			break;
		case "false":
			AbreviaSlayer.get().getConfig().set("sanction.enable", false);
			AbreviaSlayer.get().saveConfig();
			sender.sendMessage(msg.colorize(script.getString("info.disable_sanction")));
			break;
		}
	}
	
}
