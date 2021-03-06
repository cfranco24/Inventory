package com.cfranco.inventory.settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

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
	private Cell[] serialNumberColumn, refSapColumn, descriptionColumn;

	private WritableWorkbook wrWorkbook;
	private WritableSheet wrSheet;

	private File txtFile;
	private static final String TXT_FILE_NAME = "Serial_numbers.txt";


	public static final int SERIAL_NUMBER_COLUMN = 8;
	public static final int REF_SAP_COLUMN = 5;
	public static final int DESCRIPTION_COLUMN = 6;
	public static final int VALIDATION_COLUMN = 11;

	public InventoryDataXLS(String path) {
		super.setFilePath(path);
		loadSheets();
	}

	public void loadSheets(){
		try {
			workbook = Workbook.getWorkbook(new File(super.getFilePath()));
			sheet = workbook.getSheet(0);

			serialNumberColumn = sheet.getColumn(SERIAL_NUMBER_COLUMN);
			refSapColumn = sheet.getColumn(REF_SAP_COLUMN);
			descriptionColumn = sheet.getColumn(DESCRIPTION_COLUMN);


		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}

		Label label = new Label(VALIDATION_COLUMN, 0, "IS_IN_STOCK"); 
		try {
			wrWorkbook = Workbook.createWorkbook(new File(super.getFilePath()), workbook);
			wrSheet = wrWorkbook.getSheet(0);
			wrSheet.addCell(label);

			txtFile = new File(new File(super.getFilePath()).getParent()+File.separatorChar+TXT_FILE_NAME);
			
			txtFile.createNewFile();
			
//			if(!txtFile.exists()){
//				txtFile.createNewFile();
//			}

			wrWorkbook.write();
			wrWorkbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}


	}

	public boolean isInInventory(String serialNumber, MainFrameXLS frame){
		String status = "";
		loadSheets();

		//Check if number is ICCID of a card
		if(serialNumber.length() >= 6 && serialNumber.substring(0, 6).equalsIgnoreCase("089351"))
			serialNumber = serialNumber.substring(2);


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
					
					writeToTXTFile(serialNumber);

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

	public Cell[] getRefSapColumn() {
		return refSapColumn;
	}

	public Cell[] getDescriptionColumn() {
		return descriptionColumn;
	}

	public Cell[] getSerialNumberColumn() {
		return serialNumberColumn;
	}

	public String getSerialNumber(int row){
		return serialNumberColumn[row].getContents();
	}

	public String getSAPRef(int row){
		return refSapColumn[row].getContents();
	}

	public String getDescription(int row){
		return descriptionColumn[row].getContents();
	}

	public boolean isValidated(int row){
		if(sheet.getCell(VALIDATION_COLUMN, row).getContents().equalsIgnoreCase("OK")){
			return true;
		}
		return false;

	}

	private void writeToTXTFile(String serialNumber){
		BufferedWriter out = null;

		try {
			out =  new BufferedWriter(new FileWriter(txtFile,true));
			out.write(serialNumber);
			out.newLine();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to write to file! Something went wrong.");
		}
	}

}
