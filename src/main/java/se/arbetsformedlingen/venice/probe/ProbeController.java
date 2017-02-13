package se.arbetsformedlingen.venice.probe;

import spark.Request;
import spark.Response;

public class ProbeController {

    public static String getStatus(Request request, Response response) {
        return "[" +
                "{\"name\":\"GFR\",\"environments\":[{\"name\":\"production\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700770\":{\"status\":\"offline\",\"version\":\"4.6.401\"}}},{\"name\":\"t2\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"t1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"i1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"u1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}}]}," +
                "{\"name\":\"GEO\",\"environments\":[{\"name\":\"production\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700770\":{\"status\":\"offline\",\"version\":\"4.6.401\"}}},{\"name\":\"t2\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"t1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"i1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"u1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}}]}," +
                "{\"name\":\"CPR\",\"environments\":[{\"name\":\"production\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700770\":{\"status\":\"offline\",\"version\":\"4.6.401\"}}},{\"name\":\"t2\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"t1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"i1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"u1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}}]}," +
                "{\"name\":\"AGSelect\",\"environments\":[{\"name\":\"production\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700770\":{\"status\":\"offline\",\"version\":\"4.6.401\"}}},{\"name\":\"t2\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"t1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"i1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"u1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}}]}" +
                "]";
    }

}
