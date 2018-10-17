package fr.masso.abreviaslayer.commands.bases;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.masso.abreviaslayer.AbreviaSlayer;
import fr.masso.abreviaslayer.utils.Interpreter;
import fr.masso.abreviaslayer.utils.Message;
import fr.masso.abreviaslayer.utils.Permissions;

public class Help {
	
	static AbreviaSlayer plug = AbreviaSlayer.get();
	static FileConfiguration script = plug.getScript();
	static Message msg = plug.message;
	static String[] strs;
	
	public Help(CommandSender sender)
	{
		sender.sendMessage("§7§m------------------§6AbreviaSlayer Help§7§m-------------------");
		sender.sendMessage("§7Plugin version : §3" + AbreviaSlayer.getVersion());
		//strs = Interpreter.get(msg.colorize(script.getString("help.command_reload")));
		//sender.sendMessage("§5/abreviaslayer reload §9---> " + strs[0]);
		strs = Interpreter.get(msg.colorize(script.getString("help.command_prevent")));
		sender.sendMessage("§5/abreviaslayer prevent §8<§7insults §8|§7 abbreviations §8|§7 uppercases§8> <§7true §8|§7 false§8> §9---> " + strs[0]);
		strs = Interpreter.get(msg.colorize(script.getString("help.command_sanction")));
		sender.sendMessage("§5/abreviaslayer sanction §8<§7true §8|§7 false§8> §9---> " + strs[0]);
		strs = Interpreter.get(msg.colorize(script.getString("help.command_add")));
		sender.sendMessage("§5/abreviaslayer add §8<§7word§8> <§7replaceby§8> <§7abbreviations §8|§7 insults§8> §9---> " + strs[0]);
		strs = Interpreter.get(msg.colorize(script.getString("help.command_rem")));
		sender.sendMessage("§5/abreviaslayer rem §8<§7word§8> §9---> " + strs[0]);
		strs = Interpreter.get(msg.colorize(script.getString("help.command_warns")));
		sender.sendMessage("§5/abreviaslayer warns §8[§7player§8] §9---> " + strs[0]);
		sender.sendMessage(msg.longLine());
	}
	
	public static void permissionMiss(CommandSender sender, String perm)
	{
		strs = Interpreter.get(msg.colorize(script.getString("error.permission")));
		perm = Permissions.PREFIX + perm;
		if (strs.length > 1)
			sender.sendMessage(strs[0] + perm + strs[1]);
		else
			sender.sendMessage(strs[0]);
	}
	
	public static void already(CommandSender sender, String word, String which)
	{
		strs = Interpreter.get(msg.colorize(script.getString("error.already_exist")));
		if (which.equals("add"))
			sender.sendMessage(strs[0] + word + strs[1]);
		strs = Interpreter.get(msg.colorize(script.getString("error.doesnt_exist")));
		if (which.equals("rem"))
			sender.sendMessage(strs[0] + word + strs[1]);
	}
	
	public static void syntaxError(CommandSender sender, String which)
	{
		if (which.equals("prevent"))
			sender.sendMessage("§cUsage: §6/abreviaslayer prevent §c<insults | abbreviations | uppercases> <true | false>");
		if (which.equals("sanction"))
			sender.sendMessage("§cUsage: §6/abreviaslayer sanction §c<true | false>");
		if (which.equals("add"))
			sender.sendMessage("§cUsage: §6/abreviaslayer add §c<word> <replaceby> <abbreviations | insults>");
		if (which.equals("rem"))
			sender.sendMessage("§cUsage: §6/abreviaslayer rem §c<word>");
	}
	
	public static void warningInsults(Player p, String badWord, int warned, int warning, int timeMute)
	{
		if (warned < warning)
		{
			List<String> list = script.getStringList("sanction.before_mute");
			for (String str : list)
			{
				strs = Interpreter.get(msg.colorize(str));
				if (str.contains("%v"))
					p.sendMessage(strs[0] + badWord + strs[1]);
				else
					p.sendMessage(strs[0]);
			}
		}
		else
		{
			List<String> list = script.getStringList("sanction.after_mute");
			for (String str : list)
			{
				strs = Interpreter.get(msg.colorize(str));
				if (str.contains("%v"))
					p.sendMessage(strs[0] + timeMute + strs[1]);
				else
					p.sendMessage(strs[0]);
			}
		}
	}
}
