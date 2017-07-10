package com.github.aaric.achieve.elasticsearch;

import java.io.File;

/**
 * PathUtil
 *
 * @author Aaric, created on 2017-07-10T14:36.
 * @since 1.0-SNAPSHOT
 */
public class PathUtil {

    public static String getBasePath() {
        String basePath = PathUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if(basePath.contains(".jar")) {
            basePath = basePath.replace("file:", "");
            basePath = basePath.replaceAll("\\.jar.*", "");
            basePath = basePath.substring(0, basePath.lastIndexOf("/"));
        } else {
            basePath = System.getProperty("user.dir")
                    + File.separator + "src"
                    + File.separator + "test"
                    + File.separator + "resources";
        }
        return basePath;
    }

    protected PathUtil() {}
}
