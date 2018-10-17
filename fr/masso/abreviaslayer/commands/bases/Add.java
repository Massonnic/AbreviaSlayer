package fr.masso.abreviaslayer.commands.bases;

import java.io.IOException;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import fr.masso.abreviaslayer.AbreviaSlayer;
import fr.masso.abreviaslayer.configuration.ConfigurationManager;
import fr.masso.abreviaslayer.utils.Interpreter;
import fr.masso.abreviaslayer.utils.Message;

public class Add 
{	
	AbreviaSlayer plug = AbreviaSlayer.get();
	FileConfiguration script = plug.getScript();
	FileConfiguration abbreviations = ConfigurationManager.getListFile("abbreviations"),
					  insults = ConfigurationManager.getListFile("insults");
	Message msg = plug.message;
	String[] strs;

	public Add(CommandSender sender, String[] args) throws IOException
	{
		String wordHope = (args[1] + "/" + args[2].replace("_", " ")).toLowerCase(),
			   word = args[1], 
			   replaceBy = args[2].replace("_", " "),
			   elem = "";
		List<String> list1 = this.abbreviations.getStringList("abbreviations"),
					 list2 = this.insults.getStringList("insults");
		int i;

		for (i = 0; i <= list1.size() - 1; i++)
		{
			if (list1.get(i).split("/")[0].equalsIgnoreCase(word))
				elem = list1.get(i);
		}
		for (i = 0; i <= list2.size() - 1; i++)
		{
			if (list2.get(i).split("/")[0].equalsIgnoreCase(word))
				elem = list2.get(i);
		}
		
		if (elem.isEmpty())
		{
			switch (args[3].toLowerCase())
			{
			case "abbreviations":
				list1.add(wordHope);
				this.abbreviations.set("abbreviations", list1);
				this.abbreviations.save(ConfigurationManager.LISTS_FOLDER + "abbreviations.yml");
				this.strs = Interpreter.get(msg.colorize(script.getString("success.add_abbreviations")));
				sender.sendMessage(this.strs[0] + word + this.strs[1] + replaceBy + this.strs[2]);
			break;
			case "insults":
				list2.add(wordHope);
				this.insults.set("insults", list2);
				this.insults.save(ConfigurationManager.LISTS_FOLDER + "insults.yml");
				this.strs = Interpreter.get(msg.colorize(script.getString("success.add_insults")));
				sender.sendMessage(this.strs[0] + word + this.strs[1] + replaceBy + this.strs[2]);
			break;
			default:
				Help.syntaxError(sender, "add");
			}
		}
		else
			Help.already(sender, word, "add");
	}
	
}
