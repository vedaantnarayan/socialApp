package com.sprinklr.socialapp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sprinklr.socialapp.config.SocialNetworkAdapter;
import com.sprinklr.socialapp.model.Feed;
import com.sprinklr.socialapp.model.User;
import com.sprinklr.socialapp.model.UserTokenDetails;
import com.sprinklr.socialapp.repository.FeedRepository;
import com.sprinklr.socialapp.repository.UserRepository;
import com.sprinklr.socialapp.utility.ApplicationConstant;

@Service
public class TwitterService {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private SocialNetworkAdapter socialNetworkAdapter;

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private UserRepository userRepository;

	public void tweet(String email, String tweetText) {
		try {
			User user = userRepository.findByEmail(email);
			if (user != null && user.getUserTokenDetails() != null && !user.getUserTokenDetails().isEmpty()) {
				for (UserTokenDetails userTokenDetails : user.getUserTokenDetails()) {
					if (ApplicationConstant.SocialNetworkType.TWITTER.name()
							.equalsIgnoreCase(userTokenDetails.getSource())) {
						getTwitterTemplate(userTokenDetails).timelineOperations().updateStatus(tweetText);
						saveTweet(tweetText, null);
					}
				}
			}
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private void saveTweet(String tweetText, User user) {
		// TODO Post tweet will get persist into mongo.
	}

	public List<Tweet> getPublicTimeline(String userName) {
		List<Tweet> userTimelineTweetList = null;
		try {

			List<User> userList = userRepository.findAll();
			if (userList != null && !userList.isEmpty()) {
				for (User user : userList) {
					if (user.getUserTokenDetails() != null && !user.getUserTokenDetails().isEmpty()) {
						for (UserTokenDetails userTokenDetails : user.getUserTokenDetails()) {
							if (ApplicationConstant.SocialNetworkType.TWITTER.name()
									.equalsIgnoreCase(userTokenDetails.getSource())) {
								userTimelineTweetList = getTwitterTemplate(userTokenDetails).timelineOperations()
										.getUserTimeline();
								persistTwitterPost(userTimelineTweetList, HttpMethod.GET.name(), user.getUserId());
							}
						}
					}
				}
			}

		} catch (RuntimeException ex) {
			LOGGER.error("Unable fetch user tweet", ex);
		}
		return userTimelineTweetList;
	}

	public List<Feed> getTwitterFeed() {
		List<Feed> twitterFeedList = null;
		try {
			twitterFeedList = feedRepository.findBySource(ApplicationConstant.SocialNetworkType.TWITTER.name());
		} catch (RuntimeException ex) {
			LOGGER.error("Unable fetch user tweet", ex);
		}
		return twitterFeedList;
	}

	public Twitter getTwitterTemplate(UserTokenDetails userTokenDetails) {
		return socialNetworkAdapter.getTwitterTemplate(userTokenDetails);

	}

	private void persistTwitterPost(List<Tweet> tweetList, String feedType, String userId) {

		try {
			if (tweetList != null && !tweetList.isEmpty()) {
				List<Feed> mongoFeedList = new ArrayList<>();
				for (Tweet tweet : tweetList) {
					Feed feed = new Feed();
					feed.setFeedText(tweet.getText());
					feed.setFeedDate(tweet.getCreatedAt());
					feed.setFeedType(feedType);
					feed.setUserId(userId);
					feed.setSource(ApplicationConstant.SocialNetworkType.TWITTER.name());
					feed.setFeedBy(tweet.getFromUser());
					String feedDetails = new Gson().toJson(tweet);
					feed.setDetails(feedDetails);
					mongoFeedList.add(feed);
				}
				feedRepository.save(mongoFeedList);
			}
		} catch (Exception ex) {
			LOGGER.error("Unable Persist Twiiter Post to Mongo", ex);
		}
	}

}