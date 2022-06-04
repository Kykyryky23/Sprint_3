import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.OrderDetails;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public CreateOrderTest (String firstName, String lastName, String address, String metroStation,
                            String phone, int rentTime, String deliveryDate, String comment, String[] color)
    {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;

    }

    @Parameterized.Parameters
    public static Object[][] getSumData() {
        String []colorTwo = {"BLACK", "GREY"};
        String []colorBlack = {"BLACK"};
        String []colorGREY = {"GREY"};
        String []color = new String[0];
        return new Object[][] {
                { "Ganzalis", "Fast", "Mexico", "Сокольники", "+7 777 77 77 77", 2, "2022-06-06", "Ох и полетаем", colorTwo},
                { "Pedro", "Fast", "Mexico", "Сокольники", "+7 777 77 77 78", 2, "2022-06-06", "Прям улетим", colorBlack},
                { "Hyan", "Fast", "Mexico", "Сокольники", "+7 777 77 77 79", 2, "2022-06-06", "Прям улетим к звездам", colorGREY},
                { "Maria", "Fast", "Mexico", "Сокольники", "+7 666 66 66 66", 2, "2022-06-06", "Не хрен летать", color},
        };
    }

    @Test
    public void createCourierWithoutFieldLogin() {

        OrderDetails orderDetails = new OrderDetails(firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
        Order order = new Order();
        Response responseCreate = order.createOrder(orderDetails);

        responseCreate.then()
                .statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());

    }
}
