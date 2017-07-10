package com.github.aaric.achieve.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * ElasticSearchTest
 *
 * @author Aaric, created on 2017-07-04T11:11.
 * @since 1.0-SNAPSHOT
 */
public class ElasticSearchTest {

    @Test
    @Ignore
    public void testImport() throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "es-cluster")
                .put("client.transport.sniff", true)
                .build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("linux7-1"), Integer.parseInt("9300")));

        String basePath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources";
        List<String> jsonList = new ArrayList<>();
        FileReader reader = new FileReader(new File(basePath, "his_battery_data.dat"));
        BufferedReader buffer = new BufferedReader(reader);
        String line;
        while (null != (line = buffer.readLine())) {
            jsonList.add(line);
        }
        buffer.close();
        reader.close();

        if(null != jsonList && 0 != jsonList.size()) {
            for (String json: jsonList) {
                System.out.println(json);
                String id = UUID.randomUUID().toString().replace("-", "");
                IndexResponse response = client.prepareIndex("i_his_battery_data_v1", "i_his_battery_data", id)
                        .setSource(json).execute().get();
                if(response.isCreated()) {
                    System.out.println(id + "　ok!");
                }
            }
        }

        client.close();
    }
}
