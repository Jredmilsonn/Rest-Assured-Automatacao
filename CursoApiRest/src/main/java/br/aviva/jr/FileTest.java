package br.aviva.jr;

import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileTest {

	
	@Test
	public void deveObrigarEnvioArquivo() {
		
		given()
		.log().all()
		.when()
		.post("http://restapi.wcaquino.me/upload")
		.then()
		.log().all()
		.statusCode(404) //Deveria ser 400
		
		.body("error", is("Arquivo n?o enviado"))
		;
	}
	
	@Test
	public void deveFazerUploadArquivo() {
		
		given()
		.log().all()
		.multiPart("arquivo", new File("src/main/resources/users.pdf"))
		.when()
		.post("http://restapi.wcaquino.me/upload")
		.then()
		.log().all()
		.statusCode(200) //Deveria ser 400
		
		.body("name", is("users.pdf"))
		;
	}
	
	@Test
	public void naoDeveFazerUploadArquivoGrande() {
		
		given()
		.log().all()
		.multiPart("arquivo", new File("src/main/resources/Sprint 2 - Report Automa??o - Poupa Brasil.pptx"))
		.when()
		.post("http://restapi.wcaquino.me/upload")
		.then()
		.log().all()
		.time(lessThan(2900L))
		.statusCode(413) //Deveria ser 400
		
		;
	}
	
	@Test
	public void deveBaixarAquivo() throws IOException {
		
		byte[] image = given()
		.log().all()
		.when()
		.get("http://restapi.wcaquino.me/download")
		.then()
		//.log().all()
		.statusCode(200)
		.extract().asByteArray();
		
		;
		File imagem = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		System.out.println(imagem.length());
		Assert.assertThat(imagem.length(), lessThan(100000L));
	}
}
