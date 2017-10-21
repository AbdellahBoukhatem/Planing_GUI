package dataProcessing;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * The abstract class {@code AbstractExcelReader} is a library for the classes that will parse an Excel file
 * @see ReaderExcelTransilien
 * @see ReaderExcelFRET
 */
public abstract class AbstractExcelReader implements Reader
{

	/**
	 * Workbook object from the <i>POI Apache</i> api to read excel file with java.
	 * @see XSSFWorkbook
	 */
	protected XSSFWorkbook workbook;
	
	
	protected FileInputStream file;
	
	
	/**
	 * Return the corresponding letters of the argument "col".
	 * @param col the number of the column.
	 * @return a translation of "col" in letters <i>String</i> (like Excel). 
	 */
	protected String getNumCellule(int col)
	{
		char lettre[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		String column = "";
		int reste;
		
		while(col > 0)
		{
			reste = (col-1) % 26;
			column = lettre[reste] + column;
			col = (col-1) / 26;
		}
		return column;
	}
	
	
	
	/**
	 * Return true if the corresponding cell to the row "row" and column "col" of the sheet sheet is numeric and not empty, false else where
	 * 
	 * @param sheet sheet of the cell to be check.
	 * @param row the row corresponding to the cell.
	 * @param col the column corresponding to the cell.
	 * @return true or false ;)
	 * @throws Exception if the corresponding cell is null, empty or non numeric.
	 */
	protected boolean checkIfNotNumeric(XSSFSheet sheet, int row, int col) throws Exception
	{
		XSSFRow r = sheet.getRow(row);
		if(r.getCell(col).getCellTypeEnum() != CellType.NUMERIC)
			throw new Exception("Probleme : La Cellul \'" + getNumCellule(col+1) + (row+1) + "\' de l\'onglet " + sheet.getSheetName() + " n\'est pas de format nombre");
		
		return false;
	}
	
	
	/**
	 * Check if the value of cell correspond to an hour
	 * @param sheet sheet of the cell to be check.
	 * @param row the line corresponding to the cell.
	 * @param col the column corresponding to the cell.
	 * @return true or false ;)
	 * @throws Exception if the corresponding cell is null, empty or non numeric.
	 */
	protected boolean checkIfNotDate(XSSFSheet sheet, int row, int col) throws Exception
	{
		XSSFRow r = sheet.getRow(row);
		if(r.getCell(col).getCellTypeEnum() != CellType.NUMERIC)
			throw new Exception("Probleme : La Cellul \'" + getNumCellule(col+1) + (row+1) + "\' de l\'onglet " + sheet.getSheetName() + " n\'est pas au format date.");
		
		return false;
	}

	
	/**
	 * Throw an exception if the cell is null or empty (blank), false" otherwise
	 * @param sheet sheet of the cell to be checked
	 * @param row row of the cell to be checked
	 * @param col column of the cell to be checked
	 * @return false if the cell have a value, an exception otherwise
	 * @throws Exception if the cell is null
	 */
	protected boolean checkIfCellNull(XSSFSheet sheet, int row, int col) throws Exception
	{
		XSSFRow r = sheet.getRow(row);
		if(r.getCell(col) == null || r.getCell(col).getCellTypeEnum() == CellType.BLANK)
			throw new Exception("Probleme : La Cellul \'" + getNumCellule(col+1) + (row+1) + "\' de l\'onglet " + sheet.getSheetName() + " est vide");
		
		return false;
	}
	
	/**
	 * Throw an exception if the row is null, false otherwise
	 * @param sheet sheet of the row to be checked
	 * @param row the row to be checked
	 * @return false if the row isn't null
	 * @throws Exception if the row  is null
	 */
	protected boolean checkIfRowNull(XSSFSheet sheet, int row) throws Exception
	{
		XSSFRow r = sheet.getRow(row);
		if(r == null)
			throw new Exception("Alerte : La ligne " + (row + 1) + " de l\'onglet " + sheet.getSheetName() + " n\'est pas prise en compte car elle est vide");
		
		return false;
	}
	
	/**
	 * Throw an exception if the sheet "name" is null (doesn't exist the workbook)
	 * @param sheet object to be checked
	 * @param name name of the sheet
	 * @return return false if "sheet" isn't null, an exception otherwise
	 * @throws Exception if the sheet in argument is null
	 */
	protected boolean checkIfSheetNull(XSSFSheet sheet, String name) throws Exception
	{
		if(sheet == null)
			throw new Exception("Probleme : L\'onglet \"" + name + "\" est absent");
		
		return false;
	}
	
	/**
	 * Return the String value of a numeric cell. <p>
	 * 
	 * This method is useful in the following example : <p>
	 * 
	 * The cell value is "123", the .toString method give us the following value "123.0" cause it's considered as a double. In the case were the numeric
	 * values represent an id of material for instance, and that we have a material with the id 0, it generate an error. 
	 * 
	 * @param cell the cell where the value is
	 * @return the String value of a numeric cell
	 * @throws Exception if a problem occur
	 */
	protected String floatToString(XSSFCell cell) throws Exception
	{
		if(cell.getCellTypeEnum() == CellType.NUMERIC)
		{
			double v = cell.getNumericCellValue();
			if(v == (int)v)
				return Integer.toString((int)v);
		}
		
		
		return cell.toString();
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws Exception
	{
		workbook.close();
		file.close();
		
		workbook = null;
		file = null;
	}
}
