package com.github.aaric.achieve.netty;

import com.incarcloud.rooster.datapack.util.DataPackUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * LANDU协议解析
 *
 * @author Aaric, created on 2017-06-06T16:03.
 * @since 1.0-SNAPSHOT
 */
public class LanduCMDParserTest {

    /**
     * 3.1.1 车辆检测数据主动上传(0x1601)<br>
     *     格式：【命令字】+【远程诊断仪串号（设备号）】+【TripID】+【VID】+【VIN 码】+【取得检测数据时间戳】+【数据属性标识】+【数据内容】
     *
     * @throws Exception
     */
    @Test
    public void testVehicleDataInitiativeUpload() throws Exception {
        /* 测试数据 */
        byte[] array = {
                (byte) 0xAA, 0x55,
                0x00, 0x6A,
                (byte) 0xFF, (byte) 0x95,
                0x00,
                0x05,
                0x16, 0x01, 0x49, 0x4E, 0x43, 0x41, 0x52, 0x30, 0x30, 0x30, 0x30, 0x30, 0x39, 0x00, 0x00, 0x00, 0x00, 0x52, 0x31, 0x33, 0x00, 0x00, 0x32, 0x30, 0x31, 0x34, 0x2D, 0x30, 0x39, 0x2D, 0x30, 0x34, 0x20, 0x32, 0x33, 0x3A, 0x31, 0x35, 0x3A, 0x32, 0x33, 0x00, 0x01, 0x37, 0x2E, 0x38, 0x00, 0x30, 0x00, 0x30, 0x00, 0x57, 0x30, 0x30, 0x30, 0x2E, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x2C, 0x53, 0x30, 0x30, 0x2E, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x2C, 0x30, 0x2C, 0x31, 0x39, 0x37, 0x30, 0x2D, 0x30, 0x31, 0x2D, 0x30, 0x31, 0x20, 0x30, 0x30, 0x3A, 0x30, 0x30, 0x3A, 0x30, 0x34, 0x2C, 0x30, 0x00,
                0x13, 0x01
        };
        ByteBuf buffer = Unpooled.wrappedBuffer(array);

        /* 假定无格式问题数据，开始解析数据内容 */
        System.out.println("-------------------------");
        int offset;
        // 1.丢弃协议头
        buffer.skipBytes(8);
        // 2.判断命令字-0x1601
        offset = buffer.readerIndex();
        if(0x16 == buffer.getByte(offset) && 0x01 == buffer.getByte(offset+1)) {
            // 丢弃命令字
            buffer.skipBytes(2);

            // 3.远程诊断仪串号（设备号）
            // 扫描字符串
            String obdCode = DataPackUtil.readString(buffer);
            System.out.printf("obdCode: %s\n", obdCode);

            // 4.TripID-4个字节
            int tripId = DataPackUtil.readUInt4(buffer);
            System.out.printf("tripId: %d\n", tripId);

            // 5.VID
            // 扫描字符串
            String vid = DataPackUtil.readString(buffer);
            System.out.printf("vid: %s\n", vid);

            // 5.VIN
            // 扫描字符串
            String vin = DataPackUtil.readString(buffer);
            System.out.printf("vin: %s\n", vin);

            // 6.取得检测数据时间戳
            String receiveDate = DataPackUtil.readString(buffer);
            System.out.printf("receiveDate: %s\n", receiveDate);

            // 7.数据属性标识
            int dataAttrType = DataPackUtil.readUInt1(buffer);
            switch (dataAttrType) {
                case 0x01:
                    // 0x01-发动机点火时
                    // 数据格式: 【数据内容】::=【点火电压值】+【定位信息】
                    System.out.println("## 0x01-发动机点火时");

                    // 7.1 点火电源
                    String firingVoltageValue = DataPackUtil.readString(buffer);
                    System.out.printf("firingVoltageValue: %skm/h\n", firingVoltageValue);

                    // 7.2 定位信息
                    // 数据格式：【定位信息】::=【车速】+【当前行程行驶距离】+【经度】+【分割符】+【纬度】+【分割符】+【方向】+【分割符】+【定位时间】+【分割符】+【定位方式】
                    // 7.2.1 车速
                    String speed = DataPackUtil.readString(buffer);
                    System.out.printf("speed: %s\n", speed);

                    // 7.2.2 当前行程行驶距离
                    String travelDistance = DataPackUtil.readString(buffer);
                    System.out.printf("travelDistance: %s\n", travelDistance);

                    break;
                case 0x02:
                    // 0x02-发动机运行中
                    // 数据格式: 【数据内容】::=【参数数量】+【【数据 ID】+【ID 数据内容】】+…
                    System.out.println("## 0x02-发动机运行中");
                    break;
                case 0x03:
                    // 0x03-发动机熄火时
                    // 数据格式: 【数据内容】::=【本行程数据小计】+【本行程车速分组统计】+【驾驶习惯统计】+【定位信息】
                    System.out.println("## 0x03-发动机熄火时");
                    break;
                case 0x04:
                    // 0x04-发动机熄火后
                    // 数据格式: 【数据内容】::=【蓄电池电压值】
                    System.out.println("## 0x04-发动机熄火后");
                    break;
                case 0x05:
                    // 0x05-车辆不能检测
                    // 数据格式: 无【数据内容】上传
                    System.out.println("## 0x05-车辆不能检测");
                    break;
            }

        }
        System.out.println("-------------------------");

        /* 释放资源 */
        buffer.release();
    }

    /**
     * 3.1.2 上传车辆报警(0x1602)
     *
     * @throws Exception
     */
    @Test
    public void testUploadVehicleVibrationAlarm() throws Exception {

    }

    /**
     * 3.1.3 从服务器取得参数(0x1603)
     *
     * @throws Exception
     */
    @Test
    public void testParametersDerivedFromTheServer() throws Exception {

    }

    /**
     * 3.1.4 上传调试数据(0x1605)
     *
     * @throws Exception
     */
    @Test
    public void testUploadDebugData() throws Exception {

    }

    /**
     * 3.1.5 位置数据(0x1606)
     *
     * @throws Exception
     */
    @Test
    public void testPositionData() throws Exception {

    }

    /**
     * 3.1.6 冻结帧数据(0x1607)
     *
     * @throws Exception
     */
    @Test
    public void testFreezeFrame() throws Exception {

    }

    /**
     * 3.1.7 怠速车况数据(0x1608)
     *
     * @throws Exception
     */
    @Test
    public void testIdlingData() throws Exception {

    }

    /**
     * 3.1.9 行为位置数据(0x160A)
     *
     * @throws Exception
     */
    @Test
    public void testUploadVehicleData() throws Exception {

    }
}
