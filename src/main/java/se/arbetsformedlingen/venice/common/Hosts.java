package se.arbetsformedlingen.venice.common;

import java.util.Arrays;
import java.util.List;

public class Hosts {
    public static final String GFR_U1 = "L7700649.u1.local";
    public static final String GFR_I1 = "L7700671.i1.local";
    public static final String GFR_T1 = "L7700666.upa.ams.se";
    public static final String GFR_T21 = "L7700744.ata.ams.se";
    public static final String GFR_T22 = "L7700745.ata.ams.se";
    public static final String GFR_PROD1 = "L7700746.wpa.ams.se";
    public static final String GFR_PROD2 = "L7700747.wpa.ams.se";
    public static final String GFR_PROD3 = "L7700770.wpa.ams.se";

    public static final String GEO_U1 = "L7700649.u1.local";
    public static final String GEO_I1 = "L7700671.i1.local";
    public static final String GEO_T1 = "L7700666.upa.ams.se";
    public static final String GEO_T21 = "L7700744.ata.ams.se";
    public static final String GEO_T22 = "L7700745.ata.ams.se";
    public static final String GEO_PROD1 = "L7700746.wpa.ams.se";
    public static final String GEO_PROD2 = "L7700747.wpa.ams.se";
    public static final String GEO_PROD3 = "L7700770.wpa.ams.se";

    public static List<String> getGFRHosts() {
       return Arrays.asList(GFR_U1, GFR_I1, GFR_T1, GFR_T21, GFR_T22, GFR_PROD1, GFR_PROD2, GFR_PROD3);
    }
}
