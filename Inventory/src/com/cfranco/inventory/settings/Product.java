package com.cfranco.inventory.settings;

public class Product {
	private int serialNumberLenght;
	private String productName;
	
	public Product(int serialNumberLength, String productName) {
		this.serialNumberLenght = serialNumberLength;
		this.productName = productName;
	}
	
	/**
	 * Returns the length of the serial Number. Return -1 if no serial has been given 
	 * 
	 * */
	
	public int getLength(){
		if(serialNumberLenght != 0){
			return serialNumberLenght;
		}
		
		return -1;
	}
	
	/**
	 * Returns the name of the product.
	 * 
	 * */
	
	public String getName(){
		return productName;
	}

}
