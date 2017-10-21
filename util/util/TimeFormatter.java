package util;

public class TimeFormatter
{
	public final static int timeInSecond = 0;

	public final static int timeInMinute = 1;


	private TimeFormatter(){

	}



	/**
	 * Return the {@code String} corresponding to the value of {@code arg} in the specified format.
	 * @param arg the time
	 * @see #timeInMinute
	 * @see #timeInSecond
	 */
	public static String fromLongtoString(long arg, int format)
	{
		String time = "";

		//hour
		arg %= 86400;
		time = String.valueOf(arg / 3600);

		//minute
		arg %= 3600;
		if((arg/60) > 9)
			time += ":" + String.valueOf(arg / 60);
		else
			time += ":0" + String.valueOf(arg / 60);

		//second
		if(format == timeInSecond)
		{
			if((arg%60) > 9)
				time += ":" + String.valueOf(arg % 60);
			else
				time += ":0" + String.valueOf(arg % 60);
		}

		return time;
	}

	/**
	 * Return the {@code long} corresponding to the value of {@code arg} in second
	 * @param arg the time
	 */
	public static long fromStringToLong(String arg) throws Exception
	{
		String[] temp = arg.split(":");
		if(temp.length > 3)
			throw new Exception("Unkown format. Here are the admissible format : hh:mm or hh:mm:ss");

		long result = 0;
		for(int i=0; i < temp.length; i++)
		{
			if(temp[i].length() > 2)
				throw new Exception("Unkown format. Here are the admissible format : hh:mm or hh:mm:ss");
			result *= 60;
			result += Long.parseLong(temp[i]);
		}

		if(temp.length == 2)
			result *= 60;

		return result;
	}


}
