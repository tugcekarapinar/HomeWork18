package swagger.io.test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class PetStoreTest {
    public static void main(String[] args) {

        String requestBody = "";
        try {
            requestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/body.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .body(requestBody);

        Response response = request
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(200)
                .extract().response();

        int petId = response.jsonPath().getInt("id");
        Assert.assertEquals(1412, petId);

        String petName = response.jsonPath().getString("name");
        Assert.assertEquals("demo", petName);

        String petStatus = response.jsonPath().getString("status");
        Assert.assertEquals("available", petStatus);

        int categoryId = response.jsonPath().getInt("category.id");
        Assert.assertEquals(2023, categoryId);

        String categoryName = response.jsonPath().getString("category.name");
        Assert.assertEquals("wolf", categoryName);

        int tagId = response.jsonPath().getInt("tags[0].id");
        Assert.assertEquals(8, tagId);

        String tagName = response.jsonPath().getString("tags[0].name");
        Assert.assertEquals("erkek", tagName);
    }
}