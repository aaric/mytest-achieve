package com.github.aaric.achieve.elasticsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ESImporter
 *
 * @author Aaric, created on 2017-07-10T11:42.
 * @since 1.0-SNAPSHOT
 */
public class ESBeanUtil {

    public static List<ESBean> imports(String index, String type, String fileName) throws IOException {
        List<ESBean> beanList = new ArrayList<>();
        String basePath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources";
        FileReader reader = new FileReader(new File(basePath, fileName));
        BufferedReader buffer = new BufferedReader(reader);
        String id;
        String line;
        while (null != (line = buffer.readLine())) {
            id = UUID.randomUUID().toString().replace("-", "");
            beanList.add(new ESBean(index, type, id, line));
        }
        buffer.close();
        reader.close();
        return beanList;
    }

    protected ESBeanUtil() {}
}
