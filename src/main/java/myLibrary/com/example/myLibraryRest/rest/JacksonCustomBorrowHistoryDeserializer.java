package myLibrary.com.example.myLibraryRest.rest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import myLibrary.com.example.myLibraryRest.model.Book;
import myLibrary.com.example.myLibraryRest.model.BorrowHistory;
import myLibrary.com.example.myLibraryRest.model.User;

public class JacksonCustomBorrowHistoryDeserializer extends StdDeserializer<BorrowHistory> {

	private static final long serialVersionUID = 1L;

	public JacksonCustomBorrowHistoryDeserializer() {
		this(null);
	}

	public JacksonCustomBorrowHistoryDeserializer(Class<BorrowHistory> t) {
		super(t);
	}

	// wie kann man boorrowHistory deserializieren?
	@Override
	public BorrowHistory deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		CustomLocalDateEditor customLocalDateEditor = new CustomLocalDateEditor(formatter);

		JsonNode node = parser.getCodec().readTree(parser);
		BorrowHistory borrowHistory = new BorrowHistory();
		User user = new User();
		Book book = new Book();

		int userId = node.get("userId").asInt();
		user.setId(userId);
		int bookId = node.get("bookId").asInt();
		book.setId(bookId);
		int id = node.get("id").asInt();

		borrowHistory.setBook(book);
		borrowHistory.setUser(user);

		// for new BorrowHistory
		if (id == 0) {
			LocalDate dateBorrowNew = LocalDate.now();
			LocalDate dateTurnBackNew = dateBorrowNew.plusDays(21);
			borrowHistory.setDateBorrow(dateBorrowNew);
			borrowHistory.setDateTurnBack(dateTurnBackNew);

		} else {// for existing BorrowHistory

			String dateBorrowAsString = node.get("dateBorrow").asText(null);
			String dateTurnBackAsString = node.get("dateTurnBack").asText(null);

			customLocalDateEditor.setAsText(dateBorrowAsString);
			LocalDate dateBorrow = (LocalDate) customLocalDateEditor.getValue();

			customLocalDateEditor.setAsText(dateTurnBackAsString);
			LocalDate dateTurnBack = (LocalDate) customLocalDateEditor.getValue();

			borrowHistory.setId(id);
			borrowHistory.setDateBorrow(dateBorrow);
			borrowHistory.setDateTurnBack(dateTurnBack);

		}
		return borrowHistory;
	}

}
