package fr.masso.abreviaslayer;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.masso.abreviaslayer.commands.Hub;
import fr.masso.abreviaslayer.configuration.ConfigurationManager;
import fr.masso.abreviaslayer.listeners.Completion;
import fr.masso.abreviaslayer.listeners.PlayerChat;
import fr.masso.abreviaslayer.utils.Message;

public class AbreviaSlayer extends JavaPlugin
{
	public ConsoleCommandSender console = getServer().getConsoleSender();
	public Message message = new Message();
	private static AbreviaSlayer plugin;
	private static String version;

    public void onEnable()
	{
		plugin = this;
		version = this.getDescription().getVersion();
		ConfigurationManager.Init();
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getCommand("abreviaslayer").setTabCompleter(new Completion());
	}
	
	public void onDisable()
	{
		reloadConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		try {
			new Hub(sender, cmd, label, args);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public FileConfiguration getScript()
	{ 
		return YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + "script.yml")); 
	}
	
	public static AbreviaSlayer get() { return plugin; }
	public static String getVersion() { return version; }
	
}
