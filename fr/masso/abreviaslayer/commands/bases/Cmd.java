package fr.masso.abreviaslayer.commands.bases;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import fr.masso.abreviaslayer.commands.Executor;
import fr.masso.abreviaslayer.utils.Permissions;

public class Cmd implements Executor {

	public void execute(CommandSender sender, String string, String[] args) throws IOException
	{
		if (args.length >= 1)
		{
			if (args[0].equalsIgnoreCase("prevent"))
			{
				if (Permissions.hasPermission(sender, "admin"))
				{
					if (args.length > 2)
					{
						if ((args[1].equalsIgnoreCase("insults") || args[1].equalsIgnoreCase("abbreviations") || args[1].equalsIgnoreCase("uppercases"))
								&& (args[2].equals("true") || args[2].equals("false")))
							Admin.prevent(sender, args);
						else
							Help.syntaxError(sender, "prevent");
					}
					else
						Help.syntaxError(sender, "prevent");
				}
				else
					Help.permissionMiss(sender, "admin");
			}
			else if (args[0].equalsIgnoreCase("sanction"))
			{
				if (Permissions.hasPermission(sender, "admin"))
				{
					if (args.length == 2)
					{
						if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false"))
							Admin.sanction(sender, args);
						else 
							Help.syntaxError(sender, "sanction");
					}
					else
						Help.syntaxError(sender, "sanction");
				}
				else
					Help.permissionMiss(sender, "admin");
			}
			else if (args[0].equalsIgnoreCase("add"))
			{
				if (Permissions.hasPermission(sender, "add"))
				{
					if (args.length > 3)
					{
						if ((!args[1].isEmpty() && !args[2].isEmpty()) && (args[3].equalsIgnoreCase("abbreviations") || args[3].equalsIgnoreCase("insults")))
							new Add(sender, args);
						else
							Help.syntaxError(sender, "add");
					}
					else
						Help.syntaxError(sender, "add");
				}
				else
					Help.permissionMiss(sender, "add");
			}
			else if (args[0].equalsIgnoreCase("rem"))
			{
				if (Permissions.hasPermission(sender, "rem"))
				{
					if (args.length > 1)
					{
						if (!args[1].isEmpty())
							new Rem(sender, args);
						else
							Help.syntaxError(sender, "rem");
					}
					else
						Help.syntaxError(sender, "rem");
				}
				else
					Help.permissionMiss(sender, "rem");
			}
//			else if (args[0].equalsIgnoreCase("reload"))
//			{
//				if (Permissions.hasPermission(sender, "admin"))
//					new Reload(sender);
//				else
//					Help.permissionMiss(sender, "admin");
//			}
			else if (args[0].equalsIgnoreCase("warns"))
			{
				if (Permissions.hasPermission(sender, "warns"))
				{
					if (args.length > 1)
						new Warns(sender, args[1]);
					else
						new Warns(sender);					
				}
				else
					Help.permissionMiss(sender, "warns");
			}
			else if (Permissions.hasPermission(sender, "help"))
				new Help(sender);
			else
				Help.permissionMiss(sender, "help");
		}
		else if (Permissions.hasPermission(sender, "help"))
			new Help(sender);
		else
			Help.permissionMiss(sender, "help");
	}
	
}
