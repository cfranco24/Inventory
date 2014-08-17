package com.cfranco.inventory.graphic.xls;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.cfranco.inventory.settings.InventoryDataXLS;

public class ProductTableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"SAP Ref.","Description", "S/N"};

	private Object[][] tableData;

	private int numberOfRowsToPresent;
	private InventoryDataXLS data;

	public ProductTableModel(InventoryDataXLS data) {
		this.data=data;
		refreshModel();
	}

	public void refreshModel(){
		data.loadSheets();
		
		numberOfRowsToPresent = 0;
		

		for (int i = 0; i < data.getSerialNumberColumn().length; i++) {
			if(!data.isValidated(i))
				numberOfRowsToPresent++;
		}

		System.out.println("Number of rows to present: "+numberOfRowsToPresent);
		if(numberOfRowsToPresent == 1){
			JOptionPane.showMessageDialog(null, "Done! All products have been checked!");
		}

		tableData = new Object[numberOfRowsToPresent][columnNames.length];
		int counter = 0;
		for (int i = 0; i < data.getSerialNumberColumn().length; i++) {
			if(!data.isValidated(i)){
				tableData[counter][0] = data.getSAPRef(i);
				tableData[counter][1] = data.getDescription(i);
				tableData[counter][2] = data.getSerialNumber(i);
				counter++;
			}
		}

		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return tableData.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return tableData[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
