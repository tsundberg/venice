package se.arbetsformedlingen.venice;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        get("/", (request, response) -> new ModelAndView(null, "index.mustache"), new MustacheTemplateEngine());
    }
}
