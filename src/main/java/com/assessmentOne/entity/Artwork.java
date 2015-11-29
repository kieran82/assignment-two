
package com.assessmentOne.entity;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "artwork")
public class Artwork {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "group_title")
	private String groupTitle;
	
	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

	@Column(name = "title")
	private String title;

	@JsonProperty("movements")
	@ManyToMany(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinTable(name = "artwork_movements", joinColumns = {
			@JoinColumn(name = "artwork_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "movement_id", referencedColumnName = "id") })
	public List<Movement> movements;

	@JsonProperty("contributors")
	@ManyToMany(fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<Contributors> contributors;

	public Artwork() {
		movements = Collections.<Movement> emptyList();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public String toString() {
		String out = "Artwork [id=" + id + ", Title=" + getTitle() + ", Group Titleo=" + getGroupTitle() + " Finberg: "
				+ " ThumbNail = : " + getThumbnailUrl();

		for (Movement m : movements) {
			out += m.toString() + "";
		}

		out += "]]";
		return out;

	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Movement> getMovements() {
		return movements;
	}

	public void setContributors(List<Contributors> contributors) {
		this.contributors = contributors;
	}

	public List<Contributors> getContributors() {
		return contributors;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

}
