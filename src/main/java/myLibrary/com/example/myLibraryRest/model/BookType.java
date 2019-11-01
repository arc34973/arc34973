package myLibrary.com.example.myLibraryRest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



@JsonSerialize
@JsonDeserialize
public enum BookType {
	COMPUTER, MATHEMATIK, BWL, VWL
}
