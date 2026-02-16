package com.techie.microservices.order;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.techie.microservices.order.stubs.InventoryClientStub;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.mysql.MySQLContainer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.cloud.contract.wiremock.WireMockSpring.options;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	static WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());

	@BeforeAll
	static void startWireMock() {
		wireMockServer.start();
		WireMock.configureFor("localhost", wireMockServer.port());
	}


	@AfterAll
	static void stopWireMock() {
		wireMockServer.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("inventory.url",
				() -> "http://localhost:" + wireMockServer.port());
	}

	@ServiceConnection
	static MySQLContainer mySQLContainer=new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup()
	{
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
	}
	static
	{
		mySQLContainer.start();
	}

	@Test
	void shouldSubmitOrder()
	{
		String submitOrderJson= """
 		{
 		"skuCode" : "iphone_14",
 		"price" : 1400,
 		"quantity" : 100
		}		
		""";
		InventoryClientStub.stubInventoryCall("iphone_14",100);
		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(submitOrderJson)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		assertThat(responseBodyString, Matchers.is("Order Placed Successfully"));

	}

}
