package br.aviva.jr;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import groovy.util.logging.Log;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;

import static io.restassured.RestAssured.given;

public class AuthTest {

	
	@Test
	public void deveAcessarSWAAPI() {
		
		given()
		.log().all()
		.when()
		.get("https://swapi.dev/api/people/1/")
		.then()
		.log().all()
		.statusCode(200)
		
		.body("name", is("Luke Skywalker"))
		;
	}
	
	//#7900ac7c2c7730aa2f060f7f7f6fd2d7
	//https://api.openweathermap.org/data/2.5/weather?q=Fortaleza,BR&appid=7900ac7c2c7730aa2f060f7f7f6fd2d7&&units=metric
	@Test
	public void deveObterClima() {
		given()
		.log().all()
		.queryParams("q", "Fortaleza,BR")
		.queryParams("appid", "7900ac7c2c7730aa2f060f7f7f6fd2d7")
		.queryParams("units", "metrics")
		.when()
		.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
		.log().all()
		.statusCode(200)
		
		.body("name", is("Fortaleza"))
		.body("coord.lon", is(-38.5247f))
		.body("main.temp", greaterThan(25f))
		;
		
	}
	
	@Test
	public void naoDeveAcessarSemSenha() {
		
		given()
		.log().all()
		.when()
		.get("https://restapi.wcaquino.me/basicauth")
		.then()
		.log().all()
		.statusCode(401)
		;
		
	}
	
	@Test
	public void deveAutenticacaoBasica() {
		
		given()
		.log().all()
		.when()
		.get("https://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		;
	}
	
	@Test
	public void deveAutenticacaoBasicaPart2() {
		
		given()
		.log().all()
		.auth().basic("admin", "senha")
		.when()
		.get("https://restapi.wcaquino.me/basicauth")
		.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		;
	}
	
	@Test
	public void deveAutenticacaoBasicaChallenge() {
		
		given()
		.log().all()
		.auth().preemptive().basic("admin", "senha")
		.when()
		.get("https://restapi.wcaquino.me/basicauth2")
		.then()
		.log().all()
		.statusCode(200)
		.body("status", is("logado"))
		
		;
	}
	
	//https://barrigarest.wcaquino.me/
	
	@Test
	public void deveFazerAutenticacaoComToken() {
		
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "jredmilsonn@outlook.com");
		login.put("senha", "T18cl9ti");
		

		String token = given()
		.log().all()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
		.post("https://barrigarest.wcaquino.me/signin")
		.then()
		.log().all()
		.statusCode(200)
		.extract().path("token");
		
		
		given()
		.log().all()
		.header("Authorization", "JWT " + token)
		.body(login)
		.when()
		.get("http://barrigarest.wcaquino.me/contas")
		.then()
		.log().all()
		.statusCode(200)
		.body("nome", hasItem("Conta Recebe token"))
		;
		
	}
	
	@Test
	public void deveAcessarAplicacaoWeb() {
		
		//login
		String cookie = given()
		.log().all()
		.formParam("email", "jredmilsonn@outlook.com")
		.formParam("senha", "T18cl9ti")
		.contentType(ContentType.URLENC.withCharset("UTF-8"))
		.when()
		.post("http://seubarriga.wcaquino.me/logar")
		.then()
		.log().all()
		.statusCode(200)
		.extract().header("set-cookie")
		
		;
		cookie = cookie.split("=")[1].split(";")[0];
		System.out.println(cookie);
		
		//obter Conta
		
		String body = given()
		.log().all()
		.cookie("connect.sid", cookie)
		.when()
		.post("http://seubarriga.wcaquino.me/contas")
		.then()
		.log().all()
		.statusCode(200)
		
		.body("html.body.table.tbody.tr[0].td[0]", is("Conta de teste"))
		.extract().body().asString()
		;
		
		System.out.println("-----------------");
		XmlPath xmlPath = new XmlPath(CompatibilityMode.HTML, body);
		System.out.println(xmlPath.getString(body));
	}
}
