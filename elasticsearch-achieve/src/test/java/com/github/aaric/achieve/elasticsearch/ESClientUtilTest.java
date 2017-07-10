package com.github.aaric.achieve.elasticsearch;

import org.elasticsearch.client.Client;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * ESClientTest
 *
 * @author Aaric, created on 2017-07-10T11:26.
 * @since 1.0-SNAPSHOT
 */
public class ESClientUtilTest {

    @Test
    @Ignore
    public void testGetClusterClient() throws Exception {
        Map<String, String> serverMap = new HashMap<>();
        serverMap.put("linux7-1", "9300");
        Client client = ESClientUtil.getClusterClient("es-cluster", serverMap);
        Assert.assertNotNull(client);
    }
}
