package br.aviva.jr;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class EnvioDadosTest {

	@Test
	public void deveEnviarValorViaQuery() {
		
		given()
		.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users/?format=xml")
		.then()
		.log().all()
		.statusCode(200)
		.contentType(ContentType.XML)
	
		;
	}
	
	@Test
	public void deveEnviarValorViaQueryJSON() {
		
		given()
		.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users/?format=json")
		.then()
		.log().all()
		.statusCode(200)
		.contentType(ContentType.JSON)
		;
}
	
	@Test
	public void deveEnviarValorViaParams() {
		
		given()
		.log().all()
		.queryParam("format", "xml")
		.queryParam("outra", "conta")		
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
		.log().all()
		.statusCode(200)
		.contentType(ContentType.XML)
		.contentType(Matchers.containsString("utf-8"))
		;
}
	
	@Test
	public void deveEnviarValorViaHeader() {
		
		given()
		.log().all()
		.accept(ContentType.JSON)
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		.then()
		.log().all()
		.statusCode(200)
		.contentType(ContentType.HTML)
	
		;
	}
}
