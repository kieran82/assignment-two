package com.assessmentOne.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
// table name
@Table(name = "artists")
public class Artist {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "full_name")
	@JsonProperty("fc")
	private String fullName;

	@Column(name = "gender")
	private String gender = "unknown";

	@ManyToMany(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinTable(name = "artist_artwork", joinColumns = {
			@JoinColumn(name = "artist_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "artwork_id", referencedColumnName = "id") })
	public List<Artwork> artworks;

	@JsonProperty("movements")
	@ManyToMany(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinTable(name = "artist_movements", joinColumns = {
			@JoinColumn(name = "artist_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "movement_id", referencedColumnName = "id") })
	public List<Movement> movements;

	@JsonProperty("birth")
	@JsonIgnoreProperties(ignoreUnknown = true)
	@OneToOne(cascade = { CascadeType.ALL })
	@NotFound(action = NotFoundAction.IGNORE)
	public Birth birth;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Artwork> getArtworks() {
		return artworks;
	}

	public void setArtworks(List<Artwork> artworks) {
		this.artworks = artworks;
	}

	public Birth getBirth() {

		return birth;

	}

	public void setBirth(Birth birth) {

		this.birth = birth;
	}

	public List<Movement> getMovements() {
		return movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}
}