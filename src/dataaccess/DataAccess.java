package dataaccess;

import java.util.HashMap;

import business.Book;
import business.LibraryMember;
import business.LibrarySystemException;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public LibraryMember searchMember(String memberId) throws LibrarySystemException;
	public void saveNewMember(LibraryMember member) throws LibrarySystemException; 
	public Book searchBook(String bookIsbn) throws LibrarySystemException;
	public void saveNewBook(Book book) throws LibrarySystemException;
}
