package se.arbetsformedlingen.venice;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        get("/", (request, response) -> new ModelAndView(null, "index.mustache"), new MustacheTemplateEngine());
    }
}
