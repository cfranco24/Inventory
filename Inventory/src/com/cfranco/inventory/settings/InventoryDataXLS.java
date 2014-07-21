package com.cfranco.inventory.settings;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.cfranco.inventory.graphic.xls.MainFrameXLS;

public class InventoryDataXLS extends InventoryData{
	
	private Workbook workbook;
	private Sheet sheet;
	private Cell[] serialNumberColumn;
	
	private WritableWorkbook wrWorkbook;
	private WritableSheet wrSheet;
	
	
	private static final int SERIAL_NUMBER_COLUMN = 8;
	private static final int REF_SAP_COLUMN = 5;
	private static final int DESCRIPTION_COLUMN = 6;
	private static final int VALIDATION_COLUMN = 11;
	
	public InventoryDataXLS(String path) {
		super.setFilePath(path);
		loadSheets();
		
	}
	
	public void loadSheets(){
		try {
			workbook = Workbook.getWorkbook(new File(super.getFilePath()));
			sheet = workbook.getSheet(0);
			serialNumberColumn = sheet.getColumn(SERIAL_NUMBER_COLUMN);
			
			wrWorkbook = Workbook.createWorkbook(new File("/home/cfranco/Desktop/output.xls"), workbook);
			wrSheet = wrWorkbook.getSheet(0);
			
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isInInventory(String serialNumber, MainFrameXLS frame){
		String status = "";
		loadSheets();
		for (int i = 0; i < serialNumberColumn.length; i++) {
			if(serialNumberColumn[i].getContents().equalsIgnoreCase(serialNumber)){
				status = "Ref. SAP: "+sheet.getCell(REF_SAP_COLUMN, i).getContents()+"\n"+
						 "Description: "+sheet.getCell(DESCRIPTION_COLUMN, i).getContents()+"\n"+
						 "S/N: "+sheet.getCell(SERIAL_NUMBER_COLUMN, i).getContents();
				
				Label label = new Label(VALIDATION_COLUMN, i, "OK"); 
				try {
					wrWorkbook = Workbook.createWorkbook(new File(super.getFilePath()), workbook);
					wrSheet = wrWorkbook.getSheet(0);
					wrSheet.addCell(label);
					
					wrWorkbook.write();
					wrWorkbook.close();
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				frame.updateStatus(status);
				return true;
			}
		}
		status = "Item with S/N "+serialNumber+" is NOT in inventory!";
		
		frame.updateStatus(status);
		return false;
	}

}
