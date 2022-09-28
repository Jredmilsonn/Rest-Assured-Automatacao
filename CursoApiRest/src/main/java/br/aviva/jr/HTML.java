package br.aviva.jr;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class HTML {
	
	@Test
	public void deveFazerBuscarComHTML() {
		
		given()
		.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
			.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body("html.body.div.table.tbody.tr.size()", Matchers.is(3))
			.body("html.body.div.table.tbody.tr[1].td[2]", is("25"));
			.appendRootPath("html.body.div.table.tbody")
			.body("tr.find{it.toString().startsWith('2')}.td[1]", is("Maria Joaquina"))
		;
	}
	
	@Test
	public void deveFazerBuscaComHTMLeXpath() {
		
		given()
		.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=clean")
			.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body(hasXPath("count(//table/tr)", is("4")))
			.body(hasXPath("//td[text()= '2']/../td[2]"), is("Maria Joaquina"))
		;
	}


}
