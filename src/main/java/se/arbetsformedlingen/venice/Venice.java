package se.arbetsformedlingen.venice;

import se.arbetsformedlingen.venice.ci.BuildCheckScheduler;
import se.arbetsformedlingen.venice.ci.BuildController;
import se.arbetsformedlingen.venice.common.ApplicationServer;
import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.index.IndexController;
import se.arbetsformedlingen.venice.log.LogController;
import se.arbetsformedlingen.venice.log.LogcheckScheduler;
import se.arbetsformedlingen.venice.probe.ProbeCheckScheduler;
import se.arbetsformedlingen.venice.probe.ProbeController;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import static spark.Spark.awaitInitialization;
import static spark.Spark.staticFileLocation;

public class Venice {
    public static void main(String[] args) {
        staticFileLocation("/public");

        setupRoutes();
        scheduleJobs();

        awaitInitialization();
    }

    private static void setupRoutes() {
        getView("/", IndexController::getView);
        getString("/probes", ProbeController::getStatus);
        getString("/builds", BuildController::getBuilds);
        getString("/logs", LogController::getLogs);
    }

    private static void scheduleJobs() {
        scheduleBuildChecks();
        scheduleLogChecks();
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

    private static void scheduleLogChecks() {
        Scheduler scheduler = new LogcheckScheduler();
        // todo schedule every 5 minute
        scheduler.startChecking(1, TimeUnit.SECONDS);
    }

    private static void scheduleProbeChecks() {
        List<ApplicationServer> applicationServers = TPJAdmin.prepareServers();
        Scheduler scheduler = new ProbeCheckScheduler(applicationServers);
        scheduler.startChecking(30, TimeUnit.SECONDS);
    }
}
