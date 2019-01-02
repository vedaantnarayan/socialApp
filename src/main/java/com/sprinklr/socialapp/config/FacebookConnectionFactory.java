package com.sprinklr.socialapp.config;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.sprinklr.socialapp.model.UserTokenDetails;

@Component
public class FacebookConnectionFactory implements IFacebookConnectionFactory {
	
	public Facebook getFacebookTemplate(UserTokenDetails userTokenDetails) {

		Preconditions.checkNotNull(userTokenDetails.getAccessToken());

		return new FacebookTemplate(userTokenDetails.getAccessToken());
	}
}