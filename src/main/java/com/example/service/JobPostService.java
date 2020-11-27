package com.example.service;

import com.example.models.JobPost;
import com.querydsl.core.types.Predicate;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

public interface JobPostService {

     JobPost addJobPost(JobPost jobPost);
     List<JobPost> filter(String city,String skill,String company,String exp,String sal);
     List<JobPost> findMostRecentAddedJobs(int size);
     boolean sendMail(MultipartFile multipartFile,String jobId) throws MessagingException;
     List<JobPost> getByEmail(String email);
     JobPost getById(String id);
}
