package com.github.aaric.achieve.elasticsearch;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * ESImporterTest
 *
 * @author Aaric, created on 2017-07-10T11:42.
 * @since 1.0-SNAPSHOT
 */
public class ESBeanUtilTest {

    @Test
    public void testImports() throws Exception {
        List<ESBean> beanList = ESBeanUtil.imports("i_his_battery_data_v1", "i_his_battery_data", "his_battery_data.dat");
//        beanList = ESImporter.imports("i_his_bms_data_v1", "i_his_bms_data", "his_bms_data.dat");
//        beanList = ESImporter.imports("i_his_gprs_data_v1", "i_his_gprs_data", "his_gps_data.dat");
//        beanList = ESImporter.imports("i_his_hvac_data", "i_his_hvac_data", "his_hvac_data.dat");
//        beanList = ESImporter.imports("i_his_obc_data_v1", "i_his_obc_data", "his_obc_data.dat");
//        beanList = ESImporter.imports("i_his_vehicle_motor_data_v2", "i_his_vehicle_motor_data", "his_motor_data.dat");
        Assert.assertNotNull(beanList);
    }
}
