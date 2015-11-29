package com.assessmentOne.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Place {

	@Id
	@JsonProperty("name")
	@Value("test")
	@JsonIgnoreProperties(ignoreUnknown = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private String name;

	@JsonProperty("placeName")
	@Value("test")
	@JsonIgnoreProperties(ignoreUnknown = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private String placeName;

	@JsonProperty("placeType")
	@Value("test")
	@JsonIgnoreProperties(ignoreUnknown = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private String placeType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	@Override
	public String toString() {
		return name + " " + placeName + " -- " + placeType;
	}

}
