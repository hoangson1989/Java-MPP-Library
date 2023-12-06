package librarysystem;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MemberCheckoutReportWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = -6794238609482754499L;
	private boolean isInitialized;
	public static final MemberCheckoutReportWindow INSTANCE = new MemberCheckoutReportWindow();

	@Override
	public void init() {
		if (isInitialized == false) {
			JPanel mainPanel = new JPanel();
			BorderLayout bl = new BorderLayout();
			mainPanel.setLayout(bl);
			
			{}
			
		}
		
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;		
	}

	private void backToMain() {
		LibrarySystem.hideAllWindows();
		LibrarySystem.INSTANCE.setVisible(true);
		clearData();
	}
	
	private void clearData() {
		
	}
}
