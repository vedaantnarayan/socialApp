package com.sprinklr.socialapp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import com.sprinklr.socialapp.model.User;
import com.sprinklr.socialapp.model.UserTokenDetails;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sprinklr.socialapp.config.SocialNetworkAdapter;
import com.sprinklr.socialapp.model.Feed;
import com.sprinklr.socialapp.repository.FeedRepository;
import com.sprinklr.socialapp.repository.UserRepository;
import com.sprinklr.socialapp.utility.ApplicationConstant;

@Service
public class FacebookServiceImpl implements IFacebookService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private SocialNetworkAdapter socialNetworkAdapter;

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private UserRepository userRepository;

	public Facebook getFacebookTemplate(UserTokenDetails userTokenDetails) {
		return socialNetworkAdapter.getFacebookTemplate(userTokenDetails);

	}

	public User getUserProfile(String userName) {
		try {

		//	return getFacebookTemplate(userName).fetchObject("me", User.class, ApplicationConstant.FIELDS);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return null;
	}

	public void doPost(String email, String postText) {
		try {
			

			User user = userRepository.findByEmail(email);
			if (user != null && user.getUserTokenDetails() != null && !user.getUserTokenDetails().isEmpty()) {
				for (UserTokenDetails userTokenDetails : user.getUserTokenDetails()) {
					if (ApplicationConstant.SocialNetworkType.FACEBOOK.name()
							.equalsIgnoreCase(userTokenDetails.getSource())) {
						getFacebookTemplate(userTokenDetails).feedOperations().updateStatus(postText);
						saveFBPost(postText, null);
					}
				}
			}
		} catch (RuntimeException ex) {
			throw ex;
		}

	}

	private void saveFBPost(String postText, User user) {

		// TODO Post FB Post will get persist into mongo.
	}

	public List<Post> getPublicTimeline() {
		List<Post> post = null;
		try {

			List<User> userList = userRepository.findAll();
			if (userList != null && !userList.isEmpty()) {
				for (User user : userList) {
					if (user.getUserTokenDetails() != null && !user.getUserTokenDetails().isEmpty()) {
						for (UserTokenDetails userTokenDetails : user.getUserTokenDetails()) {
							if (ApplicationConstant.SocialNetworkType.FACEBOOK.name()
									.equalsIgnoreCase(userTokenDetails.getSource())) {
								post = getFacebookTemplate(userTokenDetails).feedOperations().getFeed();
								persistFBPost(post, HttpMethod.GET.name(),user.getUserId());
							}
						}
					}
				}
			}
		} catch (RuntimeException ex) {
			LOGGER.error("Unable fetch user FB Post", ex);
		}
		return post;
	}

	private void persistFBPost(List<Post> postList, String feedType,String userId) {

		try {
			if (postList != null && !postList.isEmpty()) {
				List<Feed> mongoFeedList = new ArrayList<>();
				for (Post post : postList) {
					Feed feed = new Feed();
					feed.setFeedText(post.getMessage());
					feed.setFeedDate(post.getCreatedTime());
					feed.setFeedType(feedType);
					feed.setUserId(userId);
					feed.setSource(ApplicationConstant.SocialNetworkType.FACEBOOK.name());
					feed.setFeedBy(post.getFrom().getName());
					String feedDetails = new Gson().toJson(post);
					feed.setDetails(feedDetails);
					mongoFeedList.add(feed);
				}
				feedRepository.save(mongoFeedList);
			}
		} catch (Exception ex) {
			LOGGER.error("Unable Persist FB Post to Mongo", ex);
		}
	}

	public List<Feed> getFbFeed() {
		List<Feed> fbFeedList = null;
		try {
			fbFeedList = feedRepository.findBySource(ApplicationConstant.SocialNetworkType.FACEBOOK.name());
		} catch (RuntimeException ex) {
			LOGGER.error("Unable fetch FB feed", ex);
		}
		return fbFeedList;
	}

}