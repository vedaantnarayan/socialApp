package com.sprinklr.socialapp.service;

import java.util.List;

import org.springframework.social.twitter.api.Tweet;

import com.sprinklr.socialapp.model.Feed;

public interface ITwitterService {
	
	public void tweet(String email, String tweetText);
	public List<Tweet> getPublicTimeline();
	List<Feed> getTwitterFeed() ;

}
