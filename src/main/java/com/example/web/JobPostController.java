package com.example.web;

import com.example.models.JobPost;
import com.example.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/jobportal/jobpost")
public class JobPostController {

    @Autowired
    JobPostService jobPostService;


    @PostMapping
    public ResponseEntity<JobPost> addJobPOst(@RequestBody JobPost jobPost){
        JobPost jobPost1 = jobPostService.addJobPost(jobPost);
        return new ResponseEntity<>(jobPost1, HttpStatus.OK);
    }
    @GetMapping
    public JobPost getById(@RequestParam("id") String id){
        return jobPostService.getById(id);
    }
    @PostMapping("/search")
    public List<JobPost> searchJobs(@RequestParam("city") String city,
                                    @RequestParam("skill") String skill,
                                    @RequestParam("company") String company,
                                    @RequestParam("exp") String exp,
                                    @RequestParam("sal") String sal){
        return jobPostService.filter(city,skill,company,exp,sal);

    }
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("jobId") String jobId,@RequestParam("selectedFile") MultipartFile file) throws IOException, MessagingException {

        return jobPostService.sendMail(file,jobId)?"success":"failed";
    }

    @GetMapping("/recent")
    public List<JobPost> findMostRecentAddedJobs(@RequestParam("size") int size){
        return jobPostService.findMostRecentAddedJobs(size);
    }

    @GetMapping("/byEmail")
    public List<JobPost> getJobSByEmail(@RequestParam("email") String email){
        return jobPostService.getByEmail(email);
    }
}
