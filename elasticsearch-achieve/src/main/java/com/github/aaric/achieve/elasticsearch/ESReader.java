package com.github.aaric.achieve.elasticsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * ESReader
 *
 * @author Aaric, created on 2017-07-10T12:24.
 * @since 1.0-SNAPSHOT
 */
public class ESReader implements Runnable {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ESReader.class);

    private String index;
    private String type;
    private String fileName;
    private BlockingQueue<ESBean> blockingQueue;

    public ESReader(String index, String type, String fileName, BlockingQueue<ESBean> blockingQueue) {
        this.index = index;
        this.type = type;
        this.fileName = fileName;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            List<ESBean> beanList = ESBeanUtil.imports(index, type, fileName);
            if(null != beanList && 0 < beanList.size()) {
                for (ESBean bean: beanList) {
                    blockingQueue.put(bean);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
