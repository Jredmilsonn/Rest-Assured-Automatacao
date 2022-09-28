package br.aviva.jr;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SchemaTest {

	@Test
	public void deveValidarSchemeXML() {
		
		given()
		.log().all()
		.when()
		.get("https://restapi.wcaquino.me/usersXML")
		.then()
		.log().all()
		.statusCode(200)
		.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
		
		;
	}
	
	@Test(expected=SAXParseException.class)
	public void naoDeveValidarSchemeXML() {
		
		given()
		.log().all()
		.when()
		.get("https://restapi.wcaquino.me/usersXML")
		.then()
		.log().all()
		.statusCode(200)
		.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
		;
	
	}
		@Test
		public void DeveValidarSchemaJSON() {
			given()
			.log().all()
			.when()
			.get("https://restapi.wcaquino.me/usersXML")
			.then()
			.log().all()
			.statusCode(200)
			
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("usersjson"))
			;
		}
	}
