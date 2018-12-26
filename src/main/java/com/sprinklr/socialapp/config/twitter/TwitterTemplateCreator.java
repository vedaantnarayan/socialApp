package com.sprinklr.socialapp.config.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
public class TwitterTemplateCreator {
   @Autowired
   private Environment env;
 
   public Twitter getTwitterTemplate(String accountName) {
      String consumerKey = env.getProperty(accountName + ".twitter.consumer-key");
      String consumerSecret = env.getProperty(accountName + ".twitter.consumer-secret");
      String accessToken = env.getProperty(accountName + ".twitter.access-token");
      String accessTokenSecret = env.getProperty(accountName + ".twitter.access-token-secret");
      Preconditions.checkNotNull(consumerKey);
      Preconditions.checkNotNull(consumerSecret);
      Preconditions.checkNotNull(accessToken);
      Preconditions.checkNotNull(accessTokenSecret);
 
      TwitterTemplate twitterTemplate = 
         new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
      return twitterTemplate;
   }
}