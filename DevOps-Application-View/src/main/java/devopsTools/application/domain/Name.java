package devopsTools.application.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@ToString(includeFieldNames = true)
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
