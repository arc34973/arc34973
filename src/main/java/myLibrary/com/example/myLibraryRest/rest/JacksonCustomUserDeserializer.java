package myLibrary.com.example.myLibraryRest.rest;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import myLibrary.com.example.myLibraryRest.model.User;

public class JacksonCustomUserDeserializer extends StdDeserializer<User> {

	private static final long serialVersionUID = 1L;

	public JacksonCustomUserDeserializer() {
		this(null);
	}

	public JacksonCustomUserDeserializer(Class<User> t) {
		super(t);
	}

	@Override
	public User deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		User user = new User();
		int id = node.get("id").asInt();
		String firstName = node.get("firstName").asText(null);
		String lastName = node.get("lastName").asText(null);
		String password = node.get("password").asText(null);
		String userName = node.get("userName").asText(null);
		String address = node.get("address").asText(null);
		String role = node.get("role").asText(null);
		String status = node.get("status").asText(null);

		if (!(id == 0)) {
			user.setId(id);
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setUserName(userName);
		user.setAddress(address);
		user.setRole(role);
		user.setStatus(status);
		// user.setBorrowHistories(borrowHistories);
		return user;
	}

}
