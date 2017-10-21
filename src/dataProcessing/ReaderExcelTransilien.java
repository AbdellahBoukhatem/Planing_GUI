package dataProcessing;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import data.*;
import util.TimeLapse;



public class ReaderExcelTransilien extends AbstractExcelReader
{
	public final static long startingServiceSecond  = 10800; //3:00
	private final static long secondPerDay	=86400;

	public final static String materialSheet		= "Materiels";
	public final static String missionsSheet  		= "Sillons";
	public final static String maintenaceSheet	 	= "Maintenances";
	public final static String infraSheet			= "Infrastructure";
	public final static String stationsSheet		= "Stations";
	public final static String emptyRideSheet		= "Voyage a vide";
	public final static String parkSheet 			= "Garages";
	public final static String parametersSheet 		= "Parametrage";


	public ReaderExcelTransilien(String path) throws Exception
	{
		file= new FileInputStream(new File(path));
	    workbook = new XSSFWorkbook(file);
	}


	//---------------------------------------------------------------------------------------------------------------------------------------------------------------


	private String getTypeSillon(XSSFSheet sheet, int ligne, int col) throws Exception
	{
		if(/*Algorithm.imbrication == false || */checkIfCellNull(sheet, ligne, col))
			return "OD1";

		return floatToString(sheet.getRow(ligne).getCell(col));
	}


	private double getTauxMaintenance(XSSFSheet sheet, int ligne, int col) throws Exception
	{
		if(/*Algorithm.tauxVisite == false || */checkIfCellNull(sheet, ligne, col) || checkIfNotNumeric(sheet, ligne, col))
			return 0;

		double temp = sheet.getRow(ligne).getCell(col).getNumericCellValue();
		if(temp < 0 || temp > 1)
			throw new Exception("Le taux de la cellule " + (ligne+1) + getNumCellule(col+1) + " de l\'onglet " + sheet.getSheetName() +" n\'est pas compris entre 0 et 1");

		return temp;
	}




	//---------------------------------------------------------------------------------------------------------------------------------------------------------------


	/**
	 * {@inheritDoc} that is an Excel file. The format returned is The Transilien one
	 */
	public ArrayList<Material> readingMaterials() throws Exception
	{
		ArrayList<Material> list = new ArrayList<Material>();
		XSSFSheet spreadsheet = workbook.getSheet(materialSheet);
		checkIfSheetNull(spreadsheet, materialSheet);

		int nb_row = spreadsheet.getLastRowNum();
//		System.out.println("Materiel " + nb_row);

		for( int i=1; i <= nb_row; i++)
		{
			//when a row or a cell is empty
			if(checkIfRowNull(spreadsheet, i) || checkIfCellNull(spreadsheet, i, 0) || checkIfCellNull(spreadsheet, i, 1)
					|| checkIfCellNull(spreadsheet, i, 2) || checkIfCellNull(spreadsheet, i, 3) || checkIfCellNull(spreadsheet, i, 4)
					|| checkIfCellNull(spreadsheet, i, 5) || checkIfCellNull(spreadsheet, i, 6) || checkIfNotNumeric(spreadsheet, i, 2)
					|| checkIfNotNumeric(spreadsheet, i, 3) || checkIfNotNumeric(spreadsheet, i, 4) || checkIfNotNumeric(spreadsheet, i, 5)
					|| checkIfNotNumeric(spreadsheet, i, 6))
				continue;


			XSSFRow rowI = spreadsheet.getRow(i);

			String name, matID;
			double stock, length, cycle, cost, switCab;

			name	= rowI.getCell(0).toString();
			matID	= floatToString(rowI.getCell(1));
			stock	= rowI.getCell(2).getNumericCellValue();
			length	= rowI.getCell(3).getNumericCellValue();
			cycle	= rowI.getCell(4).getNumericCellValue();
			switCab	= rowI.getCell(5).getNumericCellValue() * 60;
			cost	= rowI.getCell(6).getNumericCellValue();

			list.add(new Material(name, matID, (int)stock, (int)length, cost, (int)cycle, (long)switCab));
		}

		return list;
	}


