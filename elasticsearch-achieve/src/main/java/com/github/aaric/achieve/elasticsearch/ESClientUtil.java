package com.github.aaric.achieve.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * TransportClientUtil
 *
 * @author Aaric, created on 2017-07-10T11:09.
 * @since 1.0-SNAPSHOT
 */
public class ESClientUtil {

    /**
     * 获得ES客户端
     *
     * @param clusterName 集群名称
     * @param serverMap 集群地址
     * @return
     * @throws UnknownHostException
     */
    public static Client getClusterClient(String clusterName, Map<String, String> serverMap) throws UnknownHostException {
        if(null != clusterName && !"".equals(clusterName.trim())) {
            if(null != serverMap && 0 < serverMap.size()) {
                Settings settings = Settings.settingsBuilder()
                        .put("cluster.name", clusterName)
                        .put("client.transport.sniff", true)
                        .build();
                TransportClient client = TransportClient.builder().settings(settings).build();
                for(Map.Entry<String, String> entry: serverMap.entrySet()) {
                    client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(entry.getKey()), Integer.parseInt("9300")));
                }
                return client;
            }
        }
        return null;
    }

    protected ESClientUtil() {}
}
