package pl.otto.ecommerce.payu;

public class PayUCredentials {
    private boolean sandbox;
    private String clientId;
    private String clientSecret;

    public PayUCredentials(String clientId, String clientSecret,boolean sandbox) {
        this.sandbox = sandbox;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public static PayUCredentials sandbox(String clientId, String clientSecret){
        return new PayUCredentials(clientId, clientSecret,true);
    }

    public String getBaseURL() {
        if (sandbox) {
            return "https://secure.snd.payu.com";
        } else {
            return "https://secure.payu.com";
        }
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }


}
