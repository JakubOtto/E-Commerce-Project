package pl.otto.ecommerce.payu;

import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.AssertJUnit.assertNotNull;

public class PayUTest {


    @Test
    void itAllowsToRegisterPayment() {
        Payu payu = thereIsPayU();

        OrderCreateRequest request = thereIsExampleOrderCreateRequest();

        OrderCreateResponse response = payu.handle(request);

        assertNotNull(response.getRedirectUri());
        assertNotNull(response.getOrderId());
        assertNotNull(response.getExtOrderId());
    }

    private OrderCreateRequest thereIsExampleOrderCreateRequest() {
        var request = new OrderCreateRequest();
        request
                .setNotifyUrl("https://your.eshop.com/notify")
                .setCustomerIp("127.0.0.1")
                .setMerchantPosId("300746")
                .setDescription("My ebook store")
                .setCurrencyCode("PLN")
                .setTotalAmount(2100)
                .setExtOrderId("my_order_id_xyz123")
                .setBuyer(new Buyer()
                        .setEmail("jaspierozek@gmail.com")
                        .setPhone("523523552")
                        .setFirstName("Jas")
                        .setLastName("Pierozek")
                        .setLanguage("pl")
                )
                .setProducts(Arrays.asList(
                        new Product()
                                .setName("Ebook x")
                                .setQuantity(1)
                                .setUnitPrice(21000)
                ));

        return request;
    }

    private Payu thereIsPayU() {
        return new Payu(new RestTemplate(),
                PayUCredentials.sandbox(
                "300746",
                "2ee86a66e5d97e3fadc400c9f19b065d"
        )
        );
    }
}
