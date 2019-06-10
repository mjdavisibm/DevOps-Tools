package com.devopstools.application.domain;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
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

	private long id;

	@NonNull
	private Name name;
	@NonNull
	private Gender gender;

	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dob;

	@NonNull
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
	public double getAge() {
		LocalDate today = LocalDate.now();
		Period p = Period.between(this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), today);
		return p.getYears();
	}
	
	/*
	 * Uses a pretty printer mechanism to send back JSON for this object
	 */
	public String toPrettyPrintJson() {
		String prettyJson = this.toString();
		try {
			ObjectMapper mapper = new ObjectMapper();
			 prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prettyJson;
	}

}
