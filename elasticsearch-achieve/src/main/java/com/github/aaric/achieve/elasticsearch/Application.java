package com.github.aaric.achieve.elasticsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ESImporter.class);

    /**
     * blockingQueue
     */
    public static final BlockingQueue<ESBean> blockingQueue = new ArrayBlockingQueue<>(10);

    @Override
    public void run(String... args) throws Exception {
        String clusterName = "es-cluster";
        Map<String, String> serverMap = new HashMap<>();
        serverMap.put("linux7-1", "9300");

        new Thread(new ESReader("i_his_battery_data_v1", "i_his_battery_data", "his_battery_data.dat", blockingQueue)).start();
        new Thread(new ESReader("i_his_bms_data_v1", "i_his_bms_data", "his_bms_data.dat", blockingQueue)).start();
        new Thread(new ESReader("i_his_gprs_data_v1", "i_his_gprs_data", "his_gps_data.dat", blockingQueue)).start();
        new Thread(new ESReader("i_his_hvac_data", "i_his_hvac_data", "his_hvac_data.dat", blockingQueue)).start();
        new Thread(new ESReader("i_his_obc_data_v1", "i_his_obc_data", "his_obc_data.dat", blockingQueue)).start();
        new Thread(new ESReader("i_his_vehicle_motor_data_v2", "i_his_vehicle_motor_data", "his_motor_data.dat", blockingQueue)).start();
        new Thread(new ESImporter(clusterName, serverMap, blockingQueue)).start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
