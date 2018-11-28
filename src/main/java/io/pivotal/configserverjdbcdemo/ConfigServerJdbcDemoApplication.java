package io.pivotal.configserverjdbcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentProperties;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentRepository;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentRepositoryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerJdbcDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerJdbcDemoApplication.class, args);
	}

	@Bean
	public JdbcEnvironmentProperties jdbcEnvironmentProperties() {
		return new JdbcEnvironmentProperties();
	}

	@Bean
	public JdbcEnvironmentRepository jdbcEnvironmentRepository(JdbcTemplate jdbcTemplate, JdbcEnvironmentProperties jdbcEnvironmentProperties) {
		return new JdbcEnvironmentRepositoryFactory(jdbcTemplate).build(jdbcEnvironmentProperties);
	}

}
