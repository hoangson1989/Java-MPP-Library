package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class CheckoutRecord implements Serializable {
	private static final long serialVersionUID = 1631023488983411468L;
	private ArrayList<CheckoutRecordEntry> records;
	
	CheckoutRecord() {
		records = new ArrayList<CheckoutRecordEntry>();
	}

	public ArrayList<CheckoutRecordEntry> getRecords() {
		return records;
	}
	
	public CheckoutRecordEntry addNewCheckoutRecordEntry(LocalDate cDate, LocalDate dDate, BookCopy copy) {
		CheckoutRecordEntry entry = new CheckoutRecordEntry(cDate, dDate, copy);
		records.add(entry);
		return entry;
	}
}
