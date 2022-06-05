import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierDetails;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    int courierId;
    String login = "Ganzalis";
    String password = "123";
    String firstName = "Быстрый Ганзалис";

    CourierDetails courierDetails;
    Courier courier;
    CourierDetails courierAuthorizationData;
    Response responseCreate;

    @Before
    public void setup() {

        courierDetails = new CourierDetails(login, password, firstName);
        courier = new Courier();

    }

    @After
    public void clear()  {

        if (courierDetails.getLogin() != "") {
            if (courierDetails.getPassword() != "") {

                courierAuthorizationData = new CourierDetails(login, password);
                Response responseLogin = courier.loginCourier(courierAuthorizationData);
                courierId = responseLogin.body().jsonPath().getInt("id");
                courier.deleteCourier(courierId);

            }
        }
    }

    @Test
    public void createNewCourier () {
        responseCreate = courier.createCourier(courierDetails);
        responseCreate.then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));

    }

    @Test
    public void createDoubleCourier () {

        courier.createCourier(courierDetails);
        Response responseCreateDouble = courier.createCourier(courierDetails);
        responseCreateDouble.then()
                .statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    public void createCourierWithoutEnteringLogin () {

        courierDetails.setLogin("");

        Response response = courier.createCourier(courierDetails);
        response.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    public void createCourierWithoutEnteringPassword () {

        courierDetails.setPassword("");

        Response response = courier.createCourier(courierDetails);
        response.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

        System.out.println(courierDetails.getLogin() + " " + courierDetails.getPassword());

    }
}
