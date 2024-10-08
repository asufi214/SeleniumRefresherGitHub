package api_test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;


public class GetTestWithPathVariable extends BaseClassAPITest {
//    private static final Logger LOGGER = LogManager.getLogger(GetTestWithPathVariable.class);
    //BaseClassAPI removes the need for the previous logger

    @Test
    public void getSingleUser() {
        LOGGER.info(testCaseName);

        RestAssured.baseURI = "https://reqres.in/api/users";

        RequestSpecification httpRequest = RestAssured.given();

        String id = "3";
        Response response = httpRequest.request(Method.GET, id);
        LOGGER.debug(response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath jsonPath = response.jsonPath();
        String actualEmailId = jsonPath.getString("data.email");

        String expectedEmailId = "emma.wong@reqres.in";
        Assert.assertEquals(actualEmailId, expectedEmailId);

        LOGGER.info(endTestCase);
    }

    @Test
    public void attemptTpGetUserWithFakeId() {
        LOGGER.info(testCaseName);

        RestAssured.baseURI = "https://reqres.in/api/users";

        RequestSpecification httpRequest = RestAssured.given();

        String id = "23";
        Response response = httpRequest.request(Method.GET, id);
        LOGGER.debug(response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 404);

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(jsonPath.get().toString(), "{}");

        LOGGER.info(endTestCase);
    }
}
