package com.qa.api.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//Rest Assured FW Session 6
public class ExcelUtil {
	
	private static String TEST_DATA_SHEET_PATH="./src/test/resources/testdata/APITestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	
	public static Object[][] readData(String sheetName) {
		Object data[][]=null;
		
		try {
			//The Excel file has to be read using the FileInputStream
			//The FileInputStream will make the connection with the file
			FileInputStream ip=new FileInputStream(TEST_DATA_SHEET_PATH);
			book=WorkbookFactory.create(ip);
			//This loads the entire excel in the Java Memory.From the Workbook we need to goto the corresponding sheet
			sheet=book.getSheet(sheetName);//getSheet returns the Sheet reference
			
			data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];//first[] is for the row and the second[] is for the columns
			for(int i=0;i<sheet.getLastRowNum();i++) {
				for(int j=0;j<sheet.getRow(0).getLastCellNum();j++) {
					data[i][j]=sheet.getRow(i+1).getCell(j).getStringCellValue();
					//the first row  has the header so the data should be read from the 2nd row thats why i+1
				}
			}
			//WorkbookFactory class comes from the Apache POI API
			
		} catch (IOException | InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
