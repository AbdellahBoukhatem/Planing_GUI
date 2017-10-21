package view.planing.util;

import javafx.util.StringConverter;

public class TimeFormater extends StringConverter<Number>
{
	private String format = "HH:MM";

	/**
	 * Here are the admissible Format
	 * @param format
	 */
	public void setFormat(String format)
	{
		this.format = format;
	}


	@Override
	public Number fromString(String string)
	{
		String[] temp = string.split(":");
		if(temp.length > 3)
			throw new IllegalArgumentException("Unkown format. here are the admissible format : hh:mm or hh:mm:ss");

		long result = 0;

		for(int i=0; i < temp.length; i++)
		{
			if(temp[i].length() > 2)
				throw new IllegalArgumentException("Unkown format. here are the admissible format : hh:mm or hh:mm:ss");
			result *= 60;
			result += Long.parseLong(temp[i]);
		}

		if(temp.length == 2)
			result *= 60;

		return result;
	}

	@Override
	public String toString(Number object)
	{
		long arg = object.longValue();
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
		if(format.split(":").length > 2)
		{
			if((arg%60) > 9)
				time += ":" + String.valueOf(arg % 60);
			else
				time += ":0" + String.valueOf(arg % 60);
		}

		return time;
	}

}
