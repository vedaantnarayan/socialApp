package com.sprinklr.socialapp.config;

import org.springframework.social.twitter.api.Twitter;

import com.sprinklr.socialapp.model.UserTokenDetails;

public interface ITwitterConnectionFactory {

	public Twitter getTwitterTemplate(UserTokenDetails userTokenDetails);

}
