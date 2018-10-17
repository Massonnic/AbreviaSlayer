package fr.masso.abreviaslayer.commands.bases;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.masso.abreviaslayer.AbreviaSlayer;
import fr.masso.abreviaslayer.listeners.PlayerChat;
import fr.masso.abreviaslayer.utils.Interpreter;
import fr.masso.abreviaslayer.utils.Message;

public class Warns {
	
	AbreviaSlayer plug = AbreviaSlayer.get();
	FileConfiguration script = plug.getScript();
	Message msg = plug.message;
	Dictionary<Player, Integer> warns;
	Enumeration<Player> players;
	String[] strs;

	public Warns(CommandSender sender)
	{
		this.warns = PlayerChat.warns;
		this.players = warns.keys();
		this.strs = Interpreter.get(this.msg.colorize(this.script.getString("success.warns_list")));
		Player p;
		String line = "";
		
		sender.sendMessage(this.msg.longLine());
		try
		{
			do
			{
				p = this.players.nextElement();
				line = line.concat(this.strs[0] + p.getName() + this.strs[1] + this.warns.get(p)).concat("   §7|   ");
			}
			while (players.hasMoreElements());
		}
		catch (NoSuchElementException e) 
		{
			this.strs = Interpreter.get(this.msg.colorize(this.script.getString("info.warns_empty")));
			sender.sendMessage(this.strs[0]);
		}
		sender.sendMessage(line);
		sender.sendMessage(this.msg.longLine());
		
	}
	
	public Warns(CommandSender sender, String player)
	{
		this.warns = PlayerChat.warns;
		this.strs = Interpreter.get(this.msg.colorize(this.script.getString("success.warns_list")));
		Player p = Bukkit.getPlayer(player);
		
		if (p != null)
			sender.sendMessage(this.strs[0] + player + this.strs[1] + this.warns.get(p));
		else
		{
			this.strs = Interpreter.get(this.msg.colorize(this.script.getString("error.warns_unknown")));
			sender.sendMessage(this.strs[0] + player + strs[1]);
		}
	}
	
}
