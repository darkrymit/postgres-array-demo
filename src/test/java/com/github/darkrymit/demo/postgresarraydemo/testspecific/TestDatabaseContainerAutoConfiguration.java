package com.github.darkrymit.demo.postgresarraydemo.testspecific;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class TestDatabaseContainerAutoConfiguration {

  @Bean
  @ServiceConnection
  public PostgreSQLContainer<?> postgresContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.0"));
  }
}