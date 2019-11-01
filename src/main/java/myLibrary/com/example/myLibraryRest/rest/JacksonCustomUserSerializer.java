package myLibrary.com.example.myLibraryRest.rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import myLibrary.com.example.myLibraryRest.model.User;

public class JacksonCustomUserSerializer extends StdSerializer<User> {

	private static final long serialVersionUID = 1L;

	public JacksonCustomUserSerializer() {
		this(null);
	}

	public JacksonCustomUserSerializer(Class<User> t) {
		super(t);
	}

	@Override
	public void serialize(User user, JsonGenerator jgen, SerializerProvider provider) throws IOException {

		jgen.writeStartObject();
		if (user.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", user.getId());
		}

		jgen.writeStringField("firstName", user.getFirstName());
		jgen.writeStringField("lastName", user.getLastName());
		jgen.writeStringField("address", user.getAddress());
		jgen.writeStringField("role", user.getRole());
		jgen.writeStringField("status", user.getStatus());
		jgen.writeStringField("password", user.getPassword());
		jgen.writeStringField("userName", user.getUserName());

		jgen.writeEndObject(); // user

	}
}
