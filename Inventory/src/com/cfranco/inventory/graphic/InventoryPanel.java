package com.cfranco.inventory.graphic;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InventoryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private static final ImageIcon img = new ImageIcon("images/bg.png");;
	
	public InventoryPanel() {
		setOpaque(true);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img.getImage(),0,0,null);
	}

}
