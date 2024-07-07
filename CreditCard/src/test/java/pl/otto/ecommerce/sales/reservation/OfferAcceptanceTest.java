package pl.otto.ecommerce.sales.reservation;

import com.jayway.jsonpath.internal.function.sequence.First;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import pl.otto.ecommerce.catalog.Product;
import pl.otto.ecommerce.sales.SalesFacade;

import pl.otto.ecommerce.sales.cart.CartStorage;
import pl.otto.ecommerce.sales.offering.OfferCalculator;
import pl.otto.ecommerce.sales.reservation.AcceptOfferRequest;
import pl.otto.ecommerce.sales.reservation.ReservationDetails;


import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class OfferAcceptanceTest {

    private SpyPaymentGateway spyPaymentGateway;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        spyPaymentGateway = new SpyPaymentGateway();
        reservationRepository = new ReservationRepository();
    }

    @Test
    void itAllowsToAcceptAnOffer() {
        SalesFacade sales = thereIsSales();
        String customerId = thereIsCustomer("Jakub");
        Product product = thereIsProduct("X", BigDecimal.valueOf(10));

        sales.addProduct(customerId, product.getId(),product.getPrice());
        sales.addProduct(customerId,product.getId(),product.getPrice());

        var acceptOfferRequest = new AcceptOfferRequest();
        acceptOfferRequest
                .setFirstname("Jas")
                .setLastname("Pierog")
                .setEmail("jasiopierozek@dough.com");

        ReservationDetails reservationDetails = sales.acceptOffer(customerId,acceptOfferRequest);

        assertThat(reservationDetails.getPaymentUrl()).isNotBlank();
        assertThat(reservationDetails.getReservationId()).isNotBlank();

        assertPaymentHasBeenRegistered();
        assertThereIsReservationWithId(reservationDetails.getReservationId());
        assertReservationIsPending(reservationDetails.getReservationId());
        assertReservationIsDoneForCustomer(reservationDetails.getReservationId(),"Jas","Pierog","jasiopierozek@dough.com");
        assertReservationTotalMatchOffer(reservationDetails.getReservationId(), BigDecimal.valueOf(20));
    }

    private void assertReservationTotalMatchOffer(String reservationId, BigDecimal expectedTotal) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();
        assertThat(loaded.getTotal()).isEqualTo(expectedTotal);
    }

    private void assertReservationIsDoneForCustomer(String reservationId, String Firstname, String Lastname, String Email) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();

        CustomerDetails clientData = loaded.getCustomerDetails();

        assertThat(clientData.getFirstname()).isEqualTo(Firstname);
        assertThat(clientData.getLastname()).isEqualTo(Lastname);
        assertThat(clientData.getEmail()).isEqualTo(Email);
    }

    private void assertReservationIsPending(String reservationId) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();
        assertThat(loaded.isPending()).isTrue();
    }

    private void assertThereIsReservationWithId(String reservationId) {
        Optional<Reservation> loaded = reservationRepository.load(reservationId);
        
        assertThat(loaded).isPresent();
    }

    private void assertPaymentHasBeenRegistered() {
        assertThat(spyPaymentGateway.getRequestsCount()).isEqualTo(1);
    }

    private String thereIsCustomer(String id) {
        return id;
    }

    private SalesFacade thereIsSales() {
        return new SalesFacade(
                new CartStorage(),
                new OfferCalculator(),
                spyPaymentGateway,
                reservationRepository
        );
    }

    private Product thereIsProduct(String name, BigDecimal price) {
        Product product = new Product(UUID.randomUUID(), name, "description");
        product.changePrice(price);
        return product;
    }

}
