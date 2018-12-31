package com.sprinklr.socialapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sprinklr.socialapp.model.Feed;
import com.sprinklr.socialapp.model.TwitterInfo;
import com.sprinklr.socialapp.service.ITwitterService;

@RestController
public class TwitterController {

	@Autowired
	private ITwitterService twitterService;

	@PostMapping("/tweet")
	public String tweet(@RequestHeader("email") String email, @RequestBody TwitterInfo twitter) {
		try {
			twitterService.tweet(email, twitter.getText());
		} catch (RuntimeException ex) {
			throw ex;
		}
		return twitter.getText() + " -- these text has been tweeted   ..!!";
	}

	@GetMapping("/userTimeline")
	public List<Tweet> getPublicTimeline() {
		List<Tweet> userTimelineTweetList = null;
		try {
	//		userTimelineTweetList = twitterService.getPublicTimeline();
		} catch (RuntimeException ex) {
			throw ex;
		}
		return userTimelineTweetList;
	}
	
	@GetMapping("/twitter/feed")
	public List<Feed> getTwitterFeed() {
		List<Feed> twitterFeedList = null;
		try {
			twitterFeedList = twitterService.getTwitterFeed();
		} catch (RuntimeException ex) {
			throw ex;
		}
		return twitterFeedList;
	}

}
