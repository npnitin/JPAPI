package com.example.models;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@QueryEntity
@Document(collection = "interviewQuestions")
public class InterviewQuestions {

    @Id
    private String id;
    private String userEmail;
    private String userName;
    private String companyName;
    private String city;
    private String question;
    private String designation;
    private Date interviewDate;
    private List<Comment> comments;
}
