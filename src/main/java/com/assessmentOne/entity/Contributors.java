package com.assessmentOne.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Contributors {

	@Id
	@JsonProperty("id")
	private int artist_id;

	@JsonProperty("birthYear")
	@NotFound(action = NotFoundAction.IGNORE)
	private int birth_year;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private String name;

	public int getArtist_id() {
		return artist_id;
	}

	@Override
	public String toString() {
		return "Contributors [id=" + getArtist_id() + "   Name " + getName() + "]";
	}

	public void setArtist_id(int artist_id) {
		this.artist_id = artist_id;
	}

	public String getName() {
		return name;

	}

	public void setName(String name) {
		this.name = name;

	}

	public int getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}

}