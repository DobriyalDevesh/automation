package com.company.automation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.company.automation.driver.TestConfig;

public class ExcelObject {
	private static Logger LOGGER = Logger.getLogger(ExcelObject.class);

	private String excelPath;
	private String excelFileName;
	private Workbook workbook;
	private Sheet activeSheet;

	public ExcelObject(String excelPath, String... sheetNames) throws IOException {
		this.excelPath = excelPath;

		File excelFile = new File(excelPath);
		FileInputStream fis = new FileInputStream(excelFile);

		if (excelFile.getName().toLowerCase().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(fis);
		} else {
			workbook = new HSSFWorkbook(fis);
		}

		fis.close();

		if (sheetNames != null && sheetNames.length >= 1) {
			activeSheet = workbook.getSheet(sheetNames[0]);
		} else {
			activeSheet = workbook.getSheetAt(0);
		}
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setExcelName(String name) {
		this.excelFileName = name;
	}

	public String getExcelName() {
		return excelFileName;
	}

	public Sheet getActiveSheet() {
		return activeSheet;
	}

	private boolean isSheetExist(String sheetName) {
		return workbook.getSheetIndex(sheetName) != -1;
	}

	public void closeWorkbook() throws IOException {
		this.workbook.close();
	}

	public void createSheets(String... sheetNames) throws IOException {
		File excelFile = new File(this.excelPath);
		FileOutputStream fos = null;

		if (sheetNames != null && sheetNames.length >= 1) {
			for (String sheet : sheetNames) {
				if (!isSheetExist(sheet)) {
					workbook.createSheet(sheet);
				}
			}
		}

		fos = new FileOutputStream(excelFile);
		workbook.write(fos);
		fos.close();
	}

	private int getColumnIndex(Sheet sheet, String columnName) {
		Row row = sheet.getRow(0);
		for (Cell cell : row) {
			if (columnName.equals(getCellValue(cell))) {
				return cell.getColumnIndex();
			}
		}
		return -1;
	}

	public int getColumnIndex(String sheetName, String columnName) {
		return getColumnIndex(workbook.getSheet(sheetName), columnName);
	}

	private int getRowIndex(Sheet sheet, String rowData) {
		int i = 0;
		ArrayList<Object> columnData = getEntireColumnData(sheet, 0);
		int size = columnData.size();
		for (i = 0; i < size; i++) {
			String temp = columnData.get(i).toString();
			if (temp.equals(rowData)) {
				break;
			}

		}
		return i;
	}

	/**
	 * This function return excel name with timestamp
	 * 
	 * @param excelStartingKeyWord
	 * @author Vikas : Mintu
	 * @version 1.0
	 * @since April 24 2020
	 */
	public static String excelName(String excelStartingKeyWord) {
		String name = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		name = excelStartingKeyWord + (sdf.format(timestamp).toString()) + ".xlsx";
		return name;
	}
	
	public static String dupSearchExcelName(String excelStartingKeyWord) {
		String name = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");//new SimpleDateFormat("yyyy.MM.dd");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		name = excelStartingKeyWord + (sdf.format(timestamp).toString()) + ".xlsx";
		return name;
	}

	/**
	 * This function create new excel work book with list of sheets
	 * 
	 * @param excelName
	 *            , caseNames
	 * @author Vikas : Mintu
	 * @version 1.0
	 * @since April 24 2020
	 */
	public static boolean createNewExcelWorkBook(String excelName, List<String> caseNames) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("ADM Group");
		columnNames.add("ADM");
		// columnNames.add("Argus Screen");
		columnNames.add("Argus Navigation Step");
		columnNames.add("Argus Field Name");
		columnNames.add("Argus Field Value");
		// columnNames.add("Arisg Screen");
		columnNames.add("Arisg Navigation Step");
		columnNames.add("Mapping Type");
		/*
		 * columnNames.add("Rule Sheet"); columnNames.add("Logic");
		 */
		columnNames.add("Arisg Field Name Expected");
		columnNames.add("Field_Name_Status");
		columnNames.add("ArisG Expected Value");
		columnNames.add("ArisG Actual Value");
		columnNames.add("Result");
		columnNames.add("ARGUS Screenshot Path");
		columnNames.add("ARIS Screenshot Path");
		// Create two sheets in the excel document and name it First Sheet and
		// Second Sheet.

		for (int i = 0; i < caseNames.size(); i++) {
			XSSFSheet sheetName = workbook.createSheet(caseNames.get(i));
			CellStyle style = workbook.createCellStyle();
			// Setting Background color
			style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
			// style.setFont(BOLD);

			// Manipulate the firs sheet by creating an HSSFRow which represent a
			// single row in excel sheet, the first row started from 0 index. After
			// the row is created we create a HSSFCell in this first cell of the row
			// and set the cell value with an instance of HSSFRichTextString
			// containing the words FIRST SHEET.
			XSSFRow rowA = sheetName.createRow(0);
			for (int j = 0; j < columnNames.size(); j++) {
				XSSFCell cellA = rowA.createCell(j);
				cellA.setCellValue(new XSSFRichTextString(columnNames.get(j)));
				cellA.setCellStyle(style);
			}
		}
		// To write out the workbook into a file we need to create an output
		// stream where the workbook content will be written to.
		try (FileOutputStream fos = new FileOutputStream(
				new File("Framework\\\\Test_Output_Excel").getCanonicalPath() + "\\\\" + excelName)) {
			workbook.write(fos);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean createNewDuplicatePocessingExcel(String excelName ,String FolderLoc) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("Receipt Number");
		columnNames.add("Aer No");
		columnNames.add("Lrn No");
		columnNames.add("Classification");
		columnNames.add("Status");
		columnNames.add("Exception Details");
		columnNames.add("Screenshot Path");
		columnNames.add("Date");
		columnNames.add("Start Date");
		columnNames.add("End Date");

		XSSFSheet sheetName = workbook.createSheet("Summary");
		CellStyle style = workbook.createCellStyle();
		// Setting Background color
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		// style.setFont(BOLD);

		// Manipulate the first sheet by creating an HSSFRow which represent a
		// single row in excel sheet, the first row started from 0 index. After
		// the row is created we create a HSSFCell in this first cell of the row
		// and set the cell value with an instance of HSSFRichTextString
		// containing the words FIRST SHEET.
		XSSFRow rowA = sheetName.createRow(0);
		for (int j = 0; j < columnNames.size(); j++) {
			XSSFCell cellA = rowA.createCell(j);
			cellA.setCellValue(new XSSFRichTextString(columnNames.get(j)));
			cellA.setCellStyle(style);
		}

		// To write out the workbook into a file we need to create an output
		// stream where the workbook content will be written to.
		try (FileOutputStream fos = new FileOutputStream(
				new File("Framework\\\\Test_Output_Excel\\\\"+FolderLoc).getCanonicalPath() + "\\\\" + excelName)) {
			workbook.write(fos);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Integer> getAllRowIndex(String sheetName, String rowData) {
		return getAllRowIndex(workbook.getSheet(sheetName), rowData);
	}

	private ArrayList<Integer> getAllRowIndex(Sheet sheet, String rowData) {
		int i = 0;
		ArrayList<Integer> rowIndex = new ArrayList<Integer>();
		ArrayList<Object> columnData = getEntireColumnData(sheet, 0);
		int size = columnData.size();
		for (i = 0; i < size; i++) {
			String temp = columnData.get(i).toString();
			if (temp.equals(rowData)) {
				rowIndex.add(i);
			}
		}
		return rowIndex;
	}

	public ArrayList<Integer> getAllRowIndexForGivenColumn(String sheetName, String rowData, int columnIndex) {
		return getAllRowIndexForGivenColumn(workbook.getSheet(sheetName), rowData, columnIndex);
	}

	private ArrayList<Integer> getAllRowIndexForGivenColumn(Sheet sheet, String rowData, int columnIndex) {
		int i = 0;
		ArrayList<Integer> rowIndex = new ArrayList<Integer>();
		ArrayList<Object> columnData = getEntireColumnData(sheet, columnIndex);
		int size = columnData.size();
		for (i = 0; i < size; i++) {
			String temp = columnData.get(i).toString();
			if (temp.equals(rowData)) {
				rowIndex.add(i);
			}
		}
		return rowIndex;
	}

	public int getRowIndex(String sheetName, String rowData) {
		return getRowIndex(workbook.getSheet(sheetName), rowData);
	}

	public Object getCellValue(Cell cell) {
		Object value = null;
		CellValue cellValue = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator().evaluate(cell);

		if (cellValue != null) {
			switch (cellValue.getCellType()) {
			case STRING:
				value = cell.getStringCellValue();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					value = cell.getDateCellValue();
				} else {
					value = cell.getNumericCellValue();
				}
				break;
			case BLANK:
			case _NONE:
				value = " ";
				break;
			case BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case ERROR:
			default:
				break;
			}
		} else {
			value = " ";
		}
		return value;
	}

	public Object getCellValue(int rowPosition, int colPosition) {
		Object value = null;
		Cell cell;

		try {
			cell = activeSheet.getRow(rowPosition).getCell(colPosition, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		} catch (NullPointerException e) {
			LOGGER.error(e.getClass().getSimpleName(), e);
			return null;
		}

		value = getCellValue(cell);
		return value;
	}

	public Object getCellValue(Sheet sheet, int rowPosition, int colPosition) {
		Object value = null;
		Cell cell;

		try {
			cell = sheet.getRow(rowPosition).getCell(colPosition, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		} catch (NullPointerException e) {
			LOGGER.error(e.getClass().getSimpleName(), e);
			return null;
		}

		value = getCellValue(cell);
		return value;
	}

	public Object getCellValue(String sheetName, int rowPosition, int colPosition) {
		return getCellValue(workbook.getSheet(sheetName), rowPosition, colPosition);
	}

	public Object getCellValue(String sheetName, int rowPosition, String columnName) {
		return getCellValue(rowPosition, getColumnIndex(workbook.getSheet(sheetName), columnName));
	}

	public Object getCellValue(String sheetName, String columnName, String filterCondition,
			boolean... strictCompareFlag) {
		boolean strictCompare = (strictCompareFlag != null && strictCompareFlag.length >= 1) ? strictCompareFlag[0]
				: false;

		Object value = getCellValue(workbook.getSheet(sheetName),
				getRowIndex(sheetName, filterCondition, strictCompare),
				getColumnIndex(workbook.getSheet(sheetName), columnName));
		return value;
	}

	public int getRowIndex(String sheetName, String filterCondition, boolean strictCompare) {
		String[] conditions = filterCondition.split(Constants.CONDITION_SEPARATOR);
		LinkedHashMap<String, String> fullConditions = new LinkedHashMap<String, String>();

		for (String condition : conditions) {
			fullConditions.put(condition.split(Constants.CONDITIONVALUE_SEPARATOR)[0],
					condition.split(Constants.CONDITIONVALUE_SEPARATOR)[1]);
		}

		int[] columnIndices = new int[fullConditions.size()];
		Set<String> columnNames = fullConditions.keySet();
		Sheet sheet = workbook.getSheet(sheetName);

		for (String columnName : columnNames) {
			columnIndices = ArrayUtils.add(columnIndices, getColumnIndex(sheet, columnName));
			columnIndices = ArrayUtils.remove(columnIndices, 0);
		}

		for (Row row : sheet) {
			LinkedHashMap<String, String> newHashMap = new LinkedHashMap<>();

			for (int index : columnIndices) {
				newHashMap.put(String.valueOf(getCellValue(sheet.getRow(0).getCell(index))),
						String.valueOf(getCellValue(row.getCell(index))));
			}

			if (strictCompare) {
				if (newHashMap.equals(fullConditions))
					return row.getRowNum();
			} else {
				if (compareHashMapLoosely(newHashMap, fullConditions))
					return row.getRowNum();
			}
		}
		return -1;
	}

	private boolean compareHashMapLoosely(LinkedHashMap<String, String> one, LinkedHashMap<String, String> two) {
		Set<String> keySetOne = one.keySet();
		Set<String> keySetTwo = two.keySet();

		if (!CollectionUtils.isEqualCollection(keySetOne, keySetTwo))
			return false;
		for (String col1 : keySetOne) {
			for (String col2 : keySetTwo) {
				if (col1.equalsIgnoreCase(col2)) {
					// Numeric Logic
					if (StringUtils.isNumeric(one.get(col1)) && StringUtils.isNumeric(two.get(col2))) {
						if (Double.valueOf(one.get(col1)).doubleValue() != Double.valueOf(two.get(col2)).doubleValue())
							return false;
					} // Ignore case and trim
					else {
						if (!one.get(col1).trim().equalsIgnoreCase(two.get(col2).trim())) {
							return false;
						}
					}
					// TODO - Logic to be added for dateformat comparisons
				}
			}
		}
		return true;
	}

	public void setCellValue(Sheet sheet, int rowNum, int columnNum, Object valueToSet) throws IOException {
		Cell cell = null;

		try {
			if (sheet.getRow(rowNum) == null)
				cell = sheet.createRow(rowNum).createCell(columnNum);
			else if (sheet.getRow(rowNum).getCell(columnNum) == null) {
				cell = sheet.getRow(rowNum).createCell(columnNum);
			} else {
				cell = sheet.getRow(rowNum).getCell(columnNum);
			}
		} catch (NullPointerException e) {
			LOGGER.error(e.getClass().getSimpleName(), e);
			return;
		}

		if (valueToSet != null) {
			switch (valueToSet.getClass().getSimpleName().toUpperCase()) {
			case "INTEGER":
			case "DOUBLE":
			case "FLOAT":
			case "SHORT":
			case "BYTE":
			case "LONG":
				cell.setCellType(CellType.NUMERIC);
				cell.setCellValue(Double.parseDouble(String.valueOf(valueToSet)));
				break;
			case "DATE":
				CellStyle cellStyle = workbook.createCellStyle();
				CreationHelper createHelper = workbook.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss.ms"));
				cell.setCellValue((Date) valueToSet);
				cell.setCellStyle(cellStyle);
				break;
			case "BOOLEAN":
				cell.setCellValue(Boolean.parseBoolean(String.valueOf(valueToSet)));
				cell.setCellType(CellType.BOOLEAN);
				break;
			case "STRING":
			case "OBJECT":
			default:
				cell.setCellValue(String.valueOf(valueToSet));

				if (valueToSet.toString().toLowerCase().startsWith("=")) {
					cell.setCellType(CellType.FORMULA);
				} else {
					cell.setCellType(CellType.STRING);
				}
				break;
			}
		}

		FileOutputStream fos = new FileOutputStream(excelPath);
		workbook.write(fos);
		fos.close();
	}

	public void setCellValue(String sheetName, int rowNum, int columnNum, Object valueToSet) throws IOException {
		setCellValue(workbook.getSheet(sheetName), rowNum, columnNum, valueToSet);
	}

	public List<List<Object>> getExcelData(Sheet sheet) {
		List<List<Object>> data = new ArrayList<List<Object>>();
		List<Object> values;

		for (Row row : sheet) {
			values = new ArrayList<Object>();
			for (Cell cell : row) {
				values.add(getCellValue(cell));
			}
			data.add(values);
		}
		return data;
	}

	public List<List<Object>> getExcelData(String sheetName) {
		return getExcelData(workbook.getSheet(sheetName));
	}

	public List<List<Object>> getExcelData(String sheetName, String filterConditions, boolean... strictCompareFlag) {
		List<List<Object>> data = new ArrayList<List<Object>>();
		boolean strictCompare = (strictCompareFlag != null && strictCompareFlag.length >= 1) ? strictCompareFlag[0]
				: false;

		Sheet sheet = workbook.getSheet(sheetName);
		int[] targetRowIndices = getRowIndices(sheet, filterConditions, strictCompare);

		if (targetRowIndices.length == 1 && targetRowIndices[0] == -1) {
			return data;
		} else {
			List<List<Object>> newData = getExcelData(sheet);

			for (int i : targetRowIndices) {
				data.add(newData.get(i));
			}
		}
		return data;
	}

	public List<List<Object>> getExcelData(String sheetName, String filterConditions, String columnNames,
			boolean... strictCompareFlag) {
		List<List<Object>> data = new ArrayList<>();
		boolean strictCompare = (strictCompareFlag != null && strictCompareFlag.length >= 1) ? strictCompareFlag[0]
				: false;
		Sheet sheet = workbook.getSheet(sheetName);
		int[] targetRowIndices = getRowIndices(sheet, filterConditions, strictCompare);
		if (targetRowIndices.length == 1 && targetRowIndices[0] == -1) {
			return data;
		} else {
			List<List<Object>> newData = getExcelColumnsData(sheetName, columnNames);
			for (int i : targetRowIndices) {
				data.add(newData.get(i - 1));
			}
		}
		return data;
	}

	public List<List<List<Object>>> getExcelData() {
		List<List<List<Object>>> data = new ArrayList<List<List<Object>>>();

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			data.add(getExcelData(workbook.getSheetAt(i)));
		}
		return data;
	}

	public List<List<Object>> getExcelColumnsData(String sheetName, String columnNames) {
		List<List<Object>> data = new ArrayList<>();
		Sheet sheet = workbook.getSheet(sheetName);

		String[] arrColumns = columnNames.split(Constants.CONDITION_SEPARATOR);
		List<Object> values;

		for (Row row : sheet) {
			values = new ArrayList<>();

			if (row.getRowNum() != 0) {
				for (String string : arrColumns) {
					int colIndex = getColumnIndex(sheet, string);
					Cell cell = row.getCell(colIndex);
					values.add(getCellValue(cell));
				}
				data.add(values);
			}
		}
		return data;
	}

	private int[] getRowIndices(Sheet sheet, String filterCondition, boolean strictCompare) {
		ArrayList<Integer> list = new ArrayList<>();
		String[] conditions = filterCondition.split(Constants.CONDITION_SEPARATOR);
		LinkedHashMap<String, String> fullConditions = new LinkedHashMap<String, String>();

		for (String condition : conditions) {
			if (!condition.isEmpty()) // Added this condition to avoid exception
			// when blank/no condition is given
			{
				fullConditions.put(condition.split(Constants.CONDITIONVALUE_SEPARATOR)[0],
						condition.split(Constants.CONDITIONVALUE_SEPARATOR)[1]);
			}
		}

		int[] columnIndices = new int[fullConditions.size()];
		Set<String> columnNames = fullConditions.keySet();

		for (String columnName : columnNames) {
			columnIndices = ArrayUtils.add(columnIndices, getColumnIndex(sheet, columnName));
			columnIndices = ArrayUtils.remove(columnIndices, 0);
		}

		for (Row row : sheet) {
			LinkedHashMap<String, String> newHashMap = new LinkedHashMap<>();

			for (int index : columnIndices) {
				newHashMap.put(String.valueOf(getCellValue(sheet.getRow(0).getCell(index))),
						String.valueOf(getCellValue(row.getCell(index))));
			}

			if (strictCompare) {
				if (newHashMap.equals(fullConditions))
					list.add(row.getRowNum());
			} else {
				if (compareHashMapLoosely(newHashMap, fullConditions))
					list.add(row.getRowNum());
			}
		}

		if (list.size() == 0)
			list.add(-1);

		return ArrayUtils.toPrimitive(list.toArray(new Integer[list.size()]));
	}

	public ArrayList<Object> getEntireColumnData(Sheet sheet, int columnIndex) {
		ArrayList<Object> data = new ArrayList<>();
		for (Row row : sheet) {
			data.add(getCellValue(row.getCell(columnIndex)));
		}
		return data;
	}

	public ArrayList<Object> getEntireColumnData(String sheetName, int columnIndex) {
		return getEntireColumnData(workbook.getSheet(sheetName), columnIndex);
	}

	public ArrayList<Object> getEntireColumnData(String sheetName, String columnName) {
		ArrayList<Object> data = new ArrayList<>();
		data.addAll(getEntireColumnData(sheetName, getColumnIndex(workbook.getSheet(sheetName), columnName)));
		data.remove(0);
		return data;
	}

	private Connection getConnection(String driver, String connString) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection(connString);
	}

	public HashMap<Integer, List<Object>> queryExcel(String query, boolean... includeColumnName)
			throws ClassNotFoundException, SQLException {
		String connString = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + excelPath;
		boolean blnColInclude = (includeColumnName != null && includeColumnName.length >= 1) ? includeColumnName[0]
				: false;

		Connection conn = getConnection("sun.jdbc.odbc.JdbcOdbcDriver", connString);
		Statement statement = null;
		ResultSet result = null;

		HashMap<Integer, List<Object>> dataSet = new HashMap<>();
		List<Object> data;
		int rowCounter = 1;

		try {
			statement = conn.createStatement();
			result = statement.executeQuery(query);

			while (result.next()) {
				int columnsCount = result.getMetaData().getColumnCount();
				data = new ArrayList<Object>();

				for (int i = 1; i <= columnsCount; i++) {
					Object cellValue = null;

					if (StringUtils.isNoneBlank(String.valueOf(result.getObject(i)))) {
						cellValue = "";
					} else {
						cellValue = result.getObject(i);
					}

					if (blnColInclude) {
						data.add(result.getMetaData().getColumnName(i) + Constants.COLUMN_SEPARATOR + cellValue);
					} else {
						data.add(cellValue);
					}
				}
				dataSet.put(rowCounter++, data);
			}
		} finally {
			result.close();
			statement.close();
			conn.close();
		}
		return dataSet;
	}

	public static String createExcelQuery(String sheetName, String queryColumns, String filter) {
		return "Select " + queryColumns + " from [" + sheetName + "$] where " + filter;
	}

	public static String createExcelQuery(String sheetName, String queryColumns) {
		return "Select " + queryColumns + " from [" + sheetName + "$]";
	}

	public void setCellValue(String sheetName, int rowNum, int columnNum, String address, HyperlinkType type)
			throws IOException {
		Cell cell = null;
		Sheet sheet = workbook.getSheet(sheetName);
		try {
			if (sheet.getRow(rowNum) == null)
				cell = sheet.createRow(rowNum).createCell(columnNum);
			else if (sheet.getRow(rowNum).getCell(columnNum) == null) {
				cell = sheet.getRow(rowNum).createCell(columnNum);
			} else {
				cell = sheet.getRow(rowNum).getCell(columnNum);
			}
		} catch (NullPointerException e) {
			LOGGER.error(e.getClass().getSimpleName(), e);
			return;
		}
		// System.out.println(address);
		CreationHelper helper = workbook.getCreationHelper();
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setColor(IndexedColors.BLUE.getIndex());
		style.setFont(font);
		Hyperlink link = helper.createHyperlink(type);
		address = "./" + address;
		link.setAddress(address);
		link.setLabel("Click here to view screenshot");
		cell.setHyperlink(link);
		cell.setCellStyle(style);
		cell.setCellValue("Click here to view screenshot");
		FileOutputStream fos = new FileOutputStream(excelPath);
		workbook.write(fos);
		fos.close();
	}
}