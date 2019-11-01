package myLibrary.com.example.myLibraryRest.rest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import myLibrary.com.example.myLibraryRest.model.Book;
import myLibrary.com.example.myLibraryRest.model.BorrowHistory;
import myLibrary.com.example.myLibraryRest.model.User;

public class JacksonCustomBorrowHistorySerializer extends StdSerializer<BorrowHistory> {

	private static final long serialVersionUID = 1L;

	public JacksonCustomBorrowHistorySerializer() {
		this(null);
	}

	public JacksonCustomBorrowHistorySerializer(Class<BorrowHistory> t) {
		super(t);
	}

	@Override
	public void serialize(BorrowHistory borrowHistory, JsonGenerator jgen, SerializerProvider provider)
			throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		jgen.writeStartObject();
		if (borrowHistory.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", borrowHistory.getId());
		}

		jgen.writeStringField("dateBorrow", formatter.format(borrowHistory.getDateBorrow()));

		jgen.writeStringField("dateTurnBack", formatter.format(borrowHistory.getDateTurnBack()));

		Book book = borrowHistory.getBook();
		jgen.writeObjectFieldStart("book");
		jgen.writeNumberField("id", book.getId());
		jgen.writeStringField("title", book.getTitle());

		// jgen.writeStringField("type", book.getType());

		jgen.writeEndObject(); // book

		User user = borrowHistory.getUser();
		jgen.writeObjectFieldStart("user");
		jgen.writeNumberField("id", user.getId());
		jgen.writeStringField("firstName", user.getFirstName());
		jgen.writeStringField("nachName", user.getLastName());
		jgen.writeEndObject(); // user

		jgen.writeEndObject(); // BorrowHis
	}
}
