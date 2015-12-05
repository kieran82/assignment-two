package com.assessmentOne.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

//Java Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
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

	@Autowired
	private ArtistRepository artistRepository;

	@Inject
	public ArtistController(Twitter twitter) {
		this.twitter = twitter;

	}

	@RequestMapping(value = { "/", "/artist" })
	public String index(Model model) {

		String username = ""; // set username
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {

			username = ((UserDetails) principal).getUsername();

			role = ((UserDetails) principal).getAuthorities().toString();
		} else {

			username = "none";

			role = "none";
		}
		model.addAttribute("username", username);

		Iterable<Artist> artists = artistRepository.findAll();

		model.addAttribute("artists", artists);
		return "artist/index";
	}

	@RequestMapping("artist/{id}")
	public String viewByKey(@PathVariable int id, Model model) {

		String username = ""; // set username
		String role; // This is fix cause of a bitch of bug!!!!!!!
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {

			username = ((UserDetails) principal).getUsername();

			role = ((UserDetails) principal).getAuthorities().toString();

		} else {

			username = "none";

			role = "none";

		}

		Artist artists = artistRepository.findById(id);
		model.addAttribute("artist", artists);
		model.addAttribute("username", username);

		List<Tweet> tweets = null;
		tweets = twitter.searchOperations().search(artists.getFullName() + " art ", 3).getTweets();

		if (tweets.isEmpty()) {
			tweets = twitter.searchOperations().search("tate gallery " , 3).getTweets();
		}

		model.addAttribute("tweets", tweets);

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
