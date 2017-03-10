package spark;

import java.util.HashMap;
import java.util.Map;

public class RequestStub extends Request {
    private Map<String, String> requestParams = new HashMap<>();

    @Override
    public String params(String param) {
        return requestParams.get(param);
    }

    public static class RequestBuilder {
        private Map<String, String> requestParams = new HashMap<>();

        public RequestBuilder() {
        }

        public RequestBuilder params(String param, String value) {
            requestParams.put(param, value);
            return this;
        }

        public RequestStub build() {
            RequestStub request = new RequestStub();

            if (!requestParams.isEmpty()) {
                request.requestParams = requestParams;
            }

            return request;
        }
    }
}
