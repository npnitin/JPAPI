package com.example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "master_config")
public class Config {

    @Id
    private String id;
    List<String> cities = new ArrayList<>();
    List<String> skills = new ArrayList<>();;
    List<String> category =new ArrayList<>();;
    List<Company> companies = new ArrayList<>();;


    public String getId() {
        return id;
    }

    public List<String> getCities() {
        return cities;
    }

    public List<String> getSkills() {
        return skills;
    }

    public List<String> getCategory() {
        return category;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
