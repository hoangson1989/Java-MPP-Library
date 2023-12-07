package librarysystem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import business.Author;
import business.LibrarySystemException;

public class AddCheckoutBook extends JFrame implements LibWindow {
	public static final AddCheckoutBook INSTANCE = new AddCheckoutBook();
	private static final long serialVersionUID = 2788848225426346219L;
	private boolean isInit;
	private JPanel mainPanel;
	private JTextField tf_BookIsbn;
	private JTextField tf_MemberId;
	private JScrollPane tbScroll;
	private Component tblTable;
	
	@Override
	public void init() {
		if (isInit == false) {
			this.setLocation(250, 250);
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			// 1
			JPanel gridPanel = new JPanel();
			gridPanel.setLayout(new GridLayout(4,1));
			mainPanel.add(gridPanel, BorderLayout.NORTH);
			// 1.1
			{
				JLabel lbl = new JLabel("Checkout Book");
				
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
				rowPanel.add(lbl);
				
				gridPanel.add(rowPanel);
			}
			
			// 1.2 
			{
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lbl = new JLabel();
				lbl.setText("Book Isbn");
				rowPanel.add(lbl);
				JTextField tf = new JTextField(20);
				rowPanel.add(tf);
				gridPanel.add(rowPanel);
				
				this.tf_BookIsbn = tf;
			}
			
			// 1.3
			{
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lbl = new JLabel();
				lbl.setText("Member Id");
				rowPanel.add(lbl);
				JTextField tf = new JTextField(20);
				rowPanel.add(tf);
				gridPanel.add(rowPanel);
				
				this.tf_MemberId = tf;
			}
			
			// 1.4
			{
				JPanel rowBtns = new JPanel();
				rowBtns.setLayout(new FlowLayout(FlowLayout.CENTER));
				JButton btnAdd = new JButton(" Add ");
				btnAdd.addActionListener(evt -> {
					String member = tf_MemberId.getText().trim();
					String bookIsbn = tf_BookIsbn.getText().trim();
					try {
						LibrarySystem.INSTANCE.ci.checkoutABook(member, bookIsbn);
						JOptionPane.showMessageDialog(AddCheckoutBook.this, "Successful Checkout Book");
						//
						String[][] data = LibrarySystem.INSTANCE.ci.getCheckoutRecord2(member);
						updateTable(data);
						
					} catch (LibrarySystemException e) {
						JOptionPane.showMessageDialog(AddCheckoutBook.this, e.getMessage());
					}
				});
				
				JButton btnClear = new JButton(" Clear ");
				btnClear.addActionListener(evt -> {
					clearData();
				});
				rowBtns.add(btnAdd);
				rowBtns.add(btnClear);
				gridPanel.add(rowBtns);
			}
			
			//3. Back To Main
			JPanel lowerHalf = new JPanel();
			JButton backButton = new JButton("<= Back to Main");
			backButton.addActionListener(evt -> {
				backToMain();
			});
			lowerHalf.add(backButton);
			mainPanel.add(lowerHalf, BorderLayout.SOUTH);
			//
			getContentPane().add(mainPanel);
			
			isInit = true;
		}
	}

	@Override
	public boolean isInitialized() {
		return isInit;
	}

	@Override
	public void isInitialized(boolean val) {
		isInit = val;
		
	}

	private void clearData() {
		tf_BookIsbn.setText("");
		tf_MemberId.setText("");
		updateTable(null);
	}
	
	private void backToMain() {
		LibrarySystem.hideAllWindows();
		LibrarySystem.INSTANCE.setVisible(true);
		clearData();
	}
	
	private void updateTable(String[][] data) {
		if (data != null) {
			// add
			if (tbScroll == null) {
				// Column Names
		        String[] columnNames = { "ISBN", "Checkout Date", "Due Date"};
		        // Initializing the JTable
		        tblTable = new JTable(data, columnNames);
		        tblTable.setBounds(30, 40, 200, 300);
		 
		        // adding it to JScrollPane
		        tbScroll = new JScrollPane(tblTable);
		        mainPanel.add(tbScroll);
		        		        
			}
	        
	        
		} else {
			// remove
			if (tbScroll != null) {
				mainPanel.remove(tbScroll);
				tbScroll = null;
				tblTable = null;
			}
		}
		pack();
		AddCheckoutBook.this.getContentPane().validate();
		AddCheckoutBook.this.getContentPane().repaint();
	}

}
