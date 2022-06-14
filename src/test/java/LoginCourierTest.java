import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierDetails;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {

    int courierId;
    String login = "ganzalis";
    String password = "123";
    String firstName = "Быстрый Ганзалис";

    CourierDetails courierDetails;
    Courier courier;
    CourierDetails courierAuthorizationData;

    @Before
    public void setup() {

        courierDetails = new CourierDetails(login, password, firstName);
        courier = new Courier();
        courier.createCourier(courierDetails);
        courierAuthorizationData = new CourierDetails(login, password);
        Response responseLogin = courier.loginCourier(courierAuthorizationData);
        courierId = responseLogin.body().jsonPath().getInt("id");

    }

    @After
    public void clear() {

        courier.deleteCourier(courierId);

    }

    @Test
    public void courierAuthorization ()  {

        Response responseLogin = courier.loginCourier(courierAuthorizationData);
        responseLogin.then()
                .statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());

    }

    @Test
    public void courierAuthorizationWithoutLogin () {

        courierAuthorizationData.setLogin("");

        Response responseLogin = courier.loginCourier(courierAuthorizationData);
        responseLogin.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    public void courierAuthorizationWithoutPassword () {

        courierAuthorizationData.setPassword("");

        Response responseLogin = courier.loginCourier(courierAuthorizationData);
        responseLogin.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    public void courierAuthorizationIncorrectLogin () {

        courierAuthorizationData.setLogin("ganzalis23");

        Response responseLogin = courier.loginCourier(courierAuthorizationData);
        responseLogin.then()
                .statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    public void courierAuthorizationIncorrectPassword () {

        courierAuthorizationData.setPassword("23");

        Response responseLogin = courier.loginCourier(courierAuthorizationData);
        responseLogin.then()
                .statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

    }
}
