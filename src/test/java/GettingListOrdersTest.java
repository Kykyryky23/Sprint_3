import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GettingListOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void GettingListOrders () {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .get("/api/v1/orders");

        response.then()
                .statusCode(200);

        String test = response.body().asString();
        MatcherAssert.assertThat(test, allOf(notNullValue(), containsString("orders")));

    }
}