	/**
	 * {@inheritDoc} that is an Excel file. The format returned is The Transilien one
	 */
	public ArrayList<Mission> readingMissions() throws Exception
	{
		ArrayList<Mission> list = new ArrayList<Mission>();
		XSSFSheet spreadsheet = workbook.getSheet(missionsSheet);
		checkIfSheetNull(spreadsheet, missionsSheet);

		int nb_row = spreadsheet.getLastRowNum();
//		System.out.println("Le nombre de missions est " + nb_row);

		for(int i=1; i <= nb_row; i++)
		{
			//when a row or a cell is empty
			if(checkIfRowNull(spreadsheet, i) || checkIfCellNull(spreadsheet, i, 0) || checkIfCellNull(spreadsheet, i, 1)
					|| checkIfCellNull(spreadsheet, i, 2) || checkIfCellNull(spreadsheet, i, 3) || checkIfCellNull(spreadsheet, i, 4)
					|| checkIfCellNull(spreadsheet, i, 5) || checkIfCellNull(spreadsheet, i, 6) || checkIfCellNull(spreadsheet, i, 7)
					|| checkIfCellNull(spreadsheet, i, 8) || checkIfNotDate(spreadsheet, i, 3) || checkIfNotDate(spreadsheet, i, 5)
					|| checkIfNotNumeric(spreadsheet, i, 7) || checkIfNotNumeric(spreadsheet, i, 8))
				continue;


			XSSFRow rowI = spreadsheet.getRow(i);
			XSSFColor color = rowI.getCell(0).getCellStyle().getFillForegroundColorColor();


			String num, code, dS, aS, matIDs, category;
			long dH, aH;
			double matRequir, dist;

			num			= floatToString(rowI.getCell(0));
			code		= rowI.getCell(1).toString();
			dS			= rowI.getCell(2).toString();
			dH			= (long)(rowI.getCell(3).getNumericCellValue() * secondPerDay);
			aS			= rowI.getCell(4).toString();
			aH			= (long)(rowI.getCell(5).getNumericCellValue() * secondPerDay);
			matIDs		= floatToString(rowI.getCell(6));
			matRequir	= rowI.getCell(7).getNumericCellValue();
			dist		= rowI.getCell(8).getNumericCellValue();
			category	= getTypeSillon(spreadsheet, i, 9);

//			System.out.println(matIDs);

			//after midnight
			if(TimeLapse.isBefore(dH, startingServiceSecond))
				dH += secondPerDay;

			if(TimeLapse.isBefore(aH, dH) || TimeLapse.isBefore(aH, startingServiceSecond))
				aH += secondPerDay;


			list.add(new MissionExcel(num, code, dS, (long)dH, aS, (long)aH, matIDs, (int)matRequir, dist, category, color));
		}

//		liste.sort(new TrainComparator());
		return list;
	}


