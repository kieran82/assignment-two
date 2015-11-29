package com.assessmentOne.repository;

//Java Imports
import java.util.List;

import com.assessmentOne.entity.Artwork;
import com.assessmentOne.entity.Comment;
import com.assessmentOne.entity.Contributors;
import com.assessmentOne.entity.Movement;

public interface ArtworkRepository {
	/*
	 * Artwork findById
	 * 
	 * @param Integer id
	 */
	Artwork findById(int id);

	/*
	 * Artwork findAll
	 * 
	 * @return Arraylist Artwork
	 */
	List<Artwork> findAll();

	/*
	 * Save
	 * 
	 * @param Artwork Artwork
	 */
	void save(Artwork artwork);

	/*
	 * saveMovement
	 * 
	 * @param Arraylist Movement
	 * 
	 * @param Integer ArtworkId
	 */
	void saveMovement(List<Movement> movements, int artworkid);

	/*
	 * saveContributors
	 * 
	 * @param Arraylist Contributors
	 * 
	 * @param Integer id
	 */
	void saveContributors(List<Contributors> contributors, int id);

	/*
	 * addComment
	 * 
	 * @param Object Comment
	 */
	void addComment(Comment comment);

	/*
	 * Comment getComments
	 * 
	 * @param Integer id
	 * 
	 * @return Arraylist Comment
	 */
	List<Comment> getComments(String id);
}
