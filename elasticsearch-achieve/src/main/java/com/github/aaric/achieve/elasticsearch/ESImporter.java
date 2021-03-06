package com.github.aaric.achieve.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * ESImporter
 *
 * @author Aaric, created on 2017-07-10T12:24.
 * @since 1.0-SNAPSHOT
 */
public class ESImporter implements Runnable {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ESImporter.class);

    private String clusterName;
    private Map<String, String> serverMap;
    private BlockingQueue<ESBean> blockingQueue;

    public ESImporter(String clusterName, Map<String, String> serverMap, BlockingQueue<ESBean> blockingQueue) {
        this.clusterName = clusterName;
        this.serverMap = serverMap;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        Client client = null;
        try {
            client = ESClientUtil.getClusterClient(clusterName, serverMap);
            ESBean bean = null;
            do {
                bean = blockingQueue.take();
                IndexResponse response = client.prepareIndex(bean.getIndex(), bean.getType(), bean.getId())
                        .setSource(bean.getData()).execute().get();
                if(response.isCreated()) {
                    System.out.println("[import] " + bean.getId() + "　ok!");
                    logger.info("[import] " + bean.getId() + "　ok!");
                } else {
                    System.err.println("[import] " + bean.getId() + "　error!");
                    logger.info("[import] " + bean.getId() + "　error!");
                }

            } while (null != bean);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != client) {
                client.close();
            }
        }
    }
}
