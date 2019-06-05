package devopsTools.application.domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Customer {

	private int id;

	@NonNull
	private Name name;
	@NonNull
	private Gender gender;

	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dob;

	@NonNull
	private Address address;

	@ToString()
	public enum Gender {
		MALE, FEMALE
	}

	@JsonIgnore
	public double getAge() {
		LocalDate today = LocalDate.now();
		Period p = Period.between(this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), today);
		return p.getYears();
	}

}
