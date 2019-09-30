package com.example.web;

import com.example.models.Config;
import com.example.models.ConfigItem;
import com.example.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobportal/config")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @GetMapping
    public List<Config> getMasterConfiguration(){
        return configService.getConfig();
    }

    @PostMapping()
    public boolean updateConfig(@RequestBody ConfigItem configItem){
        return configService.updateConfig(configItem);
    }

}
