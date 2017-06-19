package com.github.aaric.achieve.htmlunit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * JsoupTest
 *
 * @author Aaric, created on 2017-06-19T10:28.
 * @since 1.0-SNAPSHOT
 */
public class JsoupTest {

    @Test
    public void testGetTitle() throws Exception {
        Document document = Jsoup
                .connect("http://www.cooggo.com:18080/")
                .get();
        Elements titles = document.getElementsByTag("title");
        for (Element title : titles) {
            System.out.println(title.text());
        }
    }
}
