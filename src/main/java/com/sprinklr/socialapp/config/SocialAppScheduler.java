package com.sprinklr.socialapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sprinklr.socialapp.service.IFacebookService;
import com.sprinklr.socialapp.service.ITwitterService;

@Component
public class SocialAppScheduler {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITwitterService twitterService;

	@Autowired
	private IFacebookService facebookService;

	//@Scheduled(cron = "0 * * ? * *")
	public void getSocialFeed() {

		LOGGER.info("=========== Synchronizing Twitter Feed Started ============");

		twitterService.getPublicTimeline();

		LOGGER.info("=========== Synchronizing Twitter Feed Ended ============");

		LOGGER.info("=========== Synchronizing Facebook Feed Started ============");

		facebookService.getPublicTimeline();

		LOGGER.info("=========== Synchronizing Facebook Feed Ended ============");

		LOGGER.info("Schedular Executed task at - " + System.currentTimeMillis() / 1000);

	}

}
