package com.assessmentOne.controller;

import java.util.List;
//Java Imports
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.assessmentOne.entity.Artwork;
import com.assessmentOne.entity.Comment;
import com.assessmentOne.repository.ArtworkRepository;

@Controller
@RequestMapping("/artwork")
public class ArtworkController {

	@Autowired
	private ArtworkRepository artworkRepository;

	private Twitter twitter;

	private ConnectionRepository connectionRepository;
	
	@Inject
	public ArtworkController(Twitter twitter, ConnectionRepository connectionRepository) {
	        this.twitter = twitter;
	        this.connectionRepository = connectionRepository;
	    }
	
	
	
	
	
	
	
	/*
	 * String Index
	 * 
	 * @param Object Model
	 */
	// Path
	@RequestMapping("")
	public String index(Model model) {

		String username = ""; // set username
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// If instance exist of userDetails
		if (principal instanceof UserDetails) {
			// Get username
			username = ((UserDetails) principal).getUsername();
			// Get role
			role = ((UserDetails) principal).getAuthorities().toString();

		} else {
			// Set username
			username = "none";
			// set Role
			role = "none";
		}
		// Add Attribute to Model for Template
		model.addAttribute("username", username);

		// Get all Artworks from Repository
		Iterable<Artwork> artworks = artworkRepository.findAll();
		// Add Attribute to Model for Template
		model.addAttribute("artworks", artworks);
		// Go to Index html in the artworks folder
		return "artwork/index";
	}

	// path
	
	/*
	 * String singleArtwork
	 * 
	 * @param Object Locale
	 * 
	 * @param Object Model
	 * 
	 * @param String id
	 */
	@RequestMapping(value = { "/{id}", "/" }, method = RequestMethod.GET)
	String singleArtwork(Locale locale, Model model, @PathVariable("id") String id) {
		// Get Artwork by ID from Repository
		Artwork artworks = artworkRepository.findById(Integer.parseInt(id));
		// Add Attribute to Model for Template
		model.addAttribute("artworks", artworks);
		// Get All Comments from Repository
		Iterable<Comment> comments = artworkRepository.getComments(id);
		// Add Attribute to Model for Template
		model.addAttribute("comments", comments);

		String username = ""; // set username
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			// Getusername
			username = ((UserDetails) principal).getUsername();
			// Get Role
			role = ((UserDetails) principal).getAuthorities().toString();
		} else {
			username = "none";
			role = "none";
		}
		
		List<Tweet> twitter2 = twitter.searchOperations().search(artworks.getTitle() + " art ", 3).getTweets();
		model.addAttribute("twitter2", twitter2);

		// Add Attribute to Model for Template
		model.addAttribute("username", username);
		// Go to View html in the artwork folder
		return "artwork/view";
	}

	// Path
	/*
	 * String commentSubmit
	 * 
	 * @param Object Comment
	 * 
	 * @param Object Model
	 * 
	 * @param String id
	 * 
	 * @method POST
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String commentSubmit(@ModelAttribute Comment comment, Model model, @PathVariable("id") String id) {

		String username = ""; // set username
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// If instance exist of userDetails
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			role = ((UserDetails) principal).getAuthorities().toString();
		} else {
			// Getusername
			username = ((UserDetails) principal).getUsername();
			// Get Role
			role = ((UserDetails) principal).getAuthorities().toString();
		}
		// Add Attribute to Model for Template
		model.addAttribute("username", username);
		// set Comment equals new comment
		Comment newComments = new Comment();

		// set username equals comment username
		newComments.setUsername(comment.getUsername());
		// set Artwork id equals comment id
		newComments.setArtworkId(id);
		// set Content equals comment content
		newComments.setContent(comment.getContent());
		// set time equals comment time
		newComments.setTime(comment.getTime());
		// add new comment to repository
		artworkRepository.addComment(newComments);
		// Get Artworks by ID from Repository
		Artwork artworks = artworkRepository.findById(Integer.parseInt(id));
		// Add Attribute to Model for Template
		model.addAttribute("artworks", artworks);
		// Get All Artworks from Repository
		Iterable<Comment> comments = artworkRepository.getComments(id);
		// Add Attribute to Model for Template
		model.addAttribute("comments", comments);
		// Go to View html in the artist folder
		return "artwork/view";
	}
}
