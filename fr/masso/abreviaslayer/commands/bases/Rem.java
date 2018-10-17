package fr.masso.abreviaslayer.commands.bases;

import java.io.IOException;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import fr.masso.abreviaslayer.AbreviaSlayer;
import fr.masso.abreviaslayer.configuration.ConfigurationManager;
import fr.masso.abreviaslayer.utils.Interpreter;
import fr.masso.abreviaslayer.utils.Message;

public class Rem {
	
	FileConfiguration script = AbreviaSlayer.get().getScript();
	FileConfiguration abbreviations = ConfigurationManager.getListFile("abbreviations"),
					  insults = ConfigurationManager.getListFile("insults");
	Message msg = AbreviaSlayer.get().message;
	List<String> list1, list2;

	public Rem (CommandSender sender, String[] args) throws IOException
	{
		String[] strs, lec;
		this.list1 = abbreviations.getStringList("abbreviations");
		this.list2 = insults.getStringList("insults");
		
		String word = args[1];
		lec = this.readLists(word);
		try
		{
			if (!lec[0].isEmpty() && lec[1].equals("abbreviations"))
			{
				this.list1.remove(lec[0]);
				this.abbreviations.set("abbreviations", this.list1);
				this.abbreviations.save(ConfigurationManager.LISTS_FOLDER + "abbreviations.yml");
				strs = Interpreter.get(this.msg.colorize(this.script.getString("success.rem_abbreviations")));
				sender.sendMessage(strs[0] + word + strs[1]);
			}
			else if (!lec[0].isEmpty() && lec[1].equals("insults"))
			{
				this.list2.remove(lec[0]);
				this.insults.set("insults", this.list2);
				this.insults.save(ConfigurationManager.LISTS_FOLDER + "insults.yml");
				strs = Interpreter.get(this.msg.colorize(this.script.getString("success.rem_insults")));
				sender.sendMessage(strs[0] + word + strs[1]);
			}
			else
				Help.already(sender, word, "rem");
		} catch (NullPointerException error)
		{
			Help.already(sender, word, "rem");
		}
	}
	
	public String[] readLists(String word)
	{
		String wordHope;
		String[] a = new String[2];
		for (int i = 0; i <= this.list1.size() - 1; i++)
		{
			if (this.list1.get(i).startsWith(word.toLowerCase()))
			{
				wordHope = this.list1.get(i);
				a[0] = wordHope;
				a[1] = "abbreviations";
				return a;
			}
		}
		for (int i = 0; i <= this.list2.size() - 1; i++)
		{
			if (this.list2.get(i).startsWith(word.toLowerCase()))
			{
				wordHope = this.list2.get(i);
				a[0] = wordHope;
				a[1] = "insults";
				return a;
			}
		}
		return a;
	}
	
}
