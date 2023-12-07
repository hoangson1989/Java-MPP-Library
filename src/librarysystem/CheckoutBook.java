package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import librarysystem.MessageWIndow;

public class CheckoutBook extends JFrame implements LibWindow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final CheckoutBook INSTANCE = new CheckoutBook();
	ControllerInterface ci = SystemController.INSTANCE;
	
	private boolean isInitialized = false;
	
	

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JTextField isbnField;
    private JTextField memberIdField;

    private JPanel innerPanel;

    private JButton checkoutButton;
    
    private CheckoutBook() {
//        mainPanel = new JPanel();
//        defineTopPanel();
//        defineMiddlePanel();
//        defineLowerPanel();
//        BorderLayout bl = new BorderLayout();
//        bl.setVgap(30);
//        mainPanel.setLayout(bl);
//
//        mainPanel.add(topPanel, BorderLayout.NORTH);
//        mainPanel.add(middlePanel, BorderLayout.CENTER);
//        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
    }
    
    @Override
	public void init() {
		// TODO Auto-generated method stub
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
		isInitialized = true;
		
	}

    private void defineTopPanel() {
        topPanel = new JPanel();
        JLabel label = new JLabel("Checkout Book");
        Util.adjustLabelFont(label, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(label);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        middlePanel.setLayout(bl);

        innerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        innerPanel.setLayout(fl);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel memberIdLabel = new JLabel("Member ID");
        JLabel isbnLabel = new JLabel("ISBN");

        memberIdField = new JTextField(10);
        isbnField = new JTextField(10);

        leftPanel.add(memberIdLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(isbnLabel);

        rightPanel.add(memberIdField);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(isbnField);

        innerPanel.add(leftPanel);
        innerPanel.add(rightPanel);
        middlePanel.add(innerPanel, BorderLayout.NORTH);
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
        lowerPanel.setLayout(fl);
        checkoutButton = new JButton("Checkout");
        addCheckoutButtonListener(checkoutButton);
        lowerPanel.add(checkoutButton);
    }

    private void addCheckoutButtonListener(JButton checkoutBtn) {
        checkoutBtn.addActionListener(evt -> {
            String bkISBN = isbnField.getText().trim();
            String memberID = memberIdField.getText().trim();

            if (bkISBN.length() == 0 || memberID.length() == 0) {
            	displayError("ISBN and Member ID may not be empty");
            } else {
                try {
                    ci.checkoutABook(memberID, bkISBN);
                    displayInfo("Checkout successful");
                } catch (LibrarySystemException e) {
                    displayError(e.getMessage());
                }
            }
        }
            );
    }
    
    private void displayError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void displayInfo(String infoMessage) {
        JOptionPane.showMessageDialog(null, infoMessage, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		isInitialized = val;
		
	}

}
