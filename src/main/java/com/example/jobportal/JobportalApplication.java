package com.example.jobportal;

import com.example.models.JobPost;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan({"com.example.*"})
@EnableMongoRepositories({"com.example.*"})
public class JobportalApplication {

	public static void main(String[] args) throws JsonProcessingException {

		SpringApplication.run(JobportalApplication.class, args);

		List<String> skills = new ArrayList<>();
		skills.add("Java");
		skills.add("Oracle");
		skills.add("Reactjs");

		JobPost jobPost = new JobPost();
		jobPost.setCity("Pune");
		jobPost.setAddress("Business bay,Vimanager,Pune");
		jobPost.setCompany("Altimetrik");
		jobPost.setExperienceFrom(3);
		jobPost.setExperienceTo(8);
		jobPost.setSalaryFrom(8);
		jobPost.setSalaryTo(20);
		jobPost.setJobDescription("Technically leading the scrum teams in your projects on Microservices and Springboot technologies. Single point of contact for all technical matters and solutioning needed to meet the deliverables assigned to the scrum team and will be responsible for ensuring that technical dependencies are resolved, team is mentored on all technical issues.");
		jobPost.setJobTitle("Senior FullStack Developer");
		jobPost.setNotiePeriod(60);
		jobPost.setPosterName("Nitin Pawar");
		jobPost.setPosterEmail("achievers.nitin@gmail.com");
		jobPost.setSkills(skills);

		System.out.println(new ObjectMapper().writeValueAsString(jobPost));

	}
}
