package com.github.aaric.achieve.htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;

/**
 * WebClientTest
 *
 * @author Aaric, created on 2017-06-19T09:54.
 * @since 1.0-SNAPSHOT
 */
public class HtmlUnitTest {

    @Test
    public void testGetHtml() throws Exception {
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setTimeout(30000);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);

        HtmlPage page = client.getPage("http://www.cooggo.com:18080/");
        System.out.println(page.asXml());

        client.close();
    }
}
