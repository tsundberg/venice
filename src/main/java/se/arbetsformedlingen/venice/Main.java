package se.arbetsformedlingen.venice;

import se.arbetsformedlingen.venice.build.BuildCheckScheduler;
import se.arbetsformedlingen.venice.build.BuildController;
import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.index.IndexController;
import se.arbetsformedlingen.venice.probe.ProbeCheckScheduler;
import se.arbetsformedlingen.venice.probe.ProbeController;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import static spark.Spark.staticFileLocation;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        getView("/", IndexController::getView);
        getString("/probes", ProbeController::getStatus);
        getString("/builds", BuildController::getBuilds);

        scheduleBuildChecks();
        scheduleProbeChecks();
    }

    private static void getView(String route, BiFunction<Request, Response, ModelAndView> controller) {
        Spark.get(route, controller::apply, new MustacheTemplateEngine());
    }

    private static void getString(String route, BiFunction<Request, Response, String> transformer) {
        Spark.get(route, transformer::apply);
    }

    private static void scheduleBuildChecks() {
        Scheduler scheduler = new BuildCheckScheduler();
        scheduler.startChecking(30, TimeUnit.SECONDS);
    }

    private static void scheduleProbeChecks() {
        Scheduler scheduler = new ProbeCheckScheduler();
        scheduler.startChecking(30, TimeUnit.SECONDS);
    }
}
