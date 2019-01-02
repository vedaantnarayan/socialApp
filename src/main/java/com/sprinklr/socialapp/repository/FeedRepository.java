package com.sprinklr.socialapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sprinklr.socialapp.model.Feed;

@Repository
public interface FeedRepository extends MongoRepository<Feed, String> {

	List<Feed> findBySource(String twitter);
}