package librarysystem;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;


public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE,
		AddNewMemberWindow.INSTANCE
	};
    	
	public static void hideAllWindows() {		
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);			
		}
	}
	
	@Override
	public void repaint() {
		updateUIByRole();
		super.repaint();
	}
     
    private LibrarySystem() {}
    
    public void init() {
    	if (isInitialized) {
			return;
		}
    	formatContentPane();
    	setPathToImage();
    	insertSplashImage();
		
		createMenus();
		//pack();
		setSize(660,500);
		isInitialized = true;
    }
    
    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+ File.separator +"src"+File.separator+"librarysystem"+File.separator+"library.jpg";
    }
    
    private void insertSplashImage() {
    	lbl_Welcome = new JLabel("");
		mainPanel.add(lbl_Welcome);

        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }
    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);		
    }
    
    private void addMenuItems() {
       options = new JMenu("Options");  
 	   menuBar.add(options);
 	   //
 	   menuItem_Login.addActionListener(new LoginListener());
 	   menuItem_viewAllBooks.addActionListener(new AllBookIdsListener());
 	   menuItem_viewAllMembers.addActionListener(new AllMemberIdsListener());
 	   menuItem_Logout.addActionListener(new LogoutListener());
 	   menuItem_AddNewMember.addActionListener(new AddNewMemberListener());
 	   //
 	   updateUIByRole();
    }
    
    private void updateUIByRole() {
    	options.removeAll();
    	Auth auth = ci.getAuthentication();
    	if (auth != null) {
    		lbl_Welcome.setText("Welcome to RedDragon Library. Please choose options for next action!!!");
    		if (auth.equals(Auth.ADMIN)) {
    			for (JMenuItem item : adminOptions) {
        			options.add(item);
        		}
        	} else if (auth.equals(Auth.LIBRARIAN)) {
        		for (JMenuItem item : librarianOptions) {
        			options.add(item);
        		}
        	} else {
        		for (JMenuItem item : allOptions) {
        			options.add(item);
        		}
        	}
    	} else {
    		for (JMenuItem item : loginOptions) {
    			options.add(item);
    		}
    		lbl_Welcome.setText("Please log in");
    	}
    }
    
    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.pack();
			LoginWindow.INSTANCE.setVisible(true);
			
		}
    	
    }
    class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			
			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);
			
		}
    	
    }
    
    class AllMemberIdsListener implements ActionListener {

    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setVisible(true);
			
			
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			
			List<String> ids = ci.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
			AllMemberIdsWindow.INSTANCE.pack();
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);
			
			
		}
    	
    }

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
	////
	JMenuItem menuItem_Login = new JMenuItem("Log In");
	JMenuItem menuItem_Logout = new JMenuItem("Log Out");
	JMenuItem menuItem_AddNewMember = new JMenuItem("Add New Member");
	JMenuItem menuItem_CheckoutBook = new JMenuItem("Checkout Book");
	JMenuItem menuItem_AddNewCopy = new JMenuItem("Add New Copy");
	JMenuItem menuItem_AddNewBook = new JMenuItem("Add New Book");
	JMenuItem menuItem_viewCheckoutRecord = new JMenuItem("View Checkout Record");
	JMenuItem menuItem_viewOverdue = new JMenuItem("View Overdue Record");
	JMenuItem menuItem_viewAllBooks = new JMenuItem("View All Books");
	JMenuItem menuItem_viewAllMembers = new JMenuItem("View All Members");

	JMenuItem[] loginOptions = { menuItem_Login };
	JMenuItem[] librarianOptions = {menuItem_Logout, menuItem_viewAllBooks, menuItem_viewCheckoutRecord, menuItem_CheckoutBook};
	JMenuItem[] adminOptions = { menuItem_Logout, menuItem_AddNewMember, menuItem_AddNewBook, menuItem_viewAllBooks , menuItem_AddNewCopy, menuItem_viewAllMembers};
	JMenuItem[] allOptions = {menuItem_Logout, menuItem_AddNewMember, menuItem_AddNewBook, menuItem_viewAllBooks,
			menuItem_AddNewCopy, menuItem_viewAllMembers, menuItem_viewCheckoutRecord, menuItem_CheckoutBook};
	
	JLabel lbl_Welcome;

	// Case 1
	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ci.logout();
			JOptionPane.showMessageDialog(LibrarySystem.this,"Successful Logout");
			updateUIByRole();
		}
		
	}
	
	// Case 2
	class AddNewMemberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddNewMemberWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(AddNewMemberWindow.INSTANCE);
			AddNewMemberWindow.INSTANCE.pack();
			AddNewMemberWindow.INSTANCE.setVisible(true);
		}
		
	}
	
	// Case Optional 2
	class ViewMemberCheckoutListenr implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			MemberCheckoutReportWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(MemberCheckoutReportWindow.INSTANCE);
			MemberCheckoutReportWindow.INSTANCE.pack();
			MemberCheckoutReportWindow.INSTANCE.setVisible(true);
		}
		
	}
}