	/**
	 * {@inheritDoc} that is an Excel file. The format returned is The Transilien one
	 */
	public ArrayList<MaintenanceSlot> readingMaintenance() throws Exception
	{
		ArrayList<MaintenanceSlot> list = new ArrayList<MaintenanceSlot>();
		XSSFSheet spreadsheet = workbook.getSheet(maintenaceSheet);
		checkIfSheetNull(spreadsheet, maintenaceSheet);

		int nb_row = spreadsheet.getLastRowNum();
//		System.out.println("Maintenance " + nb_row);

		for( int i=1; i <= nb_row; i++)
		{
			//when a row or a cell is empty
			if(checkIfRowNull(spreadsheet, i) || checkIfCellNull(spreadsheet, i, 0) || checkIfCellNull(spreadsheet, i, 1)
					|| checkIfCellNull(spreadsheet, i, 2) || checkIfCellNull(spreadsheet, i, 3) || checkIfCellNull(spreadsheet, i, 4)
					|| checkIfNotDate(spreadsheet, i, 2) || checkIfNotDate(spreadsheet, i, 3) || checkIfNotNumeric(spreadsheet, i, 4))
				continue;

			XSSFRow rowI = spreadsheet.getRow(i);

			String name, matReq;
			long startH, endH;
			double capacity, rate;

			name	 = rowI.getCell(0).toString();
			matReq	 = floatToString(rowI.getCell(1));
			startH	 = (long)(rowI.getCell(2).getNumericCellValue() * secondPerDay);
			endH	 = (long)(rowI.getCell(3).getNumericCellValue() * secondPerDay);
			capacity = rowI.getCell(4).getNumericCellValue();
			rate	 = getTauxMaintenance(spreadsheet, i, 5);

//			System.out.println(rate);

			//after midnight
			if(TimeLapse.isBefore(startH, startingServiceSecond))
				startH += secondPerDay;

			if(TimeLapse.isBefore(endH, startH) || TimeLapse.isBefore(endH, startingServiceSecond))
				endH += secondPerDay;

			list.add(new MaintenanceSlot( name, matReq, (long)startH, (long)endH, (int)capacity, rate));
		}

//		liste.sort(new TrainComparator());
		return list;
	}


	/**
	 * {@inheritDoc} that is an Excel file. The format returned is The Transilien one
	 */
	public Infrastructure readingInfrastructure() throws Exception
	{
		ArrayList<Station> list = new ArrayList<Station>();
		XSSFSheet spreadsheet = workbook.getSheet(infraSheet);
		checkIfSheetNull(spreadsheet, infraSheet);

		int nb_row = spreadsheet.getLastRowNum();
//		System.out.println("W " + nb_row);

		for( int i=1; i <= nb_row; i++)
		{
			if(checkIfRowNull(spreadsheet, i) || checkIfCellNull(spreadsheet, i, 0) || checkIfCellNull(spreadsheet, i, 1)
					|| checkIfNotNumeric(spreadsheet, i, 1))
				continue;

			XSSFRow rowI = spreadsheet.getRow(i);

			String station;
			boolean turnBack = false;
			int temp;

			station	= rowI.getCell(0).toString();
			temp	= (int)rowI.getCell(1).getNumericCellValue();

			if(temp == 1)
				turnBack = true;

			list.add( new Station(station, turnBack));
		}

		readingStationsSpecification(list);

		return new Infrastructure(list, readingEmptyRide(), readingNightPark());
	}


	/**
	 * Fill the stations list with the turning back and the parking time
	 * @param stations a list of the stations
	 */
	private void readingStationsSpecification(ArrayList<Station> stations) throws Exception
	{
		XSSFSheet spreadsheet = workbook.getSheet(stationsSheet);
		checkIfSheetNull(spreadsheet, stationsSheet);

		int nb_row = spreadsheet.getLastRowNum();

		for( int i=1; i <= nb_row; i++)
		{
			if(checkIfRowNull(spreadsheet, i) || checkIfCellNull(spreadsheet, i, 0) || checkIfCellNull(spreadsheet, i, 1)
					|| checkIfCellNull(spreadsheet, i, 2) || checkIfCellNull(spreadsheet, i, 3) || checkIfCellNull(spreadsheet, i, 4)
					|| checkIfNotDate(spreadsheet, i, 1) || checkIfNotDate(spreadsheet, i, 2)
					|| checkIfNotNumeric(spreadsheet, i, 3) || checkIfNotNumeric(spreadsheet, i, 4))
				continue;

			XSSFRow rowI = spreadsheet.getRow(i);

			String station;
			long startH, endH;
			double turn, park;

			station	= rowI.getCell(0).toString();
			startH	= (long)(rowI.getCell(1).getNumericCellValue() * secondPerDay);
			endH	= (long)(rowI.getCell(2).getNumericCellValue() * secondPerDay);
			turn	= rowI.getCell(3).getNumericCellValue() * 60;
			park	= rowI.getCell(4).getNumericCellValue() * 60;

			if(TimeLapse.isEqual(startH, endH))
			{
				startH = 0;
				endH = 2 * secondPerDay;
			}else
				if(TimeLapse.isBefore(startH, startingServiceSecond))
					startH += secondPerDay;

			if(TimeLapse.isBefore(endH, startH) || TimeLapse.isBefore(endH, startingServiceSecond))
				endH += secondPerDay;

			int s=0;
			for(; s < stations.size(); s++)
				if(stations.get(s).equals(station))
					break;

			if(s >= stations.size())
				throw new Exception("La station " + station + "n'st pas dans la liste des stations de l'onglet " + infraSheet);

			Station sta = stations.get(s);
			sta.addTurningBackSlot((long)turn, (long)startH, (long)endH);
			sta.addParkingSlot((long)park, (long)startH, (long)endH);
		}
	}


