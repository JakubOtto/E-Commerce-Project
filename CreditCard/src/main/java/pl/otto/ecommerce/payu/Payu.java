package pl.otto.ecommerce.payu;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.otto.ecommerce.sales.payment.PaymentDetails;
import pl.otto.ecommerce.sales.payment.PaymentGateway;
import pl.otto.ecommerce.sales.payment.RegisterPaymentRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

public class Payu implements PaymentGateway {

    RestTemplate http;

    PayUCredentials credentials;

    public Payu(RestTemplate http, PayUCredentials credentials) {
        this.http = http;
        this.credentials = credentials;
    }

    public OrderCreateResponse handle(OrderCreateRequest orderCreateRequest) {
        //Authorize
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", String.format("Bearer %s", getToken()));

        HttpEntity<OrderCreateRequest> request = new HttpEntity<>(orderCreateRequest, headers);

        ResponseEntity<OrderCreateResponse> orderCreateResponse = http.postForEntity(
                String.format("%s/api/v2_1/orders", credentials.getBaseURL()),
                request,
                OrderCreateResponse.class
        );
        //Create order
        return orderCreateResponse.getBody();
    }

    private String getToken() {
        String body = String.format(
                "grant_type=client_credentials&client_id=%s&client_secret=%s",
                credentials.getClientId(),
                credentials.getClientSecret()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<AuthorizationResponse> atResponse = http.postForEntity(
                String.format("%s/pl/standard/user/oauth/authorize", credentials.getBaseURL()),
                request,
                AuthorizationResponse.class
        );

        return atResponse.getBody().getAccessToken();
    }

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
        var request = new OrderCreateRequest();
        request
                .setNotifyUrl("https://my.example.shop.wbub.pl/api/order")
                .setCustomerIp("127.0.0.1")
                .setMerchantPosId("300746")
                .setDescription("My ebook store")
                .setCurrencyCode("PLN")
                .setTotalAmount(registerPaymentRequest.getTotal().multiply(BigDecimal.valueOf(100)).intValue())
                .setExtOrderId(UUID.randomUUID().toString())
                .setBuyer((new Buyer())
                        .setEmail(registerPaymentRequest.getEmail())
                        .setFirstName(registerPaymentRequest.getFirstname())
                        .setLastName(registerPaymentRequest.getLastname())
                        .setLanguage("pl")
                )
                .setProducts(Collections.singletonList(
                        new Product()
                                .setName("Ebook x")
                                .setQuantity(1)
                                .setUnitPrice(21000)
                ));

        OrderCreateResponse response = this.handle(request);

        return new PaymentDetails(response.getRedirectUri(), response.getOrderId());
    }
}

