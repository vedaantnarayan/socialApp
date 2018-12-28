package com.sprinklr.socialapp.config;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.sprinklr.socialapp.model.UserTokenDetails;

@Component
public class SocialNetworkAdapter {

	public Twitter getTwitterTemplate(UserTokenDetails userTokenDetails) {
		Preconditions.checkNotNull(userTokenDetails.getAppId());
		Preconditions.checkNotNull(userTokenDetails.getAppSecret());
		Preconditions.checkNotNull(userTokenDetails.getAccessToken());
		Preconditions.checkNotNull(userTokenDetails.getAccessTokenSecret());

		TwitterTemplate twitterTemplate = new TwitterTemplate(userTokenDetails.getAppId(),
				userTokenDetails.getAppSecret(), userTokenDetails.getAccessToken(),
				userTokenDetails.getAccessTokenSecret());
		return twitterTemplate;
	}

	public Facebook getFacebookTemplate(UserTokenDetails userTokenDetails) {

		Preconditions.checkNotNull(userTokenDetails.getAccessToken());

		return new FacebookTemplate(userTokenDetails.getAccessToken());
	}
}