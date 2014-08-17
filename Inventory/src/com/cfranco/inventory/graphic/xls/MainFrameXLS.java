package com.cfranco.inventory.graphic.xls;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cfranco.inventory.graphic.MainWindow;
import com.cfranco.inventory.settings.InventoryDataXLS;
import com.cfranco.inventory.settings.SoundUtils;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrameXLS extends MainWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollPane;
	private ProductTableModel model;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public MainFrameXLS() {
		super();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(454, 0, 473, 256);
		getContentPane().add(scrollPane);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String help = "1. Select the file wich has the inventory. \n"
						+ "(ATTENTION: The file must have the extension .xls!! .xlsx will not be accepted!)\n\n"
						+ "2. Insert the serial number in the box manually or with bar code scanner. The item will\n"
						+ "be marked in the file with an OK at the column 'L' and the serial number will be put in a .txt file \n"
						+ "named as \"Serial_Numbers.txt\", in the same directory as the inventory file.\n\n"
						+ "3. It's free. Don't complain! :)";
				
				JOptionPane.showMessageDialog(null, help);
			}
		});
		menuBar.add(mntmHelp);
	}
	
	@Override
	public void browseButtonAction() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS FILES", "xls");
		fc.setFileFilter(filter);
		
		int result = fc.showOpenDialog(MainFrameXLS.this);
		
		if(result == 0){
			super.setData(new InventoryDataXLS(fc.getSelectedFile().getAbsolutePath()));
			super.setFileDestTextPath(super.getData().getFilePath());
			super.getInputTextField().setEnabled(true);
			super.getInputTextField().setEditable(true);
			super.getInputTextField().requestFocus();
			
			table = new JTable();
			model = new ProductTableModel((InventoryDataXLS)super.getData());
			table.setModel(model);
			scrollPane.setViewportView(table);
		}
	}
	
	@Override
	public void enterButtonAction() {
		if(!((InventoryDataXLS)super.getData()).isInInventory(super.getInputTextField().getText(), this)){
			//BEEEEP
			try {
				SoundUtils.tone(650,700);
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
		
		model.refreshModel();
		
		super.getInputTextField().setText("");
		super.getInputTextField().requestFocus();
	}
}
