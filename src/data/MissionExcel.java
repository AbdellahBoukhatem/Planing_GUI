package data;

import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 * useful for the color. POI Excel have a strange way of coding the color so we save the object representing the color
 *
 */
public class MissionExcel extends Mission
{

	transient private XSSFColor color;

	/**
	 * Transilien constructor
	 * @param num number
	 * @param code code
	 * @param dS departure Station
	 * @param dH departure Hour
	 * @param eS ending Station
	 * @param eH ending Hour
	 * @param matIDs materials IDs
	 * @param matRequir required material
	 * @param dist distance in km
	 * @param category categories of the mission
	 * @param color XSSFColor
	 */
	public MissionExcel(String num, String code, String dS, long dH, String eS, long eH, String matIDs, int matRequir,
			double dist, String category, XSSFColor color)
	{
		super(num, code, dS, dH, eS, eH, matIDs, matRequir, dist, category);
		this.color = color;
	}

	/**
	 * FRET constructor
	 * @param num number
	 * @param dS departure Station
	 * @param dH departure Hour
	 * @param eS ending Station
	 * @param eH ending Hour
	 * @param matIDs materials IDs
	 * @param matRequir required material
	 * @param matCapacity maximum material capacity
	 * @param dist distance in km
	 * @param color XSSFColor
	 */
	public MissionExcel(String num, String dS, long dH, String eS, long eH, String matIDs, int matRequir,
			int matCapacity, double dist, XSSFColor color)
	{
		super(num, dS, dH, eS, eH, matIDs, matRequir, matCapacity, dist);
		this.color = color;
	}


	public XSSFColor getColor(){
		return color;
	}

}