	/**
	 * return the list of parking open at night
	 * @return a list of Park
	 * @throws Exception if a problem occur
	 */
	private ArrayList<Park> readingNightPark() throws Exception
	{
		ArrayList<Park> list = new ArrayList<Park>();
		XSSFSheet spreadsheet = workbook.getSheet(parkSheet);
		checkIfSheetNull(spreadsheet, parkSheet);

		int nb_row = spreadsheet.getLastRowNum();

		for( int i=1; i <= nb_row; i++)
		{
			if(checkIfRowNull(spreadsheet, i) || checkIfCellNull(spreadsheet, i, 0) || checkIfCellNull(spreadsheet, i, 1)
					|| checkIfCellNull(spreadsheet, i, 2) || checkIfCellNull(spreadsheet, i, 3) || checkIfCellNull(spreadsheet, i, 4)
					|| checkIfNotDate(spreadsheet, i, 2) || checkIfNotDate(spreadsheet, i, 3) || checkIfNotNumeric(spreadsheet, i, 4))
				continue;

			XSSFRow rowI = spreadsheet.getRow(i);

			String name, matIDs;
			long startH, endH;
			double cap;

			name	= rowI.getCell(0).toString();
			matIDs	= floatToString(rowI.getCell(1));
			startH	= (long)(rowI.getCell(2).getNumericCellValue() * secondPerDay);
			endH	= (long)(rowI.getCell(3).getNumericCellValue() * secondPerDay);
			cap		= rowI.getCell(4).getNumericCellValue();

			if(TimeLapse.isEqual(startH, endH))
			{
				startH = 0;
				endH = 2 * secondPerDay;
			}else
				if(TimeLapse.isBefore(startH, startingServiceSecond))
					startH += secondPerDay;

			if(TimeLapse.isBefore(endH, startH) || TimeLapse.isBefore(endH, startingServiceSecond))
				endH += secondPerDay;

//			if(endH < startingDayHour)
//				continue;

			list.add( new Park(i - 1, name, matIDs, (long)startH, (long)endH, (int)cap) );
		}

		return list;
	}


