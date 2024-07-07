package pl.otto.ecommerce.sales.reservation;

public class CustomerDetails {
    private final String customerId;
    private final String firstname;
    private final String lastname;
    private final String email;

    public CustomerDetails(String customerId, String Firstname, String Lastname, String Email) {

        this.customerId = customerId;
        firstname = Firstname;
        lastname = Lastname;
        email = Email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }
}
