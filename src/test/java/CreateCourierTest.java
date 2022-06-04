import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierAuthorizationData;
import pojo.CourierDetails;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    int courierId;
    String login = "Ganzalis";
    String password = "123";
    String firstName = "Быстрый Ганзалис";

    CourierDetails courierDetails;
    Courier courier;
    CourierAuthorizationData courierAuthorizationData;
    Response responseCreate;

    @Before
    public void setup() {

        courierDetails = new CourierDetails(login, password, firstName);
        courier = new Courier();
        responseCreate = courier.createCourier(courierDetails);

    }

    @After
    public void clear() {

        courierAuthorizationData = new CourierAuthorizationData(login, password);
        Response responseLogin = courier.loginCourier(courierAuthorizationData);
        courierId = responseLogin.body().jsonPath().getInt("id");
        courier.deleteCourier(courierId);

    }

    @Test
    public void createNewCourier () {

        Response responseCreateDouble = courier.createCourier(courierDetails);

        responseCreate.then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));

        responseCreateDouble.then()
                .statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    public void createCourierWithoutFieldLogin() {

        courierDetails.setLogin("");

        Response response = courier.createCourier(courierDetails);
        response.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    public void createCourierWithoutFieldPassword () {

        courierDetails.setPassword("");

        Response response = courier.createCourier(courierDetails);
        response.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
}
