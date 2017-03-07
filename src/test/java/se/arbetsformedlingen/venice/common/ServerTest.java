package se.arbetsformedlingen.venice.common;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerTest {

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

        List<Server> servers = new LinkedList<>();

        Server geo1 = new Server(geo, u1, new Host("l7700649.u1.local"), port);
        Server geo2 = new Server(geo, i1, new Host("l7700671.i1.local"), port);
        Server geo3 = new Server(geo, t1, new Host("l7700666.upa.ams.se"), port);
        Server geo4 = new Server(geo, t2, new Host("l7700745.ata.ams.se"), port);
        Server geo5 = new Server(geo, t2, new Host("l7700744.ata.ams.se"), port);
        Server geo6 = new Server(geo, prod, new Host("l7700770.wpa.ams.se"), port);
        Server geo7 = new Server(geo, prod, new Host("l7700747.wpa.ams.se"), port);
        Server geo8 = new Server(geo, prod, new Host("l7700746.wpa.ams.se"), port);
        servers.add(geo1);
        servers.add(geo2);
        servers.add(geo3);
        servers.add(geo4);
        servers.add(geo5);
        servers.add(geo6);
        servers.add(geo7);
        servers.add(geo8);

        Server cpr1 = new Server(cpr, u1, new Host("l7700762.u1.local"), port);
        Server cpr2 = new Server(cpr, i1, new Host("l7700761.i1.local"), port);
        Server cpr3 = new Server(cpr, t1, new Host("l7700743.upa.ams.se"), port);
        Server cpr4 = new Server(cpr, t2, new Host("l7700774.ata.ams.se"), port);
        Server cpr5 = new Server(cpr, t2, new Host("l7700773.ata.ams.se"), port);
        Server cpr6 = new Server(cpr, prod, new Host("l7700775.wpa.ams.se"), port);
        Server cpr7 = new Server(cpr, prod, new Host("l7700772.wpa.ams.se"), port);
        Server cpr8 = new Server(cpr, prod, new Host("l7700767.wpa.ams.se"), port);
        servers.add(cpr1);
        servers.add(cpr2);
        servers.add(cpr3);
        servers.add(cpr4);
        servers.add(cpr5);
        servers.add(cpr6);
        servers.add(cpr7);
        servers.add(cpr8);

        Server gfr1 = new Server(gfr, u1, new Host("l7700649.u1.local"), port);
        Server gfr2 = new Server(gfr, i1, new Host("l7700671.i1.local"), port);
        Server gfr3 = new Server(gfr, t1, new Host("l7700666.upa.ams.se"), port);
        Server gfr4 = new Server(gfr, t2, new Host("l7700745.ata.ams.se"), port);
        Server gfr5 = new Server(gfr, t2, new Host("l7700744.ata.ams.se"), port);
        Server gfr6 = new Server(gfr, prod, new Host("l7700770.wpa.ams.se"), port);
        Server gfr7 = new Server(gfr, prod, new Host("l7700747.wpa.ams.se"), port);
        Server gfr8 = new Server(gfr, prod, new Host("l7700746.wpa.ams.se"), port);
        servers.add(gfr1);
        servers.add(gfr2);
        servers.add(gfr3);
        servers.add(gfr4);
        servers.add(gfr5);
        servers.add(gfr6);
        servers.add(gfr7);
        servers.add(gfr8);

        Server agselect1 = new Server(agselect, u1, new Host("l7700797.u1.local"), port);
        Server agselect2 = new Server(agselect, i1, new Host("l7700798.i1.local"), port);
        Server agselect3 = new Server(agselect, t1, new Host("l7700788.upa.ams.se"), port);
        Server agselect4 = new Server(agselect, t2, new Host("l7700837.ata.ams.se"), port);
        Server agselect5 = new Server(agselect, t2, new Host("l7700836.ata.ams.se"), port);
        Server agselect6 = new Server(agselect, prod, new Host("l7700843.wpa.ams.se"), port);
        Server agselect7 = new Server(agselect, prod, new Host("l7700842.wpa.ams.se"), port);
        Server agselect8 = new Server(agselect, prod, new Host("l7700841.wpa.ams.se"), port);
        servers.add(agselect1);
        servers.add(agselect2);
        servers.add(agselect3);
        servers.add(agselect4);
        servers.add(agselect5);
        servers.add(agselect6);
        servers.add(agselect7);
        servers.add(agselect8);

        Collections.shuffle(servers);
        Collections.sort(servers);

        assertThat(servers).containsExactly(
                agselect8, agselect7, agselect6, agselect5, agselect4, agselect3, agselect2, agselect1,
                cpr8, cpr7, cpr6, cpr5, cpr4, cpr3, cpr2, cpr1,
                geo8, geo7, geo6, geo5, geo4, geo3, geo2, geo1,
                gfr8, gfr7, gfr6, gfr5, gfr4, gfr3, gfr2, gfr1
        );

    }
}