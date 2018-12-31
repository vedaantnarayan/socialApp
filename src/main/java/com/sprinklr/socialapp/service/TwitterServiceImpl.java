package com.sprinklr.socialapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
public class TwitterServiceImpl implements ITwitterService {
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
						saveTweet(tweetText, user, userTokenDetails.getUserName());
					}
				}
			}
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	private void saveTweet(String tweetText, User user, String userName) {

		Feed feed = new Feed();
		feed.setFeedText(tweetText);
		feed.setFeedDate(Calendar.getInstance().getTime());
		feed.setFeedType(HttpMethod.POST.name());
		feed.setUserId(user.getUserId());
		feed.setSource(ApplicationConstant.SocialNetworkType.TWITTER.name());
		feed.setFeedBy(userName);
		feedRepository.save(feed);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Tweet> getPublicTimeline() {
		List<Tweet> userTimelineTweetList = null;
		try {

			List<User> userList = userRepository.findAll();
			if (userList != null && !userList.isEmpty()) {
				ExecutorService es = Executors.newFixedThreadPool(5);
				Map<String, Future<List<Tweet>>> tweetMap = new HashMap<>();
				for (User user : userList) {
					if (user.getUserTokenDetails() != null && !user.getUserTokenDetails().isEmpty()) {
						for (UserTokenDetails userTokenDetails : user.getUserTokenDetails()) {
							if (ApplicationConstant.SocialNetworkType.TWITTER.name()
									.equalsIgnoreCase(userTokenDetails.getSource())) {

								Future<List<Tweet>> future = es.submit(new Callable() {
									public List<Tweet> call() throws Exception {
										return getTwitterTemplate(userTokenDetails).timelineOperations()
												.getUserTimeline();
									}
								});
								tweetMap.put(user.getUserId(), future);

							}
						}
					}
				}
				es.shutdown();
				for (Entry<String, Future<List<Tweet>>> futureTweetMap : tweetMap.entrySet()) {
					try {
						persistTwitterPost(futureTweetMap.getValue().get(), HttpMethod.GET.name(),
								futureTweetMap.getKey());
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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