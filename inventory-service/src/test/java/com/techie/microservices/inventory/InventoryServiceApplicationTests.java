package com.techie.microservices.inventory;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@Container
	@ServiceConnection
	static MySQLContainer mySQLContainer =
			new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

//		RestAssured.baseURI = "http://localhost:" + port;
	}

	// @Test
	// void shouldReadInventory() {
	// 	System.out.println("PORT = " + port);
	// 	var response = RestAssured.given()
	// 			.when()
	// 			.get("/api/inventory?skuCode=iphone_14&quantity=11")
	// 			.then()
	// 			.statusCode(200)
	// 			.extract().as(Boolean.class);
	// 	assertTrue(response);

	// 	var negativeResponse = RestAssured.given()
	// 			.when()
	// 			.get("/api/inventory?skuCode=iphone_14&quantity=110")
	// 			.then()
	// 			.statusCode(200)
	// 			.extract().as(Boolean.class);
	// 	assertFalse(negativeResponse);
	// }
}