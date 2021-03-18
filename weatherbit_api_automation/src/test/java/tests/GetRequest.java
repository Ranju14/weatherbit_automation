package tests;

import static com.jayway.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class GetRequest {
	@Test
	public void getRequestByPostalCode() {

		given().
		parameter("postal_code","2026").
		parameter("key","5a55d544a7eb4bb18016cdcc2bb7b895").
		when().
		get("https://api.weatherbit.io/v2.0/forecast/daily").
		then().
		assertThat().
		statusCode(200);
	}


}
