package librarysystem;

import java.awt.*;

import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class AddBookCopyWindow extends JFrame implements LibWindow {

    public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();

    private boolean isInitialized = false;

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }


    public void clear() {
        bookIdTextField.setText("");
    }

    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel lowerPanel = new JPanel();
    private final JLabel bookId = new JLabel("Book ISBN:");
    private final JTextField bookIdTextField = new JTextField("");

	private JTable table;

    /* This class is a singleton */
    private AddBookCopyWindow() {
    }

    public void init() {

        if (isInitialized) {
            return;
        }
        String[] column_names = {"ISBN","Title","Number of Copies"};
        DefaultTableModel table_model = new DefaultTableModel(column_names, 0);
        table=new JTable(table_model);
        middlePanel.add(table.getTableHeader());
        middlePanel.add(table);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();

        isInitialized = true;
        this.setTitle("Add Book Copy");

        JPanel testAreaPanel = new JPanel(new GridLayout(2,2));
        testAreaPanel.add(bookId);
        testAreaPanel.add(bookIdTextField);
        middlePanel.add(testAreaPanel, BorderLayout.NORTH);

        JButton btnSearch = new JButton("Search Book");
        JButton addBookCopy = new JButton("Add A Copy");
        JButton clearForm = new JButton("Clear");
        lowerPanel.add(btnSearch,0);
        lowerPanel.add(addBookCopy,1);
        lowerPanel.add(clearForm,2);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);

        btnSearch.addActionListener(e -> searchBookAction());
        addBookCopy.addActionListener(e -> addBookCopyAction());
        clearForm.addActionListener(e -> clearAllTextFields());
    }

    private void clearAllTextFields() {
        bookIdTextField.setText("");
    }
    private void updateTableDataWithBook(Book b) {
    	DefaultTableModel table_model = (DefaultTableModel) table.getModel();
    	//
    	if (table_model.getRowCount() > 0) {
    	    for (int i = table_model.getRowCount() - 1; i > -1; i--) {
    	    	table_model.removeRow(i);
    	    }
    	}
    	//
    	((DefaultTableModel) table.getModel()).addRow(new Object[]{b.getIsbn(),b.getTitle(), b.getNumCopies()});
    }
    private void searchBookAction() {
    	
    	//
    	try {
            ControllerInterface c = LibrarySystem.INSTANCE.ci;
            if (!bookIdTextField.getText().isEmpty()) {
                Book b = c.getBookById(bookIdTextField.getText().trim());
                if (b != null) {
                	updateTableDataWithBook(b);
                } else {
                    JOptionPane.showMessageDialog(AddBookCopyWindow.this, "Error! " + "Book with ISBN " + bookIdTextField.getText().trim() + " not found");
                }
            } else {
                JOptionPane.showMessageDialog(AddBookCopyWindow.this, "Error! " + "Incorrect Value of ISBN");
            }
            
        } catch (LibrarySystemException ex) {
        	JOptionPane.showMessageDialog(AddBookCopyWindow.this, "Error! " + ex.getMessage());
        }
    }

    private void addBookCopyAction() {
        try {
            ControllerInterface c = LibrarySystem.INSTANCE.ci;
            if (!bookIdTextField.getText().isEmpty()) {
                Book b = c.getBookById(bookIdTextField.getText().trim());
                if (b != null) {
                	c.addABookCopy(bookIdTextField.getText().trim());
                	try {
                	    Thread.sleep((long) (0.2 * 1000));
                	    searchBookAction();
                	} catch (InterruptedException ie) {
                	}
                    
                } else {
                    JOptionPane.showMessageDialog(AddBookCopyWindow.this, "Error! " + "Book with ISBN " + bookIdTextField.getText().trim() + " not found");
                }
            } else {
                JOptionPane.showMessageDialog(AddBookCopyWindow.this, "Error! " + "Incorrect Value of ISBN");
            }
            
        } catch (LibrarySystemException ex) {
        	JOptionPane.showMessageDialog(AddBookCopyWindow.this, "Error! " + ex.getMessage());
        }
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("Add Book Copy");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel.setLayout(new GridLayout(4,1));
    }

    public void defineLowerPanel() {

        JButton backToMainButn = new JButton("<= Back to Main");
        backToMainButn.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
            AddBookCopyWindow.INSTANCE.setVisible(false);
        });
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
        lowerPanel.add(backToMainButn);
        clearAllTextFields();
        //
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
    	//
    	if (table_model.getRowCount() > 0) {
    	    for (int i = table_model.getRowCount() - 1; i > -1; i--) {
    	    	table_model.removeRow(i);
    	    }
    	}
    }

}
