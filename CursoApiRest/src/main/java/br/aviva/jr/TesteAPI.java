package br.aviva.jr;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TesteAPI {
	@Test
	public void testeolaMundo() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		System.out.println(response.getBody().asString().equals("Ola Mundo!"));
		System.out.println(response.statusCode() == 200);

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}

	@Test
	public void devoConhecerOutrasFormasRestAssured() {

		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);

		// get("http://r estapi.wcaquino.me/ola").then().statusCode(200);

		// given().when().get("http://restapi.wcaquino.me/ola").then().statusCode(200);
	}

	@Test
	public void devoConhecerMatchersHamcrest() {

		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));
		Assert.assertThat(128, Matchers.isA(Integer.class));
		Assert.assertThat(128d, Matchers.isA(Double.class));
		Assert.assertThat(128d, Matchers.greaterThan(130d));

		List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1, 3, 5, 7, 9));
		assertThat(impares, containsInAnyOrder(1, 3, 5, 7));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1, 5));
		assertThat(impares, containsInAnyOrder(1, 3, 5, 7));

		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));

	}

	@Test
	public void validarBody() {
		
	given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
		.statusCode(200)
		.body(is("Ola Mundo!"))
		.body(containsString("Mundo"))
		.body(is(not(nullValue())));
	}
}
