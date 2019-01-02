package com.sprinklr.socialapp.config;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;

import com.sprinklr.socialapp.model.UserTokenDetails;

public interface IFacebookConnectionFactory {

	public Facebook getFacebookTemplate(UserTokenDetails userTokenDetails);
}
