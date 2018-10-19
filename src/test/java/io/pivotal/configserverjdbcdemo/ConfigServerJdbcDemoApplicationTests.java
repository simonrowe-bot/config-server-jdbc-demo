package io.pivotal.configserverjdbcdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.Charset;
import java.util.Base64;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ConfigServerJdbcDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void propertiesAreExposedCorrectly() {
		webTestClient.get()
				.uri("/app/cloud")
				.header("Authorization", "Basic " + base64Encode("config:config"))
				.exchange()
				.expectStatus()
				.isOk()
		        .expectBody()
		        .jsonPath("$.propertySources[0].name").isEqualTo("app-cloud")
				.jsonPath("$.propertySources[0].source.cloudPropertyKey").isEqualTo( "cloudPropertyValue")
				.jsonPath("$.propertySources[1].name").isEqualTo("app-default")
				.jsonPath("$.propertySources[1].source.propertyKey").isEqualTo("propertyValue")
				;
	}

	private String base64Encode(String value) {
		return Base64.getEncoder().encodeToString(value.getBytes(Charset.defaultCharset()));
	}

}
