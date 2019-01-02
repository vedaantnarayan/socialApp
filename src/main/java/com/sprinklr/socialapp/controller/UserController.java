package com.sprinklr.socialapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sprinklr.socialapp.model.User;
import com.sprinklr.socialapp.repository.UserDAL;
import com.sprinklr.socialapp.repository.UserRepository;

@RestController
@RequestMapping(value = "/")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	private final UserDAL userDAL;

	public UserController(UserRepository userRepository, UserDAL userDAL) {
		this.userRepository = userRepository;
		this.userDAL = userDAL;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public User addNewUsers(@RequestBody User user) {
		LOG.info("Saving user.");
		return userRepository.save(user);
	}

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		LOG.info("Getting all users.");
		return userRepository.findAll();
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable String userId) {
		LOG.info("Getting user with ID: {}.", userId);
		return userRepository.findOne(userId);
	}

	/*
	 * @RequestMapping(value = "/settings/{userId}", method = RequestMethod.GET)
	 * public Object getAllUserSettings(@PathVariable String userId) { User user =
	 * userRepository.findOne(userId); if (user != null) { return
	 * userDAL.getAllUserSettings(userId); } else { return "User not found."; } }
	 * 
	 * @RequestMapping(value = "/settings/{userId}/{key}", method =
	 * RequestMethod.GET) public String getUserSetting(@PathVariable String
	 * userId, @PathVariable String key) { return userDAL.getUserSetting(userId,
	 * key); }
	 */
}