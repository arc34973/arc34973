package myLibrary.com.example.myLibraryRest.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import myLibrary.com.example.myLibraryRest.model.Book;
import myLibrary.com.example.myLibraryRest.model.BorrowHistory;

public interface BookRepository {

	Collection<Book> findByTitle(String title) throws DataAccessException;

	Book findByBookId(int id) throws DataAccessException;

	Collection<BorrowHistory> findBorrowHistoryByBookId(int id) throws DataAccessException;

	List<Book> findAll();

	void save(Book book) throws DataAccessException;

	void delete(Book book) throws DataAccessException;
}
