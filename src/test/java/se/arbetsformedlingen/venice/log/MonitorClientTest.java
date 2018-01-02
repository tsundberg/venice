package se.arbetsformedlingen.venice.log;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import se.arbetsformedlingen.venice.model.*;

public class MonitorClientTest
{
    MonitorClient client;

    @Before
    public void setup_gfr_client() {
        String[] hosts = new String[] {
            "l7700746.wpa.ams.se",
            "l7700747.wpa.ams.se",
            "l7700770.wpa.ams.se"
        };

        String[] versions = new String[] {
            "3.0",
            "4.0",
            "5.0",
            "6.0"
        };

        int port = 8580;

        String format = "http://${host}:${port}/service/foretag/${version}/debug/servicelog";

        this.client = new MonitorClient(format, hosts, port, versions);
    }

    @Test
    public void test_fetch() throws Exception {
        MonitorResult result = client.fetch();
        System.out.println("Fetched " + result.getEntryCount() + " entries");

        List<ConsumingSystemValue> values = result.getConsumingSystemLoad();
        for (ConsumingSystemValue value : values) {
            System.out.println("[" + value.getConsumingSystem() + "] " + value.getTime() + ": " + value.getCalls());
        }

        List<HostValue> hostValues = result.getHostLoad();
        for (HostValue value : hostValues) {
            System.out.println(value.getHost() + ": " + value.getLoad());
        }
    }
}
