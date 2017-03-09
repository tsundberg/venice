package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Webservice;

public class WebserviceLoadValue implements Comparable<WebserviceLoadValue> {
    private Webservice webservice;
    private Long calls;

    public WebserviceLoadValue(Webservice webservice, Long calls) {
        this.webservice = webservice;
        this.calls = calls;
    }

    @Override
    public int compareTo(WebserviceLoadValue o) {
        return webservice.getName().compareTo(o.webservice.getName());
    }

    public Webservice getWebservice() {
        return webservice;
    }

    public Long getCalls() {
        return calls;
    }
}
