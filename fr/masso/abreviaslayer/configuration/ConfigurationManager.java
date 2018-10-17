package fr.masso.abreviaslayer.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.masso.abreviaslayer.AbreviaSlayer;

public class ConfigurationManager 
{
	static AbreviaSlayer plug = AbreviaSlayer.get();
	static final String PLUGIN_FOLDER = plug.getDataFolder() + File.separator;
	public static final String MUTES_FOLDER = PLUGIN_FOLDER + "mutes" + File.separator,
						LISTS_FOLDER = PLUGIN_FOLDER + "lists" + File.separator;
	
	public static void Init()
	{
		plug.getConfig().options().copyDefaults(true);
		plug.saveDefaultConfig();
		plug.saveResource("script.yml", false);
		plug.saveResource("lists/abbreviations.yml", false);
		plug.saveResource("lists/insults.yml", false);
		new File(MUTES_FOLDER).mkdir();
	}
	
	public static File getFile(String path) { return new File(path); }
	public static void saveFile(File file) throws IOException { YamlConfiguration.loadConfiguration(file).save(file); }
	
	public static FileConfiguration getListFile(String name)
	{
		return YamlConfiguration.loadConfiguration(new File(LISTS_FOLDER + name + ".yml"));
	}
	
	public static void addMuteFile(AsyncPlayerChatEvent content) throws IOException
	{
		Player p = content.getPlayer();
		Date date = new Date(System.currentTimeMillis());
		String playerName = p.getName(),
			   serverName = p.getServer().getServerName(),
			   worldName = p.getWorld().getName();
		
		File file = new File(MUTES_FOLDER + playerName + ".yml");
		
		if (!file.exists())
		{
			file.createNewFile();
		}
		
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		fileConf.set("UUID", p.getUniqueId().toString());
		fileConf.set("name", playerName);
		fileConf.set("at", date.toString());
		fileConf.set("server.name", serverName);
		fileConf.set("server.world", worldName);
		
		fileConf.save(file);
	}
}
