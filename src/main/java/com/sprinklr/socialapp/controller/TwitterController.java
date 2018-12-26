package com.sprinklr.socialapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sprinklr.socialapp.model.Twitter;
import com.sprinklr.socialapp.service.twitter.TwitterService;

@RestController
public class TwitterController {

	@Autowired
	private TwitterService twitterService;

	@PostMapping("/tweet")
	public String tweet(@RequestHeader("User-Name") String userName, @RequestBody Twitter twitter) {
		try {
			twitterService.tweet(userName, twitter.getText());
		} catch (RuntimeException ex) {
			throw ex;
		}
		return twitter.getText() + " -- these text has been tweeted   ..!!";
	}

	@GetMapping("/userTimeline")
	public List<Tweet> getPublicTimeline(@RequestHeader("User-Name") String userName) {
		List<Tweet> userTimelineTweetList = null;
		try {
			userTimelineTweetList = twitterService.getPublicTimeline(userName);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return userTimelineTweetList;
	}

}
