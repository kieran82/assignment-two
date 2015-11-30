package com.assessmentOne.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

//Java Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.Entities;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.assessmentOne.entity.Artist;
import com.assessmentOne.repository.ArtistRepository;

@Controller
// Path
@RequestMapping("")
public class ArtistController {

	private Twitter twitter;

	private ConnectionRepository connectionRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@Inject
	public ArtistController(Twitter twitter, ConnectionRepository connectionRepository) {
	        this.twitter = twitter;
	        this.connectionRepository = connectionRepository;
	    }

	@RequestMapping(value = { "/", "/artist" })
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

		// Get all Artists from Repository
		Iterable<Artist> artists = artistRepository.findAll();

		// Add Attribute to Model for Template
		model.addAttribute("artists", artists);
		// Go to Index html in the artist folder
		return "artist/index";
	}

	/*
	 * String viewByKey
	 * 
	 * @param Integer id
	 * 
	 * @param Object Model
	 */
	// Path
	@RequestMapping("artist/{id}")
	public String viewByKey(@PathVariable int id, Model model) {

		String username = ""; // set username
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// If instance exist of userDetails
		if (principal instanceof UserDetails) {
			// Getusername
			username = ((UserDetails) principal).getUsername();
			// Get Role
			role = ((UserDetails) principal).getAuthorities().toString();

		} else {
			// set Username
			username = "none";
			// set Role
			role = "none";

		}
		// Add Attribute to Model for Template
		model.addAttribute("username", username);

		// Get Artist by ID from Repository
		Artist artists = artistRepository.findById(id);
		// Add Attribute to Model for Template
		model.addAttribute("artist", artists);
		
		

		List<Tweet> twitter2 = twitter.searchOperations().search(artists.getFullName() + " art ", 3).getTweets();
		model.addAttribute("twitter2", twitter2);
	

		// Go to View html in the artist folder
		return "artist/view";
	}

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
		ModelAndView mav = new ModelAndView("error");

		mav.addObject("datetime", new Date());
		mav.addObject("exception", e);
		mav.addObject("url", request.getRequestURL());
		return mav;
	}
}
