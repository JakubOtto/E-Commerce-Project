package pl.otto.ecommerce.sales.payment;


public interface PaymentGateway {

    PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest);

}
