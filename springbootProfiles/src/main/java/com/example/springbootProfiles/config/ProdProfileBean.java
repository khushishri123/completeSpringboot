package com.example.springbootProfiles.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile ( "prod" )
public class ProdProfileBean implements EnvBasedConfig{

    @Override
    public void setup() {
        System.out.println ("Prod env is setup");
    }
}
