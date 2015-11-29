package com.assessmentOne.repository;

//Java Imports
import java.util.List;

import com.assessmentOne.entity.Artist;
import com.assessmentOne.entity.Movement;

public interface ArtistRepository {
	/*
	 * Artist findById
	 * 
	 * @param Integer id
	 */
	Artist findById(int id);

	/*
	 * Artist findAll
	 * 
	 * @return Arraylist Artist
	 */
	List<Artist> findAll();

	/*
	 * Save
	 * 
	 * @param Artist artist
	 */
	void save(Artist artist);

	/*
	 * saveMovement
	 * 
	 * @param Arraylist Movement
	 * 
	 * @param Integer artistId
	 */
	void saveMovement(List<Movement> movements, int artistId);
}