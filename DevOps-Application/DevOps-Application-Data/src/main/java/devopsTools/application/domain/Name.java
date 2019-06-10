package devopsTools.application.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
//<data>
@Embeddable
//</data>
public class Name {
	
	  @NotNull
	  @Size(min=1, message="Name must be at least 5 characters long")
	  private String firstName;

	  private String middleName;
	  
	  @NotNull
	  @Size(min=5, message="Name must be at least 5 characters long")
	  private String lastName;
	  
	  private String preferredName;
	
}
