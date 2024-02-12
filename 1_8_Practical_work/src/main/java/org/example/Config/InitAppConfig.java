package org.example.Config;


import org.springframework.context.annotation.*;


@Configuration
@PropertySources(value = {@PropertySource("classpath:application-init.properties")})
@Profile("init")
public class InitAppConfig {

}
