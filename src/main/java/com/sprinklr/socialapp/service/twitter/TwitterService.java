package com.sprinklr.socialapp.service.twitter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.sprinklr.socialapp.config.twitter.TwitterTemplateCreator;

@Service
public class TwitterService {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private TwitterTemplateCreator twitterCreator;


	public void tweet(String userName, String tweetText) {
		try {
			
			getTwitterTemplate(userName).timelineOperations().updateStatus(tweetText);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	public List<Tweet> getPublicTimeline(String userName) {
		List<Tweet> userTimelineTweetList = null;
		try {
			userTimelineTweetList = getTwitterTemplate(userName).timelineOperations().getUserTimeline();
		} catch (RuntimeException ex) {
			LOGGER.error("Unable fetch user tweet", ex);
		}
		return userTimelineTweetList;
	}
	
	public Twitter getTwitterTemplate(String accountName) {
		return twitterCreator.getTwitterTemplate(accountName);
		
	}
}