	/**
	 * {@inheritDoc} that is an Excel file. The format returned is The Transilien one
	 */
	public ArrayList<EmptyRide> readingEmptyRide() throws Exception
	{
		ArrayList<EmptyRide> list = new ArrayList<EmptyRide>();
		XSSFSheet spreadsheet = workbook.getSheet(emptyRideSheet);
		checkIfSheetNull(spreadsheet, emptyRideSheet);

		int nb_row = spreadsheet.getLastRowNum();
//		System.out.println("W " + nb_row);

		for( int i=1; i <= nb_row; i++)
		{
			if(checkIfRowNull(spreadsheet, i) || checkIfCellNull(spreadsheet, i, 0) || checkIfCellNull(spreadsheet, i, 1)
					|| checkIfCellNull(spreadsheet, i, 2) || checkIfCellNull(spreadsheet, i, 3) || checkIfCellNull(spreadsheet, i, 4)
					|| checkIfCellNull(spreadsheet, i, 5) || checkIfCellNull(spreadsheet, i, 6) || checkIfNotNumeric(spreadsheet, i, 3)
					|| checkIfNotNumeric(spreadsheet, i, 4)|| checkIfNotNumeric(spreadsheet, i, 5) || checkIfNotNumeric(spreadsheet, i, 6) )
				continue;

			XSSFRow rowI = spreadsheet.getRow(i);

			String station1, station2, requiMat;
			long startH, endH;
			double duration, distance;


			station1	= rowI.getCell(0).toString();
			station2	= rowI.getCell(1).toString();
			requiMat	= floatToString(rowI.getCell(2));
			startH		= (long)(rowI.getCell(3).getNumericCellValue() * secondPerDay);
			endH		= (long)(rowI.getCell(4).getNumericCellValue() * secondPerDay);
			duration	= rowI.getCell(5).getNumericCellValue() * 60;
			distance	= rowI.getCell(6).getNumericCellValue();

			if(TimeLapse.isEqual(startH, endH))
			{
				startH = 0;
				endH = 2 * secondPerDay;
			}else
				if(TimeLapse.isBefore(startH, startingServiceSecond))
					startH += secondPerDay;

			if(TimeLapse.isBefore(endH, startH) || TimeLapse.isBefore(endH, startingServiceSecond))
				endH += secondPerDay;

			list.add( new EmptyRide(station1, station2, (long)duration, distance, requiMat, (long)startH, (long)endH) );
		}

		return list;
	}


	/**
	 * {@inheritDoc} that is an Excel file. The format returned is The Transilien one
	 */
	public ArrayList<ParkingTask> readingParks() throws Exception
	{
		ArrayList<ParkingTask> list = new ArrayList<ParkingTask>();
		XSSFSheet spreadsheet = workbook.getSheet(parkSheet);
		checkIfSheetNull(spreadsheet, parkSheet);

		int nb_row = spreadsheet.getLastRowNum();

		for( int i=1; i <= nb_row; i++)
		{
			if(checkIfRowNull(spreadsheet, i) || checkIfCellNull(spreadsheet, i, 0) || checkIfCellNull(spreadsheet, i, 1)
					|| checkIfCellNull(spreadsheet, i, 2) || checkIfCellNull(spreadsheet, i, 3) || checkIfCellNull(spreadsheet, i, 4)
					|| checkIfNotDate(spreadsheet, i, 2) || checkIfNotDate(spreadsheet, i, 3) || checkIfNotNumeric(spreadsheet, i, 4))
				continue;

			XSSFRow rowI = spreadsheet.getRow(i);

			String name, matIDs;
			double cap, startH, endH;

			name	= rowI.getCell(0).toString();
			matIDs	= floatToString(rowI.getCell(1));
			startH	= rowI.getCell(2).getNumericCellValue() * secondPerDay;
			endH	= rowI.getCell(3).getNumericCellValue() * secondPerDay;
			cap		= rowI.getCell(4).getNumericCellValue();

			if(startH == endH)
			{
				startH = 0;
				endH = 172800; // 2 days in seconds
			}else
				if(startH < startingServiceSecond)
					startH += secondPerDay;


			if(startH > endH)
				endH += secondPerDay;

			list.add( new ParkingTask(name, matIDs, (long)startH, (long)endH, (int)cap) );
		}

		return list;
	}


	/**
	 * {@inheritDoc} that is an Excel file. The format returned is The Transilien one
	 */
	public Parameters readingParameters() throws Exception
	{
		XSSFSheet spreadsheet = workbook.getSheet(parametersSheet);
		checkIfSheetNull(spreadsheet, parametersSheet);


		double time, gap;

		time = spreadsheet.getRow(3).getCell(2).getNumericCellValue();
		gap	 = spreadsheet.getRow(4).getCell(2).getNumericCellValue();

		return new Parameters((long)time, gap);
	}


	@Override
	public ArrayList<OD> lectureOD() throws Exception {
		return null;
	}
}
