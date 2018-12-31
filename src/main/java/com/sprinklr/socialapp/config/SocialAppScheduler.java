package com.sprinklr.socialapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sprinklr.socialapp.service.IFacebookService;
import com.sprinklr.socialapp.service.ITwitterService;

@Component
public class SocialAppScheduler {

	@Autowired
	private ITwitterService twitterService;

	@Autowired
	private IFacebookService facebookService;

//	@Scheduled(cron = "0 * * ? * *")
	public void getSocialFeed() {

		System.out.println("=========== Synchronizing Twitter Feed Started ============");

		twitterService.getPublicTimeline();

		System.out.println("=========== Synchronizing Twitter Feed Ended ============");

		System.out.println("=========== Synchronizing Facebook Feed Started ============");

	//	facebookService.getPublicTimeline();

		System.out.println("=========== Synchronizing Facebook Feed Ended ============");

		System.out.println("Schedular Executed task at - " + System.currentTimeMillis() / 1000);

	}

}
