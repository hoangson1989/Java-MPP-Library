package dataaccess;
import java.time.LocalDate;
import java.util.ArrayList;
import business.LibrarySystemException;
import business.LibraryMember;
import business.CheckoutRecordEntry;

public class TestDueDate {

	public static void main(String[] args) {
		ArrayList<String> listMemberId = new ArrayList<>();
		listMemberId.add("1004");
		try {
			for(String str : listMemberId) {
				LibraryMember mem = DataAccessFacade.INSTANCE.searchMember(str);
				for( CheckoutRecordEntry entry : mem.getRecord().getRecords()) {
					LocalDate date = LocalDate.now();
					entry.setDueDate(date.minusDays(2));
				}
				DataAccessFacade.INSTANCE.saveNewMember(mem);
				System.out.println("finished update member : " + mem.getFirstName());
			}
			
		} catch(LibrarySystemException e) {
			System.out.println("error : " + e.getMessage());
		}
		
	}
}
