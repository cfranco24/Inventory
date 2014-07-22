package com.cfranco.inventory.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cfranco.inventory.settings.InventoryData;
import com.cfranco.inventory.settings.SoundUtils;

public class MainWindow extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fileDestTextField;
	private JTextField inputTextField;
	private InventoryData data;
	private JTextArea status;


	/**
	 * Create the frame.
	 */
	public MainWindow() {
		data = new InventoryData();
		
		
		setTitle("Inventory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 953, 311);
		contentPane = new InventoryPanel();
		setContentPane(contentPane);
		
		JLabel lblChooseThe = new JLabel("1. Choose the file destination:");
		lblChooseThe.setBounds(10, 11, 414, 14);
		contentPane.add(lblChooseThe);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browseButtonAction();
			}
		});
		btnBrowse.setBounds(335, 36, 89, 23);
		contentPane.add(btnBrowse);
		
		fileDestTextField = new JTextField();
		fileDestTextField.setEnabled(false);
		fileDestTextField.setEditable(false);
		fileDestTextField.setBounds(10, 37, 315, 20);
		contentPane.add(fileDestTextField);
		fileDestTextField.setColumns(10);
		
		JLabel lblInsertThe = new JLabel("2. Insert the serial number:");
		lblInsertThe.setBounds(10, 68, 414, 14);
		contentPane.add(lblInsertThe);
		
		inputTextField = new JTextField();
		inputTextField.setEditable(false);
		inputTextField.setEnabled(false);
		inputTextField.setBounds(10, 93, 255, 20);
		contentPane.add(inputTextField);
		inputTextField.setColumns(10);
		inputTextField.addKeyListener(this);
		
		status = new JTextArea();
		status.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
//		status.setBackground(Color.LIGHT_GRAY);
		status.setBackground(new Color(252,252,252));
		status.setEditable(false);
		status.setBounds(10, 142, 315, 83);
		contentPane.add(status);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStatus.setBounds(10, 124, 46, 14);
		contentPane.add(lblStatus);
	}
	
	public void updateStatus(String status){
		this.status.setText(status);
	}
	
	public void setData(InventoryData data) {
		this.data = data;
	}
	
	public InventoryData getData() {
		return data;
	}
	
	public void browseButtonAction(){
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
		
		int result = fc.showOpenDialog(MainWindow.this);
		
		
		if(result == 0){
			data.setFilePath(fc.getSelectedFile().getAbsolutePath());
			fileDestTextField.setText(data.getFilePath());
			inputTextField.setEnabled(true);
			inputTextField.setEditable(true);
			inputTextField.requestFocus();
			
			//print initial info to file
			
		}
	}
	
	public void enterButtonAction(){
		if(!data.addProduct(inputTextField.getText(), this)){
			//BEEEEP
			try {
				SoundUtils.tone(650,700);
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
		inputTextField.setText("");
		inputTextField.requestFocus();
		
	}
	
	public void setFileDestTextPath(String path){
		fileDestTextField.setText(path);
	}
	
	public JTextField getInputTextField() {
		return inputTextField;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			enterButtonAction();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
