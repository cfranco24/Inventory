package com.cfranco.inventory.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.cfranco.inventory.graphic.MainWindow;

public class InventoryData {
	private String filePath;
	private ArrayList<Product> productsList;
	
	public InventoryData() {
		productsList = new ArrayList<Product>();
		
		try {
			InputStream is = getClass().getResourceAsStream("products.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			
			while((line = reader.readLine()) != null){
				StringTokenizer tokenizer = new StringTokenizer(line, "|");
				productsList.add(new Product(Integer.parseInt(tokenizer.nextToken()), tokenizer.nextToken()));
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public boolean addProduct(String serialNumber, MainWindow window){
		for (int i = 0; i < productsList.size(); i++) {
			if(productsList.get(i).getLength() == serialNumber.length()){
				//write to file
				writeToInvetory(serialNumber, window.getData());
				
				//update status
				window.updateStatus("Status: Serial number saved!\n"+
									"Equipment S/N: "+serialNumber+"\n"+
									"Equipment type: "+productsList.get(i).getName());
				
				return true;
			}
				
		}
		
		window.updateStatus("Sorry! Wrong S/N! Try Again!");

		return false;
	}
	
	private void writeToInvetory(String serialNumber, InventoryData data){
		
		BufferedWriter out = null;
		
		try {
			out =  new BufferedWriter(new FileWriter(data.getFilePath(), true));
			out.write(serialNumber);
			out.newLine();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to write to file! Something went wrong.");
		}
		
		
	}

}
