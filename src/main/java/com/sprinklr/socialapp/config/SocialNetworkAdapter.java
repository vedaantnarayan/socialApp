package com.sprinklr.socialapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.sprinklr.socialapp.model.UserTokenDetails;

@Component
public class SocialNetworkAdapter {
	
	@Autowired
	private Environment env;

	public Twitter getTwitterTemplate(UserTokenDetails userTokenDetails) {
		String appId=env.getProperty("app1.twitter.app-id");
		String appSecret=env.getProperty("app1.twitter.app-secret");
		Preconditions.checkNotNull(appId);
		Preconditions.checkNotNull(appSecret);
		Preconditions.checkNotNull(userTokenDetails.getAccessToken());
		Preconditions.checkNotNull(userTokenDetails.getAccessTokenSecret());

		TwitterTemplate twitterTemplate = new TwitterTemplate(appId,
				appSecret, userTokenDetails.getAccessToken(),
				userTokenDetails.getAccessTokenSecret());
		return twitterTemplate;
	}

	public Facebook getFacebookTemplate(UserTokenDetails userTokenDetails) {

		Preconditions.checkNotNull(userTokenDetails.getAccessToken());

		return new FacebookTemplate(userTokenDetails.getAccessToken());
	}
}