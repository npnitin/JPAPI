package com.example.service;

import com.example.models.Config;
import com.example.models.ConfigItem;

import java.util.List;

public interface ConfigService {

    public List<Config> getConfig();
    public boolean updateConfig(ConfigItem configItem);
}
