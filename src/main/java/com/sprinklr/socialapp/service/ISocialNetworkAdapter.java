package com.sprinklr.socialapp.service;

import java.util.List;

import com.sprinklr.socialapp.model.Feed;
import com.sprinklr.socialapp.model.User;

public interface ISocialNetworkAdapter {

	public User getUserProfile(String userName);

	public void doPost(String email, String postText);

	public List<Feed> getPost();

}
