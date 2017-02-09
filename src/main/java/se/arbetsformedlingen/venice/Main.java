package se.arbetsformedlingen.venice;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.function.BiFunction;

import static spark.Spark.staticFileLocation;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        getView("/", MainViewController::getView);
        getString("/probes", ProbeController::getStatus);
    }

    private static void getView(String route, BiFunction<Request, Response, ModelAndView> controller) {
        Spark.get(route, controller::apply, new MustacheTemplateEngine());
    }

    private static void getString(String route, BiFunction<Request, Response, String> transformer) {
        Spark.get(route, transformer::apply);
    }
}
