package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import netscape.javascript.JSObject;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	
	@Override
	public void logout() {
		currentAuth = null;
	}
	
	@Override
	public Auth getAuthentication() {
		return currentAuth;
	}
	
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	
	@Override
	public LibraryMember searchMember(String memberId) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		return da.searchMember(memberId);
	}
	@Override
	public LibraryMember addNewMember(String memberId, String fName, String lName, String tel, String street, String city, String state, String zip) throws LibrarySystemException {
		if (memberId.isBlank() || fName.isBlank() || lName.isBlank() || tel.isBlank() || street.isBlank() ||
				city.isBlank() || state.isBlank() || zip.isBlank() ) {
            throw new LibrarySystemException("Member infos should not be empty");
		}
		
        Address address = new Address(street, city, state, zip);
        if (this.searchMember(memberId) != null) {
            throw new LibrarySystemException("LibraryMember with Id " + memberId + " exist");
        }
        DataAccess da = new DataAccessFacade();
        LibraryMember member = new LibraryMember(memberId, fName, lName, tel, address);
        da.saveNewMember(member);
		return null;
	}
	@Override
	public CheckoutRecordEntry checkoutABook(String memberId, String bookIsbn) throws LibrarySystemException {
		if (memberId == null || memberId.isBlank()) {
            throw new LibrarySystemException("memberId should not be empty");
		}
		if (bookIsbn == null || bookIsbn.isBlank()) {
            throw new LibrarySystemException("bookIsbn should not be empty");
		}
		
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(memberId);
		if (member == null) {
            throw new LibrarySystemException("Member with Id " + memberId + " not exist");
		}
		
        Book book = da.searchBook(bookIsbn);
        if (book == null) {
            throw new LibrarySystemException("Book with Isbn " + bookIsbn + " not exist");
		}
        if(book.isAvailable() == false) {
            throw new LibrarySystemException("Book with Isbn " + bookIsbn + " not available");
        }
        
        ArrayList<CheckoutRecordEntry> memberCheckoutList = member.getRecord().getRecords();
        for (CheckoutRecordEntry entry : memberCheckoutList) {
        	if (entry.getBookCopy().getBook().getIsbn().equals(bookIsbn)) {
                throw new LibrarySystemException("Member " + memberId + " already checked out the Book " + bookIsbn + ", Checkout Date : " + entry.getCheckoutDate().toString());
        	}
        }
        LocalDate cDate = LocalDate.now();
        LocalDate dDate = cDate.plusDays(book.getMaxCheckoutLength());
        final CheckoutRecordEntry newEntry = member.getRecord().addNewCheckoutRecordEntry(cDate, dDate, book.getNextAvailableCopy());
        da.saveNewMember(member);
        da.saveNewBook(book);
		
		return newEntry;
	}
	@Override
	public void addABookCopy(String bookIsbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		Book book = da.searchBook(bookIsbn);
		if (book == null) {
            throw new LibrarySystemException("Book with Isbn " + bookIsbn + " not exist");
		}
		book.addCopy();
		da.saveNewBook(book);
	}
	@Override
	public void addNewBook(String bookIsbn, String title, int maxCheckout, List<Author> authors)
			throws LibrarySystemException {
		 DataAccess da = new DataAccessFacade();
		 Book newBook = da.searchBook(bookIsbn);
		 if (newBook != null) {
			 throw new LibrarySystemException("Book with Isbn " + bookIsbn + " exist");
		 }
		 newBook = new Book(bookIsbn, title, maxCheckout, authors);
		 da.saveNewBook(newBook);		
	}
	@Override
	public String getCheckoutRecord(String memberId) throws LibrarySystemException {
		String result = "";
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(memberId);
		if (member == null) { 
			throw new LibrarySystemException("Member with Id " + memberId + " not exist");
		}
		 
		CheckoutRecord checkoutRec = member.getRecord();
		if (checkoutRec != null) {
			ArrayList<CheckoutRecordEntry> records = checkoutRec.getRecords();
			 
			String leftAlignFormat = "| %-15s | %-10s | %-10s |%n";
			result += "+-----------------+------------+------------+\n";
			result += 	     "|   Book Isbn     | Check Date |  Due Date  |\n";
			result +=       "+-----------------+------------+------------+\n";
			for (CheckoutRecordEntry entry : records) {
				result += String.format(leftAlignFormat, entry.getBookCopy().getBook().getIsbn(), entry.getCheckoutDate().toString(), entry.getDueDate());
				result += "\n";
				result +=       "+-----------------+------------+------------+\n";
			}
		} else {
			result += "No CheckoutRecord found";
		}
		
		return result;
	}
	@Override
	public ArrayList<String[]> getBookOverdueList(String bookIsbn) throws LibrarySystemException {
		if (bookIsbn == null || bookIsbn.isBlank()) {
            throw new LibrarySystemException("bookIsbn should not be empty");
		}
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		LocalDate current = LocalDate.now();
		ArrayList<String[]> overdueList = new ArrayList<String[]>();
		for(String memberId : members.keySet()) {
			LibraryMember member = members.get(memberId);
			ArrayList<CheckoutRecordEntry> records = member.getRecord().getRecords();
			for (CheckoutRecordEntry entry : records) {
				BookCopy copy = entry.getBookCopy();
				Book book = copy.getBook();
				if (book.getIsbn().equals(bookIsbn) && current.isAfter(entry.getDueDate()) && copy.isAvailable() == false) {
					//
					overdueList.add(new String[] {bookIsbn, book.getTitle(), ""+copy.getCopyNum(), entry.getDueDate().toString() });
				}
			}
		}
		return overdueList;
	}
	
	
}
