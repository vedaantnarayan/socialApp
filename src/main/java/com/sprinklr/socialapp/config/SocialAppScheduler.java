package com.sprinklr.socialapp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sprinklr.socialapp.service.TwitterService;

@Component
public class SocialAppScheduler {

	@Autowired
	private TwitterService twitterService;

	//@Scheduled(cron = "0/20 * * * * ?")
	public void getTweets() {

		List<Tweet> tweetList=twitterService.getPublicTimeline("app1");
				
		System.out.println("Schedular Executed task at - " + System.currentTimeMillis() / 1000);
		
		for (Tweet tweet : tweetList) {
			String json = new Gson().toJson(tweet);
			System.out.println("#################################################################################################");
			System.out.println(json);
			System.out.println("#################################################################################################");

		}
	}

}
