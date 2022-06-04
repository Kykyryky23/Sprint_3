import io.restassured.response.Response;
import pojo.OrderDetails;

import static io.restassured.RestAssured.given;

public class Order {
    public Response createOrder(OrderDetails orderDetails) {

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orderDetails)
                .when()
                .post("http://qa-scooter.praktikum-services.ru/api/v1/orders");

    }
}
