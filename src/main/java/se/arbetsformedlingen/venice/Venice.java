package se.arbetsformedlingen.venice;

import se.arbetsformedlingen.venice.ci.BuildCheckScheduler;
import se.arbetsformedlingen.venice.ci.BuildController;
import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.index.IndexController;
import se.arbetsformedlingen.venice.log.LogController;
import se.arbetsformedlingen.venice.log.LogcheckScheduler;
import se.arbetsformedlingen.venice.model.ApplicationServer;
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
        getString("/logs/:application/:logType", LogController::getLogs);
    }

    private static void scheduleJobs() {
        Configuration configuration = new Configuration("/etc/venice/configuration.yaml");

        scheduleBuildChecks(configuration);
        scheduleLogChecks(configuration);
        scheduleProbeChecks(configuration);
    }

    private static void getView(String route, BiFunction<Request, Response, ModelAndView> controller) {
        Spark.get(route, controller::apply, new MustacheTemplateEngine());
    }

    private static void getString(String route, BiFunction<Request, Response, String> transformer) {
        Spark.get(route, transformer::apply);
    }

    private static void scheduleBuildChecks(Configuration configuration) {
        TPJAdmin tpjAdmin = new TPJAdmin(configuration);
        Scheduler scheduler = new BuildCheckScheduler(tpjAdmin);
        scheduler.startChecking(30, TimeUnit.SECONDS);
    }

    private static void scheduleLogChecks(Configuration configuration) {
        Scheduler scheduler = new LogcheckScheduler(configuration);
        scheduler.startChecking(5, TimeUnit.MINUTES);
    }

    private static void scheduleProbeChecks(Configuration configuration) {
        TPJAdmin tpjAdmin = new TPJAdmin(configuration);
        ProbeController.setServers(tpjAdmin.getApplicationServers());
        List<ApplicationServer> applicationServers = tpjAdmin.prepareServers();

        Scheduler scheduler = new ProbeCheckScheduler(applicationServers);
        scheduler.startChecking(30, TimeUnit.SECONDS);
    }
}
