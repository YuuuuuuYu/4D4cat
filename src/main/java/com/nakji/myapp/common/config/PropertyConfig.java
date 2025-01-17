package com.nakji.myapp.common.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan("com.nakji.myapp.common.property")
public class PropertyConfig {
}
