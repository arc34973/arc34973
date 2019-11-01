package myLibrary.com.example.myLibraryRest.rest;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import myLibrary.com.example.myLibraryRest.model.BorrowHistory;
import myLibrary.com.example.myLibraryRest.service.LibraryService;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/borrowHistory")
public class BorrowHistoryRestController {

	@Autowired
	private LibraryService libraryService;

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/*/userId/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<BorrowHistory>> getBorrowHistoryByUserId(@PathVariable("userId") int userId) {
		Collection<BorrowHistory> borrowHistories = this.libraryService.findBorrowHistoryByUserId(userId);
		if (borrowHistories.isEmpty()) {
			return new ResponseEntity<Collection<BorrowHistory>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<BorrowHistory>>(borrowHistories, HttpStatus.OK);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/*/bookId/{bookId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<BorrowHistory>> getBorrowHistoryByBookId(@PathVariable("bookId") int bookId) {
		Collection<BorrowHistory> borrowHistories = this.libraryService.findBorrowHistoryByBookId(bookId);
		if (borrowHistories.isEmpty()) {
			return new ResponseEntity<Collection<BorrowHistory>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<BorrowHistory>>(borrowHistories, HttpStatus.OK);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BorrowHistory> addBorrowHistory(@RequestBody @Valid BorrowHistory borrowHistory,
			BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (borrowHistory == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<BorrowHistory>(headers, HttpStatus.BAD_REQUEST);
		}
		this.libraryService.save(borrowHistory);
		headers.setLocation(ucBuilder.path("/api/borrowHistories/{id}").buildAndExpand(borrowHistory.getId()).toUri());
		return new ResponseEntity<BorrowHistory>(borrowHistory, headers, HttpStatus.CREATED);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{borrowHistoryId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BorrowHistory> updateBorrowHistory(@PathVariable("borrowHistoryId") int borrowHistoryId,
			@RequestBody @Valid BorrowHistory borrowHistory, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (borrowHistory == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<BorrowHistory>(headers, HttpStatus.BAD_REQUEST);
		}
		BorrowHistory currentBorrowHistory = this.libraryService.findBorrowHistoryById(borrowHistoryId);
		if (currentBorrowHistory == null) {
			return new ResponseEntity<BorrowHistory>(HttpStatus.NOT_FOUND);
		}
		currentBorrowHistory.setUser(borrowHistory.getUser());
		currentBorrowHistory.setBook(borrowHistory.getBook());
		currentBorrowHistory.setDateBorrow(borrowHistory.getDateBorrow());
		currentBorrowHistory.setDateTurnBack(borrowHistory.getDateTurnBack());
		this.libraryService.save(currentBorrowHistory);
		return new ResponseEntity<BorrowHistory>(currentBorrowHistory, HttpStatus.NO_CONTENT);
	}

}
