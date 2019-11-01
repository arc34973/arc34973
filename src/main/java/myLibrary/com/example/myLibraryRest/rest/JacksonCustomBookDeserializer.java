package myLibrary.com.example.myLibraryRest.rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import myLibrary.com.example.myLibraryRest.model.Book;
import myLibrary.com.example.myLibraryRest.model.BookType;

public class JacksonCustomBookDeserializer extends StdDeserializer<Book> {

	private static final long serialVersionUID = 1L;

	public JacksonCustomBookDeserializer() {
		this(null);
	}

	public JacksonCustomBookDeserializer(Class<Book> t) {
		super(t);
	}

	@Override
	public Book deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		Book book = new Book();

		String type = node.get("type").asText(null);
		BookType bookType = BookType.valueOf(type);
		int id = node.get("id").asInt();
		String title = node.get("title").asText(null);
		String author = node.get("author").asText(null);
		String status = node.get("status").asText(null);

		if (!(id == 0)) {
			book.setId(id);
		}
		book.setTitle(title);
		book.setAuthor(author);
		book.setStatus(status);

		book.setType(bookType);
		return book;
	}

}
