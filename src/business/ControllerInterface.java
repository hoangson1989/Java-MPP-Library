package business;

import java.util.ArrayList;
import java.util.List;

import dataaccess.Auth;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void logout();
	
	// case 1 - Login
	public LibraryMember searchMember(String memberId) throws LibrarySystemException;
	public Auth getAuthentication();
	
	// case 2- Add new Library Member
	public LibraryMember addNewMember(String memberId, String fName, String lName, String tel, String street, String city, String state, String zip) throws LibrarySystemException;
	
	// case 3 - Checkout a Book
	public CheckoutRecordEntry checkoutABook(String memberId, String bookIsbn) throws LibrarySystemException;

	// case 4 - Add a Book Copy
	public void addABookCopy(String bookIsbn) throws LibrarySystemException;
	Book getBookById(String bookIsbn) throws LibrarySystemException;
	List<Book> getAllBooks();
	void addBookCopy(String bookId, int copyNum) throws LibrarySystemException;

	// case 5 - Add new Book
    public void addNewBook(String bookIsbn, String title, int maxCheckout, List<Author> authors, int copies) throws LibrarySystemException;
    
    // case 6 - Print Checkout Record
    public String getCheckoutRecord(String memberId) throws LibrarySystemException;
    public String[][] getCheckoutRecord2(String memberId) throws LibrarySystemException;
    
    // case 7 - Overdue Book Copy
    public String[][] getBookOverdueList(String bookIsbn) throws LibrarySystemException;


}
