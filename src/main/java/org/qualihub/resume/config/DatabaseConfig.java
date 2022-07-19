package org.qualihub.resume.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = {"org.qualihub.resume.domain.model"})  // scan JPA entities
public class DatabaseConfig {
}
