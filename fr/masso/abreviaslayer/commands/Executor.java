package fr.masso.abreviaslayer.commands;

import java.io.IOException;

import org.bukkit.command.CommandSender;

public abstract interface Executor {

	public abstract void execute(
			CommandSender paramCommandSender, 
				String paramString,
				String[] paramArrayOfString) throws IOException;
	
}
