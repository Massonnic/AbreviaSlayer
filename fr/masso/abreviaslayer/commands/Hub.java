package fr.masso.abreviaslayer.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.masso.abreviaslayer.commands.bases.Cmd;

public class Hub {

	public Hub(CommandSender sender, Command cmd, String label, String[] args) throws IOException
	{
		Executor exe = null;
		
		if (cmd.getName().equalsIgnoreCase("abreviaslayer"))
		{
			exe = new Cmd();
		}
		if(exe != null)
		{
			exe.execute(sender, label, args);
		}
	}
	
}
