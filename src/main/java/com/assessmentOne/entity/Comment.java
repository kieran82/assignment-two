package com.assessmentOne.entity;

public class Comment {

	private String username;
	private String artworkId;
	private String content;
	private String time;

	public Comment() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getArtworkId() {
		return artworkId;
	}

	public void setArtworkId(String artworkId) {
		this.artworkId = artworkId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}