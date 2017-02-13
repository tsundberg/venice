package se.arbetsformedlingen.venice.index;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexController {

    public static ModelAndView getView(Request request, Response response) {
        return new ModelAndView(null, "index.mustache");
    }
}
