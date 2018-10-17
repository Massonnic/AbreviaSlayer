package fr.masso.abreviaslayer.utils;

public abstract class Interpreter {

	static String str;
	
	public static String[] get(String str)
	{
		String[] strs = {str};
		if (str.contains("%v"))
		{
			strs = str.split("%v");
			if (strs.length == 1)
			{
				String[] strs2 = {strs[0], ""};
				return strs2;
			}
		}
		return strs;
	}
	
}
