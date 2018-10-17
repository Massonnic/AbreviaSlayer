package fr.masso.abreviaslayer.listeners;

import java.io.IOException;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.masso.abreviaslayer.AbreviaSlayer;
import fr.masso.abreviaslayer.commands.bases.Help;
import fr.masso.abreviaslayer.configuration.ConfigurationManager;
import fr.masso.abreviaslayer.utils.Interpreter;
import fr.masso.abreviaslayer.utils.Message;
import fr.masso.abreviaslayer.utils.Permissions;

public class PlayerChat implements Listener 
{

	AbreviaSlayer plug = AbreviaSlayer.get();
	FileConfiguration conf = plug.getConfig();
	FileConfiguration script = plug.getScript();
	Message msg = plug.message;
	boolean muted;
	AsyncPlayerChatEvent currentEvent;
	
	List<String> abbreviations, insults;
	public static Dictionary<Player, Integer> warns = new Hashtable<Player, Integer>();

	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e) throws IOException, InvalidConfigurationException
	{
		Player p = e.getPlayer();
		if (!Permissions.hasPermission(p, "bypass"))
		{
			Collection<? extends Player> ps = Bukkit.getServer().getOnlinePlayers();
			this.currentEvent = e;
			this.abbreviations = ConfigurationManager.getListFile("abbreviations").getStringList("abbreviations");
			this.insults = ConfigurationManager.getListFile("insults").getStringList("insults");
			
			CommandSender sender = Bukkit.getConsoleSender();
			StringBuilder builder = new StringBuilder();
			String newWord = "",
					badWord = "",
					str = e.getMessage(),
					first = str.substring(0, 1);
			String[] tab,
			words = str.split(" ");
			char[] chars = str.toCharArray();
			List<String> colors = this.conf.getStringList("sanction.color-warning");
			int check = 0, check2, i, n = 0,
					bad = 0,
					warning = this.conf.getInt("sanction.warning"),
					timeMute = this.conf.getInt("sanction.time-mute"),
					perCent = str.length(),
					perCentMax = this.conf.getInt("prevent.uppercases.pourcentage-max");
			
			
			for (Character c : chars)
			{
				if (Character.isUpperCase(c))
					n++;
			}
			
			//Boucle Majuscules
			for (String word : words)
			{
				for (Player player : ps)
				{
					if (word.equals(player.getName()))
						n -= player.getName().length();
				}
				for (@SuppressWarnings("unused") Character character : word.toCharArray())
				{
					word.replaceAll("é", "e");
					word.replaceAll("è", "e");
					word.replaceAll("ê", "e");
					word.replaceAll("ë", "e");
					word.replaceAll("à", "a");
					word.replaceAll("â", "a");
					word.replaceAll("ä", "a");
				}
				word = builder.toString();
			}
			if (this.conf.getBoolean("prevent.uppercases.enable"))
			{
				if ((100 * n) / perCent >= perCentMax && n != 0)
				{
					str = first + str.substring(1).toLowerCase();
					words = str.split(" ");
					for (String word : words)
					{
						for (Player player : ps)
						{
							if (word.equalsIgnoreCase(player.getName()))
								word = player.getName();
						}
						words[check] = word;
						check++;
					}
				}
			}
			
			//Boucle abreviations et insultes
			for (String word : words)
			{
				check = 0; bad = 0;
				if (this.conf.getBoolean("prevent.abbreviations"))
				{
					for (i = 0; i <= this.abbreviations.size() - 1; i++)
					{
						tab = this.abbreviations.get(i).split("/");
						if (tab[0].equalsIgnoreCase(word))
						{
							check2 = 0;
							for (Player player : ps)
							{
								if (tab[0].equals(player.getName()))
								{
									check2++;
								}
							}
							if (check2 == 0)
							{
								newWord = tab[1];
								builder.append(newWord + " ");
								check++;
							}
						}
					}
				}
				if (this.conf.getBoolean("prevent.insults"))
				{
					for (i = 0; i <= this.insults.size() - 1; i++)
					{
						tab = this.insults.get(i).split("/");
						if (tab[0].equalsIgnoreCase(word))
						{
							check2 = 0;
							for (Player player : ps)
							{
								if (tab[0].equals(player.getName()))
								{
									check2++;
								}
							}
							if (check2 == 0)
							{
								badWord = tab[0];
								newWord = tab[1];
								builder.append(newWord + " ");
								check++; bad++;
							}
						}
					}
				}
				if (check == 0)
				{
					builder.append(word + " ");
				}
			}
			String newMessage = builder.toString();
			
			updateMuted(e.isCancelled());
			if (bad != 0 && !isMuted() && this.conf.getBoolean("sanction.enable"))
				sanctionSection(p, muted, bad, warning, sender, timeMute, badWord, colors);
			if (muted == false)
				e.setMessage(newMessage);
			else
				e.setCancelled(true);
		}
	}
	
	public void sanctionSection(Player p, Boolean muted, int bad, int warning, CommandSender sender, int timeMute, String badWord, List<String> colors) throws IOException
	{
		if (warns.isEmpty() || warns.get(p) == null)
			warns.put(p, 0);
		int i, check = 0, warned = warns.get(p);
		String[] newcolors = new String[warning],
				 strs, strs2;
		
		for (i = 0; i <= colors.size() - 1; i++) {
			if (i < warning)
			{
				newcolors[i] = msg.colorize(colors.get(i));
				check++;
			}
		}
		try {
			while (check <= warning && newcolors[check] == null) {
				for (i = check; i <= newcolors.length - 1; i++) {
					newcolors[i] = newcolors[colors.size() - 1];
				}
			}
		} catch (Exception error) {}
		
		if (warned < warning) {
			warns.put(p, ++warned);
			strs = Interpreter.get(msg.colorize(this.script.getString("info.warning")));
			Help.warningInsults(p, badWord, warned, warning, timeMute);
			p.sendMessage(strs[0] + " : " + newcolors[warned - 1] + warned + "/ " + warning);
		}
		if (warned == warning) {
			warns.remove(p);
			strs = Interpreter.get(msg.colorize(this.script.getString("info.mute")));
			
			if (conf.getBoolean("sanction.custom-command.enable"))
			{
				strs2 = Interpreter.get(this.conf.getString("sanction.custom-command.syntax"));
				Bukkit.getScheduler().runTask(this.plug, new Runnable() {
					
					@Override
					public void run() {
						Bukkit.getServer().dispatchCommand(sender, strs2[0] + p.getName() + strs2[1]);
					}
				});
			}
			else
				Bukkit.getServer().dispatchCommand(sender, "emute " + p.getName() + " " + timeMute + "s");
			
			ConfigurationManager.addMuteFile(this.currentEvent);
			if (!this.conf.getBoolean("sanction.hidden"))
				Bukkit.broadcastMessage(strs[0] + p.getDisplayName() + strs[1] + timeMute + strs[2]);
			this.muted = true;
		}
	}

	public void updateMuted(Boolean m)
	{
		if (m == false)
			this.muted = false;
		else
			this.muted = true;
	}
	
	public Boolean isMuted()
	{
		return this.muted;
	}
}
