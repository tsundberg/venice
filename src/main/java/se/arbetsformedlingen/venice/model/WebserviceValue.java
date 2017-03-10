package se.arbetsformedlingen.venice.model;

public class WebserviceValue implements Comparable<WebserviceValue> {
    private Webservice webservice;
    private Long calls;

    public WebserviceValue(Webservice webservice, Long calls) {
        this.webservice = webservice;
        this.calls = calls;
    }

    @Override
    public int compareTo(WebserviceValue o) {
        return webservice.getName().compareTo(o.webservice.getName());
    }

    public Webservice getWebservice() {
        return webservice;
    }

    public Long getCalls() {
        return calls;
    }
}
