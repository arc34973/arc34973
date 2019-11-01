package myLibrary.com.example.myLibraryRest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "types")
public class BookType_OLD extends BaseEntity {
	//typeId,typeName
	
	@Column(name = "type_name")
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	

}
