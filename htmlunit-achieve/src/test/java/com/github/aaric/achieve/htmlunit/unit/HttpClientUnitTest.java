package com.github.aaric.achieve.htmlunit.unit;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * HttpClientUnitTest
 *
 * @author Aaric, created on 2017-06-19T09:55.
 * @since 1.0-SNAPSHOT
 */
public class HttpClientUnitTest {

    @Test
    public void testGet() throws Exception {
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(15000)
                .setConnectTimeout(1500)
                .setConnectionRequestTimeout(30000)
                .build();
        CloseableHttpClient httpClient;
        CloseableHttpResponse httpResponse;
        httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.cooggo.com:18080/");
        httpGet.setConfig(config);
        httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        System.out.println(EntityUtils.toString(httpEntity, "UTF-8"));
        httpResponse.close();
        httpClient.close();
    }
}
