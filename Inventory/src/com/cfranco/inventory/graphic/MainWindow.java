package com.cfranco.inventory.graphic;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cfranco.inventory.main.InventoryData;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fileDestTextField;
	private JTextField textField_1;
	private InventoryData data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		data = new InventoryData();
		
		
		setTitle("Inventory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblChooseThe = new JLabel("1. Choose the file destination:");
		lblChooseThe.setBounds(10, 11, 414, 14);
		contentPane.add(lblChooseThe);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fc.setFileFilter(filter);
				
				int result = fc.showOpenDialog(MainWindow.this);
				
				if(result == 0){
					data.setFilePath(fc.getSelectedFile().getAbsolutePath());
					fileDestTextField.setText(data.getFilePath());
				}
				JOptionPane.showMessageDialog(null, result);
				
				
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
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setBounds(10, 93, 232, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setEditable(false);
		textArea.setEnabled(false);
		textArea.setBounds(10, 142, 232, 83);
		contentPane.add(textArea);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStatus.setBounds(10, 124, 46, 14);
		contentPane.add(lblStatus);
	}
}
