package pl.otto.ecommerce.sales.payment;

import pl.otto.ecommerce.sales.reservation.AcceptOfferRequest;

import java.math.BigDecimal;

public class RegisterPaymentRequest {
    private final AcceptOfferRequest acceptOfferRequest;
    private final String reservationId;
    private final BigDecimal total;
    private final String email;
    private final String firstname;
    private final String lastname;



    public String getEmail() {
        return email;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }

    public RegisterPaymentRequest(String reservationId, AcceptOfferRequest acceptOfferRequest, BigDecimal total) {
        this.acceptOfferRequest = acceptOfferRequest;
        this.reservationId = reservationId;
        this.total = total;
        this.firstname = acceptOfferRequest.getFirstname();
        this.lastname = acceptOfferRequest.getLastname();
        this.email = acceptOfferRequest.getEmail();
    }

    public static RegisterPaymentRequest of(String reservationId, AcceptOfferRequest acceptOfferRequest, BigDecimal total) {
        return new RegisterPaymentRequest(reservationId,acceptOfferRequest,total);
    }

    public AcceptOfferRequest getAcceptOfferRequest() {
        return acceptOfferRequest;
    }

    public String getReservationId() {
        return reservationId;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
