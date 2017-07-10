package com.github.aaric.achieve.elasticsearch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Spring Boot Launcher.
 *
 * @author Aaric, created on 2017-07-10T11:05.
 * @since 1.0-SNAPSHOT
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    /**
     * blockingQueue
     */
    public static final BlockingQueue<ESBean> blockingQueue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> serverMap = new HashMap<>();
        serverMap.put("linux7-1", "9300");

        new Thread(new ESReader("i_his_battery_data_v1", "i_his_battery_data", "his_battery_data.dat", blockingQueue)).start();
        new Thread(new ESImporter("es-cluster", serverMap, blockingQueue)).start();
    }
}
