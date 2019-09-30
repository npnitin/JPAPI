package com.example.service;

import com.example.models.Company;
import com.example.models.Config;
import com.example.models.ConfigItem;
import com.example.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepository;

    @Override
    public List<Config> getConfig() {
        return configRepository.findAll();
    }

    @Override
    public boolean updateConfig(ConfigItem configItem) {
        List<Config> configItems = configRepository.findAll();
        Config config = null;
        if(configItems.isEmpty()){
            config = new Config();
        }else{
             config = configItems.get(0);
        }
        if(configItem.getKey().equalsIgnoreCase("City")){
            if(config.getCities().isEmpty()){
                List<String> citiies = new ArrayList<>(1);
                citiies.add(configItem.getValue());
                config.setCities(citiies);
            }else {
                config.getCities().add(configItem.getValue());
            }
        }else if(configItem.getKey().equalsIgnoreCase("Skill")){
            if(config.getSkills().isEmpty()){
                List<String> skills = new ArrayList<>(1);
                skills.add(configItem.getValue());
                config.setSkills(skills);
            }else {
                config.getSkills().add(configItem.getValue());
            }
        }else if(configItem.getKey().equalsIgnoreCase("company")){
            Company company = new Company();
            company.setName(configItem.getValue());
            if(config.getCompanies().isEmpty()){
                List<Company> companies = new ArrayList<>(1);
                companies.add(company);
                config.setCompanies(companies);
            }else{
                config.getCompanies().add(company);
            }
        }
        configRepository.save(config);
        return true;
    }
}
