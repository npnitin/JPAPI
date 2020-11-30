package com.example.service;

import com.example.models.JobPost;
import com.example.models.User;
import com.example.repository.JobPostRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPostServiceImpl implements JobPostService {

    @Autowired
    JobPostRepository jobPostRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public JobPost addJobPost(JobPost jobPost) {
        jobPost.setPostedOn(new Date());
        jobPost.setSkills(jobPost.getSkills().stream().map(skill->skill.toUpperCase()).collect(Collectors.toList()));
        jobPost.setCity(jobPost.getCity().toUpperCase());
        jobPost.setCompany(jobPost.getCompany().toUpperCase());
        jobPost = jobPostRepository.save(jobPost);
        return jobPost;
    }

    @Override
    public List<JobPost> filter(String city,String skill,String company,String exp,String sal) {
        int expFrom = 0;
        int expTo=20;
        int salFrom = 0;
        int salTo = 20;
        city = city.toUpperCase();
        skill = skill.toUpperCase();
        company = company.toUpperCase();

        if(exp!=null && !exp.equalsIgnoreCase("undefined") && !exp.isEmpty()){
            String[] expArr = exp.split("\\-");
            expFrom = Integer.parseInt(expArr[0]);
            expTo = Integer.parseInt(expArr[1]);
        }
        if(sal!=null && !sal.equalsIgnoreCase("undefined") && !sal.isEmpty()){
            String[] salArr = sal.split("\\-");
            salFrom = Integer.parseInt(salArr[0]);
            salTo = Integer.parseInt(salArr[1]);
        }
        company = company==null||company.equalsIgnoreCase("undefined")?"":company;
        Query query = new Query();
        query.addCriteria(Criteria.where("skills").in(skill));
        query.addCriteria(Criteria.where("city").is(city));
        query.addCriteria(Criteria.where("company").regex(company));
        query.addCriteria(Criteria.where("salaryFrom").gte(salFrom).lte(salTo));
        query.addCriteria(Criteria.where("experienceFrom").gte(expFrom).lte(expTo));
        query.with(new Sort(Sort.Direction.DESC,"postedOn"));
        List<JobPost> posts = mongoTemplate.find(query,JobPost.class);
        posts = posts
                .stream()
                .map(post-> {
                    post.setCity(capitalizeAll(post.getCity()));
                    return post;
                }).map(post->{
                    post.setCompany(capitalizeAll(post.getCompany()));
                            return post;
                }).map(post->{
                    post.setSkills(post.getSkills().stream().map(skillStr->capitalizeAll(skillStr)).collect(Collectors.toList()));
                    return post;
                }).collect(Collectors.toList());
                return posts;
    }

    @Override
    public List<JobPost> findMostRecentAddedJobs(int size) {
        PageRequest pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "postedOn"));
        pageable.getSort().descending();
        return jobPostRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean sendMail(MultipartFile file, String jobId) throws MessagingException {
        JobPost jobPost = jobPostRepository.findById(jobId).get();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(jobPost.getPosterEmail());
        helper.setText(
                "<html>" +
                "<body border=//'solid black//'>" +
                "<h3>Hi "+jobPost.getPosterName() + " ,</h3>" +
                "<h4>Greetings from ReferralJobz</h4>" +
                "<h4>One applicant applied for job you posted on ReferralJobz</h4>"+
                "<h4>Job Title:"+jobPost.getJobTitle()+" for "+jobPost.getCompany()+" company at "+jobPost.getCity()+"</h4>"+
                "<h4>Please find the attached resume of the applicant.</h4>"+
                "<br/>"+
                "<h4>Thank you,</h4>"+
                "<h4>ReferralJobz Team</h4>"+
                "</body>" +
                "</html>", true);
        helper.addAttachment(file.getOriginalFilename(), file);
        helper.setSubject("ReferralJobz Alerts");
        javaMailSender.send(message);
        return true;
    }

    @Override
    public List<JobPost> getByEmail(String email) {
        return jobPostRepository.findByPosterEmail(email);
    }

    @Override
    public JobPost getById(String id) {
        return jobPostRepository.findById(id).get();
    }

    @Override
    public String applyForJob(String jobId, String userId) {
        JobPost jobPost = jobPostRepository.findById(jobId).get();
        User user = userRepository.findById(userId).get();
        try {
            sendMail(jobPost,user.getResumeUrl());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "success";
    }

    private String capitalizeAll(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

       return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();

    }
    private void sendMail(JobPost jobPost,String resumeLink) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(jobPost.getPosterEmail());
        helper.setText(
                "<html>" +
                        "<body border=//'solid black//'>" +
                        "<div style='border-style:solid;border-width:thin;border-color:#dadce0;border-radius:8px;padding:40px 20px'>"+
                        "<h3>Hi "+jobPost.getPosterName() + " ,</h3>" +
                        "<h4>Greetings from ReferralJobz</h4>" +
                        "<h4>One applicant applied for job you posted on ReferralJobz</h4>"+
                        "<h4>Job Title:"+jobPost.getJobTitle()+" for "+jobPost.getCompany()+" company at "+jobPost.getCity()+"</h4>"+
                        "<h4>Here is download resume link of the applicant:</h4>"+"<a href=\""+resumeLink+"\" target=\"_blank\">Download Resume</a>"+
                        "<br/>"+
                        "<h4>Thank you,</h4>"+
                        "<h4>ReferralJobz Team</h4>"+
                        "</div>"+
                        "</body>" +
                        "</html>", true);
        helper.setSubject("ReferralJobz Alerts");
        javaMailSender.send(message);
    }
}
