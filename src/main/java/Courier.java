import io.restassured.response.Response;
import pojo.CourierAuthorizationData;
import pojo.CourierDetails;

import static io.restassured.RestAssured.given;

public class Courier {

    public Response createCourier(CourierDetails courierDetails) {

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierDetails)
                .when()
                .post("http://qa-scooter.praktikum-services.ru/api/v1/courier");

    }

    public Response loginCourier(CourierAuthorizationData courierAuthorizationData) {

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierAuthorizationData)
                .when()
                .post("http://qa-scooter.praktikum-services.ru/api/v1/courier/login");

    }

    public Boolean deleteCourier(int courier) {

        return given()
                .header("Content-type", "application/json")
                .when()
                .delete("http://qa-scooter.praktikum-services.ru/api/v1/courier/" + courier)
                .then()
                .extract()
                .path("ok");

    }
}
