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

    private final JTextField messageBar = new JTextField();

    public void clear() {
        messageBar.setText("");
    }

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private final JLabel bookId = new JLabel("Book ISBN:");
    private final JLabel bookCopyNum = new JLabel("Copy Number:");
    private final JTextField copyTextField = new JTextField("Please select ...");
    private final JTextField bookIdTextField = new JTextField("Please select ...");

    /* This class is a singleton */
    private AddBookCopyWindow() {
    }

    public void init() {

        if (isInitialized) {
            return;
        }
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        isInitialized = true;
        this.setTitle("Add BookCopy");

        JPanel testAreaPanel = new JPanel(new GridLayout(2,2));
        testAreaPanel.add(bookId);
        testAreaPanel.add(bookIdTextField);
        testAreaPanel.add(bookCopyNum);
        testAreaPanel.add(copyTextField);
        middlePanel.add(testAreaPanel, BorderLayout.NORTH);


        JButton addBookCopy = new JButton("Add Book Copy");
        JButton clearForm = new JButton("Clear");
        lowerPanel.add(addBookCopy,1);
        lowerPanel.add(clearForm,2);

        String[] column_names = {"ISBN","Title","Number of Copies"};
        DefaultTableModel table_model = new DefaultTableModel(column_names, 3);
        JTable table=new JTable(table_model);
        middlePanel.add(table.getTableHeader());
        middlePanel.add(table);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);

        addBookCopy.addActionListener(e -> addBookCopyAction());
        clearForm.addActionListener(e -> clearAllTextFields());
    }

    private void clearAllTextFields() {
        copyTextField.setText("Please select ...");
        bookIdTextField.setText("Please select ...");
    }

    private void addBookCopyAction() {
        try {
            ControllerInterface c = new SystemController();
            Book b = c.getBookById(bookIdTextField.getText().trim());
            if (!copyTextField.getText().isEmpty() && !bookIdTextField.getText().isEmpty() && b != null) {
                int copyNum = Integer.parseInt(copyTextField.getText().trim());
                c.addBookCopy(bookIdTextField.getText().trim(), copyNum);
            } else {
                messageBar.setText("Error! " + "Incorrect Value of ISBN or Book Copy.");
            }
            copyTextField.setText("");
            bookIdTextField.setText("");
        } catch (LibrarySystemException ex) {
            messageBar.setText("Error! " + ex.getMessage());
        }
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("All Book IDs");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
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
    }

}
