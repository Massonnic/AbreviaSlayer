package fr.masso.abreviaslayer.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class Completion implements TabCompleter 
{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) 
	{
		List<List<String>> lists = this.getLists();
		List<String> completion = new ArrayList<>();
		
		if (args.length == 2)
		{
			switch (args[0])
			{
			case "prevent":
				if (args[1].startsWith("a"))
					completion.add(lists.get(0).get(0));
				else if (args[1].startsWith("i"))
					completion.add(lists.get(0).get(1));
				else if (args[1].startsWith("u"))
					completion.add(lists.get(0).get(2));
				else
					completion.addAll(lists.get(0));
				break;
			case "sanction":
				if (args[1].startsWith("t"))
					completion.add(lists.get(2).get(0));
				else if (args[1].startsWith("f"))
					completion.add(lists.get(2).get(1));
				else
					completion.addAll(lists.get(2));
				break;
			}
		}
		else if (args.length == 3)
		{
			switch (args[0])
			{
			case "prevent":
				if (args[2].startsWith("t"))
					completion.add(lists.get(2).get(0));
				else if (args[2].startsWith("f"))
					completion.add(lists.get(2).get(1));
				else
					completion.addAll(lists.get(2));
				break;
			}
		}
		else if (args.length == 4)
		{
			switch (args[0])
			{
			case "add":
				if (args[3].startsWith("a"))
					completion.add(lists.get(1).get(0));
				else if (args[3].startsWith("i"))
					completion.add(lists.get(1).get(1));
				else
					completion.addAll(lists.get(1));
				break;
			}
		}
		return completion;
	}
	
	private List<List<String>> getLists()
	{
		List<List<String>> lists = new ArrayList<>();
		List<String> c1 = new ArrayList<>(), 
				c2 = new ArrayList<>(),
				c3 = new ArrayList<>();
		
		c1.add("abbreviations");
		c1.add("insults");
		c1.add("uppercases");
		
		c2.add("abbreviations");
		c2.add("insults");
		
		c3.add("true");
		c3.add("false");
		
		lists.add(c1);
		lists.add(c2);
		lists.add(c3);
		
		return lists;
	}
	
}
