package com.example.springbootProfiles.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile ( "dev" )
public class DevProfileBean implements EnvBasedConfig{
    @Override
    public void setup()
    {
        System.out.println ("Dev evn is setup");
    }
}
