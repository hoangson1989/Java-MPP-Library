package business;

import java.time.LocalDate;

public class CheckoutRecordEntry {
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private BookCopy bookCopy;
	
	CheckoutRecordEntry(LocalDate cDate, LocalDate dDate, BookCopy bCopy) {
		this.checkoutDate = cDate;
		this.dueDate = dDate;
		this.bookCopy = bCopy;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}
	
	@Override
	public String toString() {
		String des = "";
		des += bookCopy.toString();
		des += ("\nCheckout Date : " + checkoutDate.getMonth().toString() + "/" + checkoutDate.getDayOfMonth() + "/" + checkoutDate.getYear());
		des += ("\nDue Date : " + dueDate.getMonth().toString() + "/" + dueDate.getDayOfMonth() + "/" + dueDate.getYear());
		return des;
	}
}
