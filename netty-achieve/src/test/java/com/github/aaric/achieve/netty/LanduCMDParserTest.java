package com.github.aaric.achieve.netty;

import com.incarcloud.rooster.datapack.util.DataPackUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * LANDU协议解析
 *
 * @author Aaric, created on 2017-06-06T16:03.
 * @since 1.0-SNAPSHOT
 */
public class LanduCMDParserTest {

    private byte[] data;

    @Before
    public void begin() throws IOException {
        // 准备数据
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        FileInputStream fio = new FileInputStream(new File(desktopPath, "output.dat"));
        data = new byte[fio.available()];
        fio.read(data, 0, fio.available());
        fio.close();
    }

    /**
     * 3.1.1 车辆检测数据主动上传(0x1601)<br>
     *     格式：【命令字】+【远程诊断仪串号（设备号）】+【TripID】+【VID】+【VIN 码】+【取得检测数据时间戳】+【数据属性标识】+【数据内容】
     *
     * @throws Exception
     */
    @Test
    public void testVehicleDataInitiativeUpload() throws Exception {
        /* 测试数据 */
        ByteBuf buffer = Unpooled.wrappedBuffer(data);

        /* 假定无格式问题数据，开始解析数据内容 */
        System.out.println("-------------------------begin");
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
                    // 7.1 0x01-发动机点火时
                    // 数据格式: 【数据内容】::=【点火电压值】+【定位信息】
                    System.out.println("## 0x01-发动机点火时");

                    // 7.1.1 点火电源
                    String firingVoltageValue = DataPackUtil.readString(buffer);
                    System.out.printf("firingVoltageValue: %skm/h\n", firingVoltageValue);

                    // 7.1.2 定位信息
                    // 数据格式：【定位信息】::=【车速】+【当前行程行驶距离】+【经度】+【分割符】+【纬度】+【分割符】+【方向】+【分割符】+【定位时间】+【分割符】+【定位方式】
                    // 7.1.2.1 车速
                    String speed = DataPackUtil.readString(buffer);
                    System.out.printf("speed: %s\n", speed);

                    // 7.1.2.2 当前行程行驶距离
                    String travelDistance = DataPackUtil.readString(buffer);
                    System.out.printf("travelDistance: %s\n", travelDistance);

                    // 7.1.2.3 经度
                    String longitude = DataPackUtil.readSeparatorString(buffer);
                    System.out.printf("longitude: %s\n", longitude);

                    // 7.1.2.4 纬度
                    String latitude = DataPackUtil.readSeparatorString(buffer);
                    System.out.printf("latitude: %s\n", latitude);

                    // 7.1.2.5 方向
                    String direction = DataPackUtil.readSeparatorString(buffer);
                    System.out.printf("direction: %s\n", direction);

                    // 7.1.2.6 定位时间
                    String positioningDate = DataPackUtil.readSeparatorString(buffer);
                    System.out.printf("positioningDate: %s\n", positioningDate);

                    // 7.1.2.7 定位方式
                    String positioningMode = DataPackUtil.readString(buffer);
                    System.out.printf("positioningMode: %s\n", positioningMode);

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
        System.out.println("-------------------------end");
        System.out.printf("readerIndex: %d, writerIndex: %d\n", buffer.readerIndex(), buffer.writerIndex());

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
