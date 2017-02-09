package se.arbetsformedlingen.venice;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MainViewController {

    public static ModelAndView getView(Request request, Response response) {
        return new ModelAndView(null, "index.mustache");
    }
}
