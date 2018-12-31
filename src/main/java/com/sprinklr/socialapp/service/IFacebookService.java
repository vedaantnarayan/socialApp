package com.sprinklr.socialapp.service;

import java.util.List;

import org.springframework.social.facebook.api.Post;

import com.sprinklr.socialapp.model.Feed;
import com.sprinklr.socialapp.model.User;

public interface IFacebookService {

	public User getUserProfile(String userName);

	public void doPost(String email, String postText);

	public List<Post> getPublicTimeline();

	public List<Feed> getFbFeed();

}
