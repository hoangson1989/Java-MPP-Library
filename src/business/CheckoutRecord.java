package business;

import java.time.LocalDate;
import java.util.ArrayList;

public class CheckoutRecord {
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
