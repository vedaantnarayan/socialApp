package com.sprinklr.socialapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sprinklr.socialapp.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);
}