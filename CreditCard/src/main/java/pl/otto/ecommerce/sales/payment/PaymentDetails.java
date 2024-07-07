package pl.otto.ecommerce.sales.payment;

public class PaymentDetails {
    String url;

    public PaymentDetails(String url, String id) {

        this.url = url;
    }

    public String getPaymentUrl() {
        return url;
    }
}
