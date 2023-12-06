package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import business.LibrarySystemException;

public class OverdueWindow extends JFrame implements LibWindow {
	public static final OverdueWindow INSTANCE = new OverdueWindow();
	private static final long serialVersionUID = -3283924907900001526L;
	private boolean isInit;
	
	private JButton btnSearch;
	private JTextField tf_BookIsbn;
	private JPanel mainPanel;
	private JTable tblOverdueTable;
	private JScrollPane tbScroll;
	
	@Override
	public void init() {
		if (isInit == false) {
			
			//
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			// 1
			JPanel gridPanel = new JPanel();
			gridPanel.setLayout(new GridLayout(3,1));
			mainPanel.add(gridPanel, BorderLayout.NORTH);
			// 1.1
			{
				JLabel lbl = new JLabel("View Overdue List");
				
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
				rowPanel.add(lbl);
				
				gridPanel.add(rowPanel);
			}
			
			//1.2
			{
				JLabel lbl = new JLabel("Today is " + LocalDate.now().toString());
				
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				rowPanel.add(lbl);
				
				gridPanel.add(rowPanel);
			}
			
			//1.3
			{
				JLabel lbl = new JLabel("Book Isbn");
				JTextField tf = new JTextField(20);
				tf.getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent e) {
						updateBtnSearchVisible();
						
					}
					
					@Override
					public void insertUpdate(DocumentEvent e) {
						updateBtnSearchVisible();
						
					}
					
					@Override
					public void changedUpdate(DocumentEvent e) {
						updateBtnSearchVisible();
						
					}
				});
				JButton btn = new JButton("Search");
				btn.addActionListener(evt -> {
					try {
						String[][] data = LibrarySystem.INSTANCE.ci.getBookOverdueList(tf_BookIsbn.getText().trim());
						updateTable(data);
					} catch (LibrarySystemException e1) {
						JOptionPane.showMessageDialog(OverdueWindow.this, e1.getMessage());
					}
				});
				
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				rowPanel.add(lbl);
				rowPanel.add(tf);
				rowPanel.add(btn);
				
				gridPanel.add(rowPanel);
				
				btnSearch = btn;
				tf_BookIsbn = tf;
			}
			
			//
			{
				JPanel lowerHalf = new JPanel();
				JButton backButton = new JButton("<= Back to Main");
				backButton.addActionListener(evt -> {
					backToMain();
				});
				lowerHalf.add(backButton);
				mainPanel.add(lowerHalf, BorderLayout.SOUTH);
			}
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

	private void updateBtnSearchVisible() {
		String txt = tf_BookIsbn.getText().trim();
		if (txt != null && txt.isBlank() == false) {
			btnSearch.setEnabled(true);
		} else {
			btnSearch.setEnabled(false);			
		}
		
		updateTable(null);
	}
	
	private void updateTable(String[][] data) {
		if (data != null) {
			// add
			if (tbScroll == null) {
				// Column Names
		        String[] columnNames = { "ISBN", "Book Title", "Copy Number", "Library Member", "Due Date"};
		        // Initializing the JTable
		        tblOverdueTable = new JTable(data, columnNames);
		        tblOverdueTable.setBounds(30, 40, 200, 300);
		 
		        // adding it to JScrollPane
		        tbScroll = new JScrollPane(tblOverdueTable);
		        mainPanel.add(tbScroll);
		        		        
			}
	        
	        
		} else {
			// remove
			if (tbScroll != null) {
				mainPanel.remove(tbScroll);
				tbScroll = null;
				tblOverdueTable = null;
			}
		}
		pack();
		OverdueWindow.this.getContentPane().validate();
		OverdueWindow.this.getContentPane().repaint();
	}
	
	private void backToMain() {
		LibrarySystem.hideAllWindows();
		LibrarySystem.INSTANCE.setVisible(true);
		clearData();
	}
	
	private void clearData() {
		tf_BookIsbn.setText("");
		btnSearch.setEnabled(false);
		if (tbScroll != null) {
			mainPanel.remove(tbScroll);
			tbScroll = null;
			tblOverdueTable = null;
		}
		pack();
		OverdueWindow.this.getContentPane().validate();
		OverdueWindow.this.getContentPane().repaint();
	}
}
