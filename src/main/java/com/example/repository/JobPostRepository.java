package com.example.repository;

import com.example.models.JobPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends MongoRepository<JobPost,String>, QuerydslPredicateExecutor<JobPost> {

    public List<JobPost> findByPosterEmail(String email);

}
