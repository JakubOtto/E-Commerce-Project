package pl.otto.ecommerce.sales;

import pl.otto.ecommerce.sales.cart.Cart;
import pl.otto.ecommerce.sales.cart.CartStorage;
import pl.otto.ecommerce.sales.offering.Offer;
import pl.otto.ecommerce.sales.offering.OfferCalculator;
import pl.otto.ecommerce.sales.payment.PaymentDetails;
import pl.otto.ecommerce.sales.payment.PaymentGateway;
import pl.otto.ecommerce.sales.payment.RegisterPaymentRequest;
import pl.otto.ecommerce.sales.reservation.AcceptOfferRequest;
import pl.otto.ecommerce.sales.reservation.Reservation;
import pl.otto.ecommerce.sales.reservation.ReservationDetails;
import pl.otto.ecommerce.sales.reservation.ReservationRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class SalesFacade {
    private CartStorage cartStorage;
    private OfferCalculator offerCalculator;
    private PaymentGateway paymentGateway;
    private ReservationRepository reservationRepository;

    public SalesFacade(CartStorage cartStorage, OfferCalculator offerCalculator, PaymentGateway paymentGateway, ReservationRepository reservationRepository) {
        this.cartStorage = cartStorage;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationRepository = reservationRepository;
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = cartStorage.loadForCustomer(customerId)
                .orElse(Cart.empty());

        return offerCalculator.calculate(cart.getItems());
    }

    public void addProduct(String customerId, String productId, BigDecimal price) {
        Cart cart = cartStorage.loadForCustomer(customerId)
                .orElse(Cart.empty());

        cart.addProduct(productId, price);
        cartStorage.saveForCustomer(customerId, cart);

    }

    public ReservationDetails acceptOffer(String customerId, AcceptOfferRequest acceptOfferRequest) {
        String reservationId = UUID.randomUUID().toString();

        Offer offer = this.getCurrentOffer(customerId);
        PaymentDetails paymentDetails = paymentGateway.registerPayment(
                RegisterPaymentRequest.of(reservationId,acceptOfferRequest, offer.getTotal())
        );
        Reservation reservation = Reservation.of(reservationId, customerId, acceptOfferRequest, paymentDetails, offer);

        reservationRepository.add(reservation);
        return new ReservationDetails(reservationId, paymentDetails.getPaymentUrl());
    }
}
