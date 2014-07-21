package com.cfranco.inventory.main;

import java.awt.EventQueue;

import com.cfranco.inventory.graphic.MainWindow;
import com.cfranco.inventory.graphic.xls.MainFrameXLS;

public class Main {
	/**
	 * Launch the application.
	 */
	
	private static final boolean IS_EXCEL = true;
	
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(!IS_EXCEL){
						System.out.println("TXT FRAME");
						MainWindow frame = new MainWindow();
						frame.setVisible(true);
					}else{
						System.out.println("XLS FRAME");
						MainFrameXLS frame = new MainFrameXLS();
						frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
