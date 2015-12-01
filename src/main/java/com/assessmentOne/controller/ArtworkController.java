package com.assessmentOne.controller;

import java.util.List;
//Java Imports
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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



	@Inject
	public ArtworkController(Twitter twitter) {
		this.twitter = twitter;

	}

	@RequestMapping("")
	public String index(Model model) {

		String username = ""; // set username
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// If instance exist of userDetails
		if (principal instanceof UserDetails) {

			username = ((UserDetails) principal).getUsername();
			role = ((UserDetails) principal).getAuthorities().toString();

		} else {
			username = "none";
			role = "none";
		}

		// Add Attribute to Model for Template
		model.addAttribute("username", username);
		Iterable<Artwork> artworks = artworkRepository.findAll();
		model.addAttribute("artworks", artworks);
		return "artwork/index";
	}

	@RequestMapping(value = { "/{id}", "/" }, method = RequestMethod.GET)
	String singleArtwork(Locale locale, Model model, @PathVariable("id") String id) {

		Artwork artworks = artworkRepository.findById(Integer.parseInt(id));

		model.addAttribute("artworks", artworks);

		Iterable<Comment> comments = artworkRepository.getComments(id);

		model.addAttribute("comments", comments);

		String username = "";
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {

			username = ((UserDetails) principal).getUsername();

			role = ((UserDetails) principal).getAuthorities().toString();
		} else {
			username = "none";
			role = "none";
		}

		List<Tweet> twitter2 = twitter.searchOperations().search(artworks.getTitle() + " art ", 3).getTweets();
		model.addAttribute("twitter2", twitter2);
		System.out.println(twitter2);

		model.addAttribute("username", username);

		return "artwork/view";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String commentSubmit(@ModelAttribute Comment comment, Model model, @PathVariable("id") String id) {

		String username = ""; // set username
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			role = ((UserDetails) principal).getAuthorities().toString();
		} else {

			username = ((UserDetails) principal).getUsername();

			role = ((UserDetails) principal).getAuthorities().toString();
		}

		model.addAttribute("username", username);

		Comment newComments = new Comment();
				newComments.setUsername(comment.getUsername());
				newComments.setArtworkId(id);
				newComments.setContent(comment.getContent());
				newComments.setTime(comment.getTime());

		artworkRepository.addComment(newComments);

		Artwork artworks = artworkRepository.findById(Integer.parseInt(id));
			model.addAttribute("artworks", artworks);
		Iterable<Comment> comments = artworkRepository.getComments(id);
			model.addAttribute("comments", comments);

		return "artwork/view";
	}
}
