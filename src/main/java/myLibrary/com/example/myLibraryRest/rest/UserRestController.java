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
import myLibrary.com.example.myLibraryRest.model.User;
import myLibrary.com.example.myLibraryRest.service.LibraryService;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private LibraryService libraryService;

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/*/lastname/{lastName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<User>> getUsersList(@PathVariable("lastName") String userLastName) {
		if (userLastName == null) {
			userLastName = "";
		}
		Collection<User> users = this.libraryService.findByLastName(userLastName);
		if (users.isEmpty()) {
			return new ResponseEntity<Collection<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<User>> getUsers() {
		Collection<User> users = this.libraryService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<Collection<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("userId") int userId) {
		User user = null;
		user = this.libraryService.findByUserId(userId);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> addOwner(@RequestBody @Valid User user, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (user == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<User>(headers, HttpStatus.BAD_REQUEST);
		}
		this.libraryService.save(user);
		headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable("userId") int userId, @RequestBody @Valid User user,
			BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (user == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<User>(headers, HttpStatus.BAD_REQUEST);
		}
		User currentUser = this.libraryService.findByUserId(userId);
		if (currentUser == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		currentUser.setAddress(user.getAddress());
		currentUser.setStatus(user.getStatus());
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setPassword(user.getPassword());
		currentUser.setUserName(user.getUserName());
		currentUser.setRole(user.getRole());
		this.libraryService.save(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.NO_CONTENT);
	}

	// @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId) {
		User user = this.libraryService.findByUserId(userId);
		if (user == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.libraryService.delete(user);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
