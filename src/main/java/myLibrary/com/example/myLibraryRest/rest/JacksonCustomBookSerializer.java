package myLibrary.com.example.myLibraryRest.rest;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import myLibrary.com.example.myLibraryRest.model.Book;

public class JacksonCustomBookSerializer extends StdSerializer<Book> {

	private static final long serialVersionUID = 1L;

	public JacksonCustomBookSerializer() {
		this(null);
	}

	public JacksonCustomBookSerializer(Class<Book> t) {
		super(t);
	}

	@Override
	public void serialize(Book book, JsonGenerator jgen, SerializerProvider provider) throws IOException {

		jgen.writeStartObject();
		if (book.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", book.getId());
		}

		jgen.writeStringField("title", book.getTitle());
		jgen.writeStringField("author", book.getAuthor());
		jgen.writeStringField("type", book.getType().name());

		jgen.writeStringField("status", book.getStatus());
		jgen.writeEndObject(); // user

	}
}
