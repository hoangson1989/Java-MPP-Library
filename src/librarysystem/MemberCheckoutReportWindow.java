package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import business.LibraryMember;
import business.LibrarySystemException;

public class MemberCheckoutReportWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = -6794238609482754499L;
	private boolean isInitialized;
	public static final MemberCheckoutReportWindow INSTANCE = new MemberCheckoutReportWindow();

	JTextField tf_MemberId;
	JButton btn_Search;
	JLabel lbl_SearchResult;
	JButton btnReport;
	JTextArea tvReport;
	JScrollPane tvScroll;
	
	@Override
	public void init() {
		if (isInitialized == false) {
			JPanel mainPanel = new JPanel();
			BorderLayout bl = new BorderLayout();
			mainPanel.setLayout(bl);
			
						
			// 1.Content
			JPanel formPanel = new JPanel();
			formPanel.setLayout(new GridLayout(4,1));
			mainPanel.add(formPanel, BorderLayout.NORTH);
			
			// 1.1
			{
				JLabel lblTitle = new JLabel();
				lblTitle.setText("Member Checkout Report");
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
				rowPanel.add(lblTitle);
				
				formPanel.add(rowPanel);
			} 
						
			
			// 1.2 
			{
				JLabel lbl = new JLabel("Member Id");
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
				tf_MemberId = tf;
				
				JButton btn = new JButton(" Search ");
				btn.setEnabled(false);
				btn.addActionListener(evt -> {
					String txt = tf_MemberId.getText().trim();
					try {
						LibraryMember mem = LibrarySystem.INSTANCE.ci.searchMember(txt);
						if(mem != null) {
							btnReport.setVisible(true);
							lbl_SearchResult.setText("Found Member : " + mem.getFirstName() + " " + mem.getLastName());
						} else {
							btnReport.setVisible(false);
							lbl_SearchResult.setText("Member Id " + txt + " not found");
						}
						
					} catch (LibrarySystemException e1) {
						JOptionPane.showMessageDialog(MemberCheckoutReportWindow.this, e1.getMessage());
					}
				});
				btn_Search = btn;
				
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				rowPanel.add(lbl);
				rowPanel.add(tf);
				rowPanel.add(btn);
				formPanel.add(rowPanel);
			}
			
			//1.3 Search Result
			{
				JLabel lbl = new JLabel("");
				lbl_SearchResult = lbl;
				
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
				rowPanel.add(lbl);
				formPanel.add(rowPanel);
			}
			
			
			// 1.4 Report Button
			{
				btnReport = new JButton("View Report");
				btnReport.setVisible(false);
				btnReport.addActionListener(evt -> {
					String txt = tf_MemberId.getText().trim();
					try {
						String result = LibrarySystem.INSTANCE.ci.getCheckoutRecord(txt);
						tvReport.setText(result);
						tvScroll.setVisible(true);
					} catch (LibrarySystemException e) {
						tvScroll.setVisible(false);
					}
					tvScroll.invalidate();
					pack();
					MemberCheckoutReportWindow.this.getContentPane().validate();
					MemberCheckoutReportWindow.this.getContentPane().repaint();

				});
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
				rowPanel.add(btnReport);
				formPanel.add(rowPanel);
			}
			
			// 2 TextArea
			{
				tvReport = new JTextArea(10, 20);
				tvReport.setEditable(false);
				tvReport.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
				JPanel rowPanel = new JPanel();
				rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
				JScrollPane scroll = new JScrollPane(tvReport,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scroll.setVisible(false);
				mainPanel.add(scroll, BorderLayout.CENTER);
				
				tvScroll = scroll;
			}
			
			// 3.Back Button
			{
				JPanel lowerHalf = new JPanel();
				JButton backButton = new JButton("<= Back to Main");
				backButton.addActionListener(evt -> {
					backToMain();
				});
				lowerHalf.add(backButton);
				mainPanel.add(lowerHalf, BorderLayout.SOUTH);
			}
			// 4.
			getContentPane().add(mainPanel);
			pack();
			isInitialized(true);
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
		tf_MemberId.setText("");
		lbl_SearchResult.setText("");
		btn_Search.setEnabled(false);
		btnReport.setVisible(false);
		tvScroll.setVisible(false);
		tvScroll.invalidate();
		pack();
		MemberCheckoutReportWindow.this.getContentPane().validate();
		MemberCheckoutReportWindow.this.getContentPane().repaint();
	}
	
	private void updateBtnSearchVisible() {
		String txt = tf_MemberId.getText().trim();
		if (txt != null && txt.isBlank() == false) {
			btn_Search.setEnabled(true);
		} else {
			btn_Search.setEnabled(false);
		}
		
		if (lbl_SearchResult.getText().isEmpty() == false) {
			lbl_SearchResult.setText("");
			btnReport.setVisible(false);
			tvScroll.setVisible(false);
		}
	}
}
