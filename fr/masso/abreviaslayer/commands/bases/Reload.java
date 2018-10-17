package fr.masso.abreviaslayer.commands.bases;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import fr.masso.abreviaslayer.AbreviaSlayer;
import fr.masso.abreviaslayer.utils.Interpreter;
import fr.masso.abreviaslayer.utils.Message;

public class Reload 
{
	AbreviaSlayer plug = AbreviaSlayer.get();
	FileConfiguration script = plug.getScript();
	Message msg = plug.message;

	public Reload(CommandSender sender) throws IOException
	{
		String[] strs;
		strs = Interpreter.get(this.msg.colorize(this.script.getString("info.reload")));
		sender.sendMessage(strs[0]);
		
		
		
		strs = Interpreter.get(this.msg.colorize(this.script.getString("success.reload")));
		sender.sendMessage(strs[0]);
	}
	
}
