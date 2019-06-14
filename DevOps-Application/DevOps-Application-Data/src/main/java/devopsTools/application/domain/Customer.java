package devopsTools.application.domain;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.AccessLevel;

@Data
@RequiredArgsConstructor
// Access need to be protected or private
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(includeFieldNames = true)
@EqualsAndHashCode
// <data>
@Entity
// </data>
public class Customer extends DomainBase{

	// <data>
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	// </data>
	private long id;

	// <data>
	@Embedded
	// </data>
	@NotNull
	private Name name;
	@NonNull
	private Gender gender;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	// need to fix dates
	@Past(message = "Date of Birth cannot be in the future")
	private LocalDate dob;

	// <data>
	@Embedded
	// </data>
	@NotNull
	private Address address;

	@AllArgsConstructor
	public enum Gender {
		MALE("Male"), FEMALE("Female");

		@JsonValue
		private String name;

		@Override
		public String toString() {
			return name;
		}
	}

	@JsonIgnore
	public int getAge() {
		LocalDate today = LocalDate.now();		
		return Period.between(this.dob, today).getYears();
	}


}
