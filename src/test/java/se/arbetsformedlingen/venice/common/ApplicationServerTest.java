package se.arbetsformedlingen.venice.common;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationServerTest {

    @Test
    public void sort_servers() {
        Application geo = new Application("geo");
        Application cpr = new Application("cpr");
        Application gfr = new Application("gfr");
        Application agselect = new Application("agselect");

        Environment prod = new Environment("PROD");
        Environment t2 = new Environment("t2");
        Environment t1 = new Environment("t1");
        Environment i1 = new Environment("i1");
        Environment u1 = new Environment("u1");

        Port port = new Port("8180");

        List<ApplicationServer> applicationServers = new LinkedList<>();

        ApplicationServer geo1 = new ApplicationServer(geo, u1, new Host("l7700649.u1.local"), port);
        ApplicationServer geo2 = new ApplicationServer(geo, i1, new Host("l7700671.i1.local"), port);
        ApplicationServer geo3 = new ApplicationServer(geo, t1, new Host("l7700666.upa.ams.se"), port);
        ApplicationServer geo4 = new ApplicationServer(geo, t2, new Host("l7700745.ata.ams.se"), port);
        ApplicationServer geo5 = new ApplicationServer(geo, t2, new Host("l7700744.ata.ams.se"), port);
        ApplicationServer geo6 = new ApplicationServer(geo, prod, new Host("l7700770.wpa.ams.se"), port);
        ApplicationServer geo7 = new ApplicationServer(geo, prod, new Host("l7700747.wpa.ams.se"), port);
        ApplicationServer geo8 = new ApplicationServer(geo, prod, new Host("l7700746.wpa.ams.se"), port);
        applicationServers.add(geo1);
        applicationServers.add(geo2);
        applicationServers.add(geo3);
        applicationServers.add(geo4);
        applicationServers.add(geo5);
        applicationServers.add(geo6);
        applicationServers.add(geo7);
        applicationServers.add(geo8);

        ApplicationServer cpr1 = new ApplicationServer(cpr, u1, new Host("l7700762.u1.local"), port);
        ApplicationServer cpr2 = new ApplicationServer(cpr, i1, new Host("l7700761.i1.local"), port);
        ApplicationServer cpr3 = new ApplicationServer(cpr, t1, new Host("l7700743.upa.ams.se"), port);
        ApplicationServer cpr4 = new ApplicationServer(cpr, t2, new Host("l7700774.ata.ams.se"), port);
        ApplicationServer cpr5 = new ApplicationServer(cpr, t2, new Host("l7700773.ata.ams.se"), port);
        ApplicationServer cpr6 = new ApplicationServer(cpr, prod, new Host("l7700775.wpa.ams.se"), port);
        ApplicationServer cpr7 = new ApplicationServer(cpr, prod, new Host("l7700772.wpa.ams.se"), port);
        ApplicationServer cpr8 = new ApplicationServer(cpr, prod, new Host("l7700767.wpa.ams.se"), port);
        applicationServers.add(cpr1);
        applicationServers.add(cpr2);
        applicationServers.add(cpr3);
        applicationServers.add(cpr4);
        applicationServers.add(cpr5);
        applicationServers.add(cpr6);
        applicationServers.add(cpr7);
        applicationServers.add(cpr8);

        ApplicationServer gfr1 = new ApplicationServer(gfr, u1, new Host("l7700649.u1.local"), port);
        ApplicationServer gfr2 = new ApplicationServer(gfr, i1, new Host("l7700671.i1.local"), port);
        ApplicationServer gfr3 = new ApplicationServer(gfr, t1, new Host("l7700666.upa.ams.se"), port);
        ApplicationServer gfr4 = new ApplicationServer(gfr, t2, new Host("l7700745.ata.ams.se"), port);
        ApplicationServer gfr5 = new ApplicationServer(gfr, t2, new Host("l7700744.ata.ams.se"), port);
        ApplicationServer gfr6 = new ApplicationServer(gfr, prod, new Host("l7700770.wpa.ams.se"), port);
        ApplicationServer gfr7 = new ApplicationServer(gfr, prod, new Host("l7700747.wpa.ams.se"), port);
        ApplicationServer gfr8 = new ApplicationServer(gfr, prod, new Host("l7700746.wpa.ams.se"), port);
        applicationServers.add(gfr1);
        applicationServers.add(gfr2);
        applicationServers.add(gfr3);
        applicationServers.add(gfr4);
        applicationServers.add(gfr5);
        applicationServers.add(gfr6);
        applicationServers.add(gfr7);
        applicationServers.add(gfr8);

        ApplicationServer agselect1 = new ApplicationServer(agselect, u1, new Host("l7700797.u1.local"), port);
        ApplicationServer agselect2 = new ApplicationServer(agselect, i1, new Host("l7700798.i1.local"), port);
        ApplicationServer agselect3 = new ApplicationServer(agselect, t1, new Host("l7700788.upa.ams.se"), port);
        ApplicationServer agselect4 = new ApplicationServer(agselect, t2, new Host("l7700837.ata.ams.se"), port);
        ApplicationServer agselect5 = new ApplicationServer(agselect, t2, new Host("l7700836.ata.ams.se"), port);
        ApplicationServer agselect6 = new ApplicationServer(agselect, prod, new Host("l7700843.wpa.ams.se"), port);
        ApplicationServer agselect7 = new ApplicationServer(agselect, prod, new Host("l7700842.wpa.ams.se"), port);
        ApplicationServer agselect8 = new ApplicationServer(agselect, prod, new Host("l7700841.wpa.ams.se"), port);
        applicationServers.add(agselect1);
        applicationServers.add(agselect2);
        applicationServers.add(agselect3);
        applicationServers.add(agselect4);
        applicationServers.add(agselect5);
        applicationServers.add(agselect6);
        applicationServers.add(agselect7);
        applicationServers.add(agselect8);

        Collections.shuffle(applicationServers);
        Collections.sort(applicationServers);

        assertThat(applicationServers).containsExactly(
                agselect8, agselect7, agselect6, agselect5, agselect4, agselect3, agselect2, agselect1,
                cpr8, cpr7, cpr6, cpr5, cpr4, cpr3, cpr2, cpr1,
                geo8, geo7, geo6, geo5, geo4, geo3, geo2, geo1,
                gfr8, gfr7, gfr6, gfr5, gfr4, gfr3, gfr2, gfr1
        );

    }
}