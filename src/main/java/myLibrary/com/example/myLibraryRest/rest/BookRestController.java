package myLibrary.com.example.myLibraryRest.rest;

import java.util.Collection;

import javax.transaction.Transactional;
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

import myLibrary.com.example.myLibraryRest.model.Book;
import myLibrary.com.example.myLibraryRest.service.LibraryService;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/books")
public class BookRestController {

	@Autowired
	private LibraryService libraryService;

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/*/title/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Book>> getBooksList(@PathVariable("title") String title) {
		if (title == null) {
			title = "";
		}
		Collection<Book> books = this.libraryService.findByTitle(title);
		if (books.isEmpty()) {
			return new ResponseEntity<Collection<Book>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Book>>(books, HttpStatus.OK);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Book>> getBooks() {
		Collection<Book> books = this.libraryService.findAllBooks();
		if (books.isEmpty()) {
			return new ResponseEntity<Collection<Book>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Book>>(books, HttpStatus.OK);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{bookId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Book> getBook(@PathVariable("bookId") int bookId) {
		Book book = null;
		book = this.libraryService.findByBookId(bookId);
		if (book == null) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Book> addBook(@RequestBody @Valid Book book, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (book == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Book>(headers, HttpStatus.BAD_REQUEST);
		}
		this.libraryService.save(book);
		headers.setLocation(ucBuilder.path("/api/books/{id}").buildAndExpand(book.getId()).toUri());
		return new ResponseEntity<Book>(book, headers, HttpStatus.CREATED);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{bookId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Book> updateBook(@PathVariable("bookId") int bookId, @RequestBody @Valid Book book,
			BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (book == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Book>(headers, HttpStatus.BAD_REQUEST);
		}
		Book currentBook = this.libraryService.findByBookId(bookId);
		if (currentBook == null) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
		currentBook.setTitle(book.getTitle());
		currentBook.setStatus(book.getStatus());
		currentBook.setAuthor(book.getAuthor());
		currentBook.setBorrowHistories(book.getBorrowHistories());
		currentBook.setType(book.getType());
		this.libraryService.save(currentBook);
		return new ResponseEntity<Book>(currentBook, HttpStatus.NO_CONTENT);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteBook(@PathVariable("bookId") int bookId) {
		Book book = this.libraryService.findByBookId(bookId);
		if (book == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.libraryService.delete(book);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
