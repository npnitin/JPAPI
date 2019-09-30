package com.example.models;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@QueryEntity
@Document(collection = "jobpost")
public class JobPost {

    @Id
    private String id;
    private String jobTitle;
    private String company;
    private String city;
    private String address;
    private String jobDescription;
    private List<String> skills;
    private int experienceFrom;
    private int experienceTo;
    private int salaryFrom;
    private int salaryTo;
    private String posterName;
    private String posterEmail;
    private Date postedOn;
    private int notiePeriod;

    public String getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public List<String> getSkills() {
        return skills;
    }

    public int getExperienceFrom() {
        return experienceFrom;
    }

    public int getExperienceTo() {
        return experienceTo;
    }

    public int getSalaryFrom() {
        return salaryFrom;
    }

    public int getSalaryTo() {
        return salaryTo;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getPosterEmail() {
        return posterEmail;
    }

    public Date getPostedOn() {
        return postedOn;
    }

    public int getNotiePeriod() {
        return notiePeriod;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setExperienceFrom(int experienceFrom) {
        this.experienceFrom = experienceFrom;
    }

    public void setExperienceTo(int experienceTo) {
        this.experienceTo = experienceTo;
    }

    public void setSalaryFrom(int salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public void setSalaryTo(int salaryTo) {
        this.salaryTo = salaryTo;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public void setPosterEmail(String posterEmail) {
        this.posterEmail = posterEmail;
    }

    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

    public void setNotiePeriod(int notiePeriod) {
        this.notiePeriod = notiePeriod;
    }
}
