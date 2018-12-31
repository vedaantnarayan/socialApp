package com.sprinklr.socialapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sprinklr.socialapp.model.FacebookInfo;
import com.sprinklr.socialapp.model.Feed;
import com.sprinklr.socialapp.service.IFacebookService;

@RestController
public class FacebookController {

	@Autowired
	private IFacebookService facebookService;

	@PostMapping("/fb/post")
	public String doPost(@RequestHeader("email") String email, @RequestBody FacebookInfo facebookInfo) {
		try {
			facebookService.doPost(email, facebookInfo.getText());
		} catch (RuntimeException ex) {
			throw ex;
		}
		return facebookInfo.getText() + " -- these text has been posted on FB   ..!!";
	}

	@GetMapping("/fb/userProfile")
	public User getUserProfile(@RequestHeader("email") String email) {
		try {
		//	return facebookService.getUserProfile(email);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return null;
	}
	
	@GetMapping("/fb/publicTimeline")
	public List<Post> getPublicTimeline(@RequestHeader("User-Name") String userName) {
		try {
			return facebookService.getPublicTimeline();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}
	
	@GetMapping("/fb/feed")
	public List<Feed> getFBFeed() {
		List<Feed> fbFeedList = null;
		try {
			fbFeedList = facebookService.getFbFeed();
		} catch (RuntimeException ex) {
			throw ex;
		}
		return fbFeedList;
	}


}
