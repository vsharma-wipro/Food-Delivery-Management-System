package ApiTesting_2.ApiTesting_2;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class Apitesting {
	private static RequestSpecification requestSpecification;

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:3000";
		requestSpecification = new RequestSpecBuilder()
				               .setBaseUri(RestAssured.baseURI)
				               .addHeader("Content-Type", "application/json")
				               .build();

	}
	
	@Test()
	@Order(1)
	public void userRegistration() {
		String requestBody = "{\"username\":\"user6\",\"password\":\"pass6\",\"email\":\"user6@example.com\"}";

		given()
		       .spec(requestSpecification)
		       .body(requestBody)
	      	   .log().all()
		.when()
		       .post("/users/register")
		.then()
		      .log().all()
		      .statusCode(201)
			  .assertThat()
			  .body("username", equalTo("user6"));	      
	}

	@Test
	@Order(2)
	public void userLoginValid() {
		String requestBody = "{\"username\":\"user5\",\"password\":\"pass5\"}";

		given()
		       .spec(requestSpecification)
		       .body(requestBody)
		       .log().all()
		.when()
		       .post("/users/login")
		.then()
		       .log().all()
		       .statusCode(200)
			   .assertThat()
			   .body("message", equalTo("Valid credentials"));
	}

	@Test
	@Order(3)
	public void userLoginInvlid() {
		String requestBody = "{\"username\":\"user7\",\"password\":\"pass7\"}";

		given()
		        .spec(requestSpecification)
		        .body(requestBody)
		        .log().all()
		.when()
		        .post("/users/login")
		.then()
		        .log().all()
		        .statusCode(401)	
		        .assertThat()
		.body("message", equalTo("Invalid credentials"));
	}

	@Test()
	@Order(4)
	public void newRestaurant() {
		String requestBody = "{\"name\":\"Restaurant6\",\"address\":\"Address6\",\"cuisineType\":\"Cuisine6\",\"contactInfo\":\"contact6\"}";

		given()
		       .spec(requestSpecification)
		       .body(requestBody)
		       .log().all()
		.when()
		        .post("/restaurants")		
		.then()
		        .log().all()
		        .statusCode(201)
				.assertThat().body("address", equalTo("Address6"));
	}

	@Test
	@Order(5)
	public void RetriveRestaurant() {
		
		given()
		       .spec(requestSpecification)
		       .pathParam("restaurantId", "4")
		       .log().all()
		.when()
		       .get("/restaurants/{restaurantId}")
	    .then()
	           .log().all()
	           .statusCode(200)
	           .assertThat().body("id", equalTo(4));
	}

	@Test
	@Order(6)
	public void newOrder() {
		String requestBody = "{\"userId\":6,\"restaurantId\":6,\"foodItems\":[\"Food11\",\"Food12\"],\"totalPrice\":70.0}";

		given()
		       .spec(requestSpecification)
		       .body(requestBody)
		       .log().all()
		.when()
		        .post("/orders")
		.then()
		        .log().all()
		        .statusCode(201).assertThat()
				.body("id", equalTo(6));
	}

	@Test
	@Order(7)
	public void orderDetails() {
		given()
		       .spec(requestSpecification)
		       .pathParam("orderId", "4")
		       .log().all()
		.when()
		       .get("/orders/{orderId}")
		.then() 
		       .log().all()
			   .statusCode(200)
			   .assertThat().body("id", equalTo(4));
		       
	}

	@Test
	@Order(8)
	public void userProfile() {
		String requestBody = "{\"username\":\"user13\",\"email\":\"user13@example.com\"}";

		given()
		       .spec(requestSpecification)
		       .pathParam("userId", "3")
		       .body(requestBody)
		       .log().all()
		.when()
		       .put("/users/{userId}")
	    .then()
	           .log().all()
	           .statusCode(200)
	           .assertThat().body("username", equalTo("user13"));
	}

	@Test
	@Order(9)
	public void userAccount() {
		given()
		       .spec(requestSpecification)
		       .pathParam("userId", "4")
		       .log().all()
		.when()
		       .delete("/users/{userId}")
		.then()
		       .log().all()
			   .statusCode(204);
		      

	}

	@Test
	@Order(10)
	public void byName() {

		given()
		       .spec(requestSpecification)
		       .queryParam("name", "Food1")
		       .log().all()
		.when()
		       .get("/foods/search")
		.then()
	     	   .log().all()
			   .statusCode(200)
			   .assertThat().body("size()", equalTo(1));
	}

	@Order(10)
	public void byCuisine() {
		given()
		       .spec(requestSpecification)
		       .queryParam("cuisine", "Cuisine5")
		       .log().all()
		.when()
		       .get("/foods/search")
		.then()
		        .log().all()
				.statusCode(200)
				.assertThat().body("id", equalTo(5));
	}
}

