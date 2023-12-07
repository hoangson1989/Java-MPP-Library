package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.LibrarySystemException;

public class AddNewMemberWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1551934828469L;
	private boolean isInitialized = false;
	public static final AddNewMemberWindow INSTANCE = new AddNewMemberWindow();

	JTextField tf_MemberId;
	JTextField tf_FirstName;
	JTextField tf_LastName;
	JTextField tf_Street;
	JTextField tf_City;
	JTextField tf_State;
	JTextField tf_Zip;
	JTextField tf_Phone;
	
	@Override
	public void init() {
		if (isInitialized) {
			return;
		}
		this.setLocation(250, 250);
		JPanel mainPanel = new JPanel();
		BorderLayout bl = new BorderLayout();
		mainPanel.setLayout(bl);
		//1. Title
		JLabel lblTitle = new JLabel();
		lblTitle.setText("Add New Member");
		mainPanel.add(lblTitle, BorderLayout.NORTH);
		
		//2. Form Input
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(9,1));
		mainPanel.add(formPanel);
		
		//2.1 memberId
		{
			JPanel rowMemberId = new JPanel();
			rowMemberId.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel lblMemberId = new JLabel();
			lblMemberId.setText("Member Id");
			rowMemberId.add(lblMemberId);
			JTextField tfMemberId = new JTextField(20);
			rowMemberId.add(tfMemberId);
			formPanel.add(rowMemberId);
			
			this.tf_MemberId = tfMemberId;
		}
		
		//2.2 First Name
		{
			JPanel rowFirstName = new JPanel();
			rowFirstName.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel lblFName = new JLabel();
			lblFName.setText("First Name");
			rowFirstName.add(lblFName);
			JTextField tfFirstName = new JTextField(20);
			rowFirstName.add(tfFirstName);
			formPanel.add(rowFirstName);
			
			this.tf_FirstName = tfFirstName;
		}
		
		//2.3 Last Name
		{
			JPanel rowLastName = new JPanel();
			rowLastName.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel lblLName = new JLabel();
			lblLName.setText("Last Name");
			rowLastName.add(lblLName);
			JTextField tfLastName = new JTextField(20);
			rowLastName.add(tfLastName);
			formPanel.add(rowLastName);
			
			this.tf_LastName = tfLastName;
		}
		
		//2.4 Street
		{
			JPanel rowStreet = new JPanel();
			rowStreet.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel lblStreet = new JLabel();
			lblStreet.setText("Street");
			rowStreet.add(lblStreet);
			JTextField tfStreet = new JTextField(30);
			rowStreet.add(tfStreet);
			formPanel.add(rowStreet);
			
			this.tf_Street = tfStreet;
		}
		
		//2.5 City
		{
			JPanel rowCity = new JPanel();
			rowCity.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel lblCity = new JLabel();
			lblCity.setText("City");
			rowCity.add(lblCity);
			JTextField tfCity = new JTextField(20);
			rowCity.add(tfCity);
			formPanel.add(rowCity);
			
			this.tf_City = tfCity;
		}
		
		//2.6 State
		{
			JPanel rowState = new JPanel();
			rowState.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel lblState = new JLabel();
			lblState.setText("State");
			rowState.add(lblState);
			JTextField tfState = new JTextField(20);
			rowState.add(tfState);
			formPanel.add(rowState);
			
			this.tf_State = tfState;
		}
		//2.7 Zipcode
		{
			JPanel rowZip = new JPanel();
			rowZip.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel lblZip = new JLabel();
			lblZip.setText("Zipcode");
			rowZip.add(lblZip);
			JTextField tfZip = new JTextField(10);
			rowZip.add(tfZip);
			formPanel.add(rowZip);
			
			this.tf_Zip = tfZip;
		}
		
		//2.8 Phone
		{
			JPanel rowPhone = new JPanel();
			rowPhone.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel lblPhone = new JLabel();
			lblPhone.setText("Phone");
			rowPhone.add(lblPhone);
			JTextField tfPhone = new JTextField(12);
			rowPhone.add(tfPhone);
			formPanel.add(rowPhone);
			
			this.tf_Phone = tfPhone;
		}
		
		//2.9 Buttons
		{
			JPanel rowBtns = new JPanel();
			rowBtns.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton btnAdd = new JButton(" Add ");
			btnAdd.addActionListener(evt -> {
				try {
					LibrarySystem.INSTANCE.ci.addNewMember(tf_MemberId.getText(), 
							tf_FirstName.getText(), 
							tf_LastName.getText(), 
							tf_Phone.getText(), 
							tf_Street.getText(), 
							tf_City.getText(), 
							tf_State.getText(), 
							tf_Zip.getText() );
					JOptionPane.showMessageDialog(AddNewMemberWindow.this, "Successful Add New Member");
					LibrarySystem.INSTANCE.repaint();
					AddNewMemberWindow.this.backToMain();
				} catch (LibrarySystemException e) {
					JOptionPane.showMessageDialog(AddNewMemberWindow.this, e.getMessage());
				}
			});
			
			JButton btnClear = new JButton(" Clear ");
			btnClear.addActionListener(evt -> {
				clearData();
			});
			rowBtns.add(btnAdd);
			rowBtns.add(btnClear);
			formPanel.add(rowBtns);
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
		isInitialized(true);
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		this.isInitialized = val;
		
	}
	
	private void clearData() {
		tf_MemberId.setText("");
		tf_FirstName.setText("");
		tf_LastName.setText("");
		tf_Street.setText("");
		tf_City.setText("");
		tf_State.setText("");
		tf_Zip.setText("");
		tf_Phone.setText("");
	}
	
	private void backToMain() {
		LibrarySystem.hideAllWindows();
		LibrarySystem.INSTANCE.setVisible(true);
		clearData();
	}
}
