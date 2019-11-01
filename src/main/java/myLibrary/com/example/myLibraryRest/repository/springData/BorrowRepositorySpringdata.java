package myLibrary.com.example.myLibraryRest.repository.springData;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import myLibrary.com.example.myLibraryRest.model.Book;
import myLibrary.com.example.myLibraryRest.model.BorrowHistory;
import myLibrary.com.example.myLibraryRest.repository.BorrowRepository;

public interface BorrowRepositorySpringdata extends BorrowRepository, Repository<Book, Integer> {

	@Query("SELECT borrowHistory FROM BorrowHistory borrowHistory where book.id = :id")
	@Transactional(readOnly = true)
	Collection<BorrowHistory> findBorrowHistoryByBookId(int id);

	@Query("SELECT borrowHistory FROM BorrowHistory borrowHistory where user.id = :id")
	@Transactional(readOnly = true)
	Collection<BorrowHistory> findBorrowHistoryByUserId(int id);

	@Query("SELECT borrowHistory FROM BorrowHistory borrowHistory where id = :id")
	@Transactional(readOnly = true)
	BorrowHistory findBorrowHistoryById(int id);

	@Transactional
	void save(BorrowHistory borrowHistory);

}